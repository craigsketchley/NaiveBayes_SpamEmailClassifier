package normalisation;

/**
 * Normalises the distribution of input vectors to their standard deviation is 1.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 * 
 */
class StandardScoreNormalisation implements Normalisable {

	@Override
	public void normalise(double[][] inputVectors) {
		// For each dimension, calculate the std dv.
		if (inputVectors.length == 0) {
			return;
		}
		int vectorDimension = inputVectors[0].length;

		for (int dimIndex = 0; dimIndex < vectorDimension; dimIndex++) {
			double sum = 0;
			for (int i = 0; i < inputVectors.length; i++) {
				sum += inputVectors[i][dimIndex];
			}
			double mean = sum / inputVectors.length;
			
			sum = 0;
			for (int i = 0; i < inputVectors.length; i++) {
				sum += (inputVectors[i][dimIndex] - mean) * (inputVectors[i][dimIndex] - mean);
			}
			
			double stdDev = Math.sqrt(sum / inputVectors.length);

			for (int i = 0; i < inputVectors.length; i++) {
//				inputVectors[i][dimIndex] -= mean; // mean of zero
				inputVectors[i][dimIndex] /= stdDev; // stdDev of one.
			}
		}
	}

}
