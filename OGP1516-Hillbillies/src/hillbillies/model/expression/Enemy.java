package hillbillies.model.expression;

import hillbillies.model.Unit;

public class Enemy extends Expression<Unit> {
	
	public Enemy() {
		
	}

	@Override
	public Unit evaluate() {
		Unit enemyNull = null;
		Unit currentUnit = getTask().getAssignedUnit();
		for (Unit enemy: currentUnit.getWorld().getAllUnits()) {
			if (enemy.getFaction() != currentUnit.getFaction())
				return enemy;
		}
		return enemyNull;
	}
}
