package t5750.curator.ext;

import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * In a real application, the Service payload will most likely be more detailed
 * than this. But, this gives a good example.<br/>
 * InstanceDetails定义了服务实例的基本信息,实际中可能会定义更详细的信息。
 */
@JsonRootName("details")
public class InstanceDetails {
	private String description;

	public InstanceDetails() {
		this("");
	}

	public InstanceDetails(String description) {
		this.description = description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}