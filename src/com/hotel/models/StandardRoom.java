package com.hotel.models;

public class StandardRoom extends Room{
    public StandardRoom(int id, int roomNumber, RoomCategory category) {
        super(id, roomNumber, category);
    }
@Override
    public double calculatePrice(long nights) {
        // Стандартты бөлме үшін қарапайым формула:
        // Түндер саны * Категорияның базалық бағасы
        return nights * getCategory().getBasePrice();
    }

}
