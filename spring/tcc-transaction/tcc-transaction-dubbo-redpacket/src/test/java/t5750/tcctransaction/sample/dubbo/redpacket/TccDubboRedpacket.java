package t5750.tcctransaction.sample.dubbo.redpacket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @描述: 启动Dubbo服务用的MainClass.
 * @版本: 1.0 .
 */
public class TccDubboRedpacket {
	private static final Log log = LogFactory.getLog(TccDubboRedpacket.class);

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath:config/spring/local/appcontext.xml");
			context.start();
		} catch (Exception e) {
			log.error("== DubboProvider context start error:", e);
		}
		synchronized (TccDubboRedpacket.class) {
			while (true) {
				try {
					TccDubboRedpacket.class.wait();
				} catch (InterruptedException e) {
					log.error("== synchronized error:", e);
				}
			}
		}
	}
}