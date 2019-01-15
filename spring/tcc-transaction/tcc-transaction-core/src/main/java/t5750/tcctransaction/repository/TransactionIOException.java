package t5750.tcctransaction.repository;

/**
 * 事务IO异常.
 */
public class TransactionIOException extends RuntimeException {
	private static final long serialVersionUID = 6508064607297986329L;

	public TransactionIOException(String message) {
		super(message);
	}

	public TransactionIOException(Throwable e) {
		super(e);
	}
}
