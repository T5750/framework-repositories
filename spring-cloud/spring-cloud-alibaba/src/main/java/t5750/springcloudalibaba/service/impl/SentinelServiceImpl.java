package t5750.springcloudalibaba.service.impl;

import org.springframework.stereotype.Service;

import t5750.springcloudalibaba.service.SentinelService;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

@Service
public class SentinelServiceImpl implements SentinelService {
	/**
	 * 原函数
	 */
	@Override
	@SentinelResource(value = "testHandler", blockHandler = "exceptionHandler")
	public String testHandler(long s) {
		if (s < 0) {
			throw new IllegalArgumentException("invalid arg");
		}
		String result = String.format("Test Handler at %d", s);
		return result;
	}

	/**
	 * Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.<br/>
	 * https://github.com/alibaba/Sentinel/wiki/%E6%B3%A8%E8%A7%A3%E6%94%AF%E6%8
	 * C%81<br/>
	 * 注：1.6.0 之前的版本 fallback 函数只针对降级异常（DegradeException）进行处理，不能针对业务异常进行处理。<br/>
	 * 特别地，若 blockHandler 和 fallback 都进行了配置，则被限流降级而抛出 BlockException 时只会进入
	 * blockHandler 处理逻辑
	 */
	public String handleFallback(long s) {// , Throwable ex
		// Do some log here.
		// ex.printStackTrace();
		return "Oops, error occurred at " + s;
	}

	/**
	 * Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
	 */
	public String exceptionHandler(long s, BlockException ex) {
		// Do some log here.
		ex.printStackTrace();
		return "Oops, error occurred at " + s;
	}

	@Override
	@SentinelResource(value = "testFallback", fallback = "handleFallback")
	public String testFallback(long s) {
		throw new RuntimeException("testFallback");
	}
}