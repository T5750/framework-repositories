package t5750.springboot2api.model;

import java.io.Serializable;

public class EmployeeName implements Serializable {
	private static final long serialVersionUID = -1773599508061743940L;
	public String firstName;
	public String lastName;

	public EmployeeName(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public EmployeeName() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "EmployeeName [firstName=" + firstName + ", lastName=" + lastName
				+ "]";
	}
}
