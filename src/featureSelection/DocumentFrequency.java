package featureSelection;

import dataPreparation.Word;

/**
 * Calculates the Document Frequency for a word.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 * 
 */
class DocumentFrequency implements FeatureScoreable {
	
	@Override
	public void setFeatureScore(Word word, int spamCount, int hamCount) {
		word.setFeatureScore(word.getSpamCount() + word.getHamCount());
	}

}
