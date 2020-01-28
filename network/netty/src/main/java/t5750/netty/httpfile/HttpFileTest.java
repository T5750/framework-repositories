package t5750.netty.httpfile;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import t5750.netty.util.NettyUtil;
import t5750.socket.util.GzipUtils;
import t5750.socket.util.HttpCallerUtils;

public class HttpFileTest {
	public static void main(String[] args) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		byte[] ret = HttpCallerUtils.getStream("http://" + NettyUtil.INET_HOST
				+ ":" + NettyUtil.PORT_8765 + NettyUtil.HTTPFILE_DEFAULT_URL
				+ "001.jpg", params);
		// byte[] ret =
		// HttpProxy.get("http://192.168.1.111:8765/images/006.jpg");
		// 写出文件
		String writePath = GzipUtils.WRITE_PATH;
		FileOutputStream fos = new FileOutputStream(writePath);
		fos.write(ret);
		fos.close();
	}
}
