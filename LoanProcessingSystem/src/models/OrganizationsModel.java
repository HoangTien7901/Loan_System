package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entities.Organization;

public class OrganizationsModel {
	public static Organization findById(int id) {
		Organization Organization = new Organization();
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * from organizations where id = ?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Organization.setId(resultSet.getInt("id"));
				Organization.setName(resultSet.getString("name"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return Organization;
	}
	
	public static List<Organization> findAll() {
		List<Organization> organizations_list = new ArrayList<Organization>();
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * from organizations");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Organization organization = new Organization();
				organization.setId(resultSet.getInt("id"));
				organization.setName(resultSet.getString("name"));
				organizations_list.add(organization);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return organizations_list;
	}
	
	public static boolean create(String organization_name) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("INSERT INTO `organizations`(`name`) VALUES (?)");
			preparedStatement.setString(1, organization_name);
			
			preparedStatement.execute();
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean update(Organization organization) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE `organizations` SET `name`=? WHERE id = ?");
			preparedStatement.setString(1, organization.getName());
			preparedStatement.setInt(2, organization.getId());
			
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
					.prepareStatement("DELETE FROM `organizations` WHERE id = ?");
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
					.prepareStatement("select * from organizations where name = ?");
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
