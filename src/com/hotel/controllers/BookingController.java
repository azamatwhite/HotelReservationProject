package com.hotel.controllers;

import com.hotel.models.Booking;
import com.hotel.models.Room;
import com.hotel.models.User;
import com.hotel.models.Service;
import com.hotel.repositories.interfaces.IBookingRepository;
import com.hotel.repositories.interfaces.IRoomRepository;
import com.hotel.utils.DateUtils;

import java.time.LocalDate;
import java.util.List;

public class BookingController {
    private final IBookingRepository bookingRepo;
    private final IRoomRepository roomRepo;

   
    public BookingController(IBookingRepository bookingRepo, IRoomRepository roomRepo) {
        this.bookingRepo = bookingRepo;
        this.roomRepo = roomRepo;
    }


    public String makeBooking(User user, int roomId, String checkInStr, String checkOutStr, List<Service> services) {
        LocalDate checkIn = DateUtils.parseDate(checkInStr);
        LocalDate checkOut = DateUtils.parseDate(checkOutStr);

        if (!DateUtils.isValidRange(checkIn, checkOut)) {
            return "Invalid dates.";
        }

        // сразу БД дан тексеретіндей қылдым
        boolean isFree = bookingRepo.checkAvailability(roomId, checkIn, checkOut);

        if (!isFree) {
            return "Room is already booked for these dates.";
        }

        Room room = roomRepo.getRoomById(roomId);
        Booking booking = new Booking(0, user, room, checkIn, checkOut, services);

        bookingRepo.createBooking(booking);

        return "Booking success! Total: " + booking.getTotalPrice() + " KZT";
        }
}