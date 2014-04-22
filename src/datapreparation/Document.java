package datapreparation;
import java.util.HashMap;
import java.util.Set;

public class Document {
	public HashMap<String, Integer> subject;
	public HashMap<String, Integer> body;
	private boolean isSpam;

	public Document() {
		subject = new HashMap<String, Integer>();
		body = new HashMap<String, Integer>();
		isSpam = false;
	}
	
	public void addToSubject(String word) {
		if (subject.containsKey(word)) {
			subject.put(word, subject.get(word) + 1);
		} else {
			subject.put(word, 1);
		}
	}
	
	public void addToBody(String word) {
		if (body.containsKey(word)) {
			body.put(word, body.get(word) + 1);
		} else {
			body.put(word, 1);
		}
	}
	
	public boolean subjectHasWord(String word) {
		return subject.containsKey(word);
	}
	
	public int getSubjectWordCount(String word) {
		return (subject.get(word) != null ? subject.get(word) : 0);
	}
	
	public boolean bodyHasWord(String word) {
		return body.containsKey(word);
	}
	
	public int getBodyWordCount(String word) {
		return (body.get(word) != null ? body.get(word) : 0);
	}
	
	public Set<String> getBodyWords() {
		return body.keySet();
	}
	
	public Set<String> getSubjectWords() {
		return subject.keySet();
	}
	
	public void setSpam() {
		isSpam = true;
	}
	
	public boolean isSpam() {
		return isSpam;
	}
}
