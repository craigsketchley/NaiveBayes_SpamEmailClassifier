package datapreparation;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TextClassifier {
	public static final int FEATURE_SIZE = 200;
	
	
	public static void main(String[] args) {
		File email = new File("lingspam-mini600");
		File stopWords = new File("english.stop");

		Preprocessor pro = null;
		
		try {
			pro = new Preprocessor(stopWords);
		} catch (IOException e) {
			System.out.println("Error reading the stopWords file");
			return;
		}
		try {
			for (File f : email.listFiles()) {
				if (f.isFile()) {
					pro.addDocument(f);
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading the email files");
			e.printStackTrace();
			return;
		}
		
		ArrayList<Document> docs = pro.getDocuments();
		Word[] bodyWords = pro.getTopBodyWords(FEATURE_SIZE);
		Word[] subjectWords = pro.getTopSubjectWords(FEATURE_SIZE);
		
		try {
			TfIdf.outputBodyCsv(bodyWords, docs);
			TfIdf.outputSubjectCsv(subjectWords, docs);
		} catch (IOException e) {
			System.out.println("Error writing to csv files.");
			e.printStackTrace();
		}
		
		
	}

}
