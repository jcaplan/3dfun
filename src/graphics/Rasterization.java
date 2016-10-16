package graphics;

import geometry.Calculations;
import geometry.Line;
import geometry.Plane;
import geometry.Point;
import geometry.Shape;
import geometry.Vector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;


public class Rasterization extends Render {

	
	public final int BLACK = 0;
	Point camera;
	Plane screenPlane;

	
	public Rasterization(int width, int height) {
		super(width, height);

		clearPixels();

		try {
			Texture floor = new Texture("textures/floor.png");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void render(Collection<Shape> shapes) {
		for (Shape shape : shapes){
			render(shape);
		}
	}
	
	public void render(Shape shape){
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
					Calculations.intersectionLineAndPlane(startLine, screenPlane),
					Calculations.intersectionLineAndPlane(endLine, screenPlane)		
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

	public void drawCircle(int xCenter, int yCenter, int radius) {
		for (int x = 0, y = radius, dec = 3 - 2 * radius; x <= y; x++) {
			drawPixel(xCenter + x, yCenter + y);
			drawPixel(xCenter + x, yCenter - y);
			drawPixel(xCenter - x, yCenter + y);
			drawPixel(xCenter - x, yCenter - y);
			drawPixel(xCenter + y, yCenter + x);
			drawPixel(xCenter + y, yCenter - x);
			drawPixel(xCenter - y, yCenter + x);
			drawPixel(xCenter - y, yCenter - x);

			if (dec >= 0) {
				dec += -4 * (y--) + 4;
			}

			dec += 4 * x + 6;
		}
	}

	public void drawEllipse(int xCenter, int yCenter, int a, int b) {
		int a2 = a * a, b2 = b * b, fa2 = 4 * a2, fb2 = 4 * b2;
		for (int x = 0, y = b, sigma = 2 * b2 + a2 * (1 - 2 * b); b2 * x <= a2 * y; x++) {
			drawPixel(xCenter + x, yCenter + y);
			drawPixel(xCenter - x, yCenter + y);
			drawPixel(xCenter + x, yCenter - y);
			drawPixel(xCenter - x, yCenter - y);

			if (sigma >= 0) {
				sigma += fa2 * (1 - (y--));
			}
			sigma += b2 * (4 * x + 6);

		}
		for (int x = a, y = 0, sigma = 2 * a2 + b2 * (1 - 2 * a); a2 * y <= b2 * x; y++) {
			drawPixel(xCenter + x, yCenter + y);
			drawPixel(xCenter - x, yCenter + y);
			drawPixel(xCenter + x, yCenter - y);
			drawPixel(xCenter - x, yCenter - y);

			if (sigma >= 0) {
				sigma += fb2 * (1 - x);
				x--;
			}
			sigma += a2 * (4 * y + 6);

		}
	}
	

	public void setPerspective(Point camera, Plane screenPlane) {
		this.camera = camera;
		this.screenPlane = screenPlane;
		
	}
	

	


}
