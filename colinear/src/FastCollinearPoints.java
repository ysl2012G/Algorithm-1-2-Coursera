import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * BruteCollinearPoints FastCollinearPoints.java
 *
 * Nov 7, 2015 justin
 */

public class FastCollinearPoints {

	private int segNum;
	private LineSegment[] segs;
	private Point[] localCopy;
	private Point[] originalCopy;

	public FastCollinearPoints(Point[] points) {
		// make a local copy of the points array. then sort the array with an
		// additional point from the original copy.
		// check for line segments.
		// how to make that no two same line segments are stored in the result?
		// use a set?
		// constructor should keep the integrity of the input data.
		if (points == null)
			throw new java.lang.NullPointerException();
		segNum = 0;
		originalCopy = new Point[points.length];
		System.arraycopy(points, 0, originalCopy, 0, points.length);
		Arrays.sort(originalCopy);
		localCopy = new Point[points.length];
		System.arraycopy(originalCopy, 0, localCopy, 0, points.length);
		segs = findLine(originalCopy);

	}

	private LineSegment[] findLine(Point[] points) {
		ArrayList<LineSegment> array = new ArrayList<LineSegment>();
		// double[] slopes = new double[10*points.length];
		// Point[] lefts = new Point[10*points.length];
		// ArrayList<Double> slopes= new ArrayList<Double>();
		// ArrayList<Point> lefts = new ArrayList<Point>();

		for (int i = 0; i < points.length; i++) {
			if (points[i] == null)
				throw new java.lang.NullPointerException();
			// Arrays.sort(localCopy);
			System.arraycopy(originalCopy, 0, localCopy, 0, originalCopy.length);
			Arrays.sort(localCopy, points[i].slopeOrder());
			for (int j = 1; j < localCopy.length; j++) {
				if (points[i].equals(localCopy[j]))
					throw new java.lang.IllegalArgumentException();
				int k = j + 1;
				// double tempSlope = points[i].slopeTo(localCopy[j]);
				while (k < localCopy.length) {
					// double curSlope = points[i].slopeTo(localCopy[k]);
					if (points[i].slopeTo(localCopy[j]) == points[i].slopeTo(localCopy[k]))
						// if ( Math.abs(tempSlope - curSlope) < 0.00001 ||
						// curSlope == Double.POSITIVE_INFINITY)
						k++;
					else
						break;
				}
				k = k - 1;
				if ((k - j) >= 2) {
					// addLine(); // add the line segment, make sure that there
					// is no duplicate line segment, and increase the variable
					// segNum by 1. // two points determine a straight line. //
					// how to avoid duplicate line segments?
					if (points[i].compareTo(localCopy[j]) < 0) {
						array.add(findSeg(i, j, k, localCopy, points));
						segNum++;
					}

					// Point left = points[i].compareTo(localCopy[j]) <
					// 0?points[i]:localCopy[j];
					// // checkDuplicate becomes a bottleneck, when input is
					// grid. the bottleneck in the loop is the sorting
					// operation, which has time complexity NlgN. However, when
					// the input becomes grid, then the time complexity of
					// checking duplicates become N^2 from N. Need to find a
					// algorithm to check duplicates within time complexity of
					// NlgN.
					// if (checkDuplicate(slopes, lefts, left,
					// localCopy[j].slopeTo(localCopy[k])))
					// {
					// array.add(findSeg(i, j, k, localCopy, points));
					// slopes.add(localCopy[j].slopeTo(localCopy[k]));
					// lefts.add(left);
					// segNum++;
					// }

				}
				j = k;
			}
		}
		return array.toArray(new LineSegment[array.size()]);

	}

	// private boolean checkDuplicate(ArrayList<Double> slopes,
	// ArrayList<Point> lefts, Point left, double slope) {
	// // double epsilon = 0.00001;
	// // Collections.sort(lefts);
	// // int i = Collections.binarySearch(lefts, left);
	// // // for objects, merge sort will be used, which has time complexity
	// // NlgN.
	// //
	// // if ( i >= 0)
	// // {
	// // if ( Math.abs(slopes.get(i).doubleValue() - slope) < epsilon ||
	// // (slopes.get(i).doubleValue()) == Double.POSITIVE_INFINITY && slope ==
	// // Double.POSITIVE_INFINITY)
	// // return false;
	// // }
	// // return true;
	//
	// // for grid, the line segment number will be huge, hence for every
	// // point, to check all the remaining points, the time complexity will
	// // scale to linear very soon.(N^2)
	//
	// // given a leftmost point and the slope, check whether this line segment
	// // has been included in the result?
	// // how to confirm the line segment is included?
	//
	// double epsilon = 0.00001;
	// for (int i = 0; i < lefts.size(); i++) {
	// if (lefts.get(i).equals(left)) {
	// if (Math.abs(slopes.get(i).doubleValue() - slope) < epsilon
	// || (slopes.get(i)
	// .doubleValue()) == Double.POSITIVE_INFINITY
	// && slope == Double.POSITIVE_INFINITY)
	//
	// return false;
	// }
	// }
	//
	// return true;
	//
	// // double epsilon = 0.00001;
	// // int i = lefts.indexOf(left);
	// //// if ( i != -1 && Math.abs(slopes.get(i).doubleValue() - slope) <
	// // epsilon || (slopes.get(i).doubleValue()) == Double.POSITIVE_INFINITY
	// // && slope == Double.POSITIVE_INFINITY)
	// //// return false;
	// //// else
	// //// return true;
	// //
	// // if ( i != -1)
	// // {
	// // if (Math.abs(slopes.get(i).doubleValue() - slope) < epsilon ||
	// // (slopes.get(i).doubleValue()) == Double.POSITIVE_INFINITY && slope ==
	// // Double.POSITIVE_INFINITY)
	// // return false;
	// // else return true;
	// // }
	// // else
	// // return true;
	// // if ( i == -1)
	// // return true;
	// // else
	// // {
	// // if ( Math.abs(slopes.get(i).doubleValue() - slope) < epsilon ||
	// // (slopes.get(i).doubleValue()) == Double.POSITIVE_INFINITY && slope ==
	// // Double.POSITIVE_INFINITY)
	// // return false;
	// // else
	// // return true;
	// // }
	// }

	private LineSegment findSeg(int i, int j, int k, Point[] localCopy, Point[] points) {
		if (points[i].compareTo(localCopy[j]) < 0)
			return new LineSegment(points[i], localCopy[k]);
		else if (points[i].compareTo(localCopy[k]) > 0)
			return new LineSegment(localCopy[j], points[i]);
		else
			return new LineSegment(localCopy[j], localCopy[k]);
	}

	public int numberOfSegments() {
		int result = segNum;
		return result;
	}

	public LineSegment[] segments() {
		LineSegment[] result = new LineSegment[segNum];
		System.arraycopy(segs, 0, result, 0, segNum);
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		In in = new In(args[0]);
		int N = in.readInt();
		Point[] points = new Point[N];
		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}

		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();

		// print and draw the line segments
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
	}

}
