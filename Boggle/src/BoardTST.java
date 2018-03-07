public class BoardTST<Value> {
	private static final int R = 26;// number of upper class alphabet;

	private int size;
	private Node root;

	private static class Node {
		private Object val;
		private Node[] next = new Node[R];
	}

	public BoardTST() {
		// TODO Auto-generated constructor stub
	}

	public Node get(String key) {
		return null;
	}

	public void put(String key, Value val) {
		if (key == null)
			throw new IllegalArgumentException("key cannot be null");
		put(root, key, val, 0);
	}

	private Node put(Node x, String key, Value val, int d) {
		if (x == null)
			x = new Node();
		if (d == key.length()) {
			if (x.val == null)
				size++;
			x.val = val;
			return x;
		}
		int c = key.charAt(d) - 'A';
		x.next[c] = put(x.next[c], key, val, d + 1);
		return x;
	}
}
