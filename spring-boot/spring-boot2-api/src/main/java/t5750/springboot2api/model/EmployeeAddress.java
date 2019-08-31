package t5750.springboot2api.model;

import java.io.Serializable;

public class EmployeeAddress implements Serializable {
	private static final long serialVersionUID = 9089911519863129623L;
	public String streetNo;
	public String houseNo;
	public String zipCode;

	public String getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "EmployeeAddress [streetNo=" + streetNo + ", houseNo=" + houseNo
				+ ", zipCode=" + zipCode + "]";
	}
}
