package hillbillies.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.model.World.TerrainType;


/**
 * A class of nits with a number of attributes that can perform activities, 
 * attached to a faction and a world.
 * 
 * @invar	The position of a nit must always be valid, within the game world.
 * 			| canHaveAsPosition(getCoordinate())
 * 
 * @invar	The nit can have its name as its name.
 * 			| canHaveAsName(getName())
 * 
 * @invar	The weight of a nit must always be a valid weight for that nit.
 * 			| canHaveAsWeight(getWeight()) 
 * 
 * @invar	The strength of a nit must always be a valid strength.
 * 			| isValidStrength(getStrength()) 
 * 
 * @invar	The agility of a nit must always be a valid agility.
 * 			| isValidAgility(getAgility()) 
 * 
 * @invar	The toughness of a nit must always be a valid toughness.
 * 			| isValidToughness(getToughness()) 
 * 
 * @invar 	The current number of hitpoints must always be valid for this nit.
 * 			| canHaveAsHitPoints(getCurrentHitPoints())
 * 
 * @invar 	The current number of stamina points must always be valid for this nit.
 * 			| canHaveAsStaminaPoints(getCurrentStaminaPoints())
 * 
 * @invar	The orientation of a nit must always be valid.
 * 			| isValidOrientation(getOrientation()) 
 * 
 * @invar 	The current number of experience points must always be valid.
 * 			| isValidXP(getExperiencePoints())
 * 
 * @invar 	The current state of the nit must always be a valid state.
 * 			| canHaveAsState(getState())
 * 
 * @invar 	The nit can have its current target cube as its target cube.
 * 			| canHaveAsTargetCube(getTargetCube())
 * 
 * @invar 	The nit can always have its long term destination cube as its position.
 * 			| canHaveAsPosition(getDestCubeLT())
 * 
 * @invar 	The nit can always have its destination cube as its position.
 * 			| canHaveAsPosition(getDestination())
 * 
 * @invar 	The nit can attack the nit it is attacking.
 * 			| canAttack(getDefender())
 * 
 * @invar 	The time before completion of a work or attack task of the nit 
 * 			must be valid.
 * 			| isValidTime(getTimeToCompletion())
 * 
 * @invar 	The time the nit is resting is valid.
 * 			| isValidTime(getTimeResting())
 * 
 * @invar 	The time after the nit last rested is valid.
 * 			| isValidTime(getTimeAfterResting())
 * 
 * @invar 	The item the nit is carrying must be a valid item or null.
 * 			| isValidItem(getCarriedItem()) || getCarriedItem() == null
 * 
 * @invar	Each nit must have a proper world in which it belongs
 * 			| hasProperWorld()
 * 
 * @invar	Each nit must have a proper faction to which it belongs
 * 			| hasProperFaction()
 * 
 *@author Ruben Cartuyvels
 *@version	2.2
 *
 */
public abstract class Nit extends GameObject {

	
	
	/**
	 * Initialize a new nit with the given attributes, not yet attached to
	 * a world or a faction.
	 * 
	 * @param name
	 *            The name of the nit.
	 * @param initialPosition
	 *            The initial position of the nit.
	 * @param weight
	 *            The initial weight of the nit
	 * @param agility
	 *            The initial agility of the nit
	 * @param strength
	 *            The initial strength of the nit
	 * @param toughness
	 *            The initial toughness of the nit
	 * @param enableDefaultBehavior
	 *            Whether the default behavior of the nit is enabled
	 * 
	 * @post  the new name of the nit is equal to the given name.
	 * 		| new.getName() == name
	 * 
	 * @post the new position of the nit is equal to the given position.
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
	 * @post The new orientation of the nit is equal to Pi/2.
	 * 		| new.getOrientation() == Math.PI/2
	 * 
	 * @post The new current number of hitpoints of the nit is equal to the max number
	 * 			of hitpoints for the nit.
	 * 		| new.getCurrentHitPoints() == new.getMaxHitPoints()
	 * 
	 * @post The new current number of stamina points of the nit is equal to the max number
	 * 			of stamina points for the nit.
	 * 		| new.getCurrentStaminaPoints() == new.getMaxStaminaPoints()
	 * 
	 * @post The default behavior of the nit is equal to the given value for default behavior.
	 * 		| new.isDefaultBehaviorEnabled() == enableDefaultBehavior
	 * 
	 * 
	 * @throws IllegalPositionException(position, this)
	 *             The nit cannot have the given position (out of bounds).
	 *             | ! canHaveAsPosition(position)
	 *             
	 * @throws IllegalNameException(name, this)
	 *             The nit cannot have the given name.
	 *             | ! canHaveAsName(name)            
	 */
	@Deprecated @Raw @Model
	protected Nit (String name, Coordinate initialPosition, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) 
					throws IllegalPositionException, IllegalNameException {
		
		setPosition(World.getCubeCenter(initialPosition));

		setName(name);
		
		setAgility(agility);
		setStrength(strength);
		setToughness(toughness);
		setWeight(weight);
		
		updateCurrentHitPoints(getMaxHitPoints());
		updateCurrentStaminaPoints(getMaxStaminaPoints());

		setOrientation((float)(Math.PI/2.0));

		this.setDefaultBehavior(enableDefaultBehavior);
		this.setState(State.EMPTY);
	}
	
	
	
	/**
	 * Initialize a new nit with the given world as its world, the given default
	 * behavior and the given faction as its faction, with random attributes 
	 * and a random name, with an orientation and an empty state, not carrying an item.
	 * 
	 * @param 	enableDefaultBehavior
	 *        	Whether the default behavior of the nit is enabled.
	 * 
	 * @param	world
	 * 			The world for this new nit.
	 * 
	 * @param	faction
	 * 			The faction for this new nit.
	 * 
	 * @effect	The nits world is set to the given world
	 * 			| setWorld(world)
	 * 
	 * @effect	The nits position is set to a random position in its world.
	 * 			| setPosition(getWorld().getRandomNeighbouringSolidCube())
	 * 
	 * @effect	The nits name is set to a random name.
	 * 			| setName(generateName())
	 * 
	 * @effect	The nits agility is set to a random initial agility.
	 * 			| setAgility(generateInitialSkill())
	 * 
	 * @effect	The nits strength is set to a random initial strength.
	 * 			| setStrength(generateInitialSkill())
	 * 
	 * @effect	The nits toughness is set to a random initial toughness.
	 * 			| setToughness(generateInitialSkill())
	 * 
	 * @effect	The nits weight is set to a random initial weight.
	 * 			| setWeight(generateInitialWeight())
	 * 
	 * @effect	The nits HP are set to the current maximum.
	 * 			| updateCurrentHitPoints(getMaxHitPoints())
	 * 
	 * @effect	The nits stamina points are set to the current maximum.
	 * 			| updateCurrentStaminaPoints(getMaxStaminaPoints())
	 * 
	 * @effect	The nits orientation is set to an initial value.
	 * 			| setOrientation((Math.PI/2.0))
	 * 
	 * @effect	The nits state is set to empty.
	 * 			| setState(State.EMPTY)
	 * 
	 * @effect	The default behavior of the nit is set to the given
	 * 			default behavior.
	 * 			| setDefaultBehavior(enableDefaultBehavior)
	 * 
	 * @throws	IllegalPositionException(position, this)
	 *         	The nit cannot have the given position.
	 *          | ! canHaveAsPosition(position)
	 *             
	 * @throws	IllegalNameException(name, this)
	 *          The nit cannot have the given name.
	 *          | ! isValidName(name)            
	 */
	@Raw @Model
	protected Nit (World world, Faction faction, boolean enableDefaultBehavior) 
			throws IllegalPositionException, IllegalNameException {
		
		super(world);
		
		faction.addNit(this);

		//Coordinate position = getWorld().getRandomNeighbouringSolidCube();
		//setPosition(position);

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
	 * Return the current name of the nit.
	 */
	@Basic @Raw
	public String getName() {
		return this.name;
	}
	
	
	/**
	 * Generate a valid name for the nit.
	 * 
	 * @return 	The nit can have the generated name as its name.
	 * 			| canHaveAsName(result)
	 */
	@Model
	protected abstract String generateName();
	
	
	/**
	 * Check if the nit can have the given name as its name.
	 * 
	 * @param name
	 * 			the name to check.
	 */
	public abstract boolean canHaveAsName(String name);

	
	
	/**
	 * Set the name of the nit to the given name.
	 * 	
	 * @param 	newName
	 * 			The new name for this nit.
	 * 
	 * @post 	The new name of this nit is equal to the
	 * 			given name.
	 * 			| new.getName() == newName
	 * 
	 * @throws 	IllegalNameException
	 * 			The nit cannot have the given name as its name.
	 * 			| !canHaveAsName(newName)
	 */
	@Raw
	public void setName(String newName) throws IllegalNameException {
		if (!canHaveAsName(newName))
			throw new IllegalNameException(newName, this);
		this.name = newName;
	}
	
	
	/**
	 * Variable registering the name of the nit.
	 */
	private String name = "";
	
	
	/**
	 * Return the nits weight.
	 */
	@Basic @Raw
	public int getWeight() {
		return this.weight;
	}
	
	
	/**
	 * Set the weight of the nit to the given new value.
	 * 
	 * @param newValue
	 * 			The new weight for this nit.
	 * 
	 * @post if the given weight is valid for this nit, the new weight
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
	 * 			of the agility and the strength of the nit.
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
	 * 			| canHaveAsWeight(result) &&
	 * 			|	result >= (getStrength()+getAgility())/2)
	 * 			|	&& result <= getMaxInitialSkill()
	 */
	@Model
	private int generateInitialWeight() {
		return (getStrength() + getAgility())/2
				+ random.nextInt(getMaxInitialSkill() - (getStrength()+getAgility())/2);
	}
	
	
	/**
	 * Update the weight of the nit, to match its minimum value (mean
	 * of agility and strength).
	 * 
	 * @post	the nit can have its new weight as its weight.
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
	 * Return the nits total weight, i.e. its weight plus the weight
	 * of the item it carries.
	 * 
	 * @return	if the nit is carrying an item, the result is the weight of
	 * 			the nit plus the weight of the item. Else, the result is the
	 * 			weight of the nit.
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
	 * Variable registering the weight of the nit.
	 */
	private int weight = 0;
	
	

	/**
	 * Return the nits strength.
	 */
	@Basic @Raw
	public int getStrength() {
		return this.strength;
	}
	
	
	/**
	 * Set the strength of the nit to the given new value.
	 * 
	 * @param newValue
	 * 			The new strength for this nit.
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
	 * Variable registering the strength of the nit.
	 */
	private int strength = 0;

	
	/**
	 * Return the nits agility.
	 */
	@Basic @Raw
	public int getAgility() {
		return this.agility;
	}
	
	
	/**
	 * Set the agility of the nit to the given new value.
	 * 
	 * @param 	newValue
	 * 			The new agility for this nit.
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
	 * Variable registering the agility of the nit.
	 */
	private int agility = 0;

	
	/**
	 * Return the nits toughness.
	 */
	@Basic @Raw
	public int getToughness() {
		return this.toughness;
	}
	
	
	/**
	 * Set the toughness of the nit to the given new value.
	 * 
	 * @param 	newValue
	 * 			The new toughness for this nit.
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
	 * Variable registering the toughness of the nit.
	 */
	private int toughness = 0;
	
	
	/**
	 * Generate a random initial value for a nits toughness, strength or agility.
	 * 
	 * @return 	an integer value between the minimum initial value and maximum 
	 * 			initial value for a skill.
	 * 			| isValidStrength(result)
	 * 			|	&& result >= getMinInitialSkill()
	 * 			| 	&& result <= getMaxInitialSkill()
	 */
	@Model
	private int generateInitialSkill() {
		return random.nextInt(getMaxInitialSkill() - getMinInitialSkill() + 1)
				+ getMinInitialSkill();
	}
	
	
	/**
	 * Return the minimum initial value for a nits strength, toughness and agility.
	 */
	@Basic @Immutable
	public final static int getMinInitialSkill() {
		return minInitialSkill;
	}
	
	
	/**
	 * Return the maximum initial value for a nits strength, toughness and agility.
	 */
	@Basic @Immutable
	public final static int getMaxInitialSkill() {
		return maxInitialSkill;
	}
	
	
	/**
	 * Variable registering the minimum initial value for a skill of the nit.
	 */
	private final static int minInitialSkill = 25;
	
	
	/**
	 * Variable registering the maximum initial value for a skill of the nit.
	 */
	private final static int maxInitialSkill = 100;

	
	
	/**
	 * Return the nits current number of hitpoints.
	 */
	@Basic @Raw
	public int getCurrentHitPoints() {
		return this.currentHitPoints;
	}
	
	
	/**
	 * Check if a given value is a valid hitpoints value.
	 * 
	 * @param  value
	 * 			The value to be checked.
	 * @return true if and only if the value is larger than or equal to the
	 * 			minimum and smaller than or equal to the maximum amount of
	 * 			hitpoints for this nit.
	 * 			| result ==  (value >= getMinHitPoints() && value <= getMaxHitPoints() )
	 */
	@Raw
	public boolean canHaveAsHitPoints(int value) {
		return (value >= getMinHitPoints() && value <= getMaxHitPoints());
	}
	
	
	
	/**
	 * Update the current number of hitpoints of the nit.
	 * 
	 * @param 	newValue
	 * 			The new value for the current no. of hitpoints of the nit.
	 * 
	 * @pre		the given new value must be a valid hitpoints value for this nit.
	 * 			| canHaveAsHitPoints(newValue)
	 * 
	 * @post 	The new no. of hitpoints of the nit is equal to
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
	 * Return the nits minimum number of hitpoints.
	 */
	@Basic @Immutable @Raw
	public final static int getMinHitPoints() {
		return minHP;
	}
	
	
	/**
	 * Return the nits maximum number of HP.
	 */
	@Raw
	public abstract int getMaxHitPoints();
	
	
	/**
	 * Variable registering the current number of hitpoints of the nit.
	 */
	private int currentHitPoints = 0;

	/**
	 * Variable registering the minimum number of hitpoints of the nit.
	 */
	private static final int minHP = 0;

	
	
	
	/**
	 * Return the nits current number of stamina points.
	 */
	@Basic @Raw
	public int getCurrentStaminaPoints() {
		return this.currentStaminaPoints;
	}
	
	/**
	 * Update the current number of stamina points of the nit.
	 * 
	 * @param 	newValue
	 * 			The new value for the current no. of stamina points of the nit.
	 * 
	 * @pre		the given new value must be a valid stamina points value for this nit.
	 * 			| canHaveAsStaminaPoints(newValue)
	 * 
	 * @post 	The new no. of stamina points of the nit is equal to
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
	 * Return the nits minimum number of stamina points.
	 */
	@Basic @Immutable
	public final static int getMinStaminaPoints() {
		return minSP;
	}

	
	/**
	 * Return the nits maximum number of HP.
	 */
	@Raw
	public abstract int getMaxStaminaPoints();
	

	/**
	 * Check if a given value is a valid stamina points value.
	 * 
	 * @param 	value
	 * 			The value to be checked.
	 * @return 	true if and only if the value is larger than or equal to
	 * 			the minimum and smaller than or equal to the maximum amount of
	 * 			stamina points for this nit.
	 * 			| result =  (value >= getMinStaminaPoints && 
	 * 			|		value <= getMaxStaminaPoints() )
	 */
	@Raw
	public boolean canHaveAsStaminaPoints(int value) {
		return (value >= getMinStaminaPoints() && value <= getMaxStaminaPoints());
	}


	/**
	 * Variable registering the current number of stamina points of the nit.
	 */
	private int currentStaminaPoints = 0;

	
	/**
	 * Variable registering the minimum number of stamina points of the nit.
	 */
	private static final int minSP = 0;
	
	
	
	
	/**
	 * Return the nits orientation.
	 */
	// TODO FIX ORIENTATION!
	@Basic @Raw
	public float getOrientation() {
		return (float) (-orientation + ( Math.PI/*/2.0f*/) + ( 3*Math.PI/2));
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
	 * Set the orientation of the nit to the given new value.
	 * 
	 * @param 	newValue
	 * 			The new orientation for this nit.
	 * 
	 * @post 	if the given orientation is a float number between
	 * 			-Pi and Pi, then the new orientation of the nit is
	 * 			equal to the given orientation.
	 * 			| if (newValue >= -Math.Pi && newValue <= Math.PI)
	 * 			| 	then new.getOrientation() == newValue
	 */
	@Raw
	protected void setOrientation(float newValue) {
		if (isValidOrientation(newValue)) {
			this.orientation = newValue;
		}
	}

	
	/**
	 * Variable registering the orientation of the nit.
	 */
	private float orientation = 0.0f;

	
	
	
	/* *********************************************************
	 * 
	 * 							ACTIVITIES
	 *
	 **********************************************************/
	
	
	
	
	/**
	 * Advance the game time and manage activities of the nit.
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
		
		if (getTXP() > 10) {
			controlXP();
		}

		if (!isResting()) {
			setTimeAfterResting(getTimeAfterResting() + dt);
		}

		if (isAttacked())
			setState(State.EMPTY);

		
		else if (getTimeAfterResting() >= 180.0) {
			startResting();
		}

		else if (isResting()) {
			controlResting(dt);
		}
		else if (isMoving()) {
			controlMoving(dt);
		}
		else if (getState() == State.EMPTY && !isAttacked()) {
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
	
	
	
	/**
	 * Manage the waiting of the nit, i.e. when the state of the nit is equal to empty.
	 * 
	 * @param 	dt
	 * 			the game time interval in which to manage the falling behavior.
	 * 
	 * @effect	If the default behavior is not enabled, nothing happens.
	 * 			Else, if the nit does not already have a task,
	 * 			the nit checks if its factions scheduler has a task that has not
	 * 			yet been assigned to a nit. if so, the nits task is set to the unassigned
	 * 			task with the highest priority that the nit has not tried to execute as last
	 * 			task and was interrupted.
	 * 			| if (isDefaultBehaviorEnabled() 
	 * 			|		if(!hasAssignedTask() 
	 * 			|			&& getFaction().getScheduler().hasUnassignedTask() 
	 * 			|			&& getInterruptedTask() != getFaction().getScheduler().getHighestPriorityTask())
	 * 			| then setAssignedTask(getFaction().getScheduler().getHighestPriorityTask())
	 * 
	 * @effect	If the nit already has a task and this task is not completed yet, is will
	 * 			execute this task. If the task is completed, it is removed.
	 * 			| 		if (hasAssignedTask())
	 * 			|			if (!getAssignedTask().isCompleted())
	 * 			|				then getAssignedTask().execute(dt)
	 * 			|			else removeAssignedTask()
	 * 
	 * @effect	if the default behavior is enabled and the above conditions are not met,
	 * 			there is an equal chance that the nit will execute each
	 * 			of the following activities: move to a random cube, start working at
	 * 			a random cube, rest or attack an enemy if there is one in range.
	 * 			|	else (
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
			
			//TODO
			if (!hasAssignedTask() && getFaction().getScheduler().hasUnassignedTask() ) {
				Iterator<Task> it = getFaction().getScheduler().getIterator();
				
				Task task = getFaction().getScheduler().getHighestPriorityTask();
				while ((task == getInterruptedTask() || task.beingExecuted() )  && it.hasNext() ) {
					task = it.next();
				}
				//System.out.println(task.toString());
				if (getInterruptedTask() != task) {
					task.setBeingExecuted(true);
					//System.out.println(task.toString());
					setAssignedTask(task);
					
				//if (getAssignedTask().isCompleted()) {
				//	removeAssignedTask();
				//}
				}
			}
			
			else if (hasAssignedTask()) {
				if (!getAssignedTask().isCompleted()) {
					//System.out.println(getAssignedTask().toString());
					try {
						getAssignedTask().execute(dt);
					} catch (Exception e) {
						stopExecutingTask();
					}
				}
				if (getAssignedTask().isCompleted()) {
					removeAssignedTask();
				}
			}
			
			else  {
			
			
				double dice = random.nextDouble();
				//System.out.println(dice);
				
				if (dice < 1.0/4.0) {
					Coordinate destCube = getRandomReachableCube();
					//System.out.println(destCube.toString());
					//System.out.println(this.isReachable(destCube));
					moveTo(destCube);
					
				} else if ( dice > 1.0/4.0 && dice < 2.0/4.0) {
					
					Coordinate targetCube = getRandomNeighbouringCube();
					
					//System.out.println(getCoordinate().toString());
					//System.out.println(targetCube.toString());
					
					workAt(targetCube);
					
				}
				else if ( dice > 2.0/4.0 && dice < 3.0/4.0 
						&& !isAttacking() && !isAttacked() && enemiesInRange()) {
					attack(getEnemyInRange());
					//System.out.println("Attacking");
				}
				else {
					rest();
					//setDefaultBehavior(false);
				}
				
			}
			
		}
	}
	
	
	/**
	 * Check if the nit has an assigned task.
	 * 
	 * @return	true if the nit has an assigned task.
	 * 			| result == (getAssignedTask != null)
	 */
	public boolean hasAssignedTask() {
		return (getAssignedTask() != null);
	}
	
	
	/**
	 * Return the nits assigned task.
	 */
	@Basic
	public Task getAssignedTask() {
		return this.assignedTask;
	}
	
	
	/**
	 * Set the nits assigned task to a given task.
	 * 
	 * @param 	task
	 * 			the given task.
	 * 
	 * @post	the nits assigned task is equal to the given task.
	 * 			| new.getAssignedTask() == task
	 * 
	 * @post	the task references this nit as the nit it is assigned to.
	 * 			| (new task).getAssignedUnit() == this
	 * 
	 * @throws IllegalArgumentException
	 * 			the nit cannot have the given task as its task.
	 */
	public void setAssignedTask(Task task) throws IllegalArgumentException {
		if (!canHaveAsTask(task))
			throw new IllegalArgumentException();
		this.assignedTask = task;
		task.setAssignedNit(this);
	}
	
	
	/**
	 * Remove the currently assigned task as the nits task.
	 * 
	 * @effect	if the nit has a task, this task is no longer executed,
	 * 			|	getAssignedTask().setBeingExecuted(false);
	 * 
	 * @effect	the task will no longer reference a nit as the nit it is
	 * 			assigned to,
	 * 			| 	getAssignedTask().setAssignedNit(null)
	 * 
	 * @effect	the task is terminated,
	 * 			| getAssignedTask().terminate()
	 * 
	 * @post	and this nit does no longer reference a task.
	 * 			| new.getAssignedTask() == null
	 */
	public void removeAssignedTask() {
		if ( hasAssignedTask() ) {
			getAssignedTask().setBeingExecuted(false);
			getAssignedTask().setAssignedNit(null);
			getAssignedTask().terminate();
			this.assignedTask = null;
		}
	}
	
	
	/**
	 * Stop executing a task, this happens when the task is interrupted. The
	 * interrupted task is stored.
	 * 
	 * @effect	the nits last interrupted task is set to the current task
	 * 			|	setInterruptedTask(getAssignedTask())
	 * 
	 * @effect	the current task is interrupted.
	 * 			| 	getAssignedTask().interrupt()
	 * 
	 * @post	the nit no longer references a task.
	 * 			|	new.getAssignedTask() == null
	 */
	public void stopExecutingTask() {
		setInterruptedTask(getAssignedTask());
		getAssignedTask().interrupt();
		this.assignedTask = null;
	}
	
	
	/**
	 * Check if the nit can have a given task as its assigned task.
	 * 
	 * @param 	task
	 * 			the task to check.
	 * 
	 * @return	true if and only if the given task is not equal to null.
	 * 			| result == (task != null)
	 */
	public boolean canHaveAsTask(Task task) {
		if (/*task.getAssignedUnit() != this ||*/ task == null)
			return false;
		return true;
	}
	
	
	/**
	 * Variable registering the nits current assigned task.
	 */
	private Task assignedTask = null;
	
	
	/**
	 * Return the nits last interrupted task.
	 */
	@Basic
	private Task getInterruptedTask() {
		return this.interruptedTask;
	}
	
	
	/**
	 * Set the units last interrupted task to a given task.
	 * 
	 * @param 	task
	 * 			the given task.
	 * 
	 * @post	the units interrupted task is equal to the given task.
	 * 			| new.getInterruptedTask() == task
	 * 
	 * @throws	IllegalArgumentException
	 * 			The unit can not have the given task as a task.
	 * 			| !canHaveAsTask(task)
	 */
	private void setInterruptedTask(Task task) throws IllegalArgumentException {
		if (!canHaveAsTask(task))
			throw new IllegalArgumentException();
		this.interruptedTask = task;
	}
	
	private Task interruptedTask = null;


	
	/**
	 * Returns true if the nit is moving.
	 * 
	 * @return	True if and only if the nit is moving.
	 * 			| result == (getState() == State.MOVING)
	 */
	public boolean isMoving() {
		return (getState() == State.MOVING);
	}
	
	
	/**
	 * Start moving to an adjacent cube.
	 * 
	 * @param 	cubeDirection
	 * 			The relative directions of the cube to which the nit has to move.
	 * 
	 * @post	The destination of the nit is set to the right adjacent cube, if
	 * 			the nit is not already moving and if it can move.
	 * 			| if (!isMoving && canMove() )
	 * 			| 	then new.getDestination() == determineCube(cubeDirection)
	 * 
	 * @post	The orientation of the nit is set towards the destination, if
	 * 			the nit is not already moving and if it can move.
	 * 			| 		new.getOrientation() == Math.atan2(getVelocity()[0], getVelocity()[2] )
	 * 
	 * @effect	The nits state is set to moving, if the nit is not already moving 
	 * 			and if it can move.
	 * 			| 		startMoving()
	 * 
	 * @throws  IllegalArgumentException
	 * 			The given direction is not a valid direction.
	 * 			| !isValidCubeDirection(cubeDirection)
	 */
	public void moveToAdjacent(int[] cubeDirection) 
			throws IllegalPositionException, IllegalArgumentException {
		
		if (!isValidCubeDirection(cubeDirection))
			throw new IllegalArgumentException();

		if (!isMoving() && canMove() ) {
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
	 * @post	If the nit can move, the long term destination of the nit will be equal to
	 * 			the destination cube,
	 * 			| if (canMove())
	 * 			| 	then new.getDestCubeLT() == destCube
	 * 
	 * @post	the long term destination reached field will be equal to false,
	 * 			| 		new.isDestCubeLTReached() == false
	 * 
	 * @effect	a path will be computed. If the path is equal to null the destination
	 * 			is not reachable and the long term destination reached field 
	 * 			is set to true, hence the nit will not move. Otherwise, if a path
	 * 			is found, the nit will move towards the first cube of the path.
	 * 			|	if (getState() != State.RESTING_1 && computePath(getDestCubeLT()) != null )
	 * 			|		then moveTowards(getNeighbourWSmallestN(computePath(getDestCubeLT()), getCoordinate()))
	 * 
	 * @effect	If the computed path is equal to null, the long term destination reached field
	 * 			is set to true, to cancel the movement, and if the unit has an assigned task
	 * 			this task is stopped.
	 * 			| 	if (computePath(getDestCubeLT()) == null)
	 * 			|		then this.destCubeLTReached = true
	 *			|		if (hasAssignedTask())
	 *			|			then stopExecutingTask()
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
		
		if (canMove()) {
			setDestCubeLT(destCube);
			this.destCubeLTReached = false;
			
			
			if (getState() != State.RESTING_1) {
				
				Coordinate startCube = getCoordinate();
				List<Tuple> path = computePath(getDestCubeLT());
				
				
				if (path == null) {
					this.destCubeLTReached = true;
					if (hasAssignedTask())
						stopExecutingTask();
				}
				
				else if (Tuple.containsCube(path, startCube)) {
					Tuple nextTuple = getNeighbourWSmallestN(path, startCube);
					
					if (nextTuple != null)
						moveTowards(nextTuple.cube);
				}
			}
		}
	}
	
	
	/**
	 * Check if the nit can move.
	 */
	protected abstract boolean canMove();
	
	
	/**
	 * Get a random cube neighboring the cube currently occupied by the nit.
	 * 
	 * @return	the result is neighboring the current cube.
	 * 			| getWorld().isNeighbouring(result, getCoordinate())
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
	 * @return	The tuple with the cube that is neighboring the given cube with the smallest 
	 * 			weight.
	 */
	private Tuple getNeighbourWSmallestN(List<Tuple> path, Coordinate cube) {
		Set<Coordinate> neighbours = getWorld().getNeighbours(cube);
		List<Tuple> neighboursInPath = new ArrayList<Tuple>();
		
		for (Coordinate neighbour: neighbours) {
			if (Tuple.containsCube(path, neighbour)) {
				
				neighboursInPath.add(Tuple.getCubeTuple(path, neighbour));
			}
			
		}
		return smallestN(neighboursInPath);
	}
	
	
	/**
	 * Find the tuple from a given list of tuples with the smallest weight.
	 * 
	 * @param	lst
	 * 			the given list of cubes.
	 * 
	 * @return	the tuple with the smallest weight.
	 * 			|	for each tuple in lst:
	 * 			|		tuple.n >= result.n
	 */
	private static Tuple smallestN(List<Tuple> lst) {
		if (lst.isEmpty()){
			return null;
		} else {
			Tuple smallest = lst.get(0);
			for (Tuple element: lst){
				if (element.n < smallest.n){
					smallest = element;
				}
			}
			return smallest;
		}
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
	protected boolean isReachable(Coordinate destCube) {
		return (computePath(destCube) != null);
	}
	
	
	/**
	 * Return a random cube in the game world that is reachable for this nit.
	 */
	protected abstract Coordinate getRandomReachableCube();
	
	
	
	/**
	 * Compute a path from the current cube to the given destination cube.
	 * 
	 * @param 	destCube
	 * 			The destination cube to compute a path to.
	 * 
	 * @return	if a path can be found within a reasonable time, this path is
	 * 			returned.
	 * 			| (for some tuple in result: tuple.cube == getCoordinate()
	 * 			|	&& for some tuple in result: tuple.cube == destCube
	 * 			|		&& (for each tuple in result: 
	 * 			|			for some tuple2 in result: getWorld().isNeighbouring(tuple, tuple2) ) )
	 * 
	 * @return	if no path can be found within a reasonable time, null is returned.
	 * 			| result == null
	 */
	private List<Tuple> computePath(Coordinate destCube) {
		List<Tuple> path = new ArrayList<Tuple>();
		path.add(new Tuple(destCube, 0));
		
		int iterations = 0;
		while(!Tuple.containsCube(path, getCoordinate()) 
				&& Tuple.hasNext(path) && iterations < 3001) {
			
			Tuple nextTuple = Tuple.getNext(path);
			path = search(nextTuple, path);
			nextTuple.isChecked = true;
			iterations++;

			if (iterations >= 3001) {
				path = null;
				break;
			}
		}
		return path;
	}
	
	
	/**
	 * Search for a path in a given list of tuples.
	 */
	protected abstract List<Tuple> search(Tuple currentTuple, List<Tuple> path);
	
	
	
	/**
	 * Makes the nit start moving, i.e. sets its state to moving.
	 * 
	 * @post	The nits state is equal to moving
	 * 			| new.getState() == State.MOVING
	 */
	@Model
	private void startMoving() {
		setState(State.MOVING);
	}
	
	
	/**
	 * Manage the moving of the nit, i.e. when the state of the nit is equal to moving.
	 * 
	 * @param 	dt
	 * 			the game time interval in which to manage the moving behavior.
	 * 
	 * @effect	if the nit is sprinting, its stamina points will be updated. If the
	 * 			nit is out of stamina points it will stop sprinting and its stamina points
	 * 			will be set to the minimum value.
	 * 			| if (isSprinting())
	 * 			|		then updateCurrentStaminaPoints( 
	 * 			|				(getCurrentStaminaPoints() - (dt/0.1))  )
	 * 			|		if (getCurrentStaminaPoints() <= getMinStaminaPoints())
	 * 			|			then stopSprinting()
	 * 			|				 updateCurrentStaminaPoints(getMinStaminaPoints())
	 * 
	 * @effect	if the nits default behavior is enabled, and its stamina points are above
	 * 			the minimum, there is a 0.1 chance that the nit will start sprinting.
	 * 			| if (isDefaultBehaviorEnabled() 
	 * 			|		&& getCurrentStaminaPoints() > getMinStaminaPoints()
	 * 			|		&& random < 0.1)
	 * 			|	then startSprinting()
	 * 
	 * @effect	if the nit does not reach its destination (short term) within this
	 * 			time interval, its position is updated.
	 * 			| if (!reached(dt))
	 * 			|	then updatePosition(dt)
	 * 
	 * @effect	if the nit does reach its short term destination within this time interval,
	 * 			its position is set to the center of the destination cube and 
	 * 			1 experience point is added to its XP.
	 * 			| if (reached(dt)) 
	 * 			| 	then setPosition(getDestination())
	 *			|		 addXP(1);
	 *
	 * @effect 	if the nit reaches its short term destination within this time interval
	 * 			and this is also its long term destination, the nits long term destination
	 * 			reached field is set to true, and if it was sprinting it stops sprinting.
	 * 			| if (reached(dt) && getCoordinate().equals(getDestCubeLT()) )
	 * 			|	then this.destCubeLTReached = true
	 *			|			if (isSprinting()) 
	 *			|				then stopSprinting()
	 * 
	 * @effect	if the nit does reach its short term destination within this time interval,
	 * 			its state is set to empty.
	 * 			| if (reached(dt))
	 * 			| 	then setState(State.EMPTY)
	 * 
	 * @effect	if the nit is not occupying a cube neighbouring a solid cube, it MAY fall
	 * 			and this method will have no other effect.
	 * 			| if (!isNeighbouringSolid(getCoordinate())
	 * 			|	then fall()?
	 * 
	 */
	protected void controlMoving(double dt) throws IllegalPositionException, 
				ArithmeticException {
		
			if (isSprinting()) {
				updateCurrentStaminaPoints( (int) (getCurrentStaminaPoints() - (dt/0.1)));
				if (getCurrentStaminaPoints() <= getMinStaminaPoints()) {
					stopSprinting();
					updateCurrentStaminaPoints(getMinStaminaPoints());
				}	
			}
		
			if (isDefaultBehaviorEnabled() && getCurrentStaminaPoints() > getMinStaminaPoints()) {
				double dice = random.nextDouble();
				if (dice < 0.1) {
					startSprinting();
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
	
	
	
	/**
	 * Move towards an adjacent cube. Compute the direction and calls 
	 * moveToAdjacent method.
	 * 
	 * @param	destCube	
	 * 			The cube to move towards.
	 * 
	 * @effect	if the given destination cube is not equal to the nits current
	 * 			cube, the nit starts moving to the destination cube.
	 * 			| if (!getCoordinate().equals(destCube))
	 * 			| 	 then moveToAdjacent(destCube)
	 * 
	 * @throws	IllegalArgumentException
	 * 			the given destination cube is not a neighbouring cube of the nits
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
	public static boolean isValidCubeDirection(int[] cubeDirection) {
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
	 * Returns the destination of the nit.
	 */
	@Basic @Model
	public Coordinate getDestination() {
		return this.destination;
	}
	
	

	/**
	 * Set the destination of the nit to the given coordinates.
	 * 
	 * @param 	newDestination
	 * 			The new destination coordinates.
	 * 
	 * @post 	The new destination of this nit is equal to the
	 * 			given coordinates.
	 * 			| new.getDestination() == newDestination
	 * 
	 * @throws 	IllegalPositionException
	 * 			The given destination is not a valid position.
	 * 			| !canHaveAsPosition(newDestination)
	 */
	@Raw
	protected void setDestination(Coordinate newDestination) throws IllegalPositionException {
		if (!canHaveAsPosition(newDestination)) {
			throw new IllegalPositionException(newDestination);
		}
		this.destination = newDestination;
	}

	
	/**
	 * Returns true if the nit reaches its destination in the current time span.
	 * 
	 * @param	dt
	 * 			The current time span.
	 * 
	 * @return	True if and only if the nit reaches the destination.
	 * 			| result == (getDistanceTo(getPosition(), 
	 * 			|		World.getCubeCenter(getDestination())) < getCurrentSpeed()*dt )
	 */
	private boolean reached(double dt) throws IllegalPositionException {
		
		return (getWorld().getDistanceTo(getPosition(), World.getCubeCenter(getDestination())) < getCurrentSpeed()*dt);
	}


	
	
	/**
	 * Returns the long term destination of the nit.
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
	 * Set the long term destination of the nit to the given coordinates.
	 * 
	 * @param 	destCubeLT
	 * 			The new long term destination cube coordinates.
	 * 
	 * @post 	The new long term destination of this nit is equal to the
	 * 			given cube coordinates.
	 * 			| new.getDestCubeLT() == destCubeLT
	 * 
	 * @throws 	IllegalPositionException
	 * 			The given position is not valid.
	 * 			| !(canHaveAsPosition(destCubeLT)
	 */
	@Raw
	protected void setDestCubeLT(Coordinate destCubeLT) throws IllegalPositionException {
		if ( !canHaveAsPosition(destCubeLT) )
			throw new IllegalPositionException(destCubeLT);
		this.destCubeLT = destCubeLT;
	}


	/**
	 * Variable registering the destination of the nit in the game world.
	 */
	private Coordinate destination = new Coordinate(0,0,0);

	/**
	 * Variable registering the long term destination of the nit in the game world.
	 */
	private Coordinate destCubeLT = new Coordinate(0,0,0);
	
	/**
	 * Variable registering whether the long term destination had been reached.
	 */
	private boolean destCubeLTReached = true;
	
	
	
	/**
	 * Returns the nits movement speed, which depends on the base speed, 
	 * 		the moving direction and on whether the nit is currently moving or not.
	 * 
	 * @return	The nits speed if the nit is not moving, which is equal to 
	 * 			zero.
	 * 			| result == 0.0
	 * 
	 * @return	The nits speed speed if the nit is moving upwards, which is
	 * 			equal to half of the base speed.
	 * 			| result == 0.5*getBaseSpeed()
	 * 
	 * @return	The nits speed speed if the nit is moving downwards, which is
	 * 			equal to 1.2 times the base speed.
	 * 			| result == 1.2*getBaseSpeed()
	 * 
	 * @return	The nits speed speed if the nits z coordinate is not changing
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
	 * Returns the nits velocity.
	 * 
	 * @return	The nits current velocity vector,
	 * 			which is smaller than or equal to to its speed multiplied by
	 * 			the difference of its target position coordinates and its current
	 * 			position coordinates, divided by the distance to the destination.
	 * 			| 	result <= getCurrentSpeed()*(World.getCubeCenter(getDestination()) 
	 * 			|					- getPosition() )
	 * 			|		/ getDistanceTo(getPosition(), World.getCubeCenter(getDestination()))
	 */
	@Model @Override
	protected double[] getVelocity() throws IllegalPositionException {
		if (!isMoving())
			return super.getVelocity();
		
		double d = getWorld().getDistanceTo(getPosition(), World.getCubeCenter(getDestination()));
		double[] v = new double[3];
		for (int i=0; i<3; ++i) {
			v[i] = getCurrentSpeed()*(World.getCubeCenter(getDestination())[i]-getPosition()[i])/d;
		}
		return v;
	}
	
	
	/**
	 * Returns the nits base speed.
	 * 
	 * @return	The nits base speed if the nit is not sprinting, 
	 * 			which is equal to 1.5 times the quotient of its strength added 
	 * 			by its agility, and 2 times its weight.
	 * 			| result == 1.5*(getStrength()+getAgility())/(2.0*getWeight())
	 * 
	 * @return	The nits base speed if the nit is sprinting, 
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
	 * Returns whether the nit is sprinting or not.
	 */
	@Basic
	public boolean isSprinting() {
		return this.isSprinting;
	}
	
	
	/**
	 * Makes the nit start sprinting.
	 * 
	 * @post	If the nit can sprint
	 * 			then its sprinting field is set to true.
	 * 			| if (canSprint())
	 * 			| 	then new.isSprinting() == true
	 */
	public void startSprinting() {
		if (canSprint()) {
			this.isSprinting = true;
		}
	}
	
	
	/**
	 * Check whether the nit can start sprinting or not.
	 */
	protected abstract boolean canSprint();
	
	
	/**
	 * Makes the nit stop sprinting.
	 * 
	 * @post	The nits sprinting field is set to false.
	 * 			| new.isSprinting() == false
	 */
	public void stopSprinting() {
		this.isSprinting = false;
	}

	
	/**
	 * Variable registering the whether the nit is sprinting.
	 */
	private boolean isSprinting = false;


	/**
	 * Returns true if the nit is working.
	 * 
	 * @return	True if and only if the nit is working.
	 * 			| result == (getState() == State.WORKING)
	 */
	public boolean isWorking() {
		return (getState() == State.WORKING);
	}
	
	
	/**
	 * Makes the nit work at a given target cube.
	 * 
	 * @effect	if the nit can work, the nits target cube will be set to the given
	 * 			target cube.
	 * 			| if (canWork())
	 * 			| 	then setTargetCube(targetCube)
	 * 
	 * @effect	if the nit can work, the nit will start working
	 * 			| if (canWork())
	 * 			| 	then startWorking()
	 * 
	 * @effect	if the nit can work, its orientation is set towards the target cube.
	 * 			| if (canWork())
	 * 			| 	then setOrientation( (float) Math.atan2(getPosition()[1] 
	 * 			|					- World.getCubeCenter(getTargetCube())[1],
	 *			|		targetPosition[0] - World.getCubeCenter(getTargetCube())()[0]))
	 * 
	 * @throws	IllegalTargetException
	 * 			the nit can not have the given target cube as its target.
	 * 			| !canHaveAsTargetCube(targetCube)
	 */
	public void workAt(Coordinate targetCube) throws IllegalTargetException {
		
		if (canWork()) {
			
			try {
				setTargetCube(targetCube);
				
			} catch (IllegalTargetException e) {
				e.printStackTrace();
				throw new IllegalTargetException(getCoordinate(), targetCube);
			}
			
			startWorking();
			
			double[] targetPosition = World.getCubeCenter(getTargetCube());
			setOrientation((float)Math.atan2(getPosition()[1]-targetPosition[1],
					targetPosition[0] - getPosition()[0]));
		}
	}
	
	
	/**
	 * Check if this nit can work.
	 */
	protected abstract boolean canWork();
	

	/**
	 * Makes the nit start working, i.e. sets its state to working.
	 * 
	 * @post	The nits state is equal to working
	 * 			| new.getState() == State.WORKING
	 * 
	 * @post	The nits time to completion is equal to 500 divided by its
	 * 			strength.
	 * 			| new.getTimeToCompletion() == 500.0/getStrength()
	 */
	protected void startWorking() throws IllegalTimeException {
		setTimeToCompletion(500.0f/getStrength());
		setState(State.WORKING);
	}

	
	/**
	 * Manage the working of the nit, i.e. when the state of the nit is equal to working.
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
	 * 			smaller than zero and if the nit is carrying an item, 
	 * 			this item is dropped and 10 experience points are added to the nits XP.
	 * 			| if (isCarryingItem() && getTimeToCompletion() <= 0.0) 
	 * 			|			then dropItem()
	 * 			|				 addXP(10)
	 * 
	 * @effect	if the time before completion of the work order is equal to or
	 * 			smaller than zero, if the nit is not carrying an item and if
	 * 			the cube type of the target cube is workshop and the target cube contains
	 * 			a boulder and a log, the nits equipment is improved and 10 experience 
	 * 			points are added to the nits XP.
	 * 			| if (getTimeToCompletion() <= 0.0 && !isCarryingItem()
	 * 			|		&& getWorld().getCubeTypeAt(getTargetCube())==TerrainType.WORKSHOP
	 * 			|		&& getWorld().containsLog(getWorld().getObjectsAt(getTargetCube()))
	 * 			|		&& getWorld().containsBoulder(getWorld().getObjectsAt(getTargetCube()))
	 * 			| 	then improveEquipment(items)
	 * 			|		 addXP(10)
	 * 
	 * @effect	if the time before completion of the work order is equal to or
	 * 			smaller than zero, if the nit is not carrying an item and the above is
	 * 			not true, and if the target cube contains a boulder, the nit picks
	 * 			up the boulder and 10 experience points are added to the nits XP.
	 * 			| if ( getTimeToCompletion() <= 0.0 && !isCarryingItem()
	 * 			|		&& ! ( getWorld().getCubeTypeAt(getTargetCube())==TerrainType.WORKSHOP
	 * 			|		&& getWorld().containsLog(getWorld().getObjectsAt(getTargetCube()))
	 * 			|		&& getWorld().containsBoulder(getWorld().getObjectsAt(getTargetCube())) )
	 * 			|		&&  getWorld().containsBoulder(getWorld().getObjectsAt(getTargetCube()))   )
	 * 			| 	then pickUpItem(getWorld().getBoulderFrom(getWorld().getObjectsAt(getTargetCube())))
	 * 			|		 addXP(10)
	 * 
	 * @effect	if the time before completion of the work order is equal to or
	 * 			smaller than zero, if the nit is not carrying an item and the above is
	 * 			not true, and if the target cube contains a log, the nit picks
	 * 			up the log and 10 experience points are added to the nits XP.
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
	 * 			smaller than zero, if the nit is not carrying an item and the above is
	 * 			not true, and if the target cube is of type rock or tree, the target cube
	 * 			collapses and 10 experience points are added to the nits XP.
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
	 * 			smaller than zero, if the nit is not carrying an item and the above is
	 * 			not true, and if the target cube is of type water, the target cube
	 * 			collapses and 10 experience points are added to the XP of all nits in this cube.
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
	 * 			smaller than zero, the nits state is set to empty
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
				} else if (getWorld().getCubeTypeAt(getTargetCube()) == TerrainType.WATER) {
					getWorld().collapse(getTargetCube(), 1.00);
					for (Nit nit: getWorld().getNitsAt(getTargetCube())) {
						nit.addXP(10);
					}
				}
			}
			setState(State.EMPTY);
		}
	}
	
	

	/**
	 * Improve the nits equipment, incrementing the nits strength and toughness by 1.
	 * 
	 * @effect	If the cube type of the target cube is workshop and the target cube contains
	 * 			at least one boulder and at least one log, one boulder and one log are
	 * 			consumed,
	 * 			| if (getWorld().getCubeTypeAt(getTargetCube())==TerrainType.WORKSHOP
	 * 			|		&& getWorld().containsLog(getWorld().getObjectsAt(getTargetCube()))
	 * 			|		&& getWorld().containsBoulder(getWorld().getObjectsAt(getTargetCube()))  )
	 * 			| then (for some boulder in getWorld().getObjectsAt(getTargetCube()):
	 * 			|			boulder.terminate()
	 * 			|			 )
	 * 			|	   (for some log in getWorld().getObjectsAt(getTargetCube()):
	 * 			|			log.terminate()
	 * 			|			 )
	 * 			and the weight and toughness of the nit are incremented.
	 * 			|		setWeight(getWeight() + 1)
	 * 			|		setToughness(getToughness() +1)
	 * 
	 */
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
	 * Returns the nits target cube.
	 */
	@Basic @Raw @Model
	protected Coordinate getTargetCube() {
		return this.targetCube;
	}
	
	
	/**
	 * Checks whether this nit can have the given cube as its target cube.
	 * 
	 * @param 	targetCube
	 * 			the cube to verify
	 * 
	 * @return	true if and only if the nits world can have the cube as coordinates and
	 * 			the cube is neighbouring the cube currently occupied by the nit or is 
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
	 * Set the nits target cube.
	 * 
	 * @param 	target
	 * 			the cube to set the nits target cube to.
	 * 
	 * @post	the nit references the given cube as its target cube.
	 * 			| new.getTargetCube() == target
	 * 
	 * @throws	IllegalTargetException
	 * 			the nit can not have the given target as its target cube.
	 * 			| !canHaveAsTargetCube(target)
	 */
	@Raw @Model
	protected void setTargetCube(Coordinate target) throws IllegalTargetException {
		if (!canHaveAsTargetCube(target) )
			throw new IllegalTargetException(getCoordinate(), target);
		
		this.targetCube = target;
	}
	
	
	/**
	 * A variable registering this nits target cube.
	 */
	private Coordinate targetCube = null;
	
	
	/**
	 * Inspect whether the nit is carrying a boulder.
	 * 
	 * @return	true if and only if the nit is carrying an item and if
	 * 			this item is a boulder.
	 * 			| result == (getCarriedItem() instanceof Boulder)
	 */
	public boolean isCarryingBoulder() {
		return (getCarriedItem() instanceof Boulder);
	}
		
	
	/**
	 * Inspect whether the nit is carrying a log.
	 * 
	 * @return	true if and only if the nit is carrying an item and if
	 * 			this item is a log.
	 * 			| result == (getCarriedItem() instanceof Log)
	 */
	public boolean isCarryingLog() {
		return (getCarriedItem() instanceof Log);
	}
	
	
	/**
	 * Inspect whether the nit is carrying an item.
	 * 
	 * @return	true if and only if the nit is carrying an item.
	 * 			| result == (getCarriedItem() != null)
	 */
	public boolean isCarryingItem() {
		return (getCarriedItem() != null);
	}
	
	
	/**
	 * Checks whether the given item is a valid item for this nit to carry.
	 * 
	 * @param 	item
	 * 			the item to check
	 * 
	 * @return	true if and only if the given item is not equal to null and 
	 * 			if the item references the same world as this nit.
	 * 			| result == (item != null && getWorld() == item.getWorld())
	 */
	@Raw
	public boolean canHaveAsItem(Item item) {
		return (item != null && item.getWorld() == this.getWorld());
	}
	
	
	/**
	 * Returns this nits carried item.
	 */
	@Raw @Basic
	public Item getCarriedItem() {
		return this.carriedItem;
	}
		
	
	/**
	 * Drop the item the nit is currently carrying. If the nit is not carrying an item,
	 * this method has no effect.
	 * 
	 * @effect	if the nit is carrying an item, this items world is set to the nits world,
	 * 			| if (isCarryingItem())
	 * 			| 	then getCarriedItem().setWorld(getWorld())
	 * 			the item is then added to the nits world, 
	 * 			|		 getWorld().addItem(getCarriedItem())
	 * 			and its position is set to the nits target cube.
	 * 			|		 getCarriedItem().setPosition(getTargetCube())
	 * 
	 * @effect	If the target cube is not a valid position the items 
	 * 			position is set to the nits position.
	 * 			|		 if (!getCarriedItem().canHaveAsPosition(getTargetCube()))
	 * 			|			then getCarriedItem().setPosition(getCoordinate())
	 * 
	 * @post 	if the nit is carrying an item, the new nits carried item field 
	 * 			is equal to null.
	 * 			| if (this.isCarryingItem())
	 * 			| 		then new.carriedItem == null
	 */
	@Model
	protected void dropItem() {
		if (isCarryingItem()) {
			getCarriedItem().setWorld(getWorld());
			getWorld().addItem(getCarriedItem());
			try {
				getCarriedItem().setPosition(getTargetCube());
			} catch (RuntimeException e) {
				//e.printStackTrace();
				getCarriedItem().setPosition(getCoordinate());
			}
			this.carriedItem = null;
		}
	}
	
	
	/**
	 * Pick up the given item.
	 * 
	 * @effect	the item the nit is carrying is dropped.
	 * 			| dropItem()
	 * 
	 * @post 	the item the nit is carrying is equal to the given item.
	 * 			| new.carriedItem == item
	 * 
	 * @effect	the given item is removed from the nits current world.
	 * 			| getWorld().removeItem(item)
	 * 
	 * @throws	IllegalArgumentException
	 * 			The nit cannot have this item as the item its carrying.
	 * 			| !canHaveAsItem(item)
	 */
	@Model @Raw
	protected void pickUpItem(Item item) throws IllegalArgumentException {
		if (!canHaveAsItem(item))
			throw new IllegalArgumentException();
		
		dropItem();
		this.carriedItem = item;
		getWorld().removeItem(item);
	}
	
	
	/**
	 * Variable registering the item the nit is carrying.
	 */
	private Item carriedItem = null;


	/**
	 * Returns true if the nit is attacking.
	 * 
	 * @return	True if and only if the nit is attacking.
	 * 			| result = (getState() == State.ATTACKING)
	 */
	public boolean isAttacking() {
		return (getState() == State.ATTACKING);
	}
	
	
	
	/**
	 * Attack the given nit.
	 * 
	 * @param 	defender
	 * 			The nit to attack.
	 * 
	 * @effect	If the nit can execute an attack it starts attacking.
	 * 			| if (canExecuteAttack())
	 * 			|  	then startAttacking()
	 * 
	 * @post	If the nit can execute an attack this nits defender field 
	 * 			is set to the given other nit.
	 * 			| 	new.getDefender() == defender
	 * 
	 * @effect	If the nit can execute an attack the nit this nit is attacking
	 * 			its is attacked field is set to true.
	 * 			| 	getDefender().setAttacked(true)
	 * 
	 * @post	If the nit can execute an attack its orientation is set towards
	 * 			the attacked nit.
	 * 			| 	new.getOrientation() == arctangent(defender.getPosition()[1]-this.getPosition()[1],
	 * 			|		defender.getPosition()[0]-this.getPosition()[0]))
	 * 
	 * @post	If the nit can execute an attack the attacked nits orientation is set
	 * 			towards this nit, i.e. the attacking nit.
	 * 			| 	(new defender).getOrientation() == arctangent(this.getPosition()[1]-
	 * 			|		defender.getPosition()[1], this.getPosition()[0]-defender.getPosition()[0]))
	 *  
	 * @throws 	IllegalVictimException
	 * 			The nit cannot attack the given other nit.
	 * 			| !canAttack(defender)
	 */
	public void attack(Nit defender) throws IllegalTimeException, IllegalVictimException {
		if (!canAttack(defender))
			throw new IllegalVictimException(this, defender);

		if (canExecuteAttack()) {
			startAttacking();
						
			setDefender(defender);
			getDefender().setAttacked(true);
			
			// Orientation update
			this.setOrientation((float)Math.atan2(defender.getPosition()[1]-this.getPosition()[1],
					defender.getPosition()[0]-this.getPosition()[0]));

			defender.setOrientation((float)Math.atan2(this.getPosition()[1]-defender.getPosition()[1],
					this.getPosition()[0]-defender.getPosition()[0]));

		}
	}
	
	
	/**
	 * Check whether this nit can execute an attack.
	 */
	protected abstract boolean canExecuteAttack();
	
	
	/**
	 * Returns true if the unit can attack another given nit.
	 * 
	 * @param	victim
	 * 			The nit to check.
	 * 
	 * @return	True if and only if the given nit occupies the same
	 * 			or a neighboring cube of the game world, and if the given
	 * 			nit is not falling if it is a unit, and if it does not belong 
	 * 			to the same faction as this nit, and if it is not already attacking some
	 * 			nit or itself being attacked.
	 * 			| result == (  ( getWorld().isNeighbouring(getCoordinate(), victim.getCoordinate())
	 * 			|		|| getCoordinate().equals(victim.getCoordinate())  )
	 * 			|		&& !victim.isFalling() && getFaction() != victim.getFaction() 
	 * 			|		&& !victim.isAttacked() && !victim.isAttacking() )
	 */
	@Model
	protected boolean canAttack(Nit victim) {
		if (! ( getWorld().isNeighbouring(getCoordinate(), victim.getCoordinate())
				|| getCoordinate().equals(victim.getCoordinate())  )	)
			return false;
		if ( victim.isAttacked() || victim.isAttacking())
			return false;
		if (getFaction() == victim.getFaction())
			return false;
		if (victim instanceof Unit && ((Unit) victim).isFalling())
			return false;
		return true;
	}
	
	
	/**
	 * Return whether there are nits in this nits world that this nit can attack.
	 * 
	 * @return 	true if and only if there is at least one nit in this nits 
	 * 			world that this nit can attack.
	 * 			| result == ( for some victim in getWorld().getAllNits():
	 * 			|				canAttack(victim)   )
	 */
	private boolean enemiesInRange() {
		Iterator<Nit> it = getWorld().getAllNits().iterator();
		
		Nit victim;
		
		while (it.hasNext()) {
			victim = it.next();
			if (canAttack(victim))
				return true;
		}
		return false;
	}
	
	
	/**
	 * Returns a nit this nit can attack. Returns null if there is none.
	 * 
	 * @return	A nit this nit can attack. Null if no victim is found.
	 * 			| for each victim in getWorld().getAllNits():
	 * 			|		if ( canAttack(victim) )
	 * 			|			then result == victim
	 * 			|		else then result == null
	 */
	private Nit getEnemyInRange() {
		Iterator<Nit> it = getWorld().getAllNits().iterator();
		while (it.hasNext()) {
			Nit possibleVictim = it.next();
			if (canAttack(possibleVictim))
				return possibleVictim;
		}
		return null;
	}
	

	/**
	 * Makes the nit start attacking, i.e. sets its state to attacking.
	 * 
	 * @post	The nits state is equal to attacking
	 * 			| new.getState() == State.ATTACKING
	 * 
	 * @post	The nits time to completion is set to 1.0
	 * 			| new.getTimeToCompletion() == 1.0
	 * 
	 */
	@Model
	private void startAttacking() throws IllegalTimeException {
		setTimeToCompletion(1.0f);
		setState(State.ATTACKING);
	}

	
	/**
	 * Manage the attacking of the nit, i.e. when the state of 
	 * 				the nit is equal to attacking.
	 * 
	 * @param	dt
	 * 			the game time interval in which to manage the attacking behavior.
	 * 
	 * @effect	if the time before completion of the attack is larger than zero
	 * 			the time before completion is decremented with the given time interval.
	 * 			| if (getTimeToCompletion() > 0.0)
	 * 			| 	then setTimeToCompletion((float) (getTimeToCompletion() - dt))
	 * 
	 * @effect	if the time before completion of the attack is smaller than or equal to zero
	 * 			the time before completion is set to zero.
	 * 			| if (getTimeToCompletion() <= 0.0)
	 * 			| 	then setTimeToCompletion(0.0f)
	 * 
	 * @effect	if the time before completion of the attack is smaller than or equal to zero
	 * 			the defending nit does its defending behavior,
	 * 			| if (getTimeToCompletion() <= 0.0)
	 * 			| 	then getDefender().defend(this)
	 * 			its isAttacked field is set to false,
	 * 			|		 getDefender().setAttacked(false)
	 * 			and this nits state is set to empty.
	 * 			|		 setState(State.EMPTY)
	 * 
	 */
	private void controlAttacking(double dt) throws IllegalTimeException, ArithmeticException {
		if (getTimeToCompletion() > 0.0) {
			setTimeToCompletion((float) (getTimeToCompletion() - dt));
		}

		if (getTimeToCompletion() <= 0.0) {

			setTimeToCompletion(0.0f);
			getDefender().defend(this);
			getDefender().setAttacked(false);
			setState(State.EMPTY);

		}
	}
	
	
	/**
	 * Defend an attack from an attacking nit. The defending nit will first try
	 * 		to dodge the incoming attack, if that fails it will try to block it. If one of
	 * 		those succeeds it will be rewarded 20 XP, else it will take damage and
	 * 		the attacking nit will be rewarded 20 XP.
	 * 
	 * @param	attacker
	 * 			The attacking nit.
	 * 
	 * @effect	This nit tries to dodge the attack from the attacking nit. If it
	 * 			succeeds, its XP is incremented by 20.
	 * 			| if ( dodge(attacker) )
	 * 			| 	then addXP(20)
	 * 
	 * @effect	If the dodge fails, this nit tries to block the attack from the 
	 * 			attacking nit. If this succeeds, its XP is incremented by 20.
	 * 			| if ( block(attacker) )
	 * 			| 	then addXP(20)
	 * 
	 * @effect	If both the blocking and the dodging fails, this nit takes damage from the 
	 * 			attacking nit. The attacking nits XP is incremented by 20.
	 * 			| if ( ! block(attacker) && ! dodge(attacker) )
	 * 			| 	then takeDamage(attacker)
	 * 			|		 attacker.addXP(20)
	 */
	private void defend(Nit attacker) throws ArithmeticException {
		boolean dodged = dodge(attacker);
		//System.out.println("dodged " + dodged);
		if (!dodged) {
			boolean blocked = this.block(attacker);
			//System.out.println("blocked " + blocked);
			if (!blocked) {
				takeDamage(attacker);
				attacker.addXP(20);
			} else {
				addXP(20);
			}
		} else {
			addXP(20);
		}
	}

	
	/**
	 * Try to dodge an attack from an attacking nit. If it succeeds, this nit
	 * 		will jump to a random adjacent cube.
	 * 
	 * @param	attacker
	 * 			The attacking nit.
	 * 
	 * @effect	The dodging succeeds with a probability of 0.2 times the quotient of the 
	 * 			agility of the defender and the agility of the attacker. If it succeeds,
	 * 			this nit jumps to a random adjacent cube.
	 * 			| if ( random.nextDouble() <= 0.20*(getAgility()/attacker.getAgility()) )
	 * 			|	then jumpToRandomAdjacent()
	 * 
	 * @return	True if and only if the attack is dodged, otherwise false.
	 * 			| result == (random.nextDouble() <= 
	 * 			|			0.2*(this.getAgility()/attacker.getAgility()) )
	 */
	private boolean dodge(Nit attacker) {
		if ( random.nextDouble() <= 0.20*(getAgility()/attacker.getAgility())) {
			while(true) {
				try {
					jumpToRandomAdjacent();
					//System.out.println("tried to jump");
					break;
				}
				catch (IllegalPositionException exc){
					continue;
				}
			}
			//System.out.println("succeed to jump");
			return true;
		}
		return false;
	}

	
	/**
	 * Jump to a random adjacent cube.
	 * 
	 * @post	The nits position is set to a random adjacent cube on the same Z level.
	 * 			| new.getPosition().equals(new Coordinate(getCoordinate().get(0) + random.nextInt(3) - 1,
	 *			|		getCoordinate().get(1) + random.nextInt(3) - 1, 
	 *			|		getCoordinate().get(2)) )
	 * 
	 * @throws 	IllegalPositionException
	 * 			The nit cannot have the calculated random adjacent position
	 * 			as its position.
	 * 			| !(canHaveAsPosition(randomAdjacentPosition))
	 */
	private void jumpToRandomAdjacent() throws IllegalPositionException {
		Coordinate newPosition = new Coordinate(getCoordinate().get(0) + random.nextInt(3) - 1,
				getCoordinate().get(1) + random.nextInt(3) - 1, 
				getCoordinate().get(2));
				
		if (!canHaveAsPosition(newPosition)) 
			throw new IllegalPositionException(newPosition);
		
		setPosition(newPosition);
	}
	
	
	/**
	 * Try to block an attack from an attacking nit.
	 * 
	 * @param	attacker
	 * 			The attacking nit.
	 * 
	 * @return	True if the attack is blocked, otherwise false. The probability 
	 * 			for this to happen is equal to 0.25 times the quotient of the 
	 * 			sum of the agility and the strength of the defender and 
	 * 			the sum of the agility and the strength of the attacker.
	 * 			| result == (random.nextDouble() <= 0.25*((this.getAgility() + this.getStrength())
	 *			|	/(attacker.getAgility() + attacker.getStrength())) )
	 */
	private boolean block(Nit attacker) {
		double probability = 0.25*((this.getAgility() + this.getStrength())
				/(attacker.getAgility() + attacker.getStrength()));
		if ( random.nextDouble() <= probability ) {
			return true;
		}
		return false;
	}


	/**
	 * The nit takes damage, i.e. loses an amount of hitpoints proportional with the
	 * 		attackers strength.
	 * 
	 * @param	attacker
	 * 			The attacking nit.
	 * 
	 * @post	the hitpoints of the nit are reduced by the strength of the
	 * 			attacker divided by 10.
	 * 			| new.getCurrentHitPoints() = this.getCurrentHitPoints()
				|		- (attacker.getStrength()/10)
	 */
	private void takeDamage(Nit attacker) {
		this.updateCurrentHitPoints(this.getCurrentHitPoints() - 
				(attacker.getStrength()/10));
	}
	
	
	/**
	 * Returns whether the nit is currently being attacked.
	 */
	@Basic
	public boolean isAttacked() {
		return this.isAttacked;
	}
	
	
	/**
	 * Set the nits attacked field to a given boolean value.
	 * 
	 * @param 	isAttacked
	 * 			the value to set the attacked field to.
	 * 
	 * @post 	The attacked field of this nit is equal to the
	 * 			given value.
	 * 			| new.isAttacked() == isAttacked
	 * 
	 * @post 	The state of the nit is set to empty.
	 * 			| new.getState() == State.EMPTY
	 */
	protected void setAttacked(boolean isAttacked) {
		this.setState(State.EMPTY);
		this.isAttacked = isAttacked;
	}
	
	
	/**
	 * Field registering whether the nit is being attacked.
	 */
	private boolean isAttacked = false;

	
	/**
	 * Returns the nit that this nit is attacking.
	 */
	@Basic @Raw
	public Nit getDefender() {
		return this.defender;
	}
	
	
	/**
	 * Set the nit this nit is attacking to the given victim nit.
	 * 
	 * @param 	defender2
	 * 			The nit to set the nit this nit is attacking to.
	 * 
	 * @post	The nit this nit is attacking is equal to the given victim nit.
	 * 			| new.getDefender() == victim
	 * 
	 * @throws 	IllegalVictimException
	 * 			This nit cannot attack the given victim nit.
	 * 			| !canAttack(victim)
	 */
	@Raw
	protected void setDefender(Nit defender2) throws IllegalVictimException {
		if (!canAttack(defender2))
			throw new IllegalVictimException(this, defender2);
		this.defender = defender2;
	}

	
	/**
	 * Field registering the nit that this nit is attacking.
	 */
	private Nit defender = null;

	
	/**
	 * Returns the time left for a nit to complete its work or attack.
	 */
	@Basic
	public float getTimeToCompletion() {
		return this.timeToCompletion;
	}

		
	/**
	 * Checks if a given value is a valid time to completion value.
	 * 
	 * @param	value
	 * 			Value to check
	 * 
	 * @return	true if and only if the given value	is larger than -0.5.
	 * 			| result == (value > -2.0)
	 */
	public static boolean isValidTime(float value) {
		return ( (value > -2.0f) );
	}
	
	
	/**
	 * Checks if a given value is a valid time value.
	 * 
	 * @param	value
	 * 			Value to check
	 * 
	 * @return	true if and only if the given value is larger than or equal to 0.0.
	 * 			| result == (value >= 0.0)
	 */
	public static boolean isValidTime(double value) {
		return ( (value >= 0.0) );
	}

	
	/**
	 * Sets the time to completion for an attack or work job to a given value.
	 * 
	 * @param 	newValue
	 * 			the given value for the new time to completion.
	 * 
	 * @post	The time to completion field is set to the new value.
	 * 			| new.getTimeToCompletion() == newValue
	 * 
	 * @throws 	IllegalTimeException
	 * 			The time value is not valid.
	 * 			| !(isValidTime(newValue))
	 */
	protected void setTimeToCompletion(float newValue) throws IllegalTimeException {
		if (!isValidTime(newValue))
			throw new IllegalTimeException(newValue, this);
		this.timeToCompletion = newValue;
	}

	
	/**
	 * Field registering the time to completion.
	 */
	private float timeToCompletion = 0.0f;
	
	

	
	/**
	 * Returns the time the nit has been resting.
	 */
	@Basic 
	public double getTimeResting() {
		return this.timeResting;
	}
	
	
	/**
	 * Returns the time that has passed after the last time the nit rested.
	 */
	@Basic 
	public double getTimeAfterResting() {
		return this.timeAfterResting;
	}
	
		
	/**
	 * Returns true if the nit is resting.
	 * 
	 * @return	True if and only if the nit is resting.
	 * 			| result = (getState() == State.RESTING_HP || getState() == State.RESTING_STAM
	 * 			|		|| getState() == State.RESTING_1)
	 */
	public boolean isResting() {
		return (getState() == State.RESTING_HP || getState() == State.RESTING_STAM
				|| getState() == State.RESTING_1);
	}
	
	
	/**
	 * Make the nit rest, if it can rest.
	 * 
	 * @effect	If the nit can rest, it starts
	 * 			resting.
	 * 			| if ( canRest() )
	 * 			| 	then startResting()
	 */
	public void rest() throws IllegalTimeException {
		if (canRest()) {
			startResting();
		}
	}
	
	
	/**
	 * Check whether the nit can currently rest.
	 */
	protected abstract boolean canRest();
	
	
	/**
	 * Makes the nit start resting, i.e. sets its state to resting.
	 * 
	 * @post	The nits state is equal to the initial resting
	 * 			| new.getState() == State.RESTING_1
	 * 
	 * @post	The time the nit has been resting is set to zero.
	 * 			| new.getTimeResting() == 0.0
	 */
	private void startResting() throws IllegalTimeException {
		setTimeResting(0.0);
		setTimeAfterResting(0.0);
		setState(State.RESTING_1);
	}
	
	
	
	/**
	 * Manage the resting of the nit, i.e. when the state of 
	 * 				the nit is equal to resting.
	 * 
	 * @param	dt
	 * 			the game time interval in which to manage the falling behavior.
	 * 
	 * @effect	if the nits state is equal to the initial resting state, and if the
	 * 			quotient of the product of the time the nit has been resting and its toughness,
	 * 			and 40 is larger than 1, its current HP are incremented by this quotient
	 * 			and the time it has been resting is decremented by 40 divided by its toughness.
	 * 			| if ( (getState() == State.RESTING_1) 
	 * 			|			&& ((getTimeResting() * getToughness())/(0.2*200) > 1.0)  )
	 * 			| 	then updateCurrentHitPoints(getCurrentHitPoints() + 
	 *			|			(int) Math.round((getTimeResting()*getToughness())/(0.2*200)) )
	 *			|		 setTimeResting(getTimeResting() - (0.2*200*1.0)/getToughness());
	 * 	
	 * @effect	if the above conditions hold and the nits current HP are below its maximum HP,
	 * 			the nits state is set to resting HP.
	 * 			| if (   (getState() == State.RESTING_1) 
	 * 			|			&& ((getTimeResting() * getToughness())/(0.2*200) > 1.0)
	 * 			|		&&  (getCurrentHitPoints() < getMaxHitPoints())   )
	 * 			|	then setState(State.RESTING_HP)
	 * 
	 * @effect	if the first condition holds and the nits current HP are not below its maximum HP,
	 * 			and if its current stamina points are below its maximum stamina points,
	 * 			the nits state is set to resting stam.
	 * 			| if (   (getState() == State.RESTING_1) 
	 * 			|			&& ((getTimeResting() * getToughness())/(0.2*200) > 1.0)
	 * 			|		&& !( getCurrentHitPoints() < getMaxHitPoints())
	 * 			|		&&  (getCurrentStaminaPoints() < getMaxStaminaPoints())   )
	 * 			|	then setState(State.RESTING_STAM)
	 * 
	 * @effect	if the first condition holds and the nits current HP are not below its maximum HP,
	 * 			and if its current stamina points are not below its maximum stamina points,
	 * 			the nits state is set to empty.
	 * 			| if (   (getState() == State.RESTING_1) 
	 * 			|			&& ((getTimeResting() * getToughness())/(0.2*200) > 1.0)
	 * 			|		&& !( getCurrentHitPoints() < getMaxHitPoints())
	 * 			|		&& !(getCurrentStaminaPoints() < getMaxStaminaPoints())   )
	 * 			|	then setState(State.EMPTY)
	 * 
	 * @effect	2. if the nits state is equal to the resting HP, and if the
	 * 			quotient of the product of the time the nit has been resting and its toughness,
	 * 			and 40 is larger than 1, its current HP are incremented by this quotient
	 * 			and the time it has been resting is decremented by 40 divided by its toughness.
	 * 			| if ( (getState() == State.RESTING_HP) 
	 * 			|			&& ((getTimeResting() * getToughness())/(0.2*200) > 1.0)
	 * 			| 	then updateCurrentHitPoints(getCurrentHitPoints() + 
	 *			|			(int) Math.round((getTimeResting()*getToughness())/(0.2*200)) )
	 *			|		 setTimeResting(getTimeResting() - (0.2*200*1.0)/getToughness())
	 *
	 * @effect	if the above condition holds and the nits current HP are not below its maximum HP,
	 * 			and if its current stamina points are below its maximum stamina points,
	 * 			the nits state is set to resting stam.
	 * 			| if (   (getState() == State.RESTING_HP) 
	 * 			|			&& ((getTimeResting() * getToughness())/(0.2*200) > 1.0)
	 * 			|		&& !( getCurrentHitPoints() < getMaxHitPoints())
	 * 			|		&&  (getCurrentStaminaPoints() < getMaxStaminaPoints())   )
	 * 			|	then setState(State.RESTING_STAM)
	 * 
	 * @effect	if 2. holds and the nits current HP are not below its maximum HP,
	 * 			and if its current stamina points are not below its maximum stamina points,
	 * 			the nits state is set to empty.
	 * 			| if (   (getState() == State.RESTING_HP) 
	 * 			|			&& ((getTimeResting() * getToughness())/(0.2*200) > 1.0)
	 * 			|		&& !( getCurrentHitPoints() < getMaxHitPoints())
	 * 			|		&& !(getCurrentStaminaPoints() < getMaxStaminaPoints())   )
	 * 			|	then setState(State.EMPTY)
	 * 
	 * @effect	3. if the nits state is equal to the resting stam, and if the
	 * 			quotient of the product of the time the nit has been resting and its toughness,
	 * 			and 40 is larger than 1, its current HP are incremented by this quotient
	 * 			and the time it has been resting is decremented by 40 divided by its toughness.
	 * 			| if ( (getState() == State.RESTING_STAM) 
	 * 			|			&& ((getTimeResting() * getToughness())/(0.2*200) > 1.0)
	 * 			| 	then updateCurrentStaminaPoints(getCurrentStaminaPoints() + 
	 *			|			(int) Math.round((getTimeResting()*getToughness())/(0.2*100)) )
	 *			|		 setTimeResting(getTimeResting() - (0.2*200*1.0)/getToughness())
	 *
	 * @effect	if 3. holds and the nits current stamina points are not below its 
	 * 			maximum stamina points, the nits state is set to empty.
	 * 			| if (   (getState() == State.RESTING_STAM) 
	 * 			|			&& ((getTimeResting() * getToughness())/(0.2*200) > 1.0)
	 * 			|		&& !(getCurrentStaminaPoints() < getMaxStaminaPoints())   )
	 * 			|	then setState(State.EMPTY)
	 */
	private void controlResting(double dt) throws IllegalTimeException {
		setTimeResting(getTimeResting() + dt);

		if (getState() == State.RESTING_1) {
			if ((getTimeResting() * getToughness())/(0.2*200) > 1.0) {

				setTimeResting(getTimeResting() - (0.2*200*1.0)/getToughness());

				if ( getCurrentHitPoints() < getMaxHitPoints()) {
					updateCurrentHitPoints(getCurrentHitPoints() + 
							(int) Math.round((getTimeResting()*getToughness())/(0.2*200)) );

					setState(State.RESTING_HP);
				}
				else if (getCurrentStaminaPoints() < getMaxStaminaPoints()) {
					setState(State.RESTING_STAM);
				}
				else { setState(State.EMPTY); }
			}
		}

		else if (getState() == State.RESTING_HP) {
			if ((getTimeResting() * getToughness())/(0.2*200) > 1.0) {
				updateCurrentHitPoints(getCurrentHitPoints() + 
						(int) Math.round((getTimeResting()*getToughness())/(0.2*200)) );
				setTimeResting(getTimeResting() - (0.2*200*1.0)/getToughness());
			}
			if (getCurrentHitPoints() == getMaxHitPoints()
					&& getCurrentStaminaPoints() < getMaxStaminaPoints()) {
				setState(State.RESTING_STAM);
			}
			else if (getCurrentHitPoints() == getMaxHitPoints()
					&& getCurrentStaminaPoints() == getMaxStaminaPoints()) {
				setState(State.EMPTY);
			}
		}
		else if (getState() == State.RESTING_STAM) {
			if ((getTimeResting() * getToughness())/(0.2*100) > 1.0) {
				updateCurrentStaminaPoints(getCurrentStaminaPoints() + 
						(int) Math.round((getTimeResting()*getToughness())/(0.2*100)) );
				setTimeResting(getTimeResting() - (0.2*100*1.0)/getToughness());
			}
			if (getCurrentStaminaPoints() == getMaxStaminaPoints()) {
				setState(State.EMPTY);
			}
		}
		if (getCurrentHitPoints() == getMaxHitPoints() &&
				getCurrentStaminaPoints() == getMaxStaminaPoints()) {
			setState(State.EMPTY);
		}

	}
	
	

	/**
	 * Set the time the nit has been resting to a given value.
	 * 
	 * @param 	newValue
	 * 			The new value for the time resting.
	 * 
	 * @post	The time the nit has been resting is equal to the given value.
	 * 			| new.getTimeResting() == newValue
	 * 
	 * @throws 	IllegalTimeException
	 * 			The new value is not a valid time value.
	 * 			| !isValidTime(newValue)
	 */
	protected void setTimeResting(double newValue) throws IllegalTimeException {
		if (!isValidTime(newValue))
			throw new IllegalTimeException(newValue, this);
		this.timeResting = newValue;
	}
	
	
	/**
	 * Set the time after the nit has last rested to a given value.
	 * 
	 * @param 	newValue
	 * 			The new value for the time not rested.
	 * 
	 * @post	The time the nit has not been resting is equal to the given value.
	 * 			| new.getTimeAfterResting() == newValue
	 * 
	 * @throws IllegalTimeException
	 * 			The new value is not a valid time value.
	 * 			| !isValidTime(newValue)
	 */
	protected void setTimeAfterResting(double newValue) throws IllegalTimeException {
		if (!isValidTime(newValue))
			throw new IllegalTimeException(newValue, this);
		this.timeAfterResting = newValue;
	}
	
	
	/**
	 * Field registering how long the nit has been resting.
	 */
	private double timeResting = 0.0;
	
	
	/**
	 * Field registering how much time passed after the nit last rested.
	 */
	private double timeAfterResting = 0.0;

	
	/**
	 * Returns whether the default behavior of the nit is enabled.
	 */
	@Basic @Raw
	public boolean isDefaultBehaviorEnabled() {
		return this.defaultBehavior;
	}
	
	
	/**
	 * Set the default behavior of the nit.
	 * 
	 * @param 	value
	 * 			The new value for default behavior.
	 * 
	 * @post	The nits default behavior is equal to the given
	 * 			value.
	 * 			| new.isDefaultBehaviorEnabled() == value
	 */
	@Raw
	public void setDefaultBehavior(boolean value) {
		this.defaultBehavior = value;
	}

	
	/**
	 * Variable registering whether the default behavior of the nit is enabled.
	 */
	private boolean defaultBehavior = true;
	
	
	/**
	 * Returns the current state of the nit.
	 */
	@Basic @Raw
	public State getState() {
		return this.state;
	}
	
	
	/**
	 * Checks if the given state is a valid state for this nit.
	 * 
	 * @param 	State
	 * 			The state to check.
	 * 
	 * @return	False if the value class of states does not
	 * 			contain the given state.
	 * 			| if (!State.contains(state))
	 * 			| 	then result == false
	 */
	public boolean canHaveAsState(State state) {
		return State.contains(state);
	}
	
	
	/**
	 * Sets the current state of the nit.
	 * 
	 * @param	state
	 * 			The state to set the state of the nit to.
	 * 
	 * @post	The state of the nit is set to the given state.
	 * 			| new.getState() == state
	 * 
	 * @throws	IllegalArgumentException
	 * 			the given state value is not a valid state for the nit.
	 * 			| !canHaveAsState(state)
	 */
	@Raw
	public void setState(State state) {
		if (!canHaveAsState(state) )
			throw new IllegalArgumentException();
		this.state = state;
	}
	

	/**
	 * Variable registering the state of the nit.
	 */
	private State state;
	
	
	/**
	 * Get the nits current number of experience points.
	 */
	@Basic @Raw
	public int getExperiencePoints() {
		return this.xp;
	}
	
	
	/**
	 * Checks if the given value is a valid number of XP for a nit.
	 * 
	 * @param 	value
	 * 			the value to check.
	 * 
	 * @return	true if and only if the given value is larger than or equal to
	 * 			zero and smaller than the largest allowed value for an integer.
	 * 			| result == (value >= 0 && value < Integer.MAX_VALUE)
	 */
	public static boolean isValidXP(int value) {
		return (value >= 0 && value < Integer.MAX_VALUE);
	}
	
	
	/**
	 * Checks if the given value can be added to a nits XP.
	 * 
	 * @param 	value
	 * 			the value to check.
	 * 
	 * @return	true if and only if the maximum value for an integer minus 
	 * 			the given value is larger than the nits current XP, and if the value
	 * 			is positive or zero, and if the current XP points plus the value
	 * 			is a valid nb of XP points.
	 * 			| result == (value >= 0 && Integer.MAX_VALUE - value > getExperiencePoints() &&
	 *			|	isValidXP(value + getExperiencePoints()) )
	 */
	public boolean canAddToXP(int value) {
		return (value >= 0 && Integer.MAX_VALUE - value > getExperiencePoints() &&
					isValidXP(value + getExperiencePoints()) );
	}
	
	
	/**
	 * Return the temporary XP of the nit.
	 */
	@Raw
	protected int getTXP() {
		return this.tempXp;
	}
	
	
	/**
	 * Control the XP of the nit, i.e. check if the nit has enough temporary XP to
	 * increment one of its skills, and if so, increment one of its skills.
	 * 
	 * @effect	If the nits temporary XP is larger than 10, it is subtracted by 10.
	 * 			| if (getTXP() > 10)
	 * 			|	then subTXP(10)
	 * 
	 * @effect	If the nits temporary XP is larger than 10 and if its strength can
	 * 			be incremented by 1 AND if a random double is smaller than 1/3, OR if
	 * 			its agility can not be incremented by 1 and if a random double is larger than 1/3 
	 * 			and smaller than 2.0/3.0 and its agility cannot be incremented by 1, OR if
	 * 			its toughness can not be incremented by 1 and if a random double is larger than 2/3: 
	 * 			the nits strength will be incremented by 1.
	 * 			| if ( (getTXP() > 10 && isValidStrength(getStrength() + 1) ) 
	 * 			|		&& (  	(random.nextDouble() < 1.0/3.0)
	 * 			|				|| (random.nextDouble() > 1.0/3.0 && random.nextDouble() < 2.0/3.0
	 * 			|					&& !isValidAgility(getAgility() + 1)) 
	 * 			|				|| (random.nextDouble() > 2.0/3.0 
	 * 			|					&& !isValidToughness(getToughness() + 1) ) 
	 * 			|			)  
	 * 			|		)
	 * 			|	then setStrength(getStrength() + 1)
	 * 
	 * @effect	If the nits temporary XP is larger than 10 and if its agility can
	 * 			be incremented by 1 AND if a random double is larger than 1/3 and smaller than
	 * 			2/3, OR if its strength can not be incremented by 1 and if a random double is 
	 * 			smaller than 1/3, OR if its toughness and strength cannot be incremented by 1 and
	 * 			a random double is larger than 2/3: 
	 * 			the nits agility will be incremented by 1.
	 * 			| if ( (getTXP() > 10 && isValidAgility(getAgility() + 1) ) 
	 * 			|		&& (  	(random.nextDouble() > 1.0/3.0 && random.nextDouble() < 2.0/3.0)
	 * 			|				|| (random.nextDouble() < 1.0/3.0
	 * 			|					&& !isValidStrength(getStrength() + 1)) 
	 * 			|				|| (random.nextDouble() > 2.0/3.0 
	 * 			|					&& !isValidToughness(getToughness() + 1)
	 * 			|					&& !isValidStrength(getStrength() + 1) ) 
	 * 			|			)  
	 * 			|		)
	 * 			|	then setAgility(getAgility() + 1)
	 * 
	 * @effect	If the nits temporary XP is larger than 10 and if its toughness can
	 * 			be incremented by 1 AND if a random double is larger than 2/3, OR if 
	 * 			its strength and its agility can not be incremented by 1 and if a random double is 
	 * 			smaller than 1/3, OR if its agility and strength cannot be incremented by 1 and
	 * 			a random double is larger than 1/3 and smaller than 2/3: 
	 * 			the nits toughness will be incremented by 1.
	 * 			| if ( (getTXP() > 10 && isValidToughness(getToughness() + 1) ) 
	 * 			|		&& (  	(random.nextDouble() > 2.0/3.0 )
	 * 			|				|| (random.nextDouble() < 1.0/3.0
	 * 			|					&& !isValidStrength(getStrength() + 1)
	 * 			|					&& !isValidAgility(getAgility() + 1)) 
	 * 			|				|| (random.nextDouble() > 1.0/3.0  && random.nextDouble() < 2.0/3.0 
	 * 			|					&& !isValidAgility(getAgility() + 1)
	 * 			|					&& !isValidStrength(getStrength() + 1) ) 
	 * 			|			)  
	 * 			|		)
	 * 			|	then setToughness(getToughness() + 1)
	 * 
	 * @effect	If the nits temporary XP is larger than 10, its weight is updated.
	 * 			| if (getTXP() > 10)
	 * 			|	then updateWeight()
	 * 
	 */
	private void controlXP() throws ArithmeticException {
		while (getTXP() > 10) {
			subTXP(10);
			double dice = random.nextDouble();
			
			if (dice < 1.0/3.0 ) {
				if (isValidStrength(getStrength() + 1)) {
					setStrength(getStrength() + 1);
				} else if (isValidAgility(getAgility() + 1)) {
					setAgility(getAgility() + 1);
				} else if (isValidToughness(getToughness() + 1)) {
					setToughness(getToughness() + 1);
				}
			}
			else if (dice > 1.0/3.0 && dice < 2.0/3.0) {
				if ( isValidAgility(getAgility() + 1)) {
					setAgility(getAgility() + 1);
				} else if (isValidStrength(getStrength() + 1)) {
					setStrength(getStrength() + 1);
				} else if (isValidToughness(getToughness() + 1)) {
					setToughness(getToughness() + 1);
				}
			}
			else {
				if (isValidToughness(getToughness() + 1)) {
					setToughness(getToughness() + 1);
				} else if (isValidStrength(getStrength() + 1)) {
					setStrength(getStrength() + 1);
				} else if (isValidAgility(getAgility() + 1)) {
					setAgility(getAgility() + 1);
				}
			}
			updateWeight();
		}
	}
	
	
	/**
	 * Add a number of XP to this nits XP.
	 * 
	 * @param 	x
	 * 			The number of XP to add.
	 * 
	 * @post	The nits XP is incremented by x.
	 * 			| new.getExperiencePoints() = this.getExperiencePoints() + x
	 * 
	 * @post	The nits temporary XP is incremented by x.
	 * 			| new.getTXP() == this.getTXP() + x
	 * 
	 * @throws	ArithmeticException
	 * 			The given x cannot be added to the nits XP.
	 * 			| !canAddToXP(x)
	 */
	protected void addXP(int x) throws ArithmeticException {
		if (!canAddToXP(x)) 
			throw new ArithmeticException();
		this.xp += x;
		this.tempXp += x;
	}
	
	
	/**
	 * Subtract a number of XP from the nits temporary XP.
	 * 
	 * @param 	x
	 * 			The number of XP to subtract.
	 * 
	 * @post	The nits temporary XP is x less than before.
	 * 			| new.getTXP() == this.getTXP - x
	 * 
	 * @throws	ArithmeticException
	 * 			The given number cannot be subtracted.
	 * 			| x < 0 || !isValidXP(getTXP() - x)
	 */
	protected void subTXP(int x) throws ArithmeticException {
		if (x < 0 || !isValidXP(getTXP() - x) ) {
			throw new ArithmeticException();
		}
		this.tempXp -= x;
	}
	
	
	/**
	 * Variable registering the total number of experience points the nit has gained.
	 */
	private int xp = 0;
	
	
	/**
	 * Variable registering the temporary number of experience points of the nit, for
	 * computing reasons.
	 */
	private int tempXp = 0;
	
	

	
	/* *********************************************************
	 * 
	 * 						UNIT - FACTION
	 *
	 **********************************************************/
	
	
	/**
	 * Return the faction to which this nit belongs. Returns a null reference
	 * if this nit does not belong to any faction.
	 */
	@Basic @Raw
	public Faction getFaction() {
		return this.faction;
	}
	
	
	/**
	 * Check whether this nit can join a given faction.
	 * 
	 * @param	faction
	 * 			The faction to check.
	 * 
	 * @return	True if and only if the given faction is not effective or if it
	 * 			can be joined by this nit.
	 * 			| result == ( (faction == null)
	 * 			| 				|| faction.canHaveAsNit(this) )
	 */
	@Raw
	public boolean canHaveAsFaction(Faction faction) {
		return ( (faction == null) || faction.canHaveAsNit(this) );
	}
	
	
	/**
	 * Check whether this nit has a proper faction to which it belongs.
	 * 
	 * @return	True if and only if this nit can have its faction as the faction to
	 * 			which it belongs and if that faction is either not effective or contains
	 * 			this nit.
	 * 			| result == ( canHaveAsFaction(getFaction()) && ( (getFaction() == null)
	 * 			|				|| getFaction.hasAsNit(this) ) )
	 */
	@Raw
	public boolean hasProperFaction() {
		return (canHaveAsFaction(getFaction()) && ( (getFaction() == null) 
					|| getFaction().hasAsNit(this) ) );
	}
	
	
	/**
	 * Set the faction this nit belongs to to the given faction.
	 * 
	 * @param	faction
	 * 			The faction to add the nit to.
	 * 
	 * @post	This nit references the given world as the world
	 * 			it belongs to.
	 * 			| new.getFaction() == faction
	 * 
	 * @throws	IllegalArgumentException
	 * 			If the given faction is not effective and this nit references an
	 * 			effective faction, that faction may not contain this nit.
	 * 			| (faction == null) && (getFaction() != null) 
	 * 			|					&& (getFaction().hasAsNit(this))
	 * 
	 * @throws	IllegalArgumentException
	 * 			If the given faction is effective it must reference this unit as one of
	 * 			its units.
	 * 			| (faction == null) && (getFaction() != null) 
	 * 			|					&& (getFaction().hasAsNit(this))
	 */
	@Raw
	public void setFaction(Faction faction) throws IllegalArgumentException {
		
		if ( (faction == null) && (getFaction() != null) && (getFaction().hasAsNit(this)) )
			throw new IllegalArgumentException();
		if (!faction.hasAsNit(this) && faction != null)
			throw new IllegalArgumentException();
		this.faction = faction;
	}
	
	
	/**
	 * Variable registering the faction to which this nit belongs.
	 */
	private Faction faction = null;
	
	
	
	/**
	 * Method to terminate this nit.
	 * 
	 * @post	The new nit is terminated.
	 * 			| new.isTerminated == true;
	 * 
	 * @effect	The item the nit is carrying is dropped.
	 * 			| dropItem()
	 * 
	 * @effect 	The nit is removed from the faction it belongs to.
	 * 			| getFaction().removeNit(this)
	 * 
	 * @effect 	The nit is removed from the world it belongs to.
	 * 			| getWorld().removeNit(this)
	 */
	@Override
	public void terminate() {
		dropItem();
		getFaction().removeNit(this);
		super.terminate();
	}
	
	
}
