import java.util.Comparator;

public class Corner { // implements Comparable<Corner> {

	private int cornerX;
	private int cornerY;
	private double angle; // Angle in radians of point from center of blob. Use for sorting.

	public Corner() {
		super();
		this.cornerX = 0;
		this.cornerY = 0;
		this.angle = 0.0;
	}

	public Corner(int cornerX, int cornerY) {
		super();
		this.cornerX = cornerX;
		this.cornerY = cornerY;
		this.angle = 0.0;
	}

	public int getX() {
		return cornerX;
	}
	public void setX(int cornerX) {
		this.cornerX = cornerX;
	}
	public int getY() {
		return cornerY;
	}
	public void setY(int cornerY) {
		this.cornerY = cornerY;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public String toString() {
		double tempAngle = Math.round(angle * 100.0) / 100.0;
		return "(" + Integer.toString(cornerX) + "," + Integer.toString(cornerY) + "/" + Double.toString(tempAngle) + ")";
	}

	public String toCSV() {
		return Integer.toString(cornerX) + "," + Integer.toString(cornerY) + ",";
	}

	// public int compareTo(Corner compareCorner) {
    // 
	// 	int compareX = ((Corner) compareCorner).getX();
    // 
	// 	//ascending order
	// 	return this.cornerX - compareX;
    // 
	// 	//descending order
	// 	//return compareX - this.cornerX;
	// }

	public static Comparator<Corner> CornerXComparator
                          = new Comparator<Corner>() {

	    public int compare(Corner corner1, Corner corner2) {

	      int cornerX1 = corner1.getX();
	      int cornerX2 = corner2.getX();

	      // Ascending order
	      return cornerX1 - cornerX2;

	      // Descending order
	      // return cornerX2 - cornerX1;
	    }
	};
	public static Comparator<Corner> AngleComparator
                          = new Comparator<Corner>() {

	    public int compare(Corner corner1, Corner corner2) {

	      double angle1 = corner1.getAngle();
	      double angle2 = corner2.getAngle();

	      // Ascending order
	      return (int)(1000 * (angle1 - angle2)); // preserves 3 fractional digits of accuracy

	      // Descending order
	      // return angle2 - angle1;
	    }
	};
}