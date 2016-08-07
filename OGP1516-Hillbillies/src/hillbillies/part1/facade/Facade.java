package hillbillies.part1.facade;


import hillbillies.model.*;
import ogp.framework.util.ModelException;


public class Facade implements hillbillies.part1.facade.IFacade {
	//public Facade() {}

	@Override
	public Unit createUnit(String name, int[] initialPosition, int weight, int agility, int strength, int toughness,
			boolean enableDefaultBehavior) throws ModelException {
		try {
			Unit unit = new Unit(name, initialPosition, weight, agility, strength, toughness,
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
		catch (IllegalNameException exc) {
			throw new ModelException();
		}
	}


	@Override
	public int getWeight(Unit unit) {
		return unit.getWeight();
	}

	@Override
	public void setWeight(Unit unit, int newValue) {
		unit.setWeight(newValue);
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

	@Override
	public void advanceTime(Unit unit, double dt) throws ModelException {
		try {
			unit.advanceTime(dt);
		}
		catch (RuntimeException exc) {
			throw new ModelException();
		}
	}

	@Override
	public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {
		unit.moveToAdjacent(new int[]{dx, dy, dz});
		/*try {
				unit.moveToAdjacent(new int[]{dx, dy, dz});
			}
			catch (IllegalPositionException | IllegalArgumentException | IllegalTimeException exc) {
				throw new ModelException();
			}*/
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
			throw new ModelException();
		}
	}

	@Override
	public void work(Unit unit) throws ModelException {
		unit.work();
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
}
