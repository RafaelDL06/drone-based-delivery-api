package com.rdele.drone.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationDTO {
	
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Name can only contain letters, numbers, hyphens, and underscores")
    @NotBlank(message = "Name is required")
	private String name;
    
    @Min(value = 1, message = "Weight must be greater than 0")
	private int weight;
    
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Code must be uppercase letters, numbers, or underscores")
    @NotBlank(message = "Code is required")
	private String code;
	
    @NotBlank(message = "Image is required")
	private String image;
}
