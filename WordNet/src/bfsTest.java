
import java.util.HashSet;
import java.util.Set;

public class bfsTest {
	public static void main(String[] args) {
		String test = "ac b c d e f g h i";

		Set<String> nouns = new HashSet<String>();
		String[] parts = test.split("\\s+");
		for (String s : parts) {
			nouns.add(s);
		}
		System.out.println(nouns.contains("l"));
		// In in = new In(args[0]);
		// int id = 7;
		// Digraph digraph = new Digraph(id + 1);
		// String s = in.readLine();
		// while (s != null) {
		// String[] parts = s.split(",");
		// int bVertex = Integer.parseInt(parts[0]);
		// for (int i = 1; i < parts.length; i++) {
		// int eVertex = Integer.parseInt(parts[i]);
		// digraph.addEdge(bVertex, eVertex);
		// }
		// s = in.readLine();
		// }
		// BreadthFirstDirectedPaths path1 = new BreadthFirstDirectedPaths(digraph, 0);
		// BreadthFirstDirectedPaths path2 = new BreadthFirstDirectedPaths(digraph, 3);
		// for (int i = 0; i < digraph.V(); i++) {
		// String s1 = "the vertex " + i + " distance is " + path1.distTo(i);
		// String s2 = "the vertex " + i + " distance is " + path2.distTo(i);
		// String s3 = "the vertex " + i + " distance is " + path1.hasPathTo(i);
		// String s4 = "the vertex " + i + " distance is " + path2.hasPathTo(i);
		// System.out.println(s1);
		// System.out.println(s2);
		// System.out.println(s3);
		// System.out.println(s4);
		// }
		// SAP sap = new SAP(digraph);
		// System.out.println(sap.length(2, 1));
		// System.out.println(Integer.MAX_VALUE);
	}
}
