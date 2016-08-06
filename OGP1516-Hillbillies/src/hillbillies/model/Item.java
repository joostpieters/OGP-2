package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.World.TerrainType;

import java.util.Random;



// IS VALID POSITION ETC
// INVARIANTS WORLD-ITEM IN ITEM EN WORLD CLASS

/**
 * 
 * @invar	Each item must have a proper world in which it belongs
 * 			| hasProperWorld()
 * 
 * @author	rubencartuyvels
 * @version	1.2
 */
public class Item extends GameObject {
	
	/**
	 * Initialize this new item, not yet attached to a world.
	 */
	@Raw
	public Item(Coordinate initialPosition, World world) throws IllegalPositionException,
															IllegalArgumentException {
		
		/* if (!getWorld().canHaveAsCoordinates(initialPosition)) 
			throw new IllegalPositionException(initialPosition);
		if (getWorld().getCubeTypeAt(initialPosition).isPassable() )
			throw new IllegalPositionException(initialPosition);
			*/
		// Gaat niet want world not niet set!
		// Oplossing: world wél als argument en meteen als world setten
		
		/*double[] position = new double[3];
		for (int i=0; i<initialPosition.getCoordinates().length; i++) {
			position[i] = initialPosition.get(i) + World.getCubeLength()/2;
		}*/
		
		setWorld(world);
		
		double[] position = World.getCubeCenter(initialPosition);
		
		int weight = random.nextInt(41) + 10;
		if (!isValidWeight(weight))
			throw new IllegalArgumentException();
		this.weight = weight;
		
		try {
			setPosition(position);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	
	/**
	 * Set the position of the item to the given position.
	 * 
	 * @param 	position
	 * 			The new position coordinates for this item.
	 * 
	 * @post The new position of this item is equal to the
	 * 		given position.
	 * 		| new.getPosition() == position
	 * 
	 * @throws IllegalPositionException(position, this)
	 * 			The given position is not valid.
	 * 		| !isValidPosition(position)
	 */
	/*@Raw
	protected void setPosition(double[] position) throws IllegalPositionException {
		if (! canHaveAsPosition(position) )
			throw new IllegalPositionException(position);
		this.position[0] = position[0];
		this.position[1] = position[1];
		this.position[2] = position[2];
	}*/
	
	
	
	public void advanceTime (double dt) {
		if (! isValidDT(dt)) {
			throw new IllegalArgumentException();
		}
		
		if (!isNeighbouringSolid(getCubeCoordinate()))
			fall();
		
		if (isFalling()) {
			controlFalling(dt);
		}
	}
	
	
	private void fall() {
		if (!isFalling()) {
			setPosition(World.getCubeCenter(getCubeCoordinate()));
			setFalling(true);
		}
	}
	
	public boolean isFalling() {
		return this.isFalling;
	}
	
	public void setFalling(boolean value) {
		this.isFalling = value;
	}
	
	private boolean isFalling = false;
	
	private void controlFalling(double dt) throws IllegalPositionException {
		if(isAboveSolid(getCubeCoordinate()) || getCubeCoordinate().getCoordinates()[2] == 0) {
			setFalling(false);
			
		}
		else {
			updatePosition(dt);
		}
	}
	
	@Model
	protected double[] getVelocity() /*throws IllegalPositionException*/ {
		/*double d = Math.sqrt(Math.pow(getMovingDirection()[0],2)
				+ Math.pow(getMovingDirection()[1],2) + Math.pow(getMovingDirection()[2],2));*/
		if (isFalling())
			return new double[]{0.0,0.0,-3.0};
		
		return new double[]{0.0,0.0,0.0};
	}
	
	
	// Moet isValidWeight zijn!
	
	private static boolean isValidWeight(int value) {
		return (value >= getMinimumWeight() && value <= getMaximumWeight());
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	private static int getMinimumWeight() {
		return Item.MIN_WEIGHT;
	}
	
	private static int getMaximumWeight() {
		return Item.MAX_WEIGHT;
	}
	
	private final int weight;
	private static final int MIN_WEIGHT = 10;
	private static final int MAX_WEIGHT = 50;
	
	
	
	/**
	 * Check whether this item can be attached to a given world.
	 * 
	 * @param	world
	 * 			The world to check.
	 * 
	 * @return	True if and only if the given world is not effective or if it
	 * 			can contain this item.
	 * 			| result == ( (world == null)
	 * 			| 				|| world.canHaveAsUnit(this) )
	 */
	@Raw
	public boolean canHaveAsWorld(World world) {
		return ( (world == null) || world.canHaveAsItem(this) );
	}
	
	/**
	 * Check whether this item has a proper world in which it belongs.
	 * 
	 * @return	True if and only if this item can have its world as the world to
	 * 			which it belongs and if that world is either not effective or contains
	 * 			this item.
	 * 			| result == ( canHaveAsWorld(getWorld()) && ( (getWorld() == null)
	 * 			|				|| getWorld.hasAsItem(this) ) )
	 */
	@Raw
	public boolean hasProperWorld() {
		return (canHaveAsWorld(getWorld()) && ( (getWorld() == null) 
					|| getWorld().hasAsItem(this) ) );
	}
	
	
	/**
	 * Set the world this item belongs to to the given world.
	 * 
	 * @param	world
	 * 			The world to add the item to.
	 * 
	 * @post	This item references the given world as the world
	 * 			it belongs to.
	 * 			| new.getWorld() == world
	 * 
	 * @throws	IllegalArgumentException
	 * 			If the given world is effective it must already reference this item
	 * 			as one of its items.
	 * 			| (world != null) && !world.hasAsItem(this)
	 * 
	 * @throws	IllegalArgumentException
	 * 			If the given world is not effective and this item references an
	 * 			effective world, that world may not contain this item.
	 * 			| (world == null) && (getWorld() != null) 
	 * 			|					&& (getWorld().hasAsItem(this))
	 */
	public void setWorld(World world) throws IllegalArgumentException {
		//if ( (world != null) && !world.hasAsItem(this) )
			//throw new IllegalArgumentException();
		if ( (world == null) && (getWorld() != null) && (getWorld().hasAsItem(this)) )
			throw new IllegalArgumentException();
		this.world = world;
	}
	
	
	
	
}
