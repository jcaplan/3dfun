package geometry;

import graphics.Texture;

public class Floor extends Plane{

	private Texture texture;
	
	public Floor(double initialZ) {
		super(0, 0, 1, initialZ);
	}
	
	public Floor(double initialZ, Texture t){
		this(initialZ);
		texture = t;
	}

	
	
}
