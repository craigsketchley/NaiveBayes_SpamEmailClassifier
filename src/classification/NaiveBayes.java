package classification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 
 * @author cske2656
 * @author rbro5952
 *
 */
public class NaiveBayes {
	ArrayList<Example> examples;
	String [] featureHeaders;
	// Double hashmap, keyed on feature name, then class type, to store the statistics.
	HashMap<String, HashMap<String, FeatureStatistics>> featureStats;
	// Keyed on the feature name.
	HashMap<String, FeatureSet> featureSets;
	ArrayList<String> classTypes;
	
	HashMap<String, Integer> classCounts;
	int trainingSize;
	
	/**
	 * 
	 */
	public NaiveBayes() {
		classTypes = new ArrayList<String>();
		classCounts = new HashMap<String, Integer>();
		trainingSize = 0;
	}
	
	
	public void stratifiedCrossValidation(int kFold, File file) throws IOException {
		
		loadFile(file);
		
		HashMap<String, ArrayList<Example>> exampleClasses = new HashMap<String, ArrayList<Example>>();
		
		for (Example e : examples) {
			if (!exampleClasses.containsKey(e.classType)) {
				exampleClasses.put(e.classType, new ArrayList<Example>());
			}
			exampleClasses.get(e.classType).add(e);
		}
		
		int totalNumberOfExamples = examples.size();
		
		int minStrataSize = totalNumberOfExamples / kFold;
		
		int numberOfLargerStrata = totalNumberOfExamples % kFold;
		
		int numberOfSmallerStrata = kFold - numberOfLargerStrata;
		
		
		HashMap<String, Integer> classTypeProportion = new HashMap<String, Integer>();
		
		for (String classType : exampleClasses.keySet()) {
			classTypeProportion.put(classType, exampleClasses.get(classType).size() / kFold);
		}

		ArrayList<ArrayList<Example>> strata = new ArrayList<ArrayList<Example>>();
		
		for (int i = 0; i < kFold; i++) {
			strata.add(new ArrayList<Example>());
			for (String classType : classTypeProportion.keySet()) {
				for (int j = 0; j < classTypeProportion.get(classType); j++) { // TODO: maybe use a stack/queue.
					strata.get(i).add(exampleClasses.get(classType).remove(exampleClasses.get(classType).size() - 1));
				}
			}
		}
		
		// TODO: Temp code to divide up the remaining examples... can it be improved?
		int i = 0;
		
		for (String classType : exampleClasses.keySet()) {
			for (Example e : exampleClasses.get(classType)) {
				strata.get(i).add(e);
				i = ++i % kFold;
			}
		}
		
		
		
		double [] accuracy = new double[kFold];
		
		// For each strata, run the naive bayes on all other strata.
		for (i = 0; i < kFold; i++) {
			ArrayList<Example> testSet = strata.get(i);
			examples = new ArrayList<Example>();
			
			for (int j = 0; j < kFold; j++) {
				if (i == j) {
					continue;
				}
				examples.addAll(strata.get(j));	
			}
			
			// Run naive bayes.
			makeFeatureSets();
			
			train();
			
			accuracy[i] = getAccuracy(testSet);
		}
		
		double total = 0;
		
		System.out.println(kFold + "-fold Stratified Cross Validation:");
		for (i = 0; i < accuracy.length; i++) {
			System.out.println("Run " + (i+1) + ": " + accuracy[i]);
			total += accuracy[i];
		}
		
		System.out.println("Average for all Runs: " + (total / kFold));
	}
	
	
	public double getAccuracy(ArrayList<Example> testSet) {
		
		int correctClassifyCount = 0;
		
		for (Example e : testSet) {
			if (e.classType.equals(classify(e))) {
				correctClassifyCount++;
			}
		}
		
		return correctClassifyCount / (double) testSet.size();
	}
	

	public String classify(Example input) {
		HashMap<String, Double> probabilities = new HashMap<String, Double>();
		
		for (String classType : classTypes) {
			double logProb = Math.log(probabilityOfClass(classType));
			int i = 0;			
			for (String featureName : featureStats.keySet()) {
				FeatureStatistics fs = featureStats.get(featureName).get(classType);
				double x = input.getValues().get(i);
				double prob = fs.calculateLogProbability(x);
				
				logProb += prob;
				i++;
			}
			probabilities.put(classType, logProb);
		}
		
		double maxProb = 0;
		String classifiedType = "ERROR";
		
		for (String classType : probabilities.keySet()) {
			if (probabilities.get(classType) > maxProb) {
				maxProb = probabilities.get(classType);
				classifiedType = classType;
			}
		}
		
		return classifiedType;
	}
	
	
	public double probabilityOfClass(String classType) {
		return classCounts.get(classType) / (double) trainingSize;
	}
	
	
	/**
	 * Loads a file in CSV format into the featureSets property.
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void loadData(File file) throws IOException {
		// Check if data has been loaded before, wipe it if so.
		loadFile(file);
		
		makeFeatureSets();
		
		train();
	}
	
	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	private void loadFile(File file) throws IOException {
		examples = new ArrayList<Example>();
		
		Scanner content = new Scanner(new FileReader(file));
		
		// Use the header line to get the feature size and names.
		featureHeaders = content.nextLine().split(",");
		int featureSize = featureHeaders.length - 1;

		// All other lines
		while (content.hasNextLine()) {
			String [] line = content.nextLine().split(",");
			String classType = line[line.length - 1];
			
			if (!classTypes.contains(classType)) {
				classTypes.add(classType);
			}
			
			Example e = new Example(classType);
			for (int i = 0; i < featureSize; ++i) {
				e.add(Double.parseDouble(line[i]));
			}
			examples.add(e);
		}
		
		content.close();
	}
	
	/**
	 * 
	 */
	private void makeFeatureSets() {
		if (examples == null) {
			return;
		}
		
		featureSets = new HashMap<String, FeatureSet>();

		int featureSize = featureHeaders.length - 1;
		
		for (int i = 0; i < featureSize; i++) {
			featureSets.put(featureHeaders[i], new FeatureSet());
		}
		
		// All other lines
		for (Example e : examples) {
			for (int i = 0; i < featureSize; ++i) {
				featureSets.get(featureHeaders[i]).add(e.classType, e.getValues().get(i));
				
				// Compute the class count...
				if (!classCounts.containsKey(e.classType)) {
					classCounts.put(e.classType, 0);
				}
				classCounts.put(e.classType, classCounts.get(e.classType) + 1);
			}
		}
		
		trainingSize = featureSets.size();
	}
	
	
	/**
	 * Processes the featureSets property into statistics ready to be used.
	 */
	private void train() {
		if (featureSets == null) {
			return;
		}
		
		featureStats = new HashMap<String, HashMap<String, FeatureStatistics>>();
		
		// Iterate over the featureSets, getting the featureStats for each
		for (String featureName : featureSets.keySet()) {
			featureStats.put(featureName, featureSets.get(featureName).getFeatureStatistics());
		}
	}
	
}
