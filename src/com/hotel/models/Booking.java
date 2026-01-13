package com.hotel.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Booking {
    private int id;
    private User user;          
    private Room room;          
    private LocalDate checkIn;  
    private LocalDate checkOut; 
    private List<Service> services; 
    private double totalPrice;  

    public Booking(int id, User user, Room room, LocalDate checkIn, LocalDate checkOut, List<Service> services) {
        this.id = id;
        this.user = user;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.services = services;
        
        calculateTotalPrice();
    }

    
    public void calculateTotalPrice() {
       
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (nights <= 0) nights = 1; 

        
        double roomCost = room.calculatePrice(nights);

       
        double servicesCost = 0;
        if (services != null) {
            for (Service s : services) {
                servicesCost += s.getPrice();
            }
        }

    
        this.totalPrice = roomCost + servicesCost;
    }


    public double getTotalPrice() { return totalPrice; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { 
        this.room = room; 
        calculateTotalPrice(); // Бөлме өзгерсе, баға қайта есептелуі керек
    }

    public LocalDate getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { 
        this.checkIn = checkIn; 
        calculateTotalPrice(); // Күн өзгерсе, баға қайта есептеледі
    }

    public LocalDate getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { 
        this.checkOut = checkOut; 
        calculateTotalPrice(); 
    }

    public List<Service> getServices() { return services; }
    public void setServices(List<Service> services) { 
        this.services = services; 
        calculateTotalPrice(); // Қызметтер тізімі өзгерсе, баға жаңарады
    }
}
