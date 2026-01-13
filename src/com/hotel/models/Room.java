package com.hotel.models;

public abstract class Room {
    private int id;
    private int roomNumber;
    private RoomCategory category;
    // 1. Мына айнымалыны міндетті түрде қос:
    private boolean isAvailable = true; 

    public Room(int id, int roomNumber, RoomCategory category) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.category = category;
    }

    public abstract double calculatePrice(long nights);

    // 2. Мына Getter-ді қос (BookingController-дегі 36-жол үшін):
    public boolean isAvailable() {
        return isAvailable;
    }

    // 3. Мына Setter-ді қос (BookingController-дегі 45-жол үшін):
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public int getId() { return id; }
    public RoomCategory getCategory() { return category; }
}