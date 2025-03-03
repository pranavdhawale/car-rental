package com.car_rental.rental_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID carId;

    @Column(nullable = false)
    private double dailyPrice;

    @Column(nullable = false)
    private double totalPrice;

    @Column(nullable = false)
    private int rentedForDays;

    @Column(nullable = false)
    private String rentedAt;
}
