package t5750.spring.test;

import org.junit.Assert;

public abstract class AbstractTest {
	static public void assertEquals(Object expected, Object actual) {
		Assert.assertEquals(expected, actual);
	}

	static public void assertNotEquals(Object unexpected, Object actual) {
		Assert.assertNotEquals(unexpected, actual);
	}

	static public void assertTrue(boolean condition) {
		Assert.assertTrue(condition);
	}
}