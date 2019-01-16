package t5750.tcctransaction.unit.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import t5750.tcctransaction.Transaction;
import t5750.tcctransaction.common.TransactionType;
import t5750.tcctransaction.serializer.KryoTransactionSerializer;
import t5750.tcctransaction.serializer.ObjectSerializer;
import t5750.tcctransaction.unittest.client.TransferService;

/**
 */
public class PerformanceTest extends AbstractTestCase {
	@Autowired
	private TransferService transferService;

	@Test
	public void performanceTest() {
		long currentTime = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			transferService.performenceTuningTransfer();
		}
		long thenTime = System.currentTimeMillis();
		System.out.println(thenTime - currentTime);
	}

	@Test
	public void serializeTest() {
		ObjectSerializer objectSerializer = new KryoTransactionSerializer();
		Transaction transaction = new Transaction(TransactionType.ROOT);
		long currentTime = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			byte[] bytes = objectSerializer.serialize(transaction);
			Transaction transaction1 = (Transaction) objectSerializer
					.deserialize(bytes);
			if (transaction.getVersion() != transaction1.getVersion()) {
				throw new Error();
			}
		}
		long thenTime = System.currentTimeMillis();
		System.out.println(thenTime - currentTime);
	}
}
