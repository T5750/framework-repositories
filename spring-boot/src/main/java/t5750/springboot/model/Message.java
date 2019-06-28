package t5750.springboot.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String content;
	private Date date;

	public Message() {
	}

	public Message(Long id, String content, Date date) {
		super();
		this.id = id;
		this.content = content;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", content=" + content + ", date=" + date
				+ "]";
	}
}