package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entities.Customer;

public class CustomerModel {
	public static List<Customer> findAll() {
		List<Customer> customers_list = new ArrayList<Customer>();
		try {
			PreparedStatement prepareStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `customer_profiles`");
			ResultSet resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				Customer customer = new Customer();
				customer.setId(resultSet.getInt("id"));
				customer.setLoan_account_no(resultSet.getString("loan_account_no"));
				customer.setCreated_date(resultSet.getDate("created_date"));
				customer.setFirst_name(resultSet.getString("first_name"));
				customer.setLast_name(resultSet.getString("last_name"));
				customer.setEmail(resultSet.getString("email"));
				customer.setId_no(resultSet.getString("id_no"));
				customer.setDob(resultSet.getDate("dob"));
				customer.setOrganization_id(resultSet.getInt("organization_id"));
				customer.setOrganization_name(resultSet.getString("organization_name"));
				customer.setPhone_number(resultSet.getString("phone_number"));
				customer.setGross_salary(resultSet.getDouble("gross_salary"));
				customer.setAddress(resultSet.getString("address"));

				customers_list.add(customer);
			}

			return customers_list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
	}

	public static boolean create(Customer customer) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(
					"INSERT INTO `customer_profiles`(`loan_account_no`, `created_date`, `first_name`, `last_name`, `email`,"
							+ " `id_no`, `dob`, `organization_id`, `organization_name`, `phone_number`, `gross_salary`, `address`) "
							+ "VALUES (?, ?, ?, ? ,?, ?, ? ,?, ?, ? ,?, ?)");
			preparedStatement.setString(1, customer.getLoan_account_no());
			preparedStatement.setDate(2, new java.sql.Date(customer.getCreated_date().getTime()));
			preparedStatement.setString(3, customer.getFirst_name());
			preparedStatement.setString(4, customer.getLast_name());
			preparedStatement.setString(5, customer.getEmail());
			preparedStatement.setString(6, customer.getId_no());
			preparedStatement.setDate(7, new java.sql.Date(customer.getDob().getTime()));
			preparedStatement.setInt(8, customer.getOrganization_id());
			preparedStatement.setString(9, customer.getOrganization_name());
			preparedStatement.setString(10, customer.getPhone_number());
			preparedStatement.setDouble(11, customer.getGross_salary());
			preparedStatement.setString(12, customer.getAddress());
			
			preparedStatement.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
	}

	public static boolean update(Customer customer) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE `customer_profiles` SET `loan_account_no`=?,`created_date`=?,"
							+ "`first_name`=?,`last_name`=?,`email`=?,`id_no`=?,`dob`=?,"
							+ "`organization_id`=?,`organization_name`=?,`phone_number`=?,"
							+ "`gross_salary`=?,`address`=? WHERE id = ?");
			preparedStatement.setString(1, customer.getLoan_account_no());
			preparedStatement.setDate(2, new java.sql.Date(customer.getCreated_date().getTime()));
			preparedStatement.setString(3, customer.getFirst_name());
			preparedStatement.setString(4, customer.getLast_name());
			preparedStatement.setString(5, customer.getEmail());
			preparedStatement.setString(6, customer.getId_no());
			preparedStatement.setDate(7, new java.sql.Date(customer.getDob().getTime()));
			preparedStatement.setInt(8, customer.getOrganization_id());
			preparedStatement.setString(9, customer.getOrganization_name());
			preparedStatement.setString(10, customer.getPhone_number());
			preparedStatement.setDouble(11, customer.getGross_salary());
			preparedStatement.setString(12, customer.getAddress());
			preparedStatement.setInt(13, customer.getId());
			
			int result = preparedStatement.executeUpdate();
			if (result <= 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean deleteById(int id) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("DELETE FROM `customer_profiles` WHERE id = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean deleteByLoanAccountNo(String loan_account_no) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("DELETE FROM `customer_profiles` WHERE loan_account_no = ?");
			preparedStatement.setString(1, loan_account_no);
			preparedStatement.execute();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public static Customer findByLoanAccountNo(String loan_account_no) {
		Customer customer = new Customer();
		try {
			PreparedStatement prepareStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `customer_profiles` WHERE `loan_account_no` = ?");
			prepareStatement.setString(1, loan_account_no);
			ResultSet resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				customer.setId(resultSet.getInt("id"));
				customer.setLoan_account_no(loan_account_no);
				customer.setCreated_date(resultSet.getDate("created_date"));
				customer.setFirst_name(resultSet.getString("first_name"));
				customer.setLast_name(resultSet.getString("last_name"));
				customer.setEmail(resultSet.getString("email"));
				customer.setId_no(resultSet.getString("id_no"));
				customer.setDob(resultSet.getDate("dob"));
				customer.setOrganization_id(resultSet.getInt("organization_id"));
				customer.setOrganization_name(resultSet.getString("organization_name"));
				customer.setPhone_number(resultSet.getString("phone_number"));
				customer.setGross_salary(resultSet.getDouble("gross_salary"));
				customer.setAddress(resultSet.getString("address"));
			} else {
				throw new Exception("Error finding customer : Customer with loan account no " + loan_account_no
						+ " doesn't exist.");
			}

			return customer;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
	}
}
