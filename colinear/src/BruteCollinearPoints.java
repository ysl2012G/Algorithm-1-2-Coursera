import java.util.Arrays;
import java.util.Stack;

import edu.princeton.cs.algs4.In;
/**
 * BruteCollinearPoints
 * BruteCollinearPoints.java
 *
 * Nov 6, 2015
 * justin
 */
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

	private int segNum;
	private LineSegment[] segs;
	private int numOfPoints = 4;
	private Point[] localCopy;

	public BruteCollinearPoints(Point[] points) {
		// need a container to store the line segments found. array wouldn't
		// work.

		if (points == null)
			throw new java.lang.NullPointerException();
		segNum = 0;
		localCopy = new Point[points.length];
		System.arraycopy(points, 0, localCopy, 0, points.length);
		findLine(localCopy);
	}

	private void findLine(Point[] points) {
		Arrays.sort(points);
		Stack<LineSegment> stack = new Stack<LineSegment>();
		for (int i = 0; i < points.length; i++) {
			for (int j = i + 1; j < points.length; j++) {
				if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY)
					throw new java.lang.IllegalArgumentException();
				for (int k = j + 1; k < points.length; k++) {
					if (points[j].slopeTo(points[k]) == Double.NEGATIVE_INFINITY)
						throw new java.lang.IllegalArgumentException();

					for (int l = k + 1; l < points.length; l++) {
						// put the points in an container and sort them,
						// determine if they are collinear.
						if (points[i] == null || points[j] == null || points[k] == null || points[l] == null)
							throw new java.lang.NullPointerException();

						if (points[k].slopeTo(points[l]) == Double.NEGATIVE_INFINITY)
							throw new java.lang.IllegalArgumentException();

						if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) {
							Point[] temp = new Point[numOfPoints];
							temp[0] = points[i];
							temp[1] = points[j];
							temp[2] = points[k];
							temp[3] = points[l];
							// boolean flag = slopeEquals(temp);
							if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
								Arrays.sort(temp);
								stack.add(new LineSegment(temp[0], temp[numOfPoints - 1]));
								segNum++;
							}
						}

					}

				}
			}
		}

		segs = new LineSegment[segNum];
		for (int i = 0; i < segNum; i++)
			segs[i] = stack.pop();
	}

	// private boolean duplicatePoint ( int i, int j, int k, int l, Point[]
	// points)
	// {
	// double slope1 = points[i].slopeTo(points[j]);
	// double slope2 = points[j].slopeTo(points[k]);
	// double slope3 = points[k].slopeTo(points[l]);
	// return ( slope1 == Double.NEGATIVE_INFINITY || slope2 ==
	// Double.NEGATIVE_INFINITY || slope3 == Double.NEGATIVE_INFINITY);
	// }
	//
	//
	// private boolean slopeEquals(Point[] ptArray)
	// {
	// double slope1 = ptArray[0].slopeTo(ptArray[1]);
	// double slope2 = ptArray[0].slopeTo(ptArray[2]);
	// double slope3 = ptArray[0].slopeTo(ptArray[3]);
	// return (slope1 == slope2 && slope1 == slope3);
	// }
	//
	public int numberOfSegments() {
		int result = segNum;
		return result;
	}

	public LineSegment[] segments() {
		LineSegment[] result = new LineSegment[segs.length];
		System.arraycopy(segs, 0, result, 0, segs.length);
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
		BruteCollinearPoints collinear = new BruteCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
	}

}
