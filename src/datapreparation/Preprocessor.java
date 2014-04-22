package datapreparation;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Preprocessor {
	private HashMap<String, Integer> stopWords;
	private ArrayList<Document> docList;
	private HashMap<String, Integer> bodyBagOWords;
	private HashMap<String, Integer> subjectBagOWords;	
	
	public Preprocessor(File stopWordsFile) throws IOException {
		Scanner scnr = new Scanner(stopWordsFile);
		
		stopWords = new HashMap<String, Integer>();
		
		while (scnr.hasNext()) {
			stopWords.put(scnr.next(), 0);
		}
		
		docList = new ArrayList<Document>();
		bodyBagOWords = new HashMap<String, Integer>();
		subjectBagOWords = new HashMap<String, Integer>();
		
		scnr.close();
	}
	
	
	private boolean isPunctuation(String input) {
		return input.length() == 1 && input.matches("[^a-zA-Z]");
	}
	
	private boolean isNumber(String input) {
		char [] chars = input.toCharArray();
		
		for (char c : chars) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isStopWord(String input) {
		return stopWords.containsKey(input);
	}
	
	
	public void addDocument(File file) throws IOException {
		Document doc = new Document();
		
		Scanner scnr = new Scanner(file);
				
		// Get subject content
		String[] subject = scnr.nextLine().toLowerCase().split("\\s");
				
		for (int i = 1; i < subject.length; ++i) {
			if (!isPunctuation(subject[i]) 
					&& !isNumber(subject[i])
					&& !isStopWord(subject[i])) {
				if (!doc.subjectHasWord(subject[i])) {
					if (subjectBagOWords.containsKey(subject[i])) {
						subjectBagOWords.put(subject[i], subjectBagOWords.get(subject[i]) + 1);
					} else {
						subjectBagOWords.put(subject[i], 1);
					}
				}
				doc.addToSubject(subject[i]);
			}
		}
		
		
		// Get body content
		while (scnr.hasNextLine()) {
			String[] bodyLine = scnr.nextLine().toLowerCase().split("\\s");
			
			for (int i = 0; i < bodyLine.length; ++i) {
				if (!isPunctuation(bodyLine[i]) 
						&& !isNumber(bodyLine[i])
						&& !isStopWord(bodyLine[i])) {
					if (!doc.bodyHasWord(bodyLine[i])) {
						if (bodyBagOWords.containsKey(bodyLine[i])) {
							bodyBagOWords.put(bodyLine[i], bodyBagOWords.get(bodyLine[i]) + 1);
						} else {
							bodyBagOWords.put(bodyLine[i], 1);
						}
					}
					doc.addToBody(bodyLine[i]);
				}
			}
		}
		
		String spam = "spmsg";
		
		if (file.getName().substring(0, spam.length()).equals(spam)) {
			doc.setSpam();
		}
		
		scnr.close();
		docList.add(doc);
	}
	
	public ArrayList<Document> getDocuments() {
		return this.docList;
	}
	
	public Word[] getTopBodyWords(int size) {
		PriorityQueue<Word> bodyWords = new PriorityQueue<Word>();
		
		for (String s : bodyBagOWords.keySet()) {
			bodyWords.add(new Word(s, bodyBagOWords.get(s)));
		}
		
		Word[] output = new Word[size];
		
		for (int i = 0; i < size; i++) {
			output[i] = bodyWords.poll();
		}
		
		return output;
	}

	public Word[] getTopSubjectWords(int size) {
		PriorityQueue<Word> subjectWords = new PriorityQueue<Word>();
		
		for (String s : subjectBagOWords.keySet()) {
			subjectWords.add(new Word(s, subjectBagOWords.get(s)));
		}
		
		
		Word[] output = new Word[size];
		
		for (int i = 0; i < size; i++) {
			output[i] = subjectWords.poll();
		}
		
		return output;
	}
}
