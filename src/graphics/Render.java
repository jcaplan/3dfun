package graphics;

public class Render {
	public final int width;
	public final int height;
	public final int pixels[][];

	public Render(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width][height];
	}

	protected void drawPixel(int x, int y) {
		if(checkDimensions(x,y))
			pixels[x][y] = 0;
	}

	private boolean checkDimensions(int x, int y) {
		return (x >= 0 && x < width && y >= 0 && y < height);
	}

	protected void drawPixel(int x, int y, int color) {
		if (checkDimensions(x,y))
			pixels[x][y] = color;
	}

	
	public void clearPixels(){
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x][y] = -1;
			}
		}

	}



}
