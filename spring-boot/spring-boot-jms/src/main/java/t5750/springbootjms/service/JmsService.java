package t5750.springbootjms.service;

import t5750.springbootjms.model.Message;
import t5750.springbootjms.model.Order;
import t5750.springbootjms.model.Topic;

public interface JmsService {
	void sendMessage(Message message);

	void sendOrder(Order order);

	void sendTopic(Topic topic);
}
