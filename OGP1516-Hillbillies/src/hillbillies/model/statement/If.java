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
		if (this.condition.evaluate()) {
			this.ifBody.execute();
		} else {
			this.elseBody.execute();
		}
	}

}
