package com.srfreedconsulting.LittlePharm.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.srfreedconsulting.LittlePharm.data.models.Manufacturer;

public interface ManufacturerService {
	List<Manufacturer> getAll();
	Optional<Manufacturer> getById(UUID id);
	Manufacturer save(Manufacturer item);
	Manufacturer update(Manufacturer item) throws Exception;
	boolean delete(UUID id) throws Exception;
}
