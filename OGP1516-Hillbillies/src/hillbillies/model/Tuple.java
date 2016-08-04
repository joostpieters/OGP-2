package hillbillies.model;
import java.util.*;

public class Tuple {

	public Tuple(int[] cube, int n) { 
		this.cube = cube; 
		this.n = n; 
	} 

	public static boolean containsCubeWithSmallerN(Queue<Tuple> path, int[] cube, Tuple startTuple) {
		for (Tuple tuple: path) {
			if (tuple.cube.equals(cube) && tuple.n < startTuple.n) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean containsCube(Queue<Tuple> path, int[] cube) {
		for (Tuple tuple: path) {
			if (tuple.cube.equals(cube)) {
				return true;
			}
		}
		return false;
	}
	
	
	public static boolean hasNext(Queue<Tuple> path) {
		for (Tuple tuple: path) {
			if (tuple.isChecked = false)
				return true;
		}
		return false;
	}
	
	public static Tuple getNext(Queue<Tuple> path) {
		for (Tuple tuple: path) {
			if (tuple.isChecked = false)
				return tuple;
		}
		return null;
	}
	
	
	public boolean isChecked = false;
	
	public final int[] cube;

	public final int n; 
	
	
}
