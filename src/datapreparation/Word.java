package datapreparation;

public class Word implements Comparable<Word> {
	private String word;
	private int docFreq;

	public Word(String word, int docFreq) {
		this.word = word;
		this.docFreq = docFreq;
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

	@Override
	public int compareTo(Word o) {
		if (this.docFreq > o.docFreq) {
			return -1;
		} else if (this.docFreq < o.docFreq) {
			return 1;
		}
		return 0;
	}
}