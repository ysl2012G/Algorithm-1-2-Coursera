import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
	private int n;
	private int hamming;
	private int manhattan;
	private char[] board;
	private boolean isgoal;

	public Board(int[][] blocks) {
		if (blocks == null)
			throw new NullPointerException("can't have null board");
		n = blocks.length;
		board = new char[n * n];
		hamming = 0;
		manhattan = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				board[i * n + j] = (char) blocks[i][j];
				if (blocks[i][j] != i * n + j + 1 && blocks[i][j] != 0) {
					hamming++;
					if (blocks[i][j] != 0) {
						int temp = blocks[i][j] - 1;
						int row = temp / n;
						int col = temp % n;
						manhattan = manhattan + Math.abs(row - i) + Math.abs(col - j);
					}
				}
			}
		}
		isgoal = (hamming == 0);
	}

	public int dimension() {
		return n;
	}

	public int hamming() {
		return hamming;
	}

	public int manhattan() {
		return manhattan;
	} // sum of Manhattan distances between blocks and goal

	public boolean isGoal() {
		return isgoal;
	} // is this board the goal board?

	public Board twin() {
		int[][] twin = new int[n][n];
		int row = 0;
		int col = 0;
		twin = boardcopy();
		// for (int i = 0; i < n; i++)
		// for (int j = 0; j < n; j++)
		// twin[i][j] = (int) board[i * n + j];
		search: for (int i = 0; i < n; i++) {
			for (int j = 0; j < n - 1; j++) {
				if (twin[i][j] != 0 && twin[i][j + 1] != 0) {
					row = i;
					col = j;
					break search;
				}
			}
		}
		twin[row][col] = (int) board[row * n + col + 1];
		twin[row][col + 1] = (int) board[row * n + col];
		return new Board(twin);
	} // a
		// board
		// that
		// is
		// obtained
		// by
		// exchanging
		// any
		// pair
		// of
		// blocks

	private int[][] boardcopy() {
		int[][] copy = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				copy[i][j] = (int) board[i * n + j];
		return copy;

	}

	private int zeroindex() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int temp = (int) board[i * n + j];
				if (temp == 0)
					return (i * n + j);
			}
		}
		return -1;
	}

	public boolean equals(Object y) {
		if (y == this)
			return true;
		if (y == null)
			return false;
		if (y.getClass() != this.getClass())
			return false;
		Board that = (Board) y;
		if (that.n != this.n)
			return false;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (this.board[i * n + j] != that.board[i * n + j])
					return false;
			}
		}
		return true;
	} // does this board equal y?

	public Iterable<Board> neighbors() {
		Stack<Board> stack = new Stack<Board>();
		int zero_pos = zeroindex();
		int row = zero_pos / n;
		int col = zero_pos % n;
		int[][] neighbor = new int[n][n];
		if (row < n - 1) {
			neighbor = boardcopy();
			neighbor[row][col] = (int) board[(row + 1) * n + col];
			neighbor[row + 1][col] = (int) board[row * n + col];
			stack.push(new Board(neighbor));
		}
		if (row > 0) {
			neighbor = boardcopy();
			neighbor[row][col] = (int) board[(row - 1) * n + col];
			neighbor[row - 1][col] = (int) board[row * n + col];
			stack.push(new Board(neighbor));
		}
		if (col < n - 1) {
			neighbor = boardcopy();
			neighbor[row][col] = (int) board[row * n + col + 1];
			neighbor[row][col + 1] = (int) board[row * n + col];
			stack.push(new Board(neighbor));
		}
		if (col > 0) {
			neighbor = boardcopy();
			neighbor[row][col] = (int) board[row * n + col - 1];
			neighbor[row][col - 1] = (int) board[row * n + col];
			stack.push(new Board(neighbor));
		}

		return stack;
	} // all neighboring boards

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(n + "\n");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				s.append(String.format("%2d ", (int) board[i * n + j]));
			s.append("\n");
		}
		return s.toString();
	} // string representation of this board (in the output format specified
		// below)

	public static void main(String[] args) {
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);
		// Board twin = initial.twin();
		StdOut.println(initial.toString());
		// StdOut.println(twin.toString());
		// StdOut.println(initial.hamming);
		// StdOut.println(initial.manhattan);
		// StdOut.println(initial.isGoal());
		// StdOut.println(twin.isGoal());
		StdOut.println("zeropostion is " + initial.zeroindex());
		StdOut.println("its neighbours are: \n");
		for (Board s : initial.neighbors()) {
			StdOut.println(s.toString());
			StdOut.println(s.isGoal());
		}

		Stack<Integer> stacks = new Stack<Integer>();
		for (int i = 0; i < 10; i++) {
			stacks.push(Integer.valueOf(i));
		}
		for (Integer s : stacks)
			StdOut.println(s);
	} // unit tests (not graded)
}
