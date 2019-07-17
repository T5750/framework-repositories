package t5750.springbootjms.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class OrderValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Order.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Order order = (Order) target;
		if (order == null) {
			errors.reject(null, "order cannot be null");
		} else {
			if (order.getId() == null) {
				errors.rejectValue("id", null, "id cannot be null");
			}
			if (order.getContent() == null) {
				errors.rejectValue("content", null, "content cannot be null");
			}
			if (order.getTimestamp() == null) {
				errors.rejectValue("timestamp", null,
						"timestamp cannot be null");
			}
		}
	}
}
