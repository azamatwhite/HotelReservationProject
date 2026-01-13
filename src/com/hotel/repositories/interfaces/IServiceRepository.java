package com.hotel.repositories.interfaces;

import com.hotel.models.Service;
import java.util.List;

public interface IServiceRepository {
    List<Service> getAllServices();
    Service getServiceById(int id);
}