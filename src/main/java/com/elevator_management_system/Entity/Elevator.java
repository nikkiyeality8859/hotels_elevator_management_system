package com.elevator_management_system.Entity;

import java.util.TreeSet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class Elevator {

	
	@ManyToOne
	private Hotel hotel;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int elevator_id;

	@Column(name = "floor_count")
	private  int count = 0;

	@Enumerated(EnumType.STRING)
    @Column( nullable = false)
	private Direction currentDirection = Direction.fromText(1);
	@Enumerated(EnumType.STRING)
    @Column( nullable = false)
	private State currentState = State.IDLE;
	@Column
	private int currentFloor = 0;
	
	
	

	public Direction getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(Direction currentDirection) {
		this.currentDirection = currentDirection;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel_id(Hotel hotel) {
		this.hotel = hotel;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getElevator_id() {
		return elevator_id;
	}

	public void setElevator_id(int elevator_id) {
		this.elevator_id = elevator_id;
	}

	public TreeSet<Request> getCurrentJobs() {
		return currentJobs;
	}

	public void setCurrentJobs(TreeSet<Request> currentJobs) {
		this.currentJobs = currentJobs;
	}

	public TreeSet<Request> getUpPendingJobs() {
		return upPendingJobs;
	}

	public void setUpPendingJobs(TreeSet<Request> upPendingJobs) {
		this.upPendingJobs = upPendingJobs;
	}

	public TreeSet<Request> getDownPendingJobs() {
		return downPendingJobs;
	}

	public void setDownPendingJobs(TreeSet<Request> downPendingJobs) {
		this.downPendingJobs = downPendingJobs;
	}

	@Override
	public String toString() {
		return "Elevator [hotel=" + hotel + ", elevator_id=" + elevator_id + ", count=" + count
				+ ", currentDirection=" + currentDirection + ", currentState=" + currentState + ", currentFloor="
				+ currentFloor + ", currentJobs=" + currentJobs + ", upPendingJobs=" + upPendingJobs
				+ ", downPendingJobs=" + downPendingJobs + "]";
	}
	
	

	public Elevator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Elevator(Hotel hotel, int elevator_id, int count, Direction currentDirection, State currentState,
			int currentFloor) {
		super();
		this.hotel = hotel;
		this.elevator_id = elevator_id;
		this.count = count;
		this.currentDirection = currentDirection;
		this.currentState = currentState;
		this.currentFloor = currentFloor;
		
	}



	/**
	 * jobs which are being processed
	 */
	@Transient
	private TreeSet<Request> currentJobs = new TreeSet<>();
	/**
	 * up jobs which cannot be processed now so put in pending queue
	 */
	@Transient
	private TreeSet<Request> upPendingJobs = new TreeSet<>();
	/**
	 * down jobs which cannot be processed now so put in pending queue
	 */
	@Transient
	private TreeSet<Request> downPendingJobs = new TreeSet<>();

	public void startElevator() {
		System.out.println("The Elevator has started functioning");
		while (true) {

			if (checkIfJob()) {

				if (currentDirection == Direction.UP) {
					Request request = currentJobs.pollFirst();
					processUpRequest(request);
					if (currentJobs.isEmpty()) {
						addPendingDownJobsToCurrentJobs();

					}

				}
				if (currentDirection == Direction.DOWN) {
					Request request = currentJobs.pollLast();
					processDownRequest(request);
					if (currentJobs.isEmpty()) {
						addPendingUpJobsToCurrentJobs();
					}

				}
			}
		}
	}

	public boolean checkIfJob() {

		if (currentJobs.isEmpty()) {
			return false;
		}
		return true;

	}

	private void processUpRequest(Request request) {
		int startFloor = currentFloor;
		if (startFloor < request.getExternalRequest().getSourceFloor()) {
			for (int i = startFloor; i <= request.getExternalRequest().getSourceFloor(); i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("We have reached floor -- " + i);
				count++;
				currentFloor = i;
			}
		}

		System.out.println("Reached Source Floor--opening door");

		startFloor = currentFloor;

		for (int i = startFloor + 1; i <= request.getInternalRequest().getDestinationFloor(); i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("We have reached floor -- " + i);
			currentFloor = i;
			count++;
			if (checkIfNewJobCanBeProcessed(request)) {
				break;
			}
		}

	}

	private void processDownRequest(Request request) {

		int startFloor = currentFloor;
		if (startFloor < request.getExternalRequest().getSourceFloor()) {
			for (int i = startFloor; i <= request.getExternalRequest().getSourceFloor(); i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("We have reached floor -- " + i);
				currentFloor = i;
				count++;
			}
		}

		System.out.println("Reached Source Floor--opening door");

		startFloor = currentFloor;

		for (int i = startFloor - 1; i >= request.getInternalRequest().getDestinationFloor(); i--) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("We have reached floor -- " + i);
			currentFloor = i;
			count++;
			if (checkIfNewJobCanBeProcessed(request)) {
				break;
			}
		}

	}

	private boolean checkIfNewJobCanBeProcessed(Request currentRequest) {
		if (checkIfJob()) {
			if (currentDirection == Direction.UP) {
				Request request = currentJobs.pollLast();
				if (request.getInternalRequest().getDestinationFloor() < currentRequest.getInternalRequest()
						.getDestinationFloor()) {
					currentJobs.add(request);
					currentJobs.add(currentRequest);
					return true;
				}
				currentJobs.add(request);

			}

			if (currentDirection == Direction.DOWN) {
				Request request = currentJobs.pollFirst();
				if (request.getInternalRequest().getDestinationFloor() > currentRequest.getInternalRequest()
						.getDestinationFloor()) {
					currentJobs.add(request);
					currentJobs.add(currentRequest);
					return true;
				}
				currentJobs.add(request);

			}

		}
		return false;

	}

	private void addPendingDownJobsToCurrentJobs() {
		if (!downPendingJobs.isEmpty()) {
			System.out.println("Pick a pending down job and execute it by putting in current job");
			currentJobs = downPendingJobs;
			currentDirection = Direction.DOWN;
		} else {
			currentState = State.IDLE;
			System.out.println("The elevator is in Idle state");
		}

	}

	private void addPendingUpJobsToCurrentJobs() {
		if (!upPendingJobs.isEmpty()) {
			System.out.println("Pick a pending up job and execute it by putting in current job");

			currentJobs = upPendingJobs;
			currentDirection = Direction.UP;
		} else {
			currentState = State.IDLE;
			System.out.println("The elevator is in Idle state");

		}

	}

	public void addJob(Request request) {
		if (currentState == State.IDLE) {
			// check if the elevator is already on the floor on which the user
			// is if yes then we can directly process the destination floor
			if (currentFloor == request.getExternalRequest().getSourceFloor()) {
				System.out.println("Added current queue job -- lift state is - " + currentState + " location is - "
						+ currentFloor + " to move to floor - " + request.getInternalRequest().getDestinationFloor());
			}
			// check if the elevator is already on the floor on which the user
			// is if no then elevator first needs to move to source floor
			else {
				System.out.println("Added current queue job -- lift state is - " + currentState + " location is - "
						+ currentFloor + " to move to floor - " + request.getExternalRequest().getSourceFloor());
			}
			currentState = State.MOVING;
			currentDirection = request.getExternalRequest().getDirectionToGo();
			currentJobs.add(request);
		} else if (currentState == State.MOVING) {

			if (request.getExternalRequest().getDirectionToGo() != currentDirection) {
				addtoPendingJobs(request);
			} else if (request.getExternalRequest().getDirectionToGo() == currentDirection) {
				if (currentDirection == Direction.UP
						&& request.getInternalRequest().getDestinationFloor() < currentFloor) {
					addtoPendingJobs(request);
				} else if (currentDirection == Direction.DOWN
						&& request.getInternalRequest().getDestinationFloor() > currentFloor) {
					addtoPendingJobs(request);
				} else {
					currentJobs.add(request);
				}

			}

		}

	}

	public void addtoPendingJobs(Request request) {
		if (request.getExternalRequest().getDirectionToGo() == Direction.UP) {
			System.out.println("Add to pending up jobs");
			upPendingJobs.add(request);
		} else {
			System.out.println("Add to pending down jobs");
			downPendingJobs.add(request);
		}
	}

}

















