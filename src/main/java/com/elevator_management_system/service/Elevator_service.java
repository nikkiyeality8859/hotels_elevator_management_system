package com.elevator_management_system.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.elevator_management_system.Entity.Elevator;

public interface Elevator_service {

	void addNewElevator(Elevator elevator);

	Elevator getElevatorDetails(int elevator_id);

	List<Elevator> findAll();

	boolean isExist(int id);

	Elevator updateElevatorDetails(Elevator elevator);

	public void startLift(Elevator elevator);

	public Elevator hitRequest(Elevator elevator, String direction,
			int sourceFloor, int destinationFloor);
}
