package hillbillies.model;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Task> {

	
	@Override
	public int compare(Task o1, Task o2) {
		if (o1.getPriority() < o2.getPriority()) {
			return -1;
		} else if (o1.getPriority() > o2.getPriority()) {
			return 1;
		}
		return 0;
	}

}
