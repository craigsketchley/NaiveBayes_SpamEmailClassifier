package featureSelection;

import dataPreparation.Word;

public interface FeatureScoreable {

	/**
	 * Calculates the featureScore for the given word, and sets the Word
	 * property featureScore.
	 * 
	 * @param inputWord
	 */
	public void setFeatureScore(Word word, int spamCount, int hamCount);

}
