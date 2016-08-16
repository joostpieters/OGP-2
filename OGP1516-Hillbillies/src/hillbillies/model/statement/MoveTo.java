package hillbillies.model.statement;

import hillbillies.model.Coordinate;
import hillbillies.model.expression.Expression;

public class MoveTo extends Statement {

	private final Expression<Coordinate> position;
	//private final Coordinate position;
	
	public MoveTo(Expression<Coordinate> position) {
		this.position = position;
		//this.position2 = null;
		
		//System.out.println("");
		//System.out.println(this.position.toString());
	}
	
	/*
	public MoveTo(Coordinate position) {
		this.position2 = position;
		this.position = null;
	}*/
	
	@Override
	public void execute() {
		if (position != null) {
			this.position.setTask(getTask());
			//System.out.println("");
			//System.out.println(this.position.evaluate().toString());
			//System.out.println(this.position.toString());
			getTask().getAssignedUnit().moveTo(this.position.evaluate());
		} else {
			//this.position.setTask(getTask());
			
			//getTask().getAssignedUnit().moveTo(this.position2);
		}
	}
}
