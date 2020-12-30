package models;

import entities.Department;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentModel {
	public static List<Department> listDepartments() throws SQLException {
		List<Department> list = new ArrayList<Department>();
		String sql = "Select * From departments";
		ResultSet rst = ConnectDB.executeQuery(sql);
		while (rst.next()) {
			Department item = new Department(rst.getInt("id"), rst.getString("name"));
			list.add(item);
		}
		return list;
	}

	public static ArrayList<String> arrayListName() throws SQLException {
		ArrayList<String> list = new ArrayList<String>();
		String sql = "Select * From departments";
		ResultSet rst = ConnectDB.executeQuery(sql);
		while (rst.next()) {
			list.add(rst.getString("name"));
		}
		return list;
	}

	public static Department findById(int id) {
		Department Department = new Department();
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * from departments where id = ?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Department.setId(resultSet.getInt("id"));
				Department.setName(resultSet.getString("name"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return Department;
	}
	
	public static List<Department> findAll() {
		List<Department> departments_list = new ArrayList<Department>();
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * from departments");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Department department = new Department();
				department.setId(resultSet.getInt("id"));
				department.setName(resultSet.getString("name"));
				departments_list.add(department);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return departments_list;
	}
	
	public static boolean create(String department_name) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("INSERT INTO `departments`(`name`) VALUES (?)");
			preparedStatement.setString(1, department_name + " Finance Department");
			
			preparedStatement.execute();
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean update(Department department) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE `departments` SET `name`=? WHERE id = ?");
			preparedStatement.setString(1, department.getName());
			preparedStatement.setInt(2, department.getId());
			
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
					.prepareStatement("DELETE FROM `departments` WHERE id = ?");
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
					.prepareStatement("select * from departments where name = ?");
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
