package hillbillies.model.expression;

import hillbillies.model.Log;
import hillbillies.model.Coordinate;

public class LogPosition extends Expression<Coordinate> {
	
	
	public LogPosition() {
		
	}
	
	
	@Override 
	public Coordinate evaluate() {
		Coordinate logCoordinateNull = null;
		for (Log log: getTask().getAssignedUnit().getWorld().getAllLogs()) {
			return log.getCoordinate();
		}
		return logCoordinateNull;
	}
	
	
	
	
}
