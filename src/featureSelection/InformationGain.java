package featureSelection;

import datapreparation.Word;

public class InformationGain implements FeatureScoreable {

	private double compute(int wordSpamCount, int wordHamCount, int spamCount, int hamCount) {
		int nonWordSpamCount = spamCount - wordSpamCount;
		int nonWordHamCount = hamCount - wordHamCount;
		
		double spamScore = getScore(wordSpamCount, wordHamCount, nonWordSpamCount, nonWordHamCount);
		double hamScore = getScore(wordHamCount, wordSpamCount, nonWordHamCount, nonWordSpamCount);		
				
		return spamScore + hamScore;
	}

	
	private double getScore(int A, int B, int C, int D) {
		int POS = A + C;
		int NEG = B + D;
		double probOfWord = (A + B) / (double) (A + B + C + D);
		
		// TODO: Not sure what True/False positive/negatives are...
		int TP = 0, FP = 0, TN = 0, FN = 0;
		
		return e(POS, NEG) - (probOfWord * e(TP, FP) + (1 - probOfWord) * e(FN, TN));
	}
	
	private double e(double x, double y) {
		return -(x / (x + y)) * Math.log(x / (x + y)) - (y / (x + y)) * Math.log(y / (x + y));
	}


	@Override
	public void setFeatureScore(Word word, int spamCount, int hamCount) {
		double feaSco = compute(word.getSpamCount(), word.getHamCount(), spamCount, hamCount);
		word.setFeatureScore(feaSco);
	}
}
