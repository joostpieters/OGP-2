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
	public IllegalTargetException(int[] positionCube, int[] targetCube) {
		this.positionCube = positionCube;
		this.targetCube = targetCube;
	}
	
	/*
	public double[] getPosition() {
		return position;
	}*/
	
	private final int[] positionCube;
	private final int[] targetCube;
	
	
}
	
	
	

