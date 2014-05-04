package normalisation;

/**
 * Offers a selection of normalisation types to be used with the EmailProcessor.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 *
 */
public enum NormalisationType {
	COSINE_NORM,
	STD_SCORE_NORM,
	NONE;
	
	/**
	 * Returns the currently selected instance which implements Normalisable interface.
	 * 
	 * @return	Normalisable
	 */
	public Normalisable getInstance() {
		switch (this) {
		case COSINE_NORM:
			return new CosineNormalisation();
		case STD_SCORE_NORM:
			return new StandardScoreNormalisation();
		case NONE:
			return new PassThroughNormalisation();
		}
		return null;
	}
	
}
