package com.hotel.repositories.interfaces;
import com.hotel.models.Room;
import java.util.List;
public interface IRoomRepository {
    void addRoom(Room room);         
    Room getRoomById(int id);        
    List<Room> getAllRooms();        
    List<Room> getAvailableRooms();

}
