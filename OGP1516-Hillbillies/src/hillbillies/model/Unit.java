package hillbillies.model;


/*import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Model;*/

import be.kuleuven.cs.som.annotate.*;
import hillbillies.model.World.TerrainType;
import ogp.framework.util.ModelException;

import java.util.*;
import java.lang.*;

//import java.util.Random;

/**
 * A class of units with a current position, a name, weight, strength, agility
 * and toughness, attached to a faction and a world.
 * 
 * @invar	The position of a unit must always be valid, within the game world.
 * 			| canHaveAsPosition(getPosition())
 * 
 * @invar	The name of a unit must always be a valid name.
 * 			| isValidName(getName())
 * 
 * @invar	The weight of a unit must always be a valid weight for that unit.
 * 			| canHaveAsWeight(getWeight()) 
 * 
 * @invar	The strength of a unit must always be a valid strength.
 * 			| isValidStrength(getStrength()) 
 * 
 * @invar	The agility of a unit must always be a valid agility.
 * 			| isValidAgility(getAgility()) 
 * 
 * @invar	The toughness of a unit must always be a valid toughness.
 * 			| isValidToughness(getToughness()) 
 * 
 * @invar 	The current number of hitpoints must always be valid for this unit.
 * 			| canHaveAsHitPoints(getCurrentHitPoints())
 * 
 * @invar 	The current number of stamina points must always be valid for this unit.
 * 			| canHaveAsStaminaPoints(getCurrentStaminaPoints())
 * 
 * @invar	The orientation of a unit must always be valid.
 * 			| isValidOrientation(getOrientation()) 
 * 
 * @invar	Each unit must have a proper world in which it belongs
 * 			| hasProperWorld()
 * 
 * @invar	Each unit must have a proper faction to which it belongs
 * 			| hasProperFaction()
 * 
 *@author Ruben Cartuyvels
 *@version	1.4
 */
public class Unit extends GameObject {
	
	/**
	 * Initialize a new unit with the given attributes, not yet attached to
	 * a world or a faction.
	 * 
	 * @param name
	 *            The name of the unit.
	 * @param initialPosition
	 *            The initial position of the unit, as an array with 3 elements
	 *            {x, y, z}.
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
	 * @post The new minimum number of hitpoints of the unit is equal to 0.
	 * 		| new.getMinHitPoints() == 0
	 * 
	 * @post The new minimum number of stamina points of the unit is equal to 0.
	 * 		| new.getMinStaminaPoints() == 0
	 * 
	 * @post The default behavior of the unit is equal to the given value for default behavior.
	 * 		| new.isDefaultBehaviorEnabled() == enableDefaultBehavior
	 * 
	 * @throws IllegalPositionException(position, this)
	 *             The unit cannot have the given position (out of bounds).
	 *             | ! canHaveAsPosition(position)
	 *             
	 * @throws IllegalNameException(name, this)
	 *             The unit cannot have the given name.
	 *             | ! isValidName(name)            
	 */
	@Raw
	public Unit (String name, int[] initialPosition, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) 
					throws IllegalPositionException, IllegalNameException {

		double[] doubleInitialPosition = convertPositionToDouble(initialPosition);
		
		for (int i=0; i<doubleInitialPosition.length; i++) {
			doubleInitialPosition[i] += 0.5;
		}
		setPosition(doubleInitialPosition);

		setName(name);
		
		// isValidINITIALAgility!!! not isValidAgility !!!!!!!!!!!!
		if (isValidAgility(agility))
			setAgility(agility);
		else setAgility(25);
		if (isValidStrength(strength)) 
			setStrength(strength);
		else setStrength(25);
		if (isValidToughness(toughness))
			setToughness(toughness);
		else setToughness(25);
		if (canHaveAsWeight(weight)) 
			setWeight(weight);
		else setWeight((getStrength()+getAgility())/2 +1);
		
		setInitialTotalWeight();

		this.MIN_HP = 0;
		this.MIN_SP = 0;
		
		updateCurrentHitPoints(getMaxHitPoints());
		updateCurrentStaminaPoints(getMaxStaminaPoints());

		setOrientation((float)(Math.PI/2.0));

		this.setDefaultBehaviorEnabled(enableDefaultBehavior);
		this.setState(State.EMPTY);
	}
	

	@Raw
	public Unit (String name, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) 
					throws IllegalPositionException, IllegalNameException {

		setName(name);
		
		// isValidINITIALAgility!!! not isValidAgility !!!!!!!!!!!!
		if (isValidAgility(agility))
			setAgility(agility);
		else setAgility(25);
		if (isValidStrength(strength)) 
			setStrength(strength);
		else setStrength(25);
		if (isValidToughness(toughness))
			setToughness(toughness);
		else setToughness(25);
		if (canHaveAsWeight(weight)) 
			setWeight(weight);
		else setWeight((getStrength()+getAgility())/2 +1);
		
		setInitialTotalWeight();

		this.MIN_HP = 0;
		this.MIN_SP = 0;
		
		updateCurrentHitPoints(getMaxHitPoints());
		updateCurrentStaminaPoints(getMaxStaminaPoints());

		setOrientation((float)(Math.PI/2.0));

		this.setDefaultBehaviorEnabled(enableDefaultBehavior);
		this.setState(State.EMPTY);
	}

	
	/**
	 * Check whether the given position is a valid position for this unit in its world.
	 * 
	 * @param position
	 * 			The position to be checked.
	 * 
	 * @return True if and only if the position is effective, if it
	 * 			consists of an array with 3 double elements, if each coordinate
	 * 			is between 0 and the upper limit of that dimension, if
	 * 			the terrain type of that world cube is passable and if the world cube
	 * 			is neighbour to a solid cube.
	 * 		| result = ( (position instanceof double[])
	 * 		|		&& (position.length == 3)
	 * 		| 		&& !(position[0] < 0.0 || position[0] >= getWorld().getNbCubesX())
	 * 		|		&& !(position[1] < 0.0 || position[1] >= getWorld().getNbCubesY())
	 * 		|		&& !(position[2] < 0.0 || position[2] >= getWorld().getNbCubesZ())
	 * 		|		&& TerrainType.byOrdinal(getWorld().getTerrainTypes()
	 * 		|				[intPosition[0]][intPosition[1]][intPosition[2]]).isPassable()
	 * 		|		&& isNeighbouringSolid(convertPositionToInt(position)) )
	 */
	protected boolean canHaveAsPosition(Coordinate position) {
		boolean value = super.canHaveAsPosition(position);
		if (!isNeighbouringSolid(position) ) {
			return false;
		}
		return value;
	}
	
	
	/**
	 * Check whether the given position is a valid position for a unit.
	 * 
	 * @param position
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
	/*@Model @Deprecated
	private static boolean canHaveAsPosition(double[] position) {
		//boolean valid = true;
		if (!(position instanceof double[]) || position.length != 3 )
			return false;
		for (double coordinate: position) {
			if (coordinate < 0.0 || coordinate >= 50.0)
				return false;
		}
		return true;
	}*/
	
	
	/**
	 * Set the position of the unit to the given position.
	 * 
	 * @param position
	 * 			The new position for this unit.
	 * 
	 * @post The new position of this unit is equal to the
	 * 		given position.
	 * 		| new.getPosition() == position
	 * 
	 * @throws IllegalPositionException(position, this)
	 * 			The given position is not valid.
	 * 		| !canHaveAsPosition(position)
	 */
	/*@Raw
	private void setPosition(double[] position) throws IllegalPositionException {
		if (! canHaveAsPosition(position) )
			throw new IllegalPositionException(position);
		this.position[0] = position[0];
		this.position[1] = position[1];
		this.position[2] = position[2];
	}*/

	
	/**
	 * Return the current name of the unit.
	 */
	@Basic @Raw
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the unit to the given name.
	 * 
	 * @param newName
	 * 			The new name for this unit.
	 * 
	 * @post The new name of this unit is equal to the
	 * 		given name.
	 * 		| new.getName() == newName
	 * 
	 * @throws IllegalNameException(newName, this)
	 * 			The given name is not valid.
	 * 		| !isValidName(newName)
	 */
	@Raw
	public void setName(String newName) throws IllegalNameException {
		if (! isValidName(newName))
			throw new IllegalNameException(newName, this);
		this.name = newName;
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
	 * 		| result = ( (name != null)
	 * 		|		&& (name.length() > 1)
	 * 		| 		&& (name.matches("[a-zA-Z\\s\'\"]+"))
	 */
	@Model
	private static boolean isValidName(String name) {
		return (name != null && name.length() > 1 
				&& Character.isUpperCase(name.charAt(0)) 
				&& name.matches("[a-zA-Z\\s\'\"]+"));
	}

	/**
	 * Variable registering the name of the unit.
	 */
	private String name;


	/**
	 * Return the units weight.
	 */
	@Basic @Raw
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 * Check whether the given weight is valid.
	 * 
	 * @param value
	 * 			The value to be checked.
	 * 
	 * @return True if and only if the value is an integer, larger
	 * 			than zero and smaller than 201, and at least the mean
	 * 			of the agility and the strength of the unit.
	 * 		| result = (Integer.class.isInstance(value) && value > 0 && value < 201 
	 * 		|	&& value >= (getStrength()+getAgility())/2 )
	 */
	@Raw
	private boolean canHaveAsWeight(int value) {
		return (Integer.class.isInstance(value) && value > 0 && value < 201 
				&& value >= (getStrength()+getAgility())/2 );
	}

	/**
	 * Set the weight of the unit to the given new value.
	 * 
	 * @param newValue
	 * 			The new weight for this unit.
	 * 
	 * @post if the given weight is valid for this unit, the new weight
	 * 			will be equal to the given weight.
	 * 		| if (canHaveAsWeight(newValue))
	 * 		| then new.getWeight() == newValue
	 */
	@Raw
	public void setWeight(int newValue) {
		if (canHaveAsWeight(newValue))
			this.weight = newValue;
	}

	/**
	 * Variable registering the weight of the unit.
	 */
	private int weight = 0;
	

	/**
	 * Return the units strength.
	 */
	@Basic @Raw
	public int getStrength() {
		return this.strength;
	}
	
	/**
	 * Check whether the given strength is valid.
	 * 
	 * @param value
	 * 			The value to be checked.
	 * 
	 * @return True if and only if the value is an integer, larger
	 * 			than zero and smaller than 201
	 * 		| result = (Integer.class.isInstance(value) && value > 0 && value < 201) 
	 */
	private static boolean isValidStrength(int value) {
		return (Integer.class.isInstance(value) && value > 0 && value < 201 );
	}

	/**
	 * Set the strength of the unit to the given new value.
	 * 
	 * @param newValue
	 * 			The new strength for this unit.
	 * 
	 * @post if the given strength is valid, the new strength will be
	 * 			equal to the given strength.
	 * 		| if (isValidStrength(newValue))
	 * 		| then new.getStrength() == newValue
	 */
	@Raw
	public void setStrength(int newValue) {
		if (isValidStrength(newValue))
			this.strength = newValue;
	}

	/**
	 * Variable registering the strength of the unit.
	 */
	private int strength;

	/**
	 * Return the units agility.
	 */
	@Basic @Raw
	public int getAgility() {
		return this.agility;
	}
	
	/**
	 * Check whether the given agility is valid.
	 * 
	 * @param value
	 * 			The value to be checked.
	 * 
	 * @return True if and only if the value is an integer, larger
	 * 			than zero and smaller than 201
	 * 		| result = (Integer.class.isInstance(value) && value > 0 && value < 201) 
	 */
	private static boolean isValidAgility(int value) {
		return (Integer.class.isInstance(value) && value > 0 && value < 201 );
	}
	
	
	/**
	 * Set the agility of the unit to the given new value.
	 * 
	 * @param newValue
	 * 			The new agility for this unit.
	 * 
	 * @post if the given agility is valid, then the new agility will be
	 * 			equal to the given agility.
	 * 		| if (isValidAgility(newValue))
	 * 		| then new.getAgility() == newValue
	 */
	@Raw
	public void setAgility(int newValue) {
		if (isValidAgility(newValue))
			this.agility = newValue;
	}

	/**
	 * Variable registering the agility of the unit.
	 */
	private int agility;

	/**
	 * Return the units toughness.
	 */
	@Basic @Raw
	public int getToughness() {
		return this.toughness;
	}
	
	
	/**
	 * Check whether the given toughness is valid.
	 * 
	 * @param value
	 * 			The value to be checked.
	 * 
	 * @return True if and only if the value is an integer, larger
	 * 			than zero and smaller than 201
	 * 		| result = (Integer.class.isInstance(value) && value > 0 && value < 201) 
	 */
	private static boolean isValidToughness(int value) {
		return (Integer.class.isInstance(value) && value > 0 && value < 201 );
	}
	
	
	/**
	 * Set the toughness of the unit to the given new value.
	 * 
	 * @param newValue
	 * 			The new toughness for this unit.
	 * 
	 * @post if the given toughness is valid, then the new
	 * 			toughness will be equal to the given toughness.
	 * 		| if (isInteger(newValue))
	 * 		| then new.getToughness() == newValue
	 */
	@Raw
	public void setToughness(int newValue) {
		if (isValidToughness(newValue))
			this.toughness = newValue;
	}

	/**
	 * Variable registering the toughness of the unit.
	 */
	private int toughness;


	/**
	 * Return the units maximum number of hitpoints.
	 * 
	 * @return 	The maximum no. of hitpoints of the unit, which is equal to
	 * 			200 times the product of the weight divided by 100 and the toughness
	 * 			divided by 100, rounded up to the next integer.
	 * 			| result = Math.ceil(200*(getWeight()/100.0)*
	 * 			|	(getToughness()/100.0));
	 */
	@Raw
	public int getMaxHitPoints() {
		return (int) Math.ceil(
				200*(getWeight()/100.0)*(getToughness()/100.0));
	}


	/**
	 * Return the units minimum number of hitpoints.
	 */
	@Basic @Immutable @Raw
	private int getMinHitPoints() {
		return this.MIN_HP;
	}

	/**
	 * Return the units current number of hitpoints.
	 */
	@Basic @Raw
	public int getCurrentHitPoints() {
		return this.currentHitPoints;
	}

	/**
	 * Check if a given value is a valid hitpoints value.
	 * 
	 * @param value
	 * 			The value to be checked.
	 * @return true if and only if the value is larger than or equal to the
	 * 			minimum and smaller than or equal to the maximum amount of
	 * 			hitpoints for this unit.
	 * 		| result =  (value >= getMinHitPoints() && value <= getMaxHitPoints() )
	 */
	@Raw
	private boolean canHaveAsHitPoints(int value) {
		return (value >= getMinHitPoints() && value <= getMaxHitPoints());
	}

	/**
	 * Update the current number of hitpoints of the unit.
	 * 
	 * @param newValue
	 * 			The new value for the current no. of hitpoints of the unit.
	 * 
	 * @pre	the given new value must be a valid hitpoints value for this unit.
	 * 		| canHaveAsHitPoints(newValue)
	 * 
	 * @post The new no. of hitpoints of the unit is equal to
	 * 		the given new value.
	 * 		| new.getCurrentHitPoints() == newValue
	 * 
	 */
	@Raw
	public void updateCurrentHitPoints(int newValue) {
		assert canHaveAsHitPoints(newValue);
		if (canHaveAsHitPoints(newValue))
			this.currentHitPoints = newValue;
	}

	/**
	 * Variable registering the current number of hitpoints of the unit.
	 */
	private int currentHitPoints;

	/**
	 * Variable registering the minimum number of hitpoints of the unit.
	 */
	private final int MIN_HP;


	/**
	 * Return the units maximum number of stamina points.
	 * 
	 * @return 	The maximum no. of stamina points of the unit, which is equal to
	 * 			200 times the product of the weight divided by 100 and the toughness
	 * 			divided by 100, rounded up to the next integer.
	 * 			| result = Math.ceil(200*(getWeight()/100.0)*
	 * 			|	(getToughness()/100.0));
	 * 
	 */
	@Raw
	public int getMaxStaminaPoints() {
		return (int) Math.ceil(
				200*(getWeight()/100.0)*(getToughness()/100.0));
	}

	/**
	 * Return the units minimum number of stamina points.
	 */
	@Basic @Immutable @Raw
	private int getMinStaminaPoints() {
		return this.MIN_SP;
	}


	/**
	 * Return the units current number of stamina points.
	 */
	@Basic @Raw
	public int getCurrentStaminaPoints() {
		return this.currentStaminaPoints;
	}

	/**
	 * Check if a given value is a valid stamina points value.
	 * 
	 * @param value
	 * 			The value to be checked.
	 * @return true if and only if the value is larger than or equal to
	 * 			the minimum and smaller than or equal to the maximum amount of
	 * 			stamina points for this unit.
	 * 		| result =  (value >= getMinStaminaPoints && 
	 * 		|		value <= getMaxStaminaPoints() )
	 */
	@Raw
	private boolean canHaveAsStaminaPoints(int value) {
		return (value >= getMinStaminaPoints() && value <= getMaxStaminaPoints());
	}

	/**
	 * Update the current number of stamina points of the unit.
	 * 
	 * @param newValue
	 * 			The new value for the current no. of stamina points of the unit.
	 * 
	 * @pre	the given new value must be a valid stamina points value for this unit.
	 * 		| canHaveAsStaminaPoints(newValue)
	 * 
	 * @post The new no. of stamina points of the unit is equal to
	 * 		the given new value.
	 * 		| new.getCurrentStaminaPoints() == newValue;
	 * 
	 */
	@Raw
	public void updateCurrentStaminaPoints(int newValue) {
		assert canHaveAsStaminaPoints(newValue);
		if (canHaveAsStaminaPoints(newValue))
			this.currentStaminaPoints = newValue;
	}

	/**
	 * Variable registering the current number of stamina points of the unit.
	 */
	private int currentStaminaPoints;

	/**
	 * Variable registering the minimum number of stamina points of the unit.
	 */
	private final int MIN_SP;


	/**
	 * Advance the game time and manage activities of the unit.
	 * 
	 * @param dt
	 * 			The amount by which the game time has to be advanced
	 * 
	 * @throws IllegalArgumentException
	 * 			The value for dt is not valid.
	 * 			| ! isValidDT(dt)
	 */
	public void advanceTime(double dt) throws IllegalPositionException, 
				IllegalArgumentException, IllegalTimeException {
		if (! isValidDT(dt)) {
			throw new IllegalArgumentException();
		}
		if (getCurrentHitPoints() == 0) {
			dropItem();
			terminate();
		}
		else {
			controlXP();
			
			if (!isResting()) {
				setTimeAfterResting(getTimeAfterResting() + dt);
			}

			if (!isNeighbouringSolid(getCubeCoordinate()) && !isFalling())
				fall();

			if (isFalling()) {
				controlFalling(dt);
			}

			else if (getTimeAfterResting() >= 180.0) {
				startResting();
			}

			else if (isResting()) {
				controlResting(dt);
			}
			else if (isMoving()) {
				controlMoving(dt);
			}
			else if (getState() == State.EMPTY) {
				if (!(isDestCubeLTReached(dt))
						&& !getCubeCoordinate().equals(getDestCubeLT()) ) {

					moveTo(getDestCubeLT());
				}
				controlWaiting(dt);
			}
			else if (isAttacking()) {
				controlAttacking(dt);
			}
			else if (isWorking()) {
				controlWorking(dt);
			}
		}
	}
	
	private void fall() {
		if (!isFalling()) {
			setStartFallingCube(getCubeCoordinate());
			setPosition(World.getCubeCenter(getCubeCoordinate()));
			setState(State.FALLING);
		}
	}
	
	public boolean isFalling() {
		return (getState() == State.FALLING);
	}
	
	private void controlFalling(double dt) throws IllegalPositionException {
		if(isAboveSolid(getCubeCoordinate()) || getCubeCoordinate().get(2) == 0) {
			
			int lostHP = 10*(getStartFallingCube().get(2) - getCubeCoordinate().get(2));
			setStartFallingCube();
			this.updateCurrentHitPoints(getCurrentHitPoints() - lostHP);
			setState(State.EMPTY);
			
		}
		else {
			updatePosition(dt);
		}
	}
	
	private void setStartFallingCube(Coordinate coordinate) {
		this.startFallingCube = coordinate;
	}
	
	private void setStartFallingCube() {
		this.startFallingCube = null;
	}
	
	private Coordinate getStartFallingCube() {
		return this.startFallingCube;
	}
	
	private Coordinate startFallingCube = null;
	
	
	
	/**
	 * Manage the waiting of the unit, i.e. when the state of the unit is equal to empty.
	 * 
	 * @param dt
	 */
	private void controlWaiting(double dt) throws IllegalPositionException {

		if (isDefaultBehaviorEnabled()) {
			double dice = random.nextDouble();
			
			if (dice < 4.0/4.0) {
				Coordinate destCube = getRandomReachableCube(getCubeCoordinate());
				moveTo(destCube);
				
			} else if (dice >1.0/4.0 && dice < 2.0/4.0) {
				
				Coordinate targetCube = getWorld().getRandomNeighbouringCube(getCubeCoordinate());
				
				//System.out.println(getCubeCoordinate().toString());
				//System.out.println(targetCube.toString());
				
				workAt(targetCube);
				
			}
			else if (dice >2.0/4.0 && dice < 3.0/4.0 && enemiesInRange()) {
				attack(getEnemyInRange());
			}
			else {
				rest();
			}
			if (isMoving()) {
				if (random.nextDouble() < 0.5) {
					startSprinting();
				}
			}
		}
	}

	/**
	 * Returns true if the unit is moving.
	 * 
	 * @return	True if and only if the unit is moving.
	 * 			| result = (getState() == State.MOVING)
	 */
	public boolean isMoving() {
		return (getState() == State.MOVING);
	}

	/**
	 * Makes the unit start moving, i.e. sets its state to moving.
	 * 
	 * @post	The units state is equal to moving
	 * 			| new.getState() == State.MOVING
	 */
	private void startMoving() {
		setState(State.MOVING);
	}

	/**
	 * Manage the moving of the unit, i.e. when the state of the unit is equal to moving.
	 * 
	 * @param dt
	 */
	private void controlMoving(double dt) throws IllegalPositionException {
		
		if (!isNeighbouringSolid(getCubeCoordinate()))
			fall();
		
		else if (!isAttacked()) {
			
			setOrientation((float) Math.atan2(getVelocity()[1], getVelocity()[0] ));
			
			/*System.out.println(getVelocity()[1]);
			System.out.println(getVelocity()[0]);
			System.out.println("tan" + Math.atan2(getVelocity()[1], getVelocity()[0] ));
			*/
			
			if (isSprinting()) {
				updateCurrentStaminaPoints( (int) (getCurrentStaminaPoints() - (dt/0.1)));
				if (getCurrentStaminaPoints() <= 0) {
					stopSprinting();
					updateCurrentStaminaPoints(0);
				}
			}
			
			if (! reached(dt)) {
				updatePosition(dt);
			}
			else if (reached(dt)) {

				setPosition(World.getCubeCenter(getDestination()));
				addXP(1);
				
				if (getCubeCoordinate().equals(getDestCubeLT()) ) {
					
					this.destCubeLTReached = true;
					if (isSprinting()) stopSprinting();
				}
				setState(State.EMPTY);	
			}

		}
		else {
			setState(State.EMPTY);
		}

	}

	/**
	 * Move to an adjacent cube.
	 * 
	 * @param cubeDirection
	 * 			The relative directions of the cube to which the unit has to move.
	 * 
	 * @post	The destination of the unit is set to the right adjacent cube, if
	 * 			the unit is not already moving or in the initial resting state or attacked.
	 * 			| new.getDestination() == determineCube(cubeDirection)
	 * 
	 * @post	The moving direction of the unit is set towards the destination cube, if
	 * 			the unit is not already moving or in the initial resting state or attacked.
	 * 			| new.movingDirection == new.getDestination() - this.getPosition()
	 * 
	 * @post	The orientation of the unit is set towards the destination, if
	 * 			the unit is not already moving or in the initial resting state or attacked.
	 * 			| new.getOrientation() == Math.atan2(getVelocity()[0], getVelocity()[2] )
	 * 
	 * @throws IllegalArgumentException
	 * 			The given direction is not a valid direction.
	 * 			| !(cubeDirection instanceof int[]) || cubeDirection.length != 3 )
	 */
	public void moveToAdjacent(int[] cubeDirection) 
			throws IllegalPositionException, IllegalArgumentException {

		if (!(cubeDirection instanceof int[]) || cubeDirection.length != 3 )
			throw new IllegalArgumentException();


		if (/*!isMoving() &&*/ !isAttacked() && getState() != State.RESTING_1 && !isFalling()) {
			startMoving();
			
			double[] newPosition = new double[3];
			double[] direction = new double[3];

			for (int i=0; i<3; i++) {
				newPosition[i] = Math.floor(getPosition()[i] )
						+ (double) cubeDirection[i];
				newPosition[i] += World.getCubeLength()/2.0;
				direction[i] = newPosition[i] - getPosition()[i];
			}
			
			try {
				setDestination(convertPositionToCoordinate(newPosition));
			}
			catch (IllegalPositionException e) {
				setState(State.EMPTY);
				throw new IllegalPositionException(newPosition);
			}
			
			//this.movingDirection = direction;
			//setOrientation((float) Math.atan2(getVelocity()[0], getVelocity()[1] ));
		}
	}

	/**
	 * Move to a target cube.
	 * 
	 * @param destCube
	 * 			The coordinates of the destination cube.
	 * 
	 * @post	The long term destination of the unit will be equal to
	 * 			the destination cube.
	 * 			| new.destCubeLT == destCube
	 * 
	 * @throws IllegalPositionException
	 * 			The given destination cube is not valid.
	 * 			| !canHaveAsPosition(convertPositionToDouble(destCube))
	 */
	public void moveTo(Coordinate destCube) throws IllegalPositionException,
						IllegalArgumentException {
		
		//System.out.println(Arrays.toString(convertPositionToDouble(destCube)));
		if (!canHaveAsPosition(destCube)) {
			throw new IllegalPositionException(destCube);
		}
		
		//startMoving();
		setDestCubeLT(destCube);
		this.destCubeLTReached = false;
		
		if (getState() != State.RESTING_1) {
			
			Coordinate startCube = getCubeCoordinate();
			Queue<Tuple> path = computePath(getDestCubeLT());
			
			if (path == null) this.destCubeLTReached = true;
			
			else if (Tuple.containsCube(path, startCube)) {
				Tuple nextTuple = getNeighbourWSmallestN(path, startCube);
				
				//startMoving();
				if (nextTuple != null)
					moveTowards(nextTuple.cube);
			}
			
		}
	}
	
	private Queue<Tuple> computePath(Coordinate destCube) {
		Queue<Tuple> path = new LinkedList<Tuple>();
		path.add(new Tuple(destCube, 0));
		
		int iterations = 0;
		while(!Tuple.containsCube(path, getCubeCoordinate()) 
				&& Tuple.hasNext(path) && iterations < 301) {
			
			Tuple nextTuple = Tuple.getNext(path);
			search(nextTuple, path);
			nextTuple.isChecked = true;
			iterations++;

			if (iterations >= 301) {
				//this.destCubeLTReached = true;
				path = null;
				break;
			}
		}
		return path;
	}
	
	
	private void moveTowards(Coordinate destCube) {
						
			if ((getCubeCoordinate() != destCube)) {
				//startMoving();
				
				Coordinate startCube = getCubeCoordinate();
				
				int[] cubeDirection = new int[3];
				for (int i=0; i<cubeDirection.length; i++) {
					cubeDirection[i] = destCube.get(i) - startCube.get(i);
				}
				
				moveToAdjacent(cubeDirection);
				setOrientation((float) Math.atan2(getVelocity()[1], getVelocity()[0] ));
			}
	}
	
	
	private void search(Tuple currentTuple, Queue<Tuple> path) {
		
		List<Coordinate> cubes = new LinkedList<Coordinate>();
		
		Set<Coordinate> neighbours = getWorld().getNeighbours(currentTuple.cube);
		for (Coordinate neighbour: neighbours) {
			if (getWorld().getCubeTypeAt(neighbour).isPassable()
					
					&& getWorld().isNeighbouringSolid(neighbour)
					
					&& !Tuple.containsCubeWithSmallerN(path, neighbour, currentTuple)
					) {
				//System.out.println("if S");
				cubes.add(neighbour);
			}
		}
		
		for (Coordinate cube: cubes) {
			path.add(new Tuple(cube, currentTuple.n + 1));
		}
	}
	
	
	private Tuple getNeighbourWSmallestN(Queue<Tuple> path, Coordinate cube) {
		Set<Coordinate> neighbours = getWorld().getNeighbours(cube);
		Tuple result = null;
		boolean first = false;
		
		for (Coordinate neighbour: neighbours) {
			if (!first && Tuple.containsCube(path, neighbour)) {
				result = Tuple.getCubeTuple(path, neighbour);
				first = true;
			}
			else if (Tuple.containsCube(path, neighbour) && Tuple.getCubeTuple(path, neighbour).n < result.n) {
				result = Tuple.getCubeTuple(path, neighbour);
			}
		}
		return result;
	}
	
	private boolean isReachable(Coordinate destCube) {
		return (computePath(destCube) != null);
	}
	
	
	private Coordinate getRandomReachableCube(Coordinate currentCube) {
		Coordinate cube;
		do {
			cube = getWorld().getRandomNeighbouringSolidCube();
		} while (!isReachable(cube));
		return cube;
	}
	
	/**
	 * Returns the destination of the unit.
	 */
	@Basic @Model
	public Coordinate getDestination() {
		return this.destination;
	}

	/**
	 * Set the destination of the unit to the given coordinates.
	 * 
	 * @param newDestination
	 * 			The new destination coordinates.
	 * 
	 * @post The new destination of this unit is equal to the
	 * 		given coordinates.
	 * 		| new.getDestination() == newDestination
	 * 
	 * @throws IllegalPositionException
	 * 			The given destination is not a valid position.
	 * 		| !canHaveAsPosition(newDestination)
	 */
	private void setDestination(Coordinate newDestination) throws IllegalPositionException {
		if (!canHaveAsPosition(newDestination)) {
			throw new IllegalPositionException(newDestination);
		}
		this.destination = newDestination;
	}

	/**
	 * Returns the long term destination of the unit.
	 */
	@Basic
	public Coordinate getDestCubeLT() {
		return this.destCubeLT;
	}
	
	/**
	 * Returns the long term destination of the unit.
	 */
	/*
	@Basic
	private boolean isDestCubeLTReached(double dt) {
		return (getDistanceTo(getPosition(), convertPositionToDouble(getDestCubeLT())) 
							< getCurrentSpeed()*dt);
	}
	*/
	
	@Basic
	private boolean isDestCubeLTReached(double dt) {
		return this.destCubeLTReached;
	}

	/**
	 * Set the long term destination of the unit to the given coordinates.
	 * 
	 * @param destCubeLT
	 * 			The new long term destination cube coordinates.
	 * 
	 * @post The new long term destination of this unit is equal to the
	 * 		given cube coordinates.
	 * 		| new.getDestCubeLT() == destCubeLT
	 * 
	 * @throws IllegalPositionException
	 * 			The given position is not valid.
	 * 		| !(canHaveAsPosition(convertPositionToDouble(destCubeLT)) 
	 * 				|| destCubeLT == null )
	 */
	private void setDestCubeLT(Coordinate destCubeLT) throws IllegalPositionException {
		if ( !canHaveAsPosition(destCubeLT) 
									/*|| destCubeLT == new int[]{-1,-1,-1}*/ )
			throw new IllegalPositionException(destCubeLT);
		this.destCubeLT = destCubeLT;
	}


	/**
	 * Variable registering the destination of the unit in the game world.
	 */
	private Coordinate destination = new Coordinate(0,0,0);

	/**
	 * Variable registering the long term destination of the unit in the game world.
	 */
	private Coordinate destCubeLT = new Coordinate(0,0,0);
	
	/**
	 * Variable registering whether the long term destination had been reached.
	 */
	private boolean destCubeLTReached = true;


	/**
	 * Returns true if the unit reaches its destination in the current time span.
	 * 
	 * @param	dt
	 * 			The current time span.
	 * 
	 * @return	True if and only if the unit reaches the destination.
	 * 			| result = (getDistanceToDestination(getPosition()) < getCurrentSpeed()*dt)
	 */
	private boolean reached(double dt) throws IllegalPositionException {
		return (getDistanceTo(getPosition(), World.getCubeCenter(getDestination())) < getCurrentSpeed()*dt);
	}


	/**
	 * Returns the distance to the short term destination from a given position.
	 * 
	 * @param	position
	 * 			The position from which to calculate the distance.
	 * 
	 * @return	the distance to the destination, which is equal to the square root of 
	 * 			the square of the differences between the x, y and z coordinates of the
	 * 			current position and the destination.
	 * 			| result = Math.sqrt(Math.pow(position[0]-getDestination()[0],2)
	 * 			|	+ Math.pow(position[1]-getDestination()[1],2) 
	 * 			|	+ Math.pow(position[2]-getDestination()[2],2))
	 * 
	 * @throws	IllegalPositionException
	 * 			The given position is not a valid position.
	 * 			| !canHaveAsPosition(position)
	 */
	@Model
	private double getDistanceTo(double[] position, double[] position2) throws IllegalPositionException {
		if (!canHaveAsPosition(convertPositionToCoordinate(position)))
			throw new IllegalPositionException(position);

		return Math.sqrt(Math.pow(position[0]-position2[0],2)
				+ Math.pow(position[1]-position2[1],2) 
				+ Math.pow(position[2]-position2[2],2));
	}


	/**
	 * Update the current position of the moving unit.
	 * 
	 * @param dt
	 * 			The time span since the previous update of the position.
	 * 
	 * @post The new position of this unit is equal to the previous position
	 * 		added by the velocity times the time span.
	 * 		| new.getPosition() == this.getPosition() + this.getVelocity()*dt
	 */
	/*private void updatePosition(double dt) throws IllegalPositionException {
		double[] newPosition = new double[3];
		for (int i=0; i<3; i++) {
			newPosition[i] = getPosition()[i] + getVelocity()[i]*dt;
		}
		setPosition(newPosition);
	}*/


	/**
	 * Returns the moving direction of the unit.
	 */
	@Basic
	private double[] getMovingDirection() {
		return this.movingDirection;
	}
	
	/**
	 * Set the moving direction of the unit.
	 * 
	 * @param	direction
	 * 			The direction to set the moving direction to.
	 * 
	 * @throws	IllegalArgumentException
	 * 			The given direction is not a valid direction.
	 * 			| ! direction instanceof double[]
	 * 			| 		|| direction.length != 3
	 */
	@Raw
	private void setMovingDirection(double[] direction) throws IllegalArgumentException {
		if (!(direction instanceof double[]) || direction.length != 3 )
			throw new IllegalArgumentException();
		this.movingDirection = direction;
	}

	/**
	 * Variable registering the moving direction of the unit.
	 */
	private double[] movingDirection;


	/**
	 * Returns the units velocity.
	 * 
	 * @return	The units current velocity, which is equal to its speed multiplied by
	 * 			the difference of its target position coordinates and its current
	 * 			position coordinates, divided by the distance to the destination.
	 * 			| result = getCurrentSpeed()*(getDestination() - getPosition())
	 * 			|				/getDistanceToDestination(getPosition())
	 */
	@Model @Override
	protected double[] getVelocity() throws IllegalPositionException {
		/*double d = Math.sqrt(Math.pow(getMovingDirection()[0],2)
				+ Math.pow(getMovingDirection()[1],2) + Math.pow(getMovingDirection()[2],2));*/
		if (isFalling())
			return new double[]{0.0,0.0,-3.0};
		
		double d = getDistanceTo(getPosition(), World.getCubeCenter(getDestination()));
		double[] v = new double[3];
		for (int i=0; i<3; ++i) {
			v[i] = getCurrentSpeed()*(World.getCubeCenter(getDestination())[i]-getPosition()[i])/d;//getMovingDirection()[i]/d;
		}
		return v;
	}

	/**
	 * Returns the units base speed.
	 * 
	 * @return	The units base speed if the unit is not sprinting, 
	 * 			which is equal to 1.5 times the quotient of its strength added 
	 * 			by its agility, and 2 times its weight.
	 * 			| result = 1.5*(getStrength()+getAgility())/(2.0*getWeight())
	 * 
	 * @return	The units base speed if the unit is sprinting, 
	 * 			which is equal to 3.0 times the quotient of its strength added 
	 * 			by its agility, and 2 times its weight.
	 * 			| result = 3.0*(getStrength()+getAgility())/(2.0*getWeight())
	 */
	@Model
	private double getBaseSpeed() {
		if (isSprinting()) {
			return 3.0*(getStrength()+getAgility())/(2.0*getTotalWeight());
		} else {
			return 1.5*(getStrength()+getAgility())/(2.0*getTotalWeight());
		}
	}

	/**
	 * Returns the units movement speed, which depends on the base speed, 
	 * 		the moving direction and on whether the unit is currently moving or not.
	 * 
	 * @return	The units speed if the unit is not moving, which is equal to 
	 * 			zero.
	 * 			| result = 0
	 * 
	 * @return	The units speed speed if the unit is moving upwards, which is
	 * 			equal to half of the base speed.
	 * 			| result = 0.5*getBaseSpeed()
	 * 
	 * @return	The units speed speed if the unit is moving downwards, which is
	 * 			equal to 1.2 times the base speed.
	 * 			| result = 1.2*getBaseSpeed()
	 * 
	 * @return	The units speed speed if the units z coordinate is not changing
	 * 			while moving, which is equal to the base speed.
	 * 			| result = getBaseSpeed()
	 */
	public double getCurrentSpeed() {
		if (isMoving()) {
			if ((World.getCubeCenter(getDestination())[2] - getPosition()[2]) > 0.0)
				return 0.5*getBaseSpeed();
			else if ((World.getCubeCenter(getDestination())[2] - getPosition()[2]) < 0.0)
				return 1.2*getBaseSpeed();
			else
				return getBaseSpeed();
		}
		return 0.0;
	}

	/**
	 * Returns whether the unit is sprinting or not.
	 */
	@Basic
	public boolean isSprinting() {
		return this.isSprinting;
	}

	/**
	 * Makes the unit start sprinting.
	 * 
	 * @post	If the unit is moving and if it has enough stamina points, its 
	 * 			sprinting field is set to true.
	 * 			| new.isSprinting() == true
	 */
	public void startSprinting() {
		if (isMoving() && getCurrentStaminaPoints() > 0 && !isFalling()) {
			this.isSprinting = true;
		}
	}

	/**
	 * Makes the unit stop sprinting.
	 * 
	 * @post	The units sprinting field is set to false.
	 * 			| new.isSprinting() == false
	 */
	public void stopSprinting() {
		this.isSprinting = false;
	}

	/**
	 * Variable registering the whether the unit is sprinting.
	 */
	private boolean isSprinting;


	/**
	 * Return the units orientation.
	 */
	@Basic @Raw
	public float getOrientation() {
		return orientation;
	}
	
	
	/**
	 * Check whether the given orientation is valid.
	 * 
	 * @param value
	 * 			The value to be checked.
	 * 
	 * @return True if and only if the value is a float, larger
	 * 			than or equal to zero and smaller than 2*Pi
	 * 		| result = (Integer.class.isInstance(value) && value > 0 && value < 2*Math.PI) 
	 */
	private static boolean isValidOrientation(float value) {
		return (Float.class.isInstance(value) && value >= - Math.PI && value <= Math.PI);
	}
	

	/**
	 * Set the orientation of the unit to the given new value.
	 * 
	 * @param newValue
	 * 			The new orientation for this unit.
	 * @post if the given orientation is a float number between
	 * 			0.0 and 2*Pi, then the new orientation of the unit is
	 * 			equal to the given orientation.
	 * 		| if (isFloat(newValue) && newValue >= 0.0
	 * 		| 	&& newValue < 2*Math.PI
	 * 		| then new.getOrientation() == newValue
	 */
	@Raw
	private void setOrientation(float newValue) {
		if (isValidOrientation(newValue)) {
			this.orientation = newValue;
		}
	}

	/**
	 * Variable registering the orientation of the unit.
	 */
	private float orientation;


	/**
	 * Returns true if the unit is working.
	 * 
	 * @return	True if and only if the unit is working.
	 * 			| result = (getState() == State.WORKING)
	 */
	public boolean isWorking() {
		return (getState() == State.WORKING);
	}


	/**
	 * Makes the unit start working, i.e. sets its state to working.
	 * 
	 * @post	The units state is equal to moving
	 * 			| new.getState() == State.MOVING
	 * 
	 * @post	The units time to completion is set to 500 divided by its
	 * 			strength.
	 * 			| new.getTimeToCompletion() == 500.0/getStrength()
	 */
	private void startWorking() throws IllegalTimeException {
		setTimeToCompletion(500.0f/getStrength());
		setState(State.WORKING);
	}

	/**
	 * Manage the working of the unit, i.e. when the state of 
	 * 				the unit is equal to working.
	 * 
	 * @param dt
	 */
	private void controlWorking(double dt) throws IllegalTimeException {
		if (getTimeToCompletion() > 0.0) {
			
			double[] targetPosition = World.getCubeCenter(getTargetCube());
			setOrientation((float)Math.atan2(getPosition()[1]-targetPosition[1],
					targetPosition[0] - getPosition()[0]));
			try {
				setTimeToCompletion((float) (getTimeToCompletion() - dt));
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		else if (getTimeToCompletion() < 0.0) {
			
			setTimeToCompletion(0.0f);
			
			if (isCarryingItem()) {
				dropItem();
				addXP(10);
				
			} else {
				Set<Item> items = getWorld().getObjectsAt(getTargetCube());
				
				if ((getWorld().getCubeTypeAt(getTargetCube())==TerrainType.WORKSHOP)
							&& getWorld().containsLog(items)
							&& getWorld().containsBoulder(items)) {
					improveEquipment(items);
					addXP(10);
					
				} else if (getWorld().containsBoulder(items)) {
					pickUpItem(getWorld().getBoulderFrom(items));
					addXP(10);
					
				} else if (getWorld().containsLog(items)) {
					pickUpItem(getWorld().getLogFrom(items));
					addXP(10);
					
				} else if (getWorld().getCubeTypeAt(getTargetCube()) == TerrainType.TREE
								|| getWorld().getCubeTypeAt(getTargetCube()) == TerrainType.ROCK) {
					getWorld().collapse(getTargetCube(), 1.00);
					addXP(10);
				}
			}
			setState(State.EMPTY);
		}
	}

	/**
	 * Makes the unit work.
	 * 
	 * @post	necessary???
	 * 
	 */
	@Deprecated
	public void work()  {
		if (!isMoving() && getState() != State.RESTING_1 && !isFalling()) {
			startWorking();
		}
	}
	
	
	public void workAt(Coordinate targetCube) throws IllegalTargetException {
		
		if (!isMoving() && getState() != State.RESTING_1 && !isFalling()) {
			
			setTargetCube(targetCube);
			startWorking();
			
		}
	}
	
	private void setTargetCube(Coordinate target) {
		
		if (!getWorld().canHaveAsCoordinates(target)
				|| !(getWorld().isNeighbouring(getCubeCoordinate(), target) 
				|| getCubeCoordinate().equals(target)) )
			throw new IllegalTargetException(getCubeCoordinate(), target);
		this.targetCube = target;
	}
	
	private Coordinate getTargetCube() {
		return this.targetCube;
	}
	
	private Coordinate targetCube = null;
	
	private void dropItem() {
		if (isCarryingItem()) {
			getCarriedItem().setWorld(getWorld());
			getCarriedItem().setPosition(getPosition());
			getWorld().addItem(getCarriedItem());
			subTotalWeight(getCarriedItem());
			carriedItem = null;
		}
	}
	
	
	private void pickUpItem(Item item) {
		
		dropItem();
		
		this.carriedItem = item;
		addTotalWeight(item);
		getWorld().removeItem(item);
	}
	
	private void addTotalWeight(Item item) {
		this.totalWeight = this.getWeight() + item.getWeight();
	}
	
	private void subTotalWeight(Item item) {
		this.totalWeight = this.getWeight() - item.getWeight();
	}
	
	private int getTotalWeight() {
		return this.totalWeight;
	}
	
	private void setInitialTotalWeight() {
		this.totalWeight = getWeight();
	}
	
	private int totalWeight = 0;
	
	public boolean isCarryingBoulder() {
		return (carriedItem instanceof Boulder);
	}
		
	
	public boolean isCarryingLog() {
		return (carriedItem instanceof Log);
	}
	
	
	public boolean isCarryingItem() {
		return (carriedItem != null);
	}
	
	private Item getCarriedItem() {
		return this.carriedItem;
	}
	
	private Item carriedItem = null;
	
	
	private void improveEquipment(Set<Item> items) {
		boolean logT = false, boulderT = false;
		Iterator<Item> it = items.iterator();
		
		while ((!logT || !boulderT) && it.hasNext()) {
			if (it.next() instanceof Boulder && !boulderT) {
				it.next().terminate();
				boulderT = true;
			} else if (it.next() instanceof Log && !logT) {
				it.next().terminate();
				logT = true;
			}
		}
		
		setWeight(getWeight() + 1);
		setToughness(getToughness() +1);
	}


	/**
	 * Returns true if the unit is attacking.
	 * 
	 * @return	True if and only if the unit is attacking.
	 * 			| result = (getState() == State.ATTACKING)
	 */
	public boolean isAttacking() {
		return (getState() == State.ATTACKING);
	}



	/**
	 * Makes the unit start attacking, i.e. sets its state to attacking.
	 * 
	 * @post	The units state is equal to attacking
	 * 			| new.getState() == State.ATTACKING
	 * 
	 * @post	The units time to completion is set to 1.0
	 * 			| new.getTimeToCompletion() == 1.0
	 */
	private void startAttacking() throws IllegalTimeException {
		setTimeToCompletion(1.0f);
		setState(State.ATTACKING);
		
	}


	/**
	 * Manage the attacking of the unit, i.e. when the state of 
	 * 				the unit is equal to attacking.
	 * 
	 * @param dt
	 */
	private void controlAttacking(double dt) throws IllegalTimeException {
		if (!isFalling()) {
			setTimeToCompletion((float) (getTimeToCompletion() - dt));
			if (getTimeToCompletion() < 0.0) {
	
				//getDefender().setAttacked(true);
				getDefender().defend(this);
				getDefender().setAttacked(false);
				setState(State.EMPTY);
				
				//addXP(20);
			}
		}
	}


	/**
	 * Attack another unit.
	 * 
	 * @param defender
	 * 			The unit to attack.
	 * 
	 * @post	If the unit is not currently moving, its orientation is set towards
	 * 			the attacked unit.
	 * 			| new.getOrientation() == arctangent(defender.getPosition()[1]-this.getPosition()[1],
	 * 			|	defender.getPosition()[0]-this.getPosition()[0]))
	 * 
	 * @post	If the unit is not currently moving, the attacked units orientation is set
	 * 			towards this unit, i.e. the attacking unit.
	 * 			| (new defender).getOrientation() == arctangent(this.getPosition()[1]-
	 * 			|	defender.getPosition()[1], this.getPosition()[0]-defender.getPosition()[0]))
	 * 
	 * @post	If the unit is not currently moving, this units defender field is set to
	 * 			the unit object it is attacking.
	 * 			| new.defender == defender
	 *  
	 * @throws IllegalVictimException
	 * 			The given unit to be attacked is not in range.
	 * 			| !canAttack(defender)
	 */
	public void attack(Unit defender) throws IllegalTimeException, IllegalVictimException {
		if (!canAttack(defender))
			throw new IllegalVictimException(this, defender);

		if (!isMoving() && !isFalling()) {
			startAttacking();

			// Orientation update
			this.setOrientation((float)Math.atan2(defender.getPosition()[1]-this.getPosition()[1],
					defender.getPosition()[0]-this.getPosition()[0]));

			defender.setOrientation((float)Math.atan2(this.getPosition()[1]-defender.getPosition()[1],
					this.getPosition()[0]-defender.getPosition()[0]));


			this.defender = defender;
			getDefender().setAttacked(true);
		}
	}

	/**
	 * Returns true if the unit can attack another given unit, i.e. if the
	 * 		given unit is within range.
	 * 
	 * @param	victim
	 * 			The unit to check if in range or not.
	 * 
	 * @return	True if and only if the given unit occupies the same
	 * 			or a neighboring cube of the game world.
	 * 			| result = (abs(getCubeCoordinate() - victim.getCubeCoordinate()) <= 1)
	 * 
	 * AND NOT SAME FACTION
	 * 
	 */
	@Model
	private boolean canAttack(Unit victim) {
		Coordinate attackerPos = this.getCubeCoordinate();
		Coordinate victimPos = victim.getCubeCoordinate();
		for (int i = 0; i < attackerPos.getCoordinates().length; i++) {
			if ( Math.abs(attackerPos.get(i) - victimPos.get(i)) > 1)
				return false;
		}
		if (getFaction() == victim.getFaction())
			return false;
		return true;
	}
	
	
	private boolean enemiesInRange() {
		Iterator<Unit> it = getWorld().getAllUnits().iterator();
		while (it.hasNext()) {
			if (canAttack(it.next()))
				return true;
		}
		return false;
	}
	
	
	private Unit getEnemyInRange() {
		Iterator<Unit> it = getWorld().getAllUnits().iterator();
		while (it.hasNext()) {
			if (canAttack(it.next()))
				return it.next();
		}
		return null;
	}


	/**
	 * Returns the time left for a unit to complete its work or attack.
	 */
	@Basic
	private float getTimeToCompletion() {
		return this.timeToCompletion;
	}

	
	// ISVALIDTIME!!!!!!!!
	
	/**
	 * Checks if a given value is a valid time to completion value.
	 * 
	 * @param	value
	 * 			Value to check
	 * 
	 * @return	true if and only if the given value is a float value and
	 * 			is larger than -0.5.
	 * 			| result = (Float.class.isInstance(value) && (value > -0.5))
	 */
	private static boolean canHaveAsTime(float value) {
		return ( Float.class.isInstance(value) && (value > -10.0f) );
	}
	
	/**
	 * Checks if a given value is a valid time value.
	 * 
	 * @param	value
	 * 			Value to check
	 * 
	 * @return	true if and only if the given value is a float value and
	 * 			is larger than or equal to 0.0.
	 * 			| result = (Double.class.isInstance(value) && (value >= 0.0))
	 */
	private static boolean canHaveAsTime(double value) {
		return ( Double.class.isInstance(value) && (value >= 0.0) );
	}

	/**
	 * Sets the time to completion for an attack or work job to a given value.
	 * 
	 * @param newValue
	 * 			the given value for the new time to completion.
	 * 
	 * @post	The time to completion field is set to the new value.
	 * 			| new.getTimeToCompletion() = newValue
	 * 
	 * @throws IllegalTimeException
	 * 			The time value is not valid.
	 * 			| !(canHaveAsTime(1.0f))
	 */
	private void setTimeToCompletion(float newValue) throws IllegalTimeException {
		if (!canHaveAsTime(newValue))
			throw new IllegalTimeException(newValue, this);
		this.timeToCompletion = newValue;
	}

	/**
	 * Field registering the time to completion.
	 */
	private float timeToCompletion;


	/**
	 * Defend an attack from an attacking unit. The defending unit will first try
	 * 		to dodge the incoming attack, if that fails it will try to block it,
	 * 		and if that fails too it will take damage.
	 * 
	 * @param	attacker
	 * 			The attacking unit.
	 */
	private void defend(Unit attacker) {
		boolean dodged = this.dodge(attacker);
		if (!dodged) {
			boolean blocked = this.block(attacker);
			if (!blocked) {
				this.takeDamage(attacker);
				attacker.addXP(20);
			} else {
				addXP(20);
			}
		} else {
			addXP(20);
		}
	}

	/**
	 * Try to dodge an attack from an attacking unit. If it succeeds, the unit
	 * 		will jump to a random adjacent cube.
	 * 
	 * @param	attacker
	 * 			The attacking unit.
	 * 
	 * @return	True if the attack is dodged, otherwise false. The probability 
	 * 			for this to happen is equal to 0.2 times the quotient of the 
	 * 			agility of the defender and the agility of the attacker.
	 * 			| result = (random.nextDouble() <= 0.2*(this.getAgility()/attacker.getAgility()) )
	 */
	private boolean dodge(Unit attacker) {
		if ( random.nextDouble() <= 0.20*(this.getAgility()/attacker.getAgility())) {
			while(true) {
				try {
					this.jumpToRandomAdjacent();
					break;
				}
				catch (IllegalPositionException exc){
					continue;
				}
			}
			return true;
		}
		return false;
	}


	/**
	 * Jump to a random adjacent cube.
	 * 
	 * @post	The units position is set to a random adjacent cube.
	 * 			| new.getPosition() = randomAdjacentPosition
	 * 
	 * @throws IllegalPositionException
	 * 			The calculated random adjacent position is not a valid position.
	 * 			| !(canHaveAsPosition(randomAdjacentPosition))
	 */
	private void jumpToRandomAdjacent() throws IllegalPositionException {
		double[] newPosition = this.getPosition();
		newPosition[0] += 2*random.nextDouble() - 1.0;
		newPosition[1] += 2*random.nextDouble() - 1.0;
		
		if (!canHaveAsPosition(convertPositionToCoordinate(newPosition))) 
			throw new IllegalPositionException(newPosition);
		
		this.setPosition(newPosition);
	}


	/**
	 * Try to block an attack from an attacking unit.
	 * 
	 * @param	attacker
	 * 			The attacking unit.
	 * 
	 * @return	True if the attack is blocked, otherwise false. The probability 
	 * 			for this to happen is equal to 0.25 times the quotient of the 
	 * 			sum of the agility and the strength of the defender and 
	 * 			the sum of the agility and the strength of the attacker.
	 * 			| result = (random.nextDouble() <= 0.25*((this.getAgility() + this.getStrength())
				|	/(attacker.getAgility() + attacker.getStrength())) )
	 */
	private boolean block(Unit attacker) {
		double probability = 0.25*((this.getAgility() + this.getStrength())
				/(attacker.getAgility() + attacker.getStrength()));
		if ( random.nextDouble() <= probability ) {
			return true;
		}
		return false;
	}


	/**
	 * The unit takes damage, i.e. loses an amount of hitpoints proportional with the
	 * 		attackers strength.
	 * 
	 * @param	attacker
	 * 			The attacking unit.
	 * 
	 * @post	the hitpoints of the unit get reduced by the strength of the
	 * 			attacker divided by 10.
	 * 			| new.getCurrentHitPoints() = this.getCurrentHitPoints()
				|		- (attacker.getStrength()/10)
	 */
	private void takeDamage(Unit attacker) {
		this.updateCurrentHitPoints(this.getCurrentHitPoints() - 
				(attacker.getStrength()/10));
	}

	/**
	 * Returns the unit that this unit is attacking.
	 */
	@Basic
	private Unit getDefender() {
		return this.defender;
	}

	/**
	 * Field registering the unit that this unit is attacking.
	 */
	private Unit defender;


	/**
	 * Returns whether the unit is currently being attacked.
	 */
	@Basic
	public boolean isAttacked() {
		return this.isAttacked;
	}
	
	/**
	 * Set the units attacked field to a given boolean value.
	 * 
	 * @param isAttacked
	 * 			the value to set the attacked field to.
	 * 
	 * @post The attacked field of this unit is equal to the
	 * 		given value.
	 * 		| new.isAttacked() == isAttacked
	 */
	private void setAttacked(boolean isAttacked) {
		this.setState(State.EMPTY);
		this.isAttacked = isAttacked;
	}
	
	/**
	 * Field registering whether the unit is being attacked.
	 */
	private boolean isAttacked;
	
	
	/**
	 * Returns the time the unit has been resting.
	 */
	@Basic 
	private double getTimeResting() {
		return this.timeResting;
	}
	
	
	/**
	 * Returns the time that has passed after the last time the unit rested.
	 */
	@Basic 
	private double getTimeAfterResting() {
		return this.timeAfterResting;
	}
	

	/**
	 * Returns true if the unit is resting.
	 * 
	 * @return	True if and only if the unit is resting.
	 * 			| result = (getState() == State.RESTING_HP || getState() == State.RESTING_STAM
	 * 			|		|| getState() == State.RESTING_1)
	 */
	public boolean isResting() {
		return (getState() == State.RESTING_HP || getState() == State.RESTING_STAM
				|| getState() == State.RESTING_1);
	}
	
	
	/**
	 * Makes the unit start resting, i.e. sets its state to resting.
	 * 
	 * @post	The units state is equal to the initial resting
	 * 			| new.getState() == State.RESTING_1
	 * 
	 * @post	The time the unit has been resting is set to zero.
	 * 			| new.getTimeResting() == 0.0
	 */
	private void startResting() throws IllegalTimeException {
		setTimeResting(0.0);
		setTimeAfterResting(0.0);
		setState(State.RESTING_1);
	}
	
	
	/**
	 * Manage the resting of the unit, i.e. when the state of 
	 * 				the unit is equal to resting.
	 * 
	 * @param dt
	 */
	private void controlResting(double dt) throws IllegalTimeException {
		setTimeResting(getTimeResting() + dt);

		if (!isAttacked() && !isAttacking() && !isFalling()) {

			if (getState() == State.RESTING_1) {
				if ((getTimeResting() * getToughness())/(0.2*200) > 1.0) {
					
					updateCurrentHitPoints(getCurrentHitPoints() + 
							(int) Math.round((getTimeResting()*getToughness())/(0.2*200)) );
					setTimeResting(getTimeResting() - (0.2*200*1.0)/getToughness());

					if (this.getCurrentHitPoints() < this.getMaxHitPoints()) {
						setState(State.RESTING_HP);
					}
					else if (this.getCurrentStaminaPoints() < this.getMaxStaminaPoints()) {
						setState(State.RESTING_STAM);
					}
				}
			}

			else if (getState() == State.RESTING_HP) {
				if ((getTimeResting() * getToughness())/(0.2*200) > 1.0) {
					updateCurrentHitPoints(getCurrentHitPoints() + 
							(int) Math.round((getTimeResting()*getToughness())/(0.2*200)) );
					setTimeResting(getTimeResting() - (0.2*200*1.0)/getToughness());
				}
			}
			else if (getState() == State.RESTING_STAM) {
				if ((getTimeResting() * getToughness())/(0.2*100) > 1.0) {
					updateCurrentStaminaPoints(getCurrentStaminaPoints() + 
							(int) Math.round((getTimeResting()*getToughness())/(0.2*100)) );
					setTimeResting(getTimeResting() - (0.2*100*1.0)/getToughness());
				}
			}
			if (getCurrentHitPoints() == getMaxHitPoints() &&
					getCurrentStaminaPoints() == getMaxStaminaPoints()) {
				setState(State.EMPTY);
			}
		}
	}
	
	/**
	 * Make the unit rest, if it's not currently moving.
	 * 
	 */
	public void rest() throws IllegalTimeException {
		if (!isMoving() && !isFalling()) {
			startResting();
		}
	}
	
	/**
	 * Set the time the unit has been resting to a given value.
	 * 
	 * @param newValue
	 * 			The new value for the time resting.
	 * 
	 * @post	The time the unit has been resting is equal to the given value.
	 * 			| new.getTimeResting() == newValue
	 * 
	 * @throws IllegalTimeException
	 * 			The new value is not a valid time value.
	 * 			| !canHaveAsTime(newValue)
	 */
	private void setTimeResting(double newValue) throws IllegalTimeException {
		if (!canHaveAsTime(newValue))
			throw new IllegalTimeException(newValue, this);
		this.timeResting = newValue;
	}
	
	/**
	 * Set the time after the unit has last rested to a given value.
	 * 
	 * @param newValue
	 * 			The new value for the time not rested.
	 * 
	 * @post	The time the unit has not been resting is equal to the given value.
	 * 			| new.getTimeAfterResting() == newValue
	 * 
	 * @throws IllegalTimeException
	 * 			The new value is not a valid time value.
	 * 			| !canHaveAsTime(newValue)
	 */
	private void setTimeAfterResting(double newValue) throws IllegalTimeException {
		if (!canHaveAsTime(newValue))
			throw new IllegalTimeException(newValue, this);
		this.timeAfterResting = newValue;
	}
	
	/**
	 * Field registering how long the unit has been resting.
	 */
	private double timeResting = 0.0;
	
	/**
	 * Field registering how much time passed after the unit last rested.
	 */
	private double timeAfterResting = 0.0;

	
	/**
	 * Returns whether the default behavior of the unit is enabled.
	 */
	@Basic
	public boolean isDefaultBehaviorEnabled() {
		return this.defaultBehavior;
	}
	
	
	/**
	 * Set the default behavior of the unit.
	 * 
	 * @param value
	 * 			The new value for default behavior.
	 * 
	 * @post	If the value is true and the default behavior is not enabled,
	 * 			the default behavior is set to true, hence enabled.
	 * 			| new.isDefaultBehaviorEnabled() == true
	 * 
	 * @post	If the value is false and the default behavior is enabled,
	 * 			the default behavior is set to false, hence disabled.
	 * 			| new.isDefaultBehaviorEnabled() == false
	 */
	public void setDefaultBehaviorEnabled(boolean value) {
		this.defaultBehavior = value;
	}
	
	/**
	 * Start the default behavior of the unit.
	 * 
	 * @post	The default behavior of the unit is enabled.
	 * 			| new.isDefaultBehaviorEnabled() == true
	 */
	public void startDefaultBehavior() {
		this.defaultBehavior = true;
	}
	
	/**
	 * Stop the default behavior of the unit.
	 * 
	 * @post	The default behavior of the unit is disabled.
	 * 			| new.isDefaultBehaviorEnabled() == false
	 */
	public void stopDefaultBehavior() {
		this.defaultBehavior = false;
	}

	
	/**
	 * Returns the current state of the unit.
	 */
	@Basic @Raw
	public State getState() {
		return this.state;
	}
	
	/**
	 * Sets the current state of the unit.
	 * 
	 * @param	state
	 * 			The state to set the state of the unit to.
	 * 
	 * @post	The state of the unit is set to the given state.
	 * 			| new.getState() == state
	 * 
	 * @throws	IllegalArgumentException
	 * 			the given state value is not a valid state for the unit.
	 * 			| !State.contains(toString(state))
	 */
	@Raw
	public void setState(State state) {
		/*if (!State.contains(state.toString()) )
			throw new IllegalArgumentException();*/
		this.state = state;
	}

	/**
	 * Variable registering the state of the unit.
	 */
	private State state;
	
	/**
	 * Variable registering whether the default behavior of the unit is enabled.
	 */
	private boolean defaultBehavior;
	
	
	private void controlXP() {
		while (getTXP() > 10) {
			subTXP(10);
			double dice = random.nextDouble();
			
			if (dice < 1.0/3.0 && isValidStrength(getStrength() + 1)) {
				setStrength(getStrength() + 1);
				
				if (!isValidStrength(getStrength() + 1)) {
					if (isValidAgility(getAgility() + 1)) {
						setAgility(getAgility() + 1);
					} else if (isValidToughness(getToughness() + 1)) {
						setToughness(getToughness() + 1);
					}
				}
			}
			else if (dice > 1.0/3.0 && dice < 2.0/3.0
					&& isValidAgility(getAgility() + 1)) {
				setAgility(getAgility() + 1);
				
				if (!isValidAgility(getAgility() + 1)) {
					if (isValidStrength(getStrength() + 1)) {
						setStrength(getStrength() + 1);
					} else if (isValidToughness(getToughness() + 1)) {
						setToughness(getToughness() + 1);
					}
				}
			}
			else if (isValidToughness(getToughness() + 1)) {
				setToughness(getToughness() + 1);
				
				if (!isValidToughness(getToughness() + 1)) {
					if (isValidStrength(getStrength() + 1)) {
						setStrength(getStrength() + 1);
					} else if (isValidAgility(getAgility() + 1)) {
						setAgility(getAgility() + 1);
					}
				}
			}
		}
	}
	
	@Basic @Raw
	public int getExperiencePoints() {
		return this.xp;
	}
	
	private void addXP(int x) {
		this.xp += x;
		this.tempXp += x;
	}
	
	private int xp;
	
	public int getTXP() {
		return this.tempXp;
	}
	
	private void subTXP(int x) {
		this.tempXp -= x;
	}
	
	private int tempXp;
	
	
	
	
	
	/* *********************************************************
	 * 
	 * 						UNIT - WORLD
	 *
	 **********************************************************/
	
	
	/**
	 * Check whether this unit can be attached to a given world.
	 * 
	 * @param	world
	 * 			The world to check.
	 * 
	 * @return	True if and only if the given world is not effective or if it
	 * 			can contain this unit.
	 * 			| result == ( (world == null)
	 * 			| 				|| world.canHaveAsUnit(this) )
	 */
	@Raw
	public boolean canHaveAsWorld(World world) {
		return ( (world == null) || world.canHaveAsUnit(this) );
	}
	
	/**
	 * Check whether this unit has a proper world in which it belongs.
	 * 
	 * @return	True if and only if this unit can have its world as the world to
	 * 			which it belongs and if that world is either not effective or contains
	 * 			this unit.
	 * 			| result == ( canHaveAsWorld(getWorld()) && ( (getWorld() == null)
	 * 			|				|| getWorld.hasAsUnit(this) ) )
	 */
	@Raw
	public boolean hasProperWorld() {
		return (canHaveAsWorld(getWorld()) && ( (getWorld() == null) 
					|| getWorld().hasAsUnit(this) ) );
	}
	
	
	/**
	 * Set the world this unit belongs to to the given world.
	 * 
	 * @param	world
	 * 			The world to add the unit to.
	 * 
	 * @post	This unit references the given world as the world
	 * 			it belongs to.
	 * 			| new.getWorld() == world
	 * 
	 * @throws	IllegalArgumentException
	 * 			If the given world is effective it must already reference this unit
	 * 			as one of its units.
	 * 			| (world != null) && !world.hasAsUnit(this)
	 * 
	 * @throws	IllegalArgumentException
	 * 			If the given world is not effective and this unit references an
	 * 			effective world, that world may not contain this unit.
	 * 			| (world == null) && (getWorld() != null) 
	 * 			|					&& (getWorld().hasAsUnit(this))
	 */
	public void setWorld(World world) throws IllegalArgumentException {
		if ( (world != null) && !world.hasAsUnit(this) )
			throw new IllegalArgumentException();
		if ( (world == null) && (getWorld() != null) && (getWorld().hasAsUnit(this)) )
			throw new IllegalArgumentException();
		this.world = world;
	}
	
	
	
	/* *********************************************************
	 * 
	 * 						UNIT - FACTION
	 *
	 **********************************************************/
	
	/**
	 * Variable registering the faction to which this unit belongs.
	 */
	private Faction faction = null;
	
	
	/**
	 * Return the faction to which this unit belongs. Returns a null refererence
	 * if this unit does not belong to any faction.
	 */
	@Basic @Raw
	public Faction getFaction() {
		return this.faction;
	}
	
	/**
	 * Check whether this unit can join a given faction.
	 * 
	 * @param	faction
	 * 			The faction to check.
	 * 
	 * @return	True if and only if the given faction is not effective or if it
	 * 			can be joined by this unit.
	 * 			| result == ( (faction == null)
	 * 			| 				|| faction.canHaveAsUnit(this) )
	 */
	@Raw
	public boolean canHaveAsFaction(Faction faction) {
		return ( (faction == null) || faction.canHaveAsUnit(this) );
	}
	
	/**
	 * Check whether this unit has a proper faction to which it belongs.
	 * 
	 * @return	True if and only if this unit can have its faction as the faction to
	 * 			which it belongs and if that faction is either not effective or contains
	 * 			this unit.
	 * 			| result == ( canHaveAsFaction(getFaction()) && ( (getFaction() == null)
	 * 			|				|| getFaction.hasAsUnit(this) ) )
	 */
	@Raw
	public boolean hasProperFaction() {
		return (canHaveAsFaction(getFaction()) && ( (getFaction() == null) 
					|| getFaction().hasAsUnit(this) ) );
	}
	
	
	/**
	 * Set the faction this unit belongs to to the given faction.
	 * 
	 * @param	faction
	 * 			The faction to add the unit to.
	 * 
	 * @post	This unit references the given world as the world
	 * 			it belongs to.
	 * 			| new.getFaction() == faction
	 * 
	 * @throws	IllegalArgumentException
	 * 			If the given world is effective it must already reference this unit
	 * 			as one of its units.
	 * 			| (faction != null) && !faction.hasAsUnit(this)
	 * 
	 * @throws	IllegalArgumentException
	 * 			If the given faction is not effective and this unit references an
	 * 			effective faction, that faction may not contain this unit.
	 * 			| (faction == null) && (getFaction() != null) 
	 * 			|					&& (getFaction().hasAsUnit(this))
	 */
	public void setFaction(Faction faction) throws IllegalArgumentException {
		if ( (faction != null) && !faction.hasAsUnit(this) )
			throw new IllegalArgumentException();
		if ( (faction == null) && (getFaction() != null) && (getFaction().hasAsUnit(this)) )
			throw new IllegalArgumentException();
		this.faction = faction;
	}
	
	
	/*public void terminate() {
		this.isTerminated = true;
	}*/
	
	
	/* ISVALID POSITION etc IN WORLD IPV IN UNIT, ITEM AFZONDERLIJK???????
	 * 
	 */
	/* ISVALIDFACTION/UNITS IPV CANHAVEAS????????????
	/* tests */
	/* LOOP INVARIANTS etc */
	
	// isValidINITIALAgility!!! not isValidAgility !!!!!!!!!!!!
	
	// Override equals etc in value classes!
	
	/* VRAGEN
	 * 
	 * 
	 * Nog methods @Raw?
	 * 
	 */
}

