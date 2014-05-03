package classification;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * FeatureStatistics is used to hold the statistics for each class for a
 * specific feature.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 * 
 */
class FeatureStatistics {
	// The probability density functions for each class, keyed on the class
	// names.
	private HashMap<String, ProbabilityDensityFunction> classProbabilityFunctions;
	
	/**
	 * Creates a FeatureStatistics instance.
	 */
	private FeatureStatistics() {
		classProbabilityFunctions = new HashMap<String, ProbabilityDensityFunction>();
	}
	
	/**
	 * Creates and stores a probability density function for the given class of this feature.
	 * 
	 * @param className
	 * @param mean
	 * @param stddv
	 */
	private void addClassStatistics(String className, double mean, double stddv) {
		classProbabilityFunctions.put(className, new ProbabilityDensityFunction(mean, stddv));
	}

	/**
	 * Returns the probability of obtaining the value for this feature of a
	 * given class. Note: Should not be used in the Naive Bayes algorithm since
	 * the probabilities can be very small which may lead to underflow errors,
	 * use calculateLogProbabilityForClass() instead.
	 * 
	 * @param value
	 *            the value for which the probability is required
	 * @param className
	 *            the name of the class the probability is required.
	 * @return the probability
	 */
	public double calculateProbabilityForClass(double value, String className) {
		return classProbabilityFunctions.get(className).calculateProbability(
				value);
	}

	/**
	 * Returns the logarithm of the probability of obtaining the value for this
	 * feature of a given class.
	 * 
	 * @param value
	 *            the value for which the probability is required
	 * @param className
	 *            the name of the class the probability is required.
	 * @return the probability
	 */
	public double calculateLogProbabilityForClass(String className, double value) {
		return classProbabilityFunctions.get(className)
				.calculateLogProbability(value);
	}
	
	/**
	 * Returns a String representation of this Object.
	 * 
	 * @return	the Object as a String
	 */
	public String toString() {
		String output = "{";
		
		for (String className : classProbabilityFunctions.keySet()) {
			output += className + " : [mean=";
			output += classProbabilityFunctions.get(className).getMean() + ", stdDev=";
			output += classProbabilityFunctions.get(className).getStandardDeviation() + "],";
		}
		
		return output.substring(0, output.length() - 1) + "}";
	}
		
	/**
	 * Static method to create the array of FeatureStatistics given the examples.
	 * 
	 * Can only create feature statistics this way.
	 * 
	 * @param examples
	 * @param classIndices
	 * @return
	 */
	public static FeatureStatistics[] generateFeatureStatisticsArray(int numberOfFeatures, HashMap<String,ArrayList<Example>> classExamples) {
		FeatureStatistics[] output = new FeatureStatistics[numberOfFeatures];
		
		for (int featureIndex = 0; featureIndex < numberOfFeatures; featureIndex++) {
			output[featureIndex] = new FeatureStatistics(); 
			for (String className : classExamples.keySet()) {
				// calculate the mean/stdv of each class within the feature.
				double sum = 0;
				double sqrdSum = 0;
				int count = 0;
								
				for (Example example : classExamples.get(className)) {
					double val = example.getValue(featureIndex);
					sum += val;
					sqrdSum += val*val;
					count++;
				}
				
				double mean = sum /count;
				double stddv = sqrdSum / count - mean * mean;
				
				output[featureIndex].addClassStatistics(className, mean, stddv);
			}
		}
		
		return output;
	}

}
