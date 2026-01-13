package com.hotel.controllers;

import com.hotel.models.User;
import com.hotel.repositories.interfaces.IUserRepository;

public class UserController {
    private final IUserRepository userRepo;

    public UserController(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public String registerUser(String name, String email, String phone) {
        if (email == null || !email.contains("@")) {
            return "Қате: Email форматы дұрыс емес.";
        }

        User newUser = new User(0, name, email, phone);
        userRepo.addUser(newUser);
        
        return "Пайдаланушы сәтті тіркелді: " + name;
    }
}