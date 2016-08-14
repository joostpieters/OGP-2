package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.*;

/**
 * A class of factions, attached to worlds. A faction contains up to 50 units.
 * 
 * @invar	Each faction must have a proper world to which it is
 * 			attached.
 * 			| hasProperWorld()
 * 
 * @invar	The units associated with each faction must be proper units for
 * 			that faction.
 * 			| hasProperUnits()
 * 
 * @author rubencartuyvels
 *
 */
public class Faction /*implements Comparable<Faction>*/ {
	
	
	/**
	 * Initialize this new faction with no units attached to it. The faction is not
	 * yet attached to a world.
	 * 
	 * @post	No units are attached to this new faction.
	 * 			| new.getNbUnits() == 0
	 */
	@Raw
	public Faction () {
		//this.units.add(unit);
	}
	
	private static int getMaxNbUnits() {
		return maxUnits;
	}
	
	private final static int maxUnits = 50;
	
	/**
	 * Variable registering whether or not this faction is terminated.
	 */
	private boolean isTerminated = false;
	
	/**
	 * Check whether this faction is terminated.
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Terminate this faction.
	 * 
	 * @post	This faction is terminated.
	 * 			| new.isTerminated()
	 * 
	 * @effect	Each non-terminated unit in this world is removed from
	 * 			this world.
	 * 			| for each unit in getAllUnits():
	 * 			|		if (!unit.isTerminated())
	 * 			|			then this.removeUnit(unit)
	 * 
	 */
	public void terminate() {
		for (Unit unit: this.units) {
			if (!unit.isTerminated()) {
				unit.setFaction(null);
				this.units.remove(unit);
			}
		}
		
		getWorld().removeFaction(this);
		
		this.isTerminated = true;
		
	}
	
	
	/* *********************************************************
	 * 
	 * 						FACTION - UNIT
	 *
	 **********************************************************/
	
	/**
	 * Set collecting references to units that this faction contains.
	 * 
	 * @invar	the set of units is effective.
	 * 			| units != null
	 * 
	 * @invar	Each element in the set references a unit that this faction
	 * 			can have as unit.
	 * 			| for each unit in units:
	 * 			| 		canHaveAsUnit(unit)
	 * 
	 * @invar	Each unit in the set references this faction as their faction.
	 * 			| for each unit in units:
	 * 			|		unit.getFaction() == this
	 * 
	 * @invar	The number of units in the set is always smaller than or
	 * 			equal to 50.
	 * 			| units.size() <= 50
	 */
	private final Set<Unit> units = new HashSet<Unit>();
	
	
	
	/**
	 * Check whether the given unit is a member of this faction.
	 * 
	 * @param 	unit
	 * 			The unit to check.
	 */
	@Basic @Raw
	public boolean hasAsUnit(Unit unit) {
		return this.units.contains(unit);
	}
	
	/**
	 * Check whether this faction can contain the given unit.
	 * 
	 * @param 	unit
	 * 			The unit to check.
	 * 
	 * @return	False if the given unit is not effective.
	 * 			| if (unit == null)
	 * 			| 	then result = false
	 * 			True if this faction is not terminated or the given unit is
	 * 			also terminated
	 * 			| else result == ( (!this.isTerminated())
	 * 			|		|| unit.isTerminated())
	 */
	@Raw
	public boolean canHaveAsUnit(Unit unit) {
		return ( (unit != null) && 
						( !this.isTerminated() || unit.isTerminated()) );
	}
	
	/**
	 * Check whether this faction contains proper units.
	 * 
	 * @return	True if and only if this faction can contain each of its units
	 * 			and if each of these units references this faction as their faction.
	 * 			| result == ( for each unit in Unit:
	 * 			|		if (this.hasAsUnit(unit) )
	 * 			|			then ( canHaveAsUnit(unit) &&
	 * 			|				(unit.getFaction() == this) ) )
	 */
	@Raw
	public boolean hasProperUnits() {
		for (Unit unit: this.units) {
			if (!canHaveAsUnit(unit)) 
				return false;
			if (unit.getFaction() != this)
				return false;
		}
		return true;
	}
	
	/**
	 * Return the number of units this faction contains.
	 * 
	 * @return	The number of units this faction contains.
	 * 			| count( {unit in Unit | hasAsUnit(unit)} )
	 */
	@Raw
	public int getNbUnits() {
		return this.units.size();
	}
	
	/**
	 * Return a set collecting all units this faction contains.
	 * 
	 * @return	The resulting set is effective.
	 * 			| result != null
	 * @return	Each unit in the resulting set is attached to this faction
	 * 			and vice versa.
	 * 			| for each unit in Unit:
	 * 			| 	result.contains(unit) == this.hasAsUnit(unit)
	 */
	public Set<Unit> getAllUnits() {
		return new HashSet<Unit>(this.units);
	}
	
	/**
	 * Add the given unit to the set of units that this faction contains.
	 * 
	 * @param	unit
	 * 			The unit to be added.
	 * 
	 * @post	This faction has the given unit as one of its units.
	 * 			| new.hasAsUnit(unit)
	 * 
	 * @post	The given unit references this faction as its faction.
	 * 			| (new unit).getFaction() == this
	 * 
	 * @throws	IllegalArgumentException
	 * 			This faction cannot have the given unit as one of its units.
	 * 			| !canHaveAsUnit(unit)
	 * 
	 * @throws	IllegalArgumentException
	 * 			The given unit already references some faction.
	 * 
	 * @throws	IllegalArgumentException
	 * 			The maximum number of units in this faction has already been reached.
	 */
	public void addUnit(Unit unit)  throws IllegalArgumentException, IllegalNbException {
		if (!canHaveAsUnit(unit))
			throw new IllegalArgumentException();
		if (unit.getFaction() != this)
			throw new IllegalArgumentException();
		if (getNbUnits() >= getMaxNbUnits())
			try {
				throw new IllegalNbException();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
				/* For some reason I don't know the program crashes after throwing
				* The IllegalNbException. It keeps throwing ArrayIndexOutOfBoundExceptions
				* caused by a method that is part of one of the helper classes. So
				* I shut the program down myself in a clean way.
				*/
			}
		
		this.units.add(unit);
		//unit.setFaction(this);
	}
	
	
	/**
	 * Remove the given unit from this faction.
	 * 
	 * @param	unit
	 * 			The unit to remove.
	 * 
	 * @post	This faction does not contain the given unit.
	 * 			| !new.hasAsUnit(unit)
	 * 
	 * @post	If this faction contains the given unit, the unit is no
	 * 			longer attached to any faction.
	 * 			| if (hasAsUnit(unit))
	 * 			| 	then ( (new unit).getFaction() == null)
	 */
	public void removeUnit(Unit unit) {
		if (hasAsUnit(unit)) {
			this.units.remove(unit);
			unit.setFaction(null);
		}
	}
	
	
	
	
	/* *********************************************************
	 * 
	 * 						FACTION - WORLD
	 *
	 **********************************************************/

	
	/**
	 * Variable registering the world to which this faction belongs.
	 */
	private World world = null;
	
	
	/**
	 * Return the world to which this faction belongs. Returns a null refererence
	 * if this faction does not belong to any world.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Check whether this faction can be attached to a given world.
	 * 
	 * @param	world
	 * 			The world to check.
	 * 
	 * @return	True if and only if the given world is not effective or if it
	 * 			can contain this faction.
	 * 			| result == ( (world == null)
	 * 			| 				|| world.canHaveAsFaction(this) )
	 */
	@Raw
	public boolean canHaveAsWorld(World world) {
		return ( (world == null) || world.canHaveAsFaction(this) );
	}
	
	/**
	 * Check whether this faction has a proper world in which it belongs.
	 * 
	 * @return	True if and only if this faction can have its world as the world to
	 * 			which it belongs and if that world is either not effective or contains
	 * 			this faction.
	 * 			| result == ( canHaveAsWorld(getWorld()) && ( (getWorld() == null)
	 * 			|				|| getWorld.hasAsFaction(this) ) )
	 */
	@Raw
	public boolean hasProperWorld() {
		return (canHaveAsWorld(getWorld()) && ( (getWorld() == null) 
					|| getWorld().hasAsFaction(this) ) );
	}
	
	
	/**
	 * Set the world this faction belongs to to the given world.
	 * 
	 * @param	world
	 * 			The world to add the faction to.
	 * 
	 * @post	This faction references the given world as the world
	 * 			it belongs to.
	 * 			| new.getWorld() == world
	 * 
	 * @throws	IllegalArgumentException
	 * 			If the given world is effective it must already reference this faction
	 * 			as one of its factions.
	 * 			| (world != null) && !world.hasAsFaction(this)
	 * 
	 * @throws	IllegalArgumentException
	 * 			If the given world is not effective and this faction references an
	 * 			effective world, that world may not contain this faction.
	 * 			| (world == null) && (getWorld() != null) 
	 * 			|					&& (getWorld().hasAsFaction(this))
	 */
	public void setWorld(World world) throws IllegalArgumentException {
		if ( (world != null) && !world.hasAsFaction(this) )
			throw new IllegalArgumentException();
		if ( (world == null) && (getWorld() != null) && (getWorld().hasAsFaction(this)) )
			throw new IllegalArgumentException();
		this.world = world;
	}

	

	/* *********************************************************
	 * 
	 * 						FACTION - SCHEDULER
	 *
	 **********************************************************/
	
	
	@Basic
	public Scheduler getScheduler() {
		return this.scheduler;
	}
	
	
	private final Scheduler scheduler = new Scheduler(this);
	
	
	
	/**
	 * Check whether this faction can be attached to a given world.
	 * 
	 * @param	world
	 * 			The world to check.
	 * 
	 * @return	True if and only if the given world is not effective or if it
	 * 			can contain this faction.
	 * 			| result == ( (world == null)
	 * 			| 				|| world.canHaveAsFaction(this) )
	 */
	@Raw
	public boolean canHaveAsScheduler(Scheduler scheduler) {
		return ( (scheduler == null) || scheduler.canHaveAsFaction(this) );
	}
	
	
	
	/**
	 * Set the world this faction belongs to to the given world.
	 * 
	 * @param	world
	 * 			The world to add the faction to.
	 * 
	 * @post	This faction references the given world as the world
	 * 			it belongs to.
	 * 			| new.getWorld() == world
	 * 
	 * @throws	IllegalArgumentException
	 * 			If the given world is effective it must already reference this faction
	 * 			as one of its factions.
	 * 			| (world != null) && !world.hasAsFaction(this)
	 * 
	 * @throws	IllegalArgumentException
	 * 			If the given world is not effective and this faction references an
	 * 			effective world, that world may not contain this faction.
	 * 			| (world == null) && (getWorld() != null) 
	 * 			|					&& (getWorld().hasAsFaction(this))
	 */
	/*public void setScheduler(Scheduler scheduler) throws IllegalArgumentException {
		if ( (scheduler != null) && !scheduler.hasAsFaction(this) )
			throw new IllegalArgumentException();
		if ( (scheduler == null) && (getScheduler() != null) && (getScheduler().hasAsFaction(this)) )
			throw new IllegalArgumentException();
		this.scheduler = scheduler;
	}*/
	
	
	
}

