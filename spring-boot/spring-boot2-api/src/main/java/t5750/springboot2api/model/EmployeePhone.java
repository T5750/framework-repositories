package t5750.springboot2api.model;

import java.io.Serializable;
import java.util.List;

public class EmployeePhone implements Serializable {
	private static final long serialVersionUID = 3705958972000701963L;
	public List<String> phoneNumbers;

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	@Override
	public String toString() {
		return "EmployeeAddress [phoneNumbers=" + phoneNumbers + "]";
	}
}
