package dataPreparation;

/**
 * An object to store a word feature together with it's relevant statistics.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 *
 */
public class Word implements Comparable<Word> {
	private String word;
	private int spamCount;
	private int hamCount;
	private double featureScore;
	
	/**
	 * Creates a word object.
	 * 
	 * @param word
	 */
	public Word(String word) {
		this.word = word;
		this.spamCount = 0;
		this.hamCount = 0;
		this.featureScore = 0;
	}
	
	/**
	 * Gets the word string.
	 * 
	 * @return
	 */
	public String word() {
		return this.word;
	}
	
	/**
	 * Gets the number of spam emails this word occurs.
	 * 
	 * @return
	 */
	public int getSpamCount() {
		return this.spamCount;
	}
	
	/**
	 * Sets the number of spam emails this word occurs.
	 * 
	 * @param spamCount
	 */
	public void setSpamCount(int spamCount) {
		this.spamCount = spamCount;
	}
	
	/**
	 * Increments the number of spam emails this word occurs by one.
	 */
	public void incrementSpamCount() {
		this.spamCount++;
	}
	
	/**
	 * Gets the number of ham emails this word occurs.
	 * 
	 * @return
	 */
	public int getHamCount() {
		return this.hamCount;
	}
	
	/**
	 * Sets the number of ham emails this word occurs.
	 * 
	 * @param spamCount
	 */
	public void setHamCount(int hamCount) {
		this.hamCount = hamCount;
	}
	
	/**
	 * Increments the number of ham emails this word occurs by one.
	 */
	public void incrementHamCount() {
		this.hamCount++;
	}
	
	/**
	 * Gets the currently assigned feature score for this word.
	 * @return
	 */
	public double getFeatureScore() {
		return this.featureScore;
	}
	
	/**
	 * Sets the feature score for this word.
	 * 
	 * @param featureScore
	 */
	public void setFeatureScore(double featureScore) {
		this.featureScore = featureScore;
	}
	
	/**
	 * Returns the total email count that this word occurs.
	 * @return
	 */
	public int getDocumentFrequency() {
		return spamCount + hamCount;
	}
	
	@Override
	public String toString() {
		return word + " [fs = " + featureScore + ", df = " + getDocumentFrequency() + "]";
	}

	@Override
	public int compareTo(Word o) {
		// Order by featureScore
		if (this.featureScore > o.featureScore) {
			return 1;
		} else if (this.featureScore < o.featureScore) {
			return -1;
		} else {
			// Break ties by opting for words with higher DocFreq
			if (this.getDocumentFrequency() > o.getDocumentFrequency()) {
				return 1;
			} else if (this.getDocumentFrequency() < o.getDocumentFrequency()) {
				return -1;
			} else {
				// Finally, natural ordering...
				return word.compareTo(o.word);
			}
		}
	}
}