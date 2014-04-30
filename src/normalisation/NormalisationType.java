package normalisation;

public enum NormalisationType {
	COSINE_NORM,
	DISTRIBUTION_NORM;
	
	public Normalisable getInstance() {
		switch (this) {
		case COSINE_NORM:
			return new CosineNormalisation();
		case DISTRIBUTION_NORM:
			return new DistributionNormalisation();
		}
		return null;
	}
	
}
