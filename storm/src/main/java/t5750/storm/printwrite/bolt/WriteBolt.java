package t5750.storm.printwrite.bolt;

import java.io.FileWriter;

import org.apache.log4j.Logger;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import t5750.storm.util.StormUtil;

public class WriteBolt extends BaseBasicBolt {
	private static final long serialVersionUID = 1L;
	// private static final Log log = LogFactory.getLog(WriteBolt.class);
	private static Logger logger = Logger.getRootLogger();
	private FileWriter writer;

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		// 获取上一个组件所声明的Field
		String text = input.getStringByField("write");
		writer = StormUtil.writeFile(writer, logger, text, this);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}
}
