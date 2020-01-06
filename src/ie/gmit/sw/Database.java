package ie.gmit.sw;

import java.util.*;

/**
 * Class <i>Database</i> contains methods for creating Maps for each language
 * and calculating the right Language of the input text file.
 * 
 * @author Mark Reilly
 *
 */
public class Database {
	private Map<Language, Map<Integer, LanguageEntry>> db = new TreeMap<>();

	/**
	 * Adds an ngram from the input dataset to a map <code>langDb</code>.
	 * 
	 * @param s    is convert to hashcode, to reduce memory.Passed from
	 *             class<i>Parser</i>
	 * @param lang list of languages.
	 */
	public void add(CharSequence s, Language lang) {
		int kmer = s.hashCode();
		Map<Integer, LanguageEntry> langDb = getLanguageEntries(lang);

		int frequency = 1;
		if (langDb.containsKey(kmer)) {
			frequency += langDb.get(kmer).getFrequency();
		}
		langDb.put(kmer, new LanguageEntry(kmer, frequency));

	}

	/**
	 * Checks the treeMap <b>db</b> to see if it contains the <code>lang</code> from
	 * class <i>Language</i> if it does, return <code>langDb</code> else create new
	 * treeMap and add the <code>lang</code> to the <b>db</b>
	 * 
	 * @param lang
	 * @return langDb with language.
	 */
	private Map<Integer, LanguageEntry> getLanguageEntries(Language lang) {
		Map<Integer, LanguageEntry> langDb = null;
		if (db.containsKey(lang)) {
			langDb = db.get(lang);
		} else {
			langDb = new TreeMap<Integer, LanguageEntry>();
			db.put(lang, langDb);
		}
		return langDb;
	}

	/**
	 * Adds the top <code> max </code> amount of n-grams for each language to
	 * <b>db</b>
	 * 
	 * @param max equals the value of the top n-grams to add to the <b>db</b>
	 */
	public void resize(int max) {
		Set<Language> keys = db.keySet();
		for (Language lang : keys) {
			Map<Integer, LanguageEntry> top = getTop(max, lang);
			db.put(lang, top);
		}
	}

	// Sort Language Map in ascending order
	/**
	 * Sorts the Language Map in ascending order. Gives each n-gram a rank based on
	 * their frequency. Get's the top <code>max</code> amount of n-grams for each
	 * language.
	 * 
	 * @param max
	 * @param lang
	 * @return
	 */
	public Map<Integer, LanguageEntry> getTop(int max, Language lang) {
		Map<Integer, LanguageEntry> temp = new TreeMap<>();
		List<LanguageEntry> les = new ArrayList<>(db.get(lang).values());
		Collections.sort(les);

		int rank = 1;
		for (LanguageEntry le : les) {
			le.setRank(rank);
			temp.put(le.getKmer(), le);
			if (rank == max)
				break;
			rank++;
		}
		return temp;
	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	public Language getLanguage(Map<Integer, LanguageEntry> query) {
		TreeSet<OutOfPlaceMetric> oopm = new TreeSet<>();

		Set<Language> langs = db.keySet();
		for (Language lang : langs) {
			oopm.add(new OutOfPlaceMetric(lang, getOutOfPlaceDistance(query, db.get(lang))));
		}
		return oopm.first().getLanguage();
	}

	private int getOutOfPlaceDistance(Map<Integer, LanguageEntry> query, Map<Integer, LanguageEntry> subject) {
		int distance = 0;
		Set<LanguageEntry> les = new TreeSet<>(query.values());
		for (LanguageEntry q : les) {
			LanguageEntry s = subject.get(q.getKmer());
			if (s == null) {
				distance += subject.size() + 1;
			} else {
				distance += s.getRank() - q.getRank();
			}
		}
		return distance;
	}

	/**
	 * Show's the distance between the language and the query.
	 * Implements <code>comparable</code>
	 * @author Mark Reilly
	 *
	 */
	private class OutOfPlaceMetric implements Comparable<OutOfPlaceMetric> {
		private Language lang;
		private int distance;

		public OutOfPlaceMetric(Language lang, int distance) {
			super();
			this.lang = lang;
			this.distance = distance;
		}

		public Language getLanguage() {
			return lang;
		}

		public int getAbsoluteDistance() {
			return Math.abs(distance);
		}

		@Override
		public int compareTo(OutOfPlaceMetric o) {
			return Integer.compare(this.getAbsoluteDistance(), o.getAbsoluteDistance());
		}

		@Override
		public String toString() {
			return "[lang=" + lang + ", distance=" + getAbsoluteDistance() + "]";
		}

	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		int langCount = 0;
		int kmerCount = 0;
		Set<Language> keys = db.keySet();

		for (Language lang : keys) {
			langCount++;
			sb.append(lang.name() + "->\n");

			Collection<LanguageEntry> m = new TreeSet<>(db.get(lang).values());
			kmerCount += m.size();
			for (LanguageEntry le : m) {
				sb.append("\t" + le + "\n");
			}
		}
		sb.append(kmerCount + " total k-mers in " + langCount + " languages");
		return sb.toString();
	}
}