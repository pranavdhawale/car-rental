package com.car_rental.rental_service.dto.inventory;
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
