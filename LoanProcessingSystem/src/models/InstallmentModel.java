package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entities.Installment;

public class InstallmentModel {
	public static List<Installment> findAll() {
		List<Installment> installments_list = new ArrayList<Installment>();

		try {
			PreparedStatement prepareStatement = ConnectDB.getConnection()
					.prepareStatement("select * from installment_details");
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				Installment installment = new Installment();
				installment.setId(resultSet.getInt("id"));
				installment.setLoan_details_id(resultSet.getInt("loan_details_id"));
				installment.setPayday(resultSet.getDate("payday"));
				installment.setInstallment_term(resultSet.getDate("installment_term"));
				installment.setAmount(resultSet.getDouble("amount"));
				installment.setStatus(resultSet.getString("status"));

				installments_list.add(installment);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		} finally {
			if (ConnectDB.getConnection() != null)
				try {
					ConnectDB.getConnection().close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return installments_list;
	}

	public static boolean create(Installment installment) {
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement prepareStatement = con.prepareStatement(
					"INSERT INTO `installment_details`(`loan_details_id`, `installment_term`, `amount`, `status`) VALUES (?, ?, ?, ?)");
			prepareStatement.setInt(1, installment.getLoan_details_id());
			prepareStatement.setDate(2, new java.sql.Date(installment.getInstallment_term().getTime()));
			prepareStatement.setDouble(3, Math.ceil(installment.getAmount()));
			prepareStatement.setString(4, installment.getStatus());

			prepareStatement.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return true;
	}

	public static boolean update(Installment installment) {
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement prepareStatement = con
					.prepareStatement("SELECT * FROM `installment_details` WHERE id = ?");
			prepareStatement.setInt(1, installment.getId());
			ResultSet resultSet = prepareStatement.executeQuery();
			int loan_details_id = 0;
			if (resultSet.next()) {
				loan_details_id = resultSet.getInt("loan_details_id");
			}

			// when installment status change from pending or due to done
			if (!resultSet.getString("status").equals("done") && installment.getStatus().equals("done")) {
				prepareStatement = con
						.prepareStatement("UPDATE `loan_details` SET `total_installment`= `total_installment` + ?,"
								+ "`balance_to_be_paid`=`balance_to_be_paid` - ? WHERE id = ?");
				prepareStatement.setDouble(1, installment.getAmount());
				prepareStatement.setDouble(2, installment.getAmount());
				prepareStatement.setInt(3, loan_details_id);
				prepareStatement.execute();
			}

			// when installment status change from done to pending
			if (resultSet.getString("status").equals("done") && installment.getStatus().equals("pending")) {
				prepareStatement = con
						.prepareStatement("UPDATE `loan_details` SET `total_installment`= `total_installment` - ?,"
								+ "`balance_to_be_paid`=`balance_to_be_paid` + ? WHERE id = ?");
				prepareStatement.setDouble(1, installment.getAmount());
				prepareStatement.setDouble(2, installment.getAmount());
				prepareStatement.setInt(3, loan_details_id);
				prepareStatement.execute();
			}

			prepareStatement = con.prepareStatement(" UPDATE `installment_details` SET "
					+ "	 `payday`=?,`installment_term`=?,`amount`=?,`status`=? WHERE id = ?");
			if (installment.getPayday() == null) {
				prepareStatement.setDate(1, null);
			} else {
				prepareStatement.setDate(1, new java.sql.Date(installment.getPayday().getTime()));
			}
			prepareStatement.setDate(2, new java.sql.Date(installment.getInstallment_term().getTime()));
			prepareStatement.setDouble(3, Math.ceil(installment.getAmount()));
			prepareStatement.setString(4, installment.getStatus());
			prepareStatement.setInt(5, installment.getId());

			prepareStatement.execute();

			prepareStatement = con.prepareStatement(
					"SELECT count(*) FROM `installment_details` WHERE `status` != \"done\" and `loan_details_id` = ?");
			prepareStatement.setInt(1, loan_details_id);
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				if (resultSet.getInt("count(*)") == 0) {
					prepareStatement = ConnectDB.getConnection()
							.prepareStatement("UPDATE `loan_details` SET `status`= \"done\" WHERE id = ?");
					prepareStatement.setInt(1, loan_details_id);
					prepareStatement.execute();
				}
			}

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public static boolean delete(int id) {
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement prepareStatement = con
					.prepareStatement("DELETE FROM `fine_details` WHERE installment_details_id = ?");
			prepareStatement.setInt(1, id);
			if (prepareStatement.execute()) {
				prepareStatement = con
						.prepareStatement("DELETE FROM `installment_details` WHERE id = ?");
				prepareStatement.setInt(1, id);
				return prepareStatement.execute();
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	// find functions
	// find by loan details id
	public static List<Installment> findByLoanDetailId(int loan_details_id) {
		List<Installment> installments_list = new ArrayList<Installment>();

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `installment_details` where loan_details_id = ?");
			preparedStatement.setInt(1, loan_details_id);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Installment installment = new Installment();
				installment.setId(resultSet.getInt("id"));
				installment.setLoan_details_id(resultSet.getInt("loan_details_id"));
				installment.setPayday(resultSet.getDate("payday"));
				installment.setInstallment_term(resultSet.getDate("installment_term"));
				installment.setAmount(resultSet.getDouble("amount"));
				installment.setStatus(resultSet.getString("status"));
				installments_list.add(installment);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}

		return installments_list;
	}

	// find by installment id
	public static Installment findById(int id) {
		Installment installment = new Installment();

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `installment_details` where id = ?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				installment.setId(resultSet.getInt("id"));
				installment.setLoan_details_id(resultSet.getInt("loan_details_id"));
				installment.setPayday(resultSet.getDate("payday"));
				installment.setInstallment_term(resultSet.getDate("installment_term"));
				installment.setAmount(resultSet.getDouble("amount"));
				installment.setStatus(resultSet.getString("status"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}

		return installment;
	}

	// find By status of one loan details
	public static List<Installment> findByStatus(String status, int loan_details_id) {

		List<Installment> Installment_list = new ArrayList<Installment>();

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(
					"SELECT * FROM `installment_details` where status LIKE ? and loan_details_id = ?");

			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, loan_details_id);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Installment inDetails = new Installment();
				inDetails.setId(resultSet.getInt("id"));
				inDetails.setLoan_details_id(resultSet.getInt("loan_details_id"));
				inDetails.setPayday(resultSet.getDate("payday"));
				inDetails.setInstallment_term(resultSet.getDate("installment_term"));
				inDetails.setAmount(resultSet.getDouble("amount"));
				inDetails.setStatus(resultSet.getString("status"));
				Installment_list.add(inDetails);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}

		return Installment_list;
	}
}
