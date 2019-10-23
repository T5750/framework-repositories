package t5750.dubbox.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import t5750.dubbox.service.bid.*;

@RestController
@RequestMapping("/bid")
public class BidController {
	@Reference
	private BidService bidService;

	@RequestMapping("/start")
	public BidResponse start() {
		BidRequest request = new BidRequest();
		Impression imp = new Impression();
		imp.setBidFloor(1.1);
		imp.setId("abc");
		List<Impression> imps = new ArrayList<Impression>(1);
		imps.add(imp);
		request.setImpressions(imps);
		Geo geo = new Geo();
		geo.setCity("beijing");
		geo.setCountry("china");
		geo.setLat(100.1f);
		geo.setLon(100.1f);
		Device device = new Device();
		device.setMake("apple");
		device.setOs("ios");
		device.setVersion("7.0");
		device.setLang("zh_CN");
		device.setModel("iphone");
		device.setGeo(geo);
		request.setDevice(device);
		BidResponse bidResponse = bidService.bid(request);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			System.out.println(bidService.bid(request).getId());
			System.out.println(
					"SUCCESS: got bid response id: " + bidResponse.getId());
		}
		System.out.println(">>>>> Total time consumed:"
				+ (System.currentTimeMillis() - start));
		return bidResponse;
	}

	@RequestMapping("/throwNPE")
	public void throwNPE() {
		try {
			bidService.throwNPE();
			System.out.println("ERROR: no exception found");
		} catch (NullPointerException e) {
			System.out.println("SUCCESS: caught exception " + e.getClass());
		}
	}
}
