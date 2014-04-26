package classification;

/**
 * TODO:
 * @author Craig Sketchley
 * @author Rohan Brooker
 *
 */
public class Example {
	private double[] values;
	private String className = "UNCLASSIFIED"; // default value.
	
	/**
	 * TODO:
	 * @param classType
	 * @param numOfValues
	 */
	public Example(String classType, int numOfValues) {
		this.className = classType;
		values = new double[numOfValues];
	}
	
	/**
	 * TODO:
	 * @param className
	 * @param numOfValues
	 */
	public Example(int numOfValues) {
		values = new double[numOfValues];
	}
	
	/**
	 * TODO:
	 * @param index
	 * @param value
	 */
	public void add(int index, double value) {
		values[index] = value;
	}
	
	/**
	 * TODO:
	 * @param index
	 * @return
	 */
	public Double getValue(int index) {
		return values[index];
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public int getNumberOfValues() {
		return values.length;
	}
	
	public String getClassName() {
		return className;
	}
	
	
	/**
	 * Returns a String representation of this Object.
	 * 
	 * @return	the Object as a String
	 */
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
