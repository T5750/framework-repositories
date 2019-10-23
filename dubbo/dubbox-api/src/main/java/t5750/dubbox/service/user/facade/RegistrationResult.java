package t5750.dubbox.service.user.facade;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegistrationResult implements Serializable {
	private Long id;

	public RegistrationResult() {
	}

	public RegistrationResult(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
