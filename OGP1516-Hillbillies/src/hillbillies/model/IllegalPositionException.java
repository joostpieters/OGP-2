package hillbillies.model;


/**
* A class of exceptions signaling illegal unit positions.
* 	Each exception involves the illegal position and the unit.
*
*@author Ruben Cartuyvels
*@version	1.0
*/
public class IllegalPositionException extends RuntimeException {
	
	/**
	 * Initialize this new illegal position exception with given position
	 * and unit.
	 * 
	 * @param position
	 * 			The illegal position.
	 * @param unit
	 * 			The unit.
	 * @post The position of this new illegal position exception is equal
	 * 		to the given position
	 * 		| new.getPosition() == position
	 * 
	 * ...
	 */
	public IllegalPositionException(double[] position, Unit unit) {
		this.position = position;
		this.unit = unit;
		this.positionInt = null;
	}
	
	public IllegalPositionException(int[] position, Unit unit) {
		this.positionInt = position;
		this.unit = unit;
		this.position = null;
	}
	
	public double[] getPosition() {
		return position;
	}
	
	private final double[] position;
	private final int[] positionInt;
	
	public Unit getUnit() {
		return unit;
	}
	
	private final Unit unit;
}
