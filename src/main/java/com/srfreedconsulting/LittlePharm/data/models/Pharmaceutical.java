package com.srfreedconsulting.LittlePharm.data.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pharmaceutical {	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uid;
	
	@ManyToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	Manufacturer manufacturer ;
	
	@NotBlank
	String name;
	
	@Min(value = 0, message = "The value must be greater than 0")
	Integer quantity;
	
	@DecimalMin(value = "0.1", inclusive = false)
	BigDecimal price;
	
	@CreationTimestamp
	Date createdate;
	
	@UpdateTimestamp
	Date updatedDate;
}
