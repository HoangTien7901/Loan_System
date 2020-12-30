package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entities.LoanTypes;

public class LoanTypesModel {
	public static List<LoanTypes> find() {
		List<LoanTypes> loan_types = new ArrayList<LoanTypes>();

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `loan_types`");
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				LoanTypes loanType = new LoanTypes();
				loanType.setId(resultSet.getInt("id"));
				loanType.setName(resultSet.getString("loan_name"));
				loanType.setInterest_rate(resultSet.getFloat("interest_rate"));
				loan_types.add(loanType);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
		return loan_types;
	}

	public static LoanTypes findById(int id) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `loan_types` where id = ?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				LoanTypes loan_type = new LoanTypes();
				loan_type.setId(resultSet.getInt("id"));
				loan_type.setName(resultSet.getString("loan_name"));
				loan_type.setInterest_rate(resultSet.getFloat("interest_rate"));
				return loan_type;
			}
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
	}

	public static boolean create(LoanTypes loan_type) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("INSERT INTO `loan_types`(`loan_name`, `interest_rate`) VALUES (?,?)");
			preparedStatement.setString(1, loan_type.getName());
			preparedStatement.setFloat(2, loan_type.getInterest_rate());

			preparedStatement.execute();

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public static boolean update(LoanTypes loan_type) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE `loan_types` SET `loan_name`=?, `interest_rate`=? WHERE id = ?");
			preparedStatement.setString(1, loan_type.getName());
			preparedStatement.setFloat(2, loan_type.getInterest_rate());
			preparedStatement.setInt(3, loan_type.getId());

			preparedStatement.execute();

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public static boolean delete(int id) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("DELETE FROM `loan_types` WHERE id = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean isDuplicate(String name) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("select * from loan_types where loan_name = ?");
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return true;
		}
		return false;
	}
}
