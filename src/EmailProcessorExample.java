import dataPreparation.EmailProcessor;
import normalisation.NormalisationType;
import featureSelection.FeatureSelectionType;

/**
 * Demonstrates the use of the EmailProcessor class.
 * 
 * @author Craig Sketchley
 * @author Rohan Brooker
 *
 */
public class EmailProcessorExample {
	public static final int FEATURE_SIZE = 200;
	
	public static void main(String[] args) {
		String emailFolder = "lingspam-mini600";
		String stopWordsFilename = "data/english.stop";
				
		EmailProcessor ep = new EmailProcessor(emailFolder,
				 stopWordsFilename,
				 FeatureSelectionType.DOCUMENT_FREQUENCY,
				 NormalisationType.COSINE_NORM);
		
		ep.outputFeatureSelectionCSV(FEATURE_SIZE);
		
	}

}
