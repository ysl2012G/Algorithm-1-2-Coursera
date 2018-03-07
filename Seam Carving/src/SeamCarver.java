import java.awt.Color;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {
	private int[][] pictArray;
	private boolean isChanged;

	public SeamCarver(Picture picture) {
		if (picture == null)
			throw new IllegalArgumentException("picture must not be null");
		pictArray = createPictureArray(picture);
		isChanged = false;// picture array orientation;
	}

	/**
	 * show the orginal picture's height and width
	 */
	public int width() {
		if (isChanged) {
			return getHeight();
		} else {
			return getWidth();
		}
	}

	public int height() {
		if (isChanged) {
			return getWidth();
		} else {
			return getHeight();
		}
	}

	public Picture picture() {
		return createPicture();

	}

	public double energy(int x, int y) {
		if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1)
			throw new IllegalArgumentException("the index are not in the range");
		if (notBorder(x, y)) {
			return Math.sqrt(deltaX(x, y) + deltaY(x, y));
		} else {
			return 1000;
		}
	}

	public int[] findHorizontalSeam() {
		// don't change the orientation when find horizontal seam
		// if (isChanged) {
		// transposePicture();
		// isChanged = false;
		// }
		boolean isHorizontal = true;
		return findSeam(isHorizontal);
	}

	public int[] findVerticalSeam() {
		// change the orientation when find the vertical seam
		// if (!isChanged) {
		// transposePicture();
		// isChanged = true;
		// }
		boolean isHorizontal = false;
		return findSeam(isHorizontal);
	}

	public void removeHorizontalSeam(int[] seam) {
		if (seam == null || height() <= 1)
			throw new IllegalArgumentException("illegal horizontal seam");
		if (seam.length != width())
			throw new IllegalArgumentException("seam length doesn't match with width");
		int last = seam[0];
		for (int i : seam) {
			if (!checkValidation(i, true))
				throw new IllegalArgumentException("the value in the seam are not valid");
			if (Math.abs(last - i) > 1)
				throw new IllegalArgumentException("not adjacent value");
			last = i;
		}
		if (isChanged) {
			transposePicture();
			isChanged = false;
		}
		removeSeam(seam);
	}

	public void removeVerticalSeam(int[] seam) {
		if (seam == null || width() <= 1)
			throw new IllegalArgumentException("illegal vertical seam");
		if (seam.length != height())
			throw new IllegalArgumentException("seam length doesn't match");
		// we need change the default orientation when remove vertical seam from the
		// picture;
		int last = seam[0];
		for (int i : seam) {
			if (!checkValidation(i, false))
				throw new IllegalArgumentException("seam values are not validate");
			if (Math.abs(last - i) > 1)
				throw new IllegalArgumentException("not adjacent value");
			last = i;
		}
		if (!isChanged) {
			transposePicture();
			isChanged = true;
		}
		removeSeam(seam);

	}

	/***********************************
	 * Helper funcionn
	 ***************************************/
	/**
	 * get the current picture array(no mater vertical or horizontal) length
	 */
	private int getWidth() {
		return pictArray.length;
	}

	private int getHeight() {
		return pictArray[0].length;
	}

	private int[][] createPictureArray(Picture picture) {
		int rowNum = picture.height();
		int colNum = picture.width();
		int[][] pictureArray = new int[colNum][rowNum];
		for (int col = 0; col < colNum; col++) {
			for (int row = 0; row < rowNum; row++) {
				pictureArray[col][row] = picture.getRGB(col, row);
			}
		}
		return pictureArray;

	}

	private Picture createPicture() {
		if (isChanged) {
			transposePicture();
			isChanged = false;
		}
		int colNum = getWidth();
		int rowNum = getHeight();
		Picture pict = new Picture(colNum, rowNum);
		for (int col = 0; col < colNum; col++) {
			for (int row = 0; row < rowNum; row++) {
				pict.set(col, row, new Color(pictArray[col][row]));
			}
		}
		return pict;
	}

	private boolean notBorder(int col, int row) {
		return (col > 0 && col < width() - 1 && row > 0 && row < height() - 1);
	}

	private double deltaX(int col, int row) {
		int colindex = col;
		int rowindex = row;
		Color c1, c2;
		if (isChanged) {
			c1 = new Color(pictArray[rowindex][colindex - 1]);
			c2 = new Color(pictArray[rowindex][colindex + 1]);
		} else {
			c1 = new Color(pictArray[colindex - 1][rowindex]);
			c2 = new Color(pictArray[colindex + 1][rowindex]);
		}
		double Rx = Math.pow(c1.getRed() - c2.getRed(), 2.0);
		double Gx = Math.pow(c1.getGreen() - c2.getGreen(), 2.0);
		double Bx = Math.pow(c1.getBlue() - c2.getBlue(), 2.0);
		return Rx + Gx + Bx;
	}

	private double deltaY(int col, int row) {
		int colindex = col;
		int rowindex = row;
		Color c1, c2;
		if (isChanged) {
			c1 = new Color(pictArray[rowindex + 1][colindex]);
			c2 = new Color(pictArray[rowindex - 1][colindex]);
		} else {
			c1 = new Color(pictArray[colindex][rowindex + 1]);
			c2 = new Color(pictArray[colindex][rowindex - 1]);

		}
		double Ry = Math.pow(c1.getRed() - c2.getRed(), 2.0);
		double Gy = Math.pow(c1.getGreen() - c2.getGreen(), 2.0);
		double By = Math.pow(c1.getBlue() - c2.getBlue(), 2.0);
		return Ry + Gy + By;
	}

	/* transpose the picture array */
	private void transposePicture() {
		int rowNum = getHeight();
		int colNum = getWidth();
		int[][] tempPict = new int[rowNum][colNum];
		for (int col = 0; col < colNum; col++) {
			for (int row = 0; row < rowNum; row++) {
				tempPict[row][col] = pictArray[col][row];
			}
		}
		pictArray = tempPict;
	}

	/* default picture orientation is unchanged */
	private double[][] createEnergyArray(boolean isHorizontal) {
		int rowNum = height();
		int colNum = width();
		if (isHorizontal) {
			double[][] energyArray = new double[rowNum][colNum];
			for (int col = 0; col < rowNum; col++) {
				for (int row = 0; row < colNum; row++) {
					energyArray[col][row] = energy(row, col);
				}
			}
			return energyArray;
		} else {
			double[][] energyArray = new double[colNum][rowNum];
			for (int col = 0; col < colNum; col++) {
				for (int row = 0; row < rowNum; row++) {
					energyArray[col][row] = energy(col, row);
				}
			}
			return energyArray;
		}

	}

	/**
	 * find the seam based on horizontal picture array
	 */
	private int[] findSeam(boolean isHorizontal) {
		// check the vertical request or horizontal request for find seem
		// combine with the current picture orientation;
		double[][] energyArray = createEnergyArray(isHorizontal);
		// double[][] energyArray = createEnergyArray(!requestHorizontal);

		int rowNum = energyArray[0].length;
		int colNum = energyArray.length;
		// initilize the edge and distTo wit the energy array as edge.
		int[][] edgeTo = new int[colNum][rowNum];
		double[][] distTo = new double[colNum][rowNum];
		for (int col = 0; col < colNum; col++) {
			for (int row = 0; row < rowNum; row++) {
				distTo[col][row] = Double.POSITIVE_INFINITY;
			}
		}
		for (int col = 0; col < colNum; col++) {
			edgeTo[col][0] = col;
			distTo[col][0] = energyArray[col][0];
		}
		// get the topological order
		SeamDAG seamDAG = new SeamDAG(colNum, rowNum);
		Iterable<Integer> order = seamDAG.topological();

		for (int planarIndex : order) {
			int col = planarIndex / rowNum;
			int row = planarIndex % rowNum;
			// StdOut.printf("[%d,%d]\n", col, row);
			if (row < rowNum - 1) {
				for (int index = col - 1; index <= col + 1; index++) {
					if (index >= 0 && index < colNum) {
						// relax function
						if (distTo[index][row + 1] > distTo[col][row] + energyArray[index][row + 1]) {
							distTo[index][row + 1] = distTo[col][row] + energyArray[index][row + 1];
							edgeTo[index][row + 1] = col;
						}
					}
				}
			}
		}
		// get the min energy index on last row;
		double minEnergy = distTo[0][rowNum - 1];
		int minIndex = 0;
		for (int i = 0; i < colNum; i++) {
			if (minEnergy > distTo[i][rowNum - 1]) {
				minEnergy = distTo[i][rowNum - 1];
				minIndex = i;
			}
		}
		// using edgeto to get full index
		int[] colIndex = new int[rowNum];
		for (int i = rowNum - 1; i >= 0; i--) {
			colIndex[i] = minIndex;
			minIndex = edgeTo[minIndex][i];
		}
		return colIndex;
	}

	// remove the seam from the picture array
	private void removeSeam(int[] seam) {
		int rowNum = getHeight();
		int colNum = getWidth();
		int[][] tempPict = new int[colNum][rowNum - 1];
		for (int col = 0; col < colNum; col++) {
			int index = seam[col];
			System.arraycopy(pictArray[col], 0, tempPict[col], 0, index);
			System.arraycopy(pictArray[col], index + 1, tempPict[col], index, rowNum - index - 1);
		}
		pictArray = tempPict;
	}

	private boolean checkValidation(int index, boolean isHorizontal) {
		if (isHorizontal) {
			return index >= 0 && index < height();
		} else {
			return index >= 0 && index < width();
		}
	}

	public static void main(String[] args) {
		Picture picture = new Picture(args[0]);
		StdOut.printf("image is %d pixels wide by %d pixels high.\n", picture.width(), picture.height());

		SeamCarver sc = new SeamCarver(picture);
		// sc.transposePicture();
		// sc.isChanged = true;
		// double[][] energyArray = sc.createEnergyArray(true);
		// StdOut.printf("Printing energy calculated for each pixel.\n");
		// for (int row = 0; row < sc.width(); row++) {
		// for (int col = 0; col < sc.height(); col++)
		// StdOut.printf("%9.2f ", energyArray[col][row]);
		// StdOut.println();
		// }
		int[] seam = sc.findVerticalSeam();
		sc.removeVerticalSeam(seam);
		// for (int i : seam) {
		// StdOut.println(i);
		// }
		StdOut.println(sc.width());
		// StdOut.println(sc.pictArray[0].length);

		// int[] testarray = { 1, 2, 3, 4, 5 };
		// int index = 3;
		// int[] printarray = new int[testarray.length - 1];
		// System.arraycopy(testarray, 0, printarray, 0, index);
		// System.arraycopy(testarray, index + 1, printarray, index, testarray.length -
		// 1 - index);
		// for (int i : printarray)
		// StdOut.printf("%d", i);
		// Stack<Integer> mStack = new Stack<Integer>();
		// for (int i = 0; i < 6; i++) {
		// mStack.push(i);
		// }
		// for (int j : mStack) {
		// StdOut.println(j);
		// }
	}
}
