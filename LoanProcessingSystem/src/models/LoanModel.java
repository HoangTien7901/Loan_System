package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.swing.JOptionPane;

import entities.Installment;
import entities.Loan;

public class LoanModel {
	public static Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));

	public static List<Loan> findAll() throws SQLException {
		List<Loan> loans_list = new ArrayList<Loan>();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM `loan_details`");
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Loan loan = new Loan();
				loan.setId(resultSet.getInt("id"));
				loan.setLoan_account_no(resultSet.getString("loan_account_no"));
				loan.setAmount(resultSet.getDouble("amount"));
				loan.setLoan_type_id(resultSet.getInt("loan_type_id"));

				Date disbursement_date = new Date(resultSet.getDate("disbursement_date").getTime());
				loan.setDisbursement_date(disbursement_date);

				Date term_date = new Date(resultSet.getDate("term_date").getTime());
				loan.setTerm_date(term_date);
				loan.setInterest_rate(resultSet.getFloat("interest_rate"));
				loan.setMonthly_installment(Math.ceil(resultSet.getDouble("monthly_installment")));
				loan.setTotal_installment(Math.ceil(resultSet.getDouble("total_installment")));
				loan.setBalance_to_be_paid(resultSet.getDouble("balance_to_be_paid"));
				loan.setTotal_fine(resultSet.getDouble("total_fine"));
				loan.setStatus(resultSet.getString("status"));
				loan.setStaff_id(resultSet.getInt("staff_id"));

				loans_list.add(loan);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		} finally {
			if (con != null) {
				con.close();
			}
		}

		return loans_list;
	}

	public static boolean create(Loan loan) throws SQLException {
		int number_of_installment = Math.abs(getMonthsBetween(loan.getDisbursement_date(), loan.getTerm_date()));
		double balance_to_be_paid = Math.ceil((loan.getAmount() * (loan.getInterest_rate() / 100 + 1)) * 100) / 100;
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement preparedStatement = con.prepareStatement(
					"INSERT INTO `loan_details`(`loan_account_no`, `amount`, `loan_type_id`, `disbursement_date`, `term_date`, "
							+ "`interest_rate`, `monthly_installment`, `total_installment`, `balance_to_be_paid`, `total_fine`, `status`, `staff_id`) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, loan.getLoan_account_no());
			preparedStatement.setDouble(2, loan.getAmount());
			preparedStatement.setInt(3, loan.getLoan_type_id());
			preparedStatement.setDate(4, new java.sql.Date(loan.getDisbursement_date().getTime()));
			preparedStatement.setDate(5, new java.sql.Date(loan.getTerm_date().getTime()));
			preparedStatement.setFloat(6, loan.getInterest_rate());
			preparedStatement.setDouble(7, Math.ceil(balance_to_be_paid / number_of_installment));
			preparedStatement.setDouble(8, Math.ceil(loan.getTotal_installment()));
			preparedStatement.setDouble(9,  Math.ceil(balance_to_be_paid));
			preparedStatement.setDouble(10, Math.ceil(loan.getTotal_fine()));
			preparedStatement.setString(11, loan.getStatus());
			preparedStatement.setInt(12, loan.getStaff_id());

			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		} finally {
			if (con != null) {
				con.close();
			}
		}

		try {
			con = ConnectDB.getConnection();
			// create installments base on disbursal date and term date
			calendar.setTime(loan.getDisbursement_date());
			for (int i = 0; i < number_of_installment; i++) {
				calendar.add(Calendar.MONTH, 1);

				// get loan details id and create installments
				PreparedStatement preparedStatement = con
						.prepareStatement("SELECT * FROM `loan_details` order by id desc limit 1");
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					int loan_details_id = resultSet.getInt("id");
					double monthly_installment = resultSet.getDouble("monthly_installment");

					Installment installment = new Installment();
					installment.setLoan_details_id(loan_details_id);
					installment.setAmount(monthly_installment);
					installment.setInstallment_term(calendar.getTime());
					installment.setStatus("pending");

					InstallmentModel.create(installment);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public static boolean update(Loan loan) throws SQLException {
		Connection con = ConnectDB.getConnection();
		try {
			Loan old_loan = findById(loan.getId());

			// if amount/disbursal date/ term date change, renew all installments, fines and
			// related messages.
			if (old_loan.getAmount() != loan.getAmount()
					|| old_loan.getDisbursement_date() != loan.getDisbursement_date()
					|| old_loan.getTerm_date() != loan.getTerm_date()
					|| old_loan.getInterest_rate() != loan.getInterest_rate()) {

				// find all installments of this loan
				PreparedStatement preparedStatement = con
						.prepareStatement("select * FROM `installment_details` WHERE `loan_details_id` = ?");
				preparedStatement.setInt(1, loan.getId());
				ResultSet resultSet = preparedStatement.executeQuery();

				// delete all related fines and messages
				while (resultSet.next()) {
					preparedStatement = con
							.prepareStatement("delete FROM `fine_details` WHERE `installment_details_id` = ?");
					preparedStatement.setInt(1, resultSet.getInt("id"));
					preparedStatement.execute();

					preparedStatement = con
							.prepareStatement("DELETE FROM `messages` WHERE `installment_details_id` = ?");
					preparedStatement.setInt(1, resultSet.getInt("id"));
					preparedStatement.execute();
				}

				// delete all related installments
				preparedStatement = con
						.prepareStatement("delete FROM `installment_details` WHERE `loan_details_id` = ?");
				preparedStatement.setInt(1, loan.getId());
				preparedStatement.execute();

				// update loan
				preparedStatement = con.prepareStatement("UPDATE `loan_details` SET `amount`=?,`loan_type_id`=?,"
						+ "`disbursement_date`=?,`term_date`= ?,`interest_rate`=?,"
						+ "`monthly_installment`=?,`total_installment`=?,"
						+ "`balance_to_be_paid`=?,`total_fine`=?,`status`=?,`staff_id`=? " + "WHERE id = ?");
				preparedStatement.setDouble(1, loan.getAmount());
				preparedStatement.setInt(2, loan.getLoan_type_id());
				preparedStatement.setDate(3, new java.sql.Date(loan.getDisbursement_date().getTime()));
				preparedStatement.setDate(4, new java.sql.Date(loan.getTerm_date().getTime()));

				int number_of_installment = Math
						.abs(getMonthsBetween(loan.getDisbursement_date(), loan.getTerm_date()));
				double balance_to_be_paid = loan.getBalance_to_be_paid();
				double monthly_installment = Math.ceil(balance_to_be_paid / number_of_installment);

				preparedStatement.setFloat(5, loan.getInterest_rate());
				preparedStatement.setDouble(6, Math.ceil(monthly_installment));
				preparedStatement.setDouble(7, Math.ceil(loan.getTotal_installment()));
				preparedStatement.setDouble(8, Math.ceil(balance_to_be_paid));
				preparedStatement.setDouble(9, Math.ceil(loan.getTotal_fine()));
				preparedStatement.setString(10, loan.getStatus());
				preparedStatement.setInt(11, loan.getStaff_id());
				preparedStatement.setInt(12, loan.getId());

				preparedStatement.execute();

				calendar.setTime(loan.getDisbursement_date());
				for (int i = 0; i < number_of_installment; i++) {
					calendar.add(Calendar.MONTH, 1);

					// get loan details id and create installments
					int loan_details_id = loan.getId();

					Installment installment = new Installment();
					installment.setLoan_details_id(loan_details_id);
					installment.setAmount(monthly_installment);
					installment.setInstallment_term(calendar.getTime());
					installment.setStatus("pending");

					InstallmentModel.create(installment);
				}
			} else {
				// update loan only
				PreparedStatement preparedStatement = con
						.prepareStatement("UPDATE `loan_details` SET `amount`=?,`loan_type_id`=?,"
								+ "`disbursement_date`=?,`term_date`= ?,`interest_rate`=?,"
								+ "`monthly_installment`=?,`total_installment`=?,"
								+ "`balance_to_be_paid`=?,`total_fine`=?,`status`=?,`staff_id`=? " + "WHERE id = ?");
				preparedStatement.setDouble(1, loan.getAmount());
				preparedStatement.setInt(2, loan.getLoan_type_id());
				preparedStatement.setDate(3, new java.sql.Date(loan.getDisbursement_date().getTime()));
				preparedStatement.setDate(4, new java.sql.Date(loan.getTerm_date().getTime()));

				int number_of_installment = Math
						.abs(getMonthsBetween(loan.getDisbursement_date(), loan.getTerm_date()));
				double balance_to_be_paid = loan.getBalance_to_be_paid();
				double monthly_installment = Math.ceil(balance_to_be_paid / number_of_installment);

				preparedStatement.setFloat(5, loan.getInterest_rate());
				preparedStatement.setDouble(6, monthly_installment);
				preparedStatement.setDouble(7, loan.getTotal_installment());
				preparedStatement.setDouble(8, balance_to_be_paid);
				preparedStatement.setDouble(9, loan.getTotal_fine());
				preparedStatement.setString(10, loan.getStatus());
				preparedStatement.setInt(11, loan.getStaff_id());
				preparedStatement.setInt(12, loan.getId());

				preparedStatement.execute();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public static boolean delete(int loan_id) throws SQLException {
		Connection con = ConnectDB.getConnection();
		try {
			int result = 0;
			for (Installment installment : InstallmentModel.findByLoanDetailId(loan_id)) {
				PreparedStatement preparedStatement = con
						.prepareStatement("DELETE FROM `fine_details` WHERE installment_details_id = ?");
				preparedStatement.setInt(1, installment.getId());
				result = preparedStatement.executeUpdate();

				preparedStatement = con.prepareStatement("DELETE FROM `messages` WHERE `installment_details_id` = ?");
				preparedStatement.setInt(1, installment.getId());
				preparedStatement.execute();
			}

			PreparedStatement preparedStatement = con
					.prepareStatement("DELETE FROM `installment_details` WHERE `loan_details_id` = ?");
			preparedStatement.setInt(1, loan_id);

			result = preparedStatement.executeUpdate();

			preparedStatement = con.prepareStatement("DELETE FROM `loan_details` WHERE `id` = ?");
			preparedStatement.setInt(1, loan_id);
			result = preparedStatement.executeUpdate();
			if (result == 1) {
				return true;
			} else {
				System.out.println("failed 1");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public static List<Loan> findByLoanAccountNo(String loan_account_no) throws SQLException {
		List<Loan> loans_list = new ArrayList<Loan>();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement preparedStatement = con
					.prepareStatement("SELECT * FROM `loan_details` WHERE `loan_account_no` = ?");
			preparedStatement.setString(1, loan_account_no);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Loan loan = new Loan();
				loan.setId(resultSet.getInt("id"));
				loan.setLoan_account_no(resultSet.getString("loan_account_no"));
				loan.setAmount(resultSet.getDouble("amount"));
				loan.setLoan_type_id(resultSet.getInt("loan_type_id"));

				Date disbursement_date = new Date(resultSet.getDate("disbursement_date").getTime());
				loan.setDisbursement_date(disbursement_date);

				Date term_date = new Date(resultSet.getDate("term_date").getTime());
				loan.setTerm_date(term_date);

				loan.setInterest_rate(resultSet.getFloat("interest_rate"));
				loan.setMonthly_installment(resultSet.getDouble("monthly_installment"));
				loan.setTotal_installment(resultSet.getDouble("total_installment"));
				loan.setBalance_to_be_paid(resultSet.getDouble("balance_to_be_paid"));
				loan.setTotal_fine(resultSet.getDouble("total_fine"));
				loan.setStatus(resultSet.getString("status"));
				loan.setStaff_id(resultSet.getInt("staff_id"));

				loans_list.add(loan);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		} finally {
			if (con != null) {
				con.close();
			}

		}

		return loans_list;
	}

	// search by account,amount date
	public static List<Loan> findByAccountAmountDate(String account_loan_no, String amount_start0, String amount_end0,
			String disbursement_date0, String term_date0) throws SQLException {
		List<Loan> loans_list = new ArrayList<Loan>();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement preparedStatement = con.prepareStatement(
					"SELECT * FROM `loan_details` WHERE `loan_account_no` LIKE ? and amount >= ? and amount <= ? and disbursement_date >= ? and term_date <= ? ");
			preparedStatement.setString(1, account_loan_no);
			preparedStatement.setDouble(2, Double.parseDouble(amount_start0));
			preparedStatement.setDouble(3, Double.parseDouble(amount_end0));
			preparedStatement.setDate(4, java.sql.Date.valueOf(disbursement_date0));
			preparedStatement.setDate(5, java.sql.Date.valueOf(term_date0));
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Loan loan = new Loan();
				loan.setId(resultSet.getInt("id"));
				loan.setLoan_account_no(resultSet.getString("loan_account_no"));
				loan.setAmount(resultSet.getDouble("amount"));
				loan.setLoan_type_id(resultSet.getInt("loan_type_id"));

				Date disbursement_date = new Date(resultSet.getDate("disbursement_date").getTime());
				loan.setDisbursement_date(disbursement_date);

				Date term_date = new Date(resultSet.getDate("term_date").getTime());
				loan.setTerm_date(term_date);

				loan.setInterest_rate(resultSet.getFloat("interest_rate"));
				loan.setMonthly_installment(resultSet.getDouble("monthly_installment"));
				loan.setTotal_installment(resultSet.getDouble("total_installment"));
				loan.setBalance_to_be_paid(resultSet.getDouble("balance_to_be_paid"));
				loan.setTotal_fine(resultSet.getDouble("total_fine"));
				loan.setStatus(resultSet.getString("status"));
				loan.setStaff_id(resultSet.getInt("staff_id"));

				loans_list.add(loan);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.out.println(e.getMessage());
			return null;
		} finally {
			if (con != null) {
				con.close();
			}
		}

		return loans_list;
	}

	public static Loan findById(int id) {
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM `loan_details` WHERE `id` = ?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				Loan loan = new Loan();
				loan.setId(resultSet.getInt("id"));
				loan.setLoan_account_no(resultSet.getString("loan_account_no"));
				loan.setAmount(resultSet.getDouble("amount"));
				loan.setLoan_type_id(resultSet.getInt("loan_type_id"));

				Date disbursement_date = new Date(resultSet.getDate("disbursement_date").getTime());
				loan.setDisbursement_date(disbursement_date);

				Date term_date = new Date(resultSet.getDate("term_date").getTime());
				loan.setTerm_date(term_date);

				loan.setInterest_rate(resultSet.getFloat("interest_rate"));
				loan.setMonthly_installment(resultSet.getDouble("monthly_installment"));
				loan.setTotal_installment(resultSet.getDouble("total_installment"));
				loan.setBalance_to_be_paid(resultSet.getDouble("balance_to_be_paid"));
				loan.setTotal_fine(resultSet.getDouble("total_fine"));
				loan.setStatus(resultSet.getString("status"));
				loan.setStaff_id(resultSet.getInt("staff_id"));

				return loan;
			} else {
				throw new Exception("Error finding loan detail : Loan detail with id " + id + " doesn't exist");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static int getMonthsBetween(Date disbursement_date, Date term_date) {
		LocalDate dispursal_date_lc = disbursement_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate term_date_lc = term_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Period diff = Period.between(dispursal_date_lc, term_date_lc);

		int month = diff.getMonths();

		int year = diff.getYears();
		int duration = year * 12 + month;

		return duration;
	}
}
