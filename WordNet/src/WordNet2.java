import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class WordNet2 {
	private ArrayList<String> synsetsList;
	private ArrayList<Integer> indexList;
	private Digraph wordG;
	private final SAP sap;

	private void mapSynsets(String synsets) {
		// synsetsList = new HashMap<Integer, String>();
		synsetsList = new ArrayList<String>();
		indexList = new ArrayList<Integer>();
		In in = new In(synsets);
		String s = in.readLine();
		int id = 0;
		while (s != null) {
			String[] parts = s.split(",");
			id = Integer.parseInt(parts[0]);
			String[] nouns = parts[1].split("\\s+");
			for (String str : nouns) {
				synsetsList.add(str);
				indexList.add(id);
			}
			s = in.readLine();
		}
		// create a digraph
		wordG = new Digraph(id + 1);
	}

	private void digraphHypernyms(String hypernyms) {
		In in = new In(hypernyms);
		String s = in.readLine();
		while (s != null) {
			String[] parts = s.split(",");
			int b_vertex = Integer.parseInt(parts[0]);
			for (int i = 1; i < parts.length; i++) {
				int e_vertex = Integer.parseInt(parts[i]);
				wordG.addEdge(b_vertex, e_vertex);
			}
			s = in.readLine();
		}
	}

	/**
	 * check the digraph has only one rooted DAG
	 * 
	 * @param G
	 */
	private void isRootedDAG(Digraph G) {
		int num = 0;
		for (int i = 0; i < G.V(); i++) {
			if (G.outdegree(i) == 0) {
				num++;
			}
		}
		if (num > 1) {
			throw new IllegalArgumentException("the input hypernyms don't have a rooted vertex");
		}
	}

	private void isCycle(Digraph G) {
		DirectedCycle cycle = new DirectedCycle(G);
		if (cycle.hasCycle()) {
			throw new IllegalArgumentException("the input hypernyms has cycle");
		}
	}

	public WordNet2(String synsets, String hypernyms) {
		mapSynsets(synsets);
		digraphHypernyms(hypernyms);
		isCycle(wordG);
		isRootedDAG(wordG);
		sap = new SAP(wordG);
		// check the digraph has cycle and only one rooted vertex
		// if (isRootedDAG(wordG) || !isCycle(wordG)) {
		// throw new IllegalArgumentException("the digraph contains cycle or multi
		// rooted vertex");
		// }
	}

	public Iterable<String> nouns() {
		Map<String, Integer> map6 = new HashMap<String, Integer>();
		return map6.keySet();
		// Iterator<Map.Entry<Integer, String>> iteratorMap =
		// synsetsList.entrySet().iterator();
		// Stack<String> nounsStack=new Stack<String>();
		// while (iteratorMap.hasNext()) {
		//
		// }

	}

	public boolean isNoun(String word) {
		if (word == null) {
			throw new IllegalArgumentException("the word must input");
		}
		return synsetsList.contains(word);
	}

	public int distance(String nounA, String nounB) {
		if (nounA == null || nounB == null) {
			throw new IllegalArgumentException("please input strings");
		}
		// check the nouns belongs to wordnouns;
		if (!isNoun(nounB) || !isNoun(nounA)) {
			throw new IllegalArgumentException("the nouns don't belong to wordnet.");
		}
		return sap.length(getKeys(nounA), getKeys(nounB));
	}

	public String sap(String nounA, String nounB) {
		if (nounA == null || nounB == null) {
			throw new IllegalArgumentException("please input strings");
		}
		// check the nouns belongs to wordnouns;
		if (!isNoun(nounB) || !isNoun(nounA)) {
			throw new IllegalArgumentException("the nouns don't belong to wordnet.");
		}
		int ancestorID = sap.ancestor(getKeys(nounA), getKeys(nounB));
		int index = indexList.indexOf(ancestorID);
		return synsetsList.get(index);
	}

	private Iterable<Integer> getKeys(String noun) {
		Stack<Integer> keys = new Stack<Integer>();
		int num = synsetsList.size();
		for (int i = 0; i < num; i++) {
			if (synsetsList.get(i).equals(noun)) {
				keys.push(indexList.get(i));
			}
		}
		// synsetsList.entrySet();
		// minp
		// for (Entry<Integer, String> entry : synsetsList.entrySet()) {
		// if (entry.getValue().equals(noun)) {
		// keys.push(entry.getKey());
		// }
		// }
		return keys;
	}

	public static void main(String[] args) {
		WordNet2 wordnet = new WordNet2(args[0], args[1]);
		String s1 = "catechist";
		String s2 = "lotus_land";
		// System.out.println(wordnet.synsetsList.get(0));
		System.out.println(wordnet.getKeys(s1));
		System.out.println(wordnet.getKeys(s2));
		System.out.println(wordnet.distance(s1, s2));
		System.out.println(wordnet.sap(s1, s2));
		System.out.println(wordnet);
		// for (String s : wordnet.nouns()) {
		// System.out.println(s);
		// }
		// System.out.println(wordnet.isNoun('a'));
		// System.out.println(wordnet.isNoun('d'));
		// System.out.println(wordnet.distance('a', 'd'));
		// System.out.println(wordnet.sap('a', 'd'));
		// System.out.println(wordnet.getKeys('d'));
		// In in = new In(args[0]);
		// String s = in.readLine();
		// while (s != null) {
		// String[] part = s.split(",");
		// System.out.println(s);
		// System.out.println(part[0]);
		// System.out.println(part[1]);
		// s = in.readLine();
		// }
	}
}