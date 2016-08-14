package hillbillies.model.expression;

import hillbillies.model.Coordinate;
import hillbillies.model.Unit;
import hillbillies.model.World.TerrainType;

public class WorkshopPosition extends Expression<Coordinate> {
	
	public WorkshopPosition() {
		
	}
	
	
	@Override 
	public Coordinate evaluate() {
		Coordinate workshopCoordinateNull = null;
		
		Coordinate coordinate = null;
		
		Unit currentUnit = getTask().getAssignedUnit();
		int x = currentUnit.getWorld().getNbCubesX();
		int y = currentUnit.getWorld().getNbCubesY();
		int z = currentUnit.getWorld().getNbCubesZ();
		
		for (int i=0; i<x; i++) {
			for (int j=0; j<y; j++) {
				for (int k=0; k<z; k++) {
					coordinate = new Coordinate(i, j, k);
					if (currentUnit.getWorld().getCubeTypeAt(coordinate) == TerrainType.WORKSHOP)
						return coordinate;
				}
			}
		}
		
		return workshopCoordinateNull;
	}
	
}
