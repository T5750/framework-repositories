package t5750.pay.service.account.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类功能说明： 账户资金变动方向 @版本：V1.0
 */
public enum AccountFundDirectionEnum {
	/**
	 * 加款
	 */
	ADD("加款"),
	/**
	 * 减款
	 */
	SUB("减款");
	/** 描述 */
	private String label;

	private AccountFundDirectionEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public static List<Map<String, Object>> getList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		AccountFundDirectionEnum[] val = AccountFundDirectionEnum.values();
		for (AccountFundDirectionEnum e : val) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("label", e.getLabel());
			map.put("name", e.name());
			list.add(map);
		}
		return list;
	}

	public static AccountFundDirectionEnum getEnum(String name) {
		AccountFundDirectionEnum resultEnum = null;
		AccountFundDirectionEnum[] enumAry = AccountFundDirectionEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].name().equals(name)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
}
