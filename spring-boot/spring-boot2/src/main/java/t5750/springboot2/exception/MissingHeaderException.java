package t5750.springboot2.exception;

public class MissingHeaderException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MissingHeaderException(String exception) {
		super(exception);
	}
}
