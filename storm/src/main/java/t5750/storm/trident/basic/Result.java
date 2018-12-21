package t5750.storm.trident.basic;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;

/**
 * 继承BaseFunction类，重写execute方法
 */
public class Result extends BaseFunction {
	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		// 获取tuple输入内容
		System.out.println();
		Integer a = tuple.getIntegerByField("a");
		Integer b = tuple.getIntegerByField("b");
		Integer c = tuple.getIntegerByField("c");
		Integer d = tuple.getIntegerByField("d");
		System.out.println("a: " + a + ", b: " + b + ", c: " + c + ", d: " + d);
		if (tuple.size() > 4) {
			Integer sum = tuple.getIntegerByField("sum");
			System.out.println("sum: " + sum);
		}
	}
}