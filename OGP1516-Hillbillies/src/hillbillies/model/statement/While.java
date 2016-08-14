package hillbillies.model.statement;

import hillbillies.model.expression.Expression;

public class While extends Statement {
	
	
	private final Expression<Boolean> condition;
	private final Statement body;

	public While(Expression<Boolean> condition, Statement body) {
		this.condition = condition;
		this.body = body;
	}
	
	@Override
	public void execute() {
		while (this.condition.evaluate()) {
			this.body.execute();
		}
	}

}
