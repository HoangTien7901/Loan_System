package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Position;

public class PositionModel {
	public static List<Position> listPosition() throws SQLException {
		List<Position> list = new ArrayList<Position>();
		String sql = "Select * From Positions";
		ResultSet rst = ConnectDB.executeQuery(sql);
		while (rst.next()) {
			// System.out.println(rst.getString("name"));
			Position item = new Position(rst.getInt("id"), rst.getString("name"), rst.getInt("authority_level"));
			list.add(item);
		}
		return list;
	}

	public static ArrayList<String> arrayListName() throws SQLException {
		ArrayList<String> list = new ArrayList<String>();
		
		String sql = "Select * From Positions where authority_level != 4";
		ResultSet rst = ConnectDB.executeQuery(sql);
		while (rst.next()) {
			list.add(rst.getString("name"));
		}
		return list;
	}

	public static Position findById(int id) {
		Position position = null;
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("select * from positions where id = ?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				position = new Position();
				position.setId(resultSet.getInt("id"));
				position.setName(resultSet.getString("name"));
				position.setAuthority_level(resultSet.getInt("authority_level"));
			}
			return position;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<Position> findAll() {
		List<Position> positions_list = new ArrayList<Position>();
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("select * from positions");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Position position = new Position();
				position.setId(resultSet.getInt("id"));
				position.setName(resultSet.getString("name"));
				position.setAuthority_level(resultSet.getInt("authority_level"));
				positions_list.add(position);
			}
			return positions_list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public static boolean create(Position position) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("INSERT INTO `positions`(`name`, `authority_level`) VALUES (?,?)");
			preparedStatement.setString(1, position.getName());
			preparedStatement.setFloat(2, position.getAuthority_level());

			preparedStatement.execute();

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public static boolean update(Position position) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE `positions` SET `name`=?, `authority_level`=? WHERE id = ?");
			preparedStatement.setString(1, position.getName());
			preparedStatement.setFloat(2, position.getAuthority_level());
			preparedStatement.setInt(3, position.getId());

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
					.prepareStatement("DELETE FROM `positions` WHERE id = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public static int getAuthority_level(int position_id) {
		int authority_level = 0;
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("select * from positions where id = ?");
			preparedStatement.setInt(1, position_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				authority_level = resultSet.getInt("authority_level");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return authority_level;
	}
	
	public static boolean isDuplicate(String name) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("select * from positions where name = ?");
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
