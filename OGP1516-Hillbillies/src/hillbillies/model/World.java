package hillbillies.model;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;

import be.kuleuven.cs.som.annotate.*;
import java.util.*;


/**
 * A class of worlds containing up to 100 units and 5 factions, and a number of items.
 * 
 * @invar	The units in each world must be proper units for that world.
 * 			| hasProperUnits()
 * 
 * @invar	The factions associated with each world must be proper factions for
 * 			that world.
 * 			| hasProperFactions()
 * 
 * @invar	The logs in each world must be proper logs for that world.
 * 			| hasProperLogs()
 * 
 * @invar	The boulders in each world must be proper boulders for that world.
 * 			| hasProperBoulders()
 * 
 * @version		1.2
 * @author 		rubencartuyvels
 */
public class World {
	
	/**
	 * Initialize this new world with given terrain types for each cube of the
	 * game world, the given modelListener and a connectedToBorderChecker. 
	 * The new world contains no units and 5 empty factions.
	 * 
	 * @param	terrainTypes
	 * 
	 * @param	modelListener
	 * 
	 * @post	This new world contains no units.
	 * 
	 * @post	This new world contains 5 factions.
	 * 
	 * @throws	IllegalArgumentException
	 * 			The given terrain type array is not valid.
	 */
	// TODO finish documentation
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
		
		/*for (int i=0; i<getMaxNbFactions(); i++) {
			Faction faction = new Faction();
			addFaction(faction);
		}
		*/		
	}
	
	
	protected final static int getMaxNbUnits() {
		return maxUnits;
	}
	
	private final static int maxUnits = 100;
	
	protected final static int getMaxNbFactions() {
		return maxFactions;
	}
	
	private final static int maxFactions = 5;
	
	
	public final static double getCubeLength() {
		return cubeLength;
	}
	
	/**
	 * Variable registering the length of a cube of the game world.
	 */
	private final static double cubeLength = 1.0;
	
	
	private static final Random random = new Random();
	
	
	/**
	 * Return the terrain type array of this game world.
	 */
	@Basic @Raw
	public int[][][] getTerrainTypes() {
		return this.terrainTypes;
	}
	
	
	/*public List<Coordinate> getAllCoordinates() {
		List<Coordinate> allCoordinates = new ArrayList<Coordinate>();
		for (int i=0; i<getNbCubesX(); i++) {
			for (int j=0; j<getNbCubesY(); j++) {
				for (int z=0; z<getNbCubesZ(); z++) {
					allCoordinates.add(new Coordinate(i, j , z));
				}
			}
		}
		return allCoordinates;
	}*/
	
	private int[][][] terrainTypes;
	
	
	private final TerrainChangeListener modelListener;
	private final ConnectedToBorder connectedToBorderChecker;
	
	
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
	
	/**
	 * Check whether the given coordinates are valid for this world.
	 * 
	 * @param coordinates
	 * 			The coordinates to be checked.
	 * 
	 * @return True if and only if the coordinates array is effective, if it
	 * 			consists of an array with 3 double elements and if each coordinate
	 * 			is between 0 and the upper limit of that dimension.
	 * 		| result = ( (coordinates instanceof int[])
	 * 		|		&& (coordinates.length == 3)
	 * 		| 		&& !(coordinates[0] < 0.0 || coordinates[0] >= getNbCubesX())
	 * 		|		&& !(coordinates[1] < 0.0 || coordinates[1] >= getNbCubesY())
	 * 		|		&& !(coordinates[2] < 0.0 || coordinates[2] >= getNbCubesZ())
	 */
	public boolean canHaveAsCoordinates(Coordinate coordinates) {
		if (coordinates == null)
			return false;
		if (coordinates.get(0) < 0 || coordinates.get(0) >= getNbCubesX()) {
			return false; }
		if (coordinates.get(1) < 0 || coordinates.get(1) >= getNbCubesY()) {
			return false; }
		if (coordinates.get(2) < 0 || coordinates.get(2) >= getNbCubesZ()) {
			return false; }
		return true;
	}
	
	
	public static double[] getCubeCenter(Coordinate cubeCoordinate) {
		double[] cubeCenter = new double[3];
		for (int i=0; i<cubeCoordinate.getCoordinates().length; i++) {
			cubeCenter[i] = cubeCoordinate.getCoordinates()[i] + getCubeLength()/2;
		}
		return cubeCenter;
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
	
	
	public void setCubeTypeAt(Coordinate coordinate, TerrainType type) {
		if (!isValidTerrainType(type))
			throw new IllegalArgumentException();
		this.terrainTypes[coordinate.get(0)][coordinate.get(1)][coordinate.get(2)] = type.getNumber();
	}
	
	
	public TerrainType getCubeTypeAt(Coordinate coordinate) throws IllegalPositionException {
		if (!canHaveAsCoordinates(coordinate) )
			throw new IllegalPositionException(coordinate);
		return TerrainType.byOrdinal(this.terrainTypes[coordinate.get(0)][coordinate.get(1)][coordinate.get(2)]);
	}
	
	
	private boolean isValidTerrainType(TerrainType type) {
		return TerrainType.contains(type);
	}
	
	
	public boolean isNeighbouringSolid(Coordinate coordinates) {
		if (!canHaveAsCoordinates(coordinates) )
			throw new IllegalPositionException(coordinates);
		
		Set<Coordinate> neighbours = getNeighbours(coordinates);
		for (Coordinate neighbour: neighbours) {
			if (canHaveAsCoordinates(neighbour))
				if (!getCubeTypeAt(neighbour).isPassable())
					return true;
		}
		return false;
	}
	
	
	public Set<Coordinate> getNeighbours(Coordinate coordinates) {
		Set<Coordinate> neighbours = new HashSet<Coordinate>();
		for (int i = -1; i<2; i++) {
			for (int k = -1; k<2; k++) {
				for (int l = -1; l<2; l++) {
					Coordinate neighbour = new Coordinate(coordinates.get(0)+i,coordinates.get(1)+k,coordinates.get(2)+l);
					if (canHaveAsCoordinates(neighbour) && !neighbour.equals(coordinates))
						neighbours.add(neighbour);
				}
			}
		}
		return neighbours;
	}
	
	
	public boolean isNeighbouring(Coordinate coordinates, Coordinate neighbourCoordinates) {
		Set<Coordinate> neighbours = getNeighbours(coordinates);
		/*System.out.println(neighbourCoordinates.toString());
		System.out.println( (neighbours.contains(neighbourCoordinates)) );*/
		return (neighbours.contains(neighbourCoordinates));
	}
	
	
	public boolean isAboveSolid(Coordinate coordinates) {
		if (!canHaveAsCoordinates(coordinates) )
			throw new IllegalPositionException(coordinates);
		if (coordinates.get(2) == 0)
			return true;
		
		if (!getCubeTypeAt(new Coordinate(coordinates.get(0), 
					coordinates.get(1), coordinates.get(2)-1)).isPassable() )
			return true;
		
		return false;
	}
	
	
	public Coordinate getRandomNeighbouringSolidCube() {
		int x, y, z;
		Coordinate position;
		while(true) {
			try {
				x = random.nextInt(getNbCubesX());
				y = random.nextInt(getNbCubesY());
				z = random.nextInt(getNbCubesZ());
				position = new Coordinate(x, y, z);
				
				if (getCubeTypeAt(position).isPassable() && (
						!getCubeTypeAt(new Coordinate(x, y, z-1)).isPassable() || z == 0))
					break;
			}
			catch (IllegalPositionException exc){
				continue;
			}
		}
		return position;
	}
	
	
	public Coordinate getNearRandomNeighbouringSolidCube(Coordinate coordinate) {
		int x, y, z;
		Coordinate position;
		while(true) {
			try {
				x = random.nextInt(7) - 3;
				y = random.nextInt(7) - 3;
				z = random.nextInt(7) - 3;
				position = new Coordinate(coordinate.get(0)+x, coordinate.get(1)+y, 
									coordinate.get(2)+z);
				
				if (getCubeTypeAt(position).isPassable() && (
						!getCubeTypeAt(new Coordinate(coordinate.get(0)+x, coordinate.get(1)+y,
								coordinate.get(2)+z-1)).isPassable() || coordinate.get(2)+z == 0))
					break;
			}
			catch (IllegalPositionException exc){
				continue;
			}
		}
		return position;
	}
	
	
	public Coordinate getRandomNeighbouringCube(Coordinate coordinate) {
		Set<Coordinate> neighbours = getNeighbours(coordinate);
		int dice = random.nextInt(neighbours.size());
		int counter = 0;
		for (Coordinate neighbour: neighbours) {
			if (counter == dice)
				return neighbour;
			counter++;
		}
		return null;
	}
	
	// TODO arraylist with all timesubjects
	public void advanceTime(double dt) throws RuntimeException {
		if (!isValidDT(dt))
			throw new IllegalArgumentException();
		
		/*for (Unit unit: units) {
			unit.advanceTime(dt);
		}
		/*for (Log log: logs) {
			log.advanceTime(dt);
		}
		for (Boulder boulder: boulders) {
			boulder.advanceTime(dt);
		}*/
		
		updateTimeSubjects();
		
		for (TimeSubject timesubject: this.timeSubjects) {
			timesubject.advanceTime(dt);
		}
	}
	
	
	/**
	 * Check if a given value is a valid game time dt value.
	 * 
	 * @param 	dt
	 * 			The value to be checked.
	 * @return 	true if and only if the value is larger than or equal to
	 * 			zero and smaller than 0.2.
	 * 			| result =  (dt >= 0 && dt < 0.2 )
	 */
	public static boolean isValidDT(double dt) {
		return (dt <= 0.2 && dt >= 0.0);
	}
	
	
	/* *********************************************************
	 * 
	 * 							UNITS
	 *
	 **********************************************************/
	
	
	
	/**
	 * Spawn a unit with a random position in this world and random initial
	 * attributes.
	 * 
	 * @return the spawned unit.
	 */
	public Unit spawnUnit(boolean enableDefaultBehavior) {
		
		if (getAllUnits().size() >= getMaxNbUnits()) {
			throw new IllegalNbException();
		}
		
		Faction faction;
		if (this.factions.size() < 5) {
			faction = new Faction();
			addFaction(faction);
		} else {
			sortFactions();
			faction = this.factions.get(0);
		}
		
		Unit unit = new Unit(this, faction, enableDefaultBehavior);
		
		addUnit(unit);
		faction.addUnit(unit);
		
		return unit;
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
	}
	
	/**
	 * Return a list collecting all units this world contains.
	 * 
	 * @return	The resulting list is effective.
	 * 			| result != null
	 * 
	 * @return	Each unit in the resulting list is attached to this world
	 * 			and vice versa.
	 * 			| for each unit in Unit:
	 * 			| 	result.contains(unit) == this.hasAsUnit(unit)
	 */
	public List<Unit> getAllUnits() {
		return new ArrayList<Unit>(this.units);
	}

	
	public Set<Unit> getAllUnitsSet() {
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
	 * 
	 * @throws	IllegalArgumentException
	 * 			The maximum number of units in this world has already been reached.
	 */
	public void addUnit(Unit unit)  throws IllegalArgumentException, IllegalNbException {
		if (!canHaveAsUnit(unit))
			throw new IllegalArgumentException();
		if (unit.getWorld() != this)
			throw new IllegalArgumentException();
		if (getNbUnits() >= getMaxNbUnits())
			throw new IllegalNbException();
		
		this.units.add(unit);
		this.toAddToTimeSubjects.add(unit);
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
			this.toRemoveFromTimeSubjects.add(unit);
			unit.setWorld(null);
		}
	}
	
	
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
	 * 
	 * @invar	The number of units in the set is always smaller than or
	 * 			equal to 100.
	 * 			| units.size() <= 100
	 */
	private final Set<Unit> units = new HashSet<Unit>();
	
	
	/* *********************************************************
	 * 
	 * 							FACTIONS
	 *
	 **********************************************************/
	
	
	/**
	 * Check whether this world contains the given faction.
	 * 
	 * @param 	faction
	 * 			The faction to check.
	 */
	@Basic @Raw
	public boolean hasAsFaction(Faction faction) {
		return this.factions.contains(faction);
	}
	
	/**
	 * Check whether this world can contain the given faction.
	 * 
	 * @param 	faction
	 * 			The faction to check.
	 * 
	 * @return	False if the given faction is not effective.
	 * 			| if (faction == null)
	 * 			| 	then result = false
	 * 			True if this world is not terminated or the given faction is
	 * 			also terminated
	 * 			| else result == ( (!this.isTerminated())
	 * 			|		|| faction.isTerminated())
	 */
	@Raw
	public boolean canHaveAsFaction(Faction faction) {
		return ( (faction != null) && 
						( !this.isTerminated() || faction.isTerminated()) );
	}
	
	/**
	 * Check whether this world contains proper factions.
	 * 
	 * @return	True if and only if this world can contain each of its factions
	 * 			and if each of these factions references this world as their world.
	 * 			| result == ( for each faction in Faction:
	 * 			|		if (this.hasAsFaction(faction) )
	 * 			|			then ( canHaveAsFaction(faction) &&
	 * 			|				(faction.getWorld() == this) ) )
	 */
	@Raw
	public boolean hasProperFactions() {
		for (Faction faction: this.factions) {
			if (!canHaveAsFaction(faction)) 
				return false;
			if (faction.getWorld() != this)
				return false;
		}
		return true;
	}
	
	/**
	 * Return the number of factions this world contains.
	 * 
	 * @return	The number of factions this world contains.
	 * 			| count( {faction in Faction | hasAsFaction(faction)} )
	 */
	@Raw
	public int getNbFactions() {
		return this.factions.size();
	}
	
	/**
	 * Return a set collecting all factions this world contains.
	 * 
	 * @return	The resulting set is effective.
	 * 			| result != null
	 * @return	Each faction in the resulting set is attached to this world
	 * 			and vice versa.
	 * 			| for each faction in Faction:
	 * 			| 	result.contains(faction) == this.hasAsFaction(faction)
	 */
	public Set<Faction> getAllFactions() {
		Set<Faction> allFactions = new HashSet<Faction>();
		for (Faction faction: this.factions) {
			allFactions.add(faction);
		}
		return allFactions;
	}
	
	/**
	 * Add the given faction to the set of factions that this world contains.
	 * 
	 * @param	faction
	 * 			The faction to be added.
	 * 
	 * @post	This world has the given faction as one of its factions.
	 * 			| new.hasAsFaction(faction)
	 * @post	The given faction references this world as its world.
	 * 			| (new faction).getWorld() == this
	 * 
	 * @throws	IllegalArgumentException
	 * 			This world cannot have the given faction as one of its factions.
	 * 			| !canHaveAsFaction(faction)
	 * 
	 * @throws	IllegalArgumentException
	 * 			The given faction already references some world.
	 * 
	 * @throws	IllegalArgumentException
	 * 			The maximum number of factions in this faction has already been reached.
	 */
	public void addFaction(Faction faction)  throws IllegalArgumentException, IllegalNbException {
		if (!canHaveAsFaction(faction))
			throw new IllegalArgumentException();
		if (faction.getWorld() != null)
			throw new IllegalArgumentException();
		if (getNbFactions() >= getMaxNbFactions())
			throw new IllegalNbException(); // ILLEGAL SIZE EXCEPTION??????
		
		this.factions.add(faction);
		faction.setWorld(this);
	}
	
	
	/**
	 * Remove the given unit from this world.
	 * 
	 * @param	faction
	 * 			The faction to remove.
	 * @post	This world does not contain the given faction.
	 * 			| !new.hasAsFaction(faction)
	 * @post	If this world contains the given faction, the faction is no
	 * 			longer attached to any world.
	 * 			| if (hasAsFaction(faction))
	 * 			| 	then ( (new faction).getWorld() == null)
	 */
	public void removeFaction(Faction faction) {
		if (hasAsFaction(faction)) {
			this.factions.remove(faction);
			faction.setWorld(null);
		}
	}
	
	
	private void sortFactions() {
		this.factions.sort(new NbUnitsComparator());
	}
	
	
	/**
	 * Set collecting references to factions that this world contains.
	 * 
	 * @invar	the set of factions is effective.
	 * 			| factions != null
	 * 
	 * @invar	Each element in the set references a faction that this world
	 * 			can have as faction.
	 * 			| for each faction in factions:
	 * 			| 		canHaveAsFaction(faction)
	 * 
	 * @invar	Each faction in the set references this world as their world.
	 * 			| for each faction in factions:
	 * 			|		faction.getWorld() == this
	 * 
	 * @invar	The number of active factions in the set is always smaller than or
	 * 			equal to 5.
	 * 			| factions.size() <= 5
	 */
	private final List<Faction> factions = new ArrayList<Faction>();
	
	
	/* *********************************************************
	 * 
	 * 							ITEMS
	 *
	 **********************************************************/
	
	
	public boolean containsItem(Set<Item> items) {
		//Set<Item> items = getObjectsAt(cubeCoordinates);
		if (!items.isEmpty()) {
			return true;
		}
		return false;
	}
	
	
	public Set<Item> getObjectsAt(Coordinate cubeCoordinates) {
		Set<Item> items = new HashSet<Item>();
		
		for (Boulder boulder: this.boulders) {
			if (boulder != null && (boulder.getCoordinate().equals(cubeCoordinates)) ) {
				items.add(boulder);
			}
		}
		for (Log log: this.logs) {
			if (log != null && (log.getCoordinate().equals(cubeCoordinates) )) {
				items.add(log);
			}
		}
		return items;
	}
	
	
	
	@Basic @Raw
	public boolean hasAsItem(Item item) {
		return (this.boulders.contains(item) || this.logs.contains(item));
	}
	
	
	@Raw
	public boolean canHaveAsItem(Item item) {
		return ( (item != null) && 
						( !this.isTerminated() || item.isTerminated()) );
	}
	
	
	
	public void removeItem(Item item) {
		if (hasAsItem(item)) {
			if (item instanceof Boulder) {
				removeBoulder((Boulder) item);
			}
			if (item instanceof Log) {
				removeLog((Log) item);
			}
		}
	}
	
	public void addItem(Item item) {
		
		if (item instanceof Boulder) {
			this.addBoulder((Boulder) item);
		}
		if (item instanceof Log) {
			this.addLog((Log) item);
		}
	}
	
		
	
	
	//		LOGS
	
	
	/**
	 * Check whether this world can contain the given log.
	 * 
	 * @param 	log
	 * 			The log to check.
	 * 
	 * @return	False if the given log is not effective.
	 * 			| if (log == null)
	 * 			| 	then result = false
	 * 			True if this world is not terminated or the given log is
	 * 			also terminated
	 * 			| else result == ( (!this.isTerminated())
	 * 			|		|| log.isTerminated())
	 */
	/*@Raw
	public boolean canHaveAsLog(Log log) {
		return ( (log != null) && 
						( !this.isTerminated() || log.isTerminated()) );
	}
	*/
	/**
	 * Check whether this world contains proper logs.
	 * 
	 * @return	True if and only if this world can contain each of its logs
	 * 			and if each of these logs references this world as their world.
	 * 			| result == ( for each log in Log:
	 * 			|		if (this.hasAsLog(log) )
	 * 			|			then ( canHaveAsLog(log) &&
	 * 			|				(log.getWorld() == this) ) )
	 */
	@Raw
	public boolean hasProperLogs() {
		for (Log log: this.logs) {
			if (!canHaveAsItem(log)) 
				return false;
			if (log.getWorld() != this)
				return false;
		}
		return true;
	}
	
	
	public boolean containsLog(Set<Item> items) {
		//Set<Item> items = getObjectsAt(cubeCoordinates);
		for (Item item: items) {
			if (item instanceof Log)
				return true;
		}
		return false;
	}
	
	
	/**
	 * Return the number of logs this world contains.
	 * 
	 * @return	The number of logs this world contains.
	 * 			| count( {log in Log | hasAsLog(log)} )
	 */
	@Raw
	public int getNbLogs() {
		return this.logs.size();
	}
	
	/**
	 * Return a set collecting all logs in this world.
	 * 
	 * @return	The resulting set is effective.
	 * 			| result != null
	 * @return	Each log in the resulting set is attached to this world
	 * 			and vice versa.
	 * 			| for each log in Log:
	 * 			| 	result.contains(log) == this.hasAsLog(log)
	 */
	public Set<Log> getAllLogs() {
		return new HashSet<Log>(this.logs);
	}
	
	
	public Item getLogFrom(Set<Item> items) {
		for (Item item: items) {
			if (item instanceof Log)
				return item;
		}
		return null;
	}
	
	/**
	 * Add the given log to the set of logs that this world contains.
	 * 
	 * @param	log
	 * 			The log to be added.
	 * 
	 * @post	This world has the given log as one of its logs.
	 * 			| new.hasAsLog(log)
	 * 
	 * @post	The given log references this world as its world.
	 * 			| (new log).getWorld() == this
	 * 
	 * @throws	IllegalArgumentException
	 * 			This world cannot have the given log as one of its logs.
	 * 			| !canHaveAsLog(log)
	 * 
	 * @throws	IllegalArgumentException
	 * 			The given log already references some world.
	 */
	public void addLog(Log log)  throws IllegalArgumentException {
		if (!canHaveAsItem(log))
			throw new IllegalArgumentException();
		if (log.getWorld() != this)
			throw new IllegalArgumentException();
		this.logs.add(log);
		this.toAddToTimeSubjects.add(log);
		log.setWorld(this);
	}
	
	
	/**
	 * Remove the given log from this world.
	 * 
	 * @param	log
	 * 			The log to remove.
	 * 
	 * @post	This world does not contain the given log.
	 * 			| !new.hasAsLog(log)
	 * 
	 * @post	If this world contains the given log, the log is no
	 * 			longer attached to any world.
	 * 			| if (hasAsLog(log))
	 * 			| 	then ( (new log).getWorld() == null)
	 */
	public void removeLog(Log log) {
		if (hasAsItem(log)) {
			this.logs.remove(log);
			this.toRemoveFromTimeSubjects.add(log);
			log.setWorld(null);
		}
	}
	
	
	/**
	 * Set collecting references to logs that this world contains.
	 * 
	 * @invar	the set of logs is effective.
	 * 			| logs != null
	 * 
	 * @invar	Each element in the set references a log that this world
	 * 			can have as log.
	 * 			| for each log in logs:
	 * 			| 		canHaveAsLog(log)
	 * 
	 * @invar	Each log in the set references this world as their world.
	 * 			| for each log in logs:
	 * 			|		log.getWorld() == this
	 */
	private final Set<Log> logs = new HashSet<Log>();
	

	
	//		BOULDERS
		
	
	
	/**
	 * Check whether this world contains proper boulders.
	 * 
	 * @return	True if and only if this world can contain each of its boulders
	 * 			and if each of these boulders references this world as their world.
	 * 			| result == ( for each boulder in Boulder:
	 * 			|		if (this.hasAsBoulder(boulder) )
	 * 			|			then ( canHaveAsBoulder(boulder) &&
	 * 			|				(boulder.getWorld() == this) ) )
	 */
	@Raw
	public boolean hasProperBoulders() {
		for (Boulder boulder: this.boulders) {
			if (!canHaveAsItem(boulder)) 
				return false;
			if (boulder.getWorld() != this)
				return false;
		}
		return true;
	}
	
	
	public boolean containsBoulder(Set<Item> items) {
		//Set<Item> items = getObjectsAt(cubeCoordinates);
		for (Item item: items) {
			if (item instanceof Boulder)
				return true;
		}
		return false;
	}
	
	
	
	/**
	 * Return the number of boulders this world contains.
	 * 
	 * @return	The number of boulders this world contains.
	 * 			| count( {boulder in Boulder | hasAsBoulder(boulder)} )
	 */
	@Raw
	public int getNbBoulders() {
		return this.boulders.size();
	}
	
	/**
	 * Return a set collecting all boulders in this world.
	 * 
	 * @return	The resulting set is effective.
	 * 			| result != null
	 * @return	Each boulder in the resulting set is attached to this world
	 * 			and vice versa.
	 * 			| for each boulder in Boulder:
	 * 			| 	result.contains(boulder) == this.hasAsBoulder(boulder)
	 */
	public Set<Boulder> getAllBoulders() {
		//return new HashSet<Boulder>(this.boulders);
		return this.boulders;
	}
	
	
	public Item getBoulderFrom(Set<Item> items) {
		for (Item item: items) {
			if (item instanceof Boulder)
				return item;
		}
		return null;
	}
	
	
	/**
	 * Add the given boulder to the set of boulders that this world contains.
	 * 
	 * @param	boulder
	 * 			The boulder to be added.
	 * 
	 * @post	This world has the given boulder as one of its boulders.
	 * 			| new.hasAsBoulder(boulder)
	 * 
	 * @post	The given boulder references this world as its world.
	 * 			| (new boulder).getWorld() == this
	 * 
	 * @throws	IllegalArgumentException
	 * 			This world cannot have the given boulder as one of its boulders.
	 * 			| !canHaveAsBoulder(boulder)
	 * 
	 * @throws	IllegalArgumentException
	 * 			The given boulder already references some world.
	 */
	public void addBoulder(Boulder boulder)  throws IllegalArgumentException {
		if (!canHaveAsItem(boulder))
			throw new IllegalArgumentException();
		if (boulder.getWorld() != this)
			throw new IllegalArgumentException();
		this.boulders.add(boulder);
		this.toAddToTimeSubjects.add(boulder);
		boulder.setWorld(this);
	}
	
	
	/**
	 * Remove the given boulder from this world.
	 * 
	 * @param	boulder
	 * 			The boulder to remove.
	 * 
	 * @post	This world does not contain the given boulder.
	 * 			| !new.hasAsBoulder(boulder)
	 * 
	 * @post	If this world contains the given boulder, the boulder is no
	 * 			longer attached to any world.
	 * 			| if (hasAsBoulder(boulder))
	 * 			| 	then ( (new boulder).getWorld() == null)
	 */
	public void removeBoulder(Boulder boulder) {
		if (hasAsItem(boulder)) {
			this.boulders.remove(boulder);
			this.toRemoveFromTimeSubjects.add(boulder);
			boulder.setWorld(null);
		}
	}
	
	
	/**
	 * Set collecting references to boulders that this world contains.
	 * 
	 * @invar	the set of boulders is effective.
	 * 			| boulders != null
	 * 
	 * @invar	Each element in the set references a boulder that this world
	 * 			can have as boulder.
	 * 			| for each log in boulders:
	 * 			| 		canHaveAsBoulder(boulder)
	 * 
	 * @invar	Each boulder in the set references this world as their world.
	 * 			| for each boulder in boulders:
	 * 			|		boulder.getWorld() == this
	 */
	private final Set<Boulder> boulders = new HashSet<Boulder>();
	
	
	
	/* *********************************************************
	 * 
	 * 							TIMESUBJECTS
	 *
	 **********************************************************/
	
	
	
	private final List<TimeSubject> timeSubjects = new ArrayList<TimeSubject>();
	
	
	private final List<TimeSubject> toAddToTimeSubjects = new ArrayList<TimeSubject>();
	private final List<TimeSubject> toRemoveFromTimeSubjects = new ArrayList<TimeSubject>();
	
	
	private void updateTimeSubjects() {
		for (TimeSubject ts: this.toAddToTimeSubjects) {
			timeSubjects.add(ts);
		}
		this.toAddToTimeSubjects.clear();
		
		for (TimeSubject ts: this.toRemoveFromTimeSubjects) {
			if (timeSubjects.contains(ts))
				timeSubjects.remove(ts);
		}
		
		this.toRemoveFromTimeSubjects.clear();
		timeSubjects.removeAll(Collections.singleton(null));
	}
	
	/* *********************************************************
	 * 
	 * 							TERRAIN
	 *
	 **********************************************************/
	
	
	
	public boolean isSolidConnectedToBorder(int x, int y, int z) {
		return getConnectedToBorderChecker().isSolidConnectedToBorder(x, y, z);
	}
	
	
	public void collapse(Coordinate coordinate, double diceTreshold) {
		double dice = random.nextDouble();
		if (dice < diceTreshold) {
			if (getCubeTypeAt(coordinate) == TerrainType.ROCK) {
				setCubeTypeAt(coordinate, TerrainType.AIR);
				Boulder boulder = new Boulder(coordinate, this);
				addBoulder(boulder);
			}
			else if (getCubeTypeAt(coordinate) == TerrainType.TREE) {
				setCubeTypeAt(coordinate, TerrainType.AIR);
				Log log = new Log(coordinate, this);
				addLog(log);
			}
		}
	}
	
	
	@Basic @Immutable
	private ConnectedToBorder getConnectedToBorderChecker() {
		return this.connectedToBorderChecker;
	}
	
	
	private void caveInAll() {
		for (int x = 0; x < terrainTypes.length; x++) {
			for (int y = 0; y < terrainTypes[0].length; y++) {
				for (int z = 0; z < terrainTypes[0][0].length; z++) {
					int[] coordinate = new int[]{x,y,z};
					Coordinate cubeCoordinate = new Coordinate(x, y, z);
					if(!getCubeTypeAt(cubeCoordinate).isPassable() && !isSolidConnectedToBorder(x, y, z)) {

						collapse(cubeCoordinate, 0.25);
						this.modelListener.notifyTerrainChanged(x, y, z);
						caveIn(coordinate);
					}
					
					
				}
			}
		}
	}
	
	private void caveIn(int[] coordinate) {
		List<int[]> changed = getConnectedToBorderChecker().changeSolidToPassable(coordinate[0],
				coordinate[1], coordinate[2]);
		for (int[] cube: changed) {
			Coordinate cubeCoordinate = new Coordinate(cube[0], cube[1], cube[2]);
			if (!getCubeTypeAt(cubeCoordinate).isPassable() ) {
				collapse(cubeCoordinate, 0.25);
				this.modelListener.notifyTerrainChanged(cube[0], cube[1], cube[2]);
				caveIn(cube);
			}
		}
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
		
		public static boolean contains(TerrainType test) {

		    for (TerrainType c : TerrainType.values()) {
		        if (c.equals(test)) {
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
	
	
	/* *********************************************************
	 * 
	 * 						DESTRUCTOR
	 *
	 **********************************************************/

	
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
	 * 
	 * @effect	Each non-terminated log in this world is removed from
	 * 			this world.
	 * 			| for each log in getAllLogs():
	 * 			|		if (!log.isTerminated())
	 * 			|			then this.removeLog(log)
	 * 
	 * @effect	Each non-terminated boulder in this world is removed from
	 * 			this world.
	 * 			| for each boulder in getAllBoulders():
	 * 			|		if (!boulder.isTerminated())
	 * 			|			then this.removeBoulder(boulder)
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
				this.factions.remove(faction);
			}
		}
		
		
		for (Log log: this.logs) {
			if (!log.isTerminated()) {
				log.setWorld(null);
				this.logs.remove(log);
			}
		}
		for (Boulder boulder: this.boulders) {
			if (!boulder.isTerminated()) {
				boulder.setWorld(null);
				this.boulders.remove(boulder);
			}
		}
		
		this.isTerminated = true;
	}
	
	
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	
	private boolean isTerminated = false;
	
	
}
