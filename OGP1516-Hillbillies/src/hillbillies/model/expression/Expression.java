package hillbillies.model.expression;

import hillbillies.model.Task;

public abstract class Expression<Type> {
	
	private Task task;
	
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
	
	public abstract Type evaluate();

}
