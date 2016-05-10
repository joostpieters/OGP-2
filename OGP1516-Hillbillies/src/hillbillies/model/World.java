package hillbillies.model;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;
import ogp.framework.util.ModelException;

import be.kuleuven.cs.som.annotate.*;

import hillbillies.model.Boulder;
import hillbillies.model.Log;

import hillbillies.model.Unit;

import hillbillies.model.Faction.*;

import java.util.*;

public class World {
	
	private final static int maxUnits = 100;
	private final static int maxFactions = 100;
	
	/**
	 * Variable registering the length of a cube of the game world.
	 */
	private final static double cubeLength = 1.0;
	
	public double getCubeLength() {
		return cubeLength;
	}
	
	private int[][][] terrainTypes;
	private final TerrainChangeListener modelListener;
	private final ConnectedToBorder connectedToBorderChecker;
	
	private static final Random random = new Random();
	private boolean isTerminated = false;
	
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Initialize this new world with given terrain types for each cube of the
	 * game world and a modelListener. The new world contains no units nor factions.
	 * 
	 * @param	terrainTypes
	 * 
	 * @param	modelListener
	 * 
	 * @post	This new world contains no units.
	 * 			| new.getNbUnits() == 0
	 * 
	 * @post	This new world contains no factions.
	 * 			| new.getNbFactions() == 0
	 * 
	 * @throws	IllegalArgumentException
	 * 			The given terrain type array is not valid.
	 */
	@Raw
	public World(int[][][] terrainTypes, TerrainChangeListener modelListener) 
							throws IllegalArgumentException {
		if (!isValidTerrainTypes(terrainTypes)) 
			throw new IllegalArgumentException();
		this.terrainTypes = terrainTypes;
		this.modelListener = modelListener;
		
		this.connectedToBorderChecker = new ConnectedToBorder(getNbCubesX(),
				getNbCubesY(), getNbCubesZ());
		
		this.caveInAll();
	}
	
	private boolean isValidTerrainTypes(int[][][] terrainTypes) {
		
		int b = terrainTypes[0].length;
		int c = terrainTypes[0][0].length;
		for (int x = 0; x < terrainTypes.length; x++) {
			
			if (terrainTypes[x].length != b)
				return false;
			
			for (int y = 0; y < terrainTypes[0].length; y++) {
				
				if (terrainTypes[x][y].length != c)
					return false;
				
				for (int z = 0; z < terrainTypes[0][0].length; z++) {
					if ( !(terrainTypes[x][y][z] == 0 || terrainTypes[x][y][z] == 1
							|| terrainTypes[x][y][z] == 2 || terrainTypes[x][y][z] == 3)) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	@Basic
	public int getNbCubesX() {
		return this.terrainTypes.length;
	}
	
	@Basic
	public int getNbCubesY() {
		return this.terrainTypes[0].length;
	}
	
	@Basic
	public int getNbCubesZ() {
		return this.terrainTypes[0][0].length;
	}
	
	
	public void setCubeTypeAt(int x, int y, int z, TerrainType type) {
		if (!isValidTerrainType(type))
			throw new IllegalArgumentException();
		this.terrainTypes[x][y][z] = type.getNumber();
	}
	
	
	public TerrainType getCubeTypeAt(int x, int y, int z) {
		return TerrainType.byOrdinal(this.terrainTypes[x][y][z]);
	}
	
	private boolean isValidTerrainType(TerrainType type) {
		return TerrainType.contains("type");
	}
	
	
	public void advanceTime(double dt) {
		if (!isValidDT(dt))
			throw new IllegalArgumentException();
		
		//advance time for all units in world
		for (Unit unit: units) {
			unit.advanceTime(dt);
		}
		
		
	}
	
	/**
	 * Check if a given value is a valid game time dt value.
	 * 
	 * @param dt
	 * 			The value to be checked.
	 * @return true if and only if the value is larger than or equal to
	 * 			zero and smaller than 0.2.
	 * 		| result =  (dt >= 0 && dt < 0.2 )
	 */
	//@Model
	private static boolean isValidDT(double dt) {
		return (dt <= 0.2 && dt >= 0.0);
	}
	
	
	/* *********************************************************
	 * 
	 * 							UNITS
	 *
	 **********************************************************/
	
	/**
	 * Set collecting references to units that this world contains.
	 * 
	 * @invar	the set of units is effective.
	 * 			| units != null
	 * 
	 * @invar	Each element in the set references a unit that this world
	 * 			can have as unit.
	 * 			| for each unit in units:
	 * 			| 		canHaveAsUnit(unit)
	 * 
	 * @invar	Each unit in the set references this world as their world.
	 * 			| for each unit in units:
	 * 			|		unit.getWorld() == this
	 */
	private final Set<Unit> units = new HashSet<Unit>();
	
	/**
	 * Spawn a unit with a random position in this world and random initial
	 * attributes.
	 * 
	 * @return the spawned unit.
	 */
	public Unit spawnUnit() {
		if (getAllUnits().size() < 100) {
			int x, y, z;
			while(true) {
				try {
					x = random.nextInt(getNbCubesX()+1);
					y = random.nextInt(getNbCubesY()+1);
					z = random.nextInt(getNbCubesZ()+1);
					if (getCubeTypeAt(x, y, z).isPassable() && (
							!getCubeTypeAt(x, y, z-1).isPassable() || z == 0))
						break;
				}
				catch (IllegalPositionException exc){
					continue;
				}
			}
			int[] position = new int[]{x, y, z};
			
			
			int agility = random.nextInt(76) + 25;
			int strength = random.nextInt(76) + 25;
			int toughness = random.nextInt(76) + 25;
			int weight = (strength+agility)/2 + random.nextInt(100 - (strength+agility)/2);
			Unit unit = new Unit("Name", position, weight, agility, strength, toughness, true);
			
			return unit;
		}
		return null;
	}
	
	
	/**
	 * Check whether this world contains the given unit.
	 * 
	 * @param 	unit
	 * 			The unit to check.
	 */
	@Basic @Raw
	public boolean hasAsUnit(Unit unit) {
		return this.units.contains(unit);
	}
	
	/**
	 * Check whether this world can contain the given unit.
	 * 
	 * @param 	unit
	 * 			The unit to check.
	 * 
	 * @return	False if the given unit is not effective.
	 * 			| if (unit == null)
	 * 			| 	then result = false
	 * 			True if this world is not terminated or the given unit is
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
	 * Check whether this world contains proper units.
	 * 
	 * @return	True if and only if this world can contain each of its units
	 * 			and if each of these units references this world as their world.
	 * 			| result == ( for each unit in Unit:
	 * 			|		if (this.hasAsUnit(unit) )
	 * 			|			then ( canHaveAsUnit(unit) &&
	 * 			|				(unit.getWorld() == this) ) )
	 */
	@Raw
	public boolean hasProperUnits() {
		for (Unit unit: this.units) {
			if (!canHaveAsUnit(unit)) 
				return false;
			if (unit.getWorld() != this)
				return false;
		}
		return true;
	}
	
	/**
	 * Return the number of units this world contains.
	 * 
	 * @return	The number of units this world contains.
	 * 			| count( {unit in Unit | hasAsUnit(unit)} )
	 */
	@Raw
	public int getNbUnits() {
		return this.units.size();
		/*
		int count = 0:
		for (Object o: this.units) {
			if (hasAsUnit(o))
				count ++;
		}
		return count;*/
	}
	
	/**
	 * Return a set collecting all units this world contains.
	 * 
	 * @return	The resulting set is effective.
	 * 			| result != null
	 * @return	Each unit in the resulting set is attached to this world
	 * 			and vice versa.
	 * 			| for each unit in Unit:
	 * 			| 	result.contains(unit) == this.hasAsUnit(unit)
	 */
	public Set<Unit> getAllUnits() {
		return new HashSet<Unit>(this.units);
	}
	
	/**
	 * Add the given unit to the set of units that this world contains.
	 * 
	 * @param	unit
	 * 			The unit to be added.
	 * 
	 * @post	This world has the given unit as one of its units.
	 * 			| new.hasAsUnit(unit)
	 * @post	The given unit references this world as its world.
	 * 			| (new unit).getWorld() == this
	 * 
	 * @throws	IllegalArgumentException
	 * 			This world cannot have the given unit as one of its units.
	 * 			| !canHaveAsUnit(unit)
	 * 
	 * @throws	IllegalArgumentException
	 * 			The given unit already references some world.
	 */
	public void addUnit(Unit unit)  throws IllegalArgumentException {
		if (!canHaveAsUnit(unit))
			throw new IllegalArgumentException();
		if (unit.getWorld() != null)
			throw new IllegalArgumentException();
		this.units.add(unit);
		unit.setWorld(this);
	}
	
	
	/**
	 * Remove the given unit from this world.
	 * 
	 * @param	unit
	 * 			The unit to remove.
	 * @post	This world does not contain the given unit.
	 * 			| !new.hasAsUnit(unit)
	 * @post	If this world contains the given unit, the unit is no
	 * 			longer attached to any world.
	 * 			| if (hasAsUnit(unit))
	 * 			| 	then ( (new unit).getWorld() == null)
	 */
	public void removeUnit(Unit unit) {
		if (hasAsUnit(unit)) {
			this.units.remove(unit);
			unit.setWorld(null);
		}
	}
	
	
	/* *********************************************************
	 * 
	 * 							FACTIONS
	 *
	 **********************************************************/
	
	private final Set<Faction> factions = new TreeSet<Faction>(new NbUnitsComparator());
	
	
	public Set<Faction> getActiveFactions() {
		return new TreeSet<Faction>(this.factions);
	}
	
	
	/* *********************************************************
	 * 
	 * 							ITEMS
	 *
	 **********************************************************/
	
	private final Set<Item> logs = new HashSet<Item>();
	private final Set<Item> boulders = new HashSet<Item>();
	
	
	public Set<Item> getBoulders() {
		return new HashSet<Item>(this.boulders);
	}
	
	public Set<Item> getLogs() {
		return new HashSet<Item>(this.logs);
	}
	
	public Set<Item> getObjectsAt(int x, int y, int z) {
		//Set<Item> items = new HashSet<Item>();
		//Set<Item> items = this.boulders;
		return this.boulders;
	}
	
	
	
	/**
	 * Terminate this world.
	 * 
	 * @post	This world is terminated.
	 * 			| new.isTerminated()
	 * 
	 * @effect	Each non-terminated unit in this world is removed from
	 * 			this world.
	 * 			| for each unit in getAllUnits():
	 * 			|		if (!unit.isTerminated())
	 * 			|			then this.removeUnit(unit)
	 * 
	 * @effect	Each non-terminated faction in this world is removed from
	 * 			this world.
	 * 			| for each faction in getAllFactions():
	 * 			|		if (!faction.isTerminated())
	 * 			|			then this.removeFaction(faction)
	 */
	public void terminate() {
		for (Unit unit: this.units) {
			if (!unit.isTerminated()) {
				unit.setWorld(null);
				this.units.remove(unit);
			}
		}
		//this.factions.clear(); (IF WE CHOOSE TO MAKE FACTIONS A LIST)
		for (Faction faction: this.factions) {
			if (!faction.isTerminated()) {
				faction.setWorld(null);
				this.units.remove(faction);
			}
		}
		this.isTerminated = true;
	}
	
	
	
	
	/* *********************************************************
	 * 
	 * 							TERRAIN
	 *
	 **********************************************************/
	
	
	
	@Basic @Immutable
	private ConnectedToBorder getConnectedToBorderChecker() {
		return this.connectedToBorderChecker;
	}
	
	public boolean isSolidConnectedToBorder(int x, int y, int z) {
		return getConnectedToBorderChecker().isSolidConnectedToBorder(x, y, z);
	}
	
	public void caveInAll() {
		for (int x = 0; x < terrainTypes.length; x++) {
			for (int y = 0; y < terrainTypes[0].length; y++) {
				for (int z = 0; z < terrainTypes[0][0].length; z++) {
					
					if(!getCubeTypeAt(x, y, z).isPassable() && !isSolidConnectedToBorder(x, y, z)) {

						collapse(x, y, z);
						this.modelListener.notifyTerrainChanged(x, y, z);
						caveIn(x,y,z);
					}
					
					
				}
			}
		}
	}
	
	private void caveIn(int x, int y, int z) {
		List<int[]> changed = getConnectedToBorderChecker().changeSolidToPassable(x, y, z);
		for (int[] cube: changed) {
			if (!getCubeTypeAt(cube[0], cube[1], cube[2]).isPassable() ) {
				collapse(cube[0], cube[1], cube[2]);
				this.modelListener.notifyTerrainChanged(cube[0], cube[1], cube[2]);
				caveIn(cube[0], cube[1], cube[2]);
			}
		}
	}
	
	private void collapse(int x, int y, int z) {
		double dice = random.nextDouble();
		if (dice < 0.25) {
			if (getCubeTypeAt(x,y,z) == TerrainType.ROCK) {
				Boulder boulder = new Boulder(x, y, z, this);
				this.boulders.add(boulder);
			}
			else if (getCubeTypeAt(x,y,z) == TerrainType.TREE) {
				Log log = new Log(x, y, z, this);
				this.logs.add(log);
			}
		}
		setCubeTypeAt(x, y, z, TerrainType.AIR);
	}
	
	
	public enum TerrainType {
		AIR(true, 0),
		ROCK(false, 1),
		TREE(false, 2),
		WORKSHOP(true, 3);
		
		private TerrainType(boolean passable, int number) {
			this.passable = passable;
			this.number = number;
		}
		
		@Basic
		public boolean isPassable() {
			return this.passable;
		}
		
		@Basic
		public int getNumber() {
			return this.number;
		}
		
		private final boolean passable;
		private final int number;
		
		public static boolean contains(String test) {

		    for (TerrainType c : TerrainType.values()) {
		        if (c.name().equals(test)) {
		            return true;
		        }
		    }

		    return false;
		}
		
		public static TerrainType byOrdinal(int ord) {
	        for (TerrainType t : TerrainType.values()) {
	            if (t.getNumber() == ord) {
	                return t;
	            }
	        }
	        return null;
	    }
		
	}
}
