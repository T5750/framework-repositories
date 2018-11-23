package t5750.redis.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * HelloWorldHystrixCommand要使用Hystrix功能<br/>
 * https://www.jianshu.com/p/b9af028efebb
 */
public class CommandHelloWorld extends HystrixCommand<String> {
	private final String name;

	public CommandHelloWorld(String name) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name = name;
	}

	@Override
	protected String run() {
		// a real example would do work like a network call here
		return "Hello " + name + "!";
	}

	public static void main(String[] args) {
		// 调用程序对HelloWorldHystrixCommand实例化，执行execute()即触发HelloWorldHystrixCommand.run()的执行
		String result = new CommandHelloWorld("World").execute().toString();
		System.out.println(result);
	}
}