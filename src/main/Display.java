package main;

import graphics.Rasterization;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Display extends Canvas implements Runnable {

	
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	public static int SCALE = 1;
	private Thread thread;
	private boolean running;
	private Rasterization raster;
	int fps = 0;
	private World world;

	private BufferedImage img;
	private int[] pixel;

	public Display() {
		raster = new Rasterization(WIDTH, HEIGHT);
		//Set up screen and raster:
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixel = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	
		world = new World(raster);

	}

	private void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();

	}

	private void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			System.exit(0);
		}
	}

	

	@Override
	public void run() {

		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;
		//fps code:
		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;

			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					//System.out.println(frames + " fps");
					fps = frames;
					//previousTime += 1000;
					frames = 0;
				}
			}
			if (ticked) {
				render();
				frames++;
			}
		}
	}

	private void tick() {
		System.out.println("tick");
		world.tick();
	}

	private void render() {
		
		//Get pixels from raster, load buffer:
		
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				pixel[j + i * WIDTH] = raster.pixels[j][HEIGHT - 1 - i];
			}

		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Display game = new Display();

		frame.add(game);
		frame.pack();
		frame.setSize(WIDTH*SCALE, HEIGHT*SCALE);
		frame.setResizable(false);
		frame.setTitle("Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		game.start();
	}
}
