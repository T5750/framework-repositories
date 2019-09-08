package t5750.springboot2.service.impl;

import java.util.Random;

import org.springframework.stereotype.Service;

import t5750.springboot2.exception.RemoteServiceNotAvailableException;
import t5750.springboot2.service.BackendAdapter;

@Service
public class BackendAdapterImpl implements BackendAdapter {
	@Override
	public String getBackendResponse(boolean simulateretry,
			boolean simulateretryfallback) {
		if (simulateretry) {
			System.out.println(
					"Simulateretry is true, so try to simulate exception scenerio.");
			if (simulateretryfallback) {
				throw new RemoteServiceNotAvailableException(
						"Don't worry!! Just Simulated for Spring-retry..Must fallback as all retry will get exception!!!");
			}
			int random = new Random().nextInt(4);
			System.out.println("Random Number : " + random);
			if (random % 2 == 0) {
				throw new RemoteServiceNotAvailableException(
						"Don't worry!! Just Simulated for Spring-retry..");
			}
		}
		return "Hello from Remote Backend!!!";
	}

	@Override
	public String getBackendResponseFallback(RuntimeException e) {
		System.out
				.println("All retries completed, so Fallback method called!!!");
		return "All retries completed, so Fallback method called!!!";
	}
}
