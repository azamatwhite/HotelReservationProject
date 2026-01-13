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
            return "Error: Dates are incorrect (past tense or departure date is before arrival date).";
        }

        
        Room room = roomRepo.getRoomById(roomId);
        if (room == null || !room.isAvailable()) {
            return "Error: Room not found or not available.";
        }

       
        Booking booking = new Booking(0, user, room, checkIn, checkOut, services);
        
        
        bookingRepo.createBooking(booking);
        room.setAvailable(false);
        return "Booking completed successfully! Total amount: " + booking.getTotalPrice() + " KZT";
    }
}