package hillbillies.model.statement;

import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;

public class Attack extends Statement {
	
	private final Expression<Unit> victim;

	public Attack(Expression<Unit> unit) {
		this.victim = unit;
		
	}
	
	@Override
	public void execute() {
		this.victim.setTask(getTask());
		
		getTask().getAssignedUnit().attack(this.victim.evaluate());
	}
}
