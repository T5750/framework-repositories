package t5750.dubbox.serialize;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import t5750.dubbox.entity.SampleJava;
import t5750.dubbox.util.SerialUtil;
import de.ruedigermoeller.serialization.FSTObjectInput;
import de.ruedigermoeller.serialization.FSTObjectOutput;

/**
 * FST序列化
 */
public class FstSerializationTest {
	public static final String PATH_FST = "D:/fileFST.bin";

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		setSerializableObject();
		System.out.println("FST Serializable writeObject time:"
				+ (System.currentTimeMillis() - start) + "ms");
		getSerializableObject();
		System.out.println("FST Serializable readObject time:"
				+ (System.currentTimeMillis() - start) + "ms");
	}

	public static void setSerializableObject() throws IOException {
		FSTObjectOutput output = new FSTObjectOutput(new FileOutputStream(
				PATH_FST));
		for (int i = 0; i < SerialUtil.TEST_COUNTER; i++) {
			output.writeObject(SerialUtil.getSampleJava(i));
		}
		output.close();
	}

	public static void getSerializableObject() {
		FSTObjectInput input = null;
		try {
			input = new FSTObjectInput(new FileInputStream(PATH_FST));
			SampleJava sampleJava = null;
			while ((sampleJava = (SampleJava) input
					.readObject(SampleJava.class)) != null) {
				// System.out.println(sampleJava.getAge() + " "+
				// sampleJava.getName());
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}
}
