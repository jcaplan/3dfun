package main;

import geometry.Cube;
import geometry.Floor;
import geometry.Panel;
import geometry.Plane;
import geometry.Point;
import geometry.Shape;
import geometry.Sphere;
import geometry.Vector;
import graphics.Rasterization;

import java.awt.Color;
import java.util.ArrayList;

public class World {
	
	private int time;
	Cube cube;
	Sphere sphere;
	Rasterization raster;
	Floor floor;
	Panel panel;
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	private Plane screenPlane;
	private Point camera;
	
	
	
	public World (Rasterization raster){
		
		
		this.raster = raster;
		
		cube = new Cube(50, -300, -10, 200);
		shapes.add(cube);
		sphere = new Sphere(200,3);
		shapes.add(sphere);
		panel = new Panel(600,500,new Point(0,0,600));
		panel.setColor(Color.RED);
		panel.setRotationDeg(0, -78, 0);
		shapes.add(panel);
		
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
			sphere.setPosition(400 + 100*Math.cos(Math.toRadians(time)),0,600*Math.sin(Math.toRadians(time*2) + 100));
			cube.setRotation(Math.toRadians(45 + 5*angle), Math.toRadians(45- 1.4*angle), Math.toRadians(90));
			panel.setRotation(0, Math.toRadians(45- 120*angle), 0);
			
		}
		raster.clearPixels();
		raster.render(shapes); 
		
	}
	
	public int getTime(){
		return time;	
	}
	
}
