package com.srfreedconsulting.LittlePharm.data.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Manufacturer {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID uid;
	
	@NotBlank
	String name;
	
	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "manufacturer")
	List<Pharmaceutical> pharmaceuticals;
	
	@CreationTimestamp
	Date createdate;
	
	@UpdateTimestamp
	Date updatedDate;
}
