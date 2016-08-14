package hillbillies.model;

import java.util.*;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.model.statement.*;
import hillbillies.model.expression.*;

public class Task {
	
	
	private final HashSet<Scheduler> schedulersForTask = new HashSet<Scheduler>();
	private final String name;
	private int priority;
	private Unit unit;
	private boolean beingExecuted;
	private Statement activities;
	private Coordinate cube;
	private Map<String, Object> variables = new HashMap<String, Object>();

	
	public Task(String name, int priority, Statement activities, int[] cube ){
		if (!isValidName(name)) {
			throw new IllegalNameException(name);
		}
		this.name = name;
		
		setPriority(priority);
		setCube(new Coordinate(cube[0], cube[1], cube[2]));
		setActivities(activities);
		
		//setVariables(new HashMap<String, Expression<?>>());
		
		//setActivities(activities);
	}
	

	/*private void setActivities(Statement activities) {
		// TODO Auto-generated method stub
		
	}*/


	/*private void setVariables(HashMap<String, Expression<?>> hashMap) {
		// TODO Auto-generated method stub
		
	}*/
	
	
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
	
	
	public Statement getActivities() {
		return this.activities;
	}
	
	
	public static boolean isValidActivities(Statement activities) {
		return (activities != null /*&& activities.size() > 0*/);
	}
	
	
	private void setActivities(Statement activities) throws IllegalArgumentException {
		if (!isValidActivities(activities))
			throw new IllegalArgumentException();
		this.activities = activities;
	}


	@Raw @Basic
	public Coordinate getCube() {
		return this.cube;
	}
	
	
	@Raw
	private boolean canHaveAsCube(Coordinate coordinate) {
		return this.getSchedulerForTask().getFaction().getWorld().canHaveAsCoordinates(coordinate);
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
		this.unit = unit;
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
	private boolean beingExecuted() {
		return this.beingExecuted;
	}
	
	
	@Raw
	private void setExecuted(boolean value) {
		this.beingExecuted = value;
	}
	
	
	
	@Override
	public String toString() {
		return getName() + ", priority: " + getPriority() + ", activities: " + 
					getActivities().toString() + ", cube: " + getCube().toString()
					+ ", executing: " + beingExecuted();
	}
	
	
	
	
	
	

}