package t5750.storm.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class SampleSpout extends BaseRichSpout {
	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector spoutOutputCollector;
	private static final Map<Integer, String> FIRSTNAMEMAP = new HashMap<Integer, String>();
	static {
		FIRSTNAMEMAP.put(0, "john");
		FIRSTNAMEMAP.put(1, "nick");
		FIRSTNAMEMAP.put(2, "mick");
		FIRSTNAMEMAP.put(3, "tom");
		FIRSTNAMEMAP.put(4, "jerry");
	}
	private static final Map<Integer, String> LASTNAME = new HashMap<Integer, String>();
	static {
		LASTNAME.put(0, "anderson");
		LASTNAME.put(1, "watson");
		LASTNAME.put(2, "ponting");
		LASTNAME.put(3, "dravid");
		LASTNAME.put(4, "lara");
	}
	private static final Map<Integer, String> COMPANYNAME = new HashMap<Integer, String>();
	static {
		COMPANYNAME.put(0, "abc");
		COMPANYNAME.put(1, "dfg");
		COMPANYNAME.put(2, "pqr");
		COMPANYNAME.put(3, "ecd");
		COMPANYNAME.put(4, "awe");
	}

	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector spoutOutputCollector) {
		// Open the spout
		this.spoutOutputCollector = spoutOutputCollector;
	}

	public void nextTuple() {
		// Storm cluster repeatedly call this method to emit the continuous //
		// stream of tuples.
		final Random rand = new Random();
		// generate the random number from 0 to 4.
		int randomNumber = rand.nextInt(5);
		spoutOutputCollector.emit(new Values(FIRSTNAMEMAP.get(randomNumber),
				LASTNAME.get(randomNumber), COMPANYNAME.get(randomNumber)));
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// emits the field firstName, lastName and companyName.
		declarer.declare(new Fields("firstName", "lastName", "companyName"));
	}
}