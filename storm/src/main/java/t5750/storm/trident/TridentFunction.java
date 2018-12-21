package t5750.storm.trident;

import org.apache.storm.generated.StormTopology;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import t5750.storm.util.StormUtil;

/**
 * <B>系统名称：</B>测试TridentFunction使用<BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 */
public class TridentFunction {
	private static final String TOPOLOGY_NAME = "trident-function";

	/**
	 * 继承BaseFunction类，重写execute方法
	 */
	public static class SumFunction extends BaseFunction {
		@Override
		public void execute(TridentTuple tuple, TridentCollector collector) {
			System.out.println("传入进来的内容为：" + tuple);
			// 获取a，b2个域
			int a = tuple.getInteger(0);
			int b = tuple.getInteger(1);
			int sum = a + b;
			// 发射数据
			collector.emit(new Values(sum));
		}
	}

	public static StormTopology buildTopology() {
		TridentTopology topology = new TridentTopology();
		// 设置数据源
		FixedBatchSpout spout = new FixedBatchSpout(
		// 声明输入的域字段为"a", "b", "c", "d"
				new Fields("a", "b", "c", "d"),
				// 设置批处理大小
				4,
				// 设置数据源内容，测试数据
				new Values(1, 4, 7, 10), new Values(1, 1, 3, 11), new Values(2,
						2, 7, 1), new Values(2, 5, 7, 2));
		// 指定是否循环
		spout.setCycle(false);
		// 指定输入源spout
		Stream inputStream = topology.newStream("spout", spout);
		/**
		 * 要实现流spout-blot的模式，在trident里是使用each来做的 each方法参数：<br/>
		 * 1.输入数据源参数名称："a", "b", "c", "d"<br/>
		 * 2.需要流转执行的function对象（也就是bolt对象）：new SumFunction()<br/>
		 * 3.指定function对象里的输出参数名称：sum
		 */
		inputStream.each(new Fields("a", "b", "c", "d"), new SumFunction(),
				new Fields("sum"))
				/**
				 * 继续使用each调用下一个function(bolt)<br/>
				 * 第一个输入参数为："a", "b", "c", "d", "sum"<br/>
				 * 第二个参数为：new Fields()，也就是执行函数<br/>
				 * 第三个参数为没有输出
				 */
				.each(new Fields("a", "b", "c", "d", "sum"), new Result(),
						new Fields());
		// 利用这种方式，返回一个StormTopology对象，进行提交
		return topology.build();
	}

	public static void main(String[] args) throws Exception {
		StormUtil.submitTopology(args, buildTopology(), TOPOLOGY_NAME);
	}
}
