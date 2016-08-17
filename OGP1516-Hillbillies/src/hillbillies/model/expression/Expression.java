package hillbillies.model.expression;

import hillbillies.model.Task;

public abstract class Expression<Type> {
	
	private Task task;
	
	protected Task getTask() {
		return this.task;
	}
	
	public void setTask(Task task) {
		this.task = task;
	}
	
	public abstract Type evaluate();

}
