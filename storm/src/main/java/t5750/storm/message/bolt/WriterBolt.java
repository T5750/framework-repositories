package t5750.storm.message.bolt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import t5750.storm.util.StormUtil;

public class WriterBolt implements IRichBolt {
	private static final long serialVersionUID = 1L;
	private OutputCollector collector;
	private boolean flag = false;
	private FileWriter writer;

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		try {
			writer = new FileWriter(StormUtil.WINDOWS_FILE_DIR + "message.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute(Tuple tuple) {
		String word = tuple.getString(0);
		// List<String> list=(List<String>)tuple.getValueByField("word");
		// System.out.println("============================="+list);
		try {
			// if (!flag && word.equals("hadoop")) {
			// flag = true;
			// int a = 1 / 0;
			// }
			writer.write(word);
			writer.write("\r\n");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
			collector.fail(tuple);
		}
		collector.emit(tuple, new Values(word));
		collector.ack(tuple);
	}

	@Override
	public void cleanup() {
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
