package com.srfreedconsulting.LittlePharm.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.srfreedconsulting.LittlePharm.data.models.Pharmaceutical;
import com.srfreedconsulting.LittlePharm.data.repos.ManufacturerRepo;
import com.srfreedconsulting.LittlePharm.data.repos.PharmaceuticalRepo;
import com.srfreedconsulting.LittlePharm.exceptions.ItemNotFoundException;
import com.srfreedconsulting.LittlePharm.web.dtos.PharmaceuticalDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PharmaceuticalServiceImpl implements PharmaceuticalService {

	private final PharmaceuticalRepo pharmRepo;
	private final ManufacturerRepo manRepo;
	
	private PharmaceuticalTransformer transformer = new PharmaceuticalTransformerImpl();
		
	@Override
	public List<PharmaceuticalDto> getAll() {
		return this.transformer.listToDTO(this.pharmRepo.findAll());		
	}

	@Override
	public Optional<PharmaceuticalDto> getById(UUID id) {
		Optional<Pharmaceutical> retval = this.pharmRepo.findById(id);
		
		if(retval.isPresent()) {
			return Optional.of(transformer.toDTO(retval.get()));
		} else {
			return Optional.ofNullable(null);
		}
	}
	
	@Override
	public Optional<List<PharmaceuticalDto>> getAllByManufacturer(UUID id) {
		Optional<List<Pharmaceutical>> list = this.pharmRepo.getAllByManufacturerUid(id);
		
		if(list.isPresent()) {
			return Optional.of(transformer.listToDTO(list.get()));
		} else {
			return Optional.ofNullable(null);
		}
	}

	@Override
	public List<PharmaceuticalDto> save(List<PharmaceuticalDto> items) {
		List<Pharmaceutical> list = this.transformer.listToModel(items);
		
		list = this.pharmRepo.saveAll(list);
		
		return this.transformer.listToDTO(list);
	}
	
	@Override
	public PharmaceuticalDto save(PharmaceuticalDto item) throws ItemNotFoundException {		
		if(manRepo.existsById(UUID.fromString(item.getUid()))) {
			Pharmaceutical retval = this.pharmRepo.save(transformer.toModel(item));			
			
			return transformer.toDTO(retval);			
		}
		
		throw new ItemNotFoundException("Manufacturer Not Found");

	}

	@Override
	public PharmaceuticalDto update(PharmaceuticalDto item) throws ItemNotFoundException {
		if(this.pharmRepo.existsById(UUID.fromString(item.getUid()))) {
			Pharmaceutical model = this.pharmRepo.save(transformer.toModel(item));
			
			return transformer.toDTO(model);
		};
		
		throw new ItemNotFoundException("Manufacturer Not Found");
	}

	@Override
	public boolean delete(UUID id) throws ItemNotFoundException {
		if(this.pharmRepo.existsById(id)) {
			this.pharmRepo.deleteById(id);
			
			return true;
		};
		
		throw new ItemNotFoundException("Manufacturer Not Found");
	}
	

}
