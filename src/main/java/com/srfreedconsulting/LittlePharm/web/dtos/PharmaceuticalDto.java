package com.srfreedconsulting.LittlePharm.web.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PharmaceuticalDto {
	String uid;	
	String manufacturerId ;
	String name;
	Integer quantity;	
	BigDecimal price;
}
