import java.io.File;
import java.io.IOException;

import classification.NaiveBayes;


public class NaiveBayesExample {

	public static void main(String[] args) {
		NaiveBayes nb = new NaiveBayes();

		try {
			nb.stratifiedCrossValidation(10, new File("iris.csv"));
		} catch (IOException e) {
			System.out.println("Urm, somfin went rong.");
			e.printStackTrace();
		}
		
	}

}
