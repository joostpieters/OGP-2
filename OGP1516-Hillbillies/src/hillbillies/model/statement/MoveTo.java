package hillbillies.model.statement;

import hillbillies.model.Coordinate;
import hillbillies.model.expression.Expression;

public class MoveTo extends Statement {

	private final Expression<Coordinate> position;
	
	public MoveTo(Expression<Coordinate> position) {
		this.position = position;
	}
	
	
	@Override
	public void execute() {
		getTask().getAssignedUnit().moveTo(this.position.evaluate());
	}
}
