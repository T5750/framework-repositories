package t5750.tcctransaction.api;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

import javax.transaction.xa.Xid;

/**
 * Xid 接口是 X/Open 事务标识符 XID 结构的 Java 映射。 此接口指定三个访问器方法，以检索全局事务格式 ID、全局事务 ID
 * 和分支限定符。 Xid 接口供事务管理器和资源管理器使用。此接口对应用程序不可见。
 * http://www.zgqxb.com.cn/mydoc/j2se_api_cn/javax/transaction/xa/Xid.html
 * 
 * Xid： 指一个XA事务。不同的数据库要不同的 Xid（每个数据库连接（分支）一个）
 */
public class TransactionXid implements Xid, Serializable {
	private static final long serialVersionUID = -6817267250789142043L;
	/**
	 * XID 的格式标识符
	 */
	private int formatId = 1;
	/**
	 * 全局事务ID.
	 */
	private byte[] globalTransactionId;
	/**
	 * 分支限定符.
	 */
	private byte[] branchQualifier;

	public TransactionXid() {
		globalTransactionId = uuidToByteArray(UUID.randomUUID());
		branchQualifier = uuidToByteArray(UUID.randomUUID());
	}

	public TransactionXid(byte[] globalTransactionId) {
		this.globalTransactionId = globalTransactionId;
		branchQualifier = uuidToByteArray(UUID.randomUUID());
	}

	public TransactionXid(byte[] globalTransactionId, byte[] branchQualifier) {
		this.globalTransactionId = globalTransactionId;
		this.branchQualifier = branchQualifier;
	}

	/**
	 * 获取 XID 的格式标识符部分。
	 */
	@Override
	public int getFormatId() {
		return formatId;
	}

	/**
	 * 获取 XID 的全局事务标识符部分作为字节数组。
	 */
	@Override
	public byte[] getGlobalTransactionId() {
		return globalTransactionId;
	}

	/**
	 * 获取 XID 的事务分支标识符部分作为字节数组。
	 */
	@Override
	public byte[] getBranchQualifier() {
		return branchQualifier;
	}

	@Override
	public String toString() {
		return UUID.nameUUIDFromBytes(globalTransactionId).toString() + "|"
				+ UUID.nameUUIDFromBytes(branchQualifier).toString();
	}

	/**
	 * 克隆事务ID.
	 */
	public TransactionXid clone() {
		byte[] cloneGlobalTransactionId = new byte[globalTransactionId.length];
		byte[] cloneBranchQualifier = new byte[branchQualifier.length];
		System.arraycopy(globalTransactionId, 0, cloneGlobalTransactionId, 0,
				globalTransactionId.length);
		System.arraycopy(branchQualifier, 0, cloneBranchQualifier, 0,
				branchQualifier.length);
		TransactionXid clone = new TransactionXid(cloneGlobalTransactionId,
				cloneBranchQualifier);
		return clone;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getFormatId();
		result = prime * result + Arrays.hashCode(branchQualifier);
		result = prime * result + Arrays.hashCode(globalTransactionId);
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}
		TransactionXid other = (TransactionXid) obj;
		if (this.getFormatId() != other.getFormatId()) {
			return false;
		} else if (Arrays.equals(branchQualifier, other.branchQualifier) == false) {
			return false;
		} else if (Arrays
				.equals(globalTransactionId, other.globalTransactionId) == false) {
			return false;
		}
		return true;
	}

	public static byte[] uuidToByteArray(UUID uuid) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return bb.array();
	}

	public static UUID byteArrayToUUID(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		long firstLong = bb.getLong();
		long secondLong = bb.getLong();
		return new UUID(firstLong, secondLong);
	}

	/**
	 * 测试用：
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] gt = UUID.randomUUID().toString().getBytes();
		byte[] bt = UUID.randomUUID().toString().getBytes();
		String str = UUID.nameUUIDFromBytes(gt).toString() + "|"
				+ UUID.nameUUIDFromBytes(bt).toString();
		System.out.println(str);
	}
}
