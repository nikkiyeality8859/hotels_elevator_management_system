package com.elevator_management_system.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elevator_management_system.Entity.AddJobWorker;
import com.elevator_management_system.Entity.Direction;
import com.elevator_management_system.Entity.Elevator;
import com.elevator_management_system.Entity.ExternalRequest;
import com.elevator_management_system.Entity.InternalRequest;
import com.elevator_management_system.Entity.ProcessJobWorker;
import com.elevator_management_system.Entity.Request;
import com.elevator_management_system.service.Elevator_service;;;

@RestController
@RequestMapping("/elevator_management_system")
public class MainConroller {
	@Autowired
	Elevator_service service;

	// add new elevator
	@PostMapping("/addElevator")
	public String addElevator(@RequestBody Elevator elevator) {
		int id = elevator.getElevator_id();
		if (!service.isExist(id)) {
			service.addNewElevator(elevator);
			service.startLift(elevator);
			return "Elevator is setup successfully";

		}
		return "Elevator with " + id + " already exist";
	}

	// fetching details of elevator
	@GetMapping("/getElevatorById/{elevator_Id}")
	public ResponseEntity<Elevator> getElevatorById(@PathVariable int elevator_id) {

		if (service.isExist(elevator_id)) {
			return new ResponseEntity<>(service.getElevatorDetails(elevator_id), HttpStatus.OK);
		}
		return null;

	}

   //fetching list of all elevator
	@GetMapping("/getAllElevator")
	public ResponseEntity<List<Elevator>> getAllElevator() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}



	//request for elevator
	@PutMapping("/hitRequest/{elevator_id}/{direction}/{sourceFloor}/{destinationFloor}")
	public ResponseEntity<Elevator> hitRequestOnElevator(@PathVariable int elevator_id, @PathVariable String direction,
			int sourceFloor, int destinationFloor) {

		Elevator elevator = service.getElevatorDetails(elevator_id);

		Elevator updatedElevator = service.hitRequest(elevator, direction, sourceFloor, destinationFloor);

		return ResponseEntity.ok(service.updateElevatorDetails(updatedElevator));

	}

}
