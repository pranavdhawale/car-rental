package com.car_rental.rental_service.service;

import com.car_rental.rental_service.dto.RentalDTO;

import java.util.List;
import java.util.UUID;

public interface RentalService {
    RentalDTO createRental(RentalDTO rentalDTO);
    RentalDTO getRentalById(UUID id);
    List<RentalDTO> getAllRentals();
    RentalDTO updateRental(UUID id,RentalDTO rentalDTO);
    void deleteRental(UUID id);
}
