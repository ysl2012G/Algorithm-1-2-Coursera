import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class SeamDAG {
	private boolean[][] isVisted;
	private Stack<Integer> topologicalStack;
	private final int rowNum;

	private final int colNum;
	// private int count;

	public SeamDAG(int colNum, int rowNum) {
		this.rowNum = rowNum;
		this.colNum = colNum;
		topologicalStack = new Stack<Integer>();
		// count = 0;
		isVisted = new boolean[this.colNum][this.rowNum];
		for (int i = 0; i < colNum; i++) {
			if (!isVisted[i][0])
				traverseMatrix(i, 0);
		}
	}

	private void traverseMatrix(int col, int row) {
		int colIndex = col;
		int rowIndex = row;
		isVisted[colIndex][rowIndex] = true;
		if (rowIndex < rowNum - 1) {
			for (int index = colIndex - 1; index <= colIndex + 1; index++) {
				if (index >= 0 && index < colNum) {
					if (!isVisted[index][rowIndex + 1])
						traverseMatrix(index, rowIndex + 1);
				}
			}
		}

		topologicalStack.push(colIndex * rowNum + rowIndex);
		// count = count + 1;
		// StdOut.println("count is " + count);
		// StdOut.printf("col[%d] row[%d]\n", col, row);

	}

	public Iterable<Integer> topological() {
		return topologicalStack;
	}

	public static void main(String[] args) {
		int colNum = 7;
		int rowNum = 10;
		SeamDAG testdag = new SeamDAG(colNum, rowNum);
		Iterable<Integer> order = testdag.topological();
		for (int index : order) {
			int col = index / rowNum;
			int row = index % rowNum;
			StdOut.printf("[%d %d]\n", col, row);
		}
	}
}
