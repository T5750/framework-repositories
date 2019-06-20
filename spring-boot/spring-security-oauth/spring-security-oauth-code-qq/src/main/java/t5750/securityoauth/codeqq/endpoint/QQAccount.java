package t5750.securityoauth.codeqq.endpoint;

import java.util.List;

public class QQAccount {
	private String qq;
	private String nickName;
	private String level;
	private List<QQAccount> fans;

	public QQAccount(String qq, String nickName, String level,
			List<QQAccount> fans) {
		this.qq = qq;
		this.nickName = nickName;
		this.level = level;
		this.fans = fans;
	}

	public QQAccount(String qq, String nickName, String level) {
		this.qq = qq;
		this.nickName = nickName;
		this.level = level;
	}

	public QQAccount() {
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<QQAccount> getFans() {
		return fans;
	}

	public void setFans(List<QQAccount> fans) {
		this.fans = fans;
	}
}
