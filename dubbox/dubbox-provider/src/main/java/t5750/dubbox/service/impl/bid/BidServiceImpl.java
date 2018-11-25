package t5750.dubbox.service.impl.bid;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;

import t5750.dubbox.service.bid.BidRequest;
import t5750.dubbox.service.bid.BidResponse;
import t5750.dubbox.service.bid.BidService;
import t5750.dubbox.service.bid.SeatBid;

@Service
public class BidServiceImpl implements BidService {
	public BidResponse bid(BidRequest request) {
		BidResponse response = new BidResponse();
		response.setId("abc");
		SeatBid seatBid = new SeatBid();
		seatBid.setGroup("group");
		seatBid.setSeat("seat");
		List<SeatBid> seatBids = new ArrayList<SeatBid>(1);
		seatBids.add(seatBid);
		response.setSeatBids(seatBids);
		return response;
	}

	public void throwNPE() throws NullPointerException {
		throw new NullPointerException();
	}
}