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
		this.unit.setTask(getTask());
		
		Unit currentUnit = getTask().getAssignedUnit();
		Unit unitToFollow = this.unit.evaluate();
		
		// while
		if (! (currentUnit.getCoordinate().equals(unitToFollow.getCoordinate()) 
				|| currentUnit.getWorld().isNeighbouring(currentUnit.getCoordinate(), 
						unitToFollow.getCoordinate())  
				|| unitToFollow.isTerminated()  ) )
		{
			currentUnit.moveTo(unitToFollow.getCoordinate());
		}
		
		
	}

}
