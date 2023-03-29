package com.elevator_management_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.elevator_management_system.Dao.Elevator_Dao;
import com.elevator_management_system.Entity.AddJobWorker;
import com.elevator_management_system.Entity.Direction;
import com.elevator_management_system.Entity.Elevator;
import com.elevator_management_system.Entity.ExternalRequest;
import com.elevator_management_system.Entity.InternalRequest;
import com.elevator_management_system.Entity.ProcessJobWorker;
import com.elevator_management_system.Entity.Request;
import com.elevator_management_system.Entity.State;

@Service
public class elevator_service_impl implements Elevator_service{
	@Autowired
	private Elevator_Dao elevatorDao;
	

	@Override
	public void addNewElevator(Elevator elevator) {
		elevatorDao.save(elevator);
		
	}

	@Override
	public Elevator getElevatorDetails(int elevator_id) {
		
		return elevatorDao.getById(elevator_id);
	}

	@Override
	public List<Elevator> findAll() {
		// TODO Auto-generated method stub
		return elevatorDao.findAll();
	}

	@Override
	public boolean isExist(int id) {
		// TODO Auto-generated method stub
		return elevatorDao.existsById(id);
	}

	@Override
	public Elevator updateElevatorDetails(Elevator elevator) {
		return elevatorDao.save(elevator);
	}

	@Override
	public void startLift(Elevator elevator) {
		
		ProcessJobWorker processJobWorker = new ProcessJobWorker(elevator);
		Thread t2 = new Thread(processJobWorker);
		t2.start();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InternalRequest ir = new InternalRequest(0);

		ExternalRequest er = new ExternalRequest(Direction.UP, 0);

		Request request1 = new Request(ir, er);

		/**
		 * Pass job to the elevator
		 */
		new Thread(new AddJobWorker(elevator, request1)).start();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Elevator hitRequest(Elevator elevator,String direction, int sourceFloor,
			int destinationFloor) {
		ProcessJobWorker processJobWorker = new ProcessJobWorker(elevator);
		Thread t2 = new Thread(processJobWorker);
		t2.start();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Direction dir;
		if (direction == "up") {
			dir = Direction.UP;
		} else {
			dir = Direction.DOWN;
		}

		ExternalRequest er = new ExternalRequest(dir, sourceFloor);
		InternalRequest ir = new InternalRequest(destinationFloor);

		Request request1 = new Request(ir, er);

		/**
		 * Pass job to the elevator
		 */
		new Thread(new AddJobWorker(elevator, request1)).start();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// updating the values;

		elevator.setCount(elevator.getCount());
		elevator.setCurrentFloor(destinationFloor);
return elevator;
		
		
	}

	
	

	

	

}
