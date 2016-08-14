package hillbillies.model.expression;


public class Not extends Expression<Boolean> {
	
	
	private final Expression<Boolean> expression;

	public Not(Expression<Boolean> expression) {
		this.expression = expression;
	}
	
	@Override
	public Boolean evaluate() {
		return !this.expression.evaluate();
	}

}
