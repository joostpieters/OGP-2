package hillbillies.model;

import java.util.LinkedList;
import java.util.List;

import be.kuleuven.cs.som.annotate.Raw;


/**
 * A class of enits as a kind of nits.
 * 
 * @invar	The position of a unit must always be valid, within the game world.
 * 			| canHaveAsPosition(getCoordinate())
 * 
 * @invar	The enit can have its name as its name.
 * 			| canHaveAsName(getName())
 * 
 * @invar 	The current number of hitpoints must always be valid for this unit.
 * 			| canHaveAsHitPoints(getCurrentHitPoints())
 * 
 * @invar 	The current number of stamina points must always be valid for this unit.
 * 			| canHaveAsStaminaPoints(getCurrentStaminaPoints())
 * 
 * @invar 	The unit can always have its long term destination cube as its position.
 * 			| canHaveAsPosition(getDestCubeLT())
 * 
 * @invar 	The unit can always have its destination cube as its position.
 * 			| canHaveAsPosition(getDestination())
 * 
 * @invar 	The unit can attack the unit it is attacking.
 * 			| canAttack(getDefender())
 * 
 *@author Ruben Cartuyvels
 *@version	2.2
 *
 */
public class Enit extends Nit {
	
	
	/**
	 * Initialize a new unit with the given world as its world, the given default
	 * behavior and the given faction as its faction, with random attributes 
	 * and a random name, with an orientation and an empty state, not carrying an item.
	 * 
	 * @param 	enableDefaultBehavior
	 *        	Whether the default behavior of the unit is enabled.
	 * 
	 * @param	world
	 * 			The world for this new unit.
	 * 
	 * @param	faction
	 * 			The faction for this new unit.
	 * 
	 * @effect	The units world is set to the given world
	 * 			| setWorld(world)
	 * 
	 * @effect	The units position is set to a random position in its world.
	 * 			| setPosition(getWorld().getRandomNeighbouringSolidCube())
	 * 
	 * @effect	The units name is set to a random name.
	 * 			| setName(generateName())
	 * 
	 * @effect	The units agility is set to a random initial agility.
	 * 			| setAgility(generateInitialSkill())
	 * 
	 * @effect	The units strength is set to a random initial strength.
	 * 			| setStrength(generateInitialSkill())
	 * 
	 * @effect	The units toughness is set to a random initial toughness.
	 * 			| setToughness(generateInitialSkill())
	 * 
	 * @effect	The units weight is set to a random initial weight.
	 * 			| setWeight(generateInitialWeight())
	 * 
	 * @effect	The units HP are set to the current maximum.
	 * 			| updateCurrentHitPoints(getMaxHitPoints())
	 * 
	 * @effect	The units stamina points are set to the current maximum.
	 * 			| updateCurrentStaminaPoints(getMaxStaminaPoints())
	 * 
	 * @effect	The units orientation is set to an initial value.
	 * 			| setOrientation((Math.PI/2.0))
	 * 
	 * @effect	The units state is set to empty.
	 * 			| setState(State.EMPTY)
	 * 
	 * @effect	The default behavior of the unit is set to the given
	 * 			default behavior.
	 * 			| setDefaultBehavior(enableDefaultBehavior)
	 * 
	 * @throws	IllegalPositionException(position, this)
	 *         	The unit cannot have the given position.
	 *          | ! canHaveAsPosition(position)
	 *             
	 * @throws	IllegalNameException(name, this)
	 *          The unit cannot have the given name.
	 *          | ! isValidName(name)            
	 */
	@Raw
	public Enit (World world, Faction faction, boolean enableDefaultBehavior) 
					throws IllegalPositionException, IllegalNameException {
		
		super(world, faction, enableDefaultBehavior);
		
		
	}
	
	

	
	/* *********************************************************
	 * 
	 * 				ATTRIBUTES, POSITION, ORIENTATION
	 *
	 **********************************************************/

	

	/**
	 * Check whether the given name is a valid name for an enit.
	 * 
	 * @param name
	 * 			The name to be checked.
	 * 
	 * @return True if and only if the name is effective, if its
	 * 			length is larger than 1, if the first character is an uppercase
	 * 			letter and if it only exists of lowercase or uppercase
	 * 			letters.
	 * 		| result == ( (name != null)
	 * 		|		&& (name.length() > 1)
	 * 		| 		&& (name.matches("[a-zA-Z]+"))
	 */
	@Override @Raw
	public boolean canHaveAsName(String name) {
		return (name != null && name.length() > 1 
				&& Character.isUpperCase(name.charAt(0)) 
				&& name.matches("[a-zA-Z]+"));
	}
	
	
	@Override
	protected String generateName() {
		return "Tom";
	}
	
	
	/**
	 * Return the enits maximum number of hitpoints.
	 * 
	 * @return 	The maximum no. of hitpoints of the enit, which is equal to
	 * 			the sum of its weight and strength.
	 * 			| result == getWeight() + getStrength()
	 */
	@Raw @Override
	public int getMaxHitPoints() {
		return getWeight() + getStrength();
	}
	

	/**
	 * Return the enits maximum number of stamina points.
	 * 
	 * @return 	The maximum no. of stamina points of the enit, which is equal to
	 * 			the sum of its weight and strength.
	 * 			| result == getWeight() + getStrength()
	 * 
	 */
	@Raw @Override
	public int getMaxStaminaPoints() {
		return getWeight() + getStrength();
	}
	
	
	
	
	/* *********************************************************
	 * 
	 * 							ACTIVITIES
	 *
	 **********************************************************/
	
	
	
	
	/**
	 * Advance the game time and manage activities of the enit.
	 * 
	 * @param 	dt
	 * 			The amount by which the game time has to be advanced
	 * 
	 * @throws IllegalArgumentException
	 * 			The value for dt is not valid.
	 * 			| ! isValidDT(dt)
	 */
	@Override
	public void advanceTime(double dt) throws IllegalPositionException, 
				IllegalArgumentException, IllegalTimeException, ArithmeticException {
		if (! isValidDT(dt)) {
			throw new IllegalArgumentException();
		}
		if (getCurrentHitPoints() <= 0) {
			terminate();
		}
		
		super.advanceTime(dt);
		
	}
	
	
	/**
	 * Get a random cube that is reachable from the enits current position.
	 *  
	 * @return	A random reachable cube coordinate.
	 * 			| do ( cube = getWorld().getNearRandomCube() )
	 * 			| while (!isReachable(cube)
	 * 			| result == cube
	 */
	@Override
	protected Coordinate getRandomReachableCube() {
		Coordinate cube;
		do {
			cube = getWorld().getNearRandomCube(getCoordinate());
		} while (!isReachable(cube));
		return cube;
	}
	
	
	
	/**
	 * Adds neighbours of a given cube in the given queue to the queue, with an
	 * incremented weight, if they feature a passable terraintype,
	 * and if the queue not already contains that neighbour with a smaller weight.
	 * 
	 * @param 	currentTuple
	 * 			the tuple consisting of the cube to add the neighbours of, and its
	 * 			weight.
	 * 
	 * @param 	path
	 * 			the path queue to add the right neighbours to.
	 * 
	 * @return	the path is returned after the neighbours are added.
	 * 
	 */
	@Override
	protected List<Tuple> search(Tuple currentTuple, List<Tuple> path) {
		
		List<Coordinate> neighbours = new LinkedList<Coordinate>();
		
		for (Coordinate neighbour: getWorld().getNeighbours(currentTuple.cube)) {
			
			if (getWorld().getCubeTypeAt(neighbour).isPassable()
															
					&& ! (Tuple.containsCube(path, neighbour)
					
					&& Tuple.getCubeTuple(path, neighbour).n <= currentTuple.n )
					) {
				neighbours.add(neighbour);
			}
		}
		
		for (Coordinate neighbour: neighbours) {
			path.add(new Tuple(neighbour, currentTuple.n + 1));
		}
		
		return path;
	}
	
	

}
