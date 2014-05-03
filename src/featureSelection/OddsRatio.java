package featureSelection;

import dataPreparation.Word;

/**
 * A feature score based on the Odds Ratio method.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 *
 */
class OddsRatio implements FeatureScoreable {

	/**
	 * Returns the sum of all the classes feature score.
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
		
		return spamScore + hamScore;
	}
	
	/**
	 * The Odds Ratio formula.
	 * 
	 * @param A
	 * @param B
	 * @param C
	 * @param D
	 * @return
	 */
	private double getScore(int A, int B, int C, int D) {
		double denom = (B * C != 0) ? B * C : 1;
		return (A * D) / denom;
	}


	@Override
	public void setFeatureScore(Word word, int spamCount, int hamCount) {
		double feaSco = compute(word.getSpamCount(), word.getHamCount(), spamCount, hamCount);
		word.setFeatureScore(feaSco);
	}

}
