package com.srfreedconsulting.LittlePharm.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.srfreedconsulting.LittlePharm.data.models.Manufacturer;
import com.srfreedconsulting.LittlePharm.data.repos.ManufacturerRepo;
import com.srfreedconsulting.LittlePharm.exceptions.ItemNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ManufacturerServiceImpl implements ManufacturerService {

	private final ManufacturerRepo repo;
	
	@Override
	public List<Manufacturer> getAll() {
		return this.repo.findAll();
	}

	@Override
	public Optional<Manufacturer> getById(UUID id) {
		return this.repo.findById(id);
	}

	@Override
	public Manufacturer save(Manufacturer item) {
		return this.repo.save(item);
	}

	@Override
	public Manufacturer update(Manufacturer item) throws ItemNotFoundException {
		if(this.repo.existsById(item.getUid())) {
			return this.repo.save(item);
		};
		
		throw new ItemNotFoundException("Manufacturer Not Found");
	}

	@Override
	public boolean delete(UUID id) throws ItemNotFoundException {
		if(this.repo.existsById(id)) {
			this.repo.deleteById(id);
			
			return true;
		};
		
		throw new ItemNotFoundException("Manufacturer Not Found");
	}
}
