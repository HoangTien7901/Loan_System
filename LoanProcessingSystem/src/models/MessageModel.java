package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import entities.Message;

public class MessageModel {
	public static List<Message> findAll() {
		List<Message> messages_list = new ArrayList<Message>();
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `messages` order by id desc");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Message message = new Message();
				message.setId(resultSet.getInt("id"));
				message.setContent(resultSet.getString("content"));
				message.setType(resultSet.getString("type"));
				message.setInstallment_id(resultSet.getInt("installment_details_id"));
				message.setStatus(resultSet.getString("status"));
				message.setCreated_date(resultSet.getDate("created_date"));
				messages_list.add(message);
			}

			return messages_list;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static boolean create(Message message) throws SQLException {
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement preparedStatement = con.prepareStatement(
					"INSERT INTO `messages`(`id`, `content`, `installment_details_id`, `type`,`status`, `created_date`) VALUES (?,?,?,?,?,?)");
			preparedStatement.setInt(1, message.getId());
			preparedStatement.setString(2, message.getContent());
			preparedStatement.setInt(3, message.getInstallment_id());
			preparedStatement.setString(4, message.getType());
			preparedStatement.setString(5, message.getStatus());
			preparedStatement.setDate(6, new java.sql.Date(message.getCreated_date().getTime()));
			preparedStatement.execute();

			return true;
		} catch (SQLIntegrityConstraintViolationException sqlE) {
			// ignore
			return false;
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

	public static boolean update(Message message) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(
					"UPDATE `messages` SET `content`=?,`installment_details_id`=?,`type`=?,`status`=?, `created_date`=? WHERE id = ?");

			preparedStatement.setString(1, message.getContent());
			preparedStatement.setInt(2, message.getInstallment_id());
			preparedStatement.setString(3, message.getType());
			preparedStatement.setString(4, message.getStatus());
			preparedStatement.setDate(5, new java.sql.Date(message.getCreated_date().getTime()));
			preparedStatement.setInt(6, message.getId());
			preparedStatement.execute();

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public static boolean markAsRead(int id) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE `messages` SET `status`=? WHERE id = ?");

			preparedStatement.setString(1, "read");
			preparedStatement.setInt(2, id);
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
					.prepareStatement("DELETE FROM `messages`  WHERE id = ?");

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
