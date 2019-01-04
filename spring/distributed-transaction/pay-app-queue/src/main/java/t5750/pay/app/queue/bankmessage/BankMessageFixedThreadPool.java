package t5750.pay.app.queue.bankmessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <b>功能说明: </b>
 */
public class BankMessageFixedThreadPool {
	private static ExecutorService fixedThreadPool = Executors
			.newFixedThreadPool(5);

	public static void addTask(BankMessageTask bankMessageTask) {
		fixedThreadPool.execute(bankMessageTask);
	}
}
