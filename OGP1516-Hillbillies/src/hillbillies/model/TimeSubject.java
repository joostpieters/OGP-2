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
	
	/*protected boolean canHaveAsPosition(double[] position) {
		if (!(position instanceof double[]) || position.length != 3 )
			return false;
		if (position[0] < 0.0 || position[0] >= getWorld().getNbCubesX())
			return false;
		if (position[1] < 0.0 || position[1] >= getWorld().getNbCubesY())
			return false;
		if (position[2] < 0.0 || position[2] >= getWorld().getNbCubesZ())
			return false;
		int[] intPosition = convertPositionToInt(position);
		if (!TerrainType.byOrdinal(getWorld().getTerrainTypes()
				[intPosition[0]][intPosition[1]][intPosition[2]]).isPassable() )
			return false;
		return true;
	}*/
	
	
	protected boolean canHaveAsPosition(Coordinate coordinates) {
		
		if (!getWorld().canHaveAsCoordinates(coordinates)) {
			return false; }

		if (!TerrainType.byOrdinal(getWorld().getTerrainTypes()
				[coordinates.get(0)][coordinates.get(1)][coordinates.get(2)]).isPassable() )
			return false;
		
		return true;
	}
	
	
	/**
	 * Return the exact position of the unit in its game world.
	 */
	@Basic @Raw
	public double[] getPosition() {
		return this.position;
	}
	
	
	/**
	 * Return the position of the cube in the game world occupied by 
	 * the unit.
	 */
	@Raw
	public Coordinate getCubeCoordinate() {
		return convertPositionToCoordinate(getPosition());
	}
	
	/**
	 * Variable registering the position of the unit in the game world.
	 */
	protected double position[] = new double[3];

	
	
	
	protected boolean isNeighbouringSolid(Coordinate cubeCoordinate) {
		return getWorld().isNeighbouringSolid(cubeCoordinate);
	}
	
	protected boolean isAboveSolid(Coordinate cubeCoordinate) {
		return getWorld().isAboveSolid(cubeCoordinate);
	}
	
	
	/**
	 * Variable registering the world to which this item belongs.
	 */
	protected World world = null;
	
	/**
	 * Return the world to which this item belongs. Returns a null refererence
	 * if this item does not belong to any world.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}
	
	
	/**
	 * Variable registering whether or not this item is terminated.
	 */
	protected boolean isTerminated = false;
	
	/**
	 * Check whether this item is terminated.
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Terminate this item.
	 * 
	 * @post	This item is terminated.
	 * 			| new.isTerminated()
	 */
	public void terminate() {
		this.isTerminated = true;
	}
	
	
	
	
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
	protected static double[] convertPositionToDouble(int[] intPosition) 
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
	protected static int[] convertPositionToInt(double[] doublePosition) 
			throws IllegalArgumentException {

		if ( !(doublePosition instanceof double[]) || doublePosition.length != 3)
			throw new IllegalArgumentException();
		int[] intPosition = new int[3];
		for (int i=0; i<3; i++) {
			intPosition[i] = (int) Math.floor(doublePosition[i]);
		}
		return intPosition;
	}
	
	
	protected static Coordinate convertPositionToCoordinate(double[] doublePosition) 
			throws IllegalArgumentException {

		if ( !(doublePosition instanceof double[]) || doublePosition.length != 3)
			throw new IllegalArgumentException();
		
		return new Coordinate((int) doublePosition[0], 
				(int) doublePosition[1], (int) doublePosition[2]);
		
	}
	
	
	protected static double[] convertCoordinateToDouble(Coordinate coordinate) 
			throws IllegalArgumentException {

		return new double[]{coordinate.get(0), coordinate.get(1), coordinate.get(2)};
		
	}
	
}
