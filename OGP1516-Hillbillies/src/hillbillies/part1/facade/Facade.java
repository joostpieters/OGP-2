package hillbillies.part1.facade;

import hillbillies.model.Unit;
import hillbillies.model.IllegalPositionException;
import hillbillies.model.IllegalTimeException;
import hillbillies.model.IllegalNameException;
import ogp.framework.util.ModelException;


public class Facade implements IFacade {
	//public Facade() {}
	
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
		
		public double[] getPosition(Unit unit) {
			return unit.getPosition();
		}
		
		public int[] getCubeCoordinate(Unit unit) {
			return unit.getCubeCoordinate();
		}
		
		public String getName(Unit unit) {
			return unit.getName();
		}
		
		public void setName(Unit unit, String newName) throws ModelException {
			try {
				unit.setName(newName);
			}
			catch (IllegalNameException exc) {
				throw new ModelException();
			}
		}
		
		
		public int getWeight(Unit unit) {
			return unit.getWeight();
		}
		
		public void setWeight(Unit unit, int newValue) {
			unit.setWeight(newValue);
		}

		
		public int getStrength(Unit unit) {
			return unit.getStrength();
		}

		
		public void setStrength(Unit unit, int newValue) {
			unit.setStrength(newValue);
		}

		
		public int getAgility(Unit unit) {
			return unit.getAgility();
		}

		
		public void setAgility(Unit unit, int newValue) {
			unit.setAgility(newValue);
		}

		
		public int getToughness(Unit unit) {
			return unit.getToughness();
		}

		
		public void setToughness(Unit unit, int newValue) {
			unit.setToughness(newValue);
		}
		
		public int getMaxHitPoints(Unit unit) {
			return unit.getMaxHitPoints();
		}

		public int getCurrentHitPoints(Unit unit) {
			return unit.getCurrentHitPoints();
		}

		public int getMaxStaminaPoints(Unit unit) {
			return unit.getMaxStaminaPoints();
		}

		public int getCurrentStaminaPoints(Unit unit) {
			return unit.getCurrentStaminaPoints();
		}
		
		public void advanceTime(Unit unit, double dt) throws ModelException {
			try {
				unit.advanceTime(dt);
			}
			catch (RuntimeException exc) {
				throw new ModelException();
			}
		}
		
		public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {
			unit.moveToAdjacent(new int[]{dx, dy, dz});
			/*try {
				unit.moveToAdjacent(new int[]{dx, dy, dz});
			}
			catch (IllegalPositionException | IllegalArgumentException | IllegalTimeException exc) {
				throw new ModelException();
			}*/
		}
		
		public double getCurrentSpeed(Unit unit) throws ModelException {
			return unit.getCurrentSpeed();
		}
		
		public boolean isMoving(Unit unit) throws ModelException {
			return unit.isMoving();
		}
		
		public void startSprinting(Unit unit) throws ModelException {
			unit.startSprinting();
		}
		
		public void stopSprinting(Unit unit) throws ModelException {
			unit.stopSprinting();
		}
		
		public boolean isSprinting(Unit unit) throws ModelException {
			return unit.isSprinting();
		}
		
		public double getOrientation(Unit unit) throws ModelException {
			return unit.getOrientation();
		}
		
		public void moveTo(Unit unit, int[] cube) throws ModelException {
			try {
				unit.moveTo(cube);
			}
			catch (RuntimeException exc) {
				throw new ModelException();
			}
		}
		
		public void work(Unit unit) throws ModelException {
			unit.work();
		}
			
		public boolean isWorking(Unit unit) throws ModelException {
			return unit.isWorking();
		}

		public void fight(Unit attacker, Unit defender) throws ModelException {
			try {
				attacker.attack(defender);
			}
			catch (RuntimeException exc) {
				throw new ModelException();
			}
		}

		public boolean isAttacking(Unit unit) throws ModelException{
			return unit.isAttacking();
		}

		public void rest(Unit unit) throws ModelException {
			try {
				unit.rest();
			}
			catch (RuntimeException exc) {
				throw new ModelException();
			}
		}

		public boolean isResting(Unit unit) throws ModelException {
			return unit.isResting();
		}

		public void setDefaultBehaviorEnabled(Unit unit, boolean value) throws ModelException {
			unit.setDefaultBehaviorEnabled(value);
		}

		public boolean isDefaultBehaviorEnabled(Unit unit) throws ModelException {
			return unit.isDefaultBehaviorEnabled();
		}
}
