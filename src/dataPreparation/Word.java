package dataPreparation;

public class Word implements Comparable<Word> {
	private String word;
	private int spamCount;
	private int hamCount;
	private double featureScore;
	
	public Word(String word) {
		this.word = word;
		this.spamCount = 0;
		this.hamCount = 0;
		this.featureScore = 0;
	}
	
	public String word() {
		return this.word;
	}
	
	public int getSpamCount() {
		return this.spamCount;
	}
	
	public void setSpamCount(int spamCount) {
		this.spamCount = spamCount;
	}
	
	public void incrementSpamCount() {
		this.spamCount++;
	}
	
	public int getHamCount() {
		return this.hamCount;
	}
	
	public void setHamCount(int hamCount) {
		this.hamCount = hamCount;
	}
	
	public void incrementHamCount() {
		this.hamCount++;
	}
	
	public double getFeatureScore() {
		return this.featureScore;
	}
	
	public void setFeatureScore(double featureScore) {
		this.featureScore = featureScore;
	}
	
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