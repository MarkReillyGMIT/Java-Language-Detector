package ie.gmit.sw;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ie.gmit.sw.Parser;

public class Menu {
	
	BlockingQueue<Database> queue = new LinkedBlockingQueue<>();
	private Scanner console = new Scanner(System.in);
	private String DataSet, Query;
	private int ngrams;
	private int userChoice;

	public String getDataSet() {
		return DataSet;
	}

	public void setDataSet(String dataSet) {
		DataSet = dataSet;
	}

	public String getQuery() {
		return Query;
	}

	public void setQuery(String query) {
		Query = query;
	}

	public int getNgrams() {
		return ngrams;
	}

	public void setNgrams(int ngrams) {
		this.ngrams = ngrams;
	}

	public void showMenu() throws InterruptedException{
		userMenu();
		userChoice = console.nextInt();
		switch(userChoice) {
		case 1:
		System.out.print("\nEnter Source Path for the Data Set: ");
		DataSet = console.nextLine();
		
		console.nextLine();
		
		System.out.print("\nEnter Source Path for the Query File: ");
		Query = console.nextLine();
		
		System.out.print("\nEnter the amount ngrams: ");
		ngrams = console.nextInt();
		
		Parser p = new Parser(getDataSet(),getNgrams());
				
		Database db = new Database();
		p.setDb(db);
		
		Thread t1 =new Thread(p);
		
		t1.start();
	
		t1.join();


		db.resize(300);
		System.out.println(db);
		
			break;
		case 0:
			// Terminates the program
			System.out.println("\nTerminating Program ...");
			System.exit(0);
			break;
		default:
			// If user input is invalid
			System.out.println("\n**Please enter either 0 or 1**");
			break;
		}
		this.showMenu();
	}
	
	public void userMenu() {
		System.out.println("\n ----Language Detector---");
		System.out.println("(1) To enter files  ");
		System.out.println("(0) Exit ");
		System.out.print("==================================\nPlease Enter your option here: ");
	}

}
