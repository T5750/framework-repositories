package t5750.nio.tutorial;

import java.nio.FloatBuffer;

public class UseFloatBuffer {
	static public void main(String args[]) throws Exception {
		FloatBuffer buffer = FloatBuffer.allocate(10);
		for (int i = 0; i < buffer.capacity(); ++i) {
			float f = (float) Math.sin((((float) i) / 10) * (2 * Math.PI));
			buffer.put(f);
		}
		buffer.flip();
		while (buffer.hasRemaining()) {
			float f = buffer.get();
			System.out.println(f);
		}
	}
}
