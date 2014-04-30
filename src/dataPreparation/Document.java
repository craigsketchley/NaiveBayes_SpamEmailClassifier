package dataPreparation;
import java.util.HashMap;
import java.util.Set;

public class Document {
	private HashMap<String, Integer> words;
	private boolean isSpam;

	public Document() {
		words = new HashMap<String, Integer>();
		isSpam = false;
	}
	
	public void addToWords(String word) {
		if (words.containsKey(word)) {
			words.put(word, words.get(word) + 1);
		} else {
			words.put(word, 1);
		}
	}
	
	public boolean hasWord(String word) {
		return words.containsKey(word);
	}
	
	public int getWordCount(String word) {
		return (words.get(word) != null ? words.get(word) : 0);
	}
	
	public Set<String> getWords() {
		return words.keySet();
	}
	
	public void setSpam() {
		isSpam = true;
	}
	
	public boolean isSpam() {
		return isSpam;
	}
}
