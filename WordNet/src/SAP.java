import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class SAP {
	// private static final int INFINITY = Integer.MAX_VALUE;
	private final Digraph G;
	// private final int V;
	// private final int E;
	// private Bag<Integer>[] adj;

	/**
	 * 
	 * @param G
	 *            Digraph class
	 */
	public SAP(Digraph G) {
		this.G = new Digraph(G);
		// validateVertex(G.V());
		// this.V = G.V();
		// this.E = G.E();
		// for (int v = 0; v < this.V; v++) {
		// validateVertex(v);
		// Stack<Integer> reverse = new Stack<Integer>();
		// for (int w : G.adj(v)) {
		// reverse.push(w);
		// }
		// for (int w : reverse) {
		// adj[v].add(w);
		// }
		// }
	}

	/**
	 * @param paths
	 * @return -1 means no accestor vertex, Attention!!! check the situation when
	 *         they cannot combined
	 */
	private int findAncestor(BreadthFirstDirectedPaths path1, BreadthFirstDirectedPaths path2) {
		int ancestor = -1;
		int num = G.V();
		int length = Integer.MAX_VALUE;
		for (int i = 0; i < num; i++) {
			// find the vertex path1 has visited
			if (path1.hasPathTo(i) && path2.hasPathTo(i)) {
				// find the vertex path2 has visited {
				// compare the dist based on path1
				int dist = path1.distTo(i) + path2.distTo(i);
				if (dist < length) {
					length = dist;
					ancestor = i;
				}
			}
		}
		return ancestor;
	}

	/**
	 * find the shortest ancestral path
	 * 
	 * @param v
	 * @param w
	 * @return
	 */
	public int length(int v, int w) {
		BreadthFirstDirectedPaths paths_v = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths paths_w = new BreadthFirstDirectedPaths(G, w);
		int length = -1; // initilize, if not change ,then there is no ancestor
		int ancestor = findAncestor(paths_v, paths_w);
		if (ancestor != -1) {
			length = paths_v.distTo(ancestor) + paths_w.distTo(ancestor);
		}
		return length;
	}

	public int ancestor(int v, int w) {
		BreadthFirstDirectedPaths paths_v = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths paths_w = new BreadthFirstDirectedPaths(G, w);
		int ancestor = findAncestor(paths_v, paths_w);
		return ancestor;
	}

	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		BreadthFirstDirectedPaths paths_v = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths paths_w = new BreadthFirstDirectedPaths(G, w);
		int length = -1;
		int ancestor = findAncestor(paths_v, paths_w);
		if (ancestor != -1) {
			length = paths_v.distTo(ancestor) + paths_w.distTo(ancestor);
		}
		return length;
	}

	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		BreadthFirstDirectedPaths paths_v = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths paths_w = new BreadthFirstDirectedPaths(G, w);
		int ancestor = findAncestor(paths_v, paths_w);
		return ancestor;
	}

	// private void validateVertex(int v) {
	// if (v < 0 || v > G.V()) {
	// throw new IllegalArgumentException("Vertex" + v + "is not betwwen the 0 and "
	// + (G.V() - 1));
	// }
	// }

	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph diG = new Digraph(in);
		SAP sap = new SAP(diG);
		int length = sap.length(27985, 52166);
		int ancestor = sap.ancestor(27985, 52166);
		// Stack<Integer> stack1 = new Stack<Integer>();
		// Stack<Integer> stack2 = new Stack<Integer>();
		// stack1.push(0);
		// stack2.push(3);
		// int lengthIter = sap.length(stack1, stack2);
		// int ancestorIter = sap.ancestor(stack1, stack2);
		System.out.println(length);
		System.out.println(ancestor);
		// System.out.println(lengthIter);
		// System.out.println(ancestorIter);
		// System.out.println(stack1);
	}

}
