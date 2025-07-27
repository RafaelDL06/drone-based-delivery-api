package com.rdele.drone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rdele.drone.dto.DroneDTO;
import com.rdele.drone.dto.MedicationDTO;
import com.rdele.drone.service.DroneService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/drones")
@RequiredArgsConstructor
public class DroneController {
	
	private final DroneService droneService;
	
	@PostMapping()
	public ResponseEntity<DroneDTO> registerDrone(@Valid @RequestBody DroneDTO droneDTO){
		return ResponseEntity.status(HttpStatus.CREATED).body(droneService.registerDrone(droneDTO));
	}
	
	@PostMapping("/{serialNumber}/load")
	public List<MedicationDTO> loadDrone(@Valid @PathVariable String serialNumber, @RequestBody List<MedicationDTO> medications){
		return droneService.loadDrone(serialNumber,medications);
	}
	
	@PostMapping("/{serialNumber}/deliver")
	public String deliverMedications(@PathVariable String serialNumber) {
		return droneService.deliverMedications(serialNumber);
	}
	
	@GetMapping("/{serialNumber}")
	public DroneDTO getDroneDetail(@PathVariable String serialNumber) {
		return droneService.getDroneDetail(serialNumber);
	}
	
	@GetMapping("/{serialNumber}/medications")
	public List<MedicationDTO> getMedications(@PathVariable String serialNumber){
		return droneService.getLoadedMedications(serialNumber);
	}
	
	@GetMapping("/available")
	public List<DroneDTO> getAvailableDrones(){
		return droneService.getAvailableDrones();
	}
	
	@GetMapping("/{serialNumber}/battery")
	public int getBatteryLevel(@PathVariable String serialNumber) {
		return droneService.getBatteryLevel(serialNumber);
	}

}
