package com.elevator_management_system;

import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.elevator_management_system.Controller.MainController;
import com.elevator_management_system.Entity.Direction;
import com.elevator_management_system.Entity.Elevator;
import com.elevator_management_system.Entity.Hotel;
import com.elevator_management_system.Entity.State;
import com.elevator_management_system.service.Elevator_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
public class MainControllerTest {

	private MockMvc mock;
	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWritter = objectMapper.writer();
	@Mock
	private Elevator_service service;

	@InjectMocks
	private MainController controller;
	Hotel hotel = new Hotel(111, "taj", new ArrayList<>() {
	});
	Elevator elevator1 = new Elevator(hotel, 121, 0, Direction.UP, State.IDLE, 2);
	Elevator elevator2 = new Elevator(hotel, 122, 0, Direction.DOWN, State.MOVING, 3);
	Elevator elevator3 = new Elevator(hotel, 123, 0, Direction.DOWN, State.MOVING, 5);

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mock=MockMvcBuilders.standaloneSetup(MainController.class).build();
	}
	
	@Test
	public void getListOfElevators_success()throws Exception {
		List<Elevator> records=new ArrayList<>();
		records.add(elevator1);
		records.add(elevator2);
		records.add(elevator3);
		Mockito.when(service.findAll()).thenReturn(records);
		
		mock.perform(MockMvcRequestBuilders
	            .get("/getAllElevator")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", hasSize(3)))
	            .andExpect(jsonPath("$[2].hotel", is(hotel)));
	
	}
	
	
	@Test
	public void getElevatorById_success() throws Exception {
	    Mockito.when(service.getElevatorDetails(elevator1.getElevator_id())).thenReturn(elevator1);

	    mock.perform(MockMvcRequestBuilders
	            .get("/getElevatorById/121")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", notNullValue()))
	            .andExpect(jsonPath("$.hotel", is(hotel)));
	}

}
