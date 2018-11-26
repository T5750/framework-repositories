package t5750.dubbox.util;

import java.util.HashMap;
import java.util.Map;

import t5750.dubbox.entity.Sample;
import t5750.dubbox.entity.SampleJava;

/**
 *
 */
public class SerialUtil {
	public static final int TEST_COUNTER = 100000;

	private static Map createMap(int i) {
		Map<String, Integer> map = new HashMap<>(2);
		map.put("id", i);
		map.put("name", i);
		return map;
	}

	public static SampleJava getSampleJava(int i) {
		SampleJava sampleJava = new SampleJava("id" + i, (i + 1), createMap(i));
		return sampleJava;
	}

	public static Sample getSample(int i) {
		Sample sample = new Sample("id" + i, (i + 1), createMap(i));
		return sample;
	}
}
