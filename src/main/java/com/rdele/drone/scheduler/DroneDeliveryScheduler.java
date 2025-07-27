package com.rdele.drone.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rdele.drone.entity.Drone;
import com.rdele.drone.enums.DroneState;
import com.rdele.drone.repository.DroneRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DroneDeliveryScheduler {

	private final DroneRepository droneRepository;
	
	@Scheduled(fixedRate = 10000) //5secs
	@Transactional
	public void updateDroneState() {
		int batteryConsumption = 5;
		for(Drone drone : droneRepository.findAll()) {
			if(drone.getState()==DroneState.LOADED) {
				drone.setState(DroneState.DELIVERING);
				System.out.println(drone.getSerialNumber() + " is " + DroneState.DELIVERING);
			}
			else if(drone.getState()==DroneState.DELIVERING) {
				drone.setState(DroneState.DELIVERED);
				drone.setBatteryCapacity(Math.max(0, drone.getBatteryCapacity()-batteryConsumption));
				System.out.println(drone.getSerialNumber() + " " + DroneState.DELIVERED);
			}
			else if(drone.getState()==DroneState.DELIVERED) {
				drone.setState(DroneState.RETURNING);
				System.out.println(drone.getSerialNumber() + " is " + DroneState.RETURNING);
			}
			else if(drone.getState()==DroneState.RETURNING) {
				System.out.println(drone.getSerialNumber() + " is " + DroneState.IDLE);
				drone.setBatteryCapacity(Math.max(0, drone.getBatteryCapacity()-batteryConsumption));
				drone.setState(DroneState.IDLE);
                drone.getMedications().clear();
			}
			 droneRepository.save(drone);
		}
	}
	
}
