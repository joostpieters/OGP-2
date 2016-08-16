package hillbillies.model.expression;

import hillbillies.model.Boulder;
import hillbillies.model.Coordinate;

public class BoulderPosition extends Expression<Coordinate> {
	
	
	public BoulderPosition() {
		
	}
	
	
	@Override 
	public Coordinate evaluate() {
		Coordinate boulderCoordinateNull = null;
		for (Boulder boulder: getTask().getAssignedUnit().getWorld().getAllBoulders()) {
			return boulder.getCoordinate();
		}
		return boulderCoordinateNull;
	}
	
}
