package featureSelection;

import dataPreparation.Word;

public class SimplifiedChiSqrd implements FeatureScoreable {

	private double compute(int wordSpamCount, int wordHamCount, int spamCount,
			int hamCount) {
		int nonWordSpamCount = spamCount - wordSpamCount;
		int nonWordHamCount = hamCount - wordHamCount;
		
		double spamScore = getScore(wordSpamCount, wordHamCount, nonWordSpamCount, nonWordHamCount);
		double hamScore = getScore(wordHamCount, wordSpamCount, nonWordHamCount, nonWordSpamCount);		
		
		return Math.max(spamScore, hamScore);
	}

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
