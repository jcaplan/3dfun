package main;

import geometry.Cube;
import geometry.Plane;
import geometry.Point;
import geometry.Shape;
import geometry.Sphere;
import geometry.Vector;
import graphics.Rasterization;

import java.util.ArrayList;

public class World {
	
	private int time;
	Cube cube;
	Sphere sphere;
	Rasterization raster;
	
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	private Plane screenPlane;
	private Point camera;
	
	
	
	public World (Rasterization raster){
		
		
		this.raster = raster;
		
		cube = new Cube(100, 0, 0, 0);
//		shapes.add(cube);
//		shapes.add(new Cube(50, 100, -10, 200));
//		shapes.add(new Cube(120, -10, 4, 23));
		sphere = new Sphere(200,5);
		shapes.add(sphere);
		
		time = 0;
		

		camera = new Point(0,0,-raster.width*2);
		screenPlane = new Plane(0,0,1,0);
	
		raster.setPerspective(camera,screenPlane);
	}
	

	double angle = 0;	
	public void tick(){
		time++;

		
		if (time % 1 == 0) {
			sphere.setRotation(angle, 0, 2 * angle);
			angle += Math.toRadians(1);
			sphere.setPosition(100*Math.cos(Math.toRadians(time)),0,600*Math.sin(Math.toRadians(time*2) + 100));
//			cube.setRotation(Math.toRadians(45 + angle), Math.toRadians(45- 1.4*angle), Math.toRadians(90));
//			cube.move(0, 0, -10);
		}
		raster.clearPixels();
		raster.render(shapes); 
		
	}
	
	public int getTime(){
		return time;	
	}
	
}
