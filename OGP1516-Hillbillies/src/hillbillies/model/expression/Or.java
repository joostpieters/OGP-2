package hillbillies.model.expression;


public class Or extends Expression<Boolean> {

	
	private final Expression<Boolean> left;
	private final Expression<Boolean> right;
	

	public Or(Expression<Boolean> left, Expression<Boolean> right) {
		this.left = left;
		this.right = right;
		
	}
	
	@Override
	public Boolean evaluate() {
		this.left.setTask(getTask());
		this.right.setTask(getTask());
		
		return (this.left.evaluate() || this.right.evaluate());
	}
	
}
