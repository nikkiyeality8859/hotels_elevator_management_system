package com.elevator_management_system.Entity;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Embeddable;

@Embeddable
 public class ProcessJobWorker implements Runnable {
     @Autowired
	private Elevator elevator;

	public ProcessJobWorker(Elevator elevator) {
		this.elevator = elevator;
	}

	@Override
	public void run() {
		/**
		 * start the elevator
		 */
		elevator.startElevator();
	}

}