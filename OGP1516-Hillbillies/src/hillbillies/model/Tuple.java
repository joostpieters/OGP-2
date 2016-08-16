package hillbillies.model;
import java.util.*;

public class Tuple {

	public Tuple(Coordinate cube, int n) { 
		this.cube = cube; 
		this.n = n; 
		this.isChecked = false;
	} 

	public static boolean containsCubeWithSmallerN(List<Tuple> path, Coordinate cube, Tuple startTuple) {
		for (Tuple tuple: path) {
			if (tuple.cube.equals(cube) && tuple.n < startTuple.n) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean containsCube(List<Tuple> path, Coordinate cube) {
		for (Tuple tuple: path) {
			if (tuple.cube.equals(cube)) {
				return true;
			}
		}
		return false;
	}
	
	public static Tuple getCubeTuple(List<Tuple> path, Coordinate cube) {
		for (Tuple tuple: path) {
			if (tuple.cube.equals(cube)) {
				return tuple;
			}
		}
		return null;
	}
	
	
	public static boolean hasNext(List<Tuple> path) {
		for (Tuple tuple: path) {
			if (!tuple.isChecked)
				return true;
		}
		return false;
	}
	
	public static Tuple getNext(List<Tuple> path) {
		for (Tuple tuple: path) {
			if (!tuple.isChecked)
				return tuple;
		}
		return null;
	}
	
	
	@Override
	public String toString() {
		return "(" + this.cube.toString() + "," + n + "," + isChecked + ")";
	}
	
	public boolean isChecked = false;
	
	public final Coordinate cube;

	public final int n; 
	
	
}
