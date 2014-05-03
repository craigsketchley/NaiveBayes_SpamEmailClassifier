package featureSelection;

import dataPreparation.Word;

/**
 * A feature score based on the Simplified Chi Squared method.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 *
 */
class SimplifiedChiSqrd implements FeatureScoreable {

	/**
	 * Calculates the feature score for both spam and ham classes, returning the maximum.
	 * 
	 * @param wordSpamCount
	 * @param wordHamCount
	 * @param spamCount
	 * @param hamCount
	 * @return
	 */
	private double compute(int wordSpamCount, int wordHamCount, int spamCount,
			int hamCount) {
		int nonWordSpamCount = spamCount - wordSpamCount;
		int nonWordHamCount = hamCount - wordHamCount;
		
		double spamScore = getScore(wordSpamCount, wordHamCount, nonWordSpamCount, nonWordHamCount);
		double hamScore = getScore(wordHamCount, wordSpamCount, nonWordHamCount, nonWordSpamCount);		
		
		return Math.max(spamScore, hamScore);
	}

	/**
	 * The Simplified X^2 formula.
	 * 
	 * @param A
	 * @param B
	 * @param C
	 * @param D
	 * @return
	 */
	private double getScore(int A, int B, int C, int D) {
		int N = A + B + C + D;
		return (A * D - B * C) / (double) N * N;
	}

	@Override
	public void setFeatureScore(Word word, int spamCount, int hamCount) {
		double feaSco = compute(word.getSpamCount(), word.getHamCount(), spamCount, hamCount);
		word.setFeatureScore(feaSco);
	}

}
