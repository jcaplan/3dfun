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
	
	
	public Plane(Point[] tPoints) {
		Vector v1 = new Vector(tPoints[0], tPoints[1]);
		Vector v2 = new Vector(tPoints[0], tPoints[2]);
		getPlaneFromVectors(v1, v2);
	}
	
	public Plane(Vector v1, Vector v2){
		getPlaneFromVectors(v1,v2);
	}

	
	@Override
	public String toString() {
		return String.format("%fx + %fy + %fz + %f", plane[0],
													 plane[1],
													 plane[2],
													 plane[3]);
	}
	
	private void getPlaneFromVectors(Vector v1, Vector v2) {

		Vector normal = Calculations.crossProduct(v1, v2);
		
		double a = normal.getx();
		double b = normal.gety();
		double c = normal.getz();
		double d = -(a*v1.getx() + b*v1.gety() + c*v1.getz());
		
		plane = new double[4];
		plane[0] = a;
		plane[1] = b;
		plane[2] = c;
		plane[3] = d;
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
