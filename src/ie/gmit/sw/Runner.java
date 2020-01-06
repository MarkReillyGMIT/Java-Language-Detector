package ie.gmit.sw;

import java.io.IOException;

/**
 * Class <i>Runner</i> starts the Language Detector Program. Calls <code>showMenu()</code> which starts
 * the Program.
 * 
 * @author Mark Reilly
 * @version 1.0
 * @since 1.8
 *
 */
public class Runner {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Menu m = new Menu();
		m.showMenu();

	}

}
