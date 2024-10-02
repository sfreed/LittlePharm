package com.srfreedconsulting.LittlePharm.web.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.srfreedconsulting.LittlePharm.data.models.Manufacturer;
import com.srfreedconsulting.LittlePharm.services.ManufacturerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/manufacturers/")
public class ManufacturerController {
	private final ManufacturerService service;
	
	private ObjectMapper mapper = new ObjectMapper();
	
    @GetMapping
    public ResponseEntity<List<Manufacturer>> getAll(){
        return new ResponseEntity<List<Manufacturer>>(service.getAll(), HttpStatus.OK);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Manufacturer> getById(@PathVariable String id){
    	Optional<Manufacturer> retval = service.getById(UUID.fromString(id));
    	
    	if(retval.isPresent()) {
    		return new ResponseEntity<Manufacturer>(retval.get(), HttpStatus.OK);
    	} else {
    		return new ResponseEntity<Manufacturer>(HttpStatus.NOT_FOUND);
    	}        
    }
    
    @PostMapping
    public ResponseEntity<Manufacturer> create(@Valid @RequestBody Manufacturer item){
        return new ResponseEntity<Manufacturer>(service.save(item), HttpStatus.CREATED);
    }
    
    @PostMapping("/yaml")
    public ResponseEntity<Manufacturer> uploadYaml(@RequestBody String yamlData) {
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(yamlData);
            
            String jacksonData = mapper.writeValueAsString(data);
            
            
            Manufacturer man = mapper.readValue(jacksonData, Manufacturer.class);
            
            return new ResponseEntity<Manufacturer>(service.save(man), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Manufacturer>(HttpStatus.CREATED);
        }
    }
    
    @PutMapping("{id}")
    public ResponseEntity<Manufacturer> save(@Valid @RequestBody Manufacturer item, @PathVariable String id) throws Exception{
    	if(UUID.fromString(id).equals(item.getUid())) {
    		return new ResponseEntity<Manufacturer>(service.update(item), HttpStatus.ACCEPTED);
    	}
    	
    	return new ResponseEntity<Manufacturer>(HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Manufacturer> delete(@PathVariable UUID id) throws Exception{
    	try {
    		service.delete(id);
    		
    		return new ResponseEntity<Manufacturer>(HttpStatus.ACCEPTED);
    	} catch (Exception e) {
    		return new ResponseEntity<Manufacturer>(HttpStatus.NOT_FOUND);
    	}
    }
}
