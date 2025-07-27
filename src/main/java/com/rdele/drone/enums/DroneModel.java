package com.rdele.drone.enums;

public enum DroneModel {
	LIGHTWEIGHT(200),
	MIDDLEWEIGHT(400),
	CRUISEWEIGHT(800),
	HEAVYWEIGHT(1000);

	private final int weightLimit;
	
	DroneModel(int weightLimit) {
		this.weightLimit = weightLimit;
	}
	public int getWeightLimit() {
		return weightLimit;
	}
	
}
