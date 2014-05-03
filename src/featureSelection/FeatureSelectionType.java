package featureSelection;

/**
 * Offers a selection of feature selection types to be used with the EmailProcessor.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 *
 */
public enum FeatureSelectionType {
	DOCUMENT_FREQUENCY,
	MUTUAL_INFORMATION,
	ODDS_RATIO,
	CHI_SQRD,
	SIMPLIFIED_CHI_SQRD,
	CPD;
	
	
	/**
	 * Returns the currently selected instance which implements FeatureScorable interface.
	 * 
	 * @return
	 */
	public FeatureScoreable getInstance() {
		FeatureScoreable output = null;
		
		switch (this) {
		case DOCUMENT_FREQUENCY:
			output = new DocumentFrequency();
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
