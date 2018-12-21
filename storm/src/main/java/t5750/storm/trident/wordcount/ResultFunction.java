package t5750.storm.trident.wordcount;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;

/**
 * 继承BaseFunction类，重写execute方法
 */
public class ResultFunction extends BaseFunction {
	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		// 获取tuple输入内容
		System.out.println();
		String sub = tuple.getStringByField("sub");
		Long count = tuple.getLongByField("count");
		System.out.println("sub: " + sub + ", count: " + count);
	}
}