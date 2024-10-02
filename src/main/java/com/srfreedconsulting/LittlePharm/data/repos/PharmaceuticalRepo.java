package com.srfreedconsulting.LittlePharm.data.repos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srfreedconsulting.LittlePharm.data.models.Pharmaceutical;

@Repository
public interface PharmaceuticalRepo extends JpaRepository<Pharmaceutical, UUID> {

	Optional<List<Pharmaceutical>> getAllByManufacturerUid(UUID name);

}
