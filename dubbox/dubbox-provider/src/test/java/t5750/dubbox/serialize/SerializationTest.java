package t5750.dubbox.serialize;

import java.io.IOException;

/**
 * 序列化测试
 */
public class SerializationTest {
	public static void main(String[] args) throws IOException {
		JavaSerializationTest.main(args);
		Hessian2SerializationTest.main(args);
		KryoSerializationTest.main(args);
	}
}
