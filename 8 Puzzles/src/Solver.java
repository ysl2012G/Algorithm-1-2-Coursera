import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private Board board;
	private Board twinboard;
	private int moves;
	private Stack<Board> path;
	private Node solutionnode;

	public Solver(Board initial) {
		if (initial == null)
			throw new NullPointerException("board can't be null");
		board = initial;
		twinboard = initial.twin();
		moves = 0;
		MinPQ<Node> openset = new MinPQ<Node>();
		Node searchnode = new Node(board, moves, null);
		Node twinsearch = new Node(twinboard, moves, null);
		openset.insert(searchnode);
		openset.insert(twinsearch);
		while (!openset.isEmpty()) {
			Node currentnode = openset.delMin();
			if (currentnode.board.isGoal()) {
				solutionnode = currentnode;
				moves = solutionnode.moves;
				break;
			}
			for (Board s : currentnode.board.neighbors()) {
				if (!currentnode.has(s))
					openset.insert(new Node(s, currentnode.moves + 1, currentnode));
			}

		}

	} // find a solution to the initial board (using the A* algorithm)

	private class Node implements Comparable<Node> {
		private int moves;
		private Node previous;
		private Board board;
		int Manh_priority;
		int Hamm_prioirty;

		private Node(Board board, int moves, Node previous) {
			this.moves = moves;
			this.previous = previous;
			this.board = board;
			Manh_priority = this.board.manhattan() + this.moves;
			Hamm_prioirty = this.board.hamming() + this.moves;

		}

		public int compareTo(Node that) {
			if (this == that)
				return 0;
			if (that == null)
				return 1;
			if (this.Manh_priority != that.Manh_priority)
				return (this.Manh_priority - that.Manh_priority);
			else
				return (this.Hamm_prioirty - that.Hamm_prioirty);
		}

		private boolean has(Board board) {
			Node s = this;
			while (s.previous != null) {
				if (s.previous.board.equals(board))
					return true;
				s = s.previous;
			}
			return false;

		}

	}

	public boolean isSolvable() {
		// Node sNode = solutionnode;
		// while (sNode != null) {
		// sNode = sNode.previous;
		// if (sNode.board.equals(board))
		// return true;
		// }
		return solution() != null;

	} // is the initial
		// board
		// solvable?

	public int moves() {
		if (isSolvable())
			return moves;
		else
			return -1;
	} // min number of moves to solve initial board; -1 if unsolvable

	public Iterable<Board> solution() {
		path = new Stack<Board>();
		Node sNode = solutionnode;
		while (sNode != null) {
			path.push(sNode.board);
			sNode = sNode.previous;
		}
		if (path.isEmpty())
			return null;
		else if (path.peek() != board)
			return null;
		else
			return path;
	} // sequence
		// of
		// boards
		// in
		// a
		// shortest
		// solution;
		// null
		// if
		// unsolvable

	public static void main(String[] args) {
		for (String filename : args) {

			// read in the board specified in the filename
			In in = new In(filename);
			int n = in.readInt();
			int[][] tiles = new int[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					tiles[i][j] = in.readInt();
				}
			}

			// solve the slider puzzle
			Board initial = new Board(tiles);
			Solver solver = new Solver(initial);
			StdOut.println(initial.toString());
			StdOut.println(filename + ": " + solver.moves());
			StdOut.println(solver.isSolvable());
			int i = 0;
			for (Board s : solver.solution()) {
				StdOut.println("step " + i + ":\n" + s + "\n");
				i++;
			}
			// for (Board s : solver.solution())
			// StdOut.println(s.toString());
		} // solve a slider puzzle (given below)
	}
}