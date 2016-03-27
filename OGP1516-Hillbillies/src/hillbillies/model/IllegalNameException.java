package hillbillies.model;


/**
* A class of exceptions signaling illegal unit names.
* 	Each exception involves the illegal name and the unit.
*
*@author Ruben Cartuyvels
*@version	1.0
*/
public class IllegalNameException extends RuntimeException {
	/**
	 * Initialize this new illegal position exception with given position
	 * and unit.
	 * 
	 * @param name
	 * 			The illegal name.
	 * @param unit
	 * 			The unit.
	 * @post The name of this new illegal name exception is equal
	 * 		to the given name
	 * 		| new.getName() == name
	 * 
	 * ...
	 */
	public IllegalNameException(String name, Unit unit) {
		this.name = name;
		this.unit = unit;
	}
	
	public String getName() {
		return name;
	}
	
	private final String name;
	
	public Unit getUnit() {
		return unit;
	}
	
	private final Unit unit;
}
