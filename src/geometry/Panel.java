package geometry;

public class Panel extends Shape{

	double width;
	double height;
	Plane plane;
	
	// All panels start on xy plane oriented in the +z direction.
	// use rotation to define orientation
	
	public Panel(double width, double height, Point center){
		this.width = width;
		this.height = height;
		this.position = center;
		rotation = new Vector(0,0,0);
		initPointsAndLines();
	}
	
	@Override
	public void updateShape() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initPointsAndLines() {
		
		points = new Point[4];
		
		points[0] = new Point(-width/2, -height/2, 0);
		points[1] = new Point(-width/2,  height/2, 0);
		points[2] = new Point( width/2,  height/2, 0);
		points[3] = new Point( width/2, -height/2, 0);
		
		lines = new Line[4];
		
		lines[0] = new Line(points[0], points[1]);
		lines[1] = new Line(points[1], points[2]);
		lines[2] = new Line(points[2], points[3]);
		lines[3] = new Line(points[3], points[0]);
		
		
	}

	public Point[] getTransformedPoints() {
		Point[] tpoints = new Point[4];
		int count = 0;
		for(Point p : points){
			Point tp = new Point(p);
			tp.rotate(rotation);
			tp.move(position);
			tpoints[count++] = tp;
		}
		return tpoints;
	}

	public Plane getPlane() {
		Vector normal = Calculations.crossProduct(lines[0].getComponents(), lines[1].getComponents());
		double a = normal.getx();
		double b = normal.gety();
		double c = normal.getz();
		double d = -(a * points[0].getx() + b * points[0].gety() + c * points[0].getz());
		return new Plane(a, b, c, d);
	}
	
	
	
	
	
}
