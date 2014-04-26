package classificationTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import classification.Example;

public class ExampleTest {
	Example e;

	@Before
	public void setUp() throws Exception {
		e = new Example("test",5);
		e.add(0, 3.3);
		e.add(1, 3.4);
		e.add(2, 3.5);
		e.add(4, 7.3);
	}
	
	@Test
	public void testExample() {
		assertNotNull(e);
	}

	@Test
	public void testAdd() {
		e.add(3, 5.5);
	}

	@Test
	public void testGetValue() {
		assertTrue(e.getValue(0) == 3.3);
		assertTrue(e.getValue(2) == 3.5);
		assertTrue(e.getValue(4) == 7.3);
	}

	@Test
	public void testToString() {
		e.add(3, 5.5);

		String output = e.toString();
		
		assertTrue("{test, [3.3, 3.4, 3.5, 5.5, 7.3]}".equals(output));
	}
	
}
