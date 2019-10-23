package t5750.dubbox.serialize;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import t5750.dubbox.entity.Sample;
import t5750.dubbox.util.SerialUtil;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson序列化
 */
public class JacksonSerializationTest {
	public static final String PATH_JACKSON = "D:/fileJackson.bin";

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		setSerializableObject();
		System.out.println("Jackson Serializable writeObject time:"
				+ (System.currentTimeMillis() - start) + "ms");
		getSerializableObject();
		System.out.println("Jackson Serializable readValue time:"
				+ (System.currentTimeMillis() - start) + "ms");
	}

	public static void setSerializableObject() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonFactory factory = new JsonFactory();
		factory.setCodec(objectMapper);
		JsonGenerator jsonGenerator = factory.createGenerator(
				new FileOutputStream(PATH_JACKSON), JsonEncoding.UTF8);
		for (int i = 0; i < SerialUtil.TEST_COUNTER; i++) {
			jsonGenerator.writeObject(SerialUtil.getSample(i));
		}
		jsonGenerator.close();
	}

	public static void getSerializableObject() {
		ObjectMapper objectMapper = new ObjectMapper();
		FileInputStream input = null;
		try {
			input = new FileInputStream(PATH_JACKSON);
			Sample sample = null;
			while ((sample = objectMapper.readValue(input, Sample.class)) != null) {
				// System.out.println(sample.getAge() + " " + sample.getName());
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}
}
