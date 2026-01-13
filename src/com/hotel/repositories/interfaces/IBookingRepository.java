package com.hotel.repositories.interfaces;

import com.hotel.models.Booking;
import java.time.LocalDate;
import java.util.List;

public interface IBookingRepository {
    boolean checkAvailability(int roomId, LocalDate checkIn, LocalDate checkOut);
    void createBooking(Booking booking);
    List<Booking> getBookingsByUserId(int userId);
    List<Booking> getAllBookings();
    void cancelBooking(int bookingId);
}