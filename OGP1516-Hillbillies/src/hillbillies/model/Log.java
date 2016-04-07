package hillbillies.model;

public class Log {
	
	private World world;
	private double[] position;
	
	public Log(int x, int y, int z) {
		double xD = this.world.getCubeLength()/2 + (double) x;
		double yD = this.world.getCubeLength()/2 + (double) y;
		double zD = this.world.getCubeLength()/2 + (double) z;
		
		this.position = new double[3]; 
		setPosition(xD, yD, zD);
	}
	
	private void setPosition(double x, double y, double z) {
		this.position[0] = x;
		this.position[1] = y;
		this.position[2] = z;
	}
	
	public double[] getPosition() {
		return this.position;
	}
	
}
