package t5750.springboot2.util;

import java.io.IOException;

import org.junit.Test;
import t5750.springboot2.test.AbstractTest;

public class PropertiesUtilTest extends AbstractTest {
	@Test
	public void getProperty() throws IOException {
		String serverPort = PropertiesUtil.getProperty("server.port");
		assertEquals("18090", serverPort);
	}
}
