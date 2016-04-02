package hillbillies.model;


import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Model;

import java.util.*;
import java.lang.Math;
import java.lang.*;

//import java.util.Random;

/**
* A class of units with a current position, a name, weight, strength, agility
* and toughness. 
* 
* @invar the current number of hitpoints must always be greater than or equal
* 		to zero and equal to or smaller than the max number of hitpoints.
* 		| currentHitPoints >= 0 && currentHitPoints <= maxHitPoints
* @invar the current number of stamina points must always be greater than or equal
* 		to zero and equal to or smaller than the max number of stamina points.
* 		| currentStaminaPoints >= 0 && currentStaminaPoints <= maxStaminaPoints
* 
*@author Ruben Cartuyvels
*@version	1.2
*/
public class Unit {
	/**
	 * Initialize a new unit with the given attributes.
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
	 * @post The new orientation of the unit is equal to Pi/2.
	 * 		| new.getOrientation() = Math.PI/2
	 * 
	 * @post The new minimum number of hitpoints of the unit is equal to 0.
	 * 		| new.getMinHitPoints() = 0
	 * 
	 * @post The new minimum number of stamina points of the unit is equal to 0.
	 * 		| new.getMinStaminaPoints() = 0
	 * 
	 * @post The default behavior of the unit is equal to the given value for default behavior.
	 * 		| new.isDefaultBehaviorEnabled() = enableDefaultBehavior
	 * 
	 * @throws IllegalPositionException(position, this)
	 *             The unit cannot have the given position (out of bounds).
	 *             | ! canHaveAsPosition(position)
	 *             
	 * @throws IllegalNameException(name, this)
	 *             The unit cannot have the given name.
	 *             | ! canHaveAsName(name)            
	 */
	public Unit (String name, int[] initialPosition, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) 
					throws IllegalPositionException, IllegalNameException {
		
		double[] doubleInitialPosition = convertPositionToDouble(initialPosition);
		
		setPosition(doubleInitialPosition);
		
		setName(name);
		
		if (weight > 24 && weight < 101) 
			setWeight(weight);
		else setWeight(25);
		if (agility > 24 && agility < 101)
			setAgility(agility);
		else setAgility(25);
		if (strength > 24 && strength < 101) 
			setStrength(strength);
		else setStrength(25);
		if (toughness > 24 && toughness < 101)
			setToughness(toughness);
		else setToughness(25);
		
		this.minHitPoints = 0;
		this.minStaminaPoints = 0;
		
		setOrientation((float)(Math.PI/2.0));
		
		this.random = new Random();
		
		this.setDefaultBehaviorEnabled(enableDefaultBehavior);
	}
	
	/**
	 * Converts a position given in integers to a position given in double values.
	 * 	This is an auxiliary method.
	 * 
	 * @param intPosition
	 * 			The position given in integer values.
	 * @return the position provided as parameter converted to the java
	 * 			double primitive type.
	 * @throws IllegalArgumentException
	 * 			The parameter is not an array of integers.
	 * 			| ! intPosition instanceof int[]
	 * 			| || intPosition.length != 3
	 */
	private double[] convertPositionToDouble(int[] intPosition) 
			throws IllegalArgumentException {
		
		if ( !(intPosition instanceof int[]) || intPosition.length != 3)
			throw new IllegalArgumentException();
		double[] doublePosition = new double[3];
		for (int i=0; i<3; i++) {
			doublePosition[i] = (double) intPosition[i];
		}
		return doublePosition;
	}
	
	/**
	 * Converts a position given in doubles to a position given in integer values.
	 * 	This is an auxiliary method.
	 * 
	 * @param doublePosition
	 * 			The position given in double values.
	 * @return the position provided as parameter converted to the java
	 * 			integer primitive type.
	 * @throws IllegalArgumentException
	 * 			The parameter is not an array of doubles.
	 * 			| ! doublePosition instanceof double[]
	 * 			| || doublePosition.length != 3
	 */
	private int[] convertPositionToInt(double[] doublePosition) 
			throws IllegalArgumentException {
		
		if ( !(doublePosition instanceof double[]) || doublePosition.length != 3)
			throw new IllegalArgumentException();
		int[] intPosition = new int[3];
		for (int i=0; i<3; i++) {
			intPosition[i] = (int) doublePosition[i];
		}
		return intPosition;
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
	@Model
	private boolean canHaveAsPosition(double[] position) {
		boolean valid = true;
		if (!(position instanceof double[]) || position.length != 3 )
			valid = false;
		for (double coordinate: position) {
			if (coordinate < 0.0 || coordinate > 50.0)
				valid = false;
		}
		return valid;
	}
	
	/**
	 * Return the position of the cube in the game world occupied by 
	 * the unit.
	 */
	public int[] getCubeCoordinate() {
		return convertPositionToInt(getPosition());
	}
	
	/**
	 * Return the exact position of the unit in the game world.
	 */
	@Basic
	public double[] getPosition() {
		return this.position;
	}
	
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
	private void setPosition(double[] position) throws IllegalPositionException {
		if (! canHaveAsPosition(position) )
			throw new IllegalPositionException(position, this);
		this.position[0] = position[0];
		this.position[1] = position[1];
		this.position[2] = position[2];
	}
	
	/**
	 * Variable registering the position of the unit in the game world.
	 */
	private double position[] = new double[3];
	
	/**
	 * Return the current name of the unit.
	 */
	@Basic
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
	 * 		| !canHaveAsName(newName)
	 */
	public void setName(String newName) throws IllegalNameException {
		if (! canHaveAsName(newName))
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
	private boolean canHaveAsName(String name) {
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
	@Basic
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 * Set the weight of the unit to the given new value.
	 * 
	 * @param newValue
	 * 			The new weight for this unit.
	 * @post if the given weight is between 1 and 200 (inclusively) and
	 * 			larger than the mean of the strength and the agility
	 * 			of the unit, the new weight of the unit is equal to the given
	 * 			weight.
	 * 		| if (isInteger(newValue) && newValue > 0
	 * 		| 	&& newValue < 201 && newValue >= (getStrength()+getAgility())/2)
	 * 		| then new.getWeight() == newValue
	 */
	public void setWeight(int newValue) {
		if (Integer.class.isInstance(newValue) && newValue > 0 && newValue < 201 
				&& newValue >= (getStrength()+getAgility())/2 )
			this.weight = newValue;
	}
	
	/**
	 * Variable registering the weight of the unit.
	 */
	private int weight;
	
	/**
	 * Return the units strength.
	 */
	@Basic
	public int getStrength() {
		return this.strength;
	}
	
	/**
	 * Set the strength of the unit to the given new value.
	 * 
	 * @param newValue
	 * 			The new strength for this unit.
	 * @post if the given strength is between 1 and 200 (inclusively) then
	 * 			 the new strength of the unit is equal to the given weight.
	 * 		| if (isInteger(newValue) && newValue > 0
	 * 		| 	&& newValue < 201
	 * 		| then new.getStrength() == newValue
	 */
	public void setStrength(int newValue) {
		if (Integer.class.isInstance(newValue) && newValue > 0 && newValue < 201)
			this.strength = newValue;
	}
	
	/**
	 * Variable registering the strength of the unit.
	 */
	private int strength;
	
	/**
	 * Return the units agility.
	 */
	@Basic
	public int getAgility() {
		return this.agility;
	}
	
	/**
	 * Set the agility of the unit to the given new value.
	 * 
	 * @param newValue
	 * 			The new agility for this unit.
	 * @post if the given agility is between 1 and 200 (inclusively) then
	 * 			 the new agility of the unit is equal to the given agility.
	 * 		| if (isInteger(newValue) && newValue > 0
	 * 		| 	&& newValue < 201
	 * 		| then new.getAgility() == newValue
	 */
	public void setAgility(int newValue) {
		if (Integer.class.isInstance(newValue) && newValue > 0 && newValue < 201)
			this.agility = newValue;
	}
	
	/**
	 * Variable registering the agility of the unit.
	 */
	private int agility;
	
	/**
	 * Return the units toughness.
	 */
	@Basic
	public int getToughness() {
		return this.toughness;
	}
	
	/**
	 * Set the toughness of the unit to the given new value.
	 * 
	 * @param newValue
	 * 			The new toughness for this unit.
	 * @post if the given toughness is between 1 and 200 (inclusively) then
	 * 			 the new toughness of the unit is equal to the given toughness.
	 * 		| if (isInteger(newValue) && newValue > 0
	 * 		| 	&& newValue < 201
	 * 		| then new.getToughness() == newValue
	 */
	public void setToughness(int newValue) {
		if (Integer.class.isInstance(newValue) && newValue > 0 && newValue < 201)
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
	public int getMaxHitPoints() {
		return (int) Math.ceil(
				200*(getWeight()/100.0)*(getToughness()/100.0));
	}
	
	
	/**
	 * Return the units minimum number of hitpoints.
	 */
	@Basic @Immutable @Model
	private int getMinHitPoints() {
		return this.minHitPoints;
	}
	
	/**
	 * Return the units current number of hitpoints.
	 */
	@Basic
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
	private boolean isLegalHitPoints(int value) {
		return (value >= getMinHitPoints() && value <= getMaxHitPoints());
	}
	
	/**
	 * Update the current number of hitpoints of the unit.
	 * 
	 * @param newValue
	 * 			The new value for the current no. of hitpoints of the unit.
	 * 
	 * @pre	the given new value must be a valid hitpoints value for this unit.
	 * 		| isLegalHitPoints(newValue)
	 * 
	 * @post The new no. of hitpoints of the unit is equal to
	 * 		the given new value.
	 * 		| new.getCurrentHitPoints() == newValue
	 * 
	 */
	private void updateCurrentHitPoints(int newValue) {
		assert isLegalHitPoints(newValue);
		this.currentHitPoints = newValue;
	}
	
	/**
	 * Variable registering the current number of hitpoints of the unit.
	 */
	private int currentHitPoints;
	
	/**
	 * Variable registering the minimum number of hitpoints of the unit.
	 */
	private final int minHitPoints;
	
	
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
	public int getMaxStaminaPoints() {
		return (int) Math.ceil(
				200*(getWeight()/100.0)*(getToughness()/100.0));
	}
	
	/**
	 * Return the units minimum number of stamina points.
	 */
	@Basic @Immutable @Model
	private int getMinStaminaPoints() {
		return this.minStaminaPoints;
	}

	
	/**
	 * Return the units current number of stamina points.
	 */
	@Basic
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
	private boolean isLegalStaminaPoints(int value) {
		return (value >= getMinStaminaPoints() && value <= getMaxStaminaPoints());
	}
	
	/**
	 * Update the current number of stamina points of the unit.
	 * 
	 * @param newValue
	 * 			The new value for the current no. of stamina points of the unit.
	 * 
	 * @pre	the given new value must be a valid stamina points value for this unit.
	 * 		| isLegalStaminaPoints(newValue)
	 * 
	 * @post The new no. of stamina points of the unit is equal to
	 * 		the given new value.
	 * 		| new.getCurrentStaminaPoints() == newValue;
	 * 
	 */
	private void updateCurrentStaminaPoints(int newValue) {
		assert isLegalStaminaPoints(newValue);
		this.currentStaminaPoints = newValue;
	}
	
	/**
	 * Variable registering the current number of stamina points of the unit.
	 */
	private int currentStaminaPoints;
	
	/**
	 * Variable registering the minimum number of stamina points of the unit.
	 */
	private final int minStaminaPoints;
	
	
	
	/**
	 * Check if a given value is a valid game time dt value.
	 * 
	 * @param dt
	 * 			The value to be checked.
	 * @return true if and only if the value is larger than or equal to
	 * 			zero and smaller than 0.2.
	 * 		| result =  (dt >= 0 && dt < 0.2 )
	 */
	@Model
	private boolean isValidDT(double dt) {
		return (Double.class.isInstance(dt) && dt < 0.2 && dt >= 0.0);
	}
	
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
													IllegalArgumentException {
		if (! isValidDT(dt)) {
			throw new IllegalArgumentException();
		}
		if (!isResting()) {
			setTimeAfterResting(getTimeAfterResting() + dt);
		}
		
		if (getTimeAfterResting() >= 180.0) {
			startResting();
		}
		
		if (isResting()) {
			controlResting(dt);
		}
		else if (getState() == State.EMPTY) {
			if (this.destCubeLT != null && this.getCubeCoordinate() != this.destCubeLT) {
				moveTo(this.destCubeLT);
			}
			controlWaiting(dt);
		}
		else if (isMoving()) {
			controlMoving(dt);
		}
		else if (isAttacking()) {
			controlAttacking(dt);
		}
		else if (isResting()) {
			controlResting(dt);
		}
		else if (isWorking()) {
			controlWorking(dt);
		}
		
	}
	
	/**
	 * Manage the waiting of the unit, i.e. when the state of the unit is equal to empty.
	 * 
	 * @param dt
	 */
	private void controlWaiting(double dt) throws IllegalPositionException {
		
		if (isDefaultBehaviorEnabled()) {
			double dice = this.random.nextDouble();
			if (dice < 1.0/3.0) {
				
				int[] destCube = new int[3];
				for (int i=0; i<3; i++) {
					destCube[i] = this.random.nextInt(49);
				}
				moveTo(destCube);
				
			}
			if (dice >1.0/3.0 && dice < 2.0/3.0) {
				startWorking();
			}
			else {
				startResting();
			}
			if (isMoving()) {
				if (dice < 0.5) {
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
	
		if (!isAttacked()) {
		
			if (! reached(dt)) {
				if (isSprinting()) {
					updateCurrentStaminaPoints( (int) (getCurrentStaminaPoints() - (dt/0.1)));
					if (getCurrentStaminaPoints() <= 0) {
						stopSprinting();
						updateCurrentStaminaPoints(0);
					}
				}
				updatePosition(dt);
			}
			else if (reached(dt)) {
				
				setPosition(getDestination());
				if (isSprinting()) stopSprinting();
				setState(State.EMPTY);
			}
			
			
			if (getCubeCoordinate() == getDestCubeLT()) {
				setDestCubeLT(null);
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
	 * @post	The destination of the unit is set to the right adjacent cube.
	 * 			| new.getDestination() == determineCube(cubeDirection)
	 * 
	 * @post	The moving direction of the unit is set towards the destination.
	 * 			cube.
	 * 			| new.movingDirection == new.getDestination() - this.getPosition()
	 * 
	 * @post	The orientation of the unit is set towards the destination.
	 * 			| new.getOrientation() == Math.atan2(getVelocity()[0], getVelocity()[2] )
	 * 
	 * @throws IllegalArgumentException
	 * 			The given direction is not a valid direction.
	 * 			| !(cubeDirection instanceof int[]) || cubeDirection.length != 3 )
	 */
	public void moveToAdjacent(int... cubeDirection) 
			throws IllegalPositionException, IllegalArgumentException {
		
		if (!(cubeDirection instanceof int[]) || cubeDirection.length != 3 )
			throw new IllegalArgumentException();
		
		if (!isMoving() && !isAttacked() && getState() != State.RESTING_1) {
			startMoving();
			
			double[] newPosition = new double[3];
			double[] doubleCubeDirection = convertPositionToDouble(cubeDirection.clone());
			double[] direction = new double[3];
			
			for (int i=0; i<3; ++i) {
				newPosition[i] = (int) (getPosition()[i] 
						+ doubleCubeDirection[i]);
				newPosition[i] += getCubeLength()/2.0;
				
				direction[i] = newPosition[i] - getPosition()[i];
			}
			
			setDestination(newPosition);
			this.movingDirection = direction;
			setOrientation((float) Math.atan2(getVelocity()[0], getVelocity()[2] ));
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
	public void moveTo(int[] destCube) throws IllegalPositionException,
						IllegalArgumentException {
		
		if (!canHaveAsPosition(convertPositionToDouble(destCube))) {
			throw new IllegalPositionException(destCube, this);
		}
		
		if (getState() != State.RESTING_1) {
			setDestCubeLT(destCube);
			int[] startCube;
			while ((getCubeCoordinate() != destCube)) {
				int x, y, z;
				startCube = getCubeCoordinate();
				
				if (startCube[0] == destCube[0])  x = 0;
				else if (startCube[0] < destCube[0]) x = 1;
				else x = -1;
				
				if (startCube[1] == destCube[1])  y = 0;
				else if (startCube[1] < destCube[1]) y = 1;
				else y = -1;
				
				if (startCube[2] == destCube[2])  z = 0;
				else if (startCube[2] < destCube[2]) z = 1;
				else z = -1;
				
				moveToAdjacent(x, y, z);
			}
		}
	}

	/**
	 * Returns the destination of the unit.
	 */
	@Basic @Model
	private double[] getDestination() {
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
	private void setDestination(double[] newDestination) throws IllegalPositionException {
		if (!canHaveAsPosition(newDestination)) {
			throw new IllegalPositionException(newDestination, this);
		}
		for (int i=0; i<3; i++) {
			this.destination[i] = newDestination[i];
		}
	}
	
	/**
	 * Returns the long term destination of the unit.
	 */
	@Basic
	private int[] getDestCubeLT() {
		return this.destCubeLT;
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
	private void setDestCubeLT(int[] destCubeLT) throws IllegalPositionException {
		if ( !(canHaveAsPosition(convertPositionToDouble(destCubeLT)) || destCubeLT == null) )
			throw new IllegalPositionException(destCubeLT, this);
		this.destCubeLT = destCubeLT;
	}
	
	
	/**
	 * Variable registering the destination of the unit in the game world.
	 */
	private double[] destination = new double[3];
	
	/**
	 * Variable registering the long term destination of the unit in the game world.
	 */
	private int[] destCubeLT = null;
	
	
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
		return (getDistanceToDestination(getPosition()) < getCurrentSpeed()*dt);
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
	private double getDistanceToDestination(double[] position) throws IllegalPositionException {
		if (!canHaveAsPosition(position))
			throw new IllegalPositionException(position, this);
		
		return Math.sqrt(Math.pow(position[0]-getDestination()[0],2)
				+ Math.pow(position[1]-getDestination()[1],2) 
				+ Math.pow(position[2]-getDestination()[2],2));
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
	private void updatePosition(double dt) throws IllegalPositionException {
		double[] newPosition = new double[3];
		for (int i=0; i<3; ++i) {
			newPosition[i] = getPosition()[i] + getVelocity()[i]*dt;
		}
		setPosition(newPosition);
	}
	
	
	/**
	 * Returns the moving direction of the unit.
	 */
	@Basic
	private double[] getMovingDirection() {
		return this.movingDirection;
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
	@Model
	private double[] getVelocity() throws IllegalPositionException {
		/*double d = Math.sqrt(Math.pow(getMovingDirection()[0],2)
				+ Math.pow(getMovingDirection()[1],2) + Math.pow(getMovingDirection()[2],2));*/
		double d = getDistanceToDestination(getPosition());
		double[] v = new double[3];
		for (int i=0; i<3; ++i) {
			v[i] = getCurrentSpeed()*getMovingDirection()[i]/d;
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
		if (isSprinting())
			return 3.0*(getStrength()+getAgility())/(2.0*getWeight());
		else
			return 1.5*(getStrength()+getAgility())/(2.0*getWeight());
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
			if (this.getMovingDirection()[2] == 1.0)
				return 0.5*getBaseSpeed();
			else if (this.getMovingDirection()[2] == -1.0)
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
		if (isMoving() && getCurrentStaminaPoints() > 0) {
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
	@Basic
	public float getOrientation() {
		return orientation;
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
	private void setOrientation(float newValue) {
		if (newValue == (float) newValue && newValue >= 0.0 && newValue < 2*Math.PI) {
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
		setTimeToCompletion((float) (getTimeToCompletion() - dt));
		if (getTimeToCompletion() < 0.0) {
			setState(State.EMPTY);
		}
	}
	
	/**
	 * Makes the unit work.
	 * 
	 * @post	necessary???
	 * 
	 */
	public void work()  {
		if (!isMoving() && getState() != State.RESTING_1) {
			startWorking();
		}
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
		setTimeToCompletion((float) (getTimeToCompletion() - dt));
		if (getTimeToCompletion() < 0.0) {
			
			getDefender().setAttacked(true);
			getDefender().defend(this);
			getDefender().setAttacked(false);
			setState(State.EMPTY);
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
		
		if (!isMoving()) {
			startAttacking();
			
			// Orientation update
			this.setOrientation((float)Math.atan2(defender.getPosition()[1]-this.getPosition()[1],
					defender.getPosition()[0]-this.getPosition()[0]));
			
			defender.setOrientation((float)Math.atan2(this.getPosition()[1]-defender.getPosition()[1],
					this.getPosition()[0]-defender.getPosition()[0]));
			
			
			this.defender = defender;
		}
	}
	
	@Model
	private boolean canAttack(Unit victim) {
		
	}
	
	
	/**
	 * Returns the time left for a unit to complete its work or attack.
	 */
	@Basic
	private float getTimeToCompletion() {
		return this.timeToCompletion;
	}
	
	/**
	 * 	@throws IllegalTimeException
	 * 			The set time is not valid.
	 * 			| !(canHaveAsTime(1.0f))
	 * 
	 */
	private boolean canHaveAsTime(float value) {
		 return ( Float.class.isInstance(value) && (value >= -0.5f) );
	}
	
	private void setTimeToCompletion(float newValue) throws IllegalTimeException {
		if (!canHaveAsTime(newValue))
			throw new IllegalTimeException(newValue, this);
		this.timeToCompletion = newValue;
	}
	
	private float timeToCompletion;
	
	
	private void defend(Unit attacker) throws IllegalPositionException {
		boolean dodged = this.dodge(attacker);
		if (!dodged) {
			boolean blocked = this.block(attacker);
			if (!blocked) {
				this.takeDamage(attacker);
			}
		}
	}
	
	private boolean dodge(Unit attacker) throws IllegalPositionException {
		if ( this.random.nextDouble() <= 0.20*(this.getAgility()/attacker.getAgility())) {
			this.jumpToRandomAdjacent();
			return true;
		}
		return false;
	}
	
	private void jumpToRandomAdjacent() throws IllegalPositionException {
		double[] newPosition = this.getPosition();
		newPosition[0] += 2*this.random.nextDouble() - 1.0;
		newPosition[1] += 2*this.random.nextDouble() - 1.0;
		if (!canHaveAsPosition(newPosition)) throw new 
					IllegalPositionException(newPosition, this);
		this.setPosition(newPosition);
	}
	
	private boolean block(Unit attacker) {
		double probability = 0.25*((this.getAgility() + this.getStrength())
				/(attacker.getAgility() + attacker.getStrength()));
		if ( this.random.nextDouble() <= probability ) {
			return true;
		}
		return false;
	}
	
	private void takeDamage(Unit attacker) {
		this.updateCurrentHitPoints(this.getCurrentHitPoints() - 
				(attacker.getStrength()/10));
	}
	
	/**
	 * Returns the unit this unit is attacking.
	 */
	@Basic
	private Unit getDefender() {
		return this.defender;
	}
	
	
	private Unit defender;
	
	
	@Basic
	private boolean isAttacked() {
		return this.isAttacked;
	}
	
	private void setAttacked(boolean isAttacked) {
		this.isAttacked = isAttacked;
	}
	
	private boolean isAttacked;
	
	@Basic 
	private double getTimeResting() {
		return this.timeResting;
	}
	
	@Basic 
	private double getTimeAfterResting() {
		return this.timeAfterResting;
	}
	
	/**
	 * Returns true if the unit is resting.
	 * 
	 * @return	True if and only if the unit is resting.
	 * 			| result = (getState() == State.RESTING_HP || getState() == State.RESTING_STAM
				|		|| getState() == State.RESTING_1)
	 */
	public boolean isResting() {
		return (getState() == State.RESTING_HP || getState() == State.RESTING_STAM
				|| getState() == State.RESTING_1);
	}
	
	private void startResting() {
		setState(State.RESTING_1);
		setTimeResting(0.0);
	}
	
	private void controlResting(double dt) {
		setTimeAfterResting(0.0);
		setTimeResting(getTimeResting() + dt);
		
		while (!isAttacked() && !isAttacking()) {
			
			if (getState() == State.RESTING_1) {
				if ((getTimeResting() * getToughness())/(0.2*200) > 1) {
						
					while (getTimeResting() - 0.2 > 0.0) {
						updateCurrentHitPoints(getCurrentHitPoints() + (getToughness()/200));
						setTimeResting(getTimeResting() - 0.2);
					}
					
					if (this.getCurrentHitPoints() < this.getMaxHitPoints()) {
						setState(State.RESTING_HP);
					}
					else if (this.getCurrentStaminaPoints() < this.getMaxStaminaPoints()) {
						setState(State.RESTING_STAM);
					}
				}
			}
			
			if (getState() == State.RESTING_HP) {
				if (getTimeResting() - 0.2 > 0.0) {
					updateCurrentHitPoints(getCurrentHitPoints() + (getToughness()/200));
					setTimeResting(getTimeResting() - 0.2);
				}
			}
			else {
				if (getTimeResting() - 0.2 > 0.0) {
					updateCurrentStaminaPoints(getCurrentStaminaPoints() + (getToughness()/100));
					setTimeResting(getTimeResting() - 0.2);
				}
			}
			if (getCurrentHitPoints() == getMaxHitPoints() &&
					getCurrentStaminaPoints() == getMaxStaminaPoints()) {
				setState(State.EMPTY);
			}
		}
	}
	
	public void rest() {
		if (!isMoving()) {
			startResting();
		}
	}
	
	private void setTimeResting(double newValue) {
		this.timeResting = newValue;
	}
	
	private void setTimeAfterResting(double newValue) {
		this.timeAfterResting = newValue;
	}
	
	private double timeResting = 0.0;
	private double timeAfterResting = 0.0;
	
	
	@Basic
	public boolean isDefaultBehaviorEnabled() {
		return this.defaultBehavior;
	}
	
	public void setDefaultBehaviorEnabled(boolean value) {
		if (value == true && isDefaultBehaviorEnabled() == false) {
			startDefaultBehavior();
		}
		else if (value == false && isDefaultBehaviorEnabled() == true) {
			stopDefaultBehavior();
		}
	}
	
	public void startDefaultBehavior() {
		this.defaultBehavior = true;
	}
	
	public void stopDefaultBehavior() {
		this.defaultBehavior = false;
	}
	
	
	@Basic @Model
	private State getState() {
		return this.state;
	}
	
	private void setState(State state) {
		this.state = state;
	}
	
	private State state;
	private boolean defaultBehavior;
	private Random random;
	
	
	private static double getCubeLength() {
		return cubeLength;
	}
	
	/**
	 * Variable registering the length of a cube of the game world.
	 */
	private final static double cubeLength = 1.0;
	
	
	/* Class invars */
	/* IETS MIS MET POSITIONERING */
	/* Kijken of geen private method gebruikt w in formele doc van een public method 
	 * 		-> anders @Model
	 */
	/* LOOP INVARIANTS etc */
	/* RRAAAAAAAW */
	/* IllegalArgumentExceptions toevoegen */
	/* Documentation toevoegen */
}

