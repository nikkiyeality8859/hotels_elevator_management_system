package com.elevator_management_system.Entity;

import jakarta.persistence.Embeddable;

@Embeddable
public enum Direction {

	UP(1), DOWN(-1);
private int text =1;
	 private Direction(int text) {
			this.text = text;
		}

		public int getText(){return this.text;}

		    public static Direction fromText(int text){
		        for(Direction r : Direction.values()){
		            if(r.getText()==text){
		                return r;
		            }
		        }
		        throw new IllegalArgumentException();
		    }
}