package hillbillies.tests.unit;

import org.junit.Test;
import hillbillies.model.Coordinate;

import hillbillies.model.IllegalNameException;
import hillbillies.model.IllegalPositionException;
import hillbillies.model.Unit;

/**
 * A class collecting tests for the constructor of the class of units.
 * 
 * @author Ruben Cartuyvels
 * @version 1.0
 *
 */
public class UnitConstructorTest {
	
	/**************************************************
	 * CONSTRUCTOR: POSITION, NAME
	 **************************************************/
	
	
	@Test
	public void constructor_Valid() throws Exception {
		Unit unit = new Unit("Ruben 'C", new Coordinate(1, 1, 1), 100, 100, 100, 100, true);
	}
	
	@Test(expected = IllegalNameException.class)
	public void constructor_InvalidName_Short() {
		Unit unit = new Unit("R", new Coordinate(1, 1, 1), 100, 100, 100, 100, true);
	}
	
	@Test(expected = IllegalNameException.class)
	public void constructor_InvalidName_Lowercase() {
		Unit unit = new Unit("ruben", new Coordinate(1, 1, 1), 100, 100, 100, 100, true);
	}
	
	@Test(expected = IllegalNameException.class)
	public void constructor_InvalidName_IllegalChar() {
		Unit unit = new Unit("Ruben 7", new Coordinate(1, 1, 1), 100, 100, 100, 100, true);
	}
	
	
	@Test
	public void constructor_ValidPosition_Low() {
		Unit unit = new Unit("Ruben", new Coordinate(0, 0, 0), 100, 100, 100, 100, true);
	}
	
	@Test
	public void constructor_ValidPosition_High() {
		Unit unit = new Unit("Ruben", new Coordinate(49, 49, 49), 100, 100, 100, 100, true);
	}
	
	/*
	@Test(expected = IllegalPositionException.class)
	public void constructor_InvalidPosition_OOB_Low() {
		Unit unit = new Unit("Ruben", new Coordinate(-1, -1, -1), 100, 100, 100, 100, true);
	}
	
	@Test(expected = IllegalPositionException.class)
	public void constructor_InvalidPosition_OOB_High() {
		Unit unit = new Unit("Ruben", new Coordinate(50, 50, 50), 100, 100, 100, 100, true);
	}
	*/
	
	
	
	
}
