package hillbillies.model.statement;

import hillbillies.model.expression.Expression;

public class Print extends Statement {
	
	private final Expression<?> value;

	public Print(Expression<?> value) {
		this.value = value;
	}
	
	@Override
	public void execute() {
		System.out.println(this.value.evaluate().toString());
	}

}
