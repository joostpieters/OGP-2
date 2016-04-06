package hillbillies.model;

import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;

public class World {
	
	
	public World(int[][][] terrainTypes, TerrainChangeListener modelListener) {
		int[][][] world
	}
	
	
	
	public enum Feature {
		AIR(true, 0),
		ROCK(false, 1),
		TREE(false, 2),
		WORKSHOP(true, 3);
		
		private Feature(boolean passable, int number) {
			this.passable = passable;
			this.number = number;
		}
		
		public boolean isPassable() {
			return this.passable;
		}
		
		private final boolean passable;
		private final int number;
		
		
	}
}
