package entities;

import java.util.Date;

public class Installment {
	private int id;
	private int loan_details_id;
	private Date payday;
	private Date installment_term;
	private double amount;
	private String status;

	// getters/ setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLoan_details_id() {
		return loan_details_id;
	}

	public void setLoan_details_id(int loan_details_id) {
		this.loan_details_id = loan_details_id;
	}

	public Date getPayday() {
		return payday;
	}

	public void setPayday(Date payday) {
		this.payday = payday;
	}

	public Date getInstallment_term() {
		return installment_term;
	}

	public void setInstallment_term(Date installment_term) {
		this.installment_term = installment_term;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// constructors
	public Installment(int id, int loan_details_id, Date payday, Date installment_term, double amount, String status) {
		super();
		this.id = id;
		this.loan_details_id = loan_details_id;
		this.payday = payday;
		this.installment_term = installment_term;
		this.amount = amount;
		this.status = status;
	}

	public Installment() {
		super();
	}
}
