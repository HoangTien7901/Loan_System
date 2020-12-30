package entities;

import java.util.Date;

public class Loan {
	// properties
	private int id;
	private String loan_account_no;
	private double amount;
	private int loan_type_id;
	private Date disbursement_date;
	private Date term_date;
	private float interest_rate;
	private double monthly_installment;
	private double total_installment;
	private double balance_to_be_paid;
	private double total_fine;
	private String status;
	private int staff_id;

	// getters/setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoan_account_no() {
		return loan_account_no;
	}

	public void setLoan_account_no(String loan_account_no) {
		this.loan_account_no = loan_account_no;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getLoan_type_id() {
		return loan_type_id;
	}

	public void setLoan_type_id(int loan_type_id) {
		this.loan_type_id = loan_type_id;
	}

	public Date getDisbursement_date() {
		return disbursement_date;
	}

	public void setDisbursement_date(Date disbursement_date) {
		this.disbursement_date = disbursement_date;
	}

	public Date getTerm_date() {
		return term_date;
	}

	public void setTerm_date(Date term_date) {
		this.term_date = term_date;
	}

	public float getInterest_rate() {
		return interest_rate;
	}

	public void setInterest_rate(float interest_rate) {
		this.interest_rate = interest_rate;
	}

	public double getMonthly_installment() {
		return monthly_installment;
	}

	public void setMonthly_installment(double monthly_installment) {
		this.monthly_installment = monthly_installment;
	}

	public double getTotal_installment() {
		return total_installment;
	}

	public void setTotal_installment(double total_installment) {
		this.total_installment = total_installment;
	}

	public double getBalance_to_be_paid() {
		return balance_to_be_paid;
	}

	public void setBalance_to_be_paid(double balance_to_be_paid) {
		this.balance_to_be_paid = balance_to_be_paid;
	}

	public double getTotal_fine() {
		return total_fine;
	}

	public void setTotal_fine(double total_fine) {
		this.total_fine = total_fine;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

	// constructors
	public Loan(int id, String loan_account_no, double amount, int loan_type_id, Date disbursement_date, Date term_date,
			float interest_rate, double monthly_installment, double total_installment, double balance_to_be_paid,
			double total_fine, String status, int staff_id) {
		super();
		this.id = id;
		this.loan_account_no = loan_account_no;
		this.amount = amount;
		this.loan_type_id = loan_type_id;
		this.disbursement_date = disbursement_date;
		this.term_date = term_date;
		this.interest_rate = interest_rate;
		this.monthly_installment = monthly_installment;
		this.total_installment = total_installment;
		this.balance_to_be_paid = balance_to_be_paid;
		this.total_fine = total_fine;
		this.status = status;
		this.staff_id = staff_id;
	}

	public Loan() {
		super();
	}
}
