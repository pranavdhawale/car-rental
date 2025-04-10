package com.car_rental.rental_service.dto.inventory;

import com.car_rental.rental_service.dto.CarState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateCarStateDTO {
    private CarState state;

    public UpdateCarStateDTO(com.car_rental.rental_service.dto.CarState carState) {
        this.state = carState;
    }
}