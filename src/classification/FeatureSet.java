package classification;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author cske2656
 * @author rbro5952
 *
 */
public class FeatureSet {
	HashMap<String, ArrayList<Double>> classValues;
	
	/**
	 * 
	 */
	public FeatureSet() {
		classValues = new HashMap<String, ArrayList<Double>>();
	}
	
	/**
	 * 
	 * @return
	 */
	public HashMap<String, FeatureStatistics> getFeatureStatistics() {
		HashMap<String, FeatureStatistics> stats = new HashMap<String, FeatureStatistics>();
		
		for (String classType : classValues.keySet()) {
			stats.put(classType, calculateFeatureStats(classType));
		}
		
		return stats;
	}
	
	/**
	 * 
	 * @param classType
	 * @return
	 */
	private FeatureStatistics calculateFeatureStats(String classType) {
		ArrayList<Double> values = classValues.get(classType);
		
		double mean = 0;
		double stddv = 0;
		
		for (Double d : values) {
			mean += d;
			stddv += d * d;
		}
		
		mean /= values.size();
		stddv /= values.size();
		stddv -= mean * mean;
		
		return new FeatureStatistics(mean, Math.sqrt(stddv));
	}
	
	/**
	 * 
	 * @param classType
	 * @param value
	 */
	public void add(String classType, double value) {
		if (!classValues.containsKey(classType)) {
			classValues.put(classType, new ArrayList<Double>());
		}
		classValues.get(classType).add(value);
	}
}
