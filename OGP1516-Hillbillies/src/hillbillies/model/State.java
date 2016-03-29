package hillbillies.model;

import be.kuleuven.cs.som.annotate.Value;

@Value
public enum State {
	EMPTY, WORKING, RESTING_HP, RESTING_STAM, ATTACKING, MOVING
}
