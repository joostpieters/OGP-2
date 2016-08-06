package hillbillies.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.World.TerrainType;

public abstract class TimeSubject {
	
	public TimeSubject() {
		
	}
	
	protected static final Random random = new Random();
	
	public abstract void advanceTime(double dt);
	
	
	/**
	 * Check if a given value is a valid game time dt value.
	 * 
	 * @param dt
	 * 			The value to be checked.
	 * @return true if and only if the value is larger than or equal to
	 * 			zero and smaller than 0.2.
	 * 		| result =  (dt >= 0 && dt < 0.2 )
	 */
	@Model
	protected static boolean isValidDT(double dt) {
		return (dt <= 0.2 && dt >= 0.0);
	}
	
	
	
}
