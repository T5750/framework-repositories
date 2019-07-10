package t5750.storm.trident.wordcount;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

/**
 * <B>系统名称：</B>SplitFunction<BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 */
public class SplitFunction extends BaseFunction {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String subjects = tuple.getStringByField("subjects");
		// 获取tuple输入内容
		// 逻辑处理，然后发射给下一个组件
		for (String sub : subjects.split(" ")) {
			collector.emit(new Values(sub));
		}
	}
}
