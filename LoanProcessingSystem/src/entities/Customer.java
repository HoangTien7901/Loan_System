package entities;

import java.util.Date;

public class Customer {

	// properties
	private int id;
	private String loan_account_no;
	private Date created_date;
	private String first_name;
	private String last_name;
	private String email;
	private String id_no;
	private Date dob;
	private int organization_id;
	private String organization_name;
	private String phone_number;
	private double gross_salary;
	private String address;

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

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId_no() {
		return id_no;
	}

	public void setId_no(String id_no) {
		this.id_no = id_no;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public int getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(int organization_id) {
		this.organization_id = organization_id;
	}

	public String getOrganization_name() {
		return organization_name;
	}

	public void setOrganization_name(String organization_name) {
		this.organization_name = organization_name;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public double getGross_salary() {
		return gross_salary;
	}

	public void setGross_salary(double gross_salary) {
		this.gross_salary = gross_salary;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	// constructors
	public Customer(int id, String loan_account_no, Date created_date, String first_name, String last_name,
			String email, String id_no, Date dob, int organization_id, String organization_name, String phone_number,
			double gross_salary, String address) {
		super();
		this.id = id;
		this.loan_account_no = loan_account_no;
		this.created_date = created_date;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.id_no = id_no;
		this.dob = dob;
		this.organization_id = organization_id;
		this.organization_name = organization_name;
		this.phone_number = phone_number;
		this.gross_salary = gross_salary;
		this.address = address;
	}

	public Customer() {
		super();
	}
}
