package com.hotel.repositories.impl; // Внимание на пакет!

import com.hotel.data.IDB;
import com.hotel.models.Booking;
import com.hotel.models.Service;
import com.hotel.repositories.interfaces.IBookingRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository implements IBookingRepository {
    private final IDB db;

    public BookingRepository(IDB db) {
        this.db = db;
    }

    // 1. Метод проверки занятости (ЕГО НЕ БЫЛО В ВАШЕМ ФАЙЛЕ)
    @Override
    public boolean checkAvailability(int roomId, LocalDate checkIn, LocalDate checkOut) {
        try (Connection con = db.getConnection()) {
            // Логика: (StartA < EndB) AND (EndA > StartB)
            String sql = "SELECT COUNT(*) FROM bookings WHERE room_id = ? AND (check_in < ? AND check_out > ?)";
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, roomId);
            st.setDate(2, Date.valueOf(checkOut));
            st.setDate(3, Date.valueOf(checkIn));

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Если 0, значит свободно
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. Создание бронирования
    @Override
    public void createBooking(Booking booking) {
        Connection con = null;
        try {
            con = db.getConnection();
            con.setAutoCommit(false); // Начало транзакции

            String sqlBooking = "INSERT INTO bookings(user_id, room_id, check_in, check_out, total_price) VALUES (?, ?, ?, ?, ?) RETURNING id";

            // Важно: RETURN_GENERATED_KEYS для получения ID
            PreparedStatement st = con.prepareStatement(sqlBooking, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, booking.getUser().getId());
            st.setInt(2, booking.getRoom().getId());
            st.setDate(3, Date.valueOf(booking.getCheckIn()));
            st.setDate(4, Date.valueOf(booking.getCheckOut()));
            st.setDouble(5, booking.getTotalPrice());
            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();
            int bookingId = 0;
            if (rs.next()) {
                bookingId = rs.getInt(1);
            }

            // Сохранение услуг
            if (booking.getServices() != null && !booking.getServices().isEmpty()) {
                String sqlService = "INSERT INTO booking_services(booking_id, service_id) VALUES (?, ?)";
                PreparedStatement stService = con.prepareStatement(sqlService);

                for (Service service : booking.getServices()) {
                    stService.setInt(1, bookingId);
                    stService.setInt(2, service.getId());
                    stService.addBatch();
                }
                stService.executeBatch();
            }

            con.commit(); // Сохраняем всё
        } catch (SQLException e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if (con != null) con.setAutoCommit(true); con.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    @Override
    public List<Booking> getBookingsByUserId(int userId) { return new ArrayList<>(); }

    @Override
    public List<Booking> getAllBookings() { return new ArrayList<>(); }

    @Override
    public void cancelBooking(int bookingId) { }
}