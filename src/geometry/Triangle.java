package geometry;

public class Triangle {

	public static final int NUMSIDES = 3;
	public static final int NUMPOINTS = 3;
	
	Point[] points;
	
	public Triangle(Point point1, Point point2, Point point3) {
		points = new Point[3];
		points[0] = point1;
		points[1] = point2;
		points[2] = point3;
	}
	
	
	public Triangle(Point[] points) {
		points = new Point[3];
		for (int i = 0; i < NUMPOINTS; i++){
			this.points[i] = points[i];
		}
	}
	
	public Line[] getLines() {
		Line[] lines = new Line[3];
		lines[0] = new Line(points[0],points[1]);
		lines[1] = new Line(points[1],points[2]);
		lines[2] = new Line(points[2],points[0]);
		return lines;
	}
	
	public String toString() {
		String s = String.format("%s,%s,%s",points[0],points[1],points[2]);
		return s;
	}
	

}
