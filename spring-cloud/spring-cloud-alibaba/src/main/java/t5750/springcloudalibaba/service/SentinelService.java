package t5750.springcloudalibaba.service;

public interface SentinelService {
	String testHandler(long s);

	String testFallback(long s);
}