package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import entities.Staff;
import main.JFrameMain;

public class StaffModel {
	public static List<Staff> findAll() {
		List<Staff> staffs_list = new ArrayList<Staff>();

		try {
			PreparedStatement preparedStatement;
			if (PositionModel.getAuthority_level(JFrameMain.getLogin_staff().getPosition_id()) == 4) {
				preparedStatement = ConnectDB.getConnection().prepareStatement("select * from staff_profiles");
			} else {
				preparedStatement = ConnectDB.getConnection()
						.prepareStatement("select * from staff_profiles where position_id != 4");
			}
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Staff staff = new Staff();
				staff.setId(resultSet.getInt("id"));
				staff.setFirst_name(resultSet.getString("first_name"));
				staff.setLast_name(resultSet.getString("last_name"));
				staff.setGender(resultSet.getBoolean("gender"));
				staff.setUsername(resultSet.getString("username"));
				staff.setPassword(resultSet.getString("password"));

				Date dob = new Date(resultSet.getDate("dob").getTime());
				staff.setDob(dob);

				staff.setPosition_id(resultSet.getInt("position_id"));
				staff.setAddress(resultSet.getString("address"));
				staff.setSalary(resultSet.getDouble("salary"));

				Date start_date = new Date(resultSet.getDate("start_date").getTime());
				staff.setStart_date(start_date);

				Date end_date = null;
				if (resultSet.getDate("end_date") != null) {
					end_date = new Date(resultSet.getDate("end_date").getTime());
				}
				staff.setEnd_date(end_date);

				staff.setDepartment_id(resultSet.getInt("department_id"));
				staff.setStatus(resultSet.getString("status"));

				staffs_list.add(staff);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}

		return staffs_list;
	}

	public static Staff findByUsername(String username) {
		Staff staff = new Staff();

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("select * from staff_profiles where username = ?");
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				staff.setId(resultSet.getInt("id"));
				staff.setFirst_name(resultSet.getString("first_name"));
				staff.setLast_name(resultSet.getString("last_name"));
				staff.setGender(resultSet.getBoolean("gender"));
				staff.setUsername(resultSet.getString("username"));
				staff.setPassword(resultSet.getString("password"));

				Date dob = new Date(resultSet.getDate("dob").getTime());
				staff.setDob(dob);

				staff.setPosition_id(resultSet.getInt("position_id"));
				staff.setAddress(resultSet.getString("address"));
				staff.setSalary(resultSet.getDouble("salary"));

				Date start_date = new Date(resultSet.getDate("start_date").getTime());
				staff.setStart_date(start_date);

				Date end_date = null;
				if (resultSet.getDate("end_date") != null) {
					end_date = new Date(resultSet.getDate("end_date").getTime());
				}
				staff.setEnd_date(end_date);

				staff.setDepartment_id(resultSet.getInt("department_id"));
				staff.setStatus(resultSet.getString("status"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}

		return staff;
	}

	public static List<Staff> findByIdAndUsername(int id, String username) {
		List<Staff> staffs_list = new ArrayList<Staff>();

		try {
			String id_string = "";
			if (id != 0) {
				id_string = " and id = " + id;
			}

			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("select * from staff_profiles where username like ?" + id_string);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Staff staff = new Staff();
				staff.setId(resultSet.getInt("id"));
				staff.setFirst_name(resultSet.getString("first_name"));
				staff.setLast_name(resultSet.getString("last_name"));
				staff.setGender(resultSet.getBoolean("gender"));
				staff.setUsername(resultSet.getString("username"));
				staff.setPassword(resultSet.getString("password"));

				Date dob = new Date(resultSet.getDate("dob").getTime());
				staff.setDob(dob);

				staff.setPosition_id(resultSet.getInt("position_id"));
				staff.setAddress(resultSet.getString("address"));
				staff.setSalary(resultSet.getDouble("salary"));

				Date start_date = new Date(resultSet.getDate("start_date").getTime());
				staff.setStart_date(start_date);

				Date end_date = null;
				if (resultSet.getDate("end_date") != null) {
					end_date = new Date(resultSet.getDate("end_date").getTime());
				}
				staff.setEnd_date(end_date);

				staff.setDepartment_id(resultSet.getInt("department_id"));
				staff.setStatus(resultSet.getString("status"));
				staffs_list.add(staff);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}

		return staffs_list;
	}

	// #############
	public static boolean create(Staff staff) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(
					"INSERT INTO staff_profiles(first_name, last_name, gender, username, password, dob, "
							+ "position_id, address, salary, start_date, end_date, department_id, status) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, staff.getFirst_name());
			preparedStatement.setString(2, staff.getLast_name());
			preparedStatement.setBoolean(3, staff.isGender());
			preparedStatement.setString(4, staff.getUsername());
			preparedStatement.setString(5, staff.getPassword());
			preparedStatement.setDate(6, new java.sql.Date(staff.getDob().getTime()));
			preparedStatement.setInt(7, staff.getPosition_id());
			preparedStatement.setString(8, staff.getAddress());
			preparedStatement.setDouble(9, staff.getSalary());
			preparedStatement.setDate(10, new java.sql.Date(staff.getStart_date().getTime()));
			if (staff.getEnd_date() == null) {
				preparedStatement.setDate(11, null);
			} else {
				preparedStatement.setDate(11, new java.sql.Date(staff.getEnd_date().getTime()));
			}
			preparedStatement.setInt(12, staff.getDepartment_id());
			preparedStatement.setString(13, staff.getStatus());

			int result = preparedStatement.executeUpdate();
			if (result <= 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
	}

	public static boolean update(Staff staff) {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("UPDATE `staff_profiles` SET `first_name`=?,`last_name`=?,`gender`=?,"
							+ "`username`=?,`password`=?,`dob`=?,`position_id`=?,`address`=?,"
							+ "`salary`=?,`start_date`=?,`end_date`=?,`department_id`=?," + "`status`=? "
							+ "WHERE id = ?");
			preparedStatement.setString(1, staff.getFirst_name());
			preparedStatement.setString(2, staff.getLast_name());
			preparedStatement.setBoolean(3, staff.isGender());
			preparedStatement.setString(4, staff.getUsername());
			preparedStatement.setString(5, staff.getPassword());
			preparedStatement.setDate(6, new java.sql.Date(staff.getDob().getTime()));
			preparedStatement.setInt(7, staff.getPosition_id());
			preparedStatement.setString(8, staff.getAddress());
			preparedStatement.setDouble(9, staff.getSalary());
			preparedStatement.setDate(10, new java.sql.Date(staff.getStart_date().getTime()));
			if (staff.getEnd_date() == null) {
				preparedStatement.setDate(11, null);
			} else {
				preparedStatement.setDate(11, new java.sql.Date(staff.getEnd_date().getTime()));
			}
			preparedStatement.setInt(12, staff.getDepartment_id());
			preparedStatement.setString(13, staff.getStatus());
			preparedStatement.setInt(14, staff.getId());

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
					.prepareStatement("DELETE FROM `staff_profiles` WHERE id = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public static List<Staff> findById(int department_id) {
		List<Staff> staffs_list = new ArrayList<Staff>();

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("select * from staff_profiles where department_id = ? and end_date is null");
			preparedStatement.setInt(1, department_id);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Staff staff = new Staff();
				staff.setId(resultSet.getInt("id"));
				staff.setFirst_name(resultSet.getString("first_name"));
				staff.setLast_name(resultSet.getString("last_name"));
				staff.setGender(resultSet.getBoolean("gender"));
				staff.setUsername(resultSet.getString("username"));
				staff.setPassword(resultSet.getString("password"));

				Date dob = new Date(resultSet.getDate("dob").getTime());
				staff.setDob(dob);

				staff.setPosition_id(resultSet.getInt("position_id"));
				staff.setAddress(resultSet.getString("address"));
				staff.setSalary(resultSet.getDouble("salary"));

				Date start_date = new Date(resultSet.getDate("start_date").getTime());
				staff.setStart_date(start_date);

				Date end_date = null;
				if (resultSet.getDate("end_date") != null) {
					end_date = new Date(resultSet.getDate("end_date").getTime());
				}
				staff.setEnd_date(end_date);

				staff.setDepartment_id(resultSet.getInt("department_id"));
				staff.setStatus(resultSet.getString("status"));

				staffs_list.add(staff);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}

		return staffs_list;
	}

	public static Staff findByStaff_Id(int id) {
		Staff staff = new Staff();

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("select * from staff_profiles where id = ?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				staff.setId(resultSet.getInt("id"));
				staff.setFirst_name(resultSet.getString("first_name"));
				staff.setLast_name(resultSet.getString("last_name"));
				staff.setGender(resultSet.getBoolean("gender"));
				staff.setUsername(resultSet.getString("username"));
				staff.setPassword(resultSet.getString("password"));

				Date dob = new Date(resultSet.getDate("dob").getTime());
				staff.setDob(dob);

				staff.setPosition_id(resultSet.getInt("position_id"));
				staff.setAddress(resultSet.getString("address"));
				staff.setSalary(resultSet.getDouble("salary"));

				Date start_date = new Date(resultSet.getDate("start_date").getTime());
				staff.setStart_date(start_date);

				Date end_date = null;
				if (resultSet.getDate("end_date") != null) {
					end_date = new Date(resultSet.getDate("end_date").getTime());
				}
				staff.setEnd_date(end_date);

				staff.setDepartment_id(resultSet.getInt("department_id"));
				staff.setStatus(resultSet.getString("status"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}

		return staff;
	}
}
