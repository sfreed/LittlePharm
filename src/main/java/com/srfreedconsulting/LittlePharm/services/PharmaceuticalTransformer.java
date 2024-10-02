package com.srfreedconsulting.LittlePharm.services;

import java.util.List;

import com.srfreedconsulting.LittlePharm.data.models.Pharmaceutical;
import com.srfreedconsulting.LittlePharm.web.dtos.PharmaceuticalDto;

public interface PharmaceuticalTransformer {

	PharmaceuticalDto toDTO(Pharmaceutical item);

	Pharmaceutical toModel(PharmaceuticalDto item);

	List<Pharmaceutical> listToModel(List<PharmaceuticalDto> items);

	List<PharmaceuticalDto> listToDTO(List<Pharmaceutical> items);

}