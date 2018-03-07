import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {
	// private ArrayList<String> synsetsList;
	// private ArrayList<Integer> indexList;
	private Digraph wordG;
	private final SAP sap;
	private Set<String> nouns;
	private Map<Integer, String> synsetsMap;
	private Map<String, HashSet<Integer>> nounsIndexMap;

	private void mapSynsets(String synsets) {
		// synsetsList = new HashMap<Integer, String>();
		// synsetsList = new ArrayList<String>();
		// indexList = new ArrayList<Integer>();
		synsetsMap = new HashMap<Integer, String>();
		nounsIndexMap = new HashMap<String, HashSet<Integer>>();
		nouns = new HashSet<String>();
		In in = new In(synsets);
		String s = in.readLine();
		int id = 0;
		while (s != null) {
			String[] parts = s.split(",");
			id = Integer.parseInt(parts[0]);
			synsetsMap.put(id, parts[1].trim());
			String[] strings = parts[1].split("\\s+");
			for (String str : strings) {
				nouns.add(str);
				if (!nounsIndexMap.containsKey(str)) {
					HashSet<Integer> indexSet = new HashSet<Integer>();
					indexSet.add(id);
					nounsIndexMap.put(str, indexSet);
				} else {
					nounsIndexMap.get(str).add(id);
				}
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

	public WordNet(String synsets, String hypernyms) {
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
		return nouns;
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
		return nouns.contains(word);
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
		return synsetsMap.get(ancestorID);
	}

	private Iterable<Integer> getKeys(String noun) {
		// int num = synsetsList.size();
		// for (int i = 0; i < num; i++) {
		// if (synsetsList.get(i).equals(noun)) {
		// keys.push(indexList.get(i));
		// }
		// }
		// synsetsList.entrySet();
		// minp
		// for (Entry<Integer, String> entry : synsetsList.entrySet()) {
		// if (entry.getValue().equals(noun)) {
		// keys.push(entry.getKey());
		// }
		// }
		return nounsIndexMap.get(noun);
	}

	public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0], args[1]);
		String s1 = "substantia_grisea";
		String s2 = "funny_bone";
		// System.out.println(wordnet.synsetsList.get(0));
		System.out.println(wordnet.getKeys(s1));
		System.out.println(wordnet.getKeys(s2));
		System.out.println(wordnet.distance(s1, s2));
		System.out.println(wordnet.sap(s1, s2));
		System.out.println(wordnet.nouns.size());
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