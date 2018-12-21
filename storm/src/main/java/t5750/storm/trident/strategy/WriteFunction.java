package t5750.storm.trident.strategy;

import java.io.FileWriter;

import org.apache.log4j.Logger;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;

import t5750.storm.util.StormUtil;

/**
 * <B>系统名称：</B>WriteFunction<BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 */
public class WriteFunction extends BaseFunction {
	private static final long serialVersionUID = 1L;
	// private static final Log log = LogFactory.getLog(WriteFunction.class);
	private static Logger logger = Logger.getRootLogger();
	private FileWriter writer;

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String text = tuple.getStringByField("sub");
		writer = StormUtil.writeFile(writer, logger, text, this);
	}
}
