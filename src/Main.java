import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * Contains the main method for the Crossword Solver tool
 * 
 * @author alexeastlake
 */
class Main {
	
	public static void main(String args[]) {
		WordleSolver wordleSolver = new WordleSolver();
		
		try {
			wordleSolver.readFile("dictionary.txt");
			new GUI(wordleSolver);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error reading word files:\n" + e.getMessage());
			System.exit(0);
		}
	}
}