package com.car_rental.inventory_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarAvailabilityDTO {
    private boolean available;
    private Double dailyPrice;
}
