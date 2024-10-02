package com.srfreedconsulting.LittlePharm.data.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srfreedconsulting.LittlePharm.data.models.Manufacturer;

@Repository
public interface ManufacturerRepo extends JpaRepository<Manufacturer, UUID>{

}
