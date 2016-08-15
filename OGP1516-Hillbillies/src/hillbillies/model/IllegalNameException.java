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
	 * Initialize this new illegal name exception with given position
	 * and unit.
	 * 
	 * @param name
	 * 			The illegal name.
	 * @param nit
	 * 			The unit.
	 * @post The name of this new illegal name exception is equal
	 * 		to the given name
	 * 		| new.getName() == name
	 * 
	 * ...
	 */
	public IllegalNameException(String name, Nit nit) {
		this.name = name;
		this.unit = nit;
	}
	
	public String getName() {
		return name;
	}
	
	private final String name;
	
	public Nit getUnit() {
		return unit;
	}
	
	private final Nit unit;
}
