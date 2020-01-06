package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Class <i>Parser</i> reads the user input into ngram's for the dataset and the
 * text file, reads each file line by line. Implements <code>Runnable</code>.
 * 
 * @author Mark Reilly
 *
 */
public class Parser implements Runnable {
	private String fileName;
	private String queryFile;
	private int k;
	private Database db = null;

	public Parser() {
		
	}
	public Parser(String fileName, int k, String queryFile) {
		this.fileName = fileName;
		this.k = k;
		this.queryFile = queryFile;
	}

	public void setDb(Database db) {
		this.db = db;
	}

	/**
	 * Reads in file for the text input, iterates through each line of the text file
	 * Splits the line into two records when it comes across an <b>@</b> symbol.
	 * Calls method parse() and passes it two records. Reads in query file.
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

			String line = null;

			while ((line = br.readLine()) != null) {
				String[] record = line.trim().split("@");
				if (record.length != 2)
					continue;
				parse(record[0], record[1]);
			}

			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Parse's each line of the dataset into n-grams depending on the size of
	 * <code>k</code>. Calls the method add() in the class <i>Database</i> and
	 * passes it all the n-grams of the specified language.
	 * 
	 * @param text
	 * @param lang
	 * @param ks
	 */
	private void parse(String text, String lang, int... ks) {
		// TODO Auto-generated method stub
		Language language = Language.valueOf(lang);

		for (int i = 0; i <= text.length() - k; i++) {
			CharSequence kmer = text.substring(i, i + k);
			db.add(kmer, language);
		}

	}

	/**
	 * Read's each line in the text file. Passes <code>String queryFile</code> to the for loop.
	 * Parse's each line into n-grams of size <code>k</code> converts the n-gram to
	 * hashCode, if <code>textQueryMap</code> contains the ngram increment
	 * frequency. Add new values to the <code>textQueryMap</code>.Output the 
	 * result at the end.
	 * 
	 * @param textFile
	 */
	public void fileParser(String textFile) throws IOException {
		String queryFile;
		Map<Integer, LanguageEntry> textQueryMap = new HashMap<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(textFile)));
		
		while((queryFile = br.readLine()) !=null){
			
		for (int i = 0; i <= queryFile.length() - k; i++) {
			CharSequence ngram = queryFile.substring(i, i + k);
			int kmerHash = ngram.hashCode();

			int frequency = 1;
			if (textQueryMap.containsKey(kmerHash)) {
				frequency += textQueryMap.get(kmerHash).getFrequency();
			}

			textQueryMap.put(kmerHash, new LanguageEntry(kmerHash, frequency));
		}

	}
		Language language = db.getLanguage(textQueryMap);
		 System.out.println("The text appears to be written in " + language);
		 br.close();
	}

	/*
	 * wili-2018-Small-11750-Edited.txt
	 * 
	 */
}
