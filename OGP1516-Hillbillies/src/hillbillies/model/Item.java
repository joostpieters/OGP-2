package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
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
public abstract class Item extends GameObject {
	
	/**
	 * Initialize this new item.
	 */
	@Raw @Model
	protected Item(Coordinate initialPosition, World world) throws IllegalPositionException,
															IllegalArgumentException {
		
		// TODO
		super(world, initialPosition);
			
		int weight = random.nextInt(41) + 10;
		if (!isValidWeight(weight))
			throw new IllegalArgumentException();
		this.weight = weight;
		
	}
	
	
	@Override
	public void advanceTime (double dt) {
		if (getWorld() != null) {
			if (! isValidDT(dt)) {
				throw new IllegalArgumentException();
			}
			if (!isAboveSolid(getCoordinate()))
				fall();

			if (isFalling()) {
				controlFalling(dt);
			}
		}
	}
	
	
	
	public boolean isFalling() {
		return this.isFalling;
	}
	
	public void setFalling(boolean value) {
		this.isFalling = value;
	}
	
	
	private boolean isFalling = false;
	
	
	
	private void fall() {
		if (!isFalling()) {
			setPosition(World.getCubeCenter(getCoordinate()));
			setFalling(true);
		}
	}
	
	
	private void controlFalling(double dt) throws IllegalPositionException {
		if(isAboveSolid(getCoordinate())) {
			setFalling(false);
			
		}
		else {
			updatePosition(dt);
		}
	}
	
	
	@Model @Override
	protected double[] getVelocity() {
		if (isFalling())
			return new double[]{0.0,0.0,-3.0};
		else
			return super.getVelocity();
	}
	
		
	/**
	 * Return the items weight.
	 */
	@Basic @Raw @Immutable
	public int getWeight() {
		return this.weight;
	}
	
	
	
	
	private static int getMinimumWeight() {
		return Item.minWeight;
	}
	
	private static int getMaximumWeight() {
		return Item.maxWeight;
	}
	
	public static boolean isValidWeight(int value) {
		return (value >= getMinimumWeight() && value <= getMaximumWeight());
	}
	
	private final int weight;
	private static final int minWeight = 10;
	private static final int maxWeight = 50;
	
	
	
}
