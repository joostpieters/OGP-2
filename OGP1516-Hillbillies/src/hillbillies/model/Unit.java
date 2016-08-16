package hillbillies.model;


import be.kuleuven.cs.som.annotate.*;

import java.util.*;


/**
 * A class of units as a kind of nits.
 * 
 * @invar	The position of a unit must always be valid, within the game world.
 * 			| canHaveAsPosition(getCoordinate())
 * 
 * @invar	The unit can have its name as its name.
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
// TODO invars houden?
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
	 * @post  the new name of the unit is equal to the given name.
	 * 		| new.getName() == name
	 * 
	 * @post the new position of the unit is equal to the given position.
	 * 		| new.getPosition() == position
	 * 
	 * @post if the given weight is valid, the new weight will be
	 * 			equal to the given weight.
	 * 		| if (canHaveAsWeight(weight))
	 * 		| then new.getWeight() == weight
	 * 
	 * @post if the given strength is valid, the new strength will be
	 * 			equal to the given strength.
	 * 		| if (isValidStrength(strength))
	 * 		| then new.getStrength() == strength
	 * 
	 * @post if the given agility is valid, the new agility will be
	 * 			equal to the given agility.
	 * 		| if (isValidAgility(agility))
	 * 		| then new.getAgility() == agility
	 * 
	 * @post if the given toughness is valid, the new toughness will be
	 * 			equal to the given toughness.
	 * 		| if (isValidToughness(toughness))
	 * 		| then new.getToughness() == toughness
	 * 
	 * @post The new orientation of the unit is equal to Pi/2.
	 * 		| new.getOrientation() == Math.PI/2
	 * 
	 * @post The new current number of hitpoints of the unit is equal to the max number
	 * 			of hitpoints for the unit.
	 * 		| new.getCurrentHitPoints() == new.getMaxHitPoints()
	 * 
	 * @post The new current number of stamina points of the unit is equal to the max number
	 * 			of stamina points for the unit.
	 * 		| new.getCurrentStaminaPoints() == new.getMaxStaminaPoints()
	 * 
	 * @post The default behavior of the unit is equal to the given value for default behavior.
	 * 		| new.isDefaultBehaviorEnabled() == enableDefaultBehavior
	 * 
	 * 
	 * @throws IllegalPositionException(position, this)
	 *             The unit cannot have the given position (out of bounds).
	 *             | ! canHaveAsPosition(position)
	 *             
	 * @throws IllegalNameException(name, this)
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
	 * Check whether the unit can have the given position as its position in the game world.
	 * 
	 * @param	position
	 * 			The position to be checked.
	 * 
	 * @return	
	 */
	@Override @Raw @Model
	public boolean canHaveAsPosition(Coordinate position) {
		boolean value = super.canHaveAsPosition(position);
		return (value /*&& getWorld().isNeighbouringSolid(position)*/);
	}
	


	
	/**
	 * Check whether the given name is a valid name for a unit.
	 * 
	 * @param name
	 * 			The name to be checked.
	 * 
	 * @return True if and only if the name is effective, if its
	 * 			length is larger than 1, if the first character is an uppercase
	 * 			letter and if it only exists of lowercase or uppercase
	 * 			letters, single or double quotes and spaces.
	 * 		| result == ( (name != null)
	 * 		|		&& (name.length() > 1)
	 * 		| 		&& (name.matches("[a-zA-Z\\s\'\"]+"))
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
	 * @return	a String to give to a unit as name.
	 */
	@Override
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
	 * 			
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
			/*double[] newPosition = new double[3];
			for (int i=0; i<3; i++) {
				newPosition[i] = getPosition()[i] + getVelocity()[i]*dt;
			}
			this.position[0] = position[0];
			this.position[1] = position[1];
			this.position[2] = position[2];*/
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
	 * @post	| getStartFallingCube() == coordinate
	 */
	@Raw
	protected void setStartFallingCube(Coordinate coordinate) {
		this.startFallingCube = coordinate;
	}
	
	
	/**
	 * Set the cube where the unit starts falling to null.
	 * 
	 * @post	| getStartFallingCube() == null
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
	 * 			| do ( cube = getWorld().getRandomNeighbouringSolidCube() )
	 * 			| while (!isReachable(cube)
	 * 			| result == cube
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
	 * Manage the moving of the unit, i.e. when the state of the unit is equal to moving.
	 * 
	 * TODO Documentation
	 */
	@Override
	protected void controlMoving(double dt) throws IllegalPositionException, 
				ArithmeticException {
		
		if (!isNeighbouringSolid(getCoordinate()))
			fall();
		
		else  {
			super.controlMoving(dt);

		}

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
	 * Makes the unit start sprinting.
	 * 
	 * @post	If the unit is moving, if it has enough stamina points and if it
	 * 			is not falling, its sprinting field is set to true.
	 * 			| if (isMoving() && getCurrentStaminaPoints() > 0 && !isFalling())
	 * 			| 	then new.isSprinting() == true
	 */
	@Override
	public void startSprinting() {
		if (!isFalling()) {
			super.startSprinting();
		}
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
	 * Makes the unit work at a given target cube.
	 * 
	 * @effect	if the unit is not moving or falling and not in its initial
	 * 			resting state, the units target cube will be set to the given
	 * 			target cube.
	 * 			| if (!isMoving() && getState() != State.RESTING_1 && !isFalling())
	 * 			| 	then setTargetCube(targetCube)
	 * 
	 * @effect	if the unit is not moving or falling and not in its initial
	 * 			resting state, the unit will start working
	 * 			| if (!isMoving() && getState() != State.RESTING_1 && !isFalling())
	 * 			| 	then startWorking()
	 * 
	 * @effect	if the unit is not moving or falling and not in its initial
	 * 			resting state, its orientation is set towards the target cube.
	 * 			| if (!isMoving() && getState() != State.RESTING_1 && !isFalling())
	 * 			| 	then setOrientation( (float) Math.atan2(getPosition()[1] 
	 * 			|					- World.getCubeCenter(getTargetCube())[1],
	 *			|		targetPosition[0] - World.getCubeCenter(getTargetCube())()[0]))
	 * 
	 * @throws	IllegalTargetException
	 * 			the unit can not have the given target cube as its target.
	 * 			| !canHaveAsTargetCube(targetCube)
	 */
	// TODO all documentation necessary?
	@Override
	public void workAt(Coordinate targetCube) throws IllegalTargetException {
		
		if ( !isFalling()) {
			super.workAt(targetCube);
		}
	}

	
	
	/**
	 * Attack the given nit.
	 * 
	 * @param 	defender
	 * 			The nit to attack.
	 * 
	 * @effect	If the unit is not currently moving or falling, it starts attacking.
	 * 			| startAttacking()
	 * 
	 * @post	If the unit is not currently moving or falling, this units defender field 
	 * 			is set to the given other nit.
	 * 			| new.getDefender() == defender
	 * 
	 * @effect	If the unit is not currently moving or falling, the nit this unit is attacking
	 * 			its is attacked field is set to true.
	 * 			| getDefender().setAttacked(true)
	 * 
	 * @post	If the unit is not currently moving or falling, its orientation is set towards
	 * 			the attacked nit.
	 * 			| new.getOrientation() == arctangent(defender.getPosition()[1]-this.getPosition()[1],
	 * 			|	defender.getPosition()[0]-this.getPosition()[0]))
	 * 
	 * @post	If the unit is not currently moving or falling, the attacked nits orientation is set
	 * 			towards this unit, i.e. the attacking unit.
	 * 			| (new defender).getOrientation() == arctangent(this.getPosition()[1]-
	 * 			|	defender.getPosition()[1], this.getPosition()[0]-defender.getPosition()[0]))
	 *  
	 * @throws 	IllegalVictimException
	 * 			The unit cannot attack the given other nit.
	 * 			| !canAttack(defender)
	 */
	@Override
	public void attack(Nit defender) throws IllegalTimeException, IllegalVictimException {
		if (!canAttack(defender))
			throw new IllegalVictimException(this, defender);

		if (!isFalling() ) {
			super.attack(defender);
		}
	}
	
	
	/**
	 * Returns true if the unit can attack another given nit.
	 * 
	 * @param	victim
	 * 			The nit to check.
	 * 
	 * @return	True if and only if the given nit occupies the same
	 * 			or a neighboring cube of the game world, and if the given
	 * 			nit is not falling and it does not belong to the same faction as this
	 * 			unit.
	 * 			| result == (  ( getWorld().isNeighbouring(getCoordinate(), victim.getCoordinate())
	 * 			|		|| getCoordinate().equals(victim.getCoordinate())  )
	 * 			|		&& !victim.isFalling() && getFaction() != victim.getFaction()  )
	 */
	// TODO formal documentation return tag
	@Model @Override
	protected boolean canAttack(Nit victim) {
		if (victim instanceof Unit && ((Unit) victim).isFalling())
			return false;
		return super.canAttack(victim);
	}
	
	
	
	/**
	 * Make the unit rest, if it's not currently moving, falling or being attacked.
	 * 
	 * @effect	If the unit is not currently moving, falling or being attacked, it starts
	 * 			resting.
	 * 			| if ( !isMoving && !isFalling() && !isAttacked() )
	 * 			| 	then startResting()
	 */
	@Override
	public void rest() throws IllegalTimeException {
		if (!isFalling()) {
			super.rest();
		}
	}

	
}