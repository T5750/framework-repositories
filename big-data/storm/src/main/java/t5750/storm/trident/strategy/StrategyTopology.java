package t5750.storm.trident.strategy;

import org.apache.storm.generated.StormTopology;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import t5750.storm.util.StormUtil;

/**
 * <B>系统名称：</B>StrategyTopology<BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 */
public class StrategyTopology {
	private static final String TOPOLOGY_NAME = "trident-strategy";

	public static StormTopology buildTopology() {
		TridentTopology topology = new TridentTopology();
		// 设定数据源
		FixedBatchSpout spout = new FixedBatchSpout(
				// 声明输入的域字段为"sub"
				new Fields("sub"),
				// 设置批处理大小为4
				4,
				// 设置数据源内容，测试数据
				new Values("java"), new Values("python"), new Values("php"),
				new Values("c++"), new Values("ruby"));
		// 指定是否循环
		spout.setCycle(true);
		// 指定输入源spout
		Stream inputStream = topology.newStream("spout", spout);
		/**
		 * 要实现流sqout - bolt的模式，在trident里是使用each来做的<br/>
		 * each方法参数：<br/>
		 * 1.输入数据源参数名称："sub"<br/>
		 * 2.需要流转执行的function对象（也就是bolt对象）：new WriteFunction()<br/>
		 * 3.指定function对象里的输出参数名称，没有则不输出任何内容
		 */
		inputStream
				// 随机分组：shuffle
				.shuffle()
				// 分区分组：partitionBy
				// .partitionBy(new Fields("sub"))
				// 全局分组：global
				// .global()
				// 广播分组：broadcast
				// .broadcast()
				.each(new Fields("sub"), new WriteFunction(), new Fields())
				.parallelismHint(4);
		// 利用这种方式，返回一个StormTopology对象，进行提交
		return topology.build();
	}

	public static void main(String[] args) throws Exception {
		StormUtil.submitTopology(args, buildTopology(), TOPOLOGY_NAME);
	}
}
