import java.io.File;
import java.io.IOException;


public class TextClassifier {

	public static void main(String[] args) {
		File email = new File("lingspam-mini600/5-1430msg1.txt");
		File stopWords = new File("english.stop");

		Preprocessor pro = null;
		
		try {
			pro = new Preprocessor(stopWords);
		} catch (IOException e) {
			System.out.println("Error reading the stopWords file");
			return;
		}
		
		Document doc = null;
		
		try {
			doc = pro.parseDocument(email);
		} catch (IOException e) {
			System.out.println("Error reading the email file");
			return;
		}
		
		
		System.out.println("Subject:");
		for (int i = 0; i < doc.subject.size(); i++) {
			System.out.println(doc.subject.get(i));
		}
		
		System.out.println("Body:");
		for (int i = 0; i < doc.body.size(); i++) {
			System.out.println(doc.body.get(i));
		}
		
		
	}

}
