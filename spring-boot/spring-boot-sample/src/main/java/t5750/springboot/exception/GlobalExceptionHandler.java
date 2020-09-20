package t5750.springboot.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static Logger LOGGER = Logger
			.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = ProductNotfoundException.class)
	public ResponseEntity<Object> exception(
			ProductNotfoundException exception) {
		LOGGER.warn("Product not found");
		return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
	}
}