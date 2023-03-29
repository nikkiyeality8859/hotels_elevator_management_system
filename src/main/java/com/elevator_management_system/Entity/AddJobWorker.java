package com.elevator_management_system.Entity;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Embeddable;

@Embeddable
public class AddJobWorker implements Runnable {
@Autowired
	private Elevator elevator;
@Autowired
	private Request request;

	public AddJobWorker(Elevator elevator, Request request) {
		this.elevator = elevator;
		this.request = request;
	}

	@Override
	public void run() {

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		elevator.addJob(request);
	}

}
