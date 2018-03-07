import java.awt.Color;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
	private int width;
	private int height;
	private int[][] color;
	private double[][] energy;

	public SeamCarver(Picture picture) {
		width = picture.width();
		height = picture.height();
		color = new int[width][height];
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				color[col][row] = picture.getRGB(col, row);
			}
		}
	}

	public Picture picture() {

	}

	public int width() {

	}

	public int height() {

	}

	public double energy(int x, int y)
}
