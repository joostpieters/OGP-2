package hillbillies.tests.unit;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;

//import hillbillies.part2.facade.Facade;
//import hillbillies.part2.facade.IFacade;
import hillbillies.part3.facade.Facade;
import hillbillies.part3.facade.IFacade;
import hillbillies.model.*;
import hillbillies.model.World.TerrainType;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.ModelException;

/**
 * A class collecting tests for the class of enits.
 * 
 * @author Ruben Cartuyvels
 * @version 1.0
 *
 */
public class EnitTest {
	
	Facade facade;
	 
	private Nit enit;
	private World world;
	
	@Before
	public void setUpWorld() throws Exception {
		facade = new Facade();
		
		int[][][] types = new int[3][3][3];
		types[1][1][0] = TerrainType.ROCK.getNumber();
		types[1][1][1] = TerrainType.TREE.getNumber();
		types[1][1][2] = TerrainType.WORKSHOP.getNumber();

		world = facade.createWorld(types, new DefaultTerrainChangeListener());
		
		do {
			enit = facade.spawnNit(world, false);
		} while (! (enit instanceof Enit));
		
	}
	
	/**************************************************
	 * 	ATTRIBUTES: STRENGTH,... HITPOINTS, STAMINA
	 **************************************************/
	
	@Test
	public void setStrength_High() {
		int initStrength = enit.getStrength();
		enit.setStrength(201);
		assertEquals(enit.getStrength(), initStrength);
	}
	
	@Test
	public void setStrength_Low() {
		int initStrength = enit.getStrength();
		enit.setStrength(-1);
		assertEquals(enit.getStrength(), initStrength);
	}
	
	@Test
	public void setStrength_Ok() {
		enit.setStrength(150);
		assertEquals(enit.getStrength(), 150);
		enit.setStrength(1);
		assertEquals(enit.getStrength(), 1);
		enit.setStrength(200);
		assertEquals(enit.getStrength(), 200);
	}
	
	@Test
	public void setAgility_High() {
		int initAgility = enit.getAgility();
		enit.setAgility(201);
		assertEquals(enit.getAgility(), initAgility);
	}
	
	@Test
	public void setAgility_Ok() {
		enit.setAgility(150);
		assertEquals(enit.getAgility(), 150);
		enit.setAgility(1);
		assertEquals(enit.getAgility(), 1);
		enit.setAgility(200);
		assertEquals(enit.getAgility(), 200);
	}
	
	
	
	@Test
	public void setToughness_Ok() {
		enit.setToughness(150);
		assertEquals(enit.getToughness(), 150);
		enit.setToughness(1);
		assertEquals(enit.getToughness(), 1);
		enit.setToughness(200);
		assertEquals(enit.getToughness(), 200);
	}
	
	@Test
	public void setWeight_High() {
		int initWeight = enit.getWeight();
		enit.setWeight(201);
		assertEquals(enit.getWeight(), initWeight);
	}
	
	@Test
	public void setWeight_Low() {
		int initWeight = enit.getWeight();
		enit.setWeight(-1);
		assertEquals(enit.getWeight(), initWeight);
	}
	
	@Test
	public void setWeight_Ok() {
		enit.setWeight(150);
		assertEquals(enit.getWeight(), 150);
		enit.setWeight(200);
		assertEquals(enit.getWeight(), 200);
	}
	
	@Test
	public void setWeight_UnderMean() {
		int initWeight = enit.getWeight();
		enit.setWeight((enit.getStrength()+enit.getAgility())/2 - 10);
		assertEquals(enit.getWeight(), initWeight);
	}
	
	@Test
	public void getMaxHPStam_Middle() {
		enit.setToughness(75);
		enit.setStrength(75);
		enit.setAgility(75);
		enit.setWeight(75);
		
		//75/100 * 75/100 *200 = 112.5
		assertEquals(enit.getMaxHitPoints(), 150);
		assertEquals(enit.getMaxStaminaPoints(), 150);
	}
	
	@Test
	public void getMaxHPStam_Low() {
		enit.setToughness(1);
		enit.setStrength(1);
		enit.setAgility(1);
		enit.setWeight(1);
		
		//1/100 * 1/100 *200 = 0.02
		assertEquals(enit.getMaxHitPoints(), 2);
		assertEquals(enit.getMaxStaminaPoints(), 2);
	}
	
	
	
	/**************************************************
	 * 		ACTIVITIES: moving
	 **************************************************/
	
	
	/**
	 * 			moveToAdjacent
	 */
	
	
	@Test
	public void moveToAdjacent_Legal() throws Exception {
		if (enit.canHaveAsPosition(new Coordinate(
				enit.getCoordinate().get(0)-1, enit.getCoordinate().get(1)-1,
				enit.getCoordinate().get(2)-1))) {
			enit.moveToAdjacent(new int[]{-1, -1, -1});
			assertEquals(enit.getDestination(), new Coordinate(
				enit.getCoordinate().get(0)-1, enit.getCoordinate().get(1)-1,
				enit.getCoordinate().get(2)-1));
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void moveToAdjacent_Short() throws Exception {
		enit.moveToAdjacent(new int[]{-1, -1});
		//assertEquals(enit.getPosition(), new int[]{5,5,5});
	}
	
	
	/**
	 * 			moveTo
	 */
	
	
	@Test
	public void moveTo_Legal() throws Exception {
		Coordinate dest = world.getNearRandomNeighbouringSolidCube(enit.getCoordinate());
		enit.moveTo(dest);
		assertEquals(enit.getDestCubeLT(), dest);
	}
	
	@Test
	public void moveTo_Legal_Low() throws Exception {
		Coordinate dest = new Coordinate(0,0,0);
		enit.moveTo(dest);
		assertEquals(enit.getDestCubeLT(), dest);
	}
	
	/*
	@Test
	public void moveTo_Legal_High() throws Exception {
		Coordinate dest = new Coordinate(49,49,49);
		enit.moveTo(dest);
		assertEquals(enit.getDestCubeLT(), dest);
	}
	*/
	
	@Test
	public void moveTo_Legal_Interrupt() throws Exception {
		Coordinate dest = world.getNearRandomNeighbouringSolidCube(enit.getCoordinate());
		enit.moveTo(dest);
		assertEquals(enit.getDestCubeLT(), dest);
		enit.rest();
		assertEquals(enit.getDestCubeLT(), dest);
	}
	
	@Test(expected = IllegalPositionException.class)
	public void moveTo_OOB_High() throws Exception {
		Coordinate dest = new Coordinate(world.getNbCubesX(),world.getNbCubesY()-1,world.getNbCubesZ()-1);
		enit.moveTo(dest);
	}
	
	
	@Test(expected = IllegalPositionException.class)
	public void moveTo_OOB_Low() throws Exception {
		Coordinate dest = new Coordinate(-1,world.getNbCubesY()-1,world.getNbCubesZ()-1);
		enit.moveTo(dest);
	}
	
	
	
	/**
	 * 			getCurrentSpeed
	 */
	
	/*
	@Test
	public void getCurrentSpeed_Legal_Flat() {
		enit.moveTo(world.getNearRandomNeighbouringSolidCube(enit.getCoordinate()));
		assertEquals(enit.getCurrentSpeed(), 1.5*(enit.getStrength()+
					enit.getAgility())/(2.0*enit.getWeight()), 0.1);
	}
	
	/*@Test
	public void getCurrentSpeed_Legal_Up() {
		enit.moveTo(new Coordinate(20,20,20));
		assertEquals(enit.getCurrentSpeed(), 0.5*1.5*(enit.getStrength()+
					enit.getAgility())/(2.0*enit.getWeight()), 0.1);
	}
	
	@Test
	public void getCurrentSpeed_Legal_Down() {
		enit.moveTo(new Coordinate(20,20,0));
		assertEquals(enit.getCurrentSpeed(), 1.2*1.5*(enit.getStrength()+
					enit.getAgility())/(2.0*enit.getWeight()), 0.1);
	}
	*/
	@Test
	public void getCurrentSpeed_Legal_Standing() {
		assertEquals(enit.getCurrentSpeed(), 0.0, 0.1);
	}
	
	
	/**
	 * 			startSprinting
	 */
	
	@Test
	public void startSprinting_Legal() {
		enit.setState(State.MOVING);
		enit.startSprinting();
		assertTrue(enit.isSprinting());
		
		enit.stopSprinting();
		assertFalse(enit.isSprinting());
	}
	
	@Test
	public void startSprinting_Illegal() {
		enit.updateCurrentStaminaPoints(0);
		enit.setState(State.MOVING);
		enit.startSprinting();
		assertFalse(enit.isSprinting());
	}
	
	
	/**
	 * 			work
	 */
	
	@Test
	public void work_Legal() {
		enit.workAt(world.getRandomNeighbouringCube(enit.getCoordinate()));
		assertTrue(enit.isWorking());
	}
	
	@Test
	public void work_Illegal() {
		enit.setState(State.RESTING_1);
		enit.workAt(world.getRandomNeighbouringCube(enit.getCoordinate()));
		assertFalse(enit.isWorking());
	}
	
	
	/**
	 * 			attack
	 * @throws ModelException 
	 */
	
	
	@Test
	public void attack_Legal() throws ModelException {
		Nit enit2 = facade.spawnNit(world, false);
		enit2.moveTo(this.enit.getCoordinate());
		
		enit.attack(enit2);
		assertTrue(enit.isAttacking());
		assertTrue(enit2.isAttacked());
	}
	
	
	
	@Test(expected = IllegalVictimException.class)
	public void attack_Illegal_OOR() throws ModelException {
		Nit enit2 = facade.spawnNit(world, false);
		//enit2.moveTo(this.enit.getCoordinate());
		
		enit.attack(enit2);
		enit2.attack(enit2);
		
		assertFalse(enit2.isAttacking());
		assertFalse(enit.isAttacked());
	}
	
	/*
	@Test
	public void attack_Illegal_Moving() {
		int[] position = new int[]{2,5,5};
		Enit enit2 = facade.spawnEnit(world, true);
		
		enit.setState(State.MOVING);
		enit.attack(enit2);
		
		assertFalse(enit.isAttacking());
		assertFalse(enit2.isAttacked());
	}
	*/
	
	/**
	 * 			rest
	 */
	@Test
	public void rest_Legal() {
		enit.rest();
		assertTrue(enit.isResting());
		assertEquals(enit.getState(), State.RESTING_1);
	}
	
	
	@Test
	public void rest_Illegal() {
		enit.setState(State.MOVING);
		enit.rest();
		
		assertFalse(enit.isResting());
	}
	
	
	@Test//(expected = ArithmeticException.class)
	public void addToXP_High() {
		assertFalse(enit.canAddToXP(Integer.MAX_VALUE));
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void setState_falling() {
		enit.setState(State.FALLING);
		assertNotEquals(enit.getState(), State.FALLING);
	}
	
	
	
	
	
}