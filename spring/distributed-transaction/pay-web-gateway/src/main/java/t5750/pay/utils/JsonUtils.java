package t5750.pay.utils;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * @功能说明: JsonUtils工具类,用来通过流的方式将Json数据写回前端
 * @版本:V1.0
 */
public class JsonUtils {
	private static final Log LOG = LogFactory.getLog(JsonUtils.class);

	/**
	 * 构造函数私有化
	 */
	private JsonUtils() {
	}

	/**
	 * 将请求中的Json流转换成Json对象
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	public static JSONObject requestJson(
			HttpServletRequest httpServletRequest) {
		StringBuffer buffer = new StringBuffer();
		String line = null;
		JSONObject jsonObject = null;
		try {
			BufferedReader reader = httpServletRequest.getReader();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (Exception e) {
			LOG.error(e);
		}
		return jsonObject;
	}

	/**
	 * 将Map内的参数,转换成Json实体,并写出
	 * 
	 * @param response
	 * @param object
	 * @throws IOException
	 */
	public static void responseJson(HttpServletResponse response, Object object)
			throws IOException {
		Object toJSON = JSONObject.toJSON(object);
		try {
			response.getWriter().write(toJSON.toString());
		} catch (IOException e) {
			LOG.error(e);
		}
	}
}
