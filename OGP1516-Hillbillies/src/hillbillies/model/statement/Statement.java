package hillbillies.model.statement;

import hillbillies.model.Task;

public abstract class Statement {
	
	
	private Task task;
	
	protected Task getTask() {
		return this.task;
	}
	
	public void setTask(Task task) {
		this.task = task;
	}
	
	public abstract void execute();
	

	/*public int size() {
		// TODO Auto-generated method stub
		return 0;
	}*/

}
