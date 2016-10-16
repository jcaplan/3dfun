package geometry;

public class Point implements Comparable{

	double[] point;
	
	
	public Point (double x, double y, double z){
		point = new double[4];
		point[0] = x;
		point[1] = y;
		point[2] = z;
		point[3] = 1;
	
		
	}
	
	
	public Point(Point p) {
		point = new double[4];
		point[0] = p.getx();
		point[1] = p.gety();
		point[2] = p.getz();
		point[3] = 1;
	}


	public double getx(){
		return point[0];
	}
	public double gety(){
		return point[1];
	}
	public double getz(){
		return point[2];
	}
	public double[] asArray(){
		
		return point;
		
	}
	
	public void setx(double x){
		point[0] = x;
	}
	
	public void sety(double y){
		point[1] = y;
	}
	
	public void setz(double z){
		point[2] = z;
	}


	public void set(double x, double y, double z) {
		setx(x);
		sety(y);
		setz(z);
		
	}
	
	@Override
	public String toString() {
		String s = "";
		s += "(" + getx() + "," + gety() + "," + getz() + ")";
		return s;
	}


	public void move(double dx, double dy, double dz) {
		point[0] += dx;
		point[1] += dy;
		point[2] += dz;
	}

	public void rotate(Vector rot) {
		rotate(rot.getx(),rot.gety(),rot.getz());	
	}

	public void rotate(double thetaX, double thetaY, double thetaZ) {
			double x = getx();
			double y = gety();
			double z = getz();
			
			double cosine = Math.cos(thetaX);
			double sine = Math.sin(thetaX);

			double xx, yy, zz;
			
			//Rotation about x axis:
			
			yy = (y) * cosine - (z) * sine;
			zz = (y) * sine + (z) * cosine;

			//Rotation about y axis
			
			cosine = Math.cos(thetaY);
			sine = Math.sin(thetaY);
			
			xx = x * cosine + zz * sine;
			zz = -x * sine + zz * cosine;

			//Rotation about z axis:
			cosine = Math.cos(thetaZ);
			sine = Math.sin(thetaZ);
			
			x = xx;
			y = yy;
			
			xx = x*cosine - y*sine;
			yy = x*sine + y*cosine;
			
			setx(xx);
			sety(yy);
			setz(zz);
	}


	public void set(Point camera) {
		setx(camera.getx());
		sety(camera.gety());
		setz(camera.getz());
	}


	@Override
	public int compareTo(Object arg0) {
		Point p = (Point) arg0;
		double threshold = 0.00001;
		for (int i = 0; i < 3; i++) {
			if (point[i] > p.point[i] + threshold) {
				return 1;
			} else if (point[i]  < p.point[i] - threshold) {
				return -1;
			}
		}
		return 0;
	}


	
}
