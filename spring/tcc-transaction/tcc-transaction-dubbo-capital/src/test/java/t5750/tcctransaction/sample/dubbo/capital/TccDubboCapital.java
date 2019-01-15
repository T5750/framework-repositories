package t5750.tcctransaction.sample.dubbo.capital;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @描述: 启动Dubbo服务用的MainClass.
 * @版本: 1.0 .
 */
public class TccDubboCapital {
	private static final Log log = LogFactory.getLog(TccDubboCapital.class);

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath*:config/spring/local/appcontext.xml");
			context.start();
		} catch (Exception e) {
			log.error("== DubboProvider context start error:", e);
		}
		synchronized (TccDubboCapital.class) {
			while (true) {
				try {
					TccDubboCapital.class.wait();
				} catch (InterruptedException e) {
					log.error("== synchronized error:", e);
				}
			}
		}
	}
}