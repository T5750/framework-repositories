package t5750.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import t5750.springboot.util.Globals;

@Controller
public class KafkaMessageController {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@RequestMapping("/kafkaMessage")
	@ResponseBody
	public String kafkaMessage() {
		sendMessage("Hi Welcome to Spring For Apache Kafka");
		return "kafkaMessage";
	}

	public void sendMessage(String msg) {
		kafkaTemplate.send(Globals.KAFKA_TOPIC, msg);
	}

	// , groupId = "group-id"
	@KafkaListener(topics = Globals.KAFKA_TOPIC, group = Globals.KAFKA_GROUP_ID)
	public void listen(String message) {
		System.out.println("Received Messasge in group - "
				+ Globals.KAFKA_GROUP_ID + ": " + message);
	}
}