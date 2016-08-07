package hillbillies.model;


/*import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Model;*/

import be.kuleuven.cs.som.annotate.*;
import hillbillies.model.World.TerrainType;

import java.util.*;
//import java.lang.*;

//import java.util.Random;

/**
 * A class of units with a number of attributes that can perform activities, 
 * attached to a faction and a world.
 * 
 * @invar	The position of a unit must always be valid, within the game world.
 * 			| canHaveAsPosition(getCoordinate())
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
 * @invar 	The current number of experience points must always be valid.
 * 			| isValidXP(getExperiencePoints())
 * 
 * @invar 	The current state of the unit must always be a valid state.
 * 			| isValidState(getState())
 * 
 * @invar 	The unit can have its current target cube as its target cube.
 * 			| canHaveAsTargetCube(getTargetCube())
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
 * @invar 	The time before completion of a work or attack task of the unit 
 * 			must be valid.
 * 			| isValidTime(getTimeToCompletion())
 * 
 * @invar 	The time the unit is resting is valid.
 * 			| isValidTime(getTimeResting())
 * 
 * @invar 	The time after the unit last rested is valid.
 * 			| isValidTime(getTimeAfterResting())
 * 
 * @invar 	The item the unit is carrying must be a valid item.
 * 			| isValidItem(getCarriedItem())
 * 
 * @invar	Each unit must have a proper world in which it belongs
 * 			| hasProperWorld()
 * 
 * @invar	Each unit must have a proper faction to which it belongs
 * 			| hasProperFaction()
 * 
 *@author Ruben Cartuyvels
 *@version	2.2
 *
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
	 * 
	 * @throws IllegalPositionException(position, this)
	 *             The unit cannot have the given position (out of bounds).
	 *             | ! canHaveAsPosition(position)
	 *             
	 * @throws IllegalNameException(name, this)
	 *             The unit cannot have the given name.
	 *             | ! isValidName(name)            
	 */
	@Deprecated @Raw
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
		

		updateCurrentHitPoints(getMaxHitPoints());
		updateCurrentStaminaPoints(getMaxStaminaPoints());

		setOrientation((float)(Math.PI/2.0));

		this.setDefaultBehavior(enableDefaultBehavior);
		this.setState(State.EMPTY);
	}
	
	
	/**
	 * Initialize a new unit with the given world as its world, the given default
	 * behavior and the given faction as its faction, with random attributes 
	 * and a random name, with an orientation and an empty state.
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
		
		setWorld(world);
		setFaction(faction);
		
		Coordinate position = getWorld().getRandomNeighbouringSolidCube();
		setPosition(position);
		
		setName(generateName());
		
		setAgility(generateInitialSkill());
		setStrength(generateInitialSkill());
		setToughness(generateInitialSkill());
		setWeight(generateInitialWeight());
		
		updateCurrentHitPoints(getMaxHitPoints());
		updateCurrentStaminaPoints(getMaxStaminaPoints());

		setOrientation((float)(Math.PI/2.0));

		setState(State.EMPTY);
		setDefaultBehavior(enableDefaultBehavior);
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
	//TODO Documentation!!!
	@Override @Raw @Model
	public boolean canHaveAsPosition(Coordinate position) {
		boolean value = super.canHaveAsPosition(position);
		if (!isNeighbouringSolid(position) ) {
			//TODO is directly neighbouring solid????
			value = false;
		}
		return value;
	}
	
	
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
	public static boolean isValidName(String name) {
		return (name != null && name.length() > 1 
				&& Character.isUpperCase(name.charAt(0)) 
				&& name.matches("[a-zA-Z\\s\'\"]+"));
	}
	
	
	/**
	 * Return a name.
	 * 
	 * @return	a String to give to a unit as name.
	 */
	private String generateName() {
		return "Tom Hagen";
	}


	/**
	 * Variable registering the name of the unit.
	 */
	private String name = "";


	/**
	 * Return the units weight.
	 */
	@Basic @Raw
	public int getWeight() {
		return this.weight;
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
	 * Check whether the given weight is valid.
	 * 
	 * @param value
	 * 			The value to be checked.
	 * 
	 * @return True if and only if the value is larger
	 * 			than zero and smaller than 201, and at least the mean
	 * 			of the agility and the strength of the unit.
	 * 		| result == ( value > 0 && value < 201 
	 * 		|	&& value >= (getStrength()+getAgility())/2 )
	 */
	@Raw
	public boolean canHaveAsWeight(int value) {
		return ( value > 0 && value < 201 
				&& value >= (getStrength()+getAgility())/2 );
	}
	
	
	/**
	 * Return a random initial weight.
	 * 
	 * @return	a weight value between the mean of the agility and the strength,
	 * 			and the maximum initial value for the weight.
	 */
	private int generateInitialWeight() {
		return (getStrength() + getAgility())/2
				+ random.nextInt(getMaxInitialSkill() - (getStrength()+getAgility())/2);
	}
	
	
	/**
	 * Update the weight of the unit, to match its minimum value (mean
	 * of agility and strength).
	 * 
	 * @post	the unit can have its new weight as its weight.
	 * 			| canHaveAsWeight(new.getWeight())
	 */
	private void updateWeight() {
		int i = 0;
		while (!canHaveAsWeight(getWeight()+i)) {
			i++;
		}
		setWeight(getWeight()+i);
	}
	
	
	/**
	 * Return the units total weight, i.e. its weight plus the weight
	 * of the item it carries.
	 * 
	 * @return	if the unit is carrying an item, the result is the weight of
	 * 			the unit plus the weight of the item. Else, the result is the
	 * 			weight of the unit.
	 * 			| if isCarryingItem()
	 * 			| 	then result == getWeight() + getCarriedItem().getWeight()
	 * 			| else
	 * 			| 	then result == getWeight()
	 */
	private int getTotalWeight() {
		if (isCarryingItem())
			return getWeight() + getCarriedItem().getWeight();
		return this.getWeight();
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
	 * Check whether the given strength is valid.
	 * 
	 * @param 	value
	 * 			The value to be checked.
	 * 
	 * @return 	True if and only if the value is larger
	 * 			than zero and smaller than 201
	 * 			| result = (value > 0 && value < 201) 
	 */
	public static boolean isValidStrength(int value) {
		return (value > 0 && value < 201);
	}
	
	/**
	 * Variable registering the strength of the unit.
	 */
	private int strength = 0;

	
	/**
	 * Return the units agility.
	 */
	@Basic @Raw
	public int getAgility() {
		return this.agility;
	}
	
	
	/**
	 * Set the agility of the unit to the given new value.
	 * 
	 * @param 	newValue
	 * 			The new agility for this unit.
	 * 
	 * @post 	if the given agility is valid, then the new agility will be
	 * 			equal to the given agility.
	 * 			| if (isValidAgility(newValue))
	 * 			| then new.getAgility() == newValue
	 */
	@Raw
	public void setAgility(int newValue) {
		if (isValidAgility(newValue))
			this.agility = newValue;
	}
	
	
	/**
	 * Check whether the given agility is valid.
	 * 
	 * @param 	value
	 * 			The value to be checked.
	 * 
	 * @return True if and only if the value is larger
	 * 			than zero and smaller than 201
	 * 			| result =  value > 0 && value < 201) 
	 */
	public static boolean isValidAgility(int value) {
		return (value > 0 && value < 201 );
	}
	

	/**
	 * Variable registering the agility of the unit.
	 */
	private int agility = 0;

	
	/**
	 * Return the units toughness.
	 */
	@Basic @Raw
	public int getToughness() {
		return this.toughness;
	}
	
	
	/**
	 * Set the toughness of the unit to the given new value.
	 * 
	 * @param 	newValue
	 * 			The new toughness for this unit.
	 * 
	 * @post 	if the given toughness is valid, then the new
	 * 			toughness will be equal to the given toughness.
	 * 			| if (isInteger(newValue))
	 * 			| then new.getToughness() == newValue
	 */
	@Raw
	public void setToughness(int newValue) {
		if (isValidToughness(newValue))
			this.toughness = newValue;
	}
	
	
	/**
	 * Check whether the given toughness is valid.
	 * 
	 * @param 	value
	 * 			The value to be checked.
	 * 
	 * @return 	True if and only if the value is larger
	 * 			than zero and smaller than 201
	 * 			| result = (value > 0 && value < 201) 
	 */
	public static boolean isValidToughness(int value) {
		return (value > 0 && value < 201 );
	}
	

	/**
	 * Variable registering the toughness of the unit.
	 */
	private int toughness = 0;
	
	
	/**
	 * Generate a random initial value for a units toughness, strength or agility.
	 * 
	 * @return 	an integer value between the minimum initial value and maximum 
	 * 			initial value for a skill.
	 */
	private int generateInitialSkill() {
		return random.nextInt(getMaxInitialSkill() - getMinInitialSkill() + 1)
				+ getMinInitialSkill();
	}
	
	
	/**
	 * Return the minimum initial value for a units strength, toughness and agility.
	 */
	@Basic @Immutable
	private static int getMinInitialSkill() {
		return minInitialSkill;
	}
	
	
	/**
	 * Return the maximum initial value for a units strength, toughness and agility.
	 */
	@Basic @Immutable
	private static int getMaxInitialSkill() {
		return maxInitialSkill;
	}
	
	
	/**
	 * Variable registering the minimum initial value for a skill of the unit.
	 */
	private final static int minInitialSkill = 25;
	
	
	/**
	 * Variable registering the maximum initial value for a skill of the unit.
	 */
	private final static int maxInitialSkill = 100;

	
	
	/**
	 * Return the units maximum number of hitpoints.
	 * 
	 * @return 	The maximum no. of hitpoints of the unit, which is equal to
	 * 			200 times the product of the weight divided by 100 and the toughness
	 * 			divided by 100, rounded up to the next integer.
	 * 			| result == Math.ceil(200*(getWeight()/100.0)*
	 * 			|	(getToughness()/100.0));
	 */
	@Raw
	public int getMaxHitPoints() {
		return (int) Math.ceil(
				200*(getWeight()/100.0)*(getToughness()/100.0));
	}


	/**
	 * Return the units current number of hitpoints.
	 */
	@Basic @Raw
	public int getCurrentHitPoints() {
		return this.currentHitPoints;
	}
	
	
	/**
	 * Update the current number of hitpoints of the unit.
	 * 
	 * @param 	newValue
	 * 			The new value for the current no. of hitpoints of the unit.
	 * 
	 * @pre		the given new value must be a valid hitpoints value for this unit.
	 * 			| canHaveAsHitPoints(newValue)
	 * 
	 * @post 	The new no. of hitpoints of the unit is equal to
	 * 			the given new value.
	 * 			| new.getCurrentHitPoints() == newValue
	 * 
	 */
	@Raw
	public void updateCurrentHitPoints(int newValue) {
		assert ( canHaveAsHitPoints(newValue));
		//if (canHaveAsHitPoints(newValue))
		this.currentHitPoints = newValue;
	}

	
	
	/**
	 * Return the units minimum number of hitpoints.
	 */
	@Basic @Immutable
	public static int getMinHitPoints() {
		return minHP;
	}
	

	/**
	 * Check if a given value is a valid hitpoints value.
	 * 
	 * @param value
	 * 			The value to be checked.
	 * @return true if and only if the value is larger than or equal to the
	 * 			minimum and smaller than or equal to the maximum amount of
	 * 			hitpoints for this unit.
	 * 			| result ==  (value >= getMinHitPoints() && value <= getMaxHitPoints() )
	 */
	@Raw
	public boolean canHaveAsHitPoints(int value) {
		return (value >= getMinHitPoints() && value <= getMaxHitPoints());
	}
	
	
	/**
	 * Variable registering the current number of hitpoints of the unit.
	 */
	private int currentHitPoints = 0;

	/**
	 * Variable registering the minimum number of hitpoints of the unit.
	 */
	private static final int minHP = 0;


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
	 * Return the units current number of stamina points.
	 */
	@Basic @Raw
	public int getCurrentStaminaPoints() {
		return this.currentStaminaPoints;
	}
	
	/**
	 * Update the current number of stamina points of the unit.
	 * 
	 * @param 	newValue
	 * 			The new value for the current no. of stamina points of the unit.
	 * 
	 * @pre		the given new value must be a valid stamina points value for this unit.
	 * 			| canHaveAsStaminaPoints(newValue)
	 * 
	 * @post 	The new no. of stamina points of the unit is equal to
	 * 			the given new value.
	 * 			| new.getCurrentStaminaPoints() == newValue;
	 * 
	 */
	@Raw
	public void updateCurrentStaminaPoints(int newValue) {
		assert canHaveAsStaminaPoints(newValue);
		//if (canHaveAsStaminaPoints(newValue))
		this.currentStaminaPoints = newValue;
	}

	
	/**
	 * Return the units minimum number of stamina points.
	 */
	@Basic @Immutable
	public static int getMinStaminaPoints() {
		return minSP;
	}


	/**
	 * Check if a given value is a valid stamina points value.
	 * 
	 * @param 	value
	 * 			The value to be checked.
	 * @return 	true if and only if the value is larger than or equal to
	 * 			the minimum and smaller than or equal to the maximum amount of
	 * 			stamina points for this unit.
	 * 			| result =  (value >= getMinStaminaPoints && 
	 * 			|		value <= getMaxStaminaPoints() )
	 */
	@Raw
	public boolean canHaveAsStaminaPoints(int value) {
		return (value >= getMinStaminaPoints() && value <= getMaxStaminaPoints());
	}


	/**
	 * Variable registering the current number of stamina points of the unit.
	 */
	private int currentStaminaPoints = 0;

	
	/**
	 * Variable registering the minimum number of stamina points of the unit.
	 */
	private static final int minSP = 0;
	
	
	
	/**
	 * Return the units orientation.
	 */
	// TODO FIX ORIENTATION!
	@Basic @Raw
	public float getOrientation() {
		return -orientation + ((float) Math.PI/2.0f);
	}
	
	
	/**
	 * Check whether the given orientation is valid.
	 * 
	 * @param 	value
	 * 			The value to be checked.
	 * 
	 * @return 	True if and only if the value is a float, larger
	 * 			than or equal to zero and smaller than 2*Pi
	 * 			| result = (value > 0 && value < 2*Math.PI) 
	 */
	public static boolean isValidOrientation(float value) {
		return (value >= - Math.PI && value <= Math.PI);
	}
	

	/**
	 * Set the orientation of the unit to the given new value.
	 * 
	 * @param 	newValue
	 * 			The new orientation for this unit.
	 * 
	 * @post 	if the given orientation is a float number between
	 * 			-Pi and Pi, then the new orientation of the unit is
	 * 			equal to the given orientation.
	 * 			| if (newValue >= -Math.Pi && newValue <= Math.PI)
	 * 			| 	then new.getOrientation() == newValue
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
	private float orientation = 0.0f;

	
	
	
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
	//@Override //TODO override annotation necessary?
	public void advanceTime(double dt) throws IllegalPositionException, 
				IllegalArgumentException, IllegalTimeException, ArithmeticException {
		if (! isValidDT(dt)) {
			throw new IllegalArgumentException();
		}
		if (getCurrentHitPoints() <= 0) {
			dropItem();
			terminate();
		}
		else {
			if (getTXP() > 10) {
				controlXP();
			}
			
			if (!isResting()) {
				setTimeAfterResting(getTimeAfterResting() + dt);
			}

			if (!isNeighbouringSolid(getCoordinate()) && !isFalling())
				fall();
			
			if (isAttacked())
				setState(State.EMPTY);

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
				if (!(isDestCubeLTReached())
						&& !getCoordinate().equals(getDestCubeLT()) ) {

					moveTo(getDestCubeLT());
				} else {
					controlWaiting(dt);
				}
			}
			else if (isAttacking()) {
				controlAttacking(dt);
			}
			else if (isWorking()) {
				controlWorking(dt);
			}
		}
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
			//setPosition(getCoordinate());
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
	 * 			| if (isAboveSolid(getCoordinate()) || getCoordinate().get(2) == 0)
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
		if(isAboveSolid(getCoordinate()) || getCoordinate().get(2) == 0) {
			
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
	private Coordinate getStartFallingCube() {
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
	private void setStartFallingCube(Coordinate coordinate) {
		this.startFallingCube = coordinate;
	}
	
	
	/**
	 * Set the cube where the unit starts falling to null.
	 * 
	 * @post	| getStartFallingCube() == null
	 */
	private void setStartFallingCube() {
		this.startFallingCube = null;
	}
	
	
	/**
	 * Variable registering the cube where the unit started falling,
	 * if any.
	 */
	private Coordinate startFallingCube = null;
	
	
	
	/**
	 * Manage the waiting of the unit, i.e. when the state of the unit is equal to empty.
	 * 
	 * @param 	dt
	 * 			the game time interval in which to manage the falling behavior.
	 * 
	 * @effect	If the default behavior is not enabled, nothing happens.
	 * 			Else, there is an equal chance that the unit will execute each
	 * 			of the following activities: move to a random cube, start working at
	 * 			a random cube, rest or attack an enemy if there is one in range.
	 * 			| if (isDefaultBehaviorEnabled())
	 * 			|	then (
	 * 			|		if (random < 0.25 && random > 0.0)
	 * 			|			then moveTo(getRandomReachableCube())
	 * 			|		else if (random < 0.5 && random > 0.25)
	 * 			|			then workAt(getRandomNeighbouringCube())
	 * 			|		else if (random < 0.75 && random > 0.5 && enemiesInRange())
	 * 			|			then attack(getEnemyInRange())
	 * 			|		else if (random < 1.0 && random > 0.75)
	 * 			|			then rest()
	 * 			| 		)
	 */
	private void controlWaiting(double dt) throws IllegalPositionException {

		if (isDefaultBehaviorEnabled()) {
			double dice = random.nextDouble();
			
			if (dice < 0.0/4.0) {
				Coordinate destCube = getRandomReachableCube();
				moveTo(destCube);
				
			} else if (/*true*/ dice >1.0/4.0 && dice < 2.0/4.0) {
				
				Coordinate targetCube = getRandomNeighbouringCube();
				
				//System.out.println(getCoordinate().toString());
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
	 * 			| result == (getState() == State.MOVING)
	 */
	public boolean isMoving() {
		return (getState() == State.MOVING);
	}
	
	
	/**
	 * Start moving to an adjacent cube.
	 * 
	 * @param 	cubeDirection
	 * 			The relative directions of the cube to which the unit has to move.
	 * 
	 * @post	The destination of the unit is set to the right adjacent cube, if
	 * 			the unit is not already moving or in the initial resting state or attacked
	 * 			or falling.
	 * 			| new.getDestination() == determineCube(cubeDirection)
	 * 
	 * @post	The orientation of the unit is set towards the destination, if
	 * 			the unit is not already moving or in the initial resting state or attacked
	 * 			or falling.
	 * 			| new.getOrientation() == Math.atan2(getVelocity()[0], getVelocity()[2] )
	 * 
	 * @effect	The units state is set to moving, if the unit is not already moving 
	 * 			or in the initial resting state or attacked or falling.
	 * 			| startMoving()
	 * 
	 * @throws  IllegalArgumentException
	 * 			The given direction is not a valid direction.
	 * 			| !isValidCubeDirection(cubeDirection)
	 */
	public void moveToAdjacent(int[] cubeDirection) 
			throws IllegalPositionException, IllegalArgumentException {
		
		if (!isValidCubeDirection(cubeDirection))
			throw new IllegalArgumentException();

		if (!isMoving() && !isAttacked() && getState() != State.RESTING_1 && !isFalling()) {
			startMoving();
			
			Coordinate newPosition = new Coordinate(getCoordinate().get(0) + cubeDirection[0],
					getCoordinate().get(1) + cubeDirection[1],
					getCoordinate().get(2) + cubeDirection[2]);
			
			try {
				setDestination((newPosition));
			}
			catch (IllegalPositionException e) {
				setState(State.EMPTY);
				throw new IllegalPositionException(newPosition);
			}
			setOrientation((float) Math.atan2(getVelocity()[0], getVelocity()[1] ));
		}
	}

	
	/**
	 * Move to a target cube.
	 * 
	 * @param 	destCube
	 * 			The coordinates of the destination cube.
	 * 
	 * @post	The long term destination of the unit will be equal to
	 * 			the destination cube.
	 * 			| new.destCubeLT == destCube
	 * 
	 * @post	The long term destination reached field will be equal to false.
	 * 			| new.isDestCubeLTReached() == false
	 * 
	 * @effect	If the unit is not in the initial resting state and not falling,
	 * 			a path will be computed. If the path is equal to null the destination
	 * 			is not reachable and the long term destination reached field 
	 * 			is set to true, hence the unit will not move. Otherwise, if a path
	 * 			is found, the unit will move towards the first cube of the path.
	 * 
	 * @throws 	IllegalPositionException
	 * 			The given destination cube is not valid.
	 * 			| !canHaveAsPosition(destCube)
	 */
	public void moveTo(Coordinate destCube) throws IllegalPositionException,
						IllegalArgumentException {
		
		if (!canHaveAsPosition(destCube)) {
			throw new IllegalPositionException(destCube);
		}
		
		setDestCubeLT(destCube);
		this.destCubeLTReached = false;
		
		if (getState() != State.RESTING_1 && !isFalling()) {
			
			Coordinate startCube = getCoordinate();
			Queue<Tuple> path = computePath(getDestCubeLT());
			
			if (path == null) this.destCubeLTReached = true;
			
			else if (Tuple.containsCube(path, startCube)) {
				Tuple nextTuple = getNeighbourWSmallestN(path, startCube);
				
				if (nextTuple != null)
					moveTowards(nextTuple.cube);
			}
			
		}
	}
	
	
	/**
	 * Get a random cube neighboring the cube currently occupied by the unit.
	 * 
	 * @return	a random cube neighboring the current cube.
	 * 			| getWorld().getRandomNeighbouringCube(getCoordinate())
	 */
	private Coordinate getRandomNeighbouringCube() {
		return getWorld().getRandomNeighbouringCube(getCoordinate());
	}
	
	
	/**
	 * Find the cube that is neighboring the given cube with the smallest 
	 * weight from a given set of tuples consisting of a weight and a cube coordinate.
	 * 
	 * @param 	path
	 * 			The set containing tuples that consist of a coordinate and a weight.
	 * 
	 * @param 	cube
	 * 			The cube to find the neighbour of.
	 * 	
	 * @return	The the cube that is neighboring the given cube with the smallest 
	 * 			weight.
	 */
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
	
	
	/**
	 * Check whether a given cube is reachable or not, by trying to compute
	 * a path from the current cube to the given cube.
	 * 
	 * @param 	destCube
	 * 			the cube to test if reachable.
	 * 
	 * @return	True if and only if a path can be computed to the given cube.
	 * 			| result == (computePath(destCube) != null)
	 */
	private boolean isReachable(Coordinate destCube) {
		return (computePath(destCube) != null);
	}
	
	
	/**
	 * Get a random cube that is reachable from the units current position.
	 *  
	 * @return	A random reachable cube coordinate.
	 * 			| do ( cube = getWorld().getRandomNeighbouringSolidCube() )
	 * 			| while (!isReachable(cube)
	 * 			| result == cube
	 */
	private Coordinate getRandomReachableCube() {
		Coordinate cube;
		do {
			cube = getWorld().getRandomNeighbouringSolidCube();
		} while (!isReachable(cube));
		return cube;
	}
	
	
	/**
	 * Compute a path from the current cube to the given destination cube.
	 * 
	 * @param 	destCube
	 * 			The destination cube to compute a path to.
	 * 
	 * @return	if a path can be found within a reasonable time, this path is
	 * 			returned.
	 * 
	 * @return	if no path can be found within a reasonable time, null is returned.
	 * 			| result == null
	 */
	private Queue<Tuple> computePath(Coordinate destCube) {
		Queue<Tuple> path = new LinkedList<Tuple>();
		path.add(new Tuple(destCube, 0));
		
		int iterations = 0;
		while(!Tuple.containsCube(path, getCoordinate()) 
				&& Tuple.hasNext(path) && iterations < 301) {
			
			Tuple nextTuple = Tuple.getNext(path);
			path = search(nextTuple, path);
			nextTuple.isChecked = true;
			iterations++;

			if (iterations >= 501) {
				path = null;
				break;
			}
		}
		return path;
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
	private Queue<Tuple> search(Tuple currentTuple, Queue<Tuple> path) {
		
		Set<Coordinate> cubes = new HashSet<Coordinate>();
		
		Set<Coordinate> neighbours = getWorld().getNeighbours(currentTuple.cube);
		for (Coordinate neighbour: neighbours) {
			if (getWorld().getCubeTypeAt(neighbour).isPassable()
					
					&& getWorld().isNeighbouringSolid(neighbour)
					
					&& !Tuple.containsCubeWithSmallerN(path, neighbour, currentTuple)
					) {
				cubes.add(neighbour);
			}
		}
		
		for (Coordinate cube: cubes) {
			path.add(new Tuple(cube, currentTuple.n + 1));
		}
		
		return path;
	}
	

	
	/**
	 * Makes the unit start moving, i.e. sets its state to moving.
	 * 
	 * @post	The units state is equal to moving
	 * 			| new.getState() == State.MOVING
	 */
	@Model
	private void startMoving() {
		setState(State.MOVING);
	}

	
	/**
	 * Manage the moving of the unit, i.e. when the state of the unit is equal to moving.
	 * 
	 * @param 	dt
	 * 			the game time interval in which to manage the moving behavior.
	 * 
	 * @effect	if the unit is not occupying a cube neighbouring a solid cube, it will fall
	 * 			and this method will have no other effect.
	 * 			| if (!isNeighbouringSolid(getCoordinate())
	 * 			|	then fall()
	 * 
	 * @effect	else if the unit is sprinting, its stamina points will be updated. If the
	 * 			unit is out of stamina points it will stop sprinting and its stamina points
	 * 			will be set to zero (they might be less than zero for a little while due
	 * 			to the calculation method).
	 * 			| if (isSprinting())
	 * 			|		then updateCurrentStaminaPoints( 
	 * 			|				(getCurrentStaminaPoints() - (dt/0.1))  )
	 * 			|		if (getCurrentStaminaPoints() <= 0)
	 * 			|			then stopSprinting()
	 * 			|				 updateCurrentStaminaPoints(0)
	 * 
	 * @effect	if the unit does not reach its destination (short term) within this
	 * 			time interval, its position is updated.
	 * 			| if (!reached(dt))
	 * 			|	then updatePosition(dt)
	 * 
	 * @effect	if the unit does reach its short term destination within this time interval,
	 * 			its position is set to the center of the destination cube and 
	 * 			1 experience point is added to its XP.
	 * 			| if (reached(dt)) 
	 * 			| 	then setPosition(getDestination())
	 *			|		 addXP(1);
	 *
	 * @effect 	if the unit reaches its short term destination within this time interval
	 * 			and this is also its long term destination, the units long term destination
	 * 			reached field is set to true, and if it was sprinting it stops sprinting.
	 * 			| if (reached(dt) && getCoordinate().equals(getDestCubeLT()) )
	 * 			|	then this.destCubeLTReached = true
				|			if (isSprinting()) 
				|				then stopSprinting()
	 * 
	 * @effect	if the unit does reach its short term destination within this time interval,
	 * 			its state is set to empty.
	 * 			| if (reached(dt))
	 * 			| 	then setState(State.EMPTY)
	 * 
	 */
	private void controlMoving(double dt) throws IllegalPositionException, 
				ArithmeticException {
		
		if (!isNeighbouringSolid(getCoordinate()))
			fall();
		
		else  {
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

				setPosition(getDestination());
				addXP(1);
				
				if (getCoordinate().equals(getDestCubeLT()) ) {
					
					this.destCubeLTReached = true;
					if (isSprinting()) stopSprinting();
				}
				setState(State.EMPTY);	
			}

		}

	}
	
	
	
	/**
	 * Move towards an adjacent cube. Compute the direction and calls 
	 * moveToAdjacent method.
	 * 
	 * @param	destCube	
	 * 			The cube to move towards.
	 * 
	 * @effect	if the given destination cube is not equal to the units current
	 * 			cube, the unit starts moving to the destination cube.
	 * 			| if (!getCoordinate().equals(destCube))
	 * 			| 	 then moveToAdjacent(destCube)
	 * 
	 * @throws	IllegalArgumentException
	 * 			the given destination cube is not a neighbouring cube of the units
	 * 			current cube.
	 * 			|  ! getWorld().isNeighbouring(destCube, getCoordinate())
	 * 
	 */
	private void moveTowards(Coordinate destCube) throws IllegalArgumentException {
		if (! getWorld().isNeighbouring(destCube, getCoordinate()))
			throw new IllegalArgumentException();
						
		if ((!getCoordinate().equals(destCube))) {

			Coordinate startCube = getCoordinate();

			int[] cubeDirection = new int[3];
			for (int i=0; i<cubeDirection.length; i++) {
				cubeDirection[i] = destCube.get(i) - startCube.get(i);
			}

			moveToAdjacent(cubeDirection);
			//setOrientation((float) Math.atan2(getVelocity()[1], getVelocity()[0] ));
		}
	}
	
	
	/**
	 * Checks if the given direction is valid.
	 * 
	 * @param 	cubeDirection
	 * 			the direction to check
	 * 
	 * @return	True if and only if the given direction is an integer array with length
	 * 			equal to 3 and only contains -1, 0 and 1.
	 * 			| result == (   (cubeDirection.length == 3) &&
	 * 			|			(for each integer in cubeDirection:
	 * 			|			integer == -1 || integer == 0 || integer == 1)  )
	 */
	private static boolean isValidCubeDirection(int[] cubeDirection) {
		if (cubeDirection.length != 3)
			return false;
		boolean valid = true;
		for (int i=0; i<cubeDirection.length; i++) {
			if (cubeDirection[i] != 0 && cubeDirection[i] != 1 && cubeDirection[i] != -1)
				valid = false;
		}
		return valid;
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
	 * @param 	newDestination
	 * 			The new destination coordinates.
	 * 
	 * @post 	The new destination of this unit is equal to the
	 * 			given coordinates.
	 * 			| new.getDestination() == newDestination
	 * 
	 * @throws 	IllegalPositionException
	 * 			The given destination is not a valid position.
	 * 			| !canHaveAsPosition(newDestination)
	 */
	@Raw
	private void setDestination(Coordinate newDestination) throws IllegalPositionException {
		if (!canHaveAsPosition(newDestination)) {
			throw new IllegalPositionException(newDestination);
		}
		this.destination = newDestination;
	}

	
	/**
	 * Returns true if the unit reaches its destination in the current time span.
	 * 
	 * @param	dt
	 * 			The current time span.
	 * 
	 * @return	True if and only if the unit reaches the destination.
	 * 			| result == (getDistanceTo(getPosition(), 
	 * 			|		World.getCubeCenter(getDestination())) < getCurrentSpeed()*dt )
	 */
	private boolean reached(double dt) throws IllegalPositionException {
		return (getDistanceTo(getPosition(), World.getCubeCenter(getDestination())) < getCurrentSpeed()*dt);
	}


	/**
	 * Returns the distance between two given positions.
	 * 
	 * @param	position1
	 * 			The first position.
	 * 
	 * @param	position2
	 * 			The second position.
	 * 
	 * @return	the distance between the positions, which is equal to the square root of 
	 * 			the square of the differences between the x, y and z coordinates of the
	 * 			first position and second position.
	 * 			| result = Math.sqrt(Math.pow(position1[0]-position2[0],2)
	 * 			|	+ Math.pow(position1[1]-position2[1],2) 
	 * 			|	+ Math.pow(position1[2]-position2[2],2))
	 * 
	 * @throws	IllegalPositionException
	 * 			One of the given positions is not a valid position.
	 * 			| !canHaveAsPosition(position)
	 */
	@Model
	private double getDistanceTo(double[] position1, double[] position2) 
											throws IllegalPositionException {
		if (!canHaveAsPosition(convertPositionToCoordinate(position1)))
			throw new IllegalPositionException(position1);
		
		if (!canHaveAsPosition(convertPositionToCoordinate(position2)))
			throw new IllegalPositionException(position2);

		return Math.sqrt(Math.pow(position1[0]-position2[0],2)
				+ Math.pow(position1[1]-position2[1],2) 
				+ Math.pow(position1[2]-position2[2],2));
	}
	
	
	
	/**
	 * Returns the long term destination of the unit.
	 */
	@Basic
	public Coordinate getDestCubeLT() {
		return this.destCubeLT;
	}
	
	
	/**
	 * Returns the value of the long term destination reached field.
	 */
	@Basic
	private boolean isDestCubeLTReached() {
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
	@Raw
	private void setDestCubeLT(Coordinate destCubeLT) throws IllegalPositionException {
		if ( !canHaveAsPosition(destCubeLT) )
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
	 * Returns the units movement speed, which depends on the base speed, 
	 * 		the moving direction and on whether the unit is currently moving or not.
	 * 
	 * @return	The units speed if the unit is not moving, which is equal to 
	 * 			zero.
	 * 			| result == 0.0
	 * 
	 * @return	The units speed speed if the unit is moving upwards, which is
	 * 			equal to half of the base speed.
	 * 			| result == 0.5*getBaseSpeed()
	 * 
	 * @return	The units speed speed if the unit is moving downwards, which is
	 * 			equal to 1.2 times the base speed.
	 * 			| result == 1.2*getBaseSpeed()
	 * 
	 * @return	The units speed speed if the units z coordinate is not changing
	 * 			while moving, which is equal to the base speed.
	 * 			| result == getBaseSpeed()
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
		return 0.0d;
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
	@Model //@Override
	protected double[] getVelocity() throws IllegalPositionException {
		if (isFalling())
			return new double[]{0.0,0.0,-3.0};
		
		double d = getDistanceTo(getPosition(), World.getCubeCenter(getDestination()));
		double[] v = new double[3];
		for (int i=0; i<3; ++i) {
			v[i] = getCurrentSpeed()*(World.getCubeCenter(getDestination())[i]-getPosition()[i])/d;
		}
		return v;
	}

	
	/**
	 * Returns the units base speed.
	 * 
	 * @return	The units base speed if the unit is not sprinting, 
	 * 			which is equal to 1.5 times the quotient of its strength added 
	 * 			by its agility, and 2 times its weight.
	 * 			| result == 1.5*(getStrength()+getAgility())/(2.0*getWeight())
	 * 
	 * @return	The units base speed if the unit is sprinting, 
	 * 			which is equal to 3.0 times the quotient of its strength added 
	 * 			by its agility, and 2 times its weight.
	 * 			| result == 3.0*(getStrength()+getAgility())/(2.0*getWeight())
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
	 * Returns whether the unit is sprinting or not.
	 */
	@Basic
	public boolean isSprinting() {
		return this.isSprinting;
	}

	
	/**
	 * Makes the unit start sprinting.
	 * 
	 * @post	If the unit is moving, if it has enough stamina points and if it
	 * 			is not falling, its sprinting field is set to true.
	 * 			| if (isMoving() && getCurrentStaminaPoints() > 0 && !isFalling())
	 * 			| 	then new.isSprinting() == true
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
	private boolean isSprinting = false;


	/**
	 * Returns true if the unit is working.
	 * 
	 * @return	True if and only if the unit is working.
	 * 			| result == (getState() == State.WORKING)
	 */
	public boolean isWorking() {
		return (getState() == State.WORKING);
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
	public void workAt(Coordinate targetCube) throws IllegalTargetException {
		
		if (!isMoving() && getState() != State.RESTING_1 && !isFalling()) {
			
			try {
				setTargetCube(targetCube);
				startWorking();
				
				double[] targetPosition = World.getCubeCenter(getTargetCube());
				setOrientation((float)Math.atan2(getPosition()[1]-targetPosition[1],
						targetPosition[0] - getPosition()[0]));
				
			} catch (IllegalTargetException e) {
				e.printStackTrace();
				setState(State.EMPTY);
				throw new IllegalTargetException(getCoordinate(), targetCube);
			}
		}
	}


	/**
	 * Makes the unit start working, i.e. sets its state to working.
	 * 
	 * @post	The units state is equal to working
	 * 			| new.getState() == State.WORKING
	 * 
	 * @post	The units time to completion is equal to 500 divided by its
	 * 			strength.
	 * 			| new.getTimeToCompletion() == 500.0/getStrength()
	 */
	private void startWorking() throws IllegalTimeException {
		setTimeToCompletion(500.0f/getStrength());
		setState(State.WORKING);
	}

	
	/**
	 * Manage the working of the unit, i.e. when the state of the unit is equal to working.
	 * 
	 * @param 	dt
	 * 			the game time interval in which to manage the working behavior.
	 * 
	 * @effect	if the time before completion of the work order is larger than zero
	 * 			the time before completion is decremented with the given time interval.
	 * 			| if (getTimeToCompletion() > 0.0)
	 * 			| 	then setTimeToCompletion((float) (getTimeToCompletion() - dt))
	 * 
	 * @effect	else, if the time before completion of the work order is equal to or
	 * 			smaller than zero, this time is set to zero
	 * 			| if (getTimeToCompletion() <= 0.0)
	 * 			| 		then setTimeToCompletion(0.0f)
	 * 			
	 * @effect	if the time before completion of the work order is equal to or
	 * 			smaller than zero and if the unit is carrying an item, 
	 * 			this item is dropped and 10 experience points are added to the units XP.
	 * 			| if (isCarryingItem() && getTimeToCompletion() <= 0.0) 
	 * 			|			then dropItem()
	 * 			|				 addXP(10)
	 * 
	 * @effect	if the time before completion of the work order is equal to or
	 * 			smaller than zero, if the unit is not carrying an item and if
	 * 			the cube type of the target cube is workshop and the target cube contains
	 * 			a boulder and a log, the units equipment is improved and 10 experience 
	 * 			points are added to the units XP.
	 * 			| if (getTimeToCompletion() <= 0.0 && !isCarryingItem()
	 * 			|		&& getWorld().getCubeTypeAt(getTargetCube())==TerrainType.WORKSHOP
	 * 			|		&& getWorld().containsLog(getWorld().getObjectsAt(getTargetCube()))
	 * 			|		&& getWorld().containsBoulder(getWorld().getObjectsAt(getTargetCube()))
	 * 			| 	then improveEquipment(items)
	 * 			|		 addXP(10)
	 * 
	 * @effect	if the time before completion of the work order is equal to or
	 * 			smaller than zero, if the unit is not carrying an item and the above is
	 * 			not true, and if the target cube contains a boulder, the unit picks
	 * 			up the boulder and 10 experience points are added to the units XP.
	 * 			| if ( getTimeToCompletion() <= 0.0 && !isCarryingItem()
	 * 			|		&& ! ( getWorld().getCubeTypeAt(getTargetCube())==TerrainType.WORKSHOP
	 * 			|		&& getWorld().containsLog(getWorld().getObjectsAt(getTargetCube()))
	 * 			|		&& getWorld().containsBoulder(getWorld().getObjectsAt(getTargetCube())) )
	 * 			|		&&  getWorld().containsBoulder(getWorld().getObjectsAt(getTargetCube()))   )
	 * 			| 	then pickUpItem(getWorld().getBoulderFrom(getWorld().getObjectsAt(getTargetCube())))
	 * 			|		 addXP(10)
	 * 
	 * @effect	if the time before completion of the work order is equal to or
	 * 			smaller than zero, if the unit is not carrying an item and the above is
	 * 			not true, and if the target cube contains a log, the unit picks
	 * 			up the log and 10 experience points are added to the units XP.
	 * 			| if ( getTimeToCompletion() <= 0.0 && !isCarryingItem()
	 * 			|		&& ! ( getWorld().getCubeTypeAt(getTargetCube())==TerrainType.WORKSHOP
	 * 			|		&& getWorld().containsLog(getWorld().getObjectsAt(getTargetCube()))
	 * 			|		&& getWorld().containsBoulder(getWorld().getObjectsAt(getTargetCube())) )
	 * 			|		&& ! getWorld().containsBoulder(getWorld().getObjectsAt(getTargetCube())) 
	 * 			|		&&   getWorld().containsLog(getWorld().getObjectsAt(getTargetCube()))  )
	 * 			| 	then pickUpItem(getWorld().getLogFrom(getWorld().getObjectsAt(getTargetCube())))
	 * 			|		 addXP(10)
	 * 
	 * @effect	if the time before completion of the work order is equal to or
	 * 			smaller than zero, if the unit is not carrying an item and the above is
	 * 			not true, and if the target cube is of type rock or tree, the target cube
	 * 			collapses and 10 experience points are added to the units XP.
	 * 			| if ( getTimeToCompletion() <= 0.0 && !isCarryingItem()
	 * 			|		&& ! ( getWorld().getCubeTypeAt(getTargetCube())==TerrainType.WORKSHOP
	 * 			|		&& getWorld().containsLog(getWorld().getObjectsAt(getTargetCube()))
	 * 			|		&& getWorld().containsBoulder(getWorld().getObjectsAt(getTargetCube())) )
	 * 			|		&& ! getWorld().containsBoulder(getWorld().getObjectsAt(getTargetCube())) 
	 * 			|		&& ! getWorld().containsLog(getWorld().getObjectsAt(getTargetCube()))  
	 * 			|		&& (getWorld().getCubeTypeAt(getTargetCube()) == TerrainType.TREE
	 * 			|				|| getWorld().getCubeTypeAt(getTargetCube()) == TerrainType.ROCK)  )
	 * 			| 	then getWorld().collapse(getTargetCube(), 1.00)
	 * 			|		 addXP(10)
	 * 
	 * @effect	if the time before completion of the work order is equal to or
	 * 			smaller than zero, the units state is set to empty
	 * 			| if (getTimeToCompletion() <= 0.0)
	 * 			| 		then setState(State.EMPTY)
	 */
	private void controlWorking(double dt) throws IllegalTimeException, 
							ArithmeticException, IllegalArgumentException {
		if (getTimeToCompletion() > 0.0) {
			
			setTimeToCompletion((float) (getTimeToCompletion() - dt));
		}
		else if (getTimeToCompletion() <= 0.0) {
			
			setTimeToCompletion(0.0f);
			
			if (isCarryingItem()) {
				dropItem();
				addXP(10);
				
			} else {
				Set<Item> items = getWorld().getObjectsAt(getTargetCube());
				
				if ((getWorld().getCubeTypeAt(getTargetCube())==TerrainType.WORKSHOP)
							&& getWorld().containsLog(items)
							&& getWorld().containsBoulder(items)) {
					improveEquipment();
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
	 * Improve the units equipment, incrementing the units strength and toughness by 1.
	 * 
	 * @effect	If the cube type of the target cube is workshop and the target cube contains
	 * 			at least one boulder and at least one log, one boulder and one log are
	 * 			consumed,
	 * 			| if (getWorld().getCubeTypeAt(getTargetCube())==TerrainType.WORKSHOP
	 * 			|		&& getWorld().containsLog(getWorld().getObjectsAt(getTargetCube()))
	 * 			|		&& getWorld().containsBoulder(getWorld().getObjectsAt(getTargetCube()))  )
	 * 			| then (for each boulder in items:
	 * 			|			boulder.terminate()
	 * 			|			break )
	 * 			|	   (for each log in items:
	 * 			|			log.terminate()
	 * 			|			break )
	 * 			and the weight and toughness of the unit are incremented.
	 * 			|		setWeight(getWeight() + 1)
	 * 			|		setToughness(getToughness() +1)
	 * 
	 */
	//TODO increase by 1, correct?
	private void improveEquipment() {
		boolean logT = false, boulderT = false;
		
		Set<Item> items = getWorld().getObjectsAt(getTargetCube());
		Iterator<Item> it = items.iterator();
		
		if (getWorld().getCubeTypeAt(getTargetCube())==TerrainType.WORKSHOP
				&& getWorld().containsLog(items)
							&& getWorld().containsBoulder(items) ) { 
			
			while ((!logT || !boulderT) && it.hasNext()) {
				Item next = it.next();
				if (next instanceof Boulder && !boulderT) {
					next.terminate();
					boulderT = true;
				} else if (next instanceof Log && !logT) {
					next.terminate();
					logT = true;
				}
			}

			setWeight(getWeight() + 1);
			setToughness(getToughness() +1);
		}
	}
	
	
	/**
	 * Returns the units target cube.
	 */
	@Basic @Raw @Model
	private Coordinate getTargetCube() {
		return this.targetCube;
	}
	
	
	/**
	 * Checks whether this unit can have the given cube as its target cube.
	 * 
	 * @param 	targetCube
	 * 			the cube to verify
	 * 
	 * @return	true if and only if the units world can have the cube as coordinates and
	 * 			the cube is neighbouring the cube currently occupied by the unit or is 
	 * 			equal to that cube.
	 * 			| result == ( getWorld().canHaveAsCoordinates(targetCube)
	 * 			|			&& ( getWorld().isNeighbouring(getCoordinate(), targetCube)
	 * 			|				|| getCoordinate().equals(targetCube) ) )
	 * 			
	 */
	@Raw
	public boolean canHaveAsTargetCube(Coordinate targetCube) {
		return (getWorld().canHaveAsCoordinates(targetCube)
				&& ( getWorld().isNeighbouring(getCoordinate(), targetCube) 
				|| getCoordinate().equals(targetCube) ) );
	}

	/**
	 * Set the units target cube.
	 * 
	 * @param 	target
	 * 			the cube to set the units target cube to.
	 * 
	 * @post	the unit references the given cube as its target cube.
	 * 			| new.getTargetCube() == target
	 * 
	 * @throws	IllegalTargetException
	 * 			the unit can not have the given target as its target cube.
	 * 			| !canHaveAsTargetCube(target)
	 */
	@Raw @Model
	private void setTargetCube(Coordinate target) throws IllegalTargetException {
		if (!canHaveAsTargetCube(target) )
			throw new IllegalTargetException(getCoordinate(), target);
		
		this.targetCube = target;
	}
	
	
	/**
	 * A variable registering this units target cube.
	 */
	private Coordinate targetCube = null;
	
	
	/**
	 * Inspect whether the unit is carrying a boulder.
	 * 
	 * @return	true if and only if the unit is carrying an item and if
	 * 			this item is a boulder.
	 * 			| result == (getCarriedItem() instanceof Boulder)
	 */
	@Raw
	public boolean isCarryingBoulder() {
		return (getCarriedItem() instanceof Boulder);
	}
		
	
	/**
	 * Inspect whether the unit is carrying a log.
	 * 
	 * @return	true if and only if the unit is carrying an item and if
	 * 			this item is a log.
	 * 			| result == (getCarriedItem() instanceof Log)
	 */
	@Raw
	public boolean isCarryingLog() {
		return (getCarriedItem() instanceof Log);
	}
	
	
	/**
	 * Inspect whether the unit is carrying an item.
	 * 
	 * @return	true if and only if the unit is carrying an item.
	 * 			| result == (getCarriedItem() != null)
	 */
	@Raw
	public boolean isCarryingItem() {
		return (getCarriedItem() != null);
	}
	
	
	/**
	 * Checks whether the given item is a valid item for a unit to carry.
	 * 
	 * @param 	item
	 * 			the item to check
	 * 
	 * @return	true if and only if the given item is not equal to null.
	 * 			| result == (item != null)
	 */
	//TODO and if this item references the same world as this unit
	public static boolean isValidItem(Item item) {
		return (item != null);
	}
	
	
	/**
	 * Returns this units carried item.
	 */
	@Raw @Basic
	public Item getCarriedItem() {
		return this.carriedItem;
	}
	
	// TODO make postcond/effect at constructor that unit is not yet carrying an item
	
	
	/**
	 * Drop the item the unit is currently carrying. If the unit is not carrying an item,
	 * this method has no effect.
	 * 
	 * @effect	if the unit is carrying an item, this items world is set to the units world,
	 * 			| if (isCarryingItem())
	 * 			| 	then getCarriedItem().setWorld(getWorld())
	 * 			the item is then added to the units world, 
	 * 			|		 getWorld().addItem(getCarriedItem())
	 * 			and its position is set to the units target cube.
	 * 			|		 getCarriedItem().setPosition(getTargetCube())
	 * 
	 * @effect	If the target cube is not a valid position the items 
	 * 			position is set to the units position.
	 * 			|		 if (!getCarriedItem().canHaveAsPosition(getTargetCube()))
	 * 			|			then getCarriedItem().setPosition(getCoordinate())
	 * 
	 * @post 	if the unit is carrying an item, the new units carried item field 
	 * 			is equal to null.
	 * 			| if (this.isCarryingItem())
	 * 			| 		then new.carriedItem == null
	 */
	@Model
	private void dropItem() {
		if (isCarryingItem()) {
			getCarriedItem().setWorld(getWorld());
			getWorld().addItem(getCarriedItem());
			try {
				getCarriedItem().setPosition(getTargetCube());
			} catch (RuntimeException e) {
				e.printStackTrace();
				getCarriedItem().setPosition(getCoordinate());
			}
			this.carriedItem = null;
		}
	}
	
	/**
	 * 
	 * @param item
	 */
	@Model
	private void pickUpItem(Item item) {
		if (!isValidItem(item))
			throw new IllegalArgumentException();
		
		dropItem();
		this.carriedItem = item;
		getWorld().removeItem(item);
	}
	
	
	private Item carriedItem = null;


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

		if (!isMoving() && !isFalling() && !defender.isFalling()) {
			startAttacking();

			this.defender = defender;
			getDefender().setAttacked(true);

			// Orientation update
			this.setOrientation((float)Math.atan2(defender.getPosition()[1]-this.getPosition()[1],
					defender.getPosition()[0]-this.getPosition()[0]));

			defender.setOrientation((float)Math.atan2(this.getPosition()[1]-defender.getPosition()[1],
					this.getPosition()[0]-defender.getPosition()[0]));

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
	 * 			| result = (abs(getCoordinate() - victim.getCoordinate()) <= 1)
	 * 
	 * AND NOT SAME FACTION
	 * 
	 */
	@Model
	private boolean canAttack(Unit victim) {
		Coordinate attackerPos = this.getCoordinate();
		Coordinate victimPos = victim.getCoordinate();
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
			Unit possibleVictim = it.next();
			if (canAttack(possibleVictim))
				return possibleVictim;
		}
		return null;
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
	private void controlAttacking(double dt) throws IllegalTimeException, ArithmeticException {
		if (!isFalling()) {
			setTimeToCompletion((float) (getTimeToCompletion() - dt));
			if (getTimeToCompletion() < 0.0) {
	
				getDefender().setAttacked(true);
				getDefender().defend(this);
				getDefender().setAttacked(false);
				setState(State.EMPTY);
				
			}
		}
	}

	/**
	 * Defend an attack from an attacking unit. The defending unit will first try
	 * 		to dodge the incoming attack, if that fails it will try to block it,
	 * 		and if that fails too it will take damage.
	 * 
	 * @param	attacker
	 * 			The attacking unit.
	 */
	private void defend(Unit attacker) throws ArithmeticException {
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
		//System.out.println("got here");
		Coordinate newPosition = new Coordinate(getCoordinate().get(0) + random.nextInt(3) - 1,
				getCoordinate().get(1) + random.nextInt(3) - 1, 
				getCoordinate().get(2));
				
		/*double[] newPosition = this.getPosition();
		newPosition[0] += 2*random.nextDouble() - 1.0;
		newPosition[1] += 2*random.nextDouble() - 1.0;*/
		
		if (!canHaveAsPosition(newPosition)) 
			throw new IllegalPositionException(newPosition);
		
		setPosition(newPosition);
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
	 * Returns the time left for a unit to complete its work or attack.
	 */
	@Basic
	private float getTimeToCompletion() {
		return this.timeToCompletion;
	}

		
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
	public static boolean isValidTime(float value) {
		return ( (value > -10.0f) );
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
	public static boolean isValidTime(double value) {
		return ( (value >= 0.0) );
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
	 * 			| !(isValidTime(newValue))
	 */
	private void setTimeToCompletion(float newValue) throws IllegalTimeException {
		if (!isValidTime(newValue))
			throw new IllegalTimeException(newValue, this);
		this.timeToCompletion = newValue;
	}

	/**
	 * Field registering the time to completion.
	 */
	private float timeToCompletion;



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
	// EXTRA STATE VOOR MAKEN????
	
	
		
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
	 * Make the unit rest, if it's not currently moving or falling.
	 * 
	 */
	public void rest() throws IllegalTimeException {
		if (!isMoving() && !isFalling()) {
			startResting();
		}
	}
	
	
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
				if (this.getCurrentHitPoints() == this.getMaxHitPoints()) {
					setState(State.RESTING_STAM);
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
	 * 			| !isValidTime(newValue)
	 */
	private void setTimeResting(double newValue) throws IllegalTimeException {
		if (!isValidTime(newValue))
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
	 * 			| !isValidTime(newValue)
	 */
	private void setTimeAfterResting(double newValue) throws IllegalTimeException {
		if (!isValidTime(newValue))
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
	public void setDefaultBehavior(boolean value) {
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
	 * Variable registering whether the default behavior of the unit is enabled.
	 */
	private boolean defaultBehavior = true;
	
	
	
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
		if (!isValidState(state) )
			throw new IllegalArgumentException();
		this.state = state;
	}
	
	
	public static boolean isValidState(State state) {
		return State.contains(state);
	}
	
	

	/**
	 * Variable registering the state of the unit.
	 */
	private State state;
	
	
	
	@Basic @Raw
	public int getExperiencePoints() {
		return this.xp;
	}
	
	
	public static boolean isValidXP(int value) {
		return (value >= 0 && value < Integer.MAX_VALUE);
	}
	
	private int getTXP() {
		return this.tempXp;
	}
	
	
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
			updateWeight();
		}
	}
	
	private void addXP(int x) {
		if (!isValidXP(getExperiencePoints() + x)) 
			throw new ArithmeticException();
		this.xp += x;
		this.tempXp += x;
	}
	
	private void subTXP(int x) {
		this.tempXp -= x;
	}
	
	private int xp;
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
		//if ( (world != null) && !world.hasAsUnit(this) )
		//	throw new IllegalArgumentException();
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
		//if ( (faction != null) && !faction.hasAsUnit(this) )
		//	throw new IllegalArgumentException();
		if ( (faction == null) && (getFaction() != null) && (getFaction().hasAsUnit(this)) )
			throw new IllegalArgumentException();
		this.faction = faction;
	}
	
	
	/**
	 * Variable registering the faction to which this unit belongs.
	 */
	private Faction faction = null;
	
	
	public void terminate() {
		dropItem();
		getFaction().removeUnit(this);
		getWorld().removeUnit(this);
	}
	
	
	/* ISVALID POSITION etc IN WORLD IPV IN UNIT, ITEM AFZONDERLIJK???????
	 * 
	 */
	/* ISVALIDFACTION/UNITS IPV CANHAVEAS????????????
	/* tests */
		
	// Override equals etc in value classes!
	
	/* VRAGEN
	 * 
	 * 
	 * Nog methods @Raw?
	 * 
	 */
}

