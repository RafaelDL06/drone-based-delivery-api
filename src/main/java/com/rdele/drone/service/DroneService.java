package com.rdele.drone.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rdele.drone.dto.DroneDTO;
import com.rdele.drone.dto.MedicationDTO;
import com.rdele.drone.entity.Drone;
import com.rdele.drone.entity.Medication;
import com.rdele.drone.enums.DroneModel;
import com.rdele.drone.enums.DroneState;
import com.rdele.drone.repository.DroneRepository;
import com.rdele.drone.repository.MedicationRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class DroneService {
	private final DroneRepository droneRepository;
	private final MedicationRepository medicationRepository;

	public DroneDTO registerDrone(DroneDTO dto) {
		DroneModel model = DroneModel.valueOf(dto.getModel());
		Drone drone = new Drone();
		drone.setSerialNumber(dto.getSerialNumber());
		drone.setModel(model);
		drone.setBatteryCapacity(dto.getBatteryCapacity());
		drone.setState(DroneState.IDLE);
		droneRepository.save(drone);
		
		return new DroneDTO(drone.getSerialNumber(),
				drone.getModel().toString(),
				drone.getBatteryCapacity(),
				drone.getState());
	}

	public DroneDTO getDroneDetail(String serialNumber) {
		Drone drone = droneRepository.findBySerialNumber(serialNumber)
					.orElseThrow(() -> new RuntimeException("Drone with serial number " 
							+ serialNumber + " not found"));
		
		return new DroneDTO(drone.getSerialNumber(),
				drone.getModel().toString(),
				drone.getBatteryCapacity(),
				drone.getState());
	}

	public List<MedicationDTO> loadDrone(String serialNumber, List<MedicationDTO> medications) {
		Drone drone = droneRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new RuntimeException("Drone with serial number " 
						+ serialNumber + " not found"));
		
		if (drone.getState() != DroneState.IDLE && drone.getState() != DroneState.LOADING) {
		    throw new RuntimeException("Drone not in idle or loading state");
		}
		
		if(drone.getBatteryCapacity()<25)
			throw new RuntimeException("Battery low can't load");
		
		int currentWeight = drone.getMedications() == null ? 0 : drone.getMedications().stream().mapToInt(Medication::getWeight).sum();
	    int newWeight = medications.stream().mapToInt(m -> m.getWeight()).sum();
		
	    if(currentWeight + newWeight > drone.getModel().getWeightLimit())
	    	throw new RuntimeException("Weight limit exceeded");
	    
	    List<Medication> saveMedications = medications.stream().map(m -> {
            Medication medication = new Medication();
            medication.setName(m.getName());
            medication.setWeight(m.getWeight());
            medication.setCode(m.getCode());
            medication.setImage(m.getImage());
            medication.setDrone(drone);
            return medication;
        }).collect(Collectors.toList());
	    
	    if (drone.getState() == DroneState.IDLE)
            drone.setState(DroneState.LOADING);
	    
	    droneRepository.save(drone);
	    medicationRepository.saveAll(saveMedications);
	    
	    return saveMedications.stream()
	    	    .map(m -> new MedicationDTO(m.getName(), m.getWeight(), m.getCode(), m.getImage()))
	    	    .collect(Collectors.toList()); 
	}

	public List<MedicationDTO> getLoadedMedications(String serialNumber) {
		if(!droneRepository.existsBySerialNumber(serialNumber)) 
			throw new RuntimeException("Drone with serial number " + serialNumber + " not found");
		
		List<Medication> medications = medicationRepository.findByDroneSerialNumber(serialNumber);
		
		return medications.stream()
				.map(m -> new MedicationDTO(m.getName(), m.getWeight(), m.getCode(), m.getImage()))
	    	    .collect(Collectors.toList()); 
	}

	public List<DroneDTO> getAvailableDrones() {
		List<DroneState> droneStateList = Arrays.asList(DroneState.IDLE,DroneState.LOADING);
		int batteryCapacity = 25;
		List<Drone> drones = droneRepository.findByStateInAndBatteryCapacityGreaterThan(droneStateList, batteryCapacity);
		return drones.stream()
				.map(d -> new DroneDTO(d.getSerialNumber(),d.getModel().toString(),d.getBatteryCapacity(),d.getState()))
				.collect(Collectors.toList());
	}

	public int getBatteryLevel(String serialNumber) {
		Drone drone = droneRepository.findBySerialNumber(serialNumber)
						.orElseThrow((() -> new RuntimeException("Drone with serial number " 
								+ serialNumber + " not found")));
		return drone.getBatteryCapacity();
	}

	public String deliverMedications(String serialNumber) {
		Drone drone = droneRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new RuntimeException("Drone with serial number " 
						+ serialNumber + " not found"));
		if(drone.getState()!=DroneState.LOADING)
			throw new RuntimeException("Drone with serial number " 
					+ serialNumber + " not yet in loading state");
					
		drone.setState(DroneState.LOADED);
		droneRepository.save(drone);
		return drone.getSerialNumber() + " is delivering";
	}

}
