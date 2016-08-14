package hillbillies.model.expression;

import hillbillies.model.Coordinate;

public class LiteralPosition extends Expression<Coordinate> {
	
	private final Coordinate coordinate;

	public LiteralPosition(int x, int y, int z) {
		this.coordinate = new Coordinate(x, y, z);
	}

	
	@Override 
	public Coordinate evaluate() {
		
		return this.coordinate;
	}
	
	
	
	
}
