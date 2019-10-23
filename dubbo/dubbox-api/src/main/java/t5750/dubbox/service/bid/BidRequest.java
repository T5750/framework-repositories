package t5750.dubbox.service.bid;

import java.util.List;

public class BidRequest {
	private String id;
	private Device device;
	private List<Impression> impressions;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public List<Impression> getImpressions() {
		return impressions;
	}

	public void setImpressions(List<Impression> impressions) {
		this.impressions = impressions;
	}
}
