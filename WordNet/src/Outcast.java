import edu.princeton.cs.algs4.In;

public class Outcast {
	private final WordNet outcast;

	public Outcast(WordNet wordnet) {
		this.outcast = wordnet;
	}

	public String outcast(String[] nouns) {
		int length = nouns.length;
		int maxDist = 0;
		int index = 0;
		for (int i = 0; i < length; i++) {
			int dist = 0;
			for (int j = 0; j < length; j++) {
				dist += outcast.distance(nouns[i], nouns[j]);
			}
			if (dist > maxDist) {
				maxDist = dist;
				index = i;
			}
		}
		return nouns[index];
	}

	public static void main(String[] args) {
		Outcast cast = new Outcast(new WordNet(args[0], args[1]));
		In in = new In(args[2]);
		String nouns[] = in.readAllStrings();
		System.out.println(cast.outcast(nouns));
	}
}
