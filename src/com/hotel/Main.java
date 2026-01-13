//package com.hotel;
//
//import com.hotel.data.PostgresDB;
//import com.hotel.data.IDB;
//import com.hotel.repositories.*;
//import com.hotel.controllers.*;
//import com.hotel.models.*;
//import java.util.Scanner;
//import java.util.ArrayList;
//
//public class Main {
//    public static void main(String[] args) {
//        IDB db = new PostgresDB();
//        UserRepository userRepo = new UserRepository(db);
//        RoomRepository roomRepo = new RoomRepository(db);
//        BookingRepository bookingRepo = new BookingRepository(db);
//
//        UserController userController = new UserController(userRepo);
//        BookingController bookingController = new BookingController(bookingRepo, roomRepo);
//
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            System.out.println("\n--- HOTEL SYSTEM MENU ---");
//            System.out.println("1. Register a user");
//            System.out.println("2. Show available rooms");
//            System.out.println("3. Book a room");
//            System.out.println("0. Exit");
//            System.out.print("Select an action: ");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Очистка буфера
//
//            if (choice == 0) break;
//
//            switch (choice) {
//                case 1:
//                    System.out.print("Enter your name: ");
//                    String name = scanner.nextLine();
//                    System.out.print("Enter your email: ");
//                    String email = scanner.nextLine();
//                    System.out.print("Enter your phone number: ");
//                    String phone = scanner.nextLine();
//                    System.out.println(userController.registerUser(name, email, phone));
//                    break;
//                case 2:
//                    System.out.println("List of all numbers:");
//                    for (Room r : roomRepo.getAllRooms()) {
//                        System.out.println("Room " + r.getRoomNumber() + " (" + r.getCategory().getName() + ") - " + r.getCategory().getBasePrice());
//                    }
//                    break;
//                case 3:
//                    System.out.print("Enter user ID: ");
//                    int userId = scanner.nextInt();
//                    System.out.print("Enter room ID: ");
//                    int roomId = scanner.nextInt();
//                    scanner.nextLine();
//                    System.out.print("Check-in date (YYYY-MM-DD): ");
//                    String checkIn = scanner.nextLine();
//                    System.out.print("Check-out date (YYYY-MM-DD): ");
//                    String checkOut = scanner.nextLine();
//
//                    // Для упрощения без услуг, или можно добавить выбор
//                    User user = userRepo.getUserById(userId);
//                    if (user != null) {
//                        String res = bookingController.makeBooking(user, roomId, checkIn, checkOut, new ArrayList<>());
//                        System.out.println(res);
//                    } else {
//                        System.out.println("User not found!");
//                    }
//                    break;
//                default:
//                    System.out.println("Wrong choice.");
//            }
//        }
//    }
//}

package com.hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        // 1. Проверяем настройки (ИЗМЕНИТЕ ПОД СЕБЯ)
        String url = "jdbc:postgresql://localhost:5432/hotel_db";
        String user = "postgres";
        String pass = "password"; // Ваш пароль от Postgres

        System.out.println("--- Тест подключения к БД ---");

        try {
            // 2. Проверяем драйвер
            Class.forName("org.postgresql.Driver");
            System.out.println("✅ Драйвер найден!");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ ОШИБКА: Драйвер PostgreSQL не найден. Добавьте файл postgresql-42.x.x.jar в библиотеки.");
            return; // Дальше нет смысла проверять
        }

        // 3. Проверяем соединение
        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            System.out.println("✅ Соединение с базой установлено успешно!");
            System.out.println("Используется схема: " + con.getSchema());
        } catch (SQLException e) {
            System.out.println("❌ ОШИБКА ПОДКЛЮЧЕНИЯ:");
            System.out.println("Текст ошибки: " + e.getMessage());

            if (e.getMessage().contains("does not exist")) {
                System.out.println("👉 Совет: Проверьте, создали ли вы базу данных с именем 'hotel_db'?");
            } else if (e.getMessage().contains("authentication failed")) {
                System.out.println("👉 Совет: Неверный пароль или логин (обычно логин 'postgres').");
            } else if (e.getMessage().contains("Connection refused")) {
                System.out.println("👉 Совет: Postgres вообще не запущен или порт не 5432.");
            }
        }
    }
}