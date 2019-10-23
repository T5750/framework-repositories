package t5750.dubbox.service.bid;

import java.util.List;

public class BidResponse {
	private String id;
	private List<SeatBid> seatBids;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<SeatBid> getSeatBids() {
		return seatBids;
	}

	public void setSeatBids(List<SeatBid> seatBids) {
		this.seatBids = seatBids;
	}
}
