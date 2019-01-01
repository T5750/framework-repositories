package t5750.pay.common.core.ex.log4j;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

/**
 * <b>功能说明: </b>
 */
public class ExPatternLayout extends PatternLayout {
	public ExPatternLayout(String pattern) {
		super(pattern);
	}

	public ExPatternLayout() {
		super();
	}

	/**
	 * 重写createPatternParser方法，返回PatternParser的子类
	 */
	@Override
	protected PatternParser createPatternParser(String pattern) {
		return new ExPatternParser(pattern);
	}
}
