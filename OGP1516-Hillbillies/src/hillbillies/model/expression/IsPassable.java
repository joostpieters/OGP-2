package hillbillies.model.expression;


import hillbillies.model.Coordinate;

public class IsPassable extends Expression<Boolean> {
	
	private final Expression<Coordinate> position;

	public IsPassable(Expression<Coordinate> position) {
		this.position = position;
		
	}
	
	@Override
	public Boolean evaluate() {
		this.position.setTask(getTask());
		
		return getTask().getAssignedUnit().getWorld().getCubeTypeAt(position.evaluate()).isPassable();
	}

}
