package t5750.tcctransaction;

/**
 * 取消异常.
 */
public class CancellingException extends RuntimeException {
	public CancellingException(Throwable cause) {
		super(cause);
	}
}
