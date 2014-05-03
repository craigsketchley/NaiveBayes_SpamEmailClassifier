
# COMP3608 - Assignment 1
_A Spam Email Classifier_

________

## How to use.

This project is in 2 main parts, the email processor and the Naive Bayes classifier.

Examples of how to use both of these are in [EmailProcessorExample.java] and [NaiveBayesExample.java].

### Email Processing

To process a folder of emails, you need to create `EmailProcessor` instance and pass in various attributes to the constructor. These are:

* `emailFolder` - The folder where all the email files are stored.
* `stopwordsFilename1 - The file containing any stopwords to be ignored when processing the emails. This can be `null`. 
* `featureSelectionType` - A `FeatureSelectionType` enum, indicating what type of feature selection method you would like to use. These could be one of the following:
    - `DOCUMENT_FREQUENCY`
    - `MUTUAL_INFORMATION`
    - `ODDS_RATIO`
    - `CHI_SQRD`
    - `SIMPLIFIED_CHI_SQRD`
    - `CPD`
* `normalisationType` - A `NormalisationType` enum, indicating what type of normalisation method you would like to apply to the selected feature vectors. These could be one of the following:
    - `COSINE_NORM`
    - `DISTRIBUTION_NORM`
    - `NONE`

Once you have successfully constructed a EmailProcessor object, simply call `outputFeatureSelectionCSV(int K)` to output the top `K` features to a `body.csv` and `subject.csv`.

### Naive Bayes Classifier

To begin classification, create a `NaiveBayes` instance. Then call `loadCSVFile(String filename)` on that instance to load data from a CSV file. Finally, to complete k-Fold stratified cross validation call `stratifiedCrossValidation(int K)`. This will print the accuracy results to the console and output the stratification to 2 CSV files.