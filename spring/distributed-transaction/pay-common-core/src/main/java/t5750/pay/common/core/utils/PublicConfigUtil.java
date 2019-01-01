package t5750.pay.common.core.utils;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 平台公共配置
 */
public class PublicConfigUtil {
	private static final Log LOG = LogFactory.getLog(PublicConfigUtil.class);
	/**
	 * 通过静态代码块读取上传文件的验证格式配置文件,静态代码块只执行一次(单例)
	 */
	private static Properties properties = new Properties();

	private PublicConfigUtil() {
	}

	// 通过类装载器装载进来
	static {
		try {
			// 从类路径下读取属性文件
			properties.load(PublicConfigUtil.class.getClassLoader()
					.getResourceAsStream("public_system.properties"));
		} catch (Exception e) {
			LOG.error("加载public_system.properties文件异常" + e);
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
