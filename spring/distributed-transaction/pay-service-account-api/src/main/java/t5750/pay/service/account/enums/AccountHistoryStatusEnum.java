package t5750.pay.service.account.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类功能说明： 账户资金流水状态 @版本：V1.0
 */
public enum AccountHistoryStatusEnum {
	/**
	 * 预处理阶段
	 */
	TRYING("处理中"),
	/**
	 * 已确认的
	 */
	CONFORM("已确认"),
	/**
	 * 取消的
	 */
	CANCEL("取消");
	/** 描述 */
	private String label;

	private AccountHistoryStatusEnum(String label) {
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
		AccountHistoryStatusEnum[] val = AccountHistoryStatusEnum.values();
		for (AccountHistoryStatusEnum e : val) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("label", e.getLabel());
			map.put("name", e.name());
			list.add(map);
		}
		return list;
	}

	public static AccountHistoryStatusEnum getEnum(String name) {
		AccountHistoryStatusEnum resultEnum = null;
		AccountHistoryStatusEnum[] enumAry = AccountHistoryStatusEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].name().equals(name)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
}
