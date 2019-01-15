package t5750.tcctransaction.support;

import t5750.tcctransaction.TransactionManager;
import t5750.tcctransaction.TransactionRepository;
import t5750.tcctransaction.recover.RecoverConfig;

/**
 * 事务配置器接口
 */
public interface TransactionConfigurator {
	/**
	 * 获取事务管理器.
	 * 
	 * @return
	 */
	public TransactionManager getTransactionManager();

	/**
	 * 获取事务库.
	 * 
	 * @return
	 */
	public TransactionRepository getTransactionRepository();

	/**
	 * 获取事务恢复配置.
	 * 
	 * @return
	 */
	public RecoverConfig getRecoverConfig();
}
