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

    // Конструктор арқылы интерфейстерді қабылдаймыз (Dependency Injection)
    public BookingController(IBookingRepository bookingRepo, IRoomRepository roomRepo) {
        this.bookingRepo = bookingRepo;
        this.roomRepo = roomRepo;
    }

    // Жаңа брондау жасау әдісі
    public String makeBooking(User user, int roomId, String checkInStr, String checkOutStr, List<Service> services) {
        LocalDate checkIn = DateUtils.parseDate(checkInStr);
        LocalDate checkOut = DateUtils.parseDate(checkOutStr);

        // 1. Даталарды тексеру
        if (!DateUtils.isValidRange(checkIn, checkOut)) {
            return "Қате: Даталар дұрыс емес (өткен шақ немесе кету күні келу күнінен бұрын).";
        }

        // 2. Бөлмені табу
        Room room = roomRepo.getRoomById(roomId);
        if (room == null || !room.isAvailable()) {
            return "Қате: Бөлме табылмады немесе бос емес.";
        }

        // 3. Брондау нысанын құру (мұнда баға автоматты есептеледі)
        Booking booking = new Booking(0, user, room, checkIn, checkOut, services);
        
        // 4. Базаға сақтау
        bookingRepo.createBooking(booking);
        room.setAvailable(false); // Бөлмені "бос емес" деп белгілеу

        return "Брондау сәтті аяқталды! Жалпы сома: " + booking.getTotalPrice() + " KZT";
    }
}