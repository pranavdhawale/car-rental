package com.car_rental.inventory_service.dto;

import com.car_rental.inventory_service.entity.enums.CarState;
import lombok.*;

@Data
@NoArgsConstructor
public class UpdateCarStateDTO {
    private CarState state;

    public UpdateCarStateDTO(CarState state) {
        this.state = state;
    }
}