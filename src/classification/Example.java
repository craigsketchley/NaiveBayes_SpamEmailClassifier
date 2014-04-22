package classification;

import java.util.ArrayList;

public class Example {
	ArrayList<Double> values;
	String classType;
	
	public Example(String classType) {
		this.classType = classType;
		values = new ArrayList<Double>();
	}
	
	public void add(double value) {
		values.add(value);
	}
	
	public ArrayList<Double> getValues() {
		return values;
	}
	
	public String toString() {
		return "{" + classType + ", " + values.toString() + "}";
	}
}
