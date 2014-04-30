package normalisation;

public interface Normalisable {

	/**
	 * Will normalise in-place the given input vectors. Assumed to be in [doc][word] form.
	 * 
	 * @param inputVector
	 */
	public void normalise(double [][] inputVectors);
	
}
