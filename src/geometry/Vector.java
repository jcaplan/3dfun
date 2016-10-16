package geometry;

public class Vector {

	private double[] vector;
	
	
	public Vector (double x, double y, double z){
		vector = new double[4];
		vector[0] = x;
		vector[1] = y;
		vector[2] = z;
		vector[3] = 0;
	
		
	}
	
	public Vector() {
		vector = new double[4];
		vector[0] = 0;
		vector[1] = 0;
		vector[2] = 0;
		vector[3] = 0;
	}

	public Vector (Point p1, Point p2){
		vector = new double[4];
	
		
		vector[0] = p2.getx() - p1.getx();
		vector[1] = p2.gety() - p1.gety();
		vector[2] = p2.getz() - p1.getz();
		vector[3] = 0;
	
		
	}
	
	public Vector Vector(){
		return new Vector(0,0,0);
	}
	
	public double getx(){
		return vector[0];
	}
	public double gety(){
		return vector[1];
	}
	public double getz(){
		return vector[2];
	}
	public double[] asArray(){
		
		return vector;
	}
	
	public void setX(double x){
		vector[0] = x;
	}
	
	public void setY(double y){
		vector[1] = y;
	}
	
	public void setZ(double z){
		vector[2] = z;
	}
	
	@Override
	public String toString() {
		return "" + getx() + "," + gety() + "," + getz();
	}

	public void set(double x, double y, double z) {
		setX(x);
		setY(y);
		setZ(z);
		
	}
}
