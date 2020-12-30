package entities;

import java.util.Date;

public class Staff {
	// properties
	private int id;
	private String first_name;
	private String last_name;
	private boolean gender;
	private String username;
	private String password;
	private Date dob;
	private int position_id;
	private String address;
	private double salary;
	private Date start_date;
	private Date end_date;
	private int department_id;
	private String status;

	// getters/setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public int getPosition_id() {
		return position_id;
	}

	public void setPosition_id(int position_id) {
		this.position_id = position_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public int getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// constructors
	public Staff(int id, String first_name, String last_name, boolean gender, String username, String password,
			Date dob, int position_id, String address, double salary, Date start_date, Date end_date, int department_id,
			String status) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.gender = gender;
		this.username = username;
		this.password = password;
		this.dob = dob;
		this.position_id = position_id;
		this.address = address;
		this.salary = salary;
		this.start_date = start_date;
		this.end_date = end_date;
		this.department_id = department_id;
		this.status = status;
	}

	public Staff() {
		super();
	}
}