package t5750.storm.kafka.wordcount;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

public class PrinterBolt extends BaseBasicBolt {
	public void execute(Tuple input, BasicOutputCollector collector) {
		// get the sentence from the tuple and print it
		String sentence = input.getString(0);
		System.out.println("Received Sentence:" + sentence);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// we don't emit anything
	}
}
