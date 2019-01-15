package t5750.tcctransaction.recover;

/**
 * 事务恢复配置接口.
 */
public interface RecoverConfig {
	/**
	 * 获取最大重试次数
	 * 
	 * @return
	 */
	public int getMaxRetryCount();

	/**
	 * 获取需要执行事务恢复的持续时间.
	 * 
	 * @return
	 */
	public int getRecoverDuration();

	/**
	 * 获取定时任务规则表达式.
	 * 
	 * @return
	 */
	public String getCronExpression();
}
