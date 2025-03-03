package com.car_rental.inventory_service.service;

import com.car_rental.inventory_service.entity.Model;
import java.util.List;
import java.util.UUID;

public interface ModelService {
    List<Model> getAllModels();
    Model getModelById(UUID id);
    Model saveModel(Model model);
    void deleteModel(UUID id);
}
