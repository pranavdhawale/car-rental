package com.car_rental.rental_service.service.impl;

import com.car_rental.rental_service.dto.RentalDTO;
import com.car_rental.rental_service.entity.Rental;
import com.car_rental.rental_service.exception.ResourceNotFoundException;
import com.car_rental.rental_service.repository.RentalRepository;
import com.car_rental.rental_service.service.RentalService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final ModelMapper modelMapper;

    public RentalServiceImpl(RentalRepository rentalRepository, ModelMapper modelMapper) {
        this.rentalRepository = rentalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RentalDTO createRental(RentalDTO rentalDTO) {
        Rental rental = modelMapper.map(rentalDTO, Rental.class);
        rental = rentalRepository.save(rental);
        return modelMapper.map(rental, RentalDTO.class);
    }

    @Override
    public RentalDTO getRentalById(UUID id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id: " + id));
        return modelMapper.map(rental, RentalDTO.class);
    }

    @Override
    public List<RentalDTO> getAllRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(rental -> modelMapper.map(rental, RentalDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RentalDTO updateRental(UUID id, RentalDTO rentalDTO) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id: " + id));

        rental.setDailyPrice(rentalDTO.getDailyPrice());
        rental.setTotalPrice(rentalDTO.getTotalPrice());
        rental.setRentedForDays(rentalDTO.getRentedForDays());
        rental.setRentedAt(rentalDTO.getRentedAt());

        rental = rentalRepository.save(rental);
        return modelMapper.map(rental, RentalDTO.class);
    }

    @Override
    public void deleteRental(UUID id) {
        if (!rentalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Rental not found with id: " + id);
        }
        rentalRepository.deleteById(id);
    }
}
