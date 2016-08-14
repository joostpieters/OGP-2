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
			System.out.println("world can't have as coordinates");
			return false; }

		if (!TerrainType.byOrdinal(getWorld().getTerrainTypes()
				[coordinates.get(0)][coordinates.get(1)][coordinates.get(2)]).isPassable() ) {
			System.out.println("not passable");
			return false;
		}
		return true;
	}
	
	
	
	protected void setPosition(double[] position) throws IllegalPositionException {
		if (! canHaveAsPosition(Convert.convertPositionToCoordinate(position)) ||
				position.length != 3) {
				//System.out.println(isFalling());
				//System.out.println(hashCode());
				throw new IllegalPositionException(position);
			}
		this.position[0] = position[0];
		this.position[1] = position[1];
		this.position[2] = position[2];
	}
	//TODO parameteriseren ipv 0 1 en 2 apart
	
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
	
	
	protected /*abstract*/ double[] getVelocity() throws IllegalPositionException {
		if (isFalling())
			return new double[]{0.0,0.0,-3.0};
		return new double[]{0.0,0.0,0.0};
	}
	
	
	public boolean isFalling() {
		return this.isFalling;
	}
	
	public void setFalling(boolean value) {
		this.isFalling = value;
	}
	
	private boolean isFalling = false;
	
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
		return Convert.convertPositionToCoordinate(getPosition());
	}
	
	
	/**
	 * Variable registering the position of the game object in the game world.
	 */
	private double position[] = new double[3];

	
	
	protected boolean isNeighbouringSolid(Coordinate cubeCoordinate) {
		return getWorld().isNeighbouringSolid(cubeCoordinate);
	}
	
	
	protected boolean isAboveSolid(Coordinate cubeCoordinate) {
		return getWorld().isAboveSolid(cubeCoordinate);
	}
	
	
	
	protected void setWorld(World world) throws IllegalArgumentException {
		//if ( (world != null) && !world.hasAsItem(this) )
			//throw new IllegalArgumentException();
		//if ( (world == null) && (getWorld() != null) )
		//	throw new IllegalArgumentException();
		//TODO
		this.world = world;
	}

	
	/**
	 * Variable registering the world to which this item belongs.
	 */
	private World world = null;
	
	
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
	
	
	public abstract void terminate();
	
	
	/**
	 * Variable registering whether or not this item is terminated.
	 */
	protected boolean isTerminated = false;
	


}
