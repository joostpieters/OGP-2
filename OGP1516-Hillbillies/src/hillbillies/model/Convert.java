package hillbillies.model;

public final class Convert {
	
	
	
	/**
	 * Converts a position given in integers to a position given in double values.
	 * 	This is an auxiliary method.
	 * 
	 * @param intPosition
	 * 			The position given in integer values.
	 * @return the position provided as parameter converted to the java
	 * 			double primitive type.
	 * @throws IllegalArgumentException
	 * 			The parameter is not an array of integers.
	 * 			| ! intPosition instanceof int[]
	 * 			| || intPosition.length != 3
	 */
	public static double[] convertPositionToDouble(int[] intPosition) 
			throws IllegalArgumentException {

		if ( !(intPosition instanceof int[]) || intPosition.length != 3)
			throw new IllegalArgumentException();
		double[] doublePosition = new double[3];
		for (int i=0; i<3; i++) {
			doublePosition[i] = (double) intPosition[i];
		}
		return doublePosition;
	}


	/**
	 * Converts a position given in doubles to a position given in integer values.
	 * 	This is an auxiliary method.
	 * 
	 * @param doublePosition
	 * 			The position given in double values.
	 * @return the position provided as parameter converted to the java
	 * 			integer primitive type.
	 * @throws IllegalArgumentException
	 * 			The parameter is not an array of doubles.
	 * 			| ! doublePosition instanceof double[]
	 * 			| || doublePosition.length != 3
	 */
	public static int[] convertPositionToInt(double[] doublePosition) 
			throws IllegalArgumentException {

		if ( !(doublePosition instanceof double[]) || doublePosition.length != 3)
			throw new IllegalArgumentException();
		int[] intPosition = new int[3];
		for (int i=0; i<3; i++) {
			intPosition[i] = (int) Math.floor(doublePosition[i]);
		}
		return intPosition;
	}
	
	
	public static Coordinate convertPositionToCoordinate(double[] doublePosition) 
			throws IllegalArgumentException {

		if ( !(doublePosition instanceof double[]) || doublePosition.length != 3)
			throw new IllegalArgumentException();
		
		return new Coordinate((int) doublePosition[0], 
				(int) doublePosition[1], (int) doublePosition[2]);
		
	}
	
	
	public static double[] convertCoordinateToDouble(Coordinate coordinate) 
			throws IllegalArgumentException {

		return new double[]{coordinate.get(0), coordinate.get(1), coordinate.get(2)};
		
	}
	
	
	
}
