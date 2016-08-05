package hillbillies.model;

public abstract class GameObject extends TimeSubject {

	@Override
	public void advanceTime(double dt) {
		// TODO Auto-generated method stub

	}
	
	protected void updatePosition(double dt) throws IllegalPositionException {
		double[] newPosition = new double[3];
		for (int i=0; i<3; i++) {
			newPosition[i] = getPosition()[i] + getVelocity()[i]*dt;
		}
		setPosition(newPosition);
	}
	
	protected void setPosition(double[] position) throws IllegalPositionException {
		if (! canHaveAsPosition(convertPositionToCoordinate(position)) )
			throw new IllegalPositionException(position);
		this.position[0] = position[0];
		this.position[1] = position[1];
		this.position[2] = position[2];
	}
	
	protected abstract double[] getVelocity() throws IllegalPositionException;

}
