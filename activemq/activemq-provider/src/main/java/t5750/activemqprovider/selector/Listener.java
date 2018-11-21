package t5750.activemqprovider.selector;

import java.util.concurrent.*;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 */
public class Listener implements MessageListener {
	BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(10000);
	// new LinkedBlockingDeque<Runnable>();
	ExecutorService executor = new ThreadPoolExecutor(Runtime.getRuntime()
			.availableProcessors(), 20, 120L, TimeUnit.SECONDS, queue);

	@Override
	public void onMessage(Message message) {
		if (message instanceof MapMessage) {
			MapMessage ret = (MapMessage) message;
			executor.execute(new MessageTask(ret));
		}
	}
}