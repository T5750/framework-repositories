package t5750.springboot2provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import t5750.springboot2api.model.EmployeeName;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Boot2ProviderTest {
	private EmployeeName product;

	@Before
	public void setup() {
		product = new EmployeeName("T5750", "Evaneo");
	}

	@Test
	public void simpleGsonTest() throws JSONException {
		String expected = "{\n" +
				"\"firstName\": \"T5750\",\n" +
				"\"lastName\": \"Evaneo\"\n" +
				"}";
		Gson gson = new GsonBuilder().create();
		String data = gson.toJson(product);
		JSONAssert.assertEquals(expected, data, false);
	}

	@Test
	public void errorGsonTest() throws JSONException {
		String expected = "{\n" +
				"\"firstName\": \"T57503\",\n" +
				"\"lastName\": \"Evaneo\"\n" +
				"}";
		Gson gson = new GsonBuilder().create();
		String data = gson.toJson(product);
		JSONAssert.assertEquals(expected, data, false);
	}
}
