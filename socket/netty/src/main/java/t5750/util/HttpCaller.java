package t5750.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <B>系统名称：</B>通用系统功能<BR>
 * <B>模块名称：</B>网络交互功能<BR>
 * <B>中文类名：</B>HTTP协议调用器<BR>
 * <B>概要说明：</B><BR>
 *
 * @author 交通运输部规划研究院（邵彧）
 * @since 2013-7-18
 */
public class HttpCaller {
	/** 请求方法：获取（GET） */
	public static final String REQUEST_METHOD_GET = "GET";
	/** 请求方法：提交（POST） */
	public static final String REQUEST_METHOD_POST = "POST";
	/** 请求方法：发送（PUT） */
	public static final String REQUEST_METHOD_PUT = "PUT";
	/** 请求方法：删除（DELETE） */
	public static final String REQUEST_METHOD_DELETE = "DELETE";
	/** HTTP协议调用器配置 */
	private HttpCallerConfig config;

	/**
	 * <B>构造方法</B><BR>
	 *
	 * @param url
	 *            URL地址
	 * @param method
	 *            请求方法
	 */
	public HttpCaller(String url, String method) {
		this.config = new HttpCallerConfig();
		this.config.setUrl(url);
		this.config.setMethod(method);
	}

	/**
	 * <B>构造方法</B><BR>
	 *
	 * @param url
	 *            URL地址
	 * @param params
	 *            参数
	 */
	public HttpCaller(String url, Map<String, String> params) {
		this.config = new HttpCallerConfig();
		this.config.setUrl(url);
		this.config.setMethod(REQUEST_METHOD_POST);
		this.config.setParams(params);
	}

	/**
	 * <B>构造方法</B><BR>
	 *
	 * @param url
	 *            URL地址
	 * @param method
	 *            请求方法
	 * @param params
	 *            参数
	 */
	public HttpCaller(String url, String method, Map<String, String> params) {
		this.config = new HttpCallerConfig();
		this.config.setUrl(url);
		this.config.setMethod(method);
		this.config.setParams(params);
	}

	/**
	 * <B>构造方法</B><BR>
	 *
	 * @param url
	 *            URL地址
	 * @param method
	 *            请求方法
	 * @param params
	 *            参数
	 */
	public HttpCaller(String url, String method, boolean isStream,
			Map<String, String> params) {
		this.config = new HttpCallerConfig();
		this.config.setUrl(url);
		this.config.setMethod(method);
		this.config.setStream(isStream);
		this.config.setParams(params);
	}

	/**
	 * <B>构造方法</B><BR>
	 *
	 * @param config
	 *            配置
	 */
	public HttpCaller(HttpCallerConfig config) {
		this.config = config;
	}

	/**
	 * <B>方法名称：</B>请求<BR>
	 * <B>概要说明：</B><BR>
	 * 
	 * @param <T>
	 *
	 * @return String 文本数据
	 * @throws IOException
	 *             输入输出异常
	 */
	public <T> T request(Object T) throws IOException {
		URL targetUrl = new URL(this.config.getUrl());
		HttpURLConnection conn = (HttpURLConnection) targetUrl.openConnection();
		conn.setRequestMethod(this.config.getMethod());
		conn.setConnectTimeout(this.config.getConnTimeout());
		conn.setReadTimeout(this.config.getReadTimeout());
		// conn.setRequestProperty("Content-type", "application/json");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.connect();
		write(conn);
		if (this.config.isStream()) {
			return (T) readStream(conn);
		}
		return (T) read(conn);
	}

	/**
	 * <B>方法名称：</B>读取<BR>
	 * <B>概要说明：</B><BR>
	 *
	 * @param conn
	 *            HTTP连接
	 * @return String 文本数据
	 * @throws IOException
	 *             输入输出异常
	 */
	private byte[] readStream(HttpURLConnection conn) throws IOException {
		InputStream is = null;
		ByteArrayOutputStream bos = null;
		try {
			is = conn.getInputStream();
			byte[] buf = new byte[this.config.getMaxBufferSize()];
			int num = -1;
			bos = new ByteArrayOutputStream();
			while ((num = is.read(buf, 0, buf.length)) != -1) {
				bos.write(buf, 0, num);
			}
		} finally {
			if (bos != null) {
				bos.close();
			}
			if (is != null) {
				is.close();
			}
		}
		return bos.toByteArray();
	}

	/**
	 * <B>方法名称：</B>读取<BR>
	 * <B>概要说明：</B><BR>
	 *
	 * @param conn
	 *            HTTP连接
	 * @return String 文本数据
	 * @throws IOException
	 *             输入输出异常
	 */
	private String read(HttpURLConnection conn) throws IOException {
		StringBuffer str = new StringBuffer();
		InputStream is = null;
		InputStreamReader reader = null;
		try {
			is = conn.getInputStream();
			reader = new InputStreamReader(is, this.config.getCharset());
			char[] buffer = new char[this.config.getMaxBufferSize()];
			int c = 0;
			while ((c = reader.read(buffer)) >= 0) {
				str.append(buffer, 0, c);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (is != null) {
				is.close();
			}
		}
		if (str.length() < 1) {
			return null;
		}
		return str.toString();
	}

	/**
	 * <B>方法名称：</B>写入<BR>
	 * <B>概要说明：</B><BR>
	 *
	 * @param conn
	 *            HTTP连接
	 * @throws IOException
	 *             输入输出异常
	 */
	private void write(HttpURLConnection conn) throws IOException {
		Map<String, String> params = this.config.getParams();
		if (params == null || params.isEmpty()) {
			return;
		}
		OutputStream out = null;
		OutputStreamWriter writer = null;
		try {
			out = conn.getOutputStream();
			writer = new OutputStreamWriter(out, this.config.getCharset());
			for (Entry<String, String> param : params.entrySet()) {
				writer.write("&");
				writer.write(param.getKey());
				writer.write("=");
				if (param.getValue() != null) {
					writer.write(param.getValue());
				}
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
}
