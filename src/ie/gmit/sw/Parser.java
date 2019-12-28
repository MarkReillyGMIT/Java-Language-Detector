package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Parser implements Runnable {
	private String file;
	private int k;
	private Database db = null;
	private static Scanner console = new Scanner(System.in);
	private static String DataSet;
	private static int ngrams;

	
	public Parser(String file, int k) {
		this.file=file;
		this.k=k;
	}
	
	public void setDb(Database db) {
		this.db=db;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			
			while((line=br.readLine()) != null) {
				String[] record = line.trim().split("@");
				if(record.length !=2)continue;
				parse(record[0], record[1]);
			}
			
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void parse(String text, String lang, int...ks) {
		// TODO Auto-generated method stub
		Language language = Language.valueOf(lang);
		
		for(int i=0; i<= text.length() - k; i++) {
			CharSequence kmer = text.substring(i, i + k);
			db.add(kmer, language);
		}
		
	}
	
	
	public static void main(String[] args) throws Throwable{
			
        System.out.print("\nEnter Source Path for the Data Set: ");
		DataSet = console.nextLine();
		
		//System.out.print("\nEnter Source Path for the Query File: ");
		//Query = console.nextLine();
		
		//System.out.print("\nEnter the amount ngrams: ");
		//ngrams = console.nextInt();
		
		Parser p = new Parser(DataSet,ngrams);
			      
		Database db = new Database();
		p.setDb(db);
		Thread t =new Thread(p);
		t.start();
		t.join();
		
		
		db.resize(300);
		System.out.println(db);
		//System.out.println(queryText);
		
		 //wili-2018-Small-11750-Edited.txt
		//String s= "Sebes, Joseph; Pereira Thomas (1961) (pÃ¥ eng). The Jesuits and the Sino-Russian treaty of Nerchinsk (1689): the diary of Thomas Pereira. Bibliotheca Instituti historici S. I., 99-0105377-3 ; 18. Rome. Libris";
	}

}
