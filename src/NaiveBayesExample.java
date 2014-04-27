import java.io.IOException;

import classification.NaiveBayes;

/**
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 *
 */
public class NaiveBayesExample {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		NaiveBayes nb = new NaiveBayes();
		
		try {
			nb.loadCSVFile("data/subject.csv");
		} catch (IOException e) {
			System.out.println("Urm, somfin went rong.");
			e.printStackTrace();
		}
		
		nb.stratifiedCrossValidation(10);
	}

}
