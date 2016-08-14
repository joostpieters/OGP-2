package hillbillies.model.expression;

import hillbillies.model.Coordinate;
import hillbillies.model.Unit;

public class NextToPosition extends Expression<Coordinate> {

	private final Expression<Coordinate> position;
	
	public NextToPosition(Expression<Coordinate> position) {
		this.position = position;
	}
	
	
	@Override 
	public Coordinate evaluate() {
		Coordinate cube;
		Unit currentUnit = getTask().getAssignedUnit();
		do {
			cube = currentUnit.getWorld().getRandomNeighbouringCube(this.position.evaluate());
		} while (!currentUnit.canHaveAsPosition(cube));
		return cube;
	}
	
	
	

}
