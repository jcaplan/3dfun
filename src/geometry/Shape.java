package geometry;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Shape {

	public abstract void updateShape();
	public abstract void initPointsAndLines();
	
	Point[] points;
	Line[] lines;
	Point position = new Point(0,0,0);
	Vector rotation = new Vector();

	
	public Line[] getlines() {
		return lines;
	}
	
	
	public void setPosition(Point p){
		position = p;
	}
	
	public void setPosition(double x, double y, double z){
		if (position == null) {
			position = new Point(x,y,z);
		} else {
			position.setx(x);
			position.sety(y);
			position.setz(z);
		}
		
		
	}
	
	public Point getPosition(){
		return position;
	}
	
	public void updatePosition(double x, double y, double z){
		position.set(x,y,z);
	}

	public void move(int dx, int dy, int dz) {
		position.move(dx,dy,dz);
	}
	
	
	public void setRotation(double thetax, double thetay, double thetaz){
		rotation.set(thetax,thetay,thetaz);
	}
	
	public ArrayList<Line> translate() {
		ArrayList<Line> translated = new ArrayList<>();
		System.out.println(position);
		for (Line l : getlines()){
			Point sp = l.getStartPoint();
			Point ep = l.getEndPoint();
			Line tl = new Line(new Point(sp.getx() + position.getx(),
										 sp.gety() + position.gety(),
										 sp.getz() + position.getz()),
							   new Point(ep.getx() + position.getx(),
									     ep.gety() + position.gety(),
									     ep.getz() + position.getz())
			);
			translated.add(tl);
		}
		
		return translated;
	}

	public ArrayList<Line> applyTransformations() {
		ArrayList<Line> result = new ArrayList<>();
		for (Line l : getlines()) {
			result.add(l.rotate(rotation).translate(position));
		}
		return result;
	}

}
