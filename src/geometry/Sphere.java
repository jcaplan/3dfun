package geometry;


/*
 * 
 * http://blog.andreaskahler.com/2009/06/creating-icosphere-mesh-in-code.html
 * http://www.salsburg.com/geod/geodesicmath.pdf
 * http://mathworld.wolfram.com/GeodesicDome.html
 * 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Sphere extends Shape{

	double radius;
	int frequency;
	Point position;
	int pointCount;
	public static final int DEFAULT_FREQUENCY = 1;
	public final int NUMPOINTS;
	public final int NUMLINES;
	
	public Sphere(double radius) {
		this(radius, new Point(0,0,0), DEFAULT_FREQUENCY);
	}
	
	public Sphere(double radius, int frequency) {
		this(radius, new Point(0,0,0), frequency);
	}
	
	public Sphere(double radius, Point position, int frequency){
		this.radius = radius;
		this.frequency = frequency;
		this.position = position;
		this.NUMPOINTS = 10 * (int)Math.pow(4, frequency - 1) + 2;
		this.NUMLINES = 30 * (int)Math.pow(4, frequency - 1);
		this.pointCount = 0;
		
		initPointsAndLines();
	}

	@Override
	public void updateShape() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void initPointsAndLines() {
		points = new Point[NUMPOINTS];
		lines = new Line[NUMLINES];
		
		//Starting points 
		
		double t = radius * (1 + Math.sqrt(5.0)) / 2.0;
		
		points[0] = new Point(-radius, t, 0);
		points[1] = new Point( radius, t, 0);
		points[2] = new Point(-radius, -t, 0);
		points[3] = new Point( radius, -t, 0);

		points[4] = new Point( 0, -radius, t);
		points[5] = new Point( 0, radius, t);
		points[6] = new Point( 0, -radius, -t);
		points[7] = new Point( 0, radius, -t);

		points[8] = new Point( t, 0, -radius);
		points[9] = new Point( t, 0, radius);
		points[10] = new Point(-t, 0, -radius);
		points[11] = new Point(-t, 0, radius);
		pointCount = 12;
		
		ArrayList<Triangle> triangles = new ArrayList<>();
		
		// 5 faces around point 0
		triangles.add(new Triangle(points[0], points[11], points[5]));
		triangles.add(new Triangle(points[0], points[5], points[1]));
		triangles.add(new Triangle(points[0], points[1], points[7]));
		triangles.add(new Triangle(points[0], points[7], points[10]));
		triangles.add(new Triangle(points[0], points[10], points[11]));

		// 5 adjacent faces
		triangles.add(new Triangle(points[1], points[5], points[9]));
		triangles.add(new Triangle(points[5], points[11], points[4]));
		triangles.add(new Triangle(points[11], points[10], points[2]));
		triangles.add(new Triangle(points[10], points[7], points[6]));
		triangles.add(new Triangle(points[7], points[1], points[8]));

		// 5 faces around point 3
		triangles.add(new Triangle(points[3], points[9], points[4]));
		triangles.add(new Triangle(points[3], points[4], points[2]));
		triangles.add(new Triangle(points[3], points[2], points[6]));
		triangles.add(new Triangle(points[3], points[6], points[8]));
		triangles.add(new Triangle(points[3], points[8], points[9]));

		// 5 adjacent faces
		triangles.add(new Triangle(points[4], points[9], points[5]));
		triangles.add(new Triangle(points[2], points[4], points[11]));
		triangles.add(new Triangle(points[6], points[2], points[10]));
		triangles.add(new Triangle(points[8], points[6], points[7]));
		triangles.add(new Triangle(points[9], points[8], points[1]));
			 	
		
		for(int i = 1; i < frequency; i++){
			triangles = subdivideTriangles(triangles);
		}
		generateLines(triangles);
		updatePointPositions();
	}

	private void updatePointPositions() {
		for (Point p : points) {
			Line l = new Line(new Point(0,0,0), p);
			Vector slopes = l.getComponents();
			double ratio = radius / Math.sqrt((Math.pow(slopes.getx(),2) + Math.pow(slopes.gety(), 2)
					+ Math.pow(slopes.getz(), 2)));
			p.setx(ratio * p.getx());
			p.sety(ratio * p.gety());
			p.setz(ratio * p.getz());
		}
	}

	private ArrayList<Triangle> subdivideTriangles(ArrayList<Triangle> triangles) {
		HashMap<String,Point> seenLines = new HashMap<>();
		ArrayList<Triangle> newTriangles = new ArrayList<>();
		for (Triangle t : triangles) {
			Point[] midpoints = getMidpoints(t.getLines(), seenLines);
			newTriangles.add(new Triangle(t.points[0], midpoints[0], midpoints[2]));
			newTriangles.add(new Triangle(t.points[1], midpoints[1], midpoints[0]));
			newTriangles.add(new Triangle(t.points[2], midpoints[2], midpoints[1]));
			newTriangles.add(new Triangle(midpoints[0], midpoints[1], midpoints[2]));
		}
		return newTriangles;
	}

	private Point[] getMidpoints(Line[] lines, HashMap<String, Point> seenLines) {
		Point[] midpoints = new Point[lines.length];
		for (int i = 0; i < lines.length; i++) {
			Line l = lines[i];
			if (! seenLines.containsKey(l.toString())) {
				Point midpoint = l.getMidpoint();
				midpoints[i] = midpoint;
				points[pointCount++] = midpoint;
				seenLines.put(l.toString(), midpoint);
				
			} else {
				midpoints[i] = seenLines.get(l.toString());
			}
		}
		return midpoints;
	}

	private void generateLines(ArrayList<Triangle> triangles) {
		HashSet<String> lineSeen = new HashSet<>();
		
		int lineCount = 0;
		for (Triangle t : triangles) {
			Line[] tlines = t.getLines();
			for (Line l : tlines) {
				if (!lineSeen.contains(l.toString())){
					lines[lineCount] = l;
					lineCount++;
					lineSeen.add(l.toString());
				}
			}
			
		}
		
	}	
	
}
