package hillbillies.model.statement;

import hillbillies.model.expression.Expression;

public class If extends Statement {
	
	private final Expression<Boolean> condition;
	private final Statement ifBody;
	private final Statement elseBody;

	public If(Expression<Boolean> condition, Statement ifBody, Statement elseBody) {
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
		
		
	}
	
	
	@Override
	public void execute() {
		this.condition.setTask(getTask());
		this.ifBody.setTask(getTask());
		if (this.elseBody != null)
			this.elseBody.setTask(getTask());
		
		if (this.condition.evaluate()) {
			this.ifBody.execute();
		} else if (this.elseBody != null) {
			this.elseBody.execute();
		}
	}

}
