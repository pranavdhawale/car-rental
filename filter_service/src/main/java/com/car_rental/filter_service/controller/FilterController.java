package com.car_rental.filter_service.controller;

import com.car_rental.filter_service.dto.FilterDTO;
import com.car_rental.filter_service.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/filters")
public class FilterController {

    @Autowired
    private FilterService filterService;

    @PostMapping("create")
    public ResponseEntity<FilterDTO> createFilter(@RequestBody FilterDTO filterDTO) {
        return ResponseEntity.ok(filterService.createFilter(filterDTO));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<FilterDTO> getFilterById(@PathVariable UUID id) {
        return ResponseEntity.ok(filterService.getFilterById(id));
    }

    @GetMapping("get-all")
    public ResponseEntity<List<FilterDTO>> getAllFilters() {
        return ResponseEntity.ok(filterService.getAllFilters());
    }

    @GetMapping("/brand/{brandName}")
    public ResponseEntity<List<FilterDTO>> getFiltersByBrand(@PathVariable String brandName) {
        return ResponseEntity.ok(filterService.getFiltersByBrandName(brandName));
    }

    @GetMapping("/model/{modelName}")
    public ResponseEntity<List<FilterDTO>> getFiltersByModel(@PathVariable String modelName) {
        return ResponseEntity.ok(filterService.getFiltersByModelName(modelName));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteFilter(@PathVariable UUID id) {
        filterService.deleteFilter(id);
        return ResponseEntity.ok("Filter deleted successfully");
    }
}
