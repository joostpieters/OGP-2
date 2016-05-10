package hillbillies.model;

import java.util.Comparator;
import java.util.*;


public class Faction {
	
	private final Set<Unit> units = new HashSet<Unit>();
	
	public Faction (Unit unit) {
		this.units.add(unit);
	}
	
	public int getNbOfUnits() {
		return this.units.size();
	}
	
	public Set<Unit> getUnitsOfFaction() {
		return new HashSet<Unit>(this.units);
	}
	
	// destructor
	public void removeUnit(Unit unit) {
		this.units.remove(unit);
	}
	
	
	private boolean isTerminated = false;
	
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	private World world;
	
	public World getWorld() {
		return this.world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	
	
	public static class NbUnitsComparator implements Comparator<Faction> {
	    @Override
	    public int compare(Faction a, Faction b) {
	        return a.getNbOfUnits() < b.getNbOfUnits() ? -1 
	        			: a.getNbOfUnits() == b.getNbOfUnits() ? 0 : 1;
	    }
	}
}

