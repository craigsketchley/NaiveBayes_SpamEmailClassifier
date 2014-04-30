package normalisation;

public class CosineNormalisation implements Normalisable {

	@Override
	public void normalise(double[][] inputVectors) {
		double mag;
		for (int vecIndex = 0; vecIndex < inputVectors.length; vecIndex++) {
			mag = 0;
			for (int i = 0; i < inputVectors[vecIndex].length; i++) {
				mag += inputVectors[vecIndex][i] * inputVectors[vecIndex][i];
			}
			mag = Math.sqrt(mag);
			
			for (int i = 0; i < inputVectors[vecIndex].length; i++) {
				double normVal = (mag != 0) ? inputVectors[vecIndex][i] / mag : 0;
				inputVectors[vecIndex][i] = normVal;
			}			
		}
	}

}
