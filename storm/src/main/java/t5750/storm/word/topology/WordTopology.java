package t5750.storm.word.topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import t5750.storm.util.StormUtil;
import t5750.storm.word.bolt.WordCountBolt;
import t5750.storm.word.bolt.WordReportBolt;
import t5750.storm.word.bolt.WordSplitBolt;
import t5750.storm.word.spout.WordSpout;

public class WordTopology {
	private static final String WORD_SPOUT_ID = "word-spout";
	private static final String SPLIT_BOLT_ID = "split-bolt";
	private static final String COUNT_BOLT_ID = "count-bolt";
	private static final String REPORT_BOLT_ID = "report-bolt";
	private static final String TOPOLOGY_NAME = "word-count-topology";

	public static void main(String[] args) {
		// 实例化对象
		WordSpout spout = new WordSpout();
		WordSplitBolt splitBolt = new WordSplitBolt();
		WordCountBolt countBolt = new WordCountBolt();
		WordReportBolt reportBolt = new WordReportBolt();
		// 构建拓扑
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(WORD_SPOUT_ID, spout);
		// WordSpout-->WordSplitBolt
		builder.setBolt(SPLIT_BOLT_ID, splitBolt, 5)
				.shuffleGrouping(WORD_SPOUT_ID);
		// WordSplitBolt-->WordCountBolt
		builder.setBolt(COUNT_BOLT_ID, countBolt, 5)
				.fieldsGrouping(SPLIT_BOLT_ID, new Fields("word"));
		// WordCountBolt-->WordReportBolt
		builder.setBolt(REPORT_BOLT_ID, reportBolt, 10)
				.shuffleGrouping(COUNT_BOLT_ID);
		// 本地模式
		Config config = new Config();
		config.setDebug(false);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());
		StormUtil.waitForSeconds(10);
		cluster.killTopology(TOPOLOGY_NAME);
		cluster.shutdown();
	}
}
