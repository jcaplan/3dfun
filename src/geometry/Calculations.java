package geometry;

public class Calculations {

	public static Vector crossProduct(Vector v1, Vector v2){
		
		Vector cross = new Vector();
		
		cross.setX(v1.gety()*v2.getz() - v1.getz()*v2.gety());
		cross.setY(v1.getz()*v2.getx() - v1.getx()*v2.getz());
		cross.setZ(v1.getx()*v2.gety() - v1.gety()*v2.getx());
		
		return cross;
		
	}

	public static Point intersectionLineAndPlane(Line l, Plane p){
		
		double x0 = l.getStartPoint().getx();
		double y0 = l.getStartPoint().gety();
		double z0 = l.getStartPoint().getz();
		
		Vector gradient = l.getComponents();
		double dx = gradient.getx();
		double dy = gradient.gety();
		double dz = gradient.getz();
		
		double A = p.getA();
		double B = p.getB();
		double C = p.getC();
		double D = p.getD();
		
		
		double t = (A*x0 + B*y0 + C*z0 + D) / -(A*dx + B*dy + C*dz);
		//System.out.println(slopeZ*t);
		
		Point i = new Point(x0 + dx*t, y0+dy*t, z0+dz*t);
		
		return i;
	}
	
}
