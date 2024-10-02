package com.srfreedconsulting.LittlePharm.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.srfreedconsulting.LittlePharm.data.models.Manufacturer;
import com.srfreedconsulting.LittlePharm.data.models.Pharmaceutical;
import com.srfreedconsulting.LittlePharm.web.dtos.PharmaceuticalDto;

public class TestUtils {
	public static List<Pharmaceutical> getPharmaceuticals(){
		Manufacturer man1 = Manufacturer.builder().uid(UUID.randomUUID()).name("Test1").build();
		Manufacturer man2 = Manufacturer.builder().uid(UUID.randomUUID()).name("Test1").build();
		
		List<Pharmaceutical> list = new ArrayList<>();	
		
		list.add(Pharmaceutical.builder().uid(UUID.randomUUID()).name("Pharmaceutical1").price(BigDecimal.TEN).quantity(10).manufacturer(man1).build());
		list.add(Pharmaceutical.builder().uid(UUID.randomUUID()).name("Pharmaceutical2").price(BigDecimal.TEN).quantity(100).manufacturer(man2).build());
		
		return list;
	}
	
	public static List<PharmaceuticalDto> getPharmaceuticalDtos(){
		List<PharmaceuticalDto> list = new ArrayList<>();	
		list.add(PharmaceuticalDto.builder().uid(UUID.randomUUID().toString()).name("Pharmaceutical1").price(BigDecimal.TEN).quantity(10).manufacturerId(UUID.randomUUID().toString()).build());
		list.add(PharmaceuticalDto.builder().uid(UUID.randomUUID().toString()).name("Pharmaceutical2").price(BigDecimal.TEN).quantity(100).manufacturerId(UUID.randomUUID().toString()).build());
		
		return list;
	}
	
	public static List<Manufacturer> getManufacturers(){
		List<Manufacturer> list = new ArrayList<>();	
		list.add(Manufacturer.builder().uid(UUID.randomUUID()).name("Test1").build());
		list.add(Manufacturer.builder().uid(UUID.randomUUID()).name("Test2").build());
		
		return list;
	}
}
