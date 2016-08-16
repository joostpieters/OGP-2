package hillbillies.model.expression;

import hillbillies.model.Coordinate;
import hillbillies.model.Unit;

public class PositionOf extends Expression<Coordinate> {
	
	
	private final Expression<Unit> unit2;

	public PositionOf(Expression<Unit> unit2) {
		this.unit2 = unit2;
		
	}
	
	
	@Override 
	public Coordinate evaluate() {
		this.unit2.setTask(getTask());
		
		return unit2.evaluate().getCoordinate();
	}
	
	

}
