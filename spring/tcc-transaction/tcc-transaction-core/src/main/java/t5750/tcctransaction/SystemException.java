package t5750.tcctransaction;

/**
 * 系统异常
 */
public class SystemException extends RuntimeException {
	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable e) {
		super(e);
	}

	public SystemException(String message, Throwable e) {
		super(message, e);
	}
}
