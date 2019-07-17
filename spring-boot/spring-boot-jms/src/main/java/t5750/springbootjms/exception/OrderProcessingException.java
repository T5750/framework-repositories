package t5750.springbootjms.exception;

public class OrderProcessingException extends RuntimeException {
	public OrderProcessingException(String message) {
		super(message);
	}
}