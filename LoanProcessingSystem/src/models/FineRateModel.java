package models;

import entities.FineRate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FineRateModel {
	public static List<FineRate> findAll() {
		List<FineRate> fine_rates_list = new ArrayList<FineRate>();
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * from fine_rates order by lower_limit");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				FineRate fine_rate = new FineRate();
				fine_rate.setId(resultSet.getInt("id"));
				fine_rate.setLower_limit(resultSet.getDouble("lower_limit"));
				fine_rate.setHigher_limit(resultSet.getDouble("higher_limit"));
				fine_rate.setRate(resultSet.getFloat("rate"));
				fine_rates_list.add(fine_rate);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return fine_rates_list;
	}
	
	public static FineRate findById(int id) {
		FineRate fine_rate = null;
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("select * from fine_rates where id = ?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				fine_rate= new FineRate();
				fine_rate.setId(resultSet.getInt("id"));
				fine_rate.setLower_limit(resultSet.getDouble("lower_limit"));
				fine_rate.setHigher_limit(resultSet.getDouble("higher_limit"));
				fine_rate.setRate(resultSet.getFloat("rate"));
			}
			return fine_rate;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean create(FineRate fine_rate) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("INSERT INTO `fine_rates`(`lower_limit`, `higher_limit`, `rate`) VALUES (?,?,?)");
			preparedStatement.setDouble(1, fine_rate.getLower_limit());
			preparedStatement.setDouble(2, fine_rate.getHigher_limit());
			preparedStatement.setFloat(3, fine_rate.getRate());
			
			preparedStatement.execute();
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean update(FineRate fine_rate) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE `fine_rates` SET `lower_limit`=?,`higher_limit`=?,`rate`=? WHERE `id`=?");
			preparedStatement.setDouble(1, fine_rate.getLower_limit());
			preparedStatement.setDouble(2, fine_rate.getHigher_limit());
			preparedStatement.setFloat(3, fine_rate.getRate());
			preparedStatement.setInt(4, fine_rate.getId());
			
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
					.prepareStatement("DELETE FROM `fine_rates` WHERE id = ?");
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
