package hillbillies.model.expression;

import hillbillies.model.Coordinate;

public class SelectedPosition extends Expression<Coordinate> {
	
	
	public SelectedPosition() {
		
	}
	
	
	@Override 
	public Coordinate evaluate() {
		return getTask().getCube();
	}
	
	
	
	
	
}