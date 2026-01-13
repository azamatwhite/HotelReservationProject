package com.hotel;

import com.hotel.models.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Hotel Management System Test ---");

        // 1. Тесттік нысандар (Конструкторларды модельге сай жазамыз)
        // Ескерту: Модельдегі параметрлер ретін тексер: (id, name, email, phone)
        User testUser = new User(1, "Askar", "askar@mail.ru", "87071112233");
        
        RoomCategory suiteCat = new RoomCategory(1, "Luxury Suite", 50000);
        
        // SuiteRoom конструкторы (id, roomNumber, category)
        Room testRoom = new SuiteRoom(101, 101, suiteCat);

        // 2. Ақпаратты шығару (getName() орнына getId() немесе тікелей қолдану)
        System.out.println("Client ID: " + testUser.getId());
        System.out.println("Room Category: " + testRoom.getCategory().getName());
        
        // 3. Баға есептеу
        double price = testRoom.calculatePrice(3);
        System.out.println("Total Price for 3 nights: " + price + " KZT");
    }
}