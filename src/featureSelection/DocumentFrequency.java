package featureSelection;

import dataPreparation.Word;

public class DocumentFrequency implements FeatureScoreable {
	
	@Override
	public void setFeatureScore(Word word, int spamCount, int hamCount) {
		word.setFeatureScore(word.getSpamCount() + word.getHamCount());
	}

}
