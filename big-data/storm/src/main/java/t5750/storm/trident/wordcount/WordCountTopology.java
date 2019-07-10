package t5750.storm.trident.wordcount;

import org.apache.storm.generated.StormTopology;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import t5750.storm.util.StormUtil;

/**
 * <B>系统名称：</B>WordCountTopology<BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 */
public class WordCountTopology {
	private static final String TOPOLOGY_NAME = "trident-wordcount";

	public static StormTopology buildTopology() {
		TridentTopology topology = new TridentTopology();
		// 设定数据源
		FixedBatchSpout spout = new FixedBatchSpout(
				// 声明输入的域字段为"subjects"
				new Fields("subjects"),
				// 设置批处理大小为4
				4,
				// 设置数据源内容，测试数据
				new Values("java java php ruby c++"),
				new Values("java python python python c++"),
				new Values("java java java java ruby"),
				new Values("c++ java ruby php java"));
		// 指定是否循环
		spout.setCycle(false);
		// 使用IBatchSpout接口实例化一个spout
		// SubjectsSpout spout = new SubjectsSpout(4);
		// 指定输入源spout
		Stream inputStream = topology.newStream("spout", spout);
		/**
		 * 要实现流sqout - bolt的模式，在trident里是使用each来做的<br/>
		 * each方法参数：<br/>
		 * 1.输入数据源参数名称：subjects<br/>
		 * 2.需要流转执行的function对象（也就是bolt对象）：new Split()<br/>
		 * 3.指定function对象里的输出参数名称：subject
		 */
		// 设置随机分组
		inputStream.shuffle()
				.each(new Fields("subjects"), new SplitFunction(),
						new Fields("sub"))
				// 进行分组操作：参数为分组字段subject，比较类似于我们之前所接触的FieldsGroup
				.groupBy(new Fields("sub"))
				// 对分组之后的结果进行聚合操作:参数1为聚合方法为count函数，输出字段名称为count
				.aggregate(new Count(), new Fields("count"))
				// 继续使用each调用下一个function（bolt）输入参数为subject和count，第二个参数为new
				// Result() 也就是执行函数，第三个参数为没有输出
				.each(new Fields("sub", "count"), new ResultFunction(),
						new Fields())
				.parallelismHint(1);
		// 利用这种方式，返回一个StormTopology对象，进行提交
		return topology.build();
	}

	public static void main(String[] args) throws Exception {
		StormUtil.submitTopology(args, buildTopology(), TOPOLOGY_NAME);
	}
}
