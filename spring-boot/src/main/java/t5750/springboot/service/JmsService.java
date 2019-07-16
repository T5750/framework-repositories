package t5750.springboot.service;

import t5750.springboot.model.Message;
import t5750.springboot.model.Order;
import t5750.springboot.model.Topic;

public interface JmsService {
	void sendMessage(Message message);

	void sendOrder(Order order);

	void sendTopic(Topic topic);
}
