package t5750.pay.app.queue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import t5750.pay.app.queue.utils.SpringContextUtil;

/**
 * @描述: 通知APP.
 * @版本: V1.0
 */
public class PayAppQueue {
	private static final Log LOG = LogFactory.getLog(PayAppQueue.class);

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "spring/spring-context.xml" });
			// 初始化SpringContextUtil
			SpringContextUtil ctxUtil = new SpringContextUtil();
			ctxUtil.setApplicationContext(context);
			context.start();
			LOG.info("== context start");
		} catch (Exception e) {
			LOG.error("== application start error:", e);
			return;
		}
		synchronized (PayAppQueue.class) {
			while (true) {
				try {
					PayAppQueue.class.wait();
				} catch (InterruptedException e) {
					LOG.error("== synchronized error:", e);
				}
			}
		}
	}
}
