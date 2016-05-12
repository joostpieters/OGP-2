package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;



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
public class Item {
	
	/**
	 * Initialize this new item, not yet attached to a world.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	@Raw
	public Item(int x, int y, int z/*, World world*/) {
		double xD = this.world.getCubeLength()/2 + (double) x;
		double yD = this.world.getCubeLength()/2 + (double) y;
		double zD = this.world.getCubeLength()/2 + (double) z;
		
		
		// ??????????????????????????? world
		//this.world = world;
		setPosition(new double[] {xD, yD, zD});
	}
	
	/**
	 * Variable registering the world to which this item belongs.
	 */
	private World world = null;
	
	/**
	 * Variable registering the position in a game world of this item.
	 */
	private double[] position = new double[3];
	
	
	
	/**
	 * Check whether the given position is a valid position for an item.
	 * 
	 * @param 	position
	 * 			The position to be checked.
	 * 
	 * @return True if and only if the position is effective, if it
	 * 			consists of an array with 3 double elements, and if 
	 * 			each element is between 0.0 and 50.0.
	 * 		| result = ( (position instanceof double[])
	 * 		|		&& (position.length == 3)
	 * 		| 		&& (for each coordinate in position:
	 * 		|			coordinate > 0.0 && coordinate < 50.0)
	 */
	//@Model
	private static boolean isValidPosition(double[] position) {
		boolean valid = true;
		if (!(position instanceof double[]) || position.length != 3 )
			valid = false;
		for (double coordinate: position) {
			if (coordinate < 0.0 || coordinate >= 50.0)
				valid = false;
		}
		return valid;
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
	@Raw
	private void setPosition(double[] position) throws IllegalPositionException {
		if (! isValidPosition(position) )
			throw new IllegalPositionException(position);
		this.position[0] = position[0];
		this.position[1] = position[1];
		this.position[2] = position[2];
	}
	
	/**
	 * Return the exact position of the item in its game world.
	 */
	@Basic @Raw
	public double[] getPosition() {
		return this.position;
	}
	
	/**
	 * Return the position of the cube in the game world occupied by 
	 * the item.
	 */
	@Raw
	public int[] getIntPosition() {
		int[] intPosition = new int[3];
		for (int i=0; i<getPosition().length; i++) {
			intPosition[i] = (int) getPosition()[i];
		}
		return intPosition;
	}
	
	
	/**
	 * Return the world to which this item belongs. Returns a null refererence
	 * if this item does not belong to any world.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}
	
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
		if ( (world != null) && !world.hasAsItem(this) )
			throw new IllegalArgumentException();
		if ( (world == null) && (getWorld() != null) && (getWorld().hasAsItem(this)) )
			throw new IllegalArgumentException();
		this.world = world;
	}
	
	
	/**
	 * Variable registering whether or not this item is terminated.
	 */
	private boolean isTerminated = false;
	
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
	
	
	
	
}
