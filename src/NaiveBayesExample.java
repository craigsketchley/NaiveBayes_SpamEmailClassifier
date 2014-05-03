import java.io.IOException;

import classification.NaiveBayes;

/**
 * Demonstrates the use of the NaiveBayes class.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 *
 */
public class NaiveBayesExample {

	public static void main(String[] args) {
		NaiveBayes nb = new NaiveBayes();
		
		try {
			nb.loadCSVFile("data/body.csv");
		} catch (IOException e) {
			System.out.println("Urm, somfin went rong.");
			e.printStackTrace();
		}
		
		nb.stratifiedCrossValidation(10);
	}

}
