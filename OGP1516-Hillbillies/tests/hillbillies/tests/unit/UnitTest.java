package hillbillies.tests.unit;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;

import hillbillies.part2.facade.Facade;
import hillbillies.part2.facade.IFacade;
import hillbillies.model.*;
import hillbillies.model.World.TerrainType;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.ModelException;

/**
 * A class collecting tests for the class of units.
 * 
 * @author Ruben Cartuyvels
 * @version 1.0
 *
 */
public class UnitTest {
	
	Facade facade;
	 
	private Unit unit;
	private World world;
	
	@Before
	public void setUpWorld() throws Exception {
		facade = new Facade();
		
		int[][][] types = new int[3][3][3];
		types[1][1][0] = TerrainType.ROCK.getNumber();
		types[1][1][1] = TerrainType.TREE.getNumber();
		types[1][1][2] = TerrainType.WORKSHOP.getNumber();

		world = facade.createWorld(types, new DefaultTerrainChangeListener());
		unit = facade.spawnUnit(world, false);
		
	}
	
	/**************************************************
	 * 	ATTRIBUTES: STRENGTH,... HITPOINTS, STAMINA
	 **************************************************/
	
	@Test
	public void setStrength_High() {
		int initStrength = unit.getStrength();
		unit.setStrength(201);
		assertEquals(unit.getStrength(), initStrength);
	}
	
	@Test
	public void setStrength_Low() {
		int initStrength = unit.getStrength();
		unit.setStrength(-1);
		assertEquals(unit.getStrength(), initStrength);
	}
	
	@Test
	public void setStrength_Ok() {
		unit.setStrength(150);
		assertEquals(unit.getStrength(), 150);
		unit.setStrength(1);
		assertEquals(unit.getStrength(), 1);
		unit.setStrength(200);
		assertEquals(unit.getStrength(), 200);
	}
	
	@Test
	public void setAgility_High() {
		int initAgility = unit.getAgility();
		unit.setAgility(201);
		assertEquals(unit.getAgility(), initAgility);
	}
	
	@Test
	public void setAgility_Ok() {
		unit.setAgility(150);
		assertEquals(unit.getAgility(), 150);
		unit.setAgility(1);
		assertEquals(unit.getAgility(), 1);
		unit.setAgility(200);
		assertEquals(unit.getAgility(), 200);
	}
	
	
	
	@Test
	public void setToughness_Ok() {
		unit.setToughness(150);
		assertEquals(unit.getToughness(), 150);
		unit.setToughness(1);
		assertEquals(unit.getToughness(), 1);
		unit.setToughness(200);
		assertEquals(unit.getToughness(), 200);
	}
	
	@Test
	public void setWeight_High() {
		int initWeight = unit.getWeight();
		unit.setWeight(201);
		assertEquals(unit.getWeight(), initWeight);
	}
	
	@Test
	public void setWeight_Low() {
		int initWeight = unit.getWeight();
		unit.setWeight(-1);
		assertEquals(unit.getWeight(), initWeight);
	}
	
	@Test
	public void setWeight_Ok() {
		unit.setWeight(150);
		assertEquals(unit.getWeight(), 150);
		unit.setWeight(200);
		assertEquals(unit.getWeight(), 200);
	}
	
	@Test
	public void setWeight_UnderMean() {
		int initWeight = unit.getWeight();
		unit.setWeight((unit.getStrength()+unit.getAgility())/2 - 10);
		assertEquals(unit.getWeight(), initWeight);
	}
	
	@Test
	public void getMaxHPStam_Middle() {
		unit.setToughness(75);
		unit.setStrength(70);
		unit.setAgility(70);
		unit.setWeight(75);
		
		//75/100 * 75/100 *200 = 112.5
		assertEquals(unit.getMaxHitPoints(), 113);
		assertEquals(unit.getMaxStaminaPoints(), 113);
	}
	
	@Test
	public void getMaxHPStam_Low() {
		unit.setToughness(1);
		unit.setStrength(1);
		unit.setAgility(1);
		unit.setWeight(1);
		
		//1/100 * 1/100 *200 = 0.02
		assertEquals(unit.getMaxHitPoints(), 1);
		assertEquals(unit.getMaxStaminaPoints(), 1);
	}
	
	
	
	/**************************************************
	 * 		ACTIVITIES: moving
	 **************************************************/
	
	
	/**
	 * 			moveToAdjacent
	 */
	
	
	@Test
	public void moveToAdjacent_Legal() throws Exception {
		if (unit.canHaveAsPosition(new Coordinate(
				unit.getCoordinate().get(0)-1, unit.getCoordinate().get(1)-1,
				unit.getCoordinate().get(2)-1))) {
			unit.moveToAdjacent(new int[]{-1, -1, -1});
			assertEquals(unit.getDestination(), new Coordinate(
				unit.getCoordinate().get(0)-1, unit.getCoordinate().get(1)-1,
				unit.getCoordinate().get(2)-1));
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void moveToAdjacent_Short() throws Exception {
		unit.moveToAdjacent(new int[]{-1, -1});
		//assertEquals(unit.getPosition(), new int[]{5,5,5});
	}
	
	
	/**
	 * 			moveTo
	 */
	
	
	@Test
	public void moveTo_Legal() throws Exception {
		Coordinate dest = world.getNearRandomNeighbouringSolidCube(unit.getCoordinate());
		unit.moveTo(dest);
		assertEquals(unit.getDestCubeLT(), dest);
	}
	
	@Test
	public void moveTo_Legal_Low() throws Exception {
		Coordinate dest = new Coordinate(0,0,0);
		unit.moveTo(dest);
		assertEquals(unit.getDestCubeLT(), dest);
	}
	
	/*
	@Test
	public void moveTo_Legal_High() throws Exception {
		Coordinate dest = new Coordinate(49,49,49);
		unit.moveTo(dest);
		assertEquals(unit.getDestCubeLT(), dest);
	}
	*/
	
	@Test
	public void moveTo_Legal_Interrupt() throws Exception {
		Coordinate dest = world.getNearRandomNeighbouringSolidCube(unit.getCoordinate());
		unit.moveTo(dest);
		assertEquals(unit.getDestCubeLT(), dest);
		unit.rest();
		assertEquals(unit.getDestCubeLT(), dest);
	}
	
	@Test(expected = IllegalPositionException.class)
	public void moveTo_OOB_High() throws Exception {
		Coordinate dest = new Coordinate(world.getNbCubesX(),world.getNbCubesY()-1,world.getNbCubesZ()-1);
		unit.moveTo(dest);
	}
	
	
	@Test(expected = IllegalPositionException.class)
	public void moveTo_OOB_Low() throws Exception {
		Coordinate dest = new Coordinate(-1,world.getNbCubesY()-1,world.getNbCubesZ()-1);
		unit.moveTo(dest);
	}
	
	
	
	/**
	 * 			getCurrentSpeed
	 */
	
	/*
	@Test
	public void getCurrentSpeed_Legal_Flat() {
		unit.moveTo(world.getNearRandomNeighbouringSolidCube(unit.getCoordinate()));
		assertEquals(unit.getCurrentSpeed(), 1.5*(unit.getStrength()+
					unit.getAgility())/(2.0*unit.getWeight()), 0.1);
	}
	
	/*@Test
	public void getCurrentSpeed_Legal_Up() {
		unit.moveTo(new Coordinate(20,20,20));
		assertEquals(unit.getCurrentSpeed(), 0.5*1.5*(unit.getStrength()+
					unit.getAgility())/(2.0*unit.getWeight()), 0.1);
	}
	
	@Test
	public void getCurrentSpeed_Legal_Down() {
		unit.moveTo(new Coordinate(20,20,0));
		assertEquals(unit.getCurrentSpeed(), 1.2*1.5*(unit.getStrength()+
					unit.getAgility())/(2.0*unit.getWeight()), 0.1);
	}
	*/
	@Test
	public void getCurrentSpeed_Legal_Standing() {
		assertEquals(unit.getCurrentSpeed(), 0.0, 0.1);
	}
	
	
	/**
	 * 			startSprinting
	 */
	
	@Test
	public void startSprinting_Legal() {
		unit.setState(State.MOVING);
		unit.startSprinting();
		assertTrue(unit.isSprinting());
		
		unit.stopSprinting();
		assertFalse(unit.isSprinting());
	}
	
	@Test
	public void startSprinting_Illegal() {
		unit.updateCurrentStaminaPoints(0);
		unit.setState(State.MOVING);
		unit.startSprinting();
		assertFalse(unit.isSprinting());
	}
	
	
	/**
	 * 			work
	 */
	
	@Test
	public void work_Legal() {
		unit.work();
		assertTrue(unit.isWorking());
	}
	
	@Test
	public void work_Illegal() {
		unit.setState(State.RESTING_1);
		unit.work();
		assertFalse(unit.isWorking());
	}
	
	
	/**
	 * 			attack
	 * @throws ModelException 
	 */
	
	
	@Test
	public void attack_Legal() throws ModelException {
		Unit unit2 = facade.spawnUnit(world, false);
		unit2.moveTo(this.unit.getCoordinate());
		
		unit.attack(unit2);
		assertTrue(unit.isAttacking());
		assertTrue(unit2.isAttacked());
	}
	
	
	
	@Test(expected = IllegalVictimException.class)
	public void attack_Illegal_OOR() throws ModelException {
		Unit unit2 = facade.spawnUnit(world, false);
		//unit2.moveTo(this.unit.getCoordinate());
		
		unit.attack(unit2);
		unit2.attack(unit2);
		
		assertFalse(unit2.isAttacking());
		assertFalse(unit.isAttacked());
	}
	
	/*
	@Test
	public void attack_Illegal_Moving() {
		int[] position = new int[]{2,5,5};
		Unit unit2 = facade.spawnUnit(world, true);
		
		unit.setState(State.MOVING);
		unit.attack(unit2);
		
		assertFalse(unit.isAttacking());
		assertFalse(unit2.isAttacked());
	}
	*/
	
	/**
	 * 			rest
	 */
	@Test
	public void rest_Legal() {
		unit.rest();
		assertTrue(unit.isResting());
		assertEquals(unit.getState(), State.RESTING_1);
	}
	
	
	@Test
	public void rest_Illegal() {
		unit.setState(State.MOVING);
		unit.rest();
		
		assertFalse(unit.isResting());
	}
	
	
	@Test//(expected = ArithmeticException.class)
	public void addToXP_High() {
		assertFalse(unit.canAddToXP(Integer.MAX_VALUE));
	}
	
	
	@Test
	public void setState_falling() {
		unit.setState(State.FALLING);
		assertEquals(unit.getState(), State.FALLING);
	}
	
	
	
	
	
}