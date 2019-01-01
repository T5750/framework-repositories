package t5750.pay.common.core.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @功能说明: 支付类型枚举类
 * @版本:V1.0
 */
public enum PayTypeEnum {
	SCANPAY("WEIXIN", "扫码支付"), H5PAY("WEIXIN", "H5支付"), DIRECT_PAY("ALIPAY",
			"即时到账"), ALI_TEST("ALIPAY", "支付宝测试"), TEST_PAY("TEST_PAY",
					"测试模拟支付"), TEST_PAY_HTTP_CLIENT("TEST_PAY_HTTP_CLIENT",
							"测试模拟httpclient支付");
	/** 所属支付方式 */
	private String way;

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	/** 描述 */
	private String desc;

	private PayTypeEnum(String way, String desc) {
		this.desc = desc;
		this.way = way;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static Map<String, Map<String, Object>> toMap() {
		PayTypeEnum[] ary = PayTypeEnum.values();
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
		PayTypeEnum[] ary = PayTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("desc", ary[i].getDesc());
			map.put("name", ary[i].name());
			list.add(map);
		}
		return list;
	}

	public static PayTypeEnum getEnum(String name) {
		PayTypeEnum[] arry = PayTypeEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equalsIgnoreCase(name)) {
				return arry[i];
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getWayList(String way) {
		PayTypeEnum[] ary = PayTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			if (ary[i].way.equals(way)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("desc", ary[i].getDesc());
				map.put("name", ary[i].name());
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 取枚举的json字符串
	 *
	 * @return
	 */
	public static String getJsonStr() {
		PayTypeEnum[] enums = PayTypeEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (PayTypeEnum senum : enums) {
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
