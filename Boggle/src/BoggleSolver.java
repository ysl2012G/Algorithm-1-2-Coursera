import java.util.HashSet;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
	// private TST<Integer> dict;
	// private Bag<Integer>[] adj;
	private boolean[][] isVisted;
	// private Bag<pairs>[][] adj;
	private char[][] table;
	private HashSet<String> words;
	private Graph boardGraph;
	private int rows;
	private int cols;

	private static final int R = 26;// number of the upper class alphabet;
	private Node root;

	public BoggleSolver(String[] dictionary) {
		if (dictionary == null)
			throw new IllegalArgumentException("invalid dictionary");
		for (String s : dictionary) {
			put(s);
		}
	}

	public Iterable<String> getAllValidWords(BoggleBoard board) {
		if (board == null)
			throw new IllegalArgumentException("invalid board");
		initialGraph(board);
		words = new HashSet<String>();
		findWords();
		return words;

	}

	public int scoreOf(String word) {
		if (!contains(word))
			return 0;
		int length = word.length();
		if (length <= 2)
			return 0;
		else if (length <= 4)
			return 1;
		else if (length <= 5)
			return 2;
		else if (length <= 6)
			return 3;
		else if (length <= 7)
			return 5;
		else
			return 11;
	}

	private void initialGraph(BoggleBoard board) {
		rows = board.rows();
		cols = board.cols();
		boardGraph = new Graph(rows * cols);
		isVisted = new boolean[rows][cols];
		table = new char[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				table[i][j] = board.getLetter(i, j);
				for (int row = i - 1; row <= i + 1; row++) {
					for (int col = j - 1; col <= j + 1; col++) {
						if (row >= 0 && row < rows && col >= 0 && col < cols && (row != i || col != j)) {
							if (isVisted[row][col])
								boardGraph.addEdge(getPlanarIndex(i, j), getPlanarIndex(row, col));
						}
					}
				}
				isVisted[i][j] = true;
			}
		}
	}

	private int getPlanarIndex(int i, int j) {
		return i * cols + j;
	}

	// @SuppressWarnings("unchecked")
	// private void initialAdj(BoggleBoard board) {
	// rows = board.rows();
	// cols = board.cols();
	// adj = (Bag<pairs>[][]) new Bag[rows][cols];
	// table = new char[rows][cols];
	// for (int i = 0; i < rows; i++) {
	// for (int j = 0; j < cols; j++) {
	// table[i][j] = board.getLetter(i, j); // get the specific character;
	// adj[i][j] = new Bag<BoggleSolver.pairs>();
	// // check everty bound for i,j dice;
	// for (int row = i - 1; row <= i + 1; row++) {
	// for (int col = j - 1; col <= j + 1; col++) {
	// if (row >= 0 && row < rows && col >= 0 && col < cols) {
	// if (row != i || col != j) {
	// adj[i][j].add(new pairs(row, col));
	// }
	//
	// }
	// }
	// }
	// }
	//
	// }
	//
	// }

	private static class Node {
		private int val;
		Node[] next = new Node[R];

		public Node() {
			val = 0;// TODO Auto-generated constructor stub
		}

	}

	private void put(String key) {
		root = put(root, key, 0);
	}

	private Node put(Node x, String key, int d) {
		if (x == null)
			x = new Node();
		if (d == key.length()) {
			if (x.val != 0)
				x.val++;
			else
				x.val = 1;
			return x;
		}
		int c = key.charAt(d) - 'A';
		x.next[c] = put(x.next[c], key, d + 1);
		return x;
	}

	private Node getNode(Node x, char c) {
		int index = c - 'A';
		if (x.next[index] == null)
			return null;
		return x.next[index];
	}

	private boolean contains(String word) {
		if (word == null)
			throw new IllegalArgumentException("word cannot be null");
		Node xNode = root;
		int index = 0;
		while (index < word.length() && xNode != null) {
			xNode = getNode(xNode, word.charAt(index));
			index++;
		}
		if (xNode == null)
			return false;
		else
			return xNode.val != 0;
	}

	private void findWords() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				isVisted = new boolean[rows][cols];
				dfs(getPlanarIndex(i, j), new StringBuilder(), root);
			}
		}
	}

	private void dfs(int planarindex, StringBuilder prefix, Node currentNode) {
		int row = planarindex / cols;
		int col = planarindex % cols;
		isVisted[row][col] = true;
		char c = table[row][col];
		prefix.append(c);
		if (c == 'Q') {
			prefix.append('U');
		}
		// copy
		Node nextNode = getNode(currentNode, c);
		if (c == 'Q' && nextNode != null) {
			nextNode = getNode(nextNode, 'U');
		}
		if (nextNode != null) {
			if (nextNode.val != 0) {
				if (prefix.length() >= 3)
					words.add(prefix.toString());
			}
			for (int adjIndex : boardGraph.adj(getPlanarIndex(row, col))) {
				int adjRow = adjIndex / cols;
				int adjCol = adjIndex % cols;
				if (!isVisted[adjRow][adjCol]) {
					dfs(adjIndex, prefix, nextNode);
				}
			}
		}

		isVisted[row][col] = false;
		prefix.deleteCharAt(prefix.length() - 1);
		if (c == 'Q')
			prefix.deleteCharAt(prefix.length() - 1);
		//
		// if (dict.keysWithPrefix(str).iterator().hasNext()) {
		// if (dict.longestPrefixOf(str).equals(str)) {
		// if (str.length() >= 3)
		// words.add(str);
		// }
		// for (pairs adjDice : adj[index.row][index.col]) {
		// if (!prePairs.contains(adjDice)) {
		// dfs(adjDice, prePairs, new StringBuilder(str));
		// }
		// }
		// }

	}

	// private static class pairs {
	// private int row, col;
	//
	// public pairs(int i1, int i2) {
	// this.row = i1;
	// this.col = i2;
	// }
	//
	// public boolean equals(Object obj) {
	// if (this == obj)
	// return true;
	// if (obj == null)
	// return false;
	// if (getClass() != obj.getClass())
	// return false;
	// pairs other = (pairs) obj;
	// if (this.row != other.row)
	// return false;
	// if (this.col != other.col)
	// return false;
	// return true;
	// }
	//
	// public int hashCode() {
	// final int prime = 31;
	// return this.row * prime + this.col;
	// }
	//
	// }
	// private void dfs(pairs dice,BoggleBoard board)S

	public static void main(String[] args) {
		In in = new In(args[1]);
		String[] dictionary = in.readAllStrings();
		BoggleSolver solver = new BoggleSolver(dictionary);
		// StdOut.println();
		// StdOut.println(solver.contains("SOOT"));
		BoggleBoard board = new BoggleBoard(args[0]);
		int score = 0;
		for (String word : solver.getAllValidWords(board)) {
			StdOut.println(word);
			score += solver.scoreOf(word);
		}
		StdOut.println("Score = " + score);
	}
}
// solver
