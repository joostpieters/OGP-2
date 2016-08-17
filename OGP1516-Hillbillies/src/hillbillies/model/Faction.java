package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.*;

/**
 * A class of factions, attached to worlds. A faction contains up to 50 nits.
 * 
 * @invar	Each faction must have a proper world to which it is
 * 			attached.
 * 			| hasProperWorld()
 * 
 * @invar	The nits associated with each faction must be proper nits for
 * 			that faction.
 * 			| hasProperNits()
 * 
 * @author rubencartuyvels
 *
 */
public class Faction /*implements Comparable<Faction>*/ {
	
	
	/**
	 * Initialize this new faction with no nits attached to it. The faction is not
	 * yet attached to a world.
	 * 
	 * @post	No nits are attached to this new faction.
	 * 			| new.getNbNits() == 0
	 */
	@Raw
	public Faction () {
	}
	
	private static int getMaxNbNits() {
		return maxNits;
	}
	
	private final static int maxNits = 50;
	
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
	 * @effect	Each non-terminated nit in this world is removed from
	 * 			this world.
	 * 			| for each nit in getAllNits():
	 * 			|		if (!nit.isTerminated())
	 * 			|			then this.removeNit(nit)
	 * 
	 */
	public void terminate() {
		for (Nit nit: this.nits) {
			if (!nit.isTerminated()) {
				nit.setWorld(null);
				this.nits.remove(nit);

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
	 * Set collecting references to nits that this faction contains.
	 * 
	 * @invar	the set of nits is effective.
	 * 			| nits != null
	 * 
	 * @invar	Each element in the set references a nit that this faction
	 * 			can have as nit.
	 * 			| for each nit in nits:
	 * 			| 		canHaveAsNit(nit)
	 * 
	 * @invar	Each nit in the set references this faction as their faction.
	 * 			| for each nit in nits:
	 * 			|		nit.getFaction() == this
	 * 
	 * @invar	The number of nits in the set is always smaller than or
	 * 			equal to 50.
	 * 			| nits.size() <= 50
	 */
	private final Set<Nit> nits = new HashSet<Nit>();
	
	
	
	/**
	 * Check whether the given nit is a member of this faction.
	 * 
	 * @param 	nit
	 * 			The nit to check.
	 */
	@Basic @Raw
	public boolean hasAsNit(Nit nit) {
		return this.nits.contains(nit);
	}
	
	/**
	 * Check whether this faction can contain the given nit.
	 * 
	 * @param 	nit
	 * 			The nit to check.
	 * 
	 * @return	False if the given nit is not effective.
	 * 			| if (nit == null)
	 * 			| 	then result = false
	 * 			True if this faction is not terminated or the given nit is
	 * 			also terminated
	 * 			| else result == ( (!this.isTerminated())
	 * 			|		|| nit.isTerminated())
	 */
	@Raw
	public boolean canHaveAsNit(Nit nit) {
		return ( (nit != null) && 
						( !this.isTerminated() || nit.isTerminated()) );
	}
	
	/**
	 * Check whether this faction contains proper nits.
	 * 
	 * @return	True if and only if this faction can contain each of its nits
	 * 			and if each of these nits references this faction as their faction.
	 * 			| result == ( for each nit in Nit:
	 * 			|		if (this.hasAsNit(nit) )
	 * 			|			then ( canHaveAsNit(nit) &&
	 * 			|				(nit.getFaction() == this) ) )
	 */
	@Raw
	public boolean hasProperNits() {
		for (Nit nit: this.nits) {
			if (!canHaveAsNit(nit)) 
				return false;
			if (nit.getFaction() != this)
				return false;
		}
		return true;
	}
	
	/**
	 * Return the number of nits this faction contains.
	 * 
	 * @return	The number of nits this faction contains.
	 * 			| count( {nit in Nit | hasAsNit(nit)} )
	 */
	@Raw
	public int getNbNits() {
		return this.nits.size();
	}
	
	/**
	 * Return a set collecting all nits this faction contains.
	 * 
	 * @return	The resulting set is effective.
	 * 			| result != null
	 * @return	Each nit in the resulting set is attached to this faction
	 * 			and vice versa.
	 * 			| for each nit in Nit:
	 * 			| 	result.contains(nit) == this.hasAsNit(nit)
	 */
	public Set<Nit> getAllNits() {
		return new HashSet<Nit>(this.nits);
	}
	
	
	/**
	 * Return a set collecting all units this faction contains.
	 * 
	 * @return	The resulting set is effective.
	 * 			| result != null
	 * 
	 * @return	Each unit in the resulting set is attached to this faction
	 * 			and vice versa.
	 * 			| for each unit in Unit:
	 * 			| 	result.contains(unit) == this.hasAsNit(unit)
	 */
	public Set<Unit> getAllUnits() {
		Set<Unit> units = new HashSet<Unit>();
		for (Nit nit: this.nits) {
			if (nit instanceof Unit)
				units.add((Unit) nit);
		}
		return units;
	}

	
	/**
	 * Add the given nit to the set of nits that this faction contains.
	 * 
	 * @param	nit
	 * 			The nit to be added.
	 * 
	 * @post	This faction has the given nit as one of its nits.
	 * 			| new.hasAsNit(nit)
	 * 
	 * @post	The given nit references this faction as its faction.
	 * 			| (new nit).getFaction() == this
	 * 
	 * @throws	IllegalArgumentException
	 * 			This faction cannot have the given nit as one of its nits.
	 * 			| !canHaveAsNit(nit)
	 * 
	 * @throws	IllegalArgumentException
	 * 			The given nit already references some faction.
	 * 
	 * @throws	IllegalArgumentException
	 * 			The maximum number of nits in this faction has already been reached.
	 */
	public void addNit(Nit nit)  throws IllegalArgumentException, IllegalNbException {
		if (!canHaveAsNit(nit))
			throw new IllegalArgumentException();
		
		if (getNbNits() >= getMaxNbNits())
			try {
				throw new IllegalNbException();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
				/* For some reason I don't know the program gets stuck after throwing
				* The IllegalNbException. It keeps throwing ArrayIndexOutOfBoundExceptions
				* caused by a method that is part of one of the helper classes. So
				* I shut the program down myself in a clean way.
				*/
			}
		
		this.nits.add(nit);
		nit.setFaction(this);
	}
	
	
	/**
	 * Remove the given nit from this faction.
	 * 
	 * @param	nit
	 * 			The nit to remove.
	 * 
	 * @post	This faction does not contain the given nit.
	 * 			| !new.hasAsNit(nit)
	 * 
	 * @post	If this faction contains the given nit, the nit is no
	 * 			longer attached to any faction.
	 * 			| if (hasAsNit(nit))
	 * 			| 	then ( (new nit).getFaction() == null)
	 */
	public void removeNit(Nit nit) {
		if (hasAsNit(nit)) {
			this.nits.remove(nit);
			nit.setFaction(null);
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
	
	
	private final Scheduler scheduler = new Scheduler();
	
	
	
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
		return ( (scheduler != null) /*|| scheduler.canHaveAsFaction(this)*/ );
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

