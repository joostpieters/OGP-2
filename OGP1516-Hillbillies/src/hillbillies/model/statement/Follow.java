package hillbillies.model.statement;

import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;

public class Follow extends Statement {
	
	private final Expression<Unit> unit;

	public Follow(Expression<Unit> unit) {
		this.unit = unit;
	}
	
	@Override
	public void execute() {
		
	}

}
