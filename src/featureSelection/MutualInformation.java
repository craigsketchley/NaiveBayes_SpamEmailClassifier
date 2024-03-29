package featureSelection;

import dataPreparation.Word;

/**
 * Calculates the Mutual Information score for a word/feature.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 * 
 */
class MutualInformation implements FeatureScoreable {

	private double compute(int wordSpamCount, int wordHamCount, int spamCount,
			int hamCount) {
		int nonWordSpamCount = spamCount - wordSpamCount;
		int nonWordHamCount = hamCount - wordHamCount;
		
		double spamScore = getScore(wordSpamCount, wordHamCount, nonWordSpamCount, nonWordHamCount);
		double hamScore = getScore(wordHamCount, wordSpamCount, nonWordHamCount, nonWordSpamCount);		
		
		return Math.max(spamScore, hamScore);
	}
	
	private double getScore(int A, int B, int C, int D) {
		double output = (A * (A + B + C + D)) / ((A + B) + (C + D));
		return Math.log(output);
	}

	@Override
	public void setFeatureScore(Word word, int spamCount, int hamCount) {
		double feaSco = compute(word.getSpamCount(), word.getHamCount(), spamCount, hamCount);
		word.setFeatureScore(feaSco);
	}

}
