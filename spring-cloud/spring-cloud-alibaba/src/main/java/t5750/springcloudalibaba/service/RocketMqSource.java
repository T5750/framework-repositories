package t5750.springcloudalibaba.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface RocketMqSource {
	@Output("output1")
	MessageChannel output1();

	@Output("output2")
	MessageChannel output2();
}