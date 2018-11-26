package t5750.dubbox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.objenesis.strategy.StdInstantiatorStrategy;

import t5750.dubbox.entity.Sample;
import t5750.dubbox.util.SerialUtil;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * JKryo序列化
 */
public class MyKryoSer {
	public static final String PATH_KRYO = "D:/fileKryo.bin";

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		setSerializableObject();
		System.out.println("Kryo Serializable writeObject time:"
				+ (System.currentTimeMillis() - start) + "ms");
		getSerializableObject();
		System.out.println("Kryo Serializable readObject time:"
				+ (System.currentTimeMillis() - start) + "ms");
	}

	public static void setSerializableObject() throws IOException {
		Kryo kryo = new Kryo();
		kryo.setReferences(false);
		kryo.setRegistrationRequired(false);
		kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
		kryo.register(Sample.class);
		Output output = new Output(new FileOutputStream(PATH_KRYO));
		for (int i = 0; i < SerialUtil.TEST_COUNTER; i++) {
			kryo.writeObject(output, SerialUtil.getSample(i));
		}
		output.flush();
		output.close();
	}

	public static void getSerializableObject() {
		Kryo kryo = new Kryo();
		kryo.setReferences(false);
		kryo.setRegistrationRequired(false);
		kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
		Input input = null;
		try {
			input = new Input(new FileInputStream(PATH_KRYO));
			Sample sample = null;
			while ((sample = kryo.readObject(input, Sample.class)) != null) {
				// System.out.println(sample.getAge() + " " + sample.getName());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (KryoException e) {
			// e.printStackTrace();
		} finally {
			if (null != input) {
				input.close();
			}
		}
	}
}
