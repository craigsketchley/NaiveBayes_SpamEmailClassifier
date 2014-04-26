package classification;

/**
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 *
 */
public class ProbabilityDensityFunction {
	private final double mean;
	private final double stddv;
	private final double exponentDivisor;
	private final double divisor;

	/**
	 * 
	 * @param mean
	 * @param stddv
	 */
	public ProbabilityDensityFunction(double mean, double stddv) {
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
	 * 
	 * @return
	 */
	public double getMean() {
		return mean;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getStandardDeviation() {
		return stddv;
	}
}
