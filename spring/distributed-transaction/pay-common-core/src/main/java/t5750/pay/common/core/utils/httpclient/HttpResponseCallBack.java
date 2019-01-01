package t5750.pay.common.core.utils.httpclient;

import java.io.IOException;
import java.io.InputStream;

public interface HttpResponseCallBack {
	public void processResponse(InputStream responseBody) throws IOException;
}
