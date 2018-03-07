import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
	private Node root;
	private int size;

	private static class Node {
		private Point2D point;
		private RectHV rect;
		private Node left;
		private Node right;

		// public Node (Point2D point,RectHV rect, boolean isblack){
		//
		// }

	}

	public KdTree() {
		root = new Node();
		RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
		root.point = null;
		root.rect = rect;
		root.left = null;
		root.right = null;
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;

	}

	public int size() {
		return size;
	}

	public void insert(Point2D p) {
		if (p == null)
			throw new NullPointerException("point can not be null");
		if (!contains(p))
			put(root, p, 0);

	}

	private void put(Node x, Point2D p, int lv) {
		if (x.point == null) {
			x.point = p;
			initialnext(x, lv);
		} else if (lv % 2 == 0) {
			if (p.x() < x.point.x())
				put(x.left, p, lv + 1);
			else
				put(x.right, p, lv + 1);
		} else {
			if (p.y() < x.point.y())
				put(x.left, p, lv + 1);
			else
				put(x.right, p, lv + 1);

		}

	}

	private void initialnext(Node x, int lv) {
		if (x == null)
			throw new NullPointerException("x should contain a point");
		double xlabel = x.point.x();
		double ylabel = x.point.y();
		double xmin = x.rect.xmin();
		double xmax = x.rect.xmax();
		double ymin = x.rect.ymin();
		double ymax = x.rect.ymax();
		if (lv % 2 == 0) {
			x.left = addsubtree(xmin, ymin, xlabel, ymax);
			x.right = addsubtree(xlabel, ymin, xmax, ymax);
		} else {
			x.left = addsubtree(xmin, ymin, xmax, ylabel);
			x.right = addsubtree(xmin, ylabel, xmax, ymax);
		}
		size++;

	}

	private Node addsubtree(double xmin, double ymin, double xmax, double ymax) {
		Node next = new Node();
		next.point = null;
		next.left = null;
		next.right = null;
		next.rect = new RectHV(xmin, ymin, xmax, ymax);
		return next;
	}

	// if (x == null) {
	// x = new Node();
	// x.point = p;
	// x.rect = rect0;
	// x.color = Red;
	// }
	// if (x.color == Red) {
	// double cmp = p.x() - x.point.x();
	// if (cmp == 0)
	// cmp = 0.0;
	// if (cmp < 0) {
	// put(x.left, p);
	// x.left.color = Black;
	// x.left.rect = new RectHV(xmin, ymin, xmax, ymax);
	// } else {
	// put(x.right, p);
	// x.right.color = Black;
	// x.right.color = new RectHV(xmin, ymin, xmax, ymax);
	// }
	// }
	// if (x.color == Black) {
	// double cmp = p.y() - x.point.y();
	// if (cmp == 0)
	// cmp = 0.0;
	// if (cmp < 0) {
	// put(x.left, p);
	// x.left.color = Red;
	// x.left.rect = new RectHV(xmin, ymin, xmax, ymax);
	// } else {
	// put(x.right, p);
	// x.right.color = Red;
	// x.right.rect = new RectHV(xmin, ymin, xmax, ymax);
	// }
	// }
	//
	public boolean contains(Point2D p) {
		return find(root, p, 0);
	}

	private boolean find(Node x, Point2D p, int lv) {
		if (x.point == null)
			return false;
		else if (x.point.equals(p))
			return true;
		else if (lv % 2 == 0) {
			if (p.x() < x.point.x())
				return find(x.left, p, lv + 1);
			else
				return find(x.right, p, lv + 1);
		} else {
			if (p.y() < x.point.y())
				return find(x.left, p, lv + 1);
			else
				return find(x.right, p, lv + 1);
		}

	}

	public void draw() {
		StdDraw.setPenColor(StdDraw.BLACK);
		root.rect.draw();
		treedraw(root, 0);
	}

	private void treedraw(Node x, int lv) {
		if (x.point != null) {
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(0.01);
			x.point.draw();
			if (lv % 2 == 0) {
				// double ymin = x.rect.ymin();
				// double ymax = x.rect.ymax();
				Point2D point1 = new Point2D(x.point.x(), x.rect.ymin());
				Point2D point2 = new Point2D(x.point.x(), x.rect.ymax());
				StdDraw.setPenRadius();
				StdDraw.setPenColor(StdDraw.RED);
				point1.drawTo(point2);
			} else {
				Point2D point1 = new Point2D(x.rect.xmin(), x.point.y());
				Point2D point2 = new Point2D(x.rect.xmax(), x.point.y());
				StdDraw.setPenRadius();
				StdDraw.setPenColor(StdDraw.BLUE);
				point1.drawTo(point2);
			}
			treedraw(x.left, lv + 1);
			treedraw(x.right, lv + 1);
		}

	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new NullPointerException();
		// TreeSet<Point2D> range = new TreeSet<>();
		Stack<Point2D> range = new Stack<Point2D>();
		range = findrange(root, range, rect);
		return range;
	}

	private Stack<Point2D> findrange(Node x, Stack<Point2D> range, RectHV rect) {
		if (x.point == null) {
			return range;
		}
		if (rect.contains(x.point))
			range.push(x.point);
		if (rect.intersects(x.left.rect))
			range = findrange(x.left, range, rect);
		if (rect.intersects(x.right.rect))
			range = findrange(x.right, range, rect);
		return range;
	}

	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new NullPointerException();
		return findnearest(root, p, root.point, 0);

	}

	private Point2D findnearest(Node x, Point2D p, Point2D nearest, int lv) {
		if (x.point == null)
			return nearest;
		if (nearest.distanceSquaredTo(p) < x.rect.distanceSquaredTo(p))
			return nearest;
		if (x.point.distanceSquaredTo(p) < nearest.distanceSquaredTo(p))
			nearest = x.point;
		if (lv % 2 == 0) {
			if (p.x() < x.point.x()) {
				nearest = findnearest(x.left, p, nearest, lv + 1);
				nearest = findnearest(x.right, p, nearest, lv + 1);
				return nearest;
			} else {
				nearest = findnearest(x.right, p, nearest, lv + 1);
				nearest = findnearest(x.left, p, nearest, lv + 1);
				return nearest;
			}
		} else {
			if (p.y() < x.point.y()) {
				nearest = findnearest(x.left, p, nearest, lv + 1);
				nearest = findnearest(x.right, p, nearest, lv + 1);
				return nearest;
			} else {
				nearest = findnearest(x.right, p, nearest, lv + 1);
				nearest = findnearest(x.left, p, nearest, lv + 1);
				return nearest;
			}
		}

	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		KdTree tree = new KdTree();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			// StdDraw.setPenColor(StdDraw.BLACK);
			// StdDraw.setPenRadius(0.01);
			// new Point2D(x, y).draw();
			tree.insert(new Point2D(x, y));
		}
		tree.draw();
		// Point2D test = new Point2D(0.2, 0.3);
		// Point2D near = tree.nearest(test);
		// StdOut.println("nearest point is " + near.x() + " " + near.y());
		RectHV rect = new RectHV(0.02, 0.02, 0.8, 0.8);
		for (Point2D p : tree.range(rect))
			StdOut.println("range contains " + p.x() + "  " + p.y() + "\n");

	}

}
