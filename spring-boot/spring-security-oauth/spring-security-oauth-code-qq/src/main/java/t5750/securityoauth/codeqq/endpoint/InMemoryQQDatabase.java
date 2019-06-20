package t5750.securityoauth.codeqq.endpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryQQDatabase {
	public static Map<String, QQAccount> database;
	static {
		database = new HashMap<>();
		List<QQAccount> fans = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			fans.add(new QQAccount(("1000000" + i), ("fan" + i), (i + "")));
		}
		database.put("250577914", new QQAccount("250577914", "鱼非渔", "54", fans));
		List<QQAccount> fansTwo = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			fansTwo.add(new QQAccount(("2000000" + i), ("fan" + i), (i + "")));
		}
		database.put("920129126", new QQAccount("920129126", "下一秒升华", "31",
				fansTwo));
	}
}
