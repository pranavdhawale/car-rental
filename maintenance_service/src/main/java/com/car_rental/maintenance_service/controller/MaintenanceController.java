package com.car_rental.maintenance_service.controller;

import com.car_rental.maintenance_service.dto.MaintenanceDTO;
import com.car_rental.maintenance_service.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    @PostMapping("create")
    public ResponseEntity<MaintenanceDTO> createMaintenance(@RequestBody MaintenanceDTO maintenanceDTO) {
        return ResponseEntity.ok(maintenanceService.createMaintenance(maintenanceDTO));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<MaintenanceDTO> getMaintenanceById(@PathVariable UUID id) {
        return ResponseEntity.ok(maintenanceService.getMaintenanceById(id));
    }

    @GetMapping("get-all")
    public ResponseEntity<List<MaintenanceDTO>> getAllMaintenanceRecords() {
        return ResponseEntity.ok(maintenanceService.getAllMaintenanceRecords());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<MaintenanceDTO> updateMaintenance(@PathVariable UUID id, @RequestBody MaintenanceDTO maintenanceDTO) {
        return ResponseEntity.ok(maintenanceService.updateMaintenance(id, maintenanceDTO));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteMaintenance(@PathVariable UUID id) {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.ok("Maintenance record deleted successfully.");
    }
}
