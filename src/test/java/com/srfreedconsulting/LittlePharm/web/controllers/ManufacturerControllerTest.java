package com.srfreedconsulting.LittlePharm.web.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.srfreedconsulting.LittlePharm.data.models.Manufacturer;
import com.srfreedconsulting.LittlePharm.services.ManufacturerService;
import com.srfreedconsulting.LittlePharm.utils.TestUtils;

import lombok.SneakyThrows;

@WebMvcTest(ManufacturerController.class)
class ManufacturerControllerTest {
	@MockBean
	private ManufacturerService service;
	
	@Autowired
	private MockMvc mockMvc;

	ObjectMapper mapper = new ObjectMapper();
	
	@Test
	@SneakyThrows
	public void testGetAll() {
		//given
		List<Manufacturer> manufacturers = TestUtils.getManufacturers();
		
		//when
        when(service.getAll()).thenReturn(manufacturers);

        // Act and Assert
        mockMvc.perform(get("/v1/manufacturers/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(this.mapper.writeValueAsString(manufacturers)));		
		
	}

	@Test
	@SneakyThrows
	public void testGetById() {
		//given
		Optional<Manufacturer> manufacturer = Optional.of(TestUtils.getManufacturers().get(0));
		
		//when
        when(service.getById(any())).thenReturn(manufacturer);

        // Act and Assert
        mockMvc.perform(get("/v1/manufacturers/{id}", manufacturer.get().getUid())
        			.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(this.mapper.writeValueAsString(manufacturer.get())));	
	}
	
	@Test
	@SneakyThrows
	public void testGetByIdNotFound() {
		//given
		Optional<Manufacturer> manufacturer = Optional.of(TestUtils.getManufacturers().get(0));
		
		//when
        when(service.getById(any())).thenReturn(Optional.empty());

        // Act and Assert
        mockMvc.perform(get("/v1/manufacturers/{id}", manufacturer.get().getUid())
        			.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());	
	}

	@Test
	@SneakyThrows
	public void testCreateNew() {
		//given
		Manufacturer manufacturer = TestUtils.getManufacturers().get(0);
		manufacturer.setUid(null);
		
		//when
        when(service.save(any())).thenReturn(manufacturer);

        // Act and Assert
        mockMvc.perform(post("/v1/manufacturers/")
        			.content(mapper.writeValueAsString(manufacturer))
        			.contentType(MediaType.APPLICATION_JSON)
        			.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(this.mapper.writeValueAsString(manufacturer)));	
	}
	
	@Test
	@SneakyThrows
	public void testCreateNewBadData() {
		//given
		Manufacturer manufacturer = TestUtils.getManufacturers().get(0);
		manufacturer.setUid(null);
		manufacturer.setName(null);
		
		//when
        when(service.save(any())).thenReturn(manufacturer);

        // Act and Assert
        mockMvc.perform(post("/v1/manufacturers/")
        			.content(mapper.writeValueAsString(manufacturer))
        			.contentType(MediaType.APPLICATION_JSON)
        			.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());	
	}	

	@Test
	@SneakyThrows
	public void testSave() {
		//given
		Manufacturer manufacturer = TestUtils.getManufacturers().get(0);
		
		//when
        when(service.update(any())).thenReturn(manufacturer);

        // Act and Assert
        mockMvc.perform(put("/v1/manufacturers/{id}", manufacturer.getUid())
        			.content(mapper.writeValueAsString(manufacturer))
        			.contentType(MediaType.APPLICATION_JSON)
        			.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().json(this.mapper.writeValueAsString(manufacturer)));	
	}
	
	@Test
	@SneakyThrows
	public void testSaveBadRequest() {
		//given
		Manufacturer manufacturer = TestUtils.getManufacturers().get(0);
		
		//when
        when(service.update(any())).thenReturn(manufacturer);

        // Act and Assert
        mockMvc.perform(put("/v1/manufacturers/{id}", UUID.randomUUID().toString())
        			.content(mapper.writeValueAsString(manufacturer))
        			.contentType(MediaType.APPLICATION_JSON)
        			.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());	
	}

	@Test
	@SneakyThrows
	public void testDelete() {
		//given
		Manufacturer manufacturer = TestUtils.getManufacturers().get(0);

        // Act and Assert
        mockMvc.perform(delete("/v1/manufacturers/{id}", manufacturer.getUid()))
                .andExpect(status().isAccepted());	
	}
	
	@Test
	@SneakyThrows
	public void testDeleteException() {
		//given
		Manufacturer manufacturer = TestUtils.getManufacturers().get(0);
		
		//when
		when(service.delete(any())).thenThrow(new Exception("Manufacturer Not Found"));

        // Act and Assert
        mockMvc.perform(delete("/v1/manufacturers/{id}", manufacturer.getUid()))
                .andExpect(status().isNotFound());	
	}

}
