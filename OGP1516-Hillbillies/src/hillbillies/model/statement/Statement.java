package hillbillies.model.statement;

import hillbillies.model.Task;

public abstract class Statement {
	
	
	private Task task;
	
	protected Task getTask() {
		return this.task;
	}
	
	public void setTask(Task task) {
		this.task = task;
		try {
			throw new IllegalArgumentException();
		} catch (RuntimeException e ){
			e.printStackTrace();
		}
		System.out.println("set task");
	}
	
	public abstract void execute();
	

	/*public int size() {
		// TODO Auto-generated method stub
		return 0;
	}*/
	
	//public abstract String toString();
	
	@Override
	public String toString() {
		return this.getClass().getName();
	}

}
