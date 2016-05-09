package hillbillies.model;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;
import ogp.framework.util.ModelException;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Model;

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
	
	private final Set<Unit> units = new HashSet<Unit>();
	
	private final Set<Faction> factions = new TreeSet<Faction>(new NbUnitsComparator());
	
	private final Set<Item> logs = new HashSet<Item>();
	private final Set<Item> boulders = new HashSet<Item>();
	
	
	private final ConnectedToBorder connectedToBorderChecker;
	
	private static final Random random = new Random();
	
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
	
	public Set<Item> getObjectsAt(int x, int y, int z) {
		//Set<Item> items = new HashSet<Item>();
		//Set<Item> items = this.boulders;
		return this.boulders;
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
	
	public Unit spawnUnit() {
		if (getUnits().size() < 100) {
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
	
	public void addUnit(Unit unit) {
		// CONDITIONS IVM ASSOCIATIONS ETC CHECKEN
		
		if (!unit.equals(null))
			this.units.add(unit);
	}
	
	public Set<Unit> getUnits() {
		return new HashSet<Unit>(this.units);
	}
	
	
	public Set<Faction> getActiveFactions() {
		return new TreeSet<Faction>(this.factions);
	}
	
	public Set<Item> getBoulders() {
		return new HashSet<Item>(this.boulders);
	}
	
	public Set<Item> getLogs() {
		return new HashSet<Item>(this.logs);
	}
	
	
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
