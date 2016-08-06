package hillbillies.model;

import be.kuleuven.cs.som.annotate.Value;

@Value
public enum State {
	EMPTY, WORKING, RESTING_1, RESTING_HP, RESTING_STAM, ATTACKING, MOVING, FALLING;
	
	
	public static boolean contains(State test) {

	    for (State c : State.values()) {
	        if (c.equals(test)) {
	            return true;
	        }
	    }

	    return false;
	}
	
}
