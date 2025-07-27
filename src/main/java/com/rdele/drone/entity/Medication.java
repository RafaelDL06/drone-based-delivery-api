package com.rdele.drone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Medication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    private String name;

    private int weight;

    private String code;

    private String image;
	
	@ManyToOne
	@JoinColumn(name = "drone_id")
	private Drone drone;
	
}
