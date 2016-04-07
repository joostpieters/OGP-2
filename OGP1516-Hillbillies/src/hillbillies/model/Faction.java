package hillbillies.model;

import java.util.Comparator;
import java.util.*;


public class Faction {
	
	private final Set<Unit> units = new HashSet<Unit>();
	
	public Faction () {
		
	}
	
	public int getNbOfUnits() {
		return this.units.size();
	}
	
	public Set<Unit> getUnitsOfFaction() {
		return new HashSet<Unit>(this.units);
	}
	
	public static class NbUnitsComparator implements Comparator<Faction> {
	    @Override
	    public int compare(Faction a, Faction b) {
	        return a.getNbOfUnits() < b.getNbOfUnits() ? -1 
	        			: a.getNbOfUnits() == b.getNbOfUnits() ? 0 : 1;
	    }
	}
}

