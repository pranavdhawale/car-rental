package com.car_rental.filter_service.service;

import com.car_rental.filter_service.dto.FilterDTO;
import java.util.List;
import java.util.UUID;

public interface FilterService {
    FilterDTO createFilter(FilterDTO filterDTO);
    FilterDTO getFilterById(UUID id);
    List<FilterDTO> getAllFilters();
    List<FilterDTO> getFiltersByBrandName(String brandName);
    List<FilterDTO> getFiltersByModelName(String modelName);
    void deleteFilter(UUID id);
}
