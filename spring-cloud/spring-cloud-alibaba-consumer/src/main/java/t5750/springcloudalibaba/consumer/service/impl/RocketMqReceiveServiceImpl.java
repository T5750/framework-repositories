package t5750.springcloudalibaba.consumer.service.impl;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import t5750.springcloudalibaba.consumer.model.Foo;
import t5750.springcloudalibaba.consumer.service.RocketMqReceiveService;

@Service
public class RocketMqReceiveServiceImpl implements RocketMqReceiveService {
	@Override
	@StreamListener("input1")
	public void receiveInput1(String receiveMsg) {
		System.out.println("input1 receive: " + receiveMsg);
	}

	@Override
	@StreamListener("input2")
	public void receiveInput2(String receiveMsg) {
		System.out.println("input2 receive: " + receiveMsg);
	}

	@Override
	@StreamListener("input3")
	public void receiveInput3(@Payload Foo foo) {
		System.out.println("input3 receive: " + foo);
	}

	@Override
	@StreamListener("input4")
	public void receiveTransactionalMsg(String transactionMsg) {
		System.out.println("input4 receive transaction msg: " + transactionMsg);
	}
}
