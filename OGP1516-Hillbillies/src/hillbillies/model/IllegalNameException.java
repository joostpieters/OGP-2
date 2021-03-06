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
	 * Initialize this new illegal name exception with given name.
	 * 
	 * @param name
	 * 			The illegal name.
	 * 
	 * @post The name of this new illegal name exception is equal
	 * 		to the given name
	 * 		| new.getName() == name
	 * 
	 * ...
	 */

	public IllegalNameException(String name, Nit nit) {
		this.name = name;
		this.nit = nit;
	}
	
	public IllegalNameException(String name) {
		this.name = name;
		this.nit = null;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Nit getNit() {
		return this.nit;
	}
	
	private final String name;
	private final Nit nit;

}
