package hillbillies.model.expression;


public class False extends Expression<Boolean> {
	
	
	public False() {
		
	}
	
	
	@Override
	public Boolean evaluate() {
		return false;
	}
	
	
}
