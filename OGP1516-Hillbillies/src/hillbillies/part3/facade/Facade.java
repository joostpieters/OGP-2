package hillbillies.part3.facade;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import hillbillies.model.Boulder;
import hillbillies.model.Coordinate;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.model.Nit;
import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.TaskFactory;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.World.TerrainType;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.part3.programs.ITaskFactory;
import ogp.framework.util.ModelException;

public class Facade implements hillbillies.part3.facade.IFacade, 
			hillbillies.part1.facade.IFacade, hillbillies.part2.facade.IFacade {

	/* ********************************************
	 * 					WORLD 
	 **********************************************/
	
	@Override
	public World createWorld(int[][][] terrainTypes, 
				TerrainChangeListener modelListener) throws ModelException {
		try {
			World world = new World(terrainTypes, modelListener);
			return world;
		}
		catch (RuntimeException exc) {
			exc.printStackTrace();
			throw new ModelException();
		}
	}
	
	@Override
	public int getNbCubesX(World world) throws ModelException {
		return world.getNbCubesX();
	}
	
	@Override
	public int getNbCubesY(World world) throws ModelException {
		return world.getNbCubesY();
	}

	@Override
	public int getNbCubesZ(World world) throws ModelException {
		return world.getNbCubesZ();
	}
	
	@Override
	public void advanceTime(World world, double dt) throws ModelException {
		try {
			world.advanceTime(dt);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new ModelException();
		}
	}

	@Override
	public int getCubeType(World world, int x, int y, int z) throws ModelException {
		return world.getCubeTypeAt(new Coordinate(x,y,z)).getNumber();
	}

	@Override
	public void setCubeType(World world, int x, int y, int z, int value) throws ModelException {
		try {
			world.setCubeTypeAt(new Coordinate(x,y,z), TerrainType.byOrdinal(value));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModelException();
		}
	}

	@Override
	public boolean isSolidConnectedToBorder(World world, int x, int y, int z) throws ModelException {
		return world.isSolidConnectedToBorder(x, y, z);
	}
	
	@Override
	public Unit spawnUnit(World world, boolean enableDefaultBehavior) throws ModelException {
		try {
			return world.spawnUnit(enableDefaultBehavior);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new ModelException();
		}
	}
	
	public Nit spawnNit(World world, boolean enableDefaultBehavior) throws ModelException {
		try {
			return world.spawnNit(enableDefaultBehavior);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new ModelException();
		}
	}

	
	@Override
	public void addUnit(Unit unit, World world) throws ModelException {
		try {
			world.addNit(unit);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new ModelException();
		}
	}
	
	@Override
	public Set<Unit> getUnits(World world) throws ModelException {
		return world.getAllUnits();
	}
	
	
	/* ********************************************
	 * 					UNITS 
	 **********************************************/
	
	@Override
	public boolean isCarryingBoulder(Unit unit) throws ModelException {
		return unit.isCarryingBoulder();
	}
	
	@Override
	public boolean isCarryingLog(Unit unit) throws ModelException {
		return unit.isCarryingLog();
	}
	
	@Override
	public Faction getFaction(Unit unit) throws ModelException {
		return unit.getFaction();
	}
	
	@Override
	public boolean isAlive(Unit unit) throws ModelException {
		return !unit.isTerminated();
	}
	
	@Override
	public void workAt(Unit unit, int x, int y, int z) throws ModelException {
		try {
			unit.workAt(new Coordinate(x, y, z));
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new ModelException();
		}
	}
	
	@Override
	public int getExperiencePoints(Unit unit) throws ModelException {
		return unit.getExperiencePoints();
	}
	
	
	@Override @Deprecated
	public Unit createUnit(String name, int[] initialPosition, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) throws ModelException {
		try {
			Unit unit = new Unit(name, new Coordinate(initialPosition), weight, agility, strength, toughness,
					enableDefaultBehavior);
			return unit;
		}
		catch (RuntimeException exc) {
			throw new ModelException();
		}
	}
	
	@Override
	public double[] getPosition(Unit unit) {
		return unit.getPosition();
	}
	
	@Override
	public int[] getCubeCoordinate(Unit unit) {
		return unit.getCoordinate().getCoordinates();
	}
	
	@Override
	public String getName(Unit unit) {
		return unit.getName();
	}
	
	@Override
	public void setName(Unit unit, String newName) throws ModelException {
		try {
			unit.setName(newName);
		}
		catch (RuntimeException exc) {
			exc.printStackTrace();
			throw new ModelException();
		}
	}
	
	@Override
	public int getWeight(Unit unit) {
		return unit.getWeight();
	}
	
	@Override
	public void setWeight(Unit unit, int newValue) /*throws ModelException*/ {
		unit.setWeight(newValue);
		/*try {
			unit.setWeight(newValue);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModelException();
		}*/
	}

	@Override
	public int getStrength(Unit unit) {
		return unit.getStrength();
	}

	@Override
	public void setStrength(Unit unit, int newValue) {
		unit.setStrength(newValue);
	}

	@Override
	public int getAgility(Unit unit) {
		return unit.getAgility();
	}

	@Override
	public void setAgility(Unit unit, int newValue) {
		unit.setAgility(newValue);
	}

	@Override
	public int getToughness(Unit unit) {
		return unit.getToughness();
	}

	@Override
	public void setToughness(Unit unit, int newValue) {
		unit.setToughness(newValue);
	}
	
	@Override
	public int getMaxHitPoints(Unit unit) {
		return unit.getMaxHitPoints();
	}
	
	@Override
	public int getCurrentHitPoints(Unit unit) {
		return unit.getCurrentHitPoints();
	}

	@Override
	public int getMaxStaminaPoints(Unit unit) {
		return unit.getMaxStaminaPoints();
	}

	@Override
	public int getCurrentStaminaPoints(Unit unit) {
		return unit.getCurrentStaminaPoints();
	}
	
	@Override @Deprecated
	public void advanceTime(Unit unit, double dt) throws ModelException {
		try {
			unit.advanceTime(dt);
		}
		catch (RuntimeException exc) {
			exc.printStackTrace();
			throw new ModelException();
		}
	}
	
	@Override
	public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {
		try {
			unit.moveToAdjacent(new int[]{dx, dy, dz});
		}
		catch (RuntimeException exc) {
			exc.printStackTrace();
			throw new ModelException();
		}
	}
	
	@Override
	public double getCurrentSpeed(Unit unit) throws ModelException {
		return unit.getCurrentSpeed();
	}
	
	@Override
	public boolean isMoving(Unit unit) throws ModelException {
		return unit.isMoving();
	}
	
	@Override
	public void startSprinting(Unit unit) throws ModelException {
		unit.startSprinting();
	}
	
	@Override
	public void stopSprinting(Unit unit) throws ModelException {
		unit.stopSprinting();
	}
	
	@Override
	public boolean isSprinting(Unit unit) throws ModelException {
		return unit.isSprinting();
	}
	
	@Override
	public double getOrientation(Unit unit) throws ModelException {
		return unit.getOrientation();
	}
	
	@Override
	public void moveTo(Unit unit, int[] cube) throws ModelException {
		try {
			unit.moveTo(new Coordinate(cube));
		}
		catch (RuntimeException exc) {
			exc.printStackTrace();
			throw new ModelException();
		}
	}
		
	@Override
	public boolean isWorking(Unit unit) throws ModelException {
		return unit.isWorking();
	}
	
	
	@Override
	public void fight(Unit attacker, Unit defender) throws ModelException {
		try {
			attacker.attack(defender);
		}
		catch (RuntimeException exc) {
			exc.printStackTrace();
			throw new ModelException();
		}
	}

	@Override
	public boolean isAttacking(Unit unit) throws ModelException{
		return unit.isAttacking();
	}

	@Override
	public void rest(Unit unit) throws ModelException {
		try {
			unit.rest();
		}
		catch (RuntimeException exc) {
			exc.printStackTrace();
			throw new ModelException();
		}
	}

	@Override
	public boolean isResting(Unit unit) throws ModelException {
		return unit.isResting();
	}

	@Override
	public void setDefaultBehaviorEnabled(Unit unit, boolean value) throws ModelException {
		unit.setDefaultBehavior(value);
	}
	
	@Override
	public boolean isDefaultBehaviorEnabled(Unit unit) throws ModelException {
		return unit.isDefaultBehaviorEnabled();
	}
	
	
	/* ********************************************
	 * 					FACTIONS 
	 **********************************************/
	
	@Override
	public Set<Unit> getUnitsOfFaction(Faction faction) throws ModelException {
		return faction.getAllUnits();
	}
	
	@Override
	public Set<Faction> getActiveFactions(World world) throws ModelException {
		return world.getAllFactions();
	}
	
	
	
	/* ********************************************
	 * 					BOULDERS, LOGS 
	 **********************************************/
	
	@Override
	public double[] getPosition(Boulder boulder) throws ModelException {
		return boulder.getPosition();
	}
	
	@Override
	public Set<Boulder> getBoulders(World world) throws ModelException {
		return world.getAllBoulders();
	}
	
	@Override
	public double[] getPosition(Log log) throws ModelException {
		return log.getPosition();
	}

	@Override
	public Set<Log> getLogs(World world) throws ModelException {
		return world.getAllLogs();
	}
	
	
	
	/* ********************************************
	 * 					TASKS 
	 **********************************************/
	// TODO wrap exceptions

	@Override
	public ITaskFactory<?, ?, Task> createTaskFactory() {
		return new TaskFactory();
	}

	@Override
	public boolean isWellFormed(Task task) throws ModelException {
		return task.isWellFormed();
	}

	@Override
	public Scheduler getScheduler(Faction faction) throws ModelException {
		return faction.getScheduler();
	}

	@Override
	public void schedule(Scheduler scheduler, Task task) throws ModelException {
		scheduler.addTask(task);
	}

	@Override
	public void replace(Scheduler scheduler, Task original, Task replacement) throws ModelException {
		scheduler.replace(original, replacement);
	}

	@Override
	public boolean areTasksPartOf(Scheduler scheduler, Collection<Task> tasks) throws ModelException {
		return scheduler.areTasksPartOf(tasks);
	}

	@Override
	public Iterator<Task> getAllTasksIterator(Scheduler scheduler) throws ModelException {
		return scheduler.getIterator();
	}

	@Override
	public Set<Scheduler> getSchedulersForTask(Task task) throws ModelException {
		return task.getSchedulersForTask();
	}

	@Override
	public Unit getAssignedUnit(Task task) throws ModelException {
		return task.getAssignedUnit();
	}

	@Override
	public Task getAssignedTask(Unit unit) throws ModelException {
		return unit.getAssignedTask();
	}

	@Override
	public String getName(Task task) throws ModelException {
		return task.getName();
	}

	@Override
	public int getPriority(Task task) throws ModelException {
		return task.getPriority();
	}

}
