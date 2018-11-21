package t5750.activemqprovider.selector;

import javax.jms.MapMessage;

/**
 *
 */
public class MessageTask implements Runnable {
	private MapMessage message;

	public MessageTask(MapMessage message) {
		this.message = message;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(500);
			System.out.println("当期线程：" + Thread.currentThread().getName()
					+ "，处理任务：" + this.message.getString("id"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
