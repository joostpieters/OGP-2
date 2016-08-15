package hillbillies.model;

import java.util.*;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.model.statement.*;
import hillbillies.model.expression.*;

public class Task {
	
	
	private final HashSet<Scheduler> schedulersForTask = new HashSet<Scheduler>();
	private final String name;
	private int priority;
	private Unit unit = null;
	private boolean beingExecuted;
	private Statement actions;
	private Coordinate cube;
	private final Map<String, Object> variables = new HashMap<String, Object>();
	private boolean isCompleted = false;
	private Statement executingActivity;

	
	public Task(String name, int priority, Statement statement, int[] cube ){
		if (!isValidName(name)) {
			throw new IllegalNameException(name);
		}
		this.name = name;
		
		setPriority(priority);
		setCube(new Coordinate(cube[0], cube[1], cube[2]));
		setActions(statement);
		
		//getActions().setNextStatement(null);
		this.executingActivity = getActions();
		
		//getActions().setTask(this);
	}
	
	
	public void execute() {
		//setExecuted(true);
		if (beingExecuted() && getAssignedUnit() != null) {
			//setExecuted(true);
			
			//getActions().setTask(this);
			
			getActions().execute();
			
			setCompleted();
			
		}
	}
	
	
	@Basic
	public boolean isCompleted() {
		return this.isCompleted;
	}
	

	public void setCompleted() {
		this.isCompleted = true;
	}
	
	
	public Map<String, Object> getVariables() {
		return new HashMap<String, Object>(this.variables);
	}
	
	
	public Object getVariableFromName(String name) {
		if (this.variables.containsKey(name)) {
			return this.variables.get(name);
		}
		return null;
	}
	
	
	public void addVariableName(String name, Object object) {
		// TODO valid name, object?
		this.variables.put(name, object);
	}
	
	
	public Statement getActions() {
		return this.actions;
	}
	
	
	public static boolean isValidActions(Statement activities) {
		return (activities != null /*&& activities.size() > 0*/);
	}
	
	
	private void setActions(Statement actions) throws IllegalArgumentException {
		if (!isValidActions(actions))
			throw new IllegalArgumentException();
		this.actions = actions;
	}


	@Raw @Basic
	public Coordinate getCube() {
		return this.cube;
	}
	
	
	@Raw
	private boolean canHaveAsCube(Coordinate coordinate) {
		return true;
				//this.getSchedulerForTask().getFaction().getWorld().canHaveAsCoordinates(coordinate);
	}

	
	@Raw
	private void setCube(Coordinate coordinate) {
		if (!canHaveAsCube(coordinate))
			throw new IllegalPositionException(coordinate);
		this.cube = coordinate;
	}


	@Raw @Basic @Immutable
	public String getName() {
		return this.name;
	}
	
	/**
	 * Check whether the given name is valid.
	 *  
	 * @param  name
	 *         The name to check.
	 * @return 
	 *       | result == true				
	*/
	@Immutable //@Raw
	public static boolean isValidName(String name) {
		return (name != null);
	}

	
	
	/**
	 * Check whether this Task has the given Scheduler as one of its
	 * Schedulers.
	 * 
	 * @param  Scheduler
	 *         The Scheduler to check.
	 */
	@Basic
	@Raw
	public boolean hasAsScheduler(@Raw Scheduler scheduler) {
		return this.schedulersForTask.contains(scheduler);
	}

	/**
	 * Check whether this Task can have the given Scheduler
	 * as one of its Schedulers.
	 * 
	 * @param  Scheduler
	 *         The Scheduler to check.
	 * @return True if and only if the given Scheduler is effective
	 *         and that Scheduler is a valid Scheduler for a Task.
	 *       | result ==
	 *       |   (Scheduler != null) &&
	 *       |   Scheduler.isValidTask(this)
	 */
	@Raw
	public boolean canHaveAsScheduler(Scheduler scheduler) {
		return (scheduler != null) && (scheduler.canHaveAsTask(this));
	}

	/**
	 * Check whether this Task has proper Schedulers attached to it.
	 * 
	 * @return True if and only if this Task can have each of the
	 *         Schedulers attached to it as one of its Schedulers,
	 *         and if each of these Schedulers references this Task as
	 *         the Task to which they are attached.
	 *       | for each Scheduler in Scheduler:
	 *       |   if (hasAsScheduler(Scheduler))
	 *       |     then canHaveAsScheduler(Scheduler) &&
	 *       |          (Scheduler.getTask() == this)
	 */
	public boolean hasProperSchedulers() {
		for (Scheduler scheduler : this.schedulersForTask) {
			if (!canHaveAsScheduler(scheduler))
				return false;
			if (!scheduler.hasAsTask(this))
				return false;
		}
		return true;
	}

	/**
	 * Return the number of Schedulers associated with this Task.
	 *
	 * @return  The total number of Schedulers collected in this Task.
	 *        | result ==
	 *        |   card({Scheduler:Scheduler | hasAsScheduler({Scheduler)})
	 */
	public int getNbSchedulers() {
		return this.schedulersForTask.size();
	}

	/**
	 * Add the given Scheduler to the set of Schedulers of this Task.
	 * 
	 * @param  Scheduler
	 *         The Scheduler to be added.
	 * @pre    The given Scheduler is effective and already references
	 *         this Task.
	 *       | (Scheduler != null) && (Scheduler.getTask() == this)
	 * @post   This Task has the given Scheduler as one of its Schedulers.
	 *       | new.hasAsScheduler(Scheduler)
	 */
	public void addScheduler(@Raw Scheduler scheduler) throws IllegalArgumentException {
		if ((scheduler == null) || (!scheduler.hasAsTask(this)) )
			throw new IllegalArgumentException();
		this.schedulersForTask.add(scheduler);
	}

	/**
	 * Remove the given Scheduler from the set of Schedulers of this Task.
	 * 
	 * @param  Scheduler
	 *         The Scheduler to be removed.
	 * @pre    This Task has the given Scheduler as one of
	 *         its Schedulers, and the given Scheduler does not
	 *         reference any Task.
	 *       | this.hasAsScheduler(Scheduler) &&
	 *       | (Scheduler.getTask() == null)
	 * @post   This Task no longer has the given Scheduler as
	 *         one of its Schedulers.
	 *       | ! new.hasAsScheduler(Scheduler)
	 */
	@Raw
	public void removeScheduler(Scheduler scheduler) {
		assert this.hasAsScheduler(scheduler) && (!scheduler.hasAsTask(this));
		this.schedulersForTask.remove(scheduler);
	}

	
	public Set<Scheduler> getSchedulersForTask() {
		return new HashSet<Scheduler>(this.schedulersForTask);
	}
	
	
	private Scheduler getSchedulerForTask() {
		for (Scheduler scheduler: getSchedulersForTask()) {
			return scheduler;
		}
		return null;
	}

	
	
	public boolean isWellFormed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Raw
	public Unit getAssignedUnit() {
		return this.unit;
	}
	
	
	public boolean isValidUnit(Unit unit) {
		if (beingExecuted()) return (unit != null);
		else return (unit == null);
	}

	@Raw
	public void setAssignedUnit(Unit unit) {
		if (!isValidUnit(unit))
			throw new IllegalArgumentException();
		if (beingExecuted() && getAssignedUnit() != null) 
			throw new RuntimeException();
		if (unit != null && unit.getAssignedTask() != this )
			throw new IllegalArgumentException();
		
		this.unit = unit;
		/*try {
			throw new IllegalArgumentException();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		System.out.println("Unit assigned to task");*/
		//unit.setAssignedTask(this);
	}


	@Basic @Raw
	public int getPriority() {
		return this.priority;
	}
	
	
	public static boolean isValidPriority(int priority) {
		return true;
	}
	
	
	@Raw
	private void setPriority(int priority) throws IllegalArgumentException {
		if (!isValidPriority(priority))
			throw new IllegalArgumentException();
		this.priority = priority;
		
	}

	
	@Raw
	public boolean beingExecuted() {
		return this.beingExecuted;
	}
	
	
	@Raw
	public void setBeingExecuted(boolean value) {
		this.beingExecuted = value;
	}
	
	
	
	@Override
	public String toString() {
		return getName() + ", priority: " + getPriority() + ", activities: " + 
					getActions().toString() + ", cube: " + getCube().toString()
					+ ", executing: " + beingExecuted();
	}
	
	
	
	public void terminate() {
		if (getAssignedUnit() == null && !beingExecuted() && isCompleted()) {
			for (Scheduler scheduler: getSchedulersForTask()) {
				if (scheduler.getAllTasks().contains(this)) {
					scheduler.removeTask(this);
				}
				removeScheduler(scheduler);
			}
			this.isTerminated = true;
		}
	}
	
	
	@Raw @Basic
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	private boolean isTerminated = false;
	
	
}