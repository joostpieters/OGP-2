package hillbillies.model;


/**
* A class of exceptions signaling illegal working and attacking times.
* 	Each exception involves the illegal time variable and the unit.
*
*@author Ruben Cartuyvels
*@version	1.0
*/
public class IllegalTimeException extends RuntimeException {
	
	/**
	 * Initialize this new illegal time exception with given time
	 * and unit.
	 * 
	 * @param time
	 * 			The illegal time.
	 * @param unit
	 * 			The unit.
	 * @post The time of this new illegal position time is equal
	 * 		to the given position
	 * 		| new.getPosition() == position
	 * 
	 * ...
	 */
	public IllegalTimeException(float time, Unit unit) {
		this.time = time;
		this.unit = unit;
	}
	
	public float getTime() {
		return time;
	}
	
	private final float time;
	
	public Unit getUnit() {
		return unit;
	}
	
	private final Unit unit;
}
