package hillbillies.model;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Model;

public class World {
	
	private final static int maxUnits = 100;
	private final static int maxFactions = 100;
	
	/**
	 * Variable registering the length of a cube of the game world.
	 */
	private final static double cubeLength = 1.0;
	
	@Raw
	public World(int[][][] terrainTypes, TerrainChangeListener modelListener) 
							throws IllegalArgumentException {
		if (!isValidTerrainTypes(terrainTypes)) 
			throw new IllegalArgumentException();
		this.terrainTypes = terrainTypes;
		this.modelListener = modelListener;
	}
	
	private boolean isValidTerrainTypes(int[][][] terrainTypes) {
		/*if (! ((terrainTypes.length == terrainTypes[0].length) && 
				(terrainTypes[0].length	== terrainTypes[0][0].length))) {
			return false;
		}*/
		//int a = terrainTypes.length;
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
	
	private int[][][] terrainTypes;
	private final TerrainChangeListener modelListener;
	
	
	
	
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
