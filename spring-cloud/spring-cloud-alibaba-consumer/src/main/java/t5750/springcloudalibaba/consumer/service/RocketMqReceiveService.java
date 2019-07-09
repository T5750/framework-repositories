package t5750.springcloudalibaba.consumer.service;

import t5750.springcloudalibaba.consumer.model.Foo;

public interface RocketMqReceiveService {
	void receiveInput1(String receiveMsg);

	void receiveInput2(String receiveMsg);

	void receiveInput3(Foo foo);

	void receiveTransactionalMsg(String transactionMsg);
}
