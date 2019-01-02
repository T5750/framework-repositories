package t5750.pay.service.trade.utils.httpclient;

//package t5750.pay.facade.utils.httpclient;
//
//import java.util.Collection;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public class SimpleHttpParam {
//	
//	/**
//	 * 请求地址
//	 */
//	private String url;
//	/**
//	 * 请求参数
//	 */
//	private Map<String,Object> parameters = new LinkedHashMap<String,Object>();
//	/**
//	 * HTTP请求方式(GET或者POST)
//	 */
//	private String method = SimpleHttpUtils.HTTP_METHOD_GET;
//	/**
//	 * 请求字符集
//	 */
//	private String charSet = SimpleHttpUtils.DEFAULT_CHARSET;
//	/**
//	 * 是否验证服务端证书
//	 */
//	private boolean sslVerify = false;
//	/**
//	 * 最大返回的字节数
//	 */
//	private int maxResultSize = SimpleHttpUtils.MAX_FETCHSIZE;
//	/**
//	 * 请求头
//	 */
//	private Map<String,Object> headers = new LinkedHashMap<String,Object>();
//	/**
//	 * 读超时时间
//	 */
//	private int readTimeout = SimpleHttpUtils.DEFAULT_READ_TIMEOUT;
//	/**
//	 * 连接超时时间
//	 */
//	private int connectTimeout = SimpleHttpUtils.DEFAULT_CONNECT_TIMEOUT;
//	/**
//	 * 如果状态码不等于200，是否不读取返回的字节流
//	 */
//	private boolean ignoreContentIfUnsuccess = true;
//	/**
//	 * 请求报文体，只有当parameters为空，且请求方式为post时才有效
//	 */
//	private String postData;
//	/**
//	 * 客户端本地证书
//	 */
//	private ClientKeyStore clientKeyStore;
//	/**
//	 * 客户端信任的证书
//	 */
//	private TrustKeyStore TrustKeyStore;
//	/**
//	 * 如果需要验证服务端证书，是否验证host与证书匹配
//	 */
//	private boolean hostnameVerify = false;
//	
//	public SimpleHttpParam(String url){
//		this.url = url;
//	}
//	public String getUrl() {
//		return url;
//	}
//	public Map getParameters() {
//		return parameters;
//	}
//	public void addParameter(String key, String value){
//		this.parameters.put(key, value);
//	}
//	public void addParameters(String key, Collection<String> values){
//		this.parameters.put(key, values);
//	}
//	public void setParameters(Map _parameters) {
//		this.parameters.putAll(_parameters);
//	}
//	public String getMethod() {
//		return method;
//	}
//	public void setMethod(String method) {
//		this.method = method;
//	}
//	public String getCharSet() {
//		return charSet;
//	}
//	public void setCharSet(String charSet) {
//		this.charSet = charSet;
//	}
//	public boolean isSslVerify() {
//		return sslVerify;
//	}
//	public void setSslVerify(boolean sslVerify) {
//		this.sslVerify = sslVerify;
//	}
//	public int getMaxResultSize() {
//		return maxResultSize;
//	}
//	public void setMaxResultSize(int maxResultSize) {
//		this.maxResultSize = maxResultSize;
//	}
//	public Map getHeaders() {
//		return headers;
//	}
//	public void addHeader(String key, String value){
//		this.headers.put(key, value);
//	}
//	public void addHeaders(String key, Collection<String> values){
//		this.headers.put(key, values);
//	}
//	public void setHeaders(Map _headers) {
//		this.headers.putAll(_headers);
//	}
//	public int getReadTimeout() {
//		return readTimeout;
//	}
//	public void setReadTimeout(int readTimeout) {
//		this.readTimeout = readTimeout;
//	}
//	public int getConnectTimeout() {
//		return connectTimeout;
//	}
//	public void setConnectTimeout(int connectTimeout) {
//		this.connectTimeout = connectTimeout;
//	}
//	public boolean isIgnoreContentIfUnsuccess() {
//		return ignoreContentIfUnsuccess;
//	}
//	public void setIgnoreContentIfUnsuccess(boolean ignoreContentIfUnsuccess) {
//		this.ignoreContentIfUnsuccess = ignoreContentIfUnsuccess;
//	}
//	public String getPostData() {
//		return postData;
//	}
//	public void setPostData(String postData) {
//		this.postData = postData;
//	}
//	
//	public ClientKeyStore getClientKeyStore() {
//		return clientKeyStore;
//	}
//	public void setClientKeyStore(ClientKeyStore clientKeyStore) {
//		this.clientKeyStore = clientKeyStore;
//	}
//	public TrustKeyStore getTrustKeyStore() {
//		return TrustKeyStore;
//	}
//	public void setTrustKeyStore(TrustKeyStore trustKeyStore) {
//		TrustKeyStore = trustKeyStore;
//	}
//	public boolean isHostnameVerify() {
//		return hostnameVerify;
//	}
//	public void setHostnameVerify(boolean hostnameVerify) {
//		this.hostnameVerify = hostnameVerify;
//	}
//
// }
