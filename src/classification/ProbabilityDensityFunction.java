package classification;

/**
 * Represents a normal probability distribution of a feature set.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 *
 */
class ProbabilityDensityFunction {
	private final double mean;
	private final double stddv;
	private final double exponentDivisor;
	private final double divisor;
	
	private final double STD_DEV_SMALL = 1e-4;

	/**
	 * Creates a probability density function from a mean and standard deviation.
	 * 
	 * @param mean
	 * @param stddv
	 */
	public ProbabilityDensityFunction(double mean, double stddv) {
		// Make the std dev very small if == 0
		if (stddv == 0) {
			stddv = STD_DEV_SMALL;
		}
		
		this.mean = mean;
		this.stddv = stddv;
		this.exponentDivisor = 1.0 / (-2 * stddv * stddv);
		this.divisor = 1.0 / (stddv * Math.sqrt(2 * Math.PI));
	}
	
	/**
	 * Returns the probability of obtaining the value for the given feature.
	 * 
	 * @param value		the value for which the probability is required
	 * @return 			the probability
	 */
	public double calculateProbability(double value) {
		return divisor * Math.exp( (value - mean) * (value - mean) * exponentDivisor );
	}
	
	/**
	 * Returns the log of the probability of obtaining the value for the given feature.
	 * 
	 * @param value		the value for which the probability is required
	 * @return			the log of the probability
	 */
	public double calculateLogProbability(double value) {
		return Math.log(divisor) + (value - mean) * (value - mean) * exponentDivisor;
	}
	
	/**
	 * Returns the mean.
	 * 
	 * @return
	 */
	public double getMean() {
		return mean;
	}
	
	/**
	 * Returns the standard deviation.
	 * 
	 * @return
	 */
	public double getStandardDeviation() {
		return stddv;
	}
}
