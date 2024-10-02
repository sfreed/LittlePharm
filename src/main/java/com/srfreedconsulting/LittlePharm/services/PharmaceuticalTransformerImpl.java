package com.srfreedconsulting.LittlePharm.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.srfreedconsulting.LittlePharm.data.models.Manufacturer;
import com.srfreedconsulting.LittlePharm.data.models.Pharmaceutical;
import com.srfreedconsulting.LittlePharm.web.dtos.PharmaceuticalDto;

@Service
public class PharmaceuticalTransformerImpl implements PharmaceuticalTransformer {
	@Override
	public PharmaceuticalDto toDTO(Pharmaceutical item) {
		return  PharmaceuticalDto.builder()
				.uid(item.getUid().toString())
				.manufacturerId(item.getManufacturer().getUid().toString())
				.name(item.getName())
				.price(item.getPrice())
				.quantity(item.getQuantity())
				.build();		
	}
	
	@Override
	public List<PharmaceuticalDto> listToDTO(List<Pharmaceutical> items) {
		List<PharmaceuticalDto> retval = new ArrayList<>();
		
		items.forEach(item -> {
			retval.add(this.toDTO(item));	
		});
		
		return retval;
	}
	
	@Override
	public Pharmaceutical toModel(PharmaceuticalDto item) {
		return Pharmaceutical.builder()
				.uid(UUID.fromString(item.getUid()))
				.manufacturer(Manufacturer.builder().uid(UUID.fromString(item.getManufacturerId())).build())
				.name(item.getName())
				.price(item.getPrice())
				.quantity(item.getQuantity())
				.build();	
	}
	
	@Override
	public List<Pharmaceutical> listToModel(List<PharmaceuticalDto> items) {
		List<Pharmaceutical> retval = new ArrayList<>();
		
		items.forEach(item -> {
			retval.add(this.toModel(item));			
		});
		
		return retval;
	}
	
	
}
