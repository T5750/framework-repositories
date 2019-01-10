package t5750.pay.service.account.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @功能说明:
 * @版本:V1.0
 */
public class AccountConfigUtil {
	private static final Log LOG = LogFactory.getLog(AccountConfigUtil.class);
	/**
	 * 通过静态代码块读取上传文件的验证格式配置文件,静态代码块只执行一次(单例)
	 */
	private static Properties properties = new Properties();

	private AccountConfigUtil() {
	}

	// 通过类装载器装载进来
	static {
		try {
			// 从类路径下读取属性文件
			properties.load(AccountConfigUtil.class.getClassLoader()
					.getResourceAsStream("account_config.properties"));
		} catch (IOException e) {
			LOG.error(e);
		}
	}

	/**
	 * 函数功能说明 ：读取配置项
	 *
	 * @参数： @return void @throws
	 */
	public static String readConfig(String key) {
		return (String) properties.get(key);
	}
}
