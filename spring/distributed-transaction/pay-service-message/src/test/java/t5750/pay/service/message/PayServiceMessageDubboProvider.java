package t5750.pay.service.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @描述: 启动Dubbo服务用的MainClass.
 * @版本: 1.0 .
 */
public class PayServiceMessageDubboProvider {
	private static final Log log = LogFactory
			.getLog(PayServiceMessageDubboProvider.class);

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath:spring/spring-context.xml");
			context.start();
			// RpTransactionMessageService rpTransactionMessageService =
			// (RpTransactionMessageService)
			// context.getBean("rpTransactionMessageService");
			//
			// // step1.查询出所有符合时间条件内的，状态为发送理状态的记录
			// Map<String, Object> paramMap = new HashMap<String, Object>();
			// // 获取配置的开始处理的时间
			// String dateStr = "2016-07-18 15:27:25";
			// paramMap.put("createTimeBefore", dateStr);// 取存放了多久的消息
			// paramMap.put("status", MessageStatusEnum.SENDING.name());//
			// 取状态为发送中的消息
			// paramMap.put("areadlyDead", PublicEnum.NO.name());// 取存活的发送中消息
			// // 每次抓去5000条数据处理
			// paramMap.put("pageFirst", 0);
			// paramMap.put("pageSize", 1000);
			// List<RpTransactionMessage> messages =
			// rpTransactionMessageService.listTimeOutMessage(paramMap);
			// System.out.println(messages.size());
		} catch (Exception e) {
			log.error("== DubboProvider context start error:", e);
		}
		synchronized (PayServiceMessageDubboProvider.class) {
			while (true) {
				try {
					PayServiceMessageDubboProvider.class.wait();
				} catch (InterruptedException e) {
					log.error("== synchronized error:", e);
				}
			}
		}
	}
}