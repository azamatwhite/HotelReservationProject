package com.hotel.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    @Override
    public Connection getConnection() {
        String connectionUrl = "jdbc:postgresql://localhost:5432/db"; // Ваше имя БД
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(connectionUrl, "postgres", "root"); // Ваш логин/пароль
        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            return null;
        }
    }
}