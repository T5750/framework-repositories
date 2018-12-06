package t5750.util;

import java.io.IOException;
import java.util.Map;

/**
 * <B>系统名称：</B>通用系统功能<BR>
 * <B>模块名称：</B>网络交互功能<BR>
 * <B>中文类名：</B>HTTP协议调用器辅助类<BR>
 * <B>概要说明：</B><BR>
 *
 * @author 交通运输部规划研究院（邵彧）
 * @since 2013-7-18
 */
public final class HttpCallerUtils {
	/**
	 * <B>构造方法</B><BR>
	 */
	private HttpCallerUtils() {
	}

	/**
	 * <B>方法名称：</B>默认请求<BR>
	 * <B>概要说明：</B><BR>
	 *
	 * @param url
	 *            URL地址
	 * @param params
	 *            参数
	 * @return String 文本数据
	 * @throws IOException
	 *             输入输出异常
	 */
	public static String request(String url, Map<String, String> params)
			throws IOException {
		HttpCaller c = new HttpCaller(url, HttpCaller.REQUEST_METHOD_POST,
				params);
		return c.request(String.class);
	}

	/**
	 * <B>方法名称：</B>GET请求<BR>
	 * <B>概要说明：</B><BR>
	 *
	 * @param url
	 *            URL地址
	 * @param params
	 *            参数
	 * @return String 文本数据
	 * @throws IOException
	 *             输入输出异常
	 */
	public static String get(String url, Map<String, String> params)
			throws IOException {
		HttpCaller c = new HttpCaller(url, HttpCaller.REQUEST_METHOD_GET,
				params);
		return c.request(String.class);
	}

	public static byte[] getStream(String url, Map<String, String> params)
			throws IOException {
		HttpCaller c = new HttpCaller(url, HttpCaller.REQUEST_METHOD_GET, true,
				params);
		return c.request(Byte.class);
	}

	/**
	 * <B>方法名称：</B>POST请求<BR>
	 * <B>概要说明：</B><BR>
	 *
	 * @param url
	 *            URL地址
	 * @param params
	 *            参数
	 * @return String 文本数据
	 * @throws IOException
	 *             输入输出异常
	 */
	public static String post(String url, Map<String, String> params)
			throws IOException {
		HttpCaller c = new HttpCaller(url, HttpCaller.REQUEST_METHOD_POST,
				params);
		return c.request(String.class);
	}

	public static byte[] postStream(String url, Map<String, String> params)
			throws IOException {
		HttpCaller c = new HttpCaller(url, HttpCaller.REQUEST_METHOD_POST,
				true, params);
		return c.request(Byte.class);
	}

	/**
	 * <B>方法名称：</B>PUT请求<BR>
	 * <B>概要说明：</B><BR>
	 *
	 * @param url
	 *            URL地址
	 * @param params
	 *            参数
	 * @return String 文本数据
	 * @throws IOException
	 *             输入输出异常
	 */
	public static String put(String url, Map<String, String> params)
			throws IOException {
		HttpCaller c = new HttpCaller(url, HttpCaller.REQUEST_METHOD_PUT,
				params);
		return c.request(String.class);
	}

	/**
	 * <B>方法名称：</B>DELETE请求<BR>
	 * <B>概要说明：</B><BR>
	 *
	 * @param url
	 *            URL地址
	 * @param params
	 *            参数
	 * @return String 文本数据
	 * @throws IOException
	 *             输入输出异常
	 */
	public static String delete(String url, Map<String, String> params)
			throws IOException {
		HttpCaller c = new HttpCaller(url, HttpCaller.REQUEST_METHOD_DELETE,
				params);
		return c.request(String.class);
	}
}
