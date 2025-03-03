package com.car_rental.filter_service.service.impl;

import com.car_rental.filter_service.dto.FilterDTO;
import com.car_rental.filter_service.entity.Filter;
import com.car_rental.filter_service.exception.ResourceNotFoundException;
import com.car_rental.filter_service.repository.FilterRepository;
import com.car_rental.filter_service.service.FilterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FilterServiceImpl implements FilterService {

    @Autowired
    private FilterRepository filterRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FilterDTO createFilter(FilterDTO filterDTO) {
        Filter filter = modelMapper.map(filterDTO, Filter.class);
        Filter savedFilter = filterRepository.save(filter);
        return modelMapper.map(savedFilter, FilterDTO.class);
    }

    @Override
    public FilterDTO getFilterById(UUID id) {
        Filter filter = filterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filter not found with id: " + id));
        return modelMapper.map(filter, FilterDTO.class);
    }

    @Override
    public List<FilterDTO> getAllFilters() {
        return filterRepository.findAll().stream()
                .map(filter -> modelMapper.map(filter, FilterDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FilterDTO> getFiltersByBrandName(String brandName) {
        return filterRepository.findByBrandName(brandName).stream()
                .map(filter -> modelMapper.map(filter, FilterDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FilterDTO> getFiltersByModelName(String modelName) {
        return filterRepository.findByModelName(modelName).stream()
                .map(filter -> modelMapper.map(filter, FilterDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFilter(UUID id) {
        if (!filterRepository.existsById(id)) {
            throw new ResourceNotFoundException("Filter not found with id: " + id);
        }
        filterRepository.deleteById(id);
    }
}
