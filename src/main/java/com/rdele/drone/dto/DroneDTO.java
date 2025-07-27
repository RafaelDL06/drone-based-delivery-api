package com.rdele.drone.dto;

import com.rdele.drone.enums.DroneState;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneDTO {
	
	@NotBlank(message = "Serial number is required")
	private String serialNumber;
	
	@NotNull(message = "Model is required")
	private String model;
	
	@Min(0)
	@Max(100)
	@NotNull(message = "Battery capacity is required")
	private int batteryCapacity;
	
	private DroneState droneState;

}
