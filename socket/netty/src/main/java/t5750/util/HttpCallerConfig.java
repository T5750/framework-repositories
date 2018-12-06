package t5750.util;

import java.io.Serializable;
import java.util.Map;

/**
 * <B>系统名称：</B>通用系统功能<BR>
 * <B>模块名称：</B>网络交互功能<BR>
 * <B>中文类名：</B>HTTP协议调用器配置<BR>
 * <B>概要说明：</B><BR>
 *
 * @author 交通运输部规划研究院（邵彧）
 * @since 2013-7-18
 */
public class HttpCallerConfig implements Serializable {
	/** 默认版本唯一标识 */
	private static final long serialVersionUID = 1L;
	/** 默认配置：字符集 */
	public static final String DEFAULT_CONFIG_CHARSET = "UTF-8";
	/** 默认配置：连接超时（毫秒） */
	public static final int DEFAULT_CONFIG_CONN_TIMEOUT = 30000;
	/** 默认配置：读取超时（毫秒） */
	public static final int DEFAULT_CONFIG_READ_TIMEOUT = 30000;
	/** 默认配置：最大缓冲大小（字符/字节数） */
	public static final int DEFAULT_CONFIG_MAX_BUFFER_SIZE = 500;
	/** URL地址 */
	private String url;
	/** 请求方法 */
	private String method;
	/** 字符集 */
	private String charset = DEFAULT_CONFIG_CHARSET;
	/** 连接超时（毫秒） */
	private int connTimeout = DEFAULT_CONFIG_CONN_TIMEOUT;
	/** 读取超时（毫秒） */
	private int readTimeout = DEFAULT_CONFIG_READ_TIMEOUT;
	/** 最大缓冲大小（字符数） */
	private int maxBufferSize = DEFAULT_CONFIG_MAX_BUFFER_SIZE;
	/** 参数 */
	private Map<String, String> params;
	/** 是否获取为流数据信息 */
	private boolean isStream;

	/**
	 * <B>取得：</B>URL地址<BR>
	 *
	 * @return String URL地址
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * <B>设定：</B>URL地址<BR>
	 *
	 * @param url
	 *            URL地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * <B>取得：</B>请求方法<BR>
	 *
	 * @return String 请求方法
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * <B>设定：</B>请求方法<BR>
	 *
	 * @param method
	 *            请求方法
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * <B>取得：</B>字符集<BR>
	 *
	 * @return String 字符集
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * <B>设定：</B>字符集<BR>
	 *
	 * @param charset
	 *            字符集
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * <B>取得：</B>连接超时（毫秒）<BR>
	 *
	 * @return int 连接超时（毫秒）
	 */
	public int getConnTimeout() {
		return connTimeout;
	}

	/**
	 * <B>设定：</B>连接超时（毫秒）<BR>
	 *
	 * @param connTimeout
	 *            连接超时（毫秒）
	 */
	public void setConnTimeout(int connTimeout) {
		this.connTimeout = connTimeout;
	}

	/**
	 * <B>取得：</B>读取超时（毫秒）<BR>
	 *
	 * @return int 读取超时（毫秒）
	 */
	public int getReadTimeout() {
		return readTimeout;
	}

	/**
	 * <B>设定：</B>读取超时（毫秒）<BR>
	 *
	 * @param readTimeout
	 *            读取超时（毫秒）
	 */
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	/**
	 * <B>取得：</B>最大缓冲大小（字符/字节数）<BR>
	 *
	 * @return int 最大缓冲大小（字符/字节数）
	 */
	public int getMaxBufferSize() {
		return maxBufferSize;
	}

	/**
	 * <B>设定：</B>最大缓冲大小（字符数）<BR>
	 *
	 * @param maxBufferSize
	 *            最大缓冲大小（字符/字节数）
	 */
	public void setMaxBufferSize(int maxBufferSize) {
		this.maxBufferSize = maxBufferSize;
	}

	/**
	 * <B>取得：</B>参数<BR>
	 *
	 * @return Map<String, String> 参数
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * <B>设定：</B>参数<BR>
	 *
	 * @param params
	 *            参数
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public boolean isStream() {
		return isStream;
	}

	public void setStream(boolean isStream) {
		this.isStream = isStream;
	}

	/**
	 * <B>方法名称：</B>生成文本<BR>
	 * <B>概要说明：</B><BR>
	 *
	 * @return String 文本
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HttpCallerConfig [url=" + url + ", method=" + method
				+ ", charset=" + charset + ", connTimeout=" + connTimeout
				+ ", readTimeout=" + readTimeout + ", maxBufferSize="
				+ maxBufferSize + ", params="
				+ (params == null ? null : params.size()) + "]";
	}
}
