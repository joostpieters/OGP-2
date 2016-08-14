package hillbillies.model.statement;

import hillbillies.model.Coordinate;
import hillbillies.model.expression.Expression;

public class Work extends Statement {
	
	private final Expression<Coordinate> position;

	public Work(Expression<Coordinate> position) {
		this.position = position;
	}
	
	
	@Override
	public void execute() {
		getTask().getAssignedUnit().workAt(this.position.evaluate());
	}
}
