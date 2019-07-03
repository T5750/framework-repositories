package t5750.springcloudalibaba.util;

import org.springframework.cloud.alibaba.sentinel.rest.SentinelClientHttpResponse;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class ExceptionUtil {
	public static SentinelClientHttpResponse handleException(
			HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution, BlockException ex) {
		System.out.println("Oops: " + ex.getClass().getCanonicalName());
		return new SentinelClientHttpResponse("custom block info");
	}
}