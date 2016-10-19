package geometry;

public class Cube extends Shape{

	private double length;
	public final int NUMLINES = 12;
	public final int NUMPOINTS = 8;
	
	public Cube(double length, double xCenter, double yCenter, double zCenter) {
		this.length = length;
		initPointsAndLines();
		setPosition(xCenter, yCenter, zCenter);
	}
	@Override
	public void updateShape() {
		
		double corner1x = length / 2;
		double corner1y =  - length / 2;
		double corner1z =  - length / 2;
			
		points[0].set(corner1x, corner1y, corner1z);
		points[1].set(corner1x + length, corner1y, corner1z);
		points[2].set(corner1x + length, corner1y + length, corner1z);
		points[3].set(corner1x, corner1y + length, corner1z);
		points[4].set(corner1x, corner1y, corner1z + length);
		points[5].set(corner1x + length, corner1y, corner1z + length);
		points[6].set(corner1x + length, corner1y + length, corner1z + length);
		points[7].set(corner1x, corner1y + length, corner1z + length);
		
	}

	@Override
	public void initPointsAndLines() {
		
		points = new Point[NUMPOINTS];
		lines = new Line[NUMLINES];
		
		for (int i = 0; i < NUMPOINTS; i++){
			points[i] = new Point(0,0,0);
		}
		lines[0] = new Line(points[0],points[1]);
		lines[1] = new Line(points[1],points[2]);
		lines[2] = new Line(points[2],points[3]);
		lines[3] = new Line(points[0],points[3]);
		
		lines[4] = new Line(points[4],points[5]);
		lines[5] = new Line(points[5],points[6]);
		lines[6] = new Line(points[6],points[7]);
		lines[7] = new Line(points[7],points[4]);

		lines[8] = new Line(points[0],points[4]);
		lines[9] = new Line(points[1],points[5]);
		lines[10] = new Line(points[2],points[6]);
		lines[11] = new Line(points[3],points[7]);
		
		
		updateShape();		
	}

}
