package com.car_rental.filter_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "filter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID carId;

    @Column(nullable = false)
    private UUID modelId;

    @Column(nullable = false)
    private UUID brandId;

    @Column(nullable = false)
    private String modelName;

    @Column(nullable = false)
    private String brandName;

    @Column(nullable = false, unique = true)
    private String plate;

    @Column(nullable = false)
    private int modelYear;

    @Column(nullable = false)
    private double dailyPrice;

    @Column(nullable = false)
    private String state;
}
