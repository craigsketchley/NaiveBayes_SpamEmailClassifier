package classification;

/**
 * A feature vector for a given document containing a classification.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 *
 */
class Example {
	private double[] values;
	private String className = "UNCLASSIFIED"; // default value.
	
	/**
	 * Creates an Example instance with a classification type and number of values to be stored.
	 * @param classType
	 * @param numOfValues
	 */
	public Example(String classType, int numOfValues) {
		this.className = classType;
		values = new double[numOfValues];
	}
	
	/**
	 * Creates an Example instance with no classification type.
	 * 
	 * @param className
	 * @param numOfValues
	 */
	public Example(int numOfValues) {
		values = new double[numOfValues];
	}
	
	/**
	 * Adds a value at the given index.
	 * 
	 * @param index
	 * @param value
	 */
	public void add(int index, double value) {
		values[index] = value;
	}
	
	/**
	 * Returns the value at a given index.
	 * 
	 * @param index
	 * @return
	 */
	public Double getValue(int index) {
		return values[index];
	}
	
	/**
	 * Returns the size of the feature vector.
	 * 
	 * @return
	 */
	public int getNumberOfValues() {
		return values.length;
	}
	
	/**
	 * Returns the classification of this feature vector.
	 * 
	 * @return
	 */
	public String getClassName() {
		return className;
	}
	
	@Override
	public String toString() {
		String output = "{" + className + ", [";
		for (int i = 0; i < values.length; i++) {
			output += values[i];
			if (i != values.length - 1) {
				output += ", ";
			}
		}
		return output + "]}";
	}
}
