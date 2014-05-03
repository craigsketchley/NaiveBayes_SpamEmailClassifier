package normalisation;

/**
 * Normalises the distribution of input vectors to their standard deviation is 1
 * and mean is 0.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 * 
 */
class DistributionNormalisation implements Normalisable {

	@Override
	public void normalise(double[][] inputVectors) {
		// For each dimension, calculate the std dv.
		if (inputVectors.length == 0) {
			return;
		}
		int vectorDimension = inputVectors[0].length;

		for (int dimIndex = 0; dimIndex < vectorDimension; dimIndex++) {
			double sum = 0;
			double sqrdSum = 0;
			for (int i = 0; i < inputVectors.length; i++) {
				sum += inputVectors[i][dimIndex];
				sqrdSum += inputVectors[i][dimIndex]
						* inputVectors[i][dimIndex];
			}
			double mean = sum / vectorDimension;
			double stdDev = sqrdSum / vectorDimension - mean * mean;

			for (int i = 0; i < inputVectors.length; i++) {
				inputVectors[i][dimIndex] -= mean; // mean of zero.
				inputVectors[i][dimIndex] /= stdDev; // stdDev of one.
			}
		}
	}

}
