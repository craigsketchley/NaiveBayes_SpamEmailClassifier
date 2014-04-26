package classificationTests;

import static org.junit.Assert.*;

import org.junit.Test;

import classification.ProbabilityDensityFunction;

public class ProbabilityDensityFunctionTest {
	double DELTA10 = 0.00000000001;
	
	
	@Test
	public void standardCalculation() {
		ProbabilityDensityFunction f = new ProbabilityDensityFunction(10, 2);
		
		assertEquals(f.calculateProbability(5), 0.008764150246784274, DELTA10);
	}

	@Test
	public void meanProbability() {
		ProbabilityDensityFunction f = new ProbabilityDensityFunction(0, 1);
		
		assertEquals(f.calculateProbability(0), 1.0 / Math.sqrt(2 * Math.PI), DELTA10);
	}
	
	@Test
	public void logProbability1() {
		ProbabilityDensityFunction f = new ProbabilityDensityFunction(0, 1);
		
		assertEquals(f.calculateLogProbability(-10), Math.log(f.calculateProbability(-10)), DELTA10);
	}

	@Test
	public void logProbability2() {
		ProbabilityDensityFunction f = new ProbabilityDensityFunction(3, 4);
		
		assertEquals(f.calculateLogProbability(3), Math.log(f.calculateProbability(3)), DELTA10);
	}

}
