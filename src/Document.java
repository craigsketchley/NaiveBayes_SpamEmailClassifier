import java.util.ArrayList;
import java.util.Collections;


public class Document {
	public ArrayList<String> subject;
	public ArrayList<String> body;

	public Document() {
		subject = new ArrayList<String>();
		body = new ArrayList<String>();
	}
	
	public void addToSubject(String word) {
		subject.add(word);
	}
	
	public void addToBody(String word) {
		body.add(word);
	}
	
	public void sortDocument() {
		Collections.sort(body);
		Collections.sort(subject);
	}
	
	public boolean bodyHasWord(String word) {
		return body.contains(word);
	}
	
	public boolean subjectHasWord(String word) {
		return subject.contains(word);
	}
	
}
