package hillbillies.model.statement;

import hillbillies.model.expression.Expression;

public class While extends ComposedStatement {
	
	
	private final Expression<Boolean> condition;
	private final Statement body;

	public While(Expression<Boolean> condition, Statement body) {
		this.condition = condition;
		this.body = body;
		
	}
	
	@Override
	public void execute() {
		
		this.condition.setTask(getTask());
		this.body.setTask(getTask());
		
		
	}
	
	@Override
	public Statement getNextStatement()
	{
		if (!this.condition.evaluate())
			return this.getNextStatement();
		return this.body;
	}
	
	
	@Override
	public void setNextToExecStatement(Statement nextToExecStatement) {
		
		super.setNextToExecStatement(this.body);
		
		this.body.setNextToExecStatement(nextToExecStatement);
	}


}
