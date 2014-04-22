package datapreparation;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public abstract class TfIdf {

	private static double formula(int wordCountInDoc, int docCount, int docFreq) {
		return wordCountInDoc * (Math.log(docCount / (double) docFreq));
	}
	
	
	public static void outputSubjectCsv(Word[] words, ArrayList<Document> docs) throws IOException {
		
		File output = new File("subject.csv");
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));
		
		for (int i = 0; i < words.length; i++) {
			writer.write(words[i].word() + ",");
		}
		writer.write("class");
		writer.newLine();
		
		double [] calcValues = new double[words.length];

		for (Document d : docs) {
			double mag = 0;
			for (int i = 0; i < words.length; i++) {
				calcValues[i] = formula(d.getSubjectWordCount(words[i].word()), docs.size(), words[i].getDocFreq());
				mag += calcValues[i] * calcValues[i];
			}
			
			mag = Math.sqrt(mag);
			
			for (int i = 0; i < words.length; i++) {
				Double normVal = (mag != 0) ? calcValues[i] / mag : 0;
				writer.write(normVal.toString());
				writer.write(',');
			}
			
			writer.write(d.isSpam() ? "1" : "0");
			writer.newLine();
		}		
		
		writer.close();
	}

	
	public static void outputBodyCsv(Word[] words, ArrayList<Document> docs) throws IOException {
		
		File output = new File("body.csv");
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));
		
		for (int i = 0; i < words.length; i++) {
			writer.write(words[i].word() + ",");
		}
		writer.write("class");
		writer.newLine();
		
		double [] calcValues = new double[words.length];
		
		for (Document d : docs) {
			double mag = 0;
			for (int i = 0; i < words.length; i++) {
				calcValues[i] = formula(d.getBodyWordCount(words[i].word()), docs.size(), words[i].getDocFreq());
				mag += calcValues[i] * calcValues[i];
			}
			
			mag = Math.sqrt(mag);
			
			for (int i = 0; i < words.length; i++) {
				Double normVal = (mag != 0) ? calcValues[i] / mag : 0;
				writer.write(normVal.toString());
				writer.write(',');
			}
			
			writer.write(d.isSpam() ? "1" : "0");
			writer.newLine();
		}		
		
		writer.close();
	}
	
}