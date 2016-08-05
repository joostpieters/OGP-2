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
	 * Initialize this new illegal position exception with given position.
	 * 
	 * @param position
	 * 			The illegal position.
	 * @post The position of this new illegal position exception is equal
	 * 		to the given position
	 * 		| new.getPosition() == position
	 * 
	 * ...
	 */
	public IllegalPositionException(double[] position) {
		this.position = position;
		this.positionInt = null;
	}
	
	public IllegalPositionException(int[] position) {
		this.positionInt = position;
		this.position = null;
	}
	
	public IllegalPositionException(Coordinate position) {
		this.positionInt = position.getCoordinates();
		this.position = null;
	}
	
	public double[] getPosition() {
		return position;
	}
	
	private final double[] position;
	private final int[] positionInt;
	
	
}
