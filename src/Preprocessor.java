import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;


public class Preprocessor {
	private HashMap<String, Integer> stopWords;
	
	public Preprocessor(File stopWordsFile) throws IOException {
		Scanner scnr = new Scanner(stopWordsFile);
		
		stopWords = new HashMap<String, Integer>();
		
		while (scnr.hasNext()) {
			stopWords.put(scnr.next(), 0);
		}
		
		scnr.close();
	}
	
	
	private boolean isPunctuation(String input) {
		return input.length() == 1 && input.matches("[^a-zA-Z]");
	}
	
	private boolean isNumber(String input) {
		char [] chars = input.toCharArray();
		
		for (char c : chars) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isStopWord(String input) {
		return stopWords.containsKey(input);
	}
	
	
	public Document parseDocument(File file) throws IOException {
		Document output = new Document();
		
		Scanner scnr = new Scanner(file);
				
		// Get subject content
		String[] subject = scnr.nextLine().split("\\s");
				
		for (int i = 1; i < subject.length; ++i) {
			if (!isPunctuation(subject[i]) 
					&& !isNumber(subject[i])
					&& !isStopWord(subject[i])) {
				output.addToSubject(subject[i]);
			}
		}
		
		
		// Get body content
		while (scnr.hasNextLine()) {
			String[] bodyLine = scnr.nextLine().split("\\s");
			
			for (int i = 0; i < bodyLine.length; ++i) {
				if (!isPunctuation(bodyLine[i]) 
						&& !isNumber(bodyLine[i])
						&& !isStopWord(bodyLine[i])) {
					output.addToBody(bodyLine[i]);
				}
			}
		}
		
		output.sortDocument();
		
		scnr.close();
		return output;
	}
	
	
}
