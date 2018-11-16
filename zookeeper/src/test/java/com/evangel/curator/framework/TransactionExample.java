package com.evangel.curator.framework;

import java.util.Collection;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.api.transaction.CuratorTransactionFinal;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

import com.evangel.curator.util.CuratorTestUtil;

public class TransactionExample {
	private static final String PATH = "/a";
	private static final String A_PATH = "/a/path";
	private static final String ANOTHER_PATH = "/another/path";
	private static final String YET_ANOTHER_PATH = "/yet/another/path";

	public static void main(String[] args) {
		TestingServer server = null;
		CuratorFramework client = null;
		try {
			server = new TestingServer();
			client = CuratorTestUtil.startClient(server);
			initPath(client);
			transaction(client);
			System.out.println("--------------------------------------------");
			CuratorTransaction transactionDelete = startTransaction(client);
			System.out.println(A_PATH + " - "
					+ new String(client.getData().forPath(A_PATH)));
			CuratorTransactionFinal transactionFinalDelete = addDeleteToTransaction(transactionDelete);
			commitTransaction(transactionFinalDelete);
			CuratorTransaction transactionCreate = startTransaction(client);
			CuratorTransactionFinal transactionFinalCreate = addCreateToTransaction(transactionCreate);
			commitTransaction(transactionFinalCreate);
			System.out.println(A_PATH + " - "
					+ new String(client.getData().forPath(A_PATH)));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			CloseableUtils.closeQuietly(client);
			CloseableUtils.closeQuietly(server);
		}
	}

	public static void initPath(CuratorFramework client) throws Exception {
		client.create().creatingParentsIfNeeded().forPath(PATH, new byte[0]);
		client.create().creatingParentsIfNeeded()
				.forPath(ANOTHER_PATH, new byte[0]);
		client.create().creatingParentsIfNeeded()
				.forPath(YET_ANOTHER_PATH, new byte[0]);
	}

	public static Collection<CuratorTransactionResult> transaction(
			CuratorFramework client) throws Exception {
		// this example shows how to use ZooKeeper's new transactions
		Collection<CuratorTransactionResult> results = client.inTransaction()
				.create().forPath(A_PATH, "some data".getBytes()).and()
				.setData().forPath(ANOTHER_PATH, "other data".getBytes()).and()
				.delete().forPath(YET_ANOTHER_PATH).and().commit(); // IMPORTANT!
		// called
		for (CuratorTransactionResult result : results) {
			System.out.println(result.getForPath() + " - " + result.getType());
		}
		return results;
	}

	/*
	 * These next four methods show how to use Curator's transaction APIs in a
	 * more traditional - one-at-a-time - manner
	 */
	public static CuratorTransaction startTransaction(CuratorFramework client) {
		// start the transaction builder
		return client.inTransaction();
	}

	public static CuratorTransactionFinal addCreateToTransaction(
			CuratorTransaction transaction) throws Exception {
		// add a create operation
		return transaction.create().forPath(A_PATH, "new data".getBytes())
				.and();
	}

	public static CuratorTransactionFinal addDeleteToTransaction(
			CuratorTransaction transaction) throws Exception {
		// add a delete operation
		return transaction.delete().forPath(A_PATH).and();
	}

	public static void commitTransaction(CuratorTransactionFinal transaction)
			throws Exception {
		// commit the transaction
		transaction.commit();
	}
}