package com.car_rental.maintenance_service.service.impl;

import com.car_rental.maintenance_service.dto.MaintenanceDTO;
import com.car_rental.maintenance_service.entity.Maintenance;
import com.car_rental.maintenance_service.exception.ResourceNotFoundException;
import com.car_rental.maintenance_service.repository.MaintenanceRepository;
import com.car_rental.maintenance_service.service.MaintenanceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MaintenanceDTO createMaintenance(MaintenanceDTO maintenanceDTO) {
        Maintenance maintenance = modelMapper.map(maintenanceDTO, Maintenance.class);
        maintenance = maintenanceRepository.save(maintenance);
        return modelMapper.map(maintenance, MaintenanceDTO.class);
    }

    @Override
    public MaintenanceDTO getMaintenanceById(UUID id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance record not found with ID: " + id));
        return modelMapper.map(maintenance, MaintenanceDTO.class);
    }

    @Override
    public List<MaintenanceDTO> getAllMaintenanceRecords() {
        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        return maintenanceList.stream()
                .map(maintenance -> modelMapper.map(maintenance, MaintenanceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MaintenanceDTO updateMaintenance(UUID id, MaintenanceDTO maintenanceDTO) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance record not found with ID: " + id));

        maintenance.setInformation(maintenanceDTO.getInformation());
        maintenance.setCompleted(maintenanceDTO.isCompleted());
        maintenance.setStartDate(maintenanceDTO.getStartDate());
        maintenance.setEndDate(maintenanceDTO.getEndDate());
        maintenance.setCarId(maintenanceDTO.getCarId());

        maintenance = maintenanceRepository.save(maintenance);
        return modelMapper.map(maintenance, MaintenanceDTO.class);
    }

    @Override
    public void deleteMaintenance(UUID id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance record not found with ID: " + id));
        maintenanceRepository.delete(maintenance);
    }
}
