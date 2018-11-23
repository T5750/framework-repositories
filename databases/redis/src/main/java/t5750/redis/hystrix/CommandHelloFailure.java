package t5750.redis.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * fallback（降级）<br/>
 * https://github.com/Netflix/Hystrix/wiki/How-To-Use#Fallback
 */
public class CommandHelloFailure extends HystrixCommand<String> {
	private final String name;

	public CommandHelloFailure(String name) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name = name;
	}

	@Override
	protected String run() {
		throw new RuntimeException("this command always fails");
	}

	@Override
	protected String getFallback() {
		return "Hello Failure " + name + "!";
	}

	public static void main(String[] args) {
		String result = new CommandHelloFailure("World").execute().toString();
		System.out.println(result);
	}
}