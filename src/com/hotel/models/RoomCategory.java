package com.hotel.models;

public class RoomCategory {
    private int id;
    private String name;
    private double basePrice;

    public RoomCategory(int id, String name, double basePrice) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public double getBasePrice() {
        return basePrice;
    }
}