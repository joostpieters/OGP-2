package hillbillies.model.expression;

import hillbillies.model.Coordinate;
import hillbillies.model.Unit;

public class NextToPosition extends Expression<Coordinate> {

	private final Expression<Coordinate> position;
	
	public NextToPosition(Expression<Coordinate> position) {
		this.position = position;
		//this.position.setTask(getTask());
	}
	
	
	@Override 
	public Coordinate evaluate() {
		this.position.setTask(getTask());
		
		Coordinate cube;
		Unit currentUnit = getTask().getAssignedUnit();
		int i = 0;
		do {
			cube = currentUnit.getWorld().getRandomNeighbouringCube(this.position.evaluate());
			i++;
		} while ((!currentUnit.canHaveAsPosition(cube) || currentUnit.wouldFall(cube)) && i < 30);
		return cube;
	}
	
	
	

}
