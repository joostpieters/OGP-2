package hillbillies.model;

import java.util.Comparator;


class NbUnitsComparator implements Comparator<Faction> {
	
	
	@Override
	public int compare(Faction a, Faction b) {
		if (a.getNbUnits() < b.getNbUnits()) {
			return -1;
		} else if (a.getNbUnits() > b.getNbUnits()) {
			return 1;
		}
		return 0;
	}
	

}
