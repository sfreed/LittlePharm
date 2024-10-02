package com.srfreedconsulting.LittlePharm.web.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ManufacturerDto {
	String uid;		
	String name;
}
