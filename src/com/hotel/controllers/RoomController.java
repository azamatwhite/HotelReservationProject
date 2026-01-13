package com.hotel.controllers;

import com.hotel.models.Room;
import com.hotel.repositories.interfaces.IRoomRepository;
import java.util.List;

public class RoomController {
    private final IRoomRepository roomRepo;

    public RoomController(IRoomRepository roomRepo) {
        this.roomRepo = roomRepo;
    }

    // Барлық бос бөлмелерді тізім ретінде алу
    public List<Room> getAvailableRooms() {
        return roomRepo.getAvailableRooms();
    }

    // Бөлме туралы толық ақпаратты мәтін ретінде шығару
    public String getRoomDetails(int id) {
        Room room = roomRepo.getRoomById(id);
        if (room == null) return "Бөлме табылмады.";
        
        return "Бөлме №" + room.getId() + " | Түрі: " + room.getCategory().getName();
    }
}