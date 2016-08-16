package hillbillies.model.expression;

import hillbillies.model.Unit;

public class Friend extends Expression<Unit> {
	
	
	public Friend() {
		
	}

	@Override
	public Unit evaluate() {
		Unit friendNull = null;
		Unit currentUnit = getTask().getAssignedUnit();
		for (Unit friend: currentUnit.getWorld().getAllUnits()) {
			if (friend.getFaction() == currentUnit.getFaction())
				return friend;
		}
		return friendNull;
	}
	
	
}
