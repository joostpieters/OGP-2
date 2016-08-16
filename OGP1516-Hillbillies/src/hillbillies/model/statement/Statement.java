package hillbillies.model.statement;


import hillbillies.model.Task;

public abstract class Statement {
	
	private Statement nextStatement = null;
	
	private Statement nextToExecStatement = null;
	
	private ComposedStatement enclosingStatement = null;
	private Task task;
	
	private boolean isCompleted = false;
	
	
	public boolean isCompleted() {
		return this.isCompleted;
	}
	
	
	protected Task getTask() {
		return this.task;
	}
	
	public void setTask(Task task) {
		this.task = task;
	}
	
	
	public Statement getNextToExecStatement() {
		return this.nextToExecStatement;
	}
	
	public void setNextToExecStatement(Statement nextToExecStatement) {
		this.nextToExecStatement = nextToExecStatement;
	}
	
	public Statement getNextStatement() {
		return this.nextStatement;
	}
	
	public void setNextStatement(Statement stmt) {
		this.nextStatement = stmt;
	}
	
	public ComposedStatement getEnclosingStatement() {
		return this.enclosingStatement;
	}
	
	public void setEnclosingStatement(ComposedStatement enclosingStatement) {
		this.enclosingStatement = enclosingStatement;
	}
	
	
	public abstract void execute();
	

	
	@Override
	public String toString() {
		return this.getClass().getName();
	}
	
	
}
