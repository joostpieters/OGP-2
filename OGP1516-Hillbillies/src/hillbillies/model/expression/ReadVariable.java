package hillbillies.model.expression;


public class ReadVariable extends Expression<Object> {
	
	private final String variableName;

	public ReadVariable(String variableName) {
		this.variableName = variableName;
	}
	
	@Override
	public Object evaluate() {
		return this.getTask().getVariableFromName(this.variableName);
	}

}
