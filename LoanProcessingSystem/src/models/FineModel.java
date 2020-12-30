package models;

import java.sql.Date;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entities.Fine;

public class FineModel {
	public static List<Fine> findAll() {
		List<Fine> fines_list = new ArrayList<Fine>();
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `fine_details`");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Fine fine = new Fine();
				fine.setId(resultSet.getInt("id"));
				fine.setInstallment_details_id(resultSet.getInt("installment_details_id"));
				fine.setAmount(resultSet.getDouble("amount"));
				fine.setCreated_date(resultSet.getDate("created_date"));
				fine.setStatus(resultSet.getString("status"));
				fine.setPayday(resultSet.getDate("payday"));
				fines_list.add(fine);
			}

			return fines_list;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static List<Fine> findStatusAndDate(String status, java.util.Date date, int id) {
		String id_string = "";
		if (id != 0) {
			id_string = " and installment_details_id = " + id;
		}
		List<Fine> fines_list = new ArrayList<Fine>();
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `fine_details` where `status` = ? and created_date >= ?" + id_string);
			preparedStatement.setString(1, status);
			preparedStatement.setDate(2, new Date(date.getTime()));
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Fine fine = new Fine();
				fine.setId(resultSet.getInt("id"));
				fine.setInstallment_details_id(resultSet.getInt("installment_details_id"));
				fine.setAmount(resultSet.getDouble("amount"));
				fine.setCreated_date(resultSet.getDate("created_date"));
				fine.setStatus(resultSet.getString("status"));
				fine.setPayday(resultSet.getDate("payday"));
				fines_list.add(fine);
			}

			return fines_list;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}


	public static Fine findByInstallmentId(int fine_id) {
		Fine fine = new Fine();
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `fine_details` where `installment_details_id` = ?");
			preparedStatement.setInt(1, fine_id);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				fine.setId(resultSet.getInt("id"));
				fine.setInstallment_details_id(resultSet.getInt("installment_details_id"));
				fine.setAmount(resultSet.getDouble("amount"));
				fine.setCreated_date(resultSet.getDate("created_date"));
				fine.setStatus(resultSet.getString("status"));
				fine.setPayday(resultSet.getDate("payday"));
			}

			return fine;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public static Fine findById(int id) {
		Fine fine = new Fine();
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `fine_details` where `id` = ?");
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				fine.setId(resultSet.getInt("id"));
				fine.setInstallment_details_id(resultSet.getInt("installment_details_id"));
				fine.setAmount(resultSet.getDouble("amount"));
				fine.setCreated_date(resultSet.getDate("created_date"));
				fine.setStatus(resultSet.getString("status"));
				fine.setPayday(resultSet.getDate("payday"));
			}

			return fine;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static boolean create(Fine fine) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(
					"INSERT INTO `fine_details`(`installment_details_id`, `amount`,`created_date`, `status`, `payday`) VALUES (?,?,?,?,?)");
			preparedStatement.setInt(1, fine.getInstallment_details_id());
			preparedStatement.setDouble(2, fine.getAmount());
			preparedStatement.setDate(3, new java.sql.Date(fine.getCreated_date().getTime()));
			preparedStatement.setString(4, fine.getStatus());
			preparedStatement.setDate(5, new java.sql.Date(fine.getPayday().getTime()));

			preparedStatement.execute();

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public static boolean update(Fine fine) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE `fine_details` SET`payday`=?,`status`=? WHERE `installment_details_id`= ?");
			
			if (fine.getPayday() == null) {
				preparedStatement.setDate(1, null);
			} else {
				preparedStatement.setDate(1, new java.sql.Date(fine.getPayday().getTime()));
			}
			preparedStatement.setString(2, fine.getStatus());
			preparedStatement.setInt(3, fine.getInstallment_details_id());
			return preparedStatement.executeUpdate() > 0;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public static boolean delete(int id) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("DELETE FROM `fine_details` WHERE id = ?");

			preparedStatement.setInt(1, id);
			preparedStatement.execute();

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
}
