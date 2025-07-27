package com.rdele.drone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rdele.drone.entity.Medication;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long>{
	Optional<Medication> findByName(String name);
	List<Medication> findByDroneSerialNumber(String serialNumber);
}
