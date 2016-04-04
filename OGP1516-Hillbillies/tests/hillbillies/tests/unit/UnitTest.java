package hillbillies.tests.unit;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;


import hillbillies.model.Unit;

/**
 * A class collecting tests for the class of units.
 * 
 * @author Ruben Cartuyvels
 * @version 1.0
 *
 */
public class UnitTest {
	 
	private static Unit unitAttrib100;
	
	@Before
	public void setUpUnit() {
		int[] position = new int[]{0,0,0};
		unitAttrib100 = new Unit("Ruben", position, 100, 100, 100, 100, true);
	}
	
	// insert constructor tests here
	
	@Test
	public void setStrength_High() {
		unitAttrib100.setStrength(201);
		assertEquals(unitAttrib100.getStrength(), 100);
	}
	
	@Test
	public void setStrength_Low() {
		unitAttrib100.setStrength(-1);
		assertEquals(unitAttrib100.getStrength(), 100);
	}
	
	@Test
	public void setAgility_High() {
		unitAttrib100.setAgility(201);
		assertEquals(unitAttrib100.getAgility(), 100);
	}
	
	@Test
	public void setAgility_Low() {
		unitAttrib100.setAgility(-1);
		assertEquals(unitAttrib100.getAgility(), 100);
	}
	
	@Test
	public void setToughness_High() {
		unitAttrib100.setToughness(201);
		assertEquals(unitAttrib100.getToughness(), 100);
	}
	
	
	
	
	
}