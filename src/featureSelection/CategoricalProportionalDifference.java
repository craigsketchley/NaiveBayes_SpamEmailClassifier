package featureSelection;

import datapreparation.Word;

public class CategoricalProportionalDifference implements FeatureScoreable {

	public double compute(int wordSpamCount, int wordHamCount, int spamCount,
			int hamCount) {
		return (wordSpamCount - wordHamCount) / (double) (wordSpamCount + wordHamCount);
	}

	@Override
	public void setFeatureScore(Word word, int spamCount, int hamCount) {
		double feaSco = compute(word.getSpamCount(), word.getHamCount(), spamCount, hamCount);
		word.setFeatureScore(feaSco);
	}

}
