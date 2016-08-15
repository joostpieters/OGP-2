package hillbillies.model.expression;

import hillbillies.model.Unit;


public class IsEnemy extends Expression<Boolean> {
	
	
	private final Expression<Unit> unit2;

	
	public IsEnemy(Expression<Unit> unit2) {
		this.unit2 = unit2;
	}
	
	
	@Override
	public Boolean evaluate() {
		this.unit2.setTask(getTask());
		
		return (unit2.evaluate().getFaction() != getTask().getAssignedUnit().getFaction());
	}
	
}
