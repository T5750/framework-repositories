package t5750.tcctransaction;

import java.io.Serializable;

import org.apache.log4j.Logger;

import t5750.tcctransaction.api.TransactionXid;

/**
 * 事务参与者.
 */
public class Participant implements Serializable {
	static final Logger LOG = Logger.getLogger(Participant.class
			.getSimpleName());
	private static final long serialVersionUID = 4127729421281425247L;
	private TransactionXid xid;
	private Terminator terminator;

	public Participant() {
	}

	public Participant(TransactionXid xid, Terminator terminator) {
		this.xid = xid;
		this.terminator = terminator;
	}

	/**
	 * 回滚参与者事务（在Transaction中被调用）
	 */
	public void rollback() {
		LOG.debug("==>Participant.rollback()");
		terminator.rollback();
	}

	/**
	 * 提交参与者事务（在Transaction中被调用）.
	 */
	public void commit() {
		LOG.debug("==>Participant.rollback()");
		terminator.commit();
	}
}
