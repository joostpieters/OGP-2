package hillbillies.model;

import java.util.Comparator;


class NbNitsComparator implements Comparator<Faction> {
	
	
	@Override
	public int compare(Faction a, Faction b) {
		if (a.getNbNits() < b.getNbNits()) {
			return -1;
		} else if (a.getNbNits() > b.getNbNits()) {
			return 1;
		}
		return 0;
	}
	

}
