package t5750.springboot.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import t5750.springboot.model.Message;
import t5750.springboot.util.Globals;

@Component
public class MessageReceiver {
	@JmsListener(destination = Globals.JMS_DESTINATION)
	public void receiveMessage(Message msg) {
		System.out.println("Received " + msg);
	}
}
