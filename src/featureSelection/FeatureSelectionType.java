package featureSelection;

public enum FeatureSelectionType {
	DOCUMENT_FREQUENCY,
	INFORMATION_GAIN,
	MUTUAL_INFORMATION,
	ODDS_RATIO,
	CHI_SQRD,
	SIMPLIFIED_CHI_SQRD,
	CPD;
	
	
	public FeatureScoreable getInstance() {
		FeatureScoreable output = null;
		
		switch (this) {
		case DOCUMENT_FREQUENCY:
			output = new DocumentFrequency();
			break;
		case INFORMATION_GAIN:
			output = new InformationGain();
			break;
		case MUTUAL_INFORMATION:
			output = new MutualInformation();
			break;
		case ODDS_RATIO:
			output = new OddsRatio();
			break;
		case CHI_SQRD:
			output = new ChiSqrd();
			break;
		case SIMPLIFIED_CHI_SQRD:
			output = new SimplifiedChiSqrd();
			break;
		case CPD:
			output = new CategoricalProportionalDifference();
			break;
		}
		
		return output;
	}
}
