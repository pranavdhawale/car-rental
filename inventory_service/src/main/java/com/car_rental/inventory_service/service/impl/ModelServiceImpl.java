package com.car_rental.inventory_service.service.impl;

import com.car_rental.inventory_service.entity.Model;
import com.car_rental.inventory_service.exception.ResourceNotFoundException;
import com.car_rental.inventory_service.repository.ModelRepository;
import com.car_rental.inventory_service.service.ModelService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;

    public ModelServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public List<Model> getAllModels() {
        return modelRepository.findAll();
    }

    @Override
    public Model getModelById(UUID id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Model not found with id: " + id));
    }

    @Override
    public Model saveModel(Model model) {
        return modelRepository.save(model);
    }

    @Override
    public void deleteModel(UUID id) {
        if (!modelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Model not found with id: " + id);
        }
        modelRepository.deleteById(id);
    }
}
