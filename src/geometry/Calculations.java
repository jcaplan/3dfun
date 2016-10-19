package geometry;

public class Calculations {

	public static Vector crossProduct(Vector v1, Vector v2){
		
		Vector cross = new Vector();
		
		cross.setX(v1.gety()*v2.getz() - v1.getz()*v2.gety());
		cross.setY(v1.getz()*v2.getx() - v1.getx()*v2.getz());
		cross.setZ(v1.getx()*v2.gety() - v1.gety()*v2.getx());
		
		return cross;
		
	}

	static double x0;
	static double y0;
	static double z0;
	
	static Vector gradient;
	static double dx;
	static double dy;
	static double dz;
	
	static double A;
	static double B;
	static double C;
	static double D;
	
	
	static double t;
	
	public static Point intersectionLineAndPlane(Line l, Plane plane){
		Point p = new Point(0,0,0);
		intersectionLineAndPlane(l, plane, p);
		return p;
	}

			
	public static void intersectionLineAndPlane(Line l, Plane plane, Point point){
		
		x0 = l.getStartPoint().getx();
		y0 = l.getStartPoint().gety();
		z0 = l.getStartPoint().getz();
		
		gradient = l.getComponents();
		dx = gradient.getx();
		dy = gradient.gety();
		dz = gradient.getz();
		
		A = plane.getA();
		B = plane.getB();
		C = plane.getC();
		D = plane.getD();
		
		
		t = (A*x0 + B*y0 + C*z0 + D) / -(A*dx + B*dy + C*dz);
		//System.out.println(slopeZ*t);
		
		
		point.set(x0 + dx*t, y0+dy*t, z0+dz*t);
		
	}
	
}
