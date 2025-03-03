package com.car_rental.maintenance_service.service;

import com.car_rental.maintenance_service.dto.MaintenanceDTO;

import java.util.List;
import java.util.UUID;

public interface MaintenanceService {
    MaintenanceDTO createMaintenance(MaintenanceDTO maintenanceDTO);
    MaintenanceDTO getMaintenanceById(UUID id);
    List<MaintenanceDTO> getAllMaintenanceRecords();
    MaintenanceDTO updateMaintenance(UUID id, MaintenanceDTO maintenanceDTO);
    void deleteMaintenance(UUID id);
}
