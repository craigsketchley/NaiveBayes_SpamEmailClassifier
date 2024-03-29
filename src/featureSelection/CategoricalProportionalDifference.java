package featureSelection;

import dataPreparation.Word;

/**
 * Calculates the Categorical Proportional Difference for a word/feature.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 * 
 */
class CategoricalProportionalDifference implements FeatureScoreable {

	private double compute(int wordSpamCount, int wordHamCount, int spamCount, int hamCount) {
		double denom = wordHamCount + wordSpamCount;
		
		double spamScore = (wordSpamCount - wordHamCount) / denom;
		double hamScore = (wordHamCount - wordSpamCount) / denom;
		
		return Math.max(spamScore, hamScore);
	}

	@Override
	public void setFeatureScore(Word word, int spamCount, int hamCount) {
		double feaSco = compute(word.getSpamCount(), word.getHamCount(), spamCount, hamCount);
		word.setFeatureScore(feaSco);
	}

}
