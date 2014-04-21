
public class Word {
	private String word;
	private int docFreq;

	public Word(String word) {
		this.word = word;
		this.docFreq = 0;
	}
	
	public String word() {
		return this.word;
	}
	
	public int getDocFreq() {
		return this.docFreq;
	}
	
	public void setDocFreq(int docFreq) {
		this.docFreq = docFreq;
	}
}
