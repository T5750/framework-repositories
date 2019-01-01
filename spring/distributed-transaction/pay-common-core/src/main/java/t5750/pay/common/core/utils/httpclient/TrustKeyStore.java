package t5750.pay.common.core.utils.httpclient;

import javax.net.ssl.TrustManagerFactory;

public class TrustKeyStore {
	private TrustManagerFactory trustManagerFactory;

	TrustKeyStore(TrustManagerFactory trustManagerFactory) {
		this.trustManagerFactory = trustManagerFactory;
	}

	TrustManagerFactory getTrustManagerFactory() {
		return trustManagerFactory;
	}
}
