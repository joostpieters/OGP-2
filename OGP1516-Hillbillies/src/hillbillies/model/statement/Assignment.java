package hillbillies.model.statement;

import hillbillies.model.expression.Expression;

public class Assignment extends Statement {
	
	private final String variableName;
	private final Expression<?> value;

	public Assignment(String variableName, Expression<?> value) {
		this.variableName = variableName;
		this.value = value;
	}
	
	
	@Override
	public void execute() {
		getTask().addVariableName(this.variableName, this.value.evaluate());
	}

}
