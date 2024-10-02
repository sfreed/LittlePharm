package com.srfreedconsulting.LittlePharm.services;

import static org.mockito.ArgumentMatchers.any;
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
import com.srfreedconsulting.LittlePharm.data.repos.ManufacturerRepo;
import com.srfreedconsulting.LittlePharm.exceptions.ItemNotFoundException;
import com.srfreedconsulting.LittlePharm.utils.TestUtils;

import lombok.SneakyThrows;

@ExtendWith(MockitoExtension.class)
class ManufacturerServiceImplTest {
	@Mock
	private ManufacturerRepo repo;
	
	@InjectMocks
	private ManufacturerServiceImpl service;

	@Test
	public void testGetAll() {
		//given		
		List<Manufacturer> list = TestUtils.getManufacturers();

		//when
		when(repo.findAll()).thenReturn(list);
				
		//then		
		List<Manufacturer> result = this.service.getAll();
		
		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals(list,result);
	}

	@Test()
	public void testGetById() {
		//given		
		Manufacturer item = TestUtils.getManufacturers().get(0);
		
		//when
		when(repo.findById(any())).thenReturn(Optional.of(item));
		
		//then
		Optional<Manufacturer> result = this.service.getById(UUID.randomUUID());
		
		Assertions.assertTrue(result.isPresent());
		Assertions.assertNotNull(this.service.getById(UUID.randomUUID()));
	}
	
	@Test
	@SneakyThrows
	public void testSave() {
		//given		
		Manufacturer item = TestUtils.getManufacturers().get(0);
		
		//when
		when(repo.save(any())).thenReturn(item);
		
		//then
		Manufacturer result = this.service.save(item);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(item,result);
	}
	
	@Test
	@SneakyThrows
	public void testUpdate() {
		//given		
		Manufacturer item = TestUtils.getManufacturers().get(0);
		
		//when
		when(repo.existsById(any())).thenReturn(true);
		when(repo.save(any())).thenReturn(item);
		
		//then
		Manufacturer result = this.service.update(item);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(item,result);
	}
	
	@Test
	@SneakyThrows
	public void testUpdateNotFound() {
		//given		
		Manufacturer item = TestUtils.getManufacturers().get(0);
		
		//when
	    Exception exception = Assertions.assertThrows(ItemNotFoundException.class, () -> {
	    	this.service.update(item);
	    });
	    
		//then
	    String expectedMessage = "Manufacturer Not Found";
	    String actualMessage = exception.getMessage();

	    Assertions.assertTrue(actualMessage.contains(expectedMessage));		
	}
	
	@Test
	@SneakyThrows
	public void testDeleteNotFound() {
		//given		
		Manufacturer item = TestUtils.getManufacturers().get(0);
		
		//when
	    Exception exception = Assertions.assertThrows(ItemNotFoundException.class, () -> {
	    	this.service.delete(item.getUid());
	    });
	    
		//then
	    String expectedMessage = "Manufacturer Not Found";
	    String actualMessage = exception.getMessage();

	    Assertions.assertTrue(actualMessage.contains(expectedMessage));		
	}


}
