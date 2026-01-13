package com.hotel.models;

public class SuiteRoom extends Room {
    public SuiteRoom(int id, int roomNumber, RoomCategory category) {
        super(id, roomNumber, category);
    }

    @Override
    public double calculatePrice(long nights) {
        
        return nights * getCategory().getBasePrice() * 1.5;
    }

}
