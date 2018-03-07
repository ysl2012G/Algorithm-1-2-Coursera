import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
	private TreeSet<Point2D> pointset;

	public PointSET() {
		pointset = new TreeSet<Point2D>();
	} // construct an empty set of points

	public boolean isEmpty() {
		return size() == 0;
	} // is the set empty?

	public int size() {
		return pointset.size();
	} // number of points in the set

	public void insert(Point2D p) {
		if (p == null)
			throw new NullPointerException("point can not be null");
		if (!pointset.contains(p))
			pointset.add(p);
	} // add the point to the set (if it is not already in the set)

	public boolean contains(Point2D p) // does the set contain point p?
	{
		if (p == null)
			throw new NullPointerException();
		return pointset.contains(p);
	}

	public void draw() {
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		for (Point2D p : pointset)
			StdDraw.point(p.x(), p.y());
	} // draw
		// all
		// points
		// to
		// standard
		// draw

	public Iterable<Point2D> range(RectHV rect) // all points that are inside
												// the rectangle
	{
		// TreeSet<Point2D> stack = new TreeSet<Point2D>();
		Stack<Point2D> stack = new Stack<Point2D>();
		if (rect == null)
			throw new NullPointerException("rect can not be null");
		for (Point2D p : pointset) {
			if (rect.contains(p))
				stack.push(p);
		}
		return stack;
	}

	public Point2D nearest(Point2D p) // a nearest neighbor in the set to point
										// p; null if the set is empty
	{
		if (p == null)
			throw new NullPointerException();
		Point2D nearest = pointset.first();
		for (Point2D point : pointset)
			if (point.distanceSquaredTo(p) < nearest.distanceSquaredTo(p))
				nearest = point;
		return nearest;
		// TreeSet<Point2D> nearset = new TreeSet<Point2D>(p.distanceToOrder());
		// nearset.addAll(pointset);
		// return nearset.ceiling(p);
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		PointSET set = new PointSET();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			set.insert(new Point2D(x, y));
			// StdDraw.setPenColor(StdDraw.BLACK);
			// StdDraw.setPenRadius(0.01);
			// new Point2D(x, y).draw();
		}
		set.draw();
	} // unit testing of the methods (optional)
}