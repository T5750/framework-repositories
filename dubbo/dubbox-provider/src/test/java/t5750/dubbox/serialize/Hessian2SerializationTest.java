package t5750.dubbox.serialize;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import t5750.dubbox.entity.SampleJava;
import t5750.dubbox.util.SerialUtil;

import com.alibaba.com.caucho.hessian.io.*;

/**
 * Hessian2序列化
 */
public class Hessian2SerializationTest {
	public static final String PATH_HESSIAN2 = "D:/fileHessian2.bin";

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		setSerializableObject();
		System.out.println("Hessian2 Serializable writeObject time:"
				+ (System.currentTimeMillis() - start) + "ms");
		getSerializableObject();
		System.out.println("Hessian2 Serializable readObject time:"
				+ (System.currentTimeMillis() - start) + "ms");
	}

	public static void setSerializableObject() throws IOException {
		AbstractHessianOutput output = new Hessian2Output(new FileOutputStream(
				PATH_HESSIAN2));
		output.setSerializerFactory(new SerializerFactory());
		for (int i = 0; i < SerialUtil.TEST_COUNTER; i++) {
			output.writeObject(SerialUtil.getSampleJava(i));
		}
		output.close();
	}

	public static void getSerializableObject() {
		AbstractHessianInput input = null;
		try {
			input = new Hessian2Input(new FileInputStream(PATH_HESSIAN2));
			SampleJava sample = null;
			while ((sample = (SampleJava) input.readObject(SampleJava.class)) != null) {
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
