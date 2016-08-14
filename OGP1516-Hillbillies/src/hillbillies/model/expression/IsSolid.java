package hillbillies.model.expression;

import hillbillies.model.Coordinate;

public class IsSolid extends Expression<Boolean> {

	private final Expression<Coordinate> position;

	public IsSolid(Expression<Coordinate> position) {
		this.position = position;
	}
	
	@Override
	public Boolean evaluate() {
		return !getTask().getAssignedUnit().getWorld()
				.getCubeTypeAt(position.evaluate()).isPassable();
	}

}
