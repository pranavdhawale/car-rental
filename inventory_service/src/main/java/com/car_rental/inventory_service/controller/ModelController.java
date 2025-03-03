package com.car_rental.inventory_service.controller;

import com.car_rental.inventory_service.entity.Model;
import com.car_rental.inventory_service.service.ModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/models")
public class ModelController {
    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("get-all")
    public ResponseEntity<List<Model>> getAllModels() {
        return ResponseEntity.ok(modelService.getAllModels());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Model> getModelById(@PathVariable UUID id) {
        Model model = modelService.getModelById(id);
        return ResponseEntity.ok(model);
    }

    @PostMapping("create")
    public ResponseEntity<Model> createModel(@RequestBody Model model) {
        return ResponseEntity.ok(modelService.saveModel(model));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteModel(@PathVariable UUID id) {
        modelService.deleteModel(id);
        return ResponseEntity.ok("Model deleted successfully");
    }
}
