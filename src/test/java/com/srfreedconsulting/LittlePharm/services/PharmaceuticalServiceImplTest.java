package com.srfreedconsulting.LittlePharm.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.srfreedconsulting.LittlePharm.data.models.Manufacturer;
import com.srfreedconsulting.LittlePharm.data.models.Pharmaceutical;
import com.srfreedconsulting.LittlePharm.data.repos.ManufacturerRepo;
import com.srfreedconsulting.LittlePharm.data.repos.PharmaceuticalRepo;
import com.srfreedconsulting.LittlePharm.exceptions.ItemNotFoundException;
import com.srfreedconsulting.LittlePharm.utils.TestUtils;
import com.srfreedconsulting.LittlePharm.web.dtos.PharmaceuticalDto;

import lombok.SneakyThrows;

@ExtendWith(MockitoExtension.class)
class PharmaceuticalServiceImplTest {
	@Mock
	private PharmaceuticalRepo repo;
	
	@Mock
	private ManufacturerRepo manRepo;

	private PharmaceuticalTransformer transformer = new PharmaceuticalTransformerImpl();
	
	@InjectMocks
	private PharmaceuticalServiceImpl service;

	@Test
	public void testGetAll() {
		//given		
		List<Pharmaceutical> list = TestUtils.getPharmaceuticals();

		//when
		when(repo.findAll()).thenReturn(list);
				
		//then		
		List<PharmaceuticalDto> result = this.service.getAll();
		
		Assertions.assertEquals(2, result.size());
	}

	@Test
	public void testGetById() {
		//given		
		Pharmaceutical item = TestUtils.getPharmaceuticals().get(0);
		
		//when
		when(repo.findById(any())).thenReturn(Optional.of(item));
		
		//then
		Optional<PharmaceuticalDto> result = this.service.getById(UUID.randomUUID());
		
		Assertions.assertTrue(result.isPresent());
		Assertions.assertNotNull(this.service.getById(UUID.randomUUID()));
	}

	@SneakyThrows
	@Test
	public void testGetAllByManufacturer() {
		//given		
		List<Pharmaceutical> list = TestUtils.getPharmaceuticals();
		
		//when
		when(repo.getAllByManufacturerUid(any())).thenReturn(Optional.of(list));
		
		//then
		Optional<List<PharmaceuticalDto>> result = this.service.getAllByManufacturer(UUID.randomUUID());
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(list.size(),result.get().size());
	}
	
	@SneakyThrows
	@Test
	public void testSavePharmaceuticalDto() {
		//given		
		Pharmaceutical item = TestUtils.getPharmaceuticals().get(0);
		
		//when
		when(repo.save(any())).thenReturn(item);
		when(manRepo.existsById(any())).thenReturn(true);
		
		//then
		PharmaceuticalDto result = this.service.save(this.transformer.toDTO(item));
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(item.getUid().toString(),result.getUid());
	}
	
	@SneakyThrows
	@Test
	public void testSavePharmaceuticalDtoNoManufacturer() {
		//given		
		Pharmaceutical item = TestUtils.getPharmaceuticals().get(0);
		
		//when
	    Exception exception = Assertions.assertThrows(ItemNotFoundException.class, () -> {
	    	this.service.update(this.transformer.toDTO(item));
	    });
		
	    String expectedMessage = "Manufacturer Not Found";
	    String actualMessage = exception.getMessage();

	    //then
	    Assertions.assertTrue(actualMessage.contains(expectedMessage));	
	}
	
	@Test
	public void testSavePharmaceuticalDtoList() {
		//given		
		List<Pharmaceutical> items = TestUtils.getPharmaceuticals();
		
		//when
		when(repo.saveAll(anyList())).thenReturn(items);
		
		//then
		List<PharmaceuticalDto> result = this.service.save(this.transformer.listToDTO(items));
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(items.size(), result.size());
	}

	@Test
	@SneakyThrows
	public void testUpdate() {
		//given		
		Pharmaceutical item = TestUtils.getPharmaceuticals().get(0);
		
		//when
		when(repo.existsById(any())).thenReturn(true);
		when(repo.save(any())).thenReturn(item);
		
		//then
		PharmaceuticalDto result = this.service.update(this.transformer.toDTO(item));
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(item.getUid().toString(), result.getUid());
	}
}
