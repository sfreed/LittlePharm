package com.srfreedconsulting.LittlePharm.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.srfreedconsulting.LittlePharm.exceptions.ItemNotFoundException;
import com.srfreedconsulting.LittlePharm.web.dtos.PharmaceuticalDto;

public interface PharmaceuticalService {
	List<PharmaceuticalDto> getAll();
	Optional<List<PharmaceuticalDto>> getAllByManufacturer(UUID id);
	Optional<PharmaceuticalDto> getById(UUID id);
	List<PharmaceuticalDto> save(List<PharmaceuticalDto> item);
	PharmaceuticalDto save(PharmaceuticalDto item) throws ItemNotFoundException;
	PharmaceuticalDto update(PharmaceuticalDto item) throws ItemNotFoundException;
	boolean delete(UUID id) throws ItemNotFoundException;
	
}
