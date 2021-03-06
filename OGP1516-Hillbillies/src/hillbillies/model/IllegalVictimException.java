package hillbillies.model;


/**
* A class of exceptions signaling illegal victims.
* 	Each exception involves the attacking unit and the victim.
*
*@author Ruben Cartuyvels
*@version	1.0
*/
public class IllegalVictimException extends RuntimeException {
	/**
	 * Initialize this new illegal victim exception with given attacker and defender.
	 * 
	 * @param ...
	 * ...
	 */
	public IllegalVictimException(Nit attacker, Nit defender2) {
		this.attacker = attacker;
		this.defender = defender2;
	}
	
	public Nit getAttacker() {
		return attacker;
	}
	
	public Nit getDefender() {
		return defender;
	}
	
	private final Nit attacker;
	private final Nit defender;
}
