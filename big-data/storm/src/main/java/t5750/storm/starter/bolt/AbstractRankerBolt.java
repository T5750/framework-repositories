package t5750.storm.starter.bolt;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.storm.Config;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.TupleUtils;

import t5750.storm.starter.tools.Rankings;

/**
 * This abstract bolt provides the basic behavior of bolts that rank objects
 * according to their count.
 * <p/>
 * It uses a template method design pattern for
 * {@link AbstractRankerBolt#execute(Tuple, BasicOutputCollector)} to allow
 * actual bolt implementations to specify how incoming tuples are processed,
 * i.e. how the objects embedded within those tuples are retrieved and counted.
 */
public abstract class AbstractRankerBolt extends BaseBasicBolt {
	private static final long serialVersionUID = 4931640198501530202L;
	private static final int DEFAULT_EMIT_FREQUENCY_IN_SECONDS = 2;
	private static final int DEFAULT_COUNT = 10;
	private final int emitFrequencyInSeconds;
	private final int count;
	private final Rankings rankings;

	public AbstractRankerBolt() {
		this(DEFAULT_COUNT, DEFAULT_EMIT_FREQUENCY_IN_SECONDS);
	}

	public AbstractRankerBolt(int topN) {
		this(topN, DEFAULT_EMIT_FREQUENCY_IN_SECONDS);
	}

	public AbstractRankerBolt(int topN, int emitFrequencyInSeconds) {
		if (topN < 1) {
			throw new IllegalArgumentException(
					"topN must be >= 1 (you requested " + topN + ")");
		}
		if (emitFrequencyInSeconds < 1) {
			throw new IllegalArgumentException(
					"The emit frequency must be >= 1 seconds (you requested "
							+ emitFrequencyInSeconds + " seconds)");
		}
		count = topN;
		this.emitFrequencyInSeconds = emitFrequencyInSeconds;
		rankings = new Rankings(count);
	}

	protected Rankings getRankings() {
		return rankings;
	}

	/**
	 * This method functions as a template method (design pattern).
	 */
	@Override
	public final void execute(Tuple tuple, BasicOutputCollector collector) {
		if (TupleUtils.isTick(tuple)) {
			getLogger().debug(
					"Received tick tuple, triggering emit of current rankings");
			emitRankings(collector);
		} else {
			updateRankingsWithTuple(tuple);
		}
	}

	abstract void updateRankingsWithTuple(Tuple tuple);

	private void emitRankings(BasicOutputCollector collector) {
		collector.emit(new Values(rankings.copy()));
		getLogger().debug("Rankings: " + rankings);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("rankings"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		Map<String, Object> conf = new HashMap<String, Object>();
		conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, emitFrequencyInSeconds);
		return conf;
	}

	abstract Logger getLogger();
}