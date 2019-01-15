package t5750.tcctransaction.spring.recover;

import t5750.tcctransaction.recover.RecoverConfig;

/**
 * 默认事务恢复配置.
 */
public class DefaultRecoverConfig implements RecoverConfig {
	public static final RecoverConfig INSTANCE = new DefaultRecoverConfig();
	/**
	 * 一个事务最多尝试恢复次数（超过将不在自动恢复，需要人工干预，默认是30次）
	 */
	private int maxRetryCount = 30;
	/**
	 * 一个事务日志当超过一定时间间隔后没有更新就会被认为是发生了异常，需要恢复，
	 * 恢复Job将扫描超过这个时间间隔依旧没有更新的事务日志，并对这些事务进行恢复，时间单位是秒，默认是120秒
	 */
	private int recoverDuration = 120; // 120 seconds
	/**
	 * 恢复Job触发间隔配置，默认是(每分钟)
	 */
	private String cronExpression = "0 */1 * * * ?";

	@Override
	public int getMaxRetryCount() {
		return maxRetryCount;
	}

	@Override
	public int getRecoverDuration() {
		return recoverDuration;
	}

	@Override
	public String getCronExpression() {
		return cronExpression;
	}

	public void setMaxRetryCount(int maxRetryCount) {
		this.maxRetryCount = maxRetryCount;
	}

	public void setRecoverDuration(int recoverDuration) {
		this.recoverDuration = recoverDuration;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
}