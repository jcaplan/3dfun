package graphics;

import geometry.Calculations;
import geometry.Line;
import geometry.Panel;
import geometry.Plane;
import geometry.Point;
import geometry.Shape;
import geometry.Vector;

import java.util.Arrays;
import java.util.Collection;
import java.awt.Color;

public class Rasterization extends Render {

	
	public final int BLACK = 0;
	Point camera;
	Plane screenPlane;

	
	public Rasterization(int width, int height) {
		super(width, height);

		clearPixels();

	}

	public void render(Collection<Shape> shapes) {
		for (Shape shape : shapes){
			if (shape.hasTexture())
				renderTexture(shape);
			else  if (shape.hasColour())
				renderColor(shape);
			else
				renderOutline(shape);
		}
	}
	
	private Point getProjectedPoint(Point p) {
		return getProjectedPoint(new Line(p,camera));
	}
	
	private Point getProjectedPoint(Line l){
		return Calculations.intersectionLineAndPlane(l, screenPlane);
	}
	
	Point newpoint = new Point(0,0,0);
	Line l = new Line(0,0,0,0,0,0);
	Point screenpoint = new Point(0,0,0);
	Color c = new Color(0);
	private void renderColor(Shape shape) {
		
		// Determine the 4 projected corners and then loop through setting color
		assert(shape instanceof Panel);
		Panel panel = (Panel) shape;
		Point[] tPoints = panel.getTransformedPoints();
		float[] colors = new float[4];
		Plane p = new Plane(tPoints);
		Point[] points = new Point[tPoints.length];
		for (int i = 0; i < points.length; i++){
			
			points[i] = getProjectedPoint(tPoints[i]);
			//offset world origin to screen origin
			points[i].move(width/2, height/2, 0);
		}

		int xStart = getLowestX(points);
		int xEnd = getHighestX(points);
		int yStart, yEnd;
		orderPointsTLBLTRBR(points);
		
		
		for (int x = xStart; x < xEnd; x++){
			
			yStart = getYValueForLine(points[1],points[3], x);
			yEnd = getYValueForLine(points[0],points[2], x);
			
			//y start is the line BL -> BR for x
			for(int y = yStart; y < yEnd; y++){
				screenpoint.set(x - width/2,y-width/2,0);
				l.setPoints(screenpoint, camera);
				Calculations.intersectionLineAndPlane(l, p, newpoint);

				double brightness = (2100 - newpoint.getz())/ 3200.0;
//
				if (brightness < 0) {
					brightness = 0;
				}
				if (brightness > 1) {
					brightness = 1;
				}
				shape.getColor().getRGBColorComponents(colors);
				
				for(int i = 0; i < 3; i++){
					colors[i] = (float) (colors[i] * brightness);
				}
				int rgb = new Color(colors[0],colors[1],colors[2]).getRGB();
				drawPixel(x,y,rgb);
			}
		}

	}

	private double getDistance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.getx() - p2.getx(), 2) +
				Math.pow(p1.gety() - p2.gety(), 2));
	}

	private Point getIntersection(double[] l1, double[] l2) {
		double x = (l2[1] - l1[1]) / (l1[0] - l2[0]);
		double y = l1[0]*x + l1[1];
		return new Point(x,y,0);
	}

	private double[] getLineParameters(Point p1, Point p2) {
		double[] params = new double[2];
		double slope = (p2.gety() - p1.gety()) / (p2.getx() - p1.getx());
		double intercept = p1.gety() - slope * p1.getx();
		params[0] = slope;
		params[1] = intercept;
		return params;
	}

	private int getYValueForLine(Point p1, Point p2, int x) {
		double slope = (p2.gety() - p1.gety()) / (p2.getx() - p1.getx());
		double intercept = p1.gety() - slope * p1.getx();
		
		int y = (int)((slope * x) + intercept);
		return y;
	}

	private int getHighestX(Point[] points) {
		int highestX = Integer.MIN_VALUE;
		for (Point p : points){
			int x = (int) p.getx();
			if (x > highestX) {
				highestX = x;
			}
		}
		return highestX;
	}

	private int getLowestX(Point[] points) {
		int lowestX = Integer.MAX_VALUE;
		for (Point p : points){
			int x = (int) p.getx();
			if (x < lowestX) {
				lowestX = x;
			}
		}
		return lowestX;
	}

	private void orderPointsTLBLTRBR(Point[] points) {
		//top left bottom left top right bottom right
		Arrays.sort(points, Point.sortByIncreasingX);
		for(int i = 0; i < 4; i+= 2){
			//sort top 2 and bottom 2 by y
			if( points[i].gety() < points[i + 1].gety()){
				Point temp = points[i];
				points[i] = points[i + 1];
				points[i + 1] = temp;
			}
		}
	}

	private void renderTexture(Shape shape) {
		// TODO Auto-generated method stub
		
	}

	public void renderOutline(Shape shape){
		render(shape, BLACK);
	}
	
	public void render(Shape shape, int color) {
		for (Line l : shape.applyTransformations()) {
			//Is the line in front o
			Line startLine = new Line(l.getStartPoint(), camera);
			
			Line endLine = new Line(l.getEndPoint(), camera);
		
			if (startLine.getComponents().getz() > 0 && endLine.getComponents().getz() > 0) {
				continue;
			}
			
			Line projected = new Line(
					getProjectedPoint(startLine),
					getProjectedPoint(endLine)		
					);
			//offset world origin to screen origin
			projected.move(width/2, height/2, 0);
			drawLine(projected, color);
		}
	}
	
	public void drawLine(Line line, int color) {

		Vector gradient = line.getComponents();
		
		if (gradient.getx() == 0 && gradient.gety() == 0){
			return;
		}
		// increment or decrement depending on direction of line:
		int sx, sy;
		if (gradient.getx() > 0) {
			sx = 1;
		} else if (gradient.getx() < 0) {
			sx = -1;
			gradient.setX(-gradient.getx());
		} else {
			sx = 0;
		}

		if (gradient.gety() > 0) {
			sy = 1;
		} else if (gradient.gety() < 0) {
			sy = -1;
			gradient.setY(-gradient.gety());
		} else {
			sy = 0;
		}

		int ax = 2 * (int)gradient.getx();
		int ay = 2 * (int)gradient.gety();
		int x = (int)line.getStartPoint().getx();
		int y = (int)line.getStartPoint().gety();
		if (gradient.gety() <= gradient.getx()) {
			// single step in x direction
			for (int decy = ay - (int)gradient.getx(); x != (int)line.getEndPoint().getx(); 
					x += sx, decy += ay) {
				drawPixel(x, y, color);
				if (decy >= 0) {
					decy -= ax;
					y += sy;
				}

			}
		} else {
			for (int decx = ax - (int)gradient.gety();y != (int)line.getEndPoint().gety();
					y += sy, decx += ax) {
				drawPixel(x, y, color);
				if (decx >= 0) {
					decx -= ay;
					x += sx;
				}
			}
		}
	}
	
	public void draw(Panel panel){
		
	}

	public void setPerspective(Point camera, Plane screenPlane) {
		this.camera = camera;
		this.screenPlane = screenPlane;
		
	}
	

	


}
