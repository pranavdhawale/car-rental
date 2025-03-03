package com.car_rental.inventory_service.service;

import com.car_rental.inventory_service.entity.Car;
import java.util.List;
import java.util.UUID;

public interface CarService {
    List<Car> getAllCars();
    Car getCarById(UUID id);
    Car saveCar(Car car);
    void deleteCar(UUID id);
}
