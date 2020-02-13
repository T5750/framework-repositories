package t5750.springboot.module.miniprogram.util;

public class MiniUtil {
	public static final String MINI_URL = "https://api.weixin.qq.com/";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String WITH_TOKEN = "?" + ACCESS_TOKEN + "=";
	public static final String MINI_LOGIN = MINI_URL + "sns/jscode2session";
	public static final String MINI_ACCESS_TOKEN = MINI_URL + "cgi-bin/token";
	public static final String MINI_USER_LOG = MINI_URL
			+ "wxaapi/userlog/userlog_search";
	public static final String GET_MONTHLY_RETAIN = MINI_URL
			+ "datacube/getweanalysisappidmonthlyretaininfo" + WITH_TOKEN;
	public static final String GET_DAILY_SUMMARY = MINI_URL
			+ "datacube/getweanalysisappiddailysummarytrend" + WITH_TOKEN;
	public static final String GET_MONTHLY_VISIT_TREND = MINI_URL
			+ "datacube/getweanalysisappidmonthlyvisittrend" + WITH_TOKEN;
	public static final String GET_USER_PORTRAIT = MINI_URL
			+ "datacube/getweanalysisappiduserportrait" + WITH_TOKEN;
	public static final String GET_VISIT_DISTRIBUTION = MINI_URL
			+ "datacube/getweanalysisappidvisitdistribution" + WITH_TOKEN;
	public static final String GET_VISIT_PAGE = MINI_URL
			+ "datacube/getweanalysisappidvisitpage" + WITH_TOKEN;
	public static final String MSG_SEC_CHECK = MINI_URL + "wxa/msg_sec_check"
			+ WITH_TOKEN;
	public static final String CREATE_QR_CODE = MINI_URL
			+ "cgi-bin/wxaapp/createwxaqrcode" + WITH_TOKEN;
	public static final String AUTHORIZATION_CODE = "authorization_code";
	public static final String CLIENT_CREDENTIAL = "client_credential";
	public static final String OPENID = "openid";
}