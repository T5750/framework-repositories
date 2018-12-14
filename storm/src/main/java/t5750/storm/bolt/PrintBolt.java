package t5750.storm.bolt;

import org.apache.log4j.Logger;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class PrintBolt extends BaseBasicBolt {
	private static final long serialVersionUID = 1L;
	// private static final Log log = LogFactory.getLog(PrintBolt.class);
	private static Logger logger = Logger.getRootLogger();

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		// 获取上一个组件所声明的Field
		String print = input.getStringByField("print");
		logger.info("【print】： " + print);
		// System.out.println("Name of input word is : " + word);
		// 进行传递给下一个bolt
		collector.emit(new Values(print));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("write"));
	}
}
