package hillbillies.model.expression;

import hillbillies.model.Unit;

public class IsFriend extends Expression<Boolean> {


	private final Expression<Unit> unit2;

	
	public IsFriend(Expression<Unit> unit2) {
		this.unit2 = unit2;
	}
	
	
	@Override
	public Boolean evaluate() {
		return (unit2.evaluate().getFaction() == getTask().getAssignedUnit().getFaction());
	}

}
