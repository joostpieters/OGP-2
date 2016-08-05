package hillbillies.model;

import java.util.Arrays;

public class Coordinate {
	
	
	public Coordinate(int x, int y, int z) {
		setX(x);
		setY(y);
		setZ(z);
	}
	
	public Coordinate(int[] coordinates) {
		set(coordinates);
	}
	
	public int get(int i) {
		if (i < 0 || i > 2)
			throw new IllegalArgumentException();
		return this.coordinates[i];
	}
	
	private void setX(int x) {
		this.coordinates[0] = x;
	}
	
	private void setY(int y) {
		this.coordinates[1] = y;
	}
	
	private void setZ(int z) {
		this.coordinates[2] = z;
	}
	
	
	public void set(int i, int coord) {
		if (i < 0 || i > 2)
			throw new IllegalArgumentException();
		this.coordinates[i] = coord;
	}
	
	public void set(int[] coord) {
		if (coord.length != 3)
			throw new IllegalArgumentException();
		
		this.coordinates = coord;
	}
	
	public int[] getCoordinates() {
		return this.coordinates;
	}
	
	private int[] coordinates = new int[3];
	
	//private double[] position;
	
	
	public boolean equals(Object other) {
		if (other instanceof Coordinate && Arrays.equals(this.getCoordinates(),
				((Coordinate) other).getCoordinates()))
			return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		int sum = 0;
		for (int i=0; i<this.getCoordinates().length; i++) {
			sum += Math.pow(10, 2*i) * this.get(i);
		}
		return sum;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(this.getCoordinates());
	}
	
}
