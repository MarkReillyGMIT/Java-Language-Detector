package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Parser implements Runnable {
	private String fileName;
	private String queryFile;
	private int k;
	private Database db = null;

	public Parser(String fileName, int k, String queryFile) {
		this.fileName = fileName;
		this.k = k;
		this.queryFile=queryFile;
	}

	public void setDb(Database db) {
		this.db = db;
	}

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
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(queryFile)));
			String line;
			while ((line = br.readLine()) != null) {
				//System.out.println(line.replaceAll("\\s+",""));
				line.replaceAll("\\s+","+");
				System.out.println(line);
				//fileParser(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	private void parse(String text, String lang, int... ks) {
		// TODO Auto-generated method stub
		Language language = Language.valueOf(lang);

		for (int i = 0; i <= text.length() - k; i++) {
			CharSequence kmer = text.substring(i, i + k);
			db.add(kmer, language);
		}

	}
	
	public void fileParser(String text) {
		Map<Integer, LanguageEntry> textQueryMap = new HashMap<>();

		for (int i = 0; i <= text.length() - k; i++) {
			CharSequence ngram = text.substring(i, i + k);
			int kmerHash = ngram.hashCode();

			int frequency = 1;
			if (textQueryMap.containsKey(kmerHash)) {
				frequency += textQueryMap.get(kmerHash).getFrequency();
			}

			textQueryMap.put(kmerHash, new LanguageEntry(kmerHash, frequency));
		}

		System.out.println(db.getLanguage(textQueryMap));

	}


		/* 
		 * wili-2018-Small-11750-Edited.txt
		 * */

}
