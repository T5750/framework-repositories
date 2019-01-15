package t5750.tcctransaction.common;

/**
 * 事务类型.
 */
public enum TransactionType {
	/**
	 * 主事务:1.
	 */
	ROOT(1),
	/**
	 * 分支事务:2.
	 */
	BRANCH(2);
	int id;

	TransactionType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static TransactionType valueOf(int id) {
		switch (id) {
		case 1:
			return ROOT;
		case 2:
			return BRANCH;
		default:
			return null;
		}
	}
}
