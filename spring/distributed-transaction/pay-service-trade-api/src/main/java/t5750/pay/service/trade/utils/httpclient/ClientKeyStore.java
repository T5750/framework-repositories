package t5750.pay.service.trade.utils.httpclient;

import javax.net.ssl.KeyManagerFactory;

public class ClientKeyStore {
	private KeyManagerFactory keyManagerFactory;

	ClientKeyStore(KeyManagerFactory keyManagerFactory) {
		this.keyManagerFactory = keyManagerFactory;
	}

	KeyManagerFactory getKeyManagerFactory() {
		return keyManagerFactory;
	}
}
