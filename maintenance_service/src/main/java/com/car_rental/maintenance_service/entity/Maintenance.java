package com.car_rental.maintenance_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "maintenances")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String information;

    private boolean isCompleted;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Column(nullable = false)
    private UUID carId;
}
