package com.hotel.repositories.interfaces; // Осы жолдың дұрыстығын тексер

import com.hotel.models.User;
import java.util.List;

public interface IUserRepository {
    void addUser(User user);
    User getUserById(int id);
    User getUserByEmail(String email);
    List<User> getAllUsers();
}