package classification;

/**
 * 
 * @author cske2656
 * @author rbro5952
 *
 */
public class FeatureStatistics {
	private double mean;
	private final double EXPONENT_DIVISOR;
	private final double DIVISOR;
	
	/**
	 * 
	 * @param mean
	 * @param stddv
	 */
	public FeatureStatistics(double mean, double stddv) {
		this.mean = mean;
		this.EXPONENT_DIVISOR = 1.0 / (-2 * stddv * stddv);
		this.DIVISOR = 1.0 / (stddv * Math.sqrt(2 * Math.PI));
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public double calculateProbability(double value) {
		return DIVISOR * Math.exp( (value - mean) * (value - mean) * EXPONENT_DIVISOR );
	}
	
	public double calculateLogProbability(double value) {
		return Math.log(DIVISOR) - (value - mean) * (value - mean) * EXPONENT_DIVISOR;
	}
	
}
