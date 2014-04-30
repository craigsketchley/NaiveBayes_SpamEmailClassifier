package dataPreparation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

import normalisation.Normalisable;
import normalisation.NormalisationType;
import featureSelection.FeatureScoreable;
import featureSelection.FeatureSelectionType;

public class EmailProcessor {
	// Using a HashMap for stopWords, to quickly check if an input email word ==
	// stop word.
	private HashMap<String, Integer> stopWords;
	
	private ArrayList<Document> bodyDocList;
	private HashMap<String, Word> bodyBagOfWords;
	
	private ArrayList<Document> subjectDocList;
	private HashMap<String, Word> subjectBagOfWords;

	private FeatureScoreable featureScore;
	private Normalisable normalisation;

	private int hamCount;
	private int spamCount;
	
	// Class defaults
	private static final FeatureSelectionType DEFAULT_FEATURE_SELECTION = FeatureSelectionType.DOCUMENT_FREQUENCY;
	private static final NormalisationType DEFAULT_NORMALISATION = NormalisationType.COSINE_NORM;

	private static final String BODY_FILENAME = "data/body.csv";
	private static final String SUBJECT_FILENAME = "data/subject.csv";
	private static final String SPAM_PREFIX = "spmsg";
	
	/**
	 * 
	 * @param emailFolder
	 * @param stopwordsFilename
	 * @param featureScore
	 * @param normalisation
	 */
	public EmailProcessor(String emailFoldername, String stopwordsFilename,
			FeatureSelectionType featureSelectionType,
			NormalisationType normalisationType) {
		this.hamCount = 0;
		this.spamCount = 0;
		this.subjectDocList = new ArrayList<Document>();
		this.bodyDocList = new ArrayList<Document>();
		this.bodyBagOfWords = new HashMap<String, Word>();
		this.subjectBagOfWords = new HashMap<String, Word>();
		this.stopWords = new HashMap<String, Integer>();

		// Import the stopWords
		if (stopwordsFilename != null) {
			processStopWords(new File(stopwordsFilename));
		}

		// Import the email's
		processEmailFolder(new File(emailFoldername));

		// Setup the Feature Selection and weighting method...
		this.featureScore = featureSelectionType.getInstance();

		// Setup the Normalisation method...
		this.normalisation = normalisationType.getInstance();

	}

	/** ALTERNATE CONSTRUCTORS **/

	/**
	 * 
	 * @param emailFoldername
	 * @param stopwordsFilename
	 * @param featureSelection
	 */
	public EmailProcessor(String emailFoldername, String stopwordsFilename,
			FeatureSelectionType featureSelection) {
		this(emailFoldername, stopwordsFilename, featureSelection,
				DEFAULT_NORMALISATION);
	}

	/**
	 * 
	 * @param emailFoldername
	 * @param stopwordsFilename
	 * @param normalisation
	 */
	public EmailProcessor(String emailFoldername, String stopwordsFilename,
			NormalisationType normalisation) {
		this(emailFoldername, stopwordsFilename, DEFAULT_FEATURE_SELECTION,
				normalisation);
	}

	/**
	 * 
	 * @param emailFoldername
	 * @param stopwordsFilename
	 */
	public EmailProcessor(String emailFoldername, String stopwordsFilename) {
		this(emailFoldername, stopwordsFilename, DEFAULT_FEATURE_SELECTION,
				DEFAULT_NORMALISATION);
	}

	/** PUBLIC METHODS **/

	public void outputFeatureSelectionCSV(int K) {
		if (bodyDocList == null || bodyDocList.size() == 0
				|| subjectDocList == null || subjectDocList.size() == 0) {
			// No documents loaded to process.
			return;
		}

		// Selection, score each word using the given featureSelction
		for (Word word : bodyBagOfWords.values()) {
			featureScore.setFeatureScore(word, spamCount, hamCount);
		}
		for (Word word : subjectBagOfWords.values()) {
			featureScore.setFeatureScore(word, spamCount, hamCount);
		}

		// Order the words, picking the top K words.
		Word[] topBodyWords = getTopWords(K, bodyBagOfWords);
		Word[] topSubjectWords = getTopWords(K, subjectBagOfWords);

		// TODO: Print top 200 body words for debugging
		for (Word w : topBodyWords) {
			System.out.println(w);
		}
		
		// Apply TF*IDF to each word.
		double[][] bodyWeightMatrix = calculateTfIdf(topBodyWords, bodyDocList);
		double[][] subjectWeightMatrix = calculateTfIdf(topSubjectWords, subjectDocList);

		// Normalise in place
		normalisation.normalise(bodyWeightMatrix);
		normalisation.normalise(subjectWeightMatrix);
		
		// Output to CSV.
		try {
			outputToCSV(BODY_FILENAME, bodyWeightMatrix, bodyDocList);
			outputToCSV(SUBJECT_FILENAME, subjectWeightMatrix, subjectDocList);
		} catch (IOException e) {
			System.out.println("Had a bit of trouble outputing to the csv files. Here's the stack trace...");
			e.printStackTrace();
		}
	}

	/** PRIVATE METHODS **/
	
	/**
	 * 
	 * @param bodyFilename
	 * @param bodyWeightMatrix
	 * @throws IOException 
	 */
	private void outputToCSV(String filename, double[][] weightMatrix, ArrayList<Document> docList) throws IOException {
		if (weightMatrix.length == 0) {
			return;
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
		
		for (int i = 0; i < weightMatrix[0].length; i++) {
			writer.write("f" + (i+1) + ",");
		}
		writer.write("class");
		writer.newLine();
		
		for (int docIndex = 0; docIndex < weightMatrix.length; docIndex++) {
			for (int wordIndex = 0; wordIndex < weightMatrix[docIndex].length; wordIndex++) {
				writer.write(String.valueOf(weightMatrix[docIndex][wordIndex]) + ",");
			}
			writer.write(docList.get(docIndex).isSpam() ? "class1" : "class0");
			writer.newLine();
		}
		
		writer.close();
	}

	/**
	 * Calculates the TF*IDF matrix for the top words and training email's.
	 * 
	 * @param topWords
	 * @return
	 */
	private double[][] calculateTfIdf(Word[] topWords, ArrayList<Document> docList) {
		double[][] output = new double[docList.size()][topWords.length];

		for (int docIndex = 0; docIndex < output.length; docIndex++) {
			for (int wordIndex = 0; wordIndex < output[docIndex].length; wordIndex++) {
				output[docIndex][wordIndex] = TfIdfFormula(
						docList.get(docIndex).getWordCount(topWords[wordIndex].word()),
						docList.size(),
						topWords[wordIndex].getDocumentFrequency());
			}
		}

		return output;
	}
	
	
	/**
	 * TF*IDF Formula.
	 * 
	 * @param wordCountInDoc
	 * @param docCount
	 * @param docFreq
	 * @return
	 */
	private static double TfIdfFormula(int wordCountInDoc, int docCount, int docFreq) {
		return wordCountInDoc * (Math.log(docCount / (double) docFreq));
	}

	/**
	 * Reads in the contents of the given file, parsing the words into the
	 * stopWords HashMap.
	 * 
	 * @param stopWordsFile
	 */
	private void processStopWords(File stopWordsFile) {
		Scanner scnr = null;
		try {
			scnr = new Scanner(stopWordsFile);
		} catch (IOException e) {
			System.out.println("Unable to find Stop words file. Skipping stopwords");
			return;
		}

		while (scnr.hasNext()) {
			stopWords.put(scnr.next(), 0);
		}

		scnr.close();
	}

	/**
	 * Iterates through all the files in the given folder, parsing their
	 * contents into Document objects to the docList ArrayList.
	 * 
	 * @param emailFolder
	 */
	private void processEmailFolder(File emailFolder) {
		try {
			for (File f : emailFolder.listFiles()) {
				if (f.isFile()) {
					addDocument(f);
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading the email files");
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Returns the top K items from the bodyBagOfWords.
	 * 
	 * 
	 * @param size
	 * @return
	 */
	private Word[] getTopWords(int size, HashMap<String, Word> bagOfWords) {
		PriorityQueue<Word> bodyWords = new PriorityQueue<Word>(bagOfWords.size(), Collections.reverseOrder());

		// Words are Comparable so will be ordered.
		bodyWords.addAll(bagOfWords.values());

		Word[] output = new Word[size];

		for (int i = 0; i < size; i++) {
			output[i] = bodyWords.poll();
		}

		return output;
	}

	/**
	 * Processes a single email file. Iterates through the file adding each
	 * subject and body word string to the document. Also, creates Word objects
	 * and keeps spam and ham counts in the Word object. Finally adds the new
	 * Document to the docList.
	 * 
	 * @param file
	 * @throws IOException
	 */
	private void addDocument(File file) throws IOException {
		Document subjectDoc = new Document();
		Document bodyDoc = new Document();

		if (isSpam(file.getName())) {
			subjectDoc.setSpam();
			bodyDoc.setSpam();
			spamCount++;
		} else {
			hamCount++;
		}

		Scanner scnr = new Scanner(file);

		// Get subject content
		String[] subject = scnr.nextLine().toLowerCase().split("\\s");

		for (int i = 1; i < subject.length; ++i) { // skip "Subject:" at i = 0
			if (isValidWord(subject[i])) {
				if (!subjectDoc.hasWord(subject[i])) {
					Word word = subjectBagOfWords.get(subject[i]);
					if (word == null) {
						word = new Word(subject[i]);
						subjectBagOfWords.put(subject[i], word);
					}
					if (subjectDoc.isSpam()) {
						word.incrementSpamCount();
					} else {
						word.incrementHamCount();
					}
				}
				subjectDoc.addToWords(subject[i]);
			}
		}

		// Get body content
		while (scnr.hasNextLine()) {
			String[] bodyLine = scnr.nextLine().toLowerCase().split("\\s");

			for (int i = 0; i < bodyLine.length; ++i) {
				if (isValidWord(bodyLine[i])) {
					if (!bodyDoc.hasWord(bodyLine[i])) {
						Word word = bodyBagOfWords.get(bodyLine[i]);
						if (word == null) {
							word = new Word(bodyLine[i]);
							bodyBagOfWords.put(bodyLine[i], word);
						}
						if (bodyDoc.isSpam()) {
							word.incrementSpamCount();
						} else {
							word.incrementHamCount();
						}
					}
					bodyDoc.addToWords(bodyLine[i]);
				}
			}
		}

		scnr.close();
		
		subjectDocList.add(subjectDoc);
		bodyDocList.add(bodyDoc);
	}

	/**
	 * 
	 * @param word
	 * @return
	 */
	private boolean isValidWord(String word) {
		return word.length() != 0 && !isPunctuation(word) && !isNumber(word) && !isStopWord(word);
	}

	/**
	 * Checks if the given string is a single punctuation character.
	 * 
	 * @param input
	 * @return
	 */
	private boolean isPunctuation(String input) {
		return input.length() == 1 && input.matches("[^a-zA-Z]");
	}

	/**
	 * Checks if the given string contains any punctuation characters.
	 * 
	 * TODO: Decide if this is required.
	 * 
	 * @param input
	 * @return
	 */
	private boolean isAnyPunctuation(String input) {
		for (char c : input.toCharArray()) {
			if (!Character.isAlphabetic(c)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return true if all of the characters in the provided string are digits.
	 * 
	 * @param input
	 *            string to be checked if it is all digits
	 * @return boolean
	 */
	private boolean isNumber(String input) {
		for (char c : input.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return true if any of the characters in the provided string are digits.
	 * 
	 * TODO: Decide if this is required.
	 * 
	 * @param input
	 *            string to be checked if it is all digits
	 * @return boolean
	 */
	private boolean isAnyNumber(String input) {
		for (char c : input.toCharArray()) {
			if (Character.isDigit(c)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if the provided string input is listed in the stop words.
	 * 
	 * @param input
	 * @return
	 */
	private boolean isStopWord(String input) {
		return stopWords.containsKey(input);
	}

	/**
	 * Checks if the filename indicates if the email is classed as Spam.
	 * 
	 * @param filename
	 * @return
	 */
	private boolean isSpam(String filename) {
		return filename.substring(0, SPAM_PREFIX.length()).equals(SPAM_PREFIX);
	}
	
}
