package com.srfreedconsulting.LittlePharm.web.controllers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.srfreedconsulting.LittlePharm.data.models.Pharmaceutical;
import com.srfreedconsulting.LittlePharm.services.PharmaceuticalService;
import com.srfreedconsulting.LittlePharm.services.PharmaceuticalTransformer;
import com.srfreedconsulting.LittlePharm.web.dtos.PharmaceuticalDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/pharmaceuticals/")
public class PharmaceuticalController {
	private final PharmaceuticalService service;
	
	private final PharmaceuticalTransformer transformer;
	
	private ObjectMapper mapper = new ObjectMapper();
	
    @GetMapping
    public ResponseEntity<List<PharmaceuticalDto>> getAll(){
        return new ResponseEntity<List<PharmaceuticalDto>>(service.getAll(), HttpStatus.OK);
    }
    
    @GetMapping("/manufacturer/{id}")
    public ResponseEntity<List<PharmaceuticalDto>> getByManufacturerId(@PathVariable String id){
    	Optional<List<PharmaceuticalDto>> retval = service.getAllByManufacturer(UUID.fromString(id));
    	
    	if(retval.isPresent()) {
    		return new ResponseEntity<List<PharmaceuticalDto>>(retval.get(), HttpStatus.OK);
    	} else {
    		return new ResponseEntity<List<PharmaceuticalDto>>(HttpStatus.NOT_FOUND);
    	}        
    }

    @GetMapping("{id}")
    public ResponseEntity<PharmaceuticalDto> getById(@PathVariable String id){
    	Optional<PharmaceuticalDto> retval = service.getById(UUID.fromString(id));
    	
    	if(retval.isPresent()) {
    		return new ResponseEntity<PharmaceuticalDto>(retval.get(), HttpStatus.OK);
    	} else {
    		return new ResponseEntity<PharmaceuticalDto>(HttpStatus.NOT_FOUND);
    	}        
    }
    
    @PostMapping
    public ResponseEntity<PharmaceuticalDto> create(@Valid @RequestBody PharmaceuticalDto item){
    	try {
    		PharmaceuticalDto retval = service.save(item);
    		
    		return new ResponseEntity<PharmaceuticalDto>(retval, HttpStatus.ACCEPTED);
    	} catch (Exception e) {
    		return new ResponseEntity<PharmaceuticalDto>(HttpStatus.NOT_FOUND);
    	}
    }
    
    @PostMapping(value="/upload/yaml", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<PharmaceuticalDto>> uploadYaml(@RequestParam("file") MultipartFile file) {
        try {
        	List<PharmaceuticalDto> list = new ArrayList<>();
        	            
        	InputStream stream = file.getInputStream();
        	
            Iterable<Object> map = new Yaml().loadAll(stream);
            
            map.forEach(item -> {
    			try {
                    Pharmaceutical pharmaceutical = mapper.readValue(mapper.writeValueAsString(item), Pharmaceutical.class);
                    
                    list.add(transformer.toDTO(pharmaceutical));
    			} catch (Exception e) {
    				e.printStackTrace();
    			}                	
            });
            
            return new ResponseEntity<List<PharmaceuticalDto>>(service.save(list), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<List<PharmaceuticalDto>>(HttpStatus.CREATED);
        }
    }
    
    @PostMapping(value="/upload/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<PharmaceuticalDto>> uploadJson(@RequestParam("file") MultipartFile file) {
        try {
        	TypeReference<List<PharmaceuticalDto>> typeReference = new TypeReference<List<PharmaceuticalDto>>(){};
        	InputStream inputStream = file.getInputStream();
        	
        	List<PharmaceuticalDto> list = mapper.readValue(inputStream,typeReference);
          	            
            return new ResponseEntity<List<PharmaceuticalDto>>(service.save(list), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<List<PharmaceuticalDto>>(HttpStatus.CREATED);
        }
    }
    
    @PutMapping("{id}")
    public ResponseEntity<PharmaceuticalDto> save(@Valid @RequestBody PharmaceuticalDto item, @PathVariable String id) throws Exception{
    	if(id.equals(item.getUid())) {
    		return new ResponseEntity<PharmaceuticalDto>(service.update(item), HttpStatus.ACCEPTED);
    	}
    	
    	return new ResponseEntity<PharmaceuticalDto>(HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Pharmaceutical> delete(@PathVariable String id) throws Exception{
    	try {
    		service.delete(UUID.fromString(id));
    		
    		return new ResponseEntity<Pharmaceutical>(HttpStatus.ACCEPTED);
    	} catch (Exception e) {
    		return new ResponseEntity<Pharmaceutical>(HttpStatus.NOT_FOUND);
    	}
    }	

}
