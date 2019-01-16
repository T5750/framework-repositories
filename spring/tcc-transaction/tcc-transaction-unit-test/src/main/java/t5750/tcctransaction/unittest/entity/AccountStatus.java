package t5750.tcctransaction.unittest.entity;

/**
 */
public enum AccountStatus {
	/** 常规状态：1 **/
	NORMAL(1),
	/** 转账中：2 **/
	TRANSFERING(2);
	private int id;

	AccountStatus(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
