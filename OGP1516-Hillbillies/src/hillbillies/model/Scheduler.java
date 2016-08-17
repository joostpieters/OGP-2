package hillbillies.model;

import java.util.*;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;


public class Scheduler {
	
	
	private final List<Task> tasks = new ArrayList<Task>();
	
	
	public Scheduler(/*Faction faction*/) {
		//setFaction(faction);
	}
	
	
	private void sortTasks() {
		this.tasks.removeIf(Objects::isNull);
		this.tasks.sort(new PriorityComparator());
	}
	
	
	public Task getHighestPriorityTask() {
		sortTasks();
		for (int i=0; i<this.tasks.size(); i++) {
			if (!this.tasks.get(i).beingExecuted() )
				return this.tasks.get(i);
		}
		return null;
	}
	
	
	public boolean hasUnassignedTask() {
		if (this.tasks.size() > 0)
			for (int i=0; i<this.tasks.size(); i++) {
				if (!this.tasks.get(i).beingExecuted() )
					return true;
			}
		return false;
	}
	
	
	public List<Task> getAllTasks() {
		return new ArrayList<Task>(this.tasks);
	}

	
	public void addTask(Task task) throws IllegalArgumentException {
		if (!canHaveAsTask(task))
			throw new IllegalArgumentException();
		this.tasks.add(task);
		task.addScheduler(this);
		sortTasks();
		/*try {
			throw new IllegalArgumentException();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	
	public void removeTask(Task task) {
		if (hasAsTask(task)) {
			this.tasks.remove(task);
		}
		sortTasks();
	}
	
	
	public void replace(Task original, Task replacement) {
		if (this.tasks.contains(original)) {
			if (original.beingExecuted()) {
				original.getAssignedNit().stopExecutingTask();
				//original.getAssignedNit().deleteTask();
				original.setBeingExecuted(false);
				original.setAssignedNit(null);
			}
			
			this.tasks.set(this.tasks.indexOf(original), replacement);
			sortTasks();
		}
		
	}

	
	public boolean areTasksPartOf(Collection<Task> tasks) {
		if (this.tasks.containsAll(tasks))
				return true;
		return false;
	}

	
	public Iterator<Task> getIterator() {
		return getAllTasks().iterator();
	}

	
	public boolean canHaveAsTask(Task task) {
		return (task != null );
	}

	
	public boolean hasAsTask(Task task) {
		return (this.tasks.contains(task));
	}

	
	
	
	
}
