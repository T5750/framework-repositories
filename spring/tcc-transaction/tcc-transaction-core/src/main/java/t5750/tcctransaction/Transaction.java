package t5750.tcctransaction;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.transaction.xa.Xid;

import org.apache.log4j.Logger;

import t5750.tcctransaction.api.TransactionContext;
import t5750.tcctransaction.api.TransactionStatus;
import t5750.tcctransaction.api.TransactionXid;
import t5750.tcctransaction.common.TransactionType;

/**
 * 事务类.
 */
public class Transaction implements Serializable {
	static final Logger LOG = Logger.getLogger(Transaction.class
			.getSimpleName());
	private static final long serialVersionUID = 7291423944314337931L;
	/**
	 * 事务XID.
	 */
	private TransactionXid xid;
	/**
	 * 事务状态.
	 */
	private TransactionStatus status;
	/**
	 * 事务类型.
	 */
	private TransactionType transactionType;
	/**
	 * 事务恢复重试次数
	 */
	private volatile int retriedCount = 0;
	/**
	 * 创建时间
	 */
	private Date createTime = new Date();
	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime = new Date();
	/**
	 * 版本（默认值为1）
	 */
	private long version = 1;
	/**
	 * 参与者列表.
	 */
	private List<Participant> participants = new ArrayList<Participant>();
	/**
	 * 附加属性
	 */
	private Map<String, Object> attachments = new ConcurrentHashMap<String, Object>();

	public Transaction() {
	}

	/**
	 * 事务构造方法（基于全局事务id创建新的分支事务）
	 * 通过transactionContext传入Xid，默认状态为TRYING:1，默认事务类型为BRANCH:2
	 * 
	 * @param transactionContext
	 */
	public Transaction(TransactionContext transactionContext) {
		this.xid = transactionContext.getXid();
		this.status = TransactionStatus.TRYING;
		this.transactionType = TransactionType.BRANCH;
	}

	/**
	 * 事务构造方法，传入transactionType，默认状态为TRYING:1，Xid自动生成
	 * 
	 * @param transactionType
	 */
	public Transaction(TransactionType transactionType) {
		this.xid = new TransactionXid();
		this.status = TransactionStatus.TRYING;
		this.transactionType = transactionType;
	}

	/**
	 * 招募参与者（加入参与者）
	 * 
	 * @param participant
	 */
	public void enlistParticipant(Participant participant) {
		participants.add(participant);
	}

	/**
	 * 获取事务ID.
	 * 
	 * @return
	 */
	public Xid getXid() {
		return xid.clone();
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public List<Participant> getParticipants() {
		return Collections.unmodifiableList(participants);
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void changeStatus(TransactionStatus status) {
		this.status = status;
	}

	/**
	 * 事务提交（包含此事务的所有参与者的逐个提交，在TransactionManager中被调用）.
	 */
	public void commit() {
		LOG.debug("==>Transaction.commit()");
		for (Participant participant : participants) {
			participant.commit();
		}
	}

	/**
	 * 事务回滚（包含此事务的所有参与者的逐个回滚，在TransactionManager中被调用）.
	 */
	public void rollback() {
		LOG.debug("==>Transaction.rollback()");
		for (Participant participant : participants) {
			participant.rollback();
		}
	}

	public int getRetriedCount() {
		return retriedCount;
	}

	/**
	 * 重试次数+1
	 */
	public void addRetriedCount() {
		this.retriedCount++;
	}

	public void resetRetriedCount(int retriedCount) {
		this.retriedCount = retriedCount;
	}

	public Map<String, Object> getAttachments() {
		return attachments;
	}

	public long getVersion() {
		return version;
	}

	public void updateVersion() {
		this.version++;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date date) {
		this.lastUpdateTime = date;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void updateTime() {
		this.lastUpdateTime = new Date();
	}
}
