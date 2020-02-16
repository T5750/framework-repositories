package t5750.network.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

public class MultiHttpClientConnThread extends Thread {
	private CloseableHttpClient client;
	private HttpGet get;
	private PoolingHttpClientConnectionManager connManager;

	public MultiHttpClientConnThread(CloseableHttpClient client, HttpGet get) {
		this.client = client;
		this.get = get;
	}

	public MultiHttpClientConnThread(CloseableHttpClient client, HttpGet get,
			PoolingHttpClientConnectionManager connManager) {
		this.client = client;
		this.get = get;
		this.connManager = connManager;
	}

	public void run() {
		try {
			HttpResponse response = client.execute(get);
			if (null != connManager) {
				System.out.println("Before - Leased Connections = "
						+ connManager.getTotalStats().getLeased());
				EntityUtils.consume(response.getEntity());
				System.out.println("After - Leased Connections = "
						+ connManager.getTotalStats().getLeased());
			} else {
				EntityUtils.consume(response.getEntity());
			}
		} catch (ClientProtocolException ex) {
		} catch (IOException ex) {
		}
	}
}