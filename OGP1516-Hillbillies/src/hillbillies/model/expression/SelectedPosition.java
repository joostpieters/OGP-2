package hillbillies.model.expression;

import hillbillies.model.Coordinate;

public class SelectedPosition extends Expression<Coordinate> {
	
	//private final Expression<Coordinate> position;
	
	public SelectedPosition() {
		//this.position = position;
		//this.position.setTask(getTask());
	}
	
	
	@Override 
	public Coordinate evaluate() {
		return getTask().getCube();
	}
	
	
	
	
	
}