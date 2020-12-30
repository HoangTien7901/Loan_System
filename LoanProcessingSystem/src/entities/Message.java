package entities;

import java.util.Date;

public class Message {
	private int id;
	private String content;
	private int installment_id;
	private String type;
	private Date created_date;
	
	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String status;

	// getters/setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getInstallment_id() {
		return installment_id;
	}

	public void setInstallment_id(int installment_id) {
		this.installment_id = installment_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Message(int id, String content, int installment_id, String type, Date created_date, String status) {
		super();
		this.id = id;
		this.content = content;
		this.installment_id = installment_id;
		this.type = type;
		this.created_date = created_date;
		this.status = status;
	}

	public Message() {
		super();
	}
}
