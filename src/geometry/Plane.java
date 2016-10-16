package geometry;

public class Plane {

	double[] plane;
	
	public Plane(double a, double b, double c, double d){
		
		//ax+by+cz+d=0
		plane = new double[4];
		plane[0] = a;
		plane[1] = b;
		plane[2] = c;
		plane[3] = d;
		
	}
	
	
	public Plane Plane(Vector v1, Vector v2){
		
		Vector cross = Calculations.crossProduct(v1, v2);
		
		
		
		return new Plane(cross.getx(), cross.gety(), cross.getz(), 0);
	}
	
	
	
	public double getA(){
		return plane[0];
	}

	public double getB(){
		return plane[1];
	}
	
	public double getC(){
		return plane[2];
	}
	
	public double getD(){
		return plane[3];
	}
	
	public double[] asArray(){
		
	return plane;
		
	}
	
	
	
	
	
}
