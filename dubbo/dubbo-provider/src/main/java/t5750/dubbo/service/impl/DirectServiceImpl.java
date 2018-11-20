package t5750.dubbo.service.impl;

import t5750.dubbo.service.DirectService;

import com.alibaba.dubbo.config.annotation.Service;

@Service(version = "1.0.0")
public class DirectServiceImpl implements DirectService {
	@Override
	public String direct() {
		return "Direct Service";
	}
}
