package hillbillies.model.expression;

import hillbillies.model.Coordinate;

public class HerePosition extends Expression<Coordinate> {
	
	
	
	public HerePosition() {
		
	}
	
	
	@Override 
	public Coordinate evaluate() {
		
		return getTask().getAssignedUnit().getCoordinate();
	}
	
	
	
	

}
