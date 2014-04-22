package classification;

/**
 * FeatureStatistics is used to hold the statistics and calculate probabilities from a given feature set.
 * 
 * @author cske2656
 * @author rbro5952
 *
 */
public class FeatureStatistics {
	private double mean;
	private double stddv;
	private final double EXPONENT_DIVISOR;
	private final double DIVISOR;
	
	/**
	 * Creates an object to hold the statistics for a given Feature set.
	 * 
	 * @param mean		the mean of the feature set
	 * @param stddv		the standard deviation of the feature set
	 */
	public FeatureStatistics(double mean, double stddv) {
		this.mean = mean;
		this.stddv = stddv;
		this.EXPONENT_DIVISOR = 1.0 / (-2 * stddv * stddv);
		this.DIVISOR = 1.0 / (stddv * Math.sqrt(2 * Math.PI));
	}
	
	/**
	 * Returns the probability of obtaining the value for the given feature.
	 * 
	 * @param value		the value for which the probability is required
	 * @return 			the probability
	 */
	public double calculateProbability(double value) {
		return DIVISOR * Math.exp( (value - mean) * (value - mean) * EXPONENT_DIVISOR );
	}
	
	/**
	 * Returns the log of the probability of obtaining the value for the given feature.
	 * 
	 * @param value		the value for which the probability is required
	 * @return			the log of the probability
	 */
	public double calculateLogProbability(double value) {
		return Math.log(DIVISOR) + (value - mean) * (value - mean) * EXPONENT_DIVISOR;
	}
	
	public double getMean() {
		return this.mean;
	}
	
	public double getStdDv() {
		return this.stddv;
	}
	
}
