package com.hotel.repositories.interfaces;

import com.hotel.models.Booking;
import java.util.List;

public interface IBookingRepository {
    void createBooking(Booking booking);
    List<Booking> getBookingsByUserId(int userId);
    List<Booking> getAllBookings();
    void cancelBooking(int bookingId);
}