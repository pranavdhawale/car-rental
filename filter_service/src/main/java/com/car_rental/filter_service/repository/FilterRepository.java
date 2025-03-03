package com.car_rental.filter_service.repository;

import com.car_rental.filter_service.entity.Filter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FilterRepository extends JpaRepository<Filter, UUID> {
    List<Filter> findByBrandName(String brandName);
    List<Filter> findByModelName(String modelName);
}
