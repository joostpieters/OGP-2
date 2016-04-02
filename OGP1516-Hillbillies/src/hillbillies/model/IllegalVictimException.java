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
	public IllegalVictimException(Unit attacker, Unit defender) {
		this.attacker = attacker;
		this.defender = defender;
	}
	
	public Unit getAttacker() {
		return attacker;
	}
	
	public Unit getDefender() {
		return defender;
	}
	
	private final Unit attacker;
	private final Unit defender;
}
