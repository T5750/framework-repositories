package t5750.redis.hystrix;

import java.util.Iterator;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;

/**
 * https://github.com/Netflix/Hystrix/wiki/How-To-Use <br/>
 * An equivalent Hello World solution that uses a HystrixObservableCommand
 * instead of a HystrixCommand would involve overriding the construct method as
 * follows:
 *
 */
public class CommandHelloWorldObservable extends
		HystrixObservableCommand<String> {
	private final String name;

	public CommandHelloWorldObservable(String name) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name = name;
	}

	@Override
	protected Observable<String> construct() {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> observer) {
				try {
					if (!observer.isUnsubscribed()) {
						// a real example would do work like a network call here
						observer.onNext("Hello");
						observer.onNext(name + "!");
						observer.onCompleted();
					}
				} catch (Exception e) {
					observer.onError(e);
				}
			}
		}).subscribeOn(Schedulers.io());
	}

	public static void main(String[] args) {
		Observable<String> observable = new CommandHelloWorldObservable("World")
				.observe();
		Iterator<String> iterator = observable.toBlocking().getIterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
}