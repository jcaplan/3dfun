package graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {

	public int WIDTH;
	public int HEIGHT;
	public int[][] pixels;
	
	public Texture(String filename) throws FileNotFoundException, IOException {
		BufferedImage image = ImageIO.read(new FileInputStream(
				filename));
		int width = image.getWidth();
		int height = image.getHeight();
		int[] rgb = new int[width * height];
		pixels = new int[width][height];
		image.getRGB(0, 0, width, height, rgb, 0, width);
		for (int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				pixels[x][y] = rgb[x + y *width];
			}
		}
	}
}
