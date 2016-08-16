package hillbillies.model.expression;

import hillbillies.model.Unit;

public class IsAlive extends Expression<Boolean> {

	private final Expression<Unit> unit2;
	
	public IsAlive(Expression<Unit> unit2) {
		this.unit2 = unit2;
		
	}
	
	
	@Override
	public Boolean evaluate() {
		this.unit2.setTask(getTask());
		
		return !this.unit2.evaluate().isTerminated();
	}

}
