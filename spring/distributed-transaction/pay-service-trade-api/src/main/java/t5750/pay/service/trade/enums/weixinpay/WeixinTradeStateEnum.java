package t5750.pay.service.trade.enums.weixinpay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @功能说明: 微信交易状态枚举类
 * @版本:V1.0
 */
public enum WeixinTradeStateEnum {
	SUCCESS("成功"), FAIL("失败");
	/** 描述 */
	private String desc;

	private WeixinTradeStateEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static Map<String, Map<String, Object>> toMap() {
		WeixinTradeStateEnum[] ary = WeixinTradeStateEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = ary[num].name();
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		WeixinTradeStateEnum[] ary = WeixinTradeStateEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

	public static WeixinTradeStateEnum getEnum(String name) {
		WeixinTradeStateEnum[] arry = WeixinTradeStateEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equalsIgnoreCase(name)) {
				return arry[i];
			}
		}
		return null;
	}

	/**
	 * 取枚举的json字符串
	 *
	 * @return
	 */
	public static String getJsonStr() {
		WeixinTradeStateEnum[] enums = WeixinTradeStateEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (WeixinTradeStateEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'")
					.append(senum.getDesc()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}
}
