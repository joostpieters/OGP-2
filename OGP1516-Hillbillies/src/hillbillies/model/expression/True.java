package hillbillies.model.expression;


public class True extends Expression<Boolean> {
	
	public True() {
		
	}
	
	
	@Override
	public Boolean evaluate() {
		return true;
	}

}
