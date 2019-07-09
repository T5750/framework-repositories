package t5750.springcloudalibaba.service;

public interface RocketMqSenderService {
	void send(String msg) throws Exception;

	<T> void sendWithTags(T msg, String tag) throws Exception;

	<T> void sendObject(T msg, String tag) throws Exception;

	<T> void sendTransactionalMsg(T msg, int num) throws Exception;
}