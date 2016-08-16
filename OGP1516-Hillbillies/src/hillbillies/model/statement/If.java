package hillbillies.model.statement;

import java.util.Iterator;

import hillbillies.model.expression.Expression;

public class If extends ComposedStatement {
	
	private final Expression<Boolean> condition;
	private final Statement ifBody;
	private final Statement elseBody;

	public If(Expression<Boolean> condition, Statement ifBody, Statement elseBody) {
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
		
		
	}
	
	public Statement getIfBody() {
		return this.ifBody;
	}
	
	public Expression<Boolean> getCondition() {
		return this.condition;
	}
	
	public Statement getElse() {
		return this.elseBody;
	}
	
	
	@Override
	public void execute() {
		this.condition.setTask(getTask());
		this.ifBody.setTask(getTask());
		
		if (this.elseBody != null)
			this.elseBody.setTask(getTask());
		
	}
	
	
	
	@Override
	public void setNextToExecStatement(Statement nextToExecStatement) {
		super.setNextToExecStatement(this.getIfBody());
		if (this.getElse() == null) {
			this.getIfBody().setNextToExecStatement(nextToExecStatement);
		} else {
			this.getIfBody().setNextToExecStatement(this.getElse());
			this.getElse().setNextToExecStatement(nextToExecStatement);
		}
	}
	
	
	@Override
	public Statement getNextStatement () {
		if (getCondition().evaluate()) {
			return getIfBody();
		} else if (getElse() == null) {
			return getNextStatement();
		} else {
			return getElse();
		}
	}
	
	
	@Override
	public void setNextStatement(Statement nextStatement) {
		super.setNextStatement(nextStatement);
		getIfBody().setNextStatement(nextStatement);
		if (getElse() != null) {
			getElse().setNextStatement(nextStatement);
		}
	}
	
	/*
	@Override
	public void setTask (Task task) {
		super.setTask(task);
		this.ifBody.setTask(task);
		if (this.elseBody != null) {
			this.elseBody.setTask(task);
		}
	}*/
}
