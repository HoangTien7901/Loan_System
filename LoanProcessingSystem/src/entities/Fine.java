package entities;

import java.util.Date;

public class Fine {
	private int id;
	private int installment_details_id;
	private double amount;
	private Date created_date;
	private Date payday;
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInstallment_details_id() {
		return installment_details_id;
	}

	public void setInstallment_details_id(int installment_details_id) {
		this.installment_details_id = installment_details_id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getPayday() {
		return payday;
	}

	public void setPayday(Date payday) {
		this.payday = payday;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Fine(int id, int installment_details_id, double amount, Date created_date, Date payday, String status) {
		super();
		this.id = id;
		this.installment_details_id = installment_details_id;
		this.amount = amount;
		this.created_date = created_date;
		this.payday = payday;
		this.status = status;
	}

	public Fine() {
		super();
	}
}
