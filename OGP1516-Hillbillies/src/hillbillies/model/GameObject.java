package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.World.TerrainType;


public abstract class GameObject extends TimeSubject {

	
	
	protected boolean canHaveAsPosition(Coordinate coordinates) {
		if (coordinates == null) {
			System.out.println("coordinates == null");
			return false;
		}
		if (!getWorld().canHaveAsCoordinates(coordinates)) {
			return false; }

		if (!TerrainType.byOrdinal(getWorld().getTerrainTypes()
				[coordinates.get(0)][coordinates.get(1)][coordinates.get(2)]).isPassable() )
			return false;
		
		return true;
	}
	
	
	
	protected void setPosition(double[] position) throws IllegalPositionException {
		if (! canHaveAsPosition(convertPositionToCoordinate(position)) ) {
				throw new IllegalPositionException(position);
			}
		this.position[0] = position[0];
		this.position[1] = position[1];
		this.position[2] = position[2];
	}
	
	
	protected void setPosition(Coordinate position) throws IllegalPositionException {
		if (! canHaveAsPosition(position) ) {
				throw new IllegalPositionException(position);
			}
		this.position[0] = World.getCubeCenter(position)[0];
		this.position[1] = World.getCubeCenter(position)[1];
		this.position[2] = World.getCubeCenter(position)[2];
	}
	
	
	protected void updatePosition(double dt) throws IllegalPositionException {
		double[] newPosition = new double[3];
		for (int i=0; i<3; i++) {
			newPosition[i] = getPosition()[i] + getVelocity()[i]*dt;
		}
		setPosition(newPosition);
	}
	
	
	protected abstract double[] getVelocity() throws IllegalPositionException;
	
	
	/**
	 * Return the exact position of the game object in its game world.
	 */
	@Basic @Raw
	public double[] getPosition() {
		return this.position;
	}
	
	
	/**
	 * Return the position of the cube in the game world occupied by 
	 * the game object.
	 */
	@Raw
	public Coordinate getCoordinate() {
		return convertPositionToCoordinate(getPosition());
	}
	
	/**
	 * Variable registering the position of the game object in the game world.
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
	public abstract void terminate();
	
	
	/**
	 * Variable registering whether or not this item is terminated.
	 */
	protected boolean isTerminated = false;
	
	
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
