package com.hotel.repositories.impl;

import com.hotel.data.IDB;
import com.hotel.models.User;
import com.hotel.repositories.interfaces.IUserRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    @Override
    public void addUser(User user) {
        try (Connection con = db.getConnection()) {
            String sql = "INSERT INTO users(name, email, phone) VALUES (?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, user.getName());
            st.setString(2, user.getEmail()); // Геттер getEmail должен быть в модели User
            st.setString(3, user.getPhone()); // Геттер getPhone должен быть в модели User
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserById(int id) {
        try (Connection con = db.getConnection()) {
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        try (Connection con = db.getConnection()) {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection con = db.getConnection()) {
            String sql = "SELECT * FROM users";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}