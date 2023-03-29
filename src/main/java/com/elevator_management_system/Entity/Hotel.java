package com.elevator_management_system.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Hotel {
	@Id
	private int hotel_id;
	@Column
	private String hotel_name;

	@OneToMany
	private List<Elevator> elevators;

	public Hotel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Hotel(int hotel_id, String hotel_name, List<Elevator> elevators) {
		super();
		this.hotel_id = hotel_id;
		this.hotel_name = hotel_name;
		this.elevators = elevators;
	}

	@Override
	public String toString() {
		return "Hotel [hotel_id=" + hotel_id + ", hotel_name=" + hotel_name + ", elevators=" + elevators + "]";
	}

}
