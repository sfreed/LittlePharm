package com.srfreedconsulting.LittlePharm.web.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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
import com.srfreedconsulting.LittlePharm.services.PharmaceuticalService;
import com.srfreedconsulting.LittlePharm.services.PharmaceuticalTransformer;
import com.srfreedconsulting.LittlePharm.utils.TestUtils;
import com.srfreedconsulting.LittlePharm.web.dtos.PharmaceuticalDto;

import lombok.SneakyThrows;

@WebMvcTest(PharmaceuticalController.class)
class PharmaceuticalControllerTest {
	@MockBean
	private PharmaceuticalService service;
	
	@MockBean
	PharmaceuticalTransformer transformer;
	
	@Autowired
	private MockMvc mockMvc;
	
	ObjectMapper mapper = new ObjectMapper();

	@Test
	@SneakyThrows
	public void testGetAll() {
		//given
		List<PharmaceuticalDto> pharmaceutical = TestUtils.getPharmaceuticalDtos();
		
		//when
        when(service.getAll()).thenReturn(pharmaceutical);

        // Act and Assert
        mockMvc.perform(get("/v1/pharmaceuticals/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(this.mapper.writeValueAsString(pharmaceutical)));				
	}

	@Test
	@SneakyThrows
	public void getByManufacturerId() {
		//given
		Optional<List<PharmaceuticalDto>> pharmaceutical = Optional.of(TestUtils.getPharmaceuticalDtos());
		
		//when
        when(service.getAllByManufacturer(any())).thenReturn(pharmaceutical);

        // Act and Assert
        mockMvc.perform(get("/v1/pharmaceuticals/manufacturer/{id}", pharmaceutical.get().get(0).getManufacturerId())
        			.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(this.mapper.writeValueAsString(pharmaceutical.get())));			
	}
	
	@Test
	@SneakyThrows
	public void getByManufacturerIdNotFound() {
		//given
		Optional<List<PharmaceuticalDto>> pharmaceutical = Optional.of(TestUtils.getPharmaceuticalDtos());
		
		//when
        when(service.getAllByManufacturer(any())).thenReturn(Optional.empty());

        // Act and Assert
        mockMvc.perform(get("/v1/pharmaceuticals/manufacturer/{id}", pharmaceutical.get().get(0).getManufacturerId())
        			.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());			
	}

	@Test
	@SneakyThrows
	public void testGetById() {
		//given
		Optional<PharmaceuticalDto> pharmaceutical = Optional.of(TestUtils.getPharmaceuticalDtos().get(0));
		
		//when
        when(service.getById(any())).thenReturn(pharmaceutical);

        // Act and Assert
        mockMvc.perform(get("/v1/pharmaceuticals/{id}", pharmaceutical.get().getUid())
        			.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(this.mapper.writeValueAsString(pharmaceutical.get())));			
	}
	
	@Test
	@SneakyThrows
	public void testGetByIdNotFound() {
		//given
		Optional<PharmaceuticalDto> pharmaceutical = Optional.of(TestUtils.getPharmaceuticalDtos().get(0));
		
		//when
        when(service.getById(any())).thenReturn(Optional.empty());

        // Act and Assert
        mockMvc.perform(get("/v1/pharmaceuticals/{id}", pharmaceutical.get().getUid())
        			.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());			
	}

	@Test
	@SneakyThrows
	public void testCreateNew() {
		//given
		PharmaceuticalDto pharmaceutical = TestUtils.getPharmaceuticalDtos().get(0);
		pharmaceutical.setUid(null);
		
		//when
        when(service.save(pharmaceutical)).thenReturn(pharmaceutical);

        // Act and Assert
        mockMvc.perform(post("/v1/pharmaceuticals/")
        			.content(mapper.writeValueAsString(pharmaceutical))
        			.contentType(MediaType.APPLICATION_JSON)
        			.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(this.mapper.writeValueAsString(pharmaceutical)));		
	}
	
	@Test
	@SneakyThrows
	public void testCreateNewBadData() {
		//given
		List<PharmaceuticalDto> pharmaceutical = TestUtils.getPharmaceuticalDtos();
		
		pharmaceutical.forEach(p -> {
			p.setUid(null);
			p.setName(null);
		});
		
		//when
        when(service.save(anyList())).thenReturn(pharmaceutical);

        // Act and Assert
        mockMvc.perform(post("/v1/pharmaceuticals/")
        			.content(mapper.writeValueAsString(pharmaceutical))
        			.contentType(MediaType.APPLICATION_JSON)
        			.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());		
	}

	@Test
	@SneakyThrows
	public void testSave() {
		//given
		PharmaceuticalDto pharmaceutical = TestUtils.getPharmaceuticalDtos().get(0);
		
		//when
        when(service.update(any())).thenReturn(pharmaceutical);

        // Act and Assert
        mockMvc.perform(put("/v1/pharmaceuticals/{id}", pharmaceutical.getUid())
        			.content(mapper.writeValueAsString(pharmaceutical))
        			.contentType(MediaType.APPLICATION_JSON)
        			.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().json(this.mapper.writeValueAsString(pharmaceutical)));			
	}
	
	@Test
	@SneakyThrows
	public void testSaveBadRequest() {
		//given
		PharmaceuticalDto pharmaceutical = TestUtils.getPharmaceuticalDtos().get(0);
		
		//when
        when(service.update(any())).thenReturn(pharmaceutical);

        // Act and Assert
        mockMvc.perform(put("/v1/pharmaceuticals/{id}", UUID.randomUUID().toString())
        			.content(mapper.writeValueAsString(pharmaceutical))
        			.contentType(MediaType.APPLICATION_JSON)
        			.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());	
	}	

	@Test
	@SneakyThrows
	public void testDelete() {
		//given
		PharmaceuticalDto pharmaceutical = TestUtils.getPharmaceuticalDtos().get(0);

        // Act and Assert
        mockMvc.perform(delete("/v1/pharmaceuticals/{id}", pharmaceutical.getUid()))
                .andExpect(status().isAccepted());			
	}
	
	@Test
	@SneakyThrows
	public void testDeleteException() {
		//given
		PharmaceuticalDto pharmaceutical = TestUtils.getPharmaceuticalDtos().get(0);
		
		//when
		when(service.delete(any())).thenThrow(new Exception("Manufacturer Not Found"));

        // Act and Assert
        mockMvc.perform(delete("/v1/pharmaceuticals/{id}", pharmaceutical.getUid()))
                .andExpect(status().isNotFound());	
	}
}
