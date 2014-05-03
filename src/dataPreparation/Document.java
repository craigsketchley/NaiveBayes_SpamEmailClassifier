package dataPreparation;

import java.util.HashMap;
import java.util.Set;

/**
 * Class to store the words and classification of an email document.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 * 
 */
class Document {
	private HashMap<String, Integer> words;
	private boolean isSpam;

	/**
	 * Create a new Document
	 */
	public Document() {
		words = new HashMap<String, Integer>();
		isSpam = false;
	}

	/**
	 * Add a word to this document.
	 * 
	 * @param word
	 */
	public void addToWords(String word) {
		if (words.containsKey(word)) {
			words.put(word, words.get(word) + 1);
		} else {
			words.put(word, 1);
		}
	}

	/**
	 * Checks if this document contains the given word.
	 * 
	 * @param word
	 * @return
	 */
	public boolean hasWord(String word) {
		return words.containsKey(word);
	}

	/**
	 * Gets the number of occurrences of the given word in this document.
	 * 
	 * @param word
	 * @return
	 */
	public int getWordCount(String word) {
		return (words.get(word) != null ? words.get(word) : 0);
	}

	/**
	 * Gets the set of words in this document.
	 * 
	 * @return
	 */
	public Set<String> getWords() {
		return words.keySet();
	}

	/**
	 * Sets the classification of this email document to spam.
	 */
	public void setSpam() {
		isSpam = true;
	}

	/**
	 * Checks if this email document is spam.
	 * 
	 * @return
	 */
	public boolean isSpam() {
		return isSpam;
	}
}
