package classificationTests;

import static org.junit.Assert.*;

import org.junit.Test;

import classification.FeatureStatistics;

public class FeatureStatisticsTest {
	double DELTA10 = 0.00000000001;
	
	
	@Test
	public void standardCalculation() {
		FeatureStatistics f = new FeatureStatistics(10, 2);
		
		assertEquals(f.calculateProbability(5), 0.008764150246784274, DELTA10);
	}

	@Test
	public void meanProbability() {
		FeatureStatistics f = new FeatureStatistics(0, 1);
		
		assertEquals(f.calculateProbability(0), 1.0 / Math.sqrt(2 * Math.PI), DELTA10);
	}
	
	@Test
	public void logProbability1() {
		FeatureStatistics f = new FeatureStatistics(0, 1);
		
		assertEquals(f.calculateLogProbability(-10), Math.log(f.calculateProbability(-10)), DELTA10);
	}

	@Test
	public void logProbability2() {
		FeatureStatistics f = new FeatureStatistics(3, 4);
		
		assertEquals(f.calculateLogProbability(3), Math.log(f.calculateProbability(3)), DELTA10);
	}

}
