package com.hotel.models;

public class RoomCategory {
    private int id;
    private String name;
    private double basePrice;

    // Конструктор: параметрлер реті мен түрі (int, String, double) маңызды
    public RoomCategory(int id, String name, double basePrice) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
    }

    // Main.java файлына осы керек
    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }
}