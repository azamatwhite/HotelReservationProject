package com.hotel.models;

public abstract class Room {
    private int id;
    private int roomNumber;
    private RoomCategory category;

    private boolean isAvailable = true; 

    public Room(int id, int roomNumber, RoomCategory category) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.category = category;
    }

    public int getRoomNumber(){
        return roomNumber;
    }

    public abstract double calculatePrice(long nights);


    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public int getId() { return id; }
    public RoomCategory getCategory() { return category; }
}