package hillbillies.model;


import be.kuleuven.cs.som.annotate.*;

import java.util.*;


/**
 * A class of units as a kind of nits.
 * 
 *
 *@author Ruben Cartuyvels
 *@version	2.2
 *
 */
public class Unit extends Nit {
	
	/**
	 * Initialize a new unit with the given attributes, not yet attached to
	 * a world or a faction.
	 * 
	 * @param name
	 *            The name of the unit.
	 * @param initialPosition
	 *            The initial position of the unit.
	 * @param weight
	 *            The initial weight of the unit
	 * @param agility
	 *            The initial agility of the unit
	 * @param strength
	 *            The initial strength of the unit
	 * @param toughness
	 *            The initial toughness of the unit
	 * @param enableDefaultBehavior
	 *            Whether the default behavior of the unit is enabled
	 * 
	 * @effect	the new unit is initialized as a nit with the given name, position, weight,
	 * 			agility, strength, toughness and default behavior. 
	 * 
	 * @throws 	IllegalPositionException(position, this)
	 *             The unit cannot have the given position (out of bounds).
	 *             | ! canHaveAsPosition(position)
	 *             
	 * @throws 	IllegalNameException(name, this)
	 *             The unit cannot have the given name.
	 *             | ! canHaveAsName(name)            
	 */
	@Raw @Deprecated
	public Unit (String name, Coordinate initialPosition, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) 
					throws IllegalPositionException, IllegalNameException {

		super(name, initialPosition, weight, agility, strength, toughness, enableDefaultBehavior);
	}
	
	
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
	 * @effect	the unit is initialized as a nit with the given world, faction and 
	 * 			default behavior, with random attributes 
	 * 			and a random name, with an orientation and an empty state, not carrying an item.
	 * 			| super(world, faction, enableDefaultBehavior)
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
	public Unit (World world, Faction faction, boolean enableDefaultBehavior) 
					throws IllegalPositionException, IllegalNameException {
		
		super(world, faction, enableDefaultBehavior);

	}
	
	
	/* *********************************************************
	 * 
	 * 				ATTRIBUTES, POSITION, ORIENTATION
	 *
	 **********************************************************/

	
	
	/**
	 * Check whether the unit would fall if its position were the given position.
	 * 
	 * @param	position
	 * 			The position to be checked.
	 * 
	 * @return	true if and only if the given position is not neighbouring a solid cube
	 * 			and is not above a solid cube.
	 * 			|	result == ! (   getWorld().isNeighbouringSolid(position) 
	 * 			|			|| getWorld().isAboveSolid(position)   )
	 * 
	 * @throws	IllegalPositionException()
	 * 			the unit can not have the given position as its position.
	 * 			| !canHaveAsPosition(position)
	 */
	@Model
	public boolean wouldFall(Coordinate position) {
		if (!canHaveAsPosition(position))
			throw new IllegalPositionException(position);
		return ( !(getWorld().isNeighbouringSolid(position) || getWorld().isAboveSolid(position)));
	}
	

	
	/**
	 * Check whether the given name is a valid name for a unit.
	 * 
	 * @param 	name
	 * 			The name to be checked.
	 * 
	 * @return 	True if and only if the name is effective, if its
	 * 			length is larger than 1, if the first character is an uppercase
	 * 			letter and if it only exists of lowercase or uppercase
	 * 			letters, single or double quotes and spaces.
	 * 			| result == ( (name != null)
	 * 			|		&& (name.length() > 1)
	 * 			| 		&& (name.matches("[a-zA-Z\\s\'\"]+"))
	 */
	@Override @Raw
	public boolean canHaveAsName(String name) {
		return (name != null && name.length() > 1 
				&& Character.isUpperCase(name.charAt(0)) 
				&& name.matches("[a-zA-Z\\s\'\"]+"));
	}
	
	
	/**
	 * Return a name.
	 * 
	 * @return 	The unit can have the generated name as its name.
	 * 			| canHaveAsName(result)
	 */
	@Override @Model
	protected String generateName() {
		return "Tom Hagen";
	}


	
	/**
	 * Return the units maximum number of hitpoints.
	 * 
	 * @return 	The maximum no. of hitpoints of the unit, which is equal to
	 * 			200 times the product of the weight divided by 100 and the toughness
	 * 			divided by 100, rounded up to the next integer.
	 * 			| result == Math.ceil(200*(getWeight()/100.0)*
	 * 			|	(getToughness()/100.0))
	 */
	@Raw @Override
	public int getMaxHitPoints() {
		return (int) Math.ceil(
				200*(getWeight()/100.0)*(getToughness()/100.0));
	}
	

	/**
	 * Return the units maximum number of stamina points.
	 * 
	 * @return 	The maximum no. of stamina points of the unit, which is equal to
	 * 			200 times the product of the weight divided by 100 and the toughness
	 * 			divided by 100, rounded up to the next integer.
	 * 			| result == Math.ceil(200*(getWeight()/100.0)*
	 * 			|	(getToughness()/100.0))
	 * 
	 */
	@Raw @Override
	public int getMaxStaminaPoints() {
		return (int) Math.ceil(
				200*(getWeight()/100.0)*(getToughness()/100.0));
	}
	
	

	
	
	/* *********************************************************
	 * 
	 * 							ACTIVITIES
	 *
	 **********************************************************/
	
	

	/**
	 * Advance the game time and manage activities of the unit.
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
		
		if (!isNeighbouringSolid(getCoordinate()) && !isFalling())
			fall();

		if (isFalling()) {
			controlFalling(dt);
		}
		
		super.advanceTime(dt);
	}
	
	
	/**
	 * Check whether the unit is falling.
	 * 
	 * @return	True if and only if the unit is falling.
	 * 			| result == (getState() == State.FALLING) 
	 */
	@Basic @Raw
	public boolean isFalling() {
		return (getState() == State.FALLING);
	}
	
	
	
	/**
	 * Make the unit fall, if it's not already falling.
	 * 
	 * @effect	if the unit was not already falling the cube 
	 * 			where the unit started falling is set to its current cube,
	 * 			| if (!isFalling())
	 * 			| 	then setStartFallingCube(getCoordinate())
	 * 			its position is set to the center of the cube it is currently occupying,
	 * 			|		setPosition(getCoordinate());
	 * 			and the units state is set to falling.
	 * 			| 		setState(State.FALLING)
	 */
	private void fall() {
		if (!isFalling()) {
			setStartFallingCube(getCoordinate());
			setPosition(getCoordinate());
			setState(State.FALLING);
		}
	}
	
	
	/**
	 * Manage the falling of the unit, i.e. when the state of the unit is equal to falling.
	 * 
	 * @param 	dt
	 * 			the game time interval in which to manage the falling behavior.
	 * 
	 * @effect	if the units position is above a solid cube or at the bottom of
	 * 			the game world, the unit stops falling, i.e. its state is set
	 * 			to empty,
	 * 			| if (isAboveSolid(getCoordinate()) )
	 * 			| 	then setState(State.EMPTY)
	 * 			it loses 10 HP for each Z-level it fell,
	 * 			|		updateCurrentHitPoints(getCurrentHitPoints() - 
	 * 			|				10*(getStartFallingCube().get(2) - getCoordinate().get(2))
	 * 			its field for registering the cube where it started
	 * 			falling is set to null again,
	 * 			| 		setStartFallingCube()
	 * 			and the position of the unit is set to the middle of its current
	 * 			cube for clarity.
	 * 			| 		setPosition(getCoordinate())
	 * 
	 * @effect	if the units position is NOT above a solid cube or at the bottom of
	 * 			the game world, its position is updated.
	 * 			| else
	 * 			|	then updatePosition(dt)
	 * 			
	 */
	private void controlFalling(double dt) throws IllegalPositionException {
		if(isAboveSolid(getCoordinate()) ) {
			
			int lostHP = 10*(getStartFallingCube().get(2) - getCoordinate().get(2));
			setStartFallingCube();
			updateCurrentHitPoints(getCurrentHitPoints() - lostHP);
			setState(State.EMPTY);
			setPosition(getCoordinate());
			
		}
		else {
			updatePosition(dt);
			
		}
	}
	
	
	/**
	 * Returns the cube where the unit started falling. 
	 */
	@Basic
	protected Coordinate getStartFallingCube() {
		return this.startFallingCube;
	}
	
	
	/**
	 * Set the cube where the unit starts falling, to later compute the amount
	 * of HP lost.
	 * 
	 * @param 	coordinate
	 * 			The cube where the unit starts falling.
	 * 
	 * @post	| new.getStartFallingCube() == coordinate
	 */
	@Raw
	protected void setStartFallingCube(Coordinate coordinate) {
		this.startFallingCube = coordinate;
	}
	
	
	/**
	 * Set the cube where the unit starts falling to null.
	 * 
	 * @post	| new.getStartFallingCube() == null
	 */
	protected void setStartFallingCube() {
		this.startFallingCube = null;
	}
	
	
	/**
	 * Variable registering the cube where the unit started falling,
	 * if any.
	 */
	private Coordinate startFallingCube = null;
	
	
	
	
	/**
	 * Get a random cube that is reachable from the units current position.
	 *  
	 * @return	A random reachable cube coordinate.
	 * 			| isReachable(result)
	 */
	@Override
	protected Coordinate getRandomReachableCube() {
		Coordinate cube;
		do {
			cube = getWorld().getNearRandomNeighbouringSolidCube(getCoordinate());
		} while (!isReachable(cube));
		return cube;
	}
	
	
	/**
	 * Adds neighbours of a given cube in the given queue to the queue, with an
	 * incremented weight, if they feature a passable terraintype, if they 
	 * neighbour a solid cube, and if the queue not already contains that 
	 * neighbour with a smaller weight.
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
					
					&& isNeighbouringSolid(neighbour)
										
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
	 * Check if the unit can currently move.
	 * 
	 * @return	true if and only if the unit is not attacked, not in its initial
	 * 			resting state, and not falling.
	 * 			|	result == (!isAttacked() && getState() != State.RESTING_1 && !isFalling() )
	 */
	@Override @Model
	protected boolean canMove() {
		return (!isAttacked() && getState() != State.RESTING_1 && !isFalling());
	}
	

	/**
	 * Returns the units velocity.
	 * 
	 * @return	The units velocity vector when the unit is falling.
	 * 			| if (isFalling())
	 * 			| 	then result == {0.0, 0.0, -3.0}
	 * 
	 * @return	The units current velocity vector, when the unit is not falling,
	 * 			which is equal to its speed multiplied by
	 * 			the difference of its target position coordinates and its current
	 * 			position coordinates, divided by the distance to the destination.
	 * 			| if (!isFalling())
	 * 			| 	then result == getCurrentSpeed()*(World.getCubeCenter(getDestination()) 
	 * 			|					- getPosition() )
	 * 			|		/ getDistanceTo(getPosition(), World.getCubeCenter(getDestination()))
	 */
	@Model @Override
	protected double[] getVelocity() throws IllegalPositionException {
		if (isFalling())
			return new double[]{0.0,0.0,-3.0};
		else
			return super.getVelocity();
	}

	
	/**
	 * Check if the unit can currently start sprinting.
	 * 
	 * @return	true if and only if the unit is moving, its stamina points
	 * 			are above the minimum level and if it is not falling.
	 * 			| result == (isMoving() && getCurrentStaminaPoints() > 0 && !isFalling())
	 */
	@Override @Model
	protected boolean canSprint() {
		return (isMoving() && getCurrentStaminaPoints() > 0 && !isFalling());
	}
	

	
	/**
	 * Makes the unit work.
	 * 
	 * @effect	if the unit is not moving or falling and not in its initial
	 * 			resting state, the unit will start working.
	 * 			| if (!isMoving() && getState() != State.RESTING_1 && !isFalling())
	 * 			| 	then startWorking()
	 */
	@Deprecated
	public void work()  {
		if (!isMoving() && getState() != State.RESTING_1 && !isFalling()) {
			startWorking();
		}
	}
	
	
	/**
	 * Check if the unit can currently work.
	 * 
	 * @return	true if and only if the unit is not attacked, not in its initial
	 * 			resting state, not moving and not falling.
	 * 			|	result == (!isAttacked() && !isMoving() && getState() != State.RESTING_1
	 * 			|			 && !isFalling() )
	 */
	@Override @Model
	protected boolean canWork() {
		if (!isAttacked() && !isMoving() && getState() != State.RESTING_1 && !isFalling())
			return true;
		return false;
	}
	
	
	/**
	 * Check if the unit can currently execute an attack.
	 * 
	 * @return true if and only if the unit is not attacked, not in its initial
	 * 			resting state, not moving and not falling and not already attacking.
	 * 			|	result == (!isAttacked() && !isMoving() && getState() != State.RESTING_1
	 * 			|			 && !isFalling() && !isAttacking() )
	 */
	@Override @Model
	protected boolean canExecuteAttack() {
		if (!isMoving() && !isAttacked() && !isAttacking() && getState() != State.RESTING_1 && !isFalling())
			return true;
		return false;
	}
	
	
	/**
	 * Check if the unit can currently rest.
	 * 
	 * @return true if and only if the unit is not attacked, not in its initial
	 * 			resting state, not moving and not falling.
	 * 			|	result == (!isAttacked() && !isMoving() && getState() != State.RESTING_1
	 * 			|			 && !isFalling() )
	 */
	@Override @Model
	protected boolean canRest() {
		if (!isMoving() && !isAttacked() && getState() != State.RESTING_1 && !isFalling())
			return true;
		return false;
	}
	
	
	/**
	 * Checks if the given state is a valid state for this unit.
	 * 
	 * @param 	State
	 * 			The state to check.
	 * 
	 * @return	True if and only if the value class of states 
	 * 			contains the given state.
	 * 			| result == (State.contains(state))
	 */
	@Override @Model
	public boolean canHaveAsState(State state) {
		return State.contains(state);
	}
	
}