package t5750.rest.jersey.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Address {
	private String city;
	private String street;

	public Address() {
	}

	public Address(String city, String street) {
		this.city = city;
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
}
