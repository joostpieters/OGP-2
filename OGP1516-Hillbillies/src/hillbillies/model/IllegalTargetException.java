package hillbillies.model;

public class IllegalTargetException extends RuntimeException {
	
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
	public IllegalTargetException(Coordinate positionCube, Coordinate targetCube) {
		this.positionCube = positionCube;
		this.targetCube = targetCube;
	}
	
	/*
	public double[] getPosition() {
		return position;
	}*/
	
	private final Coordinate positionCube;
	private final Coordinate targetCube;
	
	
}
	
	
	

