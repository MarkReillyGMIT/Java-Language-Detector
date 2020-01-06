package ie.gmit.sw;

import java.io.IOException;
import java.util.Scanner;
import ie.gmit.sw.Parser;
/**
 * Menu() provides the UI for the program. Allows the user to enter input, threads
 * are started here.
 * @author Mark Reilly
 *
 */
public class Menu {
	
	private Scanner console = new Scanner(System.in);
	private int ngrams;
	private int userChoice;
	private String firstFile;
	private String secondFile;

	public String getFirstFile() {
		return firstFile;
	}

	public void setFirstFile(String firstFile) {
		this.firstFile = firstFile;
	}

	public String getSecondFile() {
		return secondFile;
	}

	public void setSecondFile(String secondFile) {
		this.secondFile = secondFile;
	}

	public int getNgrams() {
		return ngrams;
	}

	public void setNgrams(int ngrams) {
		this.ngrams = ngrams;
	}
	/**
	 * Prompts user for input, starts threads when all the input is entered.
	 * Sets the size of method <code>resize()</code> in Database.
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void showMenu() throws InterruptedException, IOException{
		userMenu();
		userChoice = console.nextInt();
		switch(userChoice) {
		case 1:
		Database db = new Database();
		fileInputPrompt();
		Parser p1 = new Parser(getFirstFile(), getNgrams(), getSecondFile());	
		p1.setDb(db);
		System.out.println("Processing Query...Please Wait...");
		
		Thread t1 =new Thread(p1);
		t1.start();		
		t1.join();
		
		db.resize(300);
		//Polish
		//String s ="Smocze Księżniczki, choć z grubsza tyle samo mężczyzn i kobiet, były potomkami Ithmitne i różnych zalotników przez lata i dlatego nosiły cechy drugiego rodzica, a także ojca, co oznaczało, że niektórzy byli uprzejmi i inni posłuszni byli agresywni i niezainteresowani sprawami państwa, ale wszyscy byli potrzebni, aby zabezpieczyć swoje rządy, ponieważ podczas gdy byli liczni, co najmniej dwa tuziny, z wystarczającą siłą, Domy Rodów Smoków mogły je obalić. Ostatecznie w 627LN Wielkie Domy powstały przeciwko Smoczym Książętom w wojnie nazwanej przez interweniującą partię, Kepeski Jot, w której pojawił się Starożytny Niebieski Smok Kepeski i odwrócił konflikt przeciwko Książętom.";
		//English
		//String s = "The Dragon Princes, though there were roughly equal numbers of males and females, were sired from Ithmitne and various suitors over the years and so bore the traits of their other parent as well as those of their father, which meant that while some were kind and dutiful others were aggressive and disinterested in the affairs of state but all were needed to secure their rule as while they were great in number, at least two dozen, with enough strength the Houses of the Dragon Lands could overthrow them. Eventually in 627LN, the Great Houses did rise up against the Dragon Princes in a war named for the intervening party, Kepeski Jot, wherein the Ancient Blue Dragon Kepeski appeared and turned the tied of the conflict against the Princes.";
		p1.fileParser(getSecondFile());	
		
		
			break;
		case 0:
			// Terminates program
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
	
	/**
	 * Method for UI.
	 */
	private void userMenu() {
		System.out.println("\n ----Language Detector---");
		System.out.println("(1) To enter files  ");
		System.out.println("(0) Exit ");
		System.out.print("==================================\nPlease Enter your option here: ");
	}
	/**
	 * Method for UI, gets the file inputs and ngram size input.
	 * @return
	 */
	private Menu fileInputPrompt() {
		this.firstFile = Helper.getInputString("(Enter Wili Data Location> ");
		this.secondFile = Helper.getInputString("Enter Query File Location> ");
		this.ngrams = Helper.getInputInt("Enter Size of N-gram to Use> ");
		return null;
	}
	
}
