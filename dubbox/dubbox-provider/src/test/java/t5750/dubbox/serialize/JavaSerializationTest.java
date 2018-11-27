package t5750.dubbox.serialize;

import java.io.*;

import t5750.dubbox.entity.SampleJava;
import t5750.dubbox.util.SerialUtil;

/**
 * Java序列化
 */
public class JavaSerializationTest {
	public static final String PATH_JAVA = "D:/fileJava.bin";

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		setSerializableObject();
		System.out.println("Java Serializable writeObject time:"
				+ (System.currentTimeMillis() - start) + "ms");
		getSerializableObject();
		System.out.println("Java Serializable readObject time:"
				+ (System.currentTimeMillis() - start) + "ms");
	}

	public static void setSerializableObject() throws IOException {
		FileOutputStream fo = new FileOutputStream(PATH_JAVA);
		ObjectOutputStream so = new ObjectOutputStream(fo);
		for (int i = 0; i < SerialUtil.TEST_COUNTER; i++) {
			so.writeObject(SerialUtil.getSampleJava(i));
		}
		so.flush();
		so.close();
	}

	public static void getSerializableObject() {
		FileInputStream fi;
		try {
			fi = new FileInputStream(PATH_JAVA);
			ObjectInputStream si = new ObjectInputStream(fi);
			SampleJava sampleJava = null;
			while ((sampleJava = (SampleJava) si.readObject()) != null) {
				// System.out.println(sample.getAge() + " " + sample.getName());
			}
			fi.close();
			si.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
