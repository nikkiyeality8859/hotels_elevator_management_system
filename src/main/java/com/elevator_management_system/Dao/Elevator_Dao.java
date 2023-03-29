package com.elevator_management_system.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elevator_management_system.Entity.Elevator;
@Repository
public interface Elevator_Dao extends JpaRepository<Elevator, Integer> {

	

}
