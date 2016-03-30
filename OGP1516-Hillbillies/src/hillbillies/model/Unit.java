package hillbillies.model;


import java.util.regex.*;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

import be.kuleuven.cs.som.taglet.PostTaglet;

import java.util.*;

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
*@version	1.0
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
	 * @post The new maximum no. of hitpoints and current no. of hitpoints 
	 * 		of the unit is equal to
	 * 		200 times the product of the weight divided by 100 and the toughness
	 * 		divided by 100, rounded up to the next integer.
	 * 		| new.getMaxHitPoints() = Math.ceil(200*(getWeight()/100.0)*
	 * 		|	(getToughness()/100.0));
	 * 
	 * @post The new maximum no. of stamina points and current no. of stamina
	 * 		points of the unit is equal to
	 * 		200 times the product of the weight divided by 100 and the toughness
	 * 		divided by 100, rounded up to the next integer.
	 * 		| new.getMaxStaminaPoints() = Math.ceil(200*(getWeight()/100.0)*
	 * 		|	(getToughness()/100.0));
	 * 
	 * @post The new orientation of the unit is equal to Pi/2
	 * 		| new.getOrientation() = Math.PI/2
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
		/*if (! canHaveAsPosition(doubleInitialPosition))
			throw new IllegalPositionException(
					doubleInitialPosition, this);*/
		setPosition(doubleInitialPosition);
		
		/*if (! canHaveAsName(name))
			throw new IllegalNameException(name, this);*/
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
		
		this.currentHitPoints = this.currentStaminaPoints = this.maxHitPoints 
				= this.maxStaminaPoints = (int) Math.ceil(
				200*(getWeight()/100.0)*(getToughness()/100.0));
		
		setOrientation(Math.PI/2.0);
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
	public double[] convertPositionToDouble(int[] intPosition) 
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
	public int[] convertPositionToInt(double[] doublePosition) 
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
	public boolean canHaveAsPosition(double[] position) {
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
	 * Set the position of the unit to the given position.
	 * 
	 * @param position
	 * 			The new position for this unit.
	 * 
	 * @post The new position of this unit is equal to the
	 * 		given position.
	 * 		| new.getPosition() == position
	 * 
	 * @throws IllegalPositionException
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
	 * Return the position of the cube in the game world occupied by 
	 * the unit.
	 */
	public int[] getCubeCoordinate() {
		return convertPositionToInt(getPosition());
	}
	
	/**
	 * Return the exact position of the unit in the game world.
	 */
	public double[] getPosition() {
		return this.position;
	}
	
	/**
	 * Variable registering the position of the unit in the game world.
	 */
	private double position[] = new double[3];
	
	/**
	 * Return the current name of the unit.
	 */
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
	 * @throws IllegalNameException
	 * 			The given position is not valid.
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
	public boolean canHaveAsName(String name) {
		return (name != null && name.length() > 1 
				&& Character.isUpperCase(name.charAt(0)) 
				&& name.matches("[a-zA-Z\\s\'\"]+"));
	}
	
	/**
	 * Variable registering the name of the unit.
	 */
	private String name;
	
	
	/**
	 * Return the units' weight.
	 */
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
		if (newValue == (int) newValue && newValue > 0 && newValue < 201 
				&& newValue >= (getStrength()+getAgility())/2 )
			this.weight = newValue;
	}
	
	/**
	 * Variable registering the weight of the unit.
	 */
	private int weight;
	
	/**
	 * Return the units' strength.
	 */
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
		if (newValue == (int) newValue && newValue > 0 && newValue < 201)
			this.strength = newValue;
	}
	
	/**
	 * Variable registering the strength of the unit.
	 */
	private int strength;
	
	/**
	 * Return the units' agility.
	 */
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
		if (newValue == (int) newValue && newValue > 0 && newValue < 201)
			this.agility = newValue;
	}
	
	/**
	 * Variable registering the agility of the unit.
	 */
	private int agility;
	
	/**
	 * Return the units' toughness.
	 */
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
		if (newValue == (int) newValue && newValue > 0 && newValue < 201)
			this.toughness = newValue;
	}
	
	/**
	 * Variable registering the toughness of the unit.
	 */
	private int toughness;
	
	/**
	 * Return the units' maximum number of hitpoints.
	 */
	public int getMaxHitPoints() {
		return this.maxHitPoints;
	}
	
	/**
	 * Update the maximum number of hitpoints of the unit.
	 * 
	 * @post The new maximum no. of hitpoints of the unit is equal to
	 * 		200 times the product of the weight divided by 100 and the toughness
	 * 		divided by 100, rounded up to the next integer.
	 * 		| new.getMaxHitPoints() = Math.ceil(200*(getWeight()/100.0)*
	 * 		|	(getToughness()/100.0));
	 * 
	 */
	public void updateMaxHitPoints() {
		this.maxHitPoints = (int) Math.ceil(
				200*(getWeight()/100.0)*(getToughness()/100.0));
	}
	
	/**
	 * Variable registering the maximum number of hitpoints of the unit.
	 */
	private int maxHitPoints;
	
	
	/**
	 * Return the units' current number of hitpoints.
	 */
	public int getCurrentHitPoints() {
		return this.currentHitPoints;
	}
	
	/**
	 * Update the current number of hitpoints of the unit.
	 * 
	 * @param newValue
	 * 			The new value for the current no. of hitpoints of the unit.
	 * 
	 * @pre the given new value must be larger than or equal to zero and 
	 * 		less than or equal to the max no. of hitpoints
	 * 		| newValue >= 0 && newValue <= getMaxHitPoints()
	 * 
	 * @post The new no. of hitpoints of the unit is equal to
	 * 		the given new value.
	 * 
	 */
	public void updateCurrentHitPoints(int newValue) {
		this.currentHitPoints = newValue;
	}
	
	/**
	 * Variable registering the current number of hitpoints of the unit.
	 */
	private int currentHitPoints;
	
	/**
	 * Return the units' maximum number of stamina points.
	 */
	public int getMaxStaminaPoints() {
		return this.maxStaminaPoints;
	}
	
	/**
	 * Update the maximum number of stamina points of the unit.
	 * 
	 * @post The new maximum no. of stamina points of the unit is equal to
	 * 		200 times the product of the weight divided by 100 and the toughness
	 * 		divided by 100, rounded up to the next integer.
	 * 		| new.getMaxStaminaPoints() = Math.ceil(200*(getWeight()/100.0)*
	 * 		|	(getToughness()/100.0));
	 * 
	 */
	public void updateMaxStaminaPoints() {
		this.maxStaminaPoints = (int) Math.ceil(
				200*(getWeight()/100.0)*(getToughness()/100.0));
	}
	
	/**
	 * Variable registering the maximum number of stamina points of the unit.
	 */
	private int maxStaminaPoints;
	
	/**
	 * Return the units' current number of stamina points.
	 */
	public int getCurrentStaminaPoints() {
		return this.currentStaminaPoints;
	}
	
	/**
	 * Update the current number of stamina points of the unit.
	 * 
	 * @param newValue
	 * 			The new value for the current no. of stamina points of the unit.
	 * 
	 * @pre the given new value must be larger than or equal to zero and 
	 * 		less than or equal to the max no. of stamina points
	 * 		| newValue >= 0 && newValue <= getMaxStaminaPoints()
	 * 
	 * @post The new no. of stamina points of the unit is equal to
	 * 		the given new value.
	 * 
	 */
	public void updateCurrentStaminaPoints(int newValue) {
		this.currentStaminaPoints = newValue;
	}
	
	/**
	 * Variable registering the current number of stamina points of the unit.
	 */
	private int currentStaminaPoints;
	
	
	/* DEFENSIVE PROGR --> ADD DOCUMENTATION + EXCEPTIONS!!!! */
	public void advanceTime(double dt) throws IllegalPositionException, 
													IllegalArgumentException {
		if (dt > 0.2 || dt < 0.0) {
			throw new IllegalArgumentException();
		}
		if (!isResting()) {
			this.timeAfterResting += dt;
		}
		if (this.timeAfterResting >= 180.0) {
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
	
	private double timeAfterResting = 0.0;
	
	private void controlWaiting(double dt) {
		if (isDefaultBehaviorEnabled()) {
			Random random = new Random();
			double dice = random.nextDouble();
			if (dice < 1.0/3.0) {
				
				int[] destCube = new int[3];
				for (int i=0; i<3; i++) {
					destCube[i] = random.nextInt(49);
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
	
	private void startMoving() {
		setState(State.MOVING);
	}
	
	private void controlMoving(double dt) {
	
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
				
				setPosition(this.destination);
				if (isSprinting()) stopSprinting();
				setState(State.EMPTY);
			}
			
			
			if (this.getCubeCoordinate() == this.destCubeLT) {
				this.destCubeLT = null;
			}
			
		}
		else {
			setState(State.EMPTY);
		}
		
	}
	
	private final static double cubeLength = 1.0;
	
	
	public void moveToAdjacent(int... cubeDirection) 
			throws IllegalArgumentException, IllegalPositionException {
		if (!isMoving() && !isAttacked() && getState() != State.RESTING_1) {
			startMoving();
			
			double[] newPosition = new double[3];
			double[] doubleCubeDirection = convertPositionToDouble(cubeDirection.clone());
			double[] direction = new double[3];
			
			for (int i=0; i<3; ++i) {
				newPosition[i] = (int) (getPosition()[i] 
						+ doubleCubeDirection[i]);
				newPosition[i] += cubeLength/2.0;
				
				direction[i] = newPosition[i] - getPosition()[i];
			}
			
			setDestination(newPosition);
			this.movingDirection = direction;
			setOrientation(Math.atan2(getVelocity()[0], getVelocity()[2] ));
		}
	}
	
	/* DEFENSIVE PROGR*/
	public void moveTo(int[] destCube) {
		if (getState() != State.RESTING_1) {
			this.destCubeLT = destCube;
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
	//////////////////////////////////////////////
	
	
	private double[] getDestination() {
		return this.destination;
	}
	
	private void setDestination(double[] newDestination) throws IllegalArgumentException,
						IllegalPositionException {
		if ( !(newDestination instanceof double[]) || newDestination.length != 3)
			throw new IllegalArgumentException();
		if (!canHaveAsPosition(newDestination)) {
			throw new IllegalPositionException(newDestination, this);
		}
		for (int i=0; i<3; i++) {
			this.destination[i] = newDestination[i];
		}
	}
	
	private double[] destination = new double[3];
	private int[] destCubeLT = null;
	
	private void updatePosition(double dt) throws IllegalPositionException {
		double[] newPosition = new double[3];
		for (int i=0; i<3; ++i) {
			newPosition[i] = getPosition()[i] + getVelocity()[i]*dt;
		}
		setPosition(newPosition);
	}
	
	private boolean reached(double dt) {
		double d = Math.sqrt(Math.pow(getPosition()[0]-getDestination()[0],2)
				+ Math.pow(getPosition()[1]-getDestination()[1],2) 
				+ Math.pow(getPosition()[2]-getDestination()[2],2));
		if (d<0.2) return true;
		while (dt > 0.01) {
			dt -= 0.01;
			double[] tempPosition = new double[3];
			for (int i=0; i<3; ++i) {
				tempPosition[i] = getPosition()[i] - getVelocity()[i]*dt;
			}
			double d2 = Math.sqrt(Math.pow(tempPosition[0]-getDestination()[0],2)
					+ Math.pow(tempPosition[1]-getDestination()[1],2) 
					+ Math.pow(tempPosition[2]-getDestination()[2],2));
			if (d2<0.2) return true;
		}
		return false;
	}
	
	public double[] getMovingDirection() {
		return this.movingDirection;
	}
	private double[] movingDirection;
	
	
	private double[] getVelocity() {
		double d = Math.sqrt(Math.pow(getMovingDirection()[0],2)
				+ Math.pow(getMovingDirection()[1],2) + Math.pow(getMovingDirection()[2],2));
		double[] v = new double[3];
		for (int i=0; i<3; ++i) {
			v[i] = getCurrentSpeed()*getMovingDirection()[i]/d;
		}
		return v;
	}
	
	private double getBaseSpeed() {
		if (isSprinting())
			return 3*(getStrength()+getAgility())/(2.0*getWeight());
		return 1.5*(getStrength()+getAgility())/(2.0*getWeight());
	}
	
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
	
	public boolean isMoving() {
		return (getState() == State.MOVING);
	}
	
	public boolean isSprinting() {
		return this.isSprinting;
	}
	
	public void startSprinting() {
		if (isMoving() && getCurrentStaminaPoints() > 0) {
			this.isSprinting = true;
		}
	}
	
	public void stopSprinting() {
		this.isSprinting = false;
	}
	
	private boolean isSprinting;
	
	/* TOTAL PROGRAMMING*/
	public double getOrientation() {
		return orientation;
	}
	public void setOrientation(double newValue) {
		this.orientation = newValue;
	}
	private double orientation;
	
	
	/* DEFENSIVE PROGR*/
	
	public boolean isWorking() {
		return (getState() == State.WORKING);
	}
	
	
	private void startWorking() {
		setState(State.WORKING);
		this.timeToCompletion = 500.0f/getStrength();
	}
	
	private void controlWorking(double dt) {
		this.timeToCompletion -= dt;
		if (this.timeToCompletion < 0.0) {
			setState(State.EMPTY);
		}
	}
	
	public void work() throws IllegalStateException {
		if (!isMoving() && getState() != State.RESTING_1) {
			startWorking();
		}
	}
	
	private float timeToCompletion;
	

	private void startAttacking() {
		setState(State.ATTACKING);
		this.timeToCompletion = 1.0f;
	}
	
	private void controlAttacking(double dt) {
		this.timeToCompletion -= dt;
		if (this.timeToCompletion < 0.0) {
			
			getDefender().setAttacked(true);
			getDefender().defend(this);
			getDefender().setAttacked(false);
			setState(State.EMPTY);
		}
	}
	
	public void attack(Unit defender) throws IllegalPositionException {
		if (!isMoving()) {
			startAttacking();
			
			// Orientation update
			this.setOrientation(Math.atan2(defender.getPosition()[1]-this.getPosition()[1],
					defender.getPosition()[0]-this.getPosition()[0]));
			
			defender.setOrientation(Math.atan2(this.getPosition()[1]-defender.getPosition()[1],
					this.getPosition()[0]-defender.getPosition()[0]));
			
			
			this.defender = defender;
		}
	}
	
	private Unit getDefender() {
		return this.defender;
	}
	
	private Unit defender;
	
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
		Random random = new Random();
		if ( random.nextDouble() <= 0.20*(this.getAgility()/attacker.getAgility())) {
			this.jumpToRandomAdjacent();
			return true;
		}
		return false;
	}
	
	private void jumpToRandomAdjacent() throws IllegalPositionException {
		Random random = new Random();
		double[] newPosition = this.getPosition();
		newPosition[0] += 2*random.nextDouble() - 1.0;
		newPosition[1] += 2*random.nextDouble() - 1.0;
		if (!canHaveAsPosition(newPosition)) throw new 
					IllegalPositionException(newPosition, this);
		this.setPosition(newPosition);
	}
	
	private boolean block(Unit attacker) {
		Random random = new Random();
		double probability = 0.25*((this.getAgility() + this.getStrength())
				/(attacker.getAgility() + attacker.getStrength()));
		if ( random.nextDouble() <= probability ) {
			return true;
		}
		return false;
	}
	
	private void takeDamage(Unit attacker) {
		this.updateCurrentHitPoints(this.getCurrentHitPoints() - 
				(attacker.getStrength()/10));
	}
	
	public boolean isAttacking() {
		return (getState() == State.ATTACKING);
	}
	
	private boolean isAttacked() {
		return this.isAttacked;
	}
	
	private void setAttacked(boolean isAttacked) {
		this.isAttacked = isAttacked;
	}
	
	private boolean isAttacked;
	
	
	private void startResting() {
		setState(State.RESTING_1);
		this.timeResting = 0.0;
	}
	
	private void controlResting(double dt) {
		
		if (!isAttacked()) {
			this.timeAfterResting = 0.0;
			this.timeResting += dt;
			
			if (getState() == State.RESTING_1) {
				if ((this.timeResting * getToughness())/(0.2*200) > 1) {
						
					while (this.timeResting - 0.2 > 0.0) {
						updateCurrentHitPoints(getCurrentHitPoints() + (getToughness()/200));
						this.timeResting -= 0.2;
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
				if (this.timeResting - 0.2 > 0.0) {
					updateCurrentHitPoints(getCurrentHitPoints() + (getToughness()/200));
					this.timeResting -= 0.2;
				}
			}
			else {
				if (this.timeResting - 0.2 > 0.0) {
					updateCurrentStaminaPoints(getCurrentStaminaPoints() + (getToughness()/100));
					this.timeResting -= 0.2;
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
	
	public boolean isResting() {
		return (getState() == State.RESTING_HP || getState() == State.RESTING_STAM
				|| getState() == State.RESTING_1);
	}
	
	private double timeResting = 0.0;
	
	
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
	
	public boolean isDefaultBehaviorEnabled() {
		return this.defaultBehavior;
	}
	
	private void setState(State state) {
		this.state = state;
	}
	
	private State getState() {
		return this.state;
	}
	
	private State state;
	private boolean defaultBehavior;
	
	
	/* can private methods be invoked by public methods? + nakijken! en alles aanpassen */
	/* IllegalArgumentExceptions toevoegen */
	/* Verbetering: Random object in field zetten en hergebruiken*/
	/* Documentation toevoegen */
}

