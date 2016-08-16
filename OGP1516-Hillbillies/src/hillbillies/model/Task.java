package hillbillies.model;

import java.util.*;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.model.statement.*;
import hillbillies.model.expression.*;

public class Task {
	
	/**
	 * A set containing all the schedulers that have this task.
	 * 
	 * @invar	the set of schedulers is effective.
	 * 			| schedulersForTask != null
	 * 
	 * @invar	Each element in the set references a scheduler that this task
	 * 			can have as scheduler.
	 * 			| for each scheduler in schedulersForTask:
	 * 			| 		canHaveAsScheduler(scheduler)
	 * 
	 * @invar	Each scheduler in the set references this task as one
	 * 			of their tasks.
	 * 			| for each scheduler in schedulersForTask:
	 * 			|		scheduler.hasAsTask(this)
	 * 
	 */
	private final HashSet<Scheduler> schedulersForTask = new HashSet<Scheduler>();
	
	
	/**
	 * A variable registering the name of the task.
	 */
	private final String name;
	
	
	/**
	 * A variable registering the priority of the task.
	 */
	private int priority = 0;
	
	
	/**
	 * A variable registering unit this task is assigned to.
	 */
	private Nit unit = null;
	
	
	/**
	 * A variable registering whether the task is being executed.
	 */
	private boolean beingExecuted = false;
	
	
	/**
	 * A variable registering the statements of this task.
	 */
	private Statement actions = null;
	
	
	/**
	 * A variable registering the selected cube of this task.
	 */
	private Coordinate cube = null;
	
	
	/**
	 * A variable registering the assigned variables in this task.
	 */
	private final Map<String, Object> variables = new HashMap<String, Object>();
	
	
	/**
	 * A variable registering whether the task is completed.
	 */
	private boolean isCompleted = false;
	
	
	/**
	 * A variable keeping track of the currently executed activity of the task.
	 */
	private Statement executingActivity = null;
	
	
	
	/**
	 * Initialize this task with the given name, priority, statement and cube.
	 * 
	 * @param name
	 * 			
	 * @param priority
	 * 
	 * @param statement
	 * 
	 * @param cube
	 * 
	 * @post	The new tasks name is equal to the given name.
	 * 			| new.getName() == name
	 * 
	 * @effect	The new tasks priority is set to the given priority.
	 * 			| setPriority(priority)
	 * 
	 * @effect	The new tasks cube is set to the given cube.
	 * 			| setCube(cube)
	 * 
	 * @effect	The new tasks statement is set to the given statement.
	 * 			| setActions(statement)
	 * 
	 * @post	The new tasks executingActivity is set to its statement.
	 * 			| new.getExecutingActivity() = getActivity()
	 * 
	 * @effect	The task of the new tasks statements is set to this task.
	 * 			| getActions().setTask(this)
	 * 
	 * @throws	IllegalNameException
	 * 			The given name is not a valid name.
	 * 			| !isValidName(name)
	 * 
	 */
	public Task(String name, int priority, Statement statement, int[] cube ) throws IllegalNameException {
		if (!isValidName(name)) {
			throw new IllegalNameException(name);
		}
		this.name = name;
		
		setPriority(priority);
		setCube(new Coordinate(cube[0], cube[1], cube[2]));
		setActions(statement);
		
		setExecutingActivity(getActions());
		
		getActions().setTask(this);
	}
	
	
	
	/**
	 * Initialize this task with the given name, priority, statement and cube.
	 * 
	 * @param name
	 * 			
	 * @param priority
	 * 
	 * @param statement
	 * 
	 * @param cube
	 * 
	 * @post	The new tasks name is equal to the given name.
	 * 			| new.getName() == name
	 * 
	 * @effect	The new tasks priority is set to the given priority.
	 * 			| setPriority(priority)
	 * 
	 * @post	The new tasks cube field is equal to null.
	 * 			| new.getCube() == null
	 * 
	 * @effect	The new tasks statement is set to the given statement.
	 * 			| setActions(statement)
	 * 
	 * @post	The new tasks executingActivity is set to its statement.
	 * 			| new.getExecutingActivity() = getActivity()
	 * 
	 * @effect	The task of the new tasks statements is set to this task.
	 * 			| getActions().setTask(this)
	 * 
	 * @throws	IllegalNameException
	 * 			The given name is not a valid name.
	 * 			| !isValidName(name)
	 * 
	 */
	public Task(String name, int priority, Statement statement ){
		if (!isValidName(name)) {
			throw new IllegalNameException(name);
		}
		this.name = name;
		
		setPriority(priority);
		setActions(statement);
		
		setExecutingActivity(getActions());

		getActions().setTask(this);
	}
	
	
	/**
	 * Execute this task.
	 * 
	 * @param 	dt
	 * 			The time interval in which to execute this task. For each 0.001 second,
	 * 			1 substatement of the tasks statement may be executed.
	 * 
	 * 
	 */
	public void execute(double dt) {
	    while (this.executingActivity != null && dt > 0 ) {
	    	
	    	this.executingActivity.execute();
	    	
	    	if (this.executingActivity.isCompleted()) {
	    		break;
	    		
	    	} else {
	    		this.executingActivity = this.executingActivity.getNextStatement();
	    		
	    		dt -= 0.001d;
	    		if (this.executingActivity == null)
	    			setCompleted();
			}
	    }
	    if ( isCompleted()) {
    		setBeingExecuted(false);
			setAssignedNit(null);
			terminate();
    	}
	}
	
	
	public void interrupt() {
		setBeingExecuted(false);
		setAssignedNit(null);
		// TODO start over
		getActions().setNextStatement(null);
		setPriority(getPriority() - 1);
	}
	
	
	public Statement getExecutingActivity() {
		return this.executingActivity;
	}

	public void setExecutingActivity(Statement executingActivity) {
		this.executingActivity = executingActivity;
	}

	@Basic
	public boolean isCompleted() {
		//return this.executingActivity == null;
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
	public Nit getAssignedNit() {
		return this.unit;
	}
	
	
	@Raw
	public Unit getAssignedUnit() {
		if (getAssignedNit() instanceof Unit)
			return (Unit) getAssignedNit();
		return null;
	}
	
	
	public boolean isValidNit(Nit unit) {
		if (beingExecuted()) return (unit != null);
		else return (unit == null);
	}

	@Raw
	public void setAssignedNit(Nit unit) {
		if (!isValidNit(unit))
			throw new IllegalArgumentException();
		if (beingExecuted() && getAssignedNit() != null) 
			throw new RuntimeException();
		if (unit != null && unit.getAssignedTask() != this )
			throw new IllegalArgumentException();
		
		this.unit = unit;
		/*try {
			throw new IllegalArgumentException();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		System.out.println("Nit assigned to task");*/
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
					getActions().toString() + 
					String.format(", cube: %s", (getCube() == null ? "none" : getCube().toString()))
					+ ", executing: " + beingExecuted();
	}
	
	
	
	public void terminate() {
		if (getAssignedNit() == null && !beingExecuted() && isCompleted()) {
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