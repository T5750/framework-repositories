package t5750.storm.message.spout;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class MessageSpout implements IRichSpout {
	private static final long serialVersionUID = 1L;
	private int index = 0;
	private String[] subjects = new String[] { "groovy,oeacnbase",
			"openfire,restful", "flume,activiti", "hadoop,hbase",
			"spark,sqoop" };
	private SpoutOutputCollector collector;

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void nextTuple() {
		if (index < subjects.length) {
			String sub = subjects[index];
			// 发送信息参数1为数值，参数2为msgId
			collector.emit(new Values(sub), index);
			index++;
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("subjects"));
	}

	@Override
	public void ack(Object msgId) {
		System.out.println("【消息发送成功！！！】（msgId = " + msgId + "）");
	}

	@Override
	public void fail(Object msgId) {
		System.out.println("【消息发送失败！！！】（msgId = " + msgId + "）");
		System.out.println("【重发进行中...】");
		collector.emit(new Values((Integer) msgId), msgId);
		// int msgIdIndex = Integer.valueOf(msgId.toString());
		// if (msgIdIndex < subjects.length) {
		// String sub = subjects[msgIdIndex];
		// collector.emit(new Values(sub), msgIdIndex);
		System.out.println("【重发成功！！！】");
		// }
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

	@Override
	public void close() {
	}

	@Override
	public void activate() {
	}

	@Override
	public void deactivate() {
	}
}
