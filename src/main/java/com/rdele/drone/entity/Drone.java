package com.rdele.drone.entity;

import java.util.List;

import com.rdele.drone.enums.DroneModel;
import com.rdele.drone.enums.DroneState;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Drone {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private DroneModel model;

	@Column(length = 100, unique = true, nullable = false)
	private String serialNumber;

	private int weightLimit;

	private int batteryCapacity; 

	@Enumerated(EnumType.STRING)
	private DroneState state;

	@OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Medication> medications;


}


