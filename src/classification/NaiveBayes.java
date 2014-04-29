package classification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 * 
 */
public class NaiveBayes {
	// Stores all the examples parsed when loading a csv file.
	private ArrayList<Example> examples;

	// Stores the Example's in groups according to class.
	private HashMap<String, ArrayList<Example>> classExamples;

	// Stores the names of all the features.
	private String[] featureHeaders;
	private int numOfFeatures;

	private String inputFilename;

	// Double hashmap, keyed on feature name, then class type, to store the
	// statistics.
	private FeatureStatistics[] featureStats;

	/**
	 * Creates an instance of Naive Bayes.
	 */
	public NaiveBayes() {
		reset();
	}

	/**
	 * Wipes this instance of naive bayes, ready to load new training data.
	 */
	public void reset() {
		examples = new ArrayList<Example>();
		classExamples = new HashMap<String, ArrayList<Example>>();
		numOfFeatures = 0;
		featureHeaders = null;
		featureStats = null;
		inputFilename = null;
	}

	/**
	 * Returns the names of all the class types in the training data.
	 * 
	 * @return
	 */
	private Iterable<String> classNames() {
		if (classExamples != null) {
			return classExamples.keySet();
		}
		return null;
	}

	/**
	 * 
	 * @param kFold
	 */
	public void stratifiedCrossValidation(int kFold) {
		HashMap<String, Integer> classTypeProportion = new HashMap<String, Integer>();
		HashMap<String, Integer> classExamplesIndex = new HashMap<String, Integer>();

		for (String className : classNames()) {
			classTypeProportion.put(className, classExamples.get(className)
					.size() / kFold);
			classExamplesIndex.put(className, 0);
		}

		ArrayList<ArrayList<Example>> strata = new ArrayList<ArrayList<Example>>();

		for (int i = 0; i < kFold; i++) {
			strata.add(new ArrayList<Example>());
			for (String className : classNames()) {
				for (int j = 0; j < classTypeProportion.get(className); j++) {
					strata.get(i).add(classExamples.get(className).get(classExamplesIndex.get(className)));
					classExamplesIndex.put(className, classExamplesIndex.get(className) + 1);
				}
			}
		}

		// TODO: Temp code to divide up the remaining examples... can it be
		// improved?
		// Currently sharing the remaining examples of each class amongst the folds.
		int strataIndex = 0;
		for (String className : classNames()) {
			for (int i = classExamplesIndex.get(className); i < classExamples.get(className).size(); i++) {
				strata.get(strataIndex).add(classExamples.get(className).get(i));
				strataIndex = ++strataIndex % kFold;
			}
		}

		//File name output and map to store each fold
		String outputFilename = inputFilename.substring(0, inputFilename.length() - 4) + "-folds.csv";

		double[] accuracy = new double[kFold];

		HashMap<String, ArrayList<Example>> classExamplesBackup = classExamples;
		String foldHeader;

		// For each strata, run the naive bayes on all other strata.
		for (int i = 0; i < kFold; i++) {
			ArrayList<Example> testSet = strata.get(i);

			// Create a new classExamples from the other strata.
			classExamples = new HashMap<String, ArrayList<Example>>();

			// Every other strata...
			for (int j = 0; j < kFold; j++) {
				if (i == j) {
					continue;
				}
				for (Example e : strata.get(j)) {
					if (!classExamples.containsKey(e.getClassName())) {
						classExamples.put(e.getClassName(), new ArrayList<Example>());
					}
					classExamples.get(e.getClassName()).add(e);
				}
			}

			// Run naive bayes.
			train();

			accuracy[i] = getClassificationAccuracy(testSet);
		}
		//Output the folds to a CSV file
		outputToCSV(strata, kFold, outputFilename);
		double total = 0;

		System.out.println(kFold + "-fold Stratified Cross Validation:");
		for (int i = 0; i < accuracy.length; i++) {
			System.out.println("Run " + (i + 1) + ": " + accuracy[i]);
			total += accuracy[i];
		}

		System.out.println("Average for all Runs: " + (total / kFold));

		// Set classExamples back to normal.
		classExamples = classExamplesBackup;
	}

	/**
	 * 
	 * @param testSet
	 * @return
	 */
	private double getClassificationAccuracy(ArrayList<Example> testSet) {
		int correctClassifyCount = 0;

		for (Example e : testSet) {
			String classification = classify(e);
			if (e.getClassName().equals(classification)) {
				correctClassifyCount++;
			}
		}

		return correctClassifyCount / (double) testSet.size();
	}

	/**
	 * Returns the best classification of the example input.
	 * 
	 * @param input
	 * @return
	 */
	private String classify(Example input) {
		// TODO: How to break ties.

		double maxProb = Double.NEGATIVE_INFINITY;
		String classifiedType = "UNCLASSIFIED";

		double logProb;
		double totalLogProb;

		for (String className : classNames()) {
			totalLogProb = 0;
			for (int i = 0; i < numOfFeatures; i++) {
				logProb = featureStats[i].calculateLogProbabilityForClass(className, input.getValue(i));
				if ((new Double(logProb)).equals(Double.NaN)) {
					// TODO: Skipping values with zero variance. Could do this some other way.
					continue;
				}
				totalLogProb += logProb;
			}
			totalLogProb += probabilityOfClass(className);
			if (totalLogProb > maxProb) {
				maxProb = totalLogProb;
				classifiedType = className;
			}
		}

		return classifiedType;
	}

	/**
	 * Calculates and returns the probability of an example being a given class type
	 * based on the proportions of the training data.
	 * 
	 * @param className	the name of the class to calculate the probability
	 * @return	the probability
	 */
	private double probabilityOfClass(String className) {
		return classExamples.get(className).size() / (double) examples.size();
	}

	/**
	 * 
	 * Loads the input csv file into the ArrayList examples and classIndices.
	 * Assumes the file is a csv in the following format:
	 * 
	 * f1,f2,f3,...,f199,f200,class
	 * 
	 * @param file
	 *            In CSV format.
	 * @throws IOException
	 */
	public void loadCSVFile(String filename) throws IOException {
		reset(); // Ensure a clean Naive Bayes.

		inputFilename = filename;

		Scanner content = new Scanner(new FileReader(new File(filename)));
		String[] line;
		String classType;

		// Use the header line to get the feature size and names.
		featureHeaders = content.nextLine().split(",");

		numOfFeatures = featureHeaders.length - 1;

		// All other lines
		while (content.hasNextLine()) {
			line = content.nextLine().split(",");
			classType = line[line.length - 1];

			// Create a new example and add it to the examples.
			Example e = new Example(classType, numOfFeatures);
			for (int i = 0; i < numOfFeatures; ++i) {
				e.add(i, Double.parseDouble(line[i]));
			}
			examples.add(e);

			// Add this example to the array of its class type.
			if (!classExamples.containsKey(classType)) {
				classExamples.put(classType, new ArrayList<Example>());
			}
			classExamples.get(classType).add(e);
		}

		content.close();
	}

	/**
	 * Calculates the statistics required for classification using the examples
	 * ArrayList and classIndices. Once completed, this Naive Bayes will be
	 * ready to begin classification of new data. Note: should only be called
	 * after data has been loaded.
	 */
	public void train() {
		featureStats = FeatureStatistics.generateFeatureStatisticsArray(numOfFeatures, classExamples);
	}

	/**
	 * 
	 * Output each fold breakdown to a single CSV file
	 * 
	 */
	private void outputToCSV(ArrayList<ArrayList<Example>> strata, int kFold, String fileName){

		File output = new File(fileName);
		BufferedWriter writer;
		double value = 0;

		try {
			writer = new BufferedWriter(new FileWriter(output));

			for (int i = 0; i < kFold; i++) {
				writer.write("fold" + (i+1));
				writer.newLine();
				for (Example e : strata.get(i)) {
					for(int j = 0; j < e.getNumberOfValues(); j++){
						value = e.getValue(j);
						writer.write(String.valueOf(value) + ",");
					}
					writer.write(e.getClassName());
					writer.newLine();
				}
				writer.newLine();
			}
			writer.close();
		} catch (IOException o) {
			System.out.println("Error writing to " + fileName + " " + o.toString());
			return;
		}
	}
}
