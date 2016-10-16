package geometry;

import java.util.ArrayList;

public class Line {

	private Point points[] = new Point[2];
	
	public Line(Point p1, Point p2){
		points[0] = p1;
		points[1] = p2;
			
	}
	
	public Line(Point p, Vector v){
		points[0] = p;
		points[1] = new Point(p.getx() + v.getx(),
							  p.gety() + v.gety(),
							  p.getz() + v.getz());
	}
	
	public Line(int x0, int y0, int z0, int x1, int y1, int z1){
		points[0] = new Point(x0,y0,z0);
		points[1] = new Point(x1,y1,z1);
	}
	
	
	public Line(Line line) {
		points[0] = new Point(line.getStartPoint());
		points[1] = new Point(line.getEndPoint());
	}

	public Vector getComponents() {
		double dx = points[1].getx() - points[0].getx();
		double dy = points[1].gety() - points[0].gety();
		double dz = points[1].getz() - points[0].getz();
		
		return new Vector(dx, dy, dz);
	}

	public Point getStartPoint() {
		return points[0];
	}
	
	public Point getEndPoint() {
		return points[1];
	}
	
	
	@Override 
	public String toString() {
		String s = "";
		
		if (points[0].compareTo(points[1]) > 0){
			s += points[0] + "," + points[1];
		} else {
			s += points[1] + "," + points[0];
		}
		return s;
	}

	public void move(int dx, int dy, int dz) {
		getStartPoint().move(dx, dy, dz);
		getEndPoint().move(dx, dy, dz);
		
	}
	
	public Line translate(Point offset) {
		Point sp = getStartPoint();
		Point ep = getEndPoint();
		Line tl = new Line(new Point(sp.getx() + offset.getx(),
									 sp.gety() + offset.gety(),
									 sp.getz() + offset.getz()),
						   new Point(ep.getx() + offset.getx(),
								     ep.gety() + offset.gety(),
								     ep.getz() + offset.getz())
		);
		return tl;
	}
	
	public Line rotate(double thetaX, double thetaY, double thetaZ) {
		Line rl = new Line(this);
		rl.getStartPoint().rotate(thetaX,thetaY,thetaZ);
		rl.getEndPoint().rotate(thetaX,thetaY,thetaZ);
		return rl;
	}

	public Line rotate(Vector rot) {
		return rotate(rot.getx(),rot.gety(),rot.getz());
	}

	public Point getMidpoint() {
		double x = (getStartPoint().getx() + getEndPoint().getx()) / 2;
		double y = (getStartPoint().gety() + getEndPoint().gety()) / 2;
		double z = (getStartPoint().getz() + getEndPoint().getz()) / 2;
		return new Point(x,y,z);
	}
	
}
