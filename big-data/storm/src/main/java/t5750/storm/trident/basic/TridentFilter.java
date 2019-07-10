package t5750.storm.trident.basic;

import org.apache.storm.generated.StormTopology;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;

import t5750.storm.util.StormUtil;

/**
 * <B>系统名称：</B>测试TridentFilter使用<BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 */
public class TridentFilter {
	private static final String TOPOLOGY_NAME = "trident-filter";

	/**
	 * 继承BaseFilter类，重写isKeep方法
	 */
	public static class CheckEvenSumFilter extends BaseFilter {
		private static final long serialVersionUID = 7L;

		@Override
		public boolean isKeep(TridentTuple tuple) {
			int number1 = tuple.getInteger(0);
			int number2 = tuple.getInteger(1);
			int sum = number1 + number2;
			if (sum % 2 == 0) {
				return true;
			}
			return false;
		}
	}

	public static StormTopology buildTopology() {
		TridentTopology topology = new TridentTopology();
		// 设置数据源
		FixedBatchSpout spout = StormUtil.getSpout();
		// 指定输入源spout
		Stream inputStream = topology.newStream("spout", spout);
		/**
		 * 要实现流spout-blot的模式，在trident里是使用each来做的 each方法参数：<br/>
		 * 1.输入数据源参数名称："a", "b", "c", "d"<br/>
		 * 2.需要流转执行的function对象（也就是bolt对象）：new SumFunction()<br/>
		 * 3.指定function对象里的输出参数名称：sum
		 */
		inputStream.each(new Fields("a", "b", "c", "d"),
				new CheckEvenSumFilter())
		/**
		 * 继续使用each调用下一个function(bolt)<br/>
		 * 第一个输入参数为："a", "b", "c", "d", "sum"<br/>
		 * 第二个参数为：new Fields()，也就是执行函数<br/>
		 * 第三个参数为没有输出
		 */
		.each(new Fields("a", "b", "c", "d"), new Result(), new Fields());
		// 利用这种方式，返回一个StormTopology对象，进行提交
		return topology.build();
	}

	public static void main(String[] args) throws Exception {
		StormUtil.submitTopology(args, buildTopology(), TOPOLOGY_NAME);
	}
}
