package com.car_rental.rental_service.dto.inventory;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data @Builder
public class CarDetailsDTO {
    private UUID carId;
    private String modelName;
    private String brandName;
    private String plate;
    private int modelYear;
    private double dailyPrice;
}
