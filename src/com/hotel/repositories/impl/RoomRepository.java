package com.hotel.repositories;

import com.hotel.data.IDB;
import com.hotel.models.*;
import com.hotel.repositories.interfaces.IRoomRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository implements IRoomRepository {
    private final IDB db;

    public RoomRepository(IDB db) {
        this.db = db;
    }

    @Override
    public void addRoom(Room room) {
        // Логика добавления (обычно для Admin части)
        try (Connection con = db.getConnection()) {
            String sql = "INSERT INTO rooms(room_number, category_id) VALUES (?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, room.getRoomNumber()); // Нужно добавить геттер в Room
            st.setInt(2, room.getCategory().getId());
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Room getRoomById(int id) {
        try (Connection con = db.getConnection()) {
            // JOIN нужен, чтобы получить название категории и цену
            String sql = "SELECT r.id, r.room_number, c.id as cat_id, c.name as cat_name, c.base_price " +
                    "FROM rooms r " +
                    "JOIN room_categories c ON r.category_id = c.id " +
                    "WHERE r.id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return mapRowToRoom(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Room> getAvailableRooms() {
        // Просто возвращает все комнаты.
        // Реальная фильтрация по датам происходит через checkAvailability в BookingRepository
        return getAllRooms();
    }

    @Override
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        try (Connection con = db.getConnection()) {
            String sql = "SELECT r.id, r.room_number, c.id as cat_id, c.name as cat_name, c.base_price " +
                    "FROM rooms r JOIN room_categories c ON r.category_id = c.id";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                rooms.add(mapRowToRoom(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    // Вспомогательный метод для создания правильного объекта (Standard или Suite)
    private Room mapRowToRoom(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int number = rs.getInt("room_number");

        RoomCategory cat = new RoomCategory(
                rs.getInt("cat_id"),
                rs.getString("cat_name"),
                rs.getDouble("base_price")
        );

        // Полиморфизм: создаем нужный класс в зависимости от названия категории
        if (cat.getName().toLowerCase().contains("suite")) {
            return new SuiteRoom(id, number, cat);
        } else {
            return new StandardRoom(id, number, cat);
        }
    }
}