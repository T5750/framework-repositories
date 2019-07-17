package t5750.springbootjms.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class Order implements Serializable {
	@NotNull
	private Long id;
	@NotNull
	private String content;
	private Date timestamp;

	public Order() {
	}

	public Order(String content, Date timestamp) {
		this.content = content;
		this.timestamp = timestamp;
	}

	public Order(Long id, String content, Date timestamp) {
		this.id = id;
		this.content = content;
		this.timestamp = timestamp;
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Order{id=" + id + ", content='" + content + '\''
				+ ", timestamp=" + timestamp + '}';
	}
}