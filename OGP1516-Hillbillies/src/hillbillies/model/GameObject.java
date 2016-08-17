package hillbillies.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.World.TerrainType;

/**
 * A class of game objects, with a given position and world.
 * 
 * @author 	rubencartuyvels
 * @version	2.5
 */
public abstract class GameObject {
	
	
	protected GameObject() {
		
	}
	
	
	protected GameObject(World world) {
		setWorld(world);
		setPosition(getWorld().getRandomNeighbouringSolidCube());
	}
	
	protected GameObject(World world, Coordinate position) {
		setWorld(world);
		setPosition(position);
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
	 */
	@Model
	protected static boolean isValidDT(double dt) {
		return (dt <= 0.2 && dt >= 0.0);
	}
	
	

	public boolean canHaveAsPosition(Coordinate coordinates) {
		if (coordinates == null) {
			return false;
		}
		if (!getWorld().canHaveAsCoordinates(coordinates)) {
			return false; }

		if (!TerrainType.byOrdinal(getWorld().getTerrainTypes()
				[coordinates.get(0)][coordinates.get(1)][coordinates.get(2)]).isPassable() ) {
			return false;
		}
		return true;
	}
	
	
	
	protected void setPosition(double[] position) throws IllegalPositionException {
		if (! canHaveAsPosition(Convert.convertPositionToCoordinate(position)) ||
				position.length != 3) {
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
	
	
	protected double[] getVelocity() throws IllegalPositionException {
		return new double[]{0.0,0.0,0.0};
	}
	
	
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
	
	
	
	/**
	 * Check whether this game object is terminated.
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	
	public void terminate() {
		getWorld().removeGameObject(this);
		this.isTerminated = true;
	}
	
	
	/**
	 * Variable registering whether or not this game object is terminated.
	 */
	protected boolean isTerminated = false;
	
	
	
	/* *********************************************************
	 * 
	 * 						UNIT - WORLD
	 *
	 **********************************************************/
	
	
	/**
	 * Variable registering the world to which this game object belongs.
	 */
	private World world = null;
	
	
	/**
	 * Return the world to which this game object belongs. Returns a null refererence
	 * if this game object does not belong to any world.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}
	
	
	/**
	 * Check whether this game object can be attached to a given world.
	 * 
	 * @param	world
	 * 			The world to check.
	 * 
	 * @return	True if and only if the given world is not effective or if it
	 * 			can contain this game object.
	 * 			| result == ( (world == null)
	 * 			| 				|| world.canHaveAsGameObject(this) )
	 */
	@Raw
	public boolean canHaveAsWorld(World world) {
		return ( (world == null) || world.canHaveAsGameObject(this) );
	}
	
	
	/**
	 * Check whether this game object has a proper world in which it belongs.
	 * 
	 * @return	True if and only if this game object can have its world as the world to
	 * 			which it belongs and if that world is either not effective or contains
	 * 			this game object.
	 * 			| result == ( canHaveAsWorld(getWorld()) && ( (getWorld() == null)
	 * 			|				|| getWorld.hasAsGameObject(this) ) )
	 */
	@Raw
	public boolean hasProperWorld() {
		return (canHaveAsWorld(getWorld()) && ( (getWorld() == null) 
					|| getWorld().hasAsGameObject(this) ) );
	}
	
	
	/**
	 * Set the world this game object belongs to to the given world.
	 * 
	 * @param	world
	 * 			The world to add the game object to.
	 * 
	 * @post	This game object references the given world as the world
	 * 			it belongs to.
	 * 			| new.getWorld() == world
	 * 
	 * @throws	IllegalArgumentException
	 * 			If the given world is not effective and this game object references an
	 * 			effective world, that world may not contain this game object.
	 * 			| (world == null) && (getWorld() != null) 
	 * 			|					&& (getWorld().hasAsGameObject(this))
	 */
	//TODO
	public void setWorld(World world) throws IllegalArgumentException {
		//if ( (world != null) && !world.hasAsGameObject(this) )
		//	throw new IllegalArgumentException();
		if ( (world == null) && (getWorld() != null) && (getWorld().hasAsGameObject(this)) )
			throw new IllegalArgumentException();
		this.world = world;
	}
	
	
	
	
	
	


}
