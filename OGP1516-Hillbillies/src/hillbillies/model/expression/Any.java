package hillbillies.model.expression;

import hillbillies.model.Unit;

public class Any extends Expression<Unit> {
	
	public Any() {
		
	}
	
	
	@Override
	public Unit evaluate() {
		Unit anyUnit = null;
		for (Unit unit2: getTask().getAssignedUnit().getWorld().getAllUnits()) {
			if (getTask().getAssignedUnit() != unit2) {
				return unit2;
			}
		}
		return anyUnit;
	}
	
}
