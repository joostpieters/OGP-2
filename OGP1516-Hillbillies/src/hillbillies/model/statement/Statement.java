package hillbillies.model.statement;

import hillbillies.model.Task;

public abstract class Statement {
	
	
	private Task task;
	private Statement nextStatement;
	
	protected Task getTask() {
		return this.task;
	}
	
	public void setTask(Task task) {
		this.task = task;
//		try {
//			throw new IllegalArgumentException();
//		} catch (RuntimeException e ){
//			e.printStackTrace();
//		}
//		System.out.println("set task");
	}
	
	
	public void setNextStatement(Statement nextStatement) {
		this.nextStatement = nextStatement;
	}
	
	public Statement getNextStatement() {
		return this.nextStatement;
	}
	
	public abstract void execute();
	

	/*public int size() {
		// TODO Auto-generated method stub
		return 0;
	}*/
	
	
	@Override
	public String toString() {
		return this.getClass().getName();
	}

}
