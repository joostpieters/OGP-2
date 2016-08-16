package hillbillies.model;

import java.util.LinkedList;
import java.util.List;

import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;


/**
 * A class of enits as a kind of nits.
 * 
 * 
 *@author Ruben Cartuyvels
 *@version	2.2
 *
 */
public class Enit extends Nit {
	
	
	/**
	 * Initialize a new enit with the given world as its world, the given default
	 * behavior and the given faction as its faction, with random attributes 
	 * and a random name, with an orientation and an empty state, not carrying an item.
	 * 
	 * @param 	enableDefaultBehavior
	 *        	Whether the default behavior of the enit is enabled.
	 * 
	 * @param	world
	 * 			The world for this new enit.
	 * 
	 * @param	faction
	 * 			The faction for this new enit.
	 * 
	 * @effect	the enit is initialized as a nit with the given world, faction and 
	 * 			default behavior, with random attributes 
	 * 			and a random name, with an orientation and an empty state, not carrying an item.
	 * 			| super(world, faction, enableDefaultBehavior)
	 * 
	 * @throws	IllegalPositionException(position, this)
	 *         	The enit cannot have the given position.
	 *          | ! canHaveAsPosition(position)
	 *             
	 * @throws	IllegalNameException(name, this)
	 *          The enit cannot have the given name.
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
	 * @param 	name
	 * 			The name to be checked.
	 * 
	 * @return 	True if and only if the name is effective, if its
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
	
	
	/**
	 * Return a name.
	 * 
	 * @return 	The enit can have the generated name as its name.
	 * 			| canHaveAsName(result)
	 */
	@Override @Model
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
	 * 			| isReachable(result)
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
	
	
	/**
	 * Check if the enit can currently start sprinting.
	 * 
	 * @return	true if and only if the enit is moving and its stamina points
	 * 			are above the minimum level.
	 * 			| result == (isMoving() && getCurrentStaminaPoints() > 0 )
	 */
	@Override @Model
	protected boolean canSprint() {
		return (isMoving() && getCurrentStaminaPoints() > 0);
	}
	
	
	
	/**
	 * Check if the enit can currently work.
	 * 
	 * @return	true if and only if the enit is not attacked, not in its initial
	 * 			resting state and not moving.
	 * 			|	result == (!isAttacked() && !isMoving() && getState() != State.RESTING_1 )
	 */
	@Override @Model
	protected boolean canWork() {
		if (!isMoving() && getState() != State.RESTING_1)
			return true;
		return false;
	}
	
	
	/**
	 * Check if the enit can currently move.
	 * 
	 * @return	true if and only if the enit is not attacked and not in its initial
	 * 			resting state.
	 * 			|	result == (!isAttacked() && getState() != State.RESTING_1 )
	 */
	@Override @Model
	protected boolean canMove() {
		return (!isAttacked() && getState() != State.RESTING_1);
	}
	
	
	
	/**
	 * Check if the enit can currently execute an attack.
	 * 
	 * @return true if and only if the enit is not attacked, not in its initial
	 * 			resting state, not moving and not already attacking.
	 * 			|	result == (!isAttacked() && !isMoving() && getState() != State.RESTING_1
	 * 			|			&& !isAttacking() )
	 */
	@Override @Model
	protected boolean canExecuteAttack() {
		if (!isMoving() && !isAttacked() && !isAttacking() && getState() != State.RESTING_1)
			return true;
		return false;
	}
	
	
	
	/**
	 * Check if the unit can currently rest.
	 * 
	 * @return true if and only if the unit is not attacked, not in its initial
	 * 			resting state and not moving.
	 * 			|	result == (!isAttacked() && !isMoving() && getState() != State.RESTING_1)
	 */
	@Override @Model
	protected boolean canRest() {
		if (!isMoving() && !isAttacked() && getState() != State.RESTING_1)
			return true;
		return false;
	}
	
	
	
	
	/**
	 * Checks if the given state is a valid state for this enit.
	 * 
	 * @param 	State
	 * 			The state to check.
	 * 
	 * @return	True if and only if the value class of states 
	 * 			contains the given state and if the state is not falling
	 * 			| result == (State.contains(state)
	 * 			|		&& !state.equals(State.FALLING))
	 */
	@Override @Model
	public boolean canHaveAsState(State state) {
		return (State.contains(state) && !state.equals(State.FALLING));
	}
	
	
}
