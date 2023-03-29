package com.elevator_management_system.Entity;

import jakarta.persistence.Embeddable;

@Embeddable
public enum State {

	MOVING(1), STOPPED(0), IDLE(-1);
	private int text =-1;

	
	 private State(int text) {
		this.text = text;
	}

	public int getText(){return this.text;}

	    public static State fromText(int text){
	        for(State r : State.values()){
	            if(r.getText()==text){
	                return r;
	            }
	        }
	        throw new IllegalArgumentException();
	    }
	}