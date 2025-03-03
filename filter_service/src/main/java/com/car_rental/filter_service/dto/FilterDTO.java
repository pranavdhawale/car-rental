package com.car_rental.filter_service.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterDTO {
    private UUID id;
    private UUID carId;
    private UUID modelId;
    private UUID brandId;
    private String modelName;
    private String brandName;
    private String plate;
    private int modelYear;
    private double dailyPrice;
    private String state;
}
