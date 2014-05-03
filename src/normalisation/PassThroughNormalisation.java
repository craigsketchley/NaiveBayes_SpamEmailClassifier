package normalisation;

/**
 * Offers a pass through when normalising the input vectors, leaving them as they are.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 *
 */
class PassThroughNormalisation implements Normalisable {

	@Override
	public void normalise(double[][] inputVectors) {
		return;
	}

}
