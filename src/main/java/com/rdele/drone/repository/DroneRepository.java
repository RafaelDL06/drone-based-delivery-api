package com.rdele.drone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rdele.drone.entity.Drone;
import com.rdele.drone.enums.DroneState;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long>{
	Optional<Drone> findBySerialNumber(String serialNumber);
	List<Drone> findByStateInAndBatteryCapacityGreaterThan(List<DroneState> states, int battery);
	boolean existsBySerialNumber(String serialNumber);


}
