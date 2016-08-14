package hillbillies.model.expression;

import hillbillies.model.Unit;

public class This extends Expression<Unit> {
	
	public This() {
		
	}
	
	@Override
	public Unit evaluate() {
		return getTask().getAssignedUnit();
	}
	
}
