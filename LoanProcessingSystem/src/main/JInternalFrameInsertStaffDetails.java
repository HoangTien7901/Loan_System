package main;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.border.LineBorder;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;

import entities.Staff;
import models.ConnectDB;
import models.DepartmentModel;
import models.PositionModel;
import models.StaffModel;

import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Cursor;

public class JInternalFrameInsertStaffDetails extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private final ButtonGroup buttonGroupGender = new ButtonGroup();
	private JPasswordField passwordField;
	private JTextField textFieldAddress;
	private JTextField textFieldSalary;
	private ArrayList<String> positions;
	private ArrayList<String> departments;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	private JComboBox<String> comboBoxDepartment;
	private JComboBox<String> comboBoxPosition;
	private JDateChooser dateChooserDob;
	private JDateChooser dateChooserStartDate;
	private JTextField textFieldUsername;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameInsertStaffDetails frame = new JInternalFrameInsertStaffDetails();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JInternalFrameInsertStaffDetails() {
		try {
			positions = PositionModel.arrayListName();
			departments = DepartmentModel.arrayListName();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBounds(100, 50, 721, 709);
		getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("Add staff");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitle.setBounds(10, 10, 697, 48);
		getContentPane().add(lblTitle);

		JLabel lblNewLabel = new JLabel("<html>First name <font color=red>(*)</font></html>");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(20, 91, 79, 35);
		getContentPane().add(lblNewLabel);

		textFieldFirstName = new JTextField();
		textFieldFirstName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldFirstName.setBounds(115, 91, 172, 35);
		textFieldFirstName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean ret = false;
				try {
					if (e.getKeyChar() == ' ') {
						e.consume();
					}
					Double.parseDouble(e.getKeyChar() + "");
				} catch (NumberFormatException ee) {
					ret = true;
				}
				if (!ret) {
					e.consume();
				}
			}
		});
		getContentPane().add(textFieldFirstName);
		textFieldFirstName.setColumns(10);

		JLabel lblLastName = new JLabel("<html>Last name <font color=red>(*)</font></html>");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLastName.setBounds(393, 91, 85, 35);
		getContentPane().add(lblLastName);

		textFieldLastName = new JTextField();
		textFieldLastName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldLastName.setColumns(10);
		textFieldLastName.setBounds(492, 91, 172, 35);
		textFieldLastName.addKeyListener(new KeyAdapter() {
			@Override
			// only word
			public void keyTyped(KeyEvent e) {
				boolean ret = false;
				try {
					Double.parseDouble(e.getKeyChar() + "");
				} catch (NumberFormatException ee) {
					ret = true;
				}
				if (!ret) {
					e.consume();
				}
			}
		});
		getContentPane().add(textFieldLastName);

		JLabel lblNewLabel_1 = new JLabel("<html>Gender <font color=red>(*)</font></html>");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(20, 146, 85, 35);
		getContentPane().add(lblNewLabel_1);

		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setSelected(true);
		buttonGroupGender.add(rdbtnMale);
		rdbtnMale.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnMale.setBounds(125, 153, 103, 21);
		getContentPane().add(rdbtnMale);

		rdbtnFemale = new JRadioButton("Female");
		buttonGroupGender.add(rdbtnFemale);
		rdbtnFemale.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnFemale.setBounds(277, 151, 103, 21);
		getContentPane().add(rdbtnFemale);

		JLabel lblNewLabel_2 = new JLabel("<html>Password <font color=red>(*)</font></html>");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(397, 205, 85, 35);
		getContentPane().add(lblNewLabel_2);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			// only one word's allowed
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == ' ' || e.getKeyChar() == '"' || e.getKeyChar() == '\'' || e.getKeyChar() == ';') {
					e.consume();
				}
			}
		});
		passwordField.setBounds(492, 207, 179, 35);
		getContentPane().add(passwordField);

		dateChooserDob = new JDateChooser();
		dateChooserDob.setBounds(492, 285, 179, 35);
		dateChooserDob.setFont(new Font("Tahoma", Font.PLAIN, 15));
		dateChooserDob.setDateFormatString("dd/MM/yyyy");
		getContentPane().add(dateChooserDob);

		JLabel lblNewLabel_2_1 = new JLabel("<html>Dob <font color=red>(*)</font></html>");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_1.setBounds(429, 285, 49, 35);
		getContentPane().add(lblNewLabel_2_1);

		comboBoxPosition = new JComboBox<String>();
		comboBoxPosition.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxPosition.setBounds(115, 285, 179, 35);
		getContentPane().add(comboBoxPosition);

		DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<String>();
		for (int i = 0; i < positions.size(); i++) {
			defaultComboBoxModel.addElement((i + 1) + " - " + positions.get(i));
		}
		comboBoxPosition.setModel(defaultComboBoxModel);

		JLabel lblNewLabel_2_1_1 = new JLabel("<html>Position <font color=red>(*)</font></html>");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_1_1.setBounds(20, 285, 67, 35);
		getContentPane().add(lblNewLabel_2_1_1);

		textFieldAddress = new JTextField();
		textFieldAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldAddress.setColumns(10);
		textFieldAddress.setBounds(115, 366, 322, 35);
		getContentPane().add(textFieldAddress);

		JLabel lblAddress = new JLabel("<html>Address <font color=red>(*)</font></html>");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAddress.setBounds(20, 366, 417, 35);
		getContentPane().add(lblAddress);

		JLabel lblSalary = new JLabel("<html>Salary <font color=red>(*)</font></html>");
		lblSalary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSalary.setBounds(20, 441, 79, 35);
		getContentPane().add(lblSalary);

		textFieldSalary = new JTextField();
		textFieldSalary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldSalary.setColumns(10);
		textFieldSalary.setBounds(115, 441, 172, 35);
		textFieldSalary.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean ret = true;
				try {
					Double.parseDouble(textFieldSalary.getText() + e.getKeyChar());
				} catch (NumberFormatException ee) {
					ret = false;
				}
				if (!ret) {
					e.consume();
				}
			}
		});
		getContentPane().add(textFieldSalary);

		dateChooserStartDate = new JDateChooser();
		dateChooserStartDate.getCalendarButton().setFont(new Font("Tahoma", Font.PLAIN, 15));
		dateChooserStartDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		dateChooserStartDate.setBounds(485, 441, 179, 35);
		dateChooserStartDate.setDateFormatString("dd/MM/yyyy");
		getContentPane().add(dateChooserStartDate);

		JLabel lblNewLabel_2_1_2 = new JLabel("<html>Start date <font color=red>(*)</font></html>");
		lblNewLabel_2_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_1_2.setBounds(390, 441, 79, 35);
		getContentPane().add(lblNewLabel_2_1_2);

		JLabel lblDepartment = new JLabel("<html>Department <font color=red>(*)</font></html>");
		lblDepartment.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDepartment.setBounds(20, 509, 93, 35);
		getContentPane().add(lblDepartment);

		comboBoxDepartment = new JComboBox<String>();
		comboBoxDepartment.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxDepartment.setBounds(115, 511, 241, 35);
		getContentPane().add(comboBoxDepartment);

		defaultComboBoxModel = new DefaultComboBoxModel<String>();
		for (int i = 0; i < departments.size(); i++) {
			defaultComboBoxModel.addElement((i + 1) + " - " + departments.get(i));
		}
		comboBoxDepartment.setModel(defaultComboBoxModel);

		JButton btnUpdate = new JButton("Add");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setIcon(new ImageIcon(JInternalFrameInsertStaffDetails.class.getResource("/img/add.png")));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insert();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnUpdate.setBounds(219, 595, 120, 42);
		getContentPane().add(btnUpdate);

		JLabel lblNewLabel_2_2 = new JLabel("<html>Username <font color=red>(*)</font></html>");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_2.setBounds(20, 205, 85, 35);
		getContentPane().add(lblNewLabel_2_2);

		textFieldUsername = new JTextField();
		textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldUsername.setColumns(10);
		textFieldUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				textFieldUsername.setText(textFieldUsername.getText().replaceAll("[^a-zA-Z0-9]", ""));
			}
		});
		textFieldUsername.setBounds(115, 205, 172, 35);
		getContentPane().add(textFieldUsername);

		JButton btnReset = new JButton("Reset");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setIcon(new ImageIcon(JInternalFrameInsertStaffDetails.class.getResource("/img/reset.png")));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReset.setBounds(398, 595, 120, 42);
		getContentPane().add(btnReset);
	}

	public void reset() {
		textFieldFirstName.setText("");
		textFieldLastName.setText("");
		textFieldUsername.setText("");
		passwordField.setText("");
		rdbtnMale.setSelected(true);
		comboBoxPosition.setSelectedIndex(0);
		textFieldAddress.setText("");
		textFieldSalary.setText("");
		comboBoxDepartment.setSelectedIndex(0);
		dateChooserDob.setDate(null);
		dateChooserStartDate.setDate(null);
	}

	public void insert() {
		Staff staff = new Staff();

		String first_name = textFieldFirstName.getText().trim();
		String last_name = textFieldLastName.getText().trim();
		String username = textFieldUsername.getText().trim();
		boolean gender = true;
		if (rdbtnFemale.isSelected()) {
			gender = false;
		}
		String password = new String(passwordField.getPassword());
		Date dob = dateChooserDob.getDate();
		int position_id = comboBoxPosition.getSelectedIndex() + 1;
		String address = textFieldAddress.getText().trim();
		double salary = textFieldSalary.getText().trim().isEmpty() ? 0
				: Double.parseDouble(textFieldSalary.getText().trim());
		Date start_date = dateChooserStartDate.getDate();
		int department_id = comboBoxDepartment.getSelectedIndex() + 1;

		try {
			if (first_name.isEmpty() || last_name.isEmpty() || password.isEmpty() || username.isEmpty()
					|| start_date.toString().trim().isEmpty() || dob == null || salary == 0 || address.isEmpty()) {
				throw new Exception("Please fill in all fields have *");
			}

			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("select * from staff_profiles where username = ?");
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				throw new Exception("Username existed.");
			}
			Calendar calendar = Calendar.getInstance();
			if ((calendar.get(Calendar.YEAR)
					- Integer.parseInt(new SimpleDateFormat("yyyy").format(dateChooserDob.getDate()))) < 18) {
				throw new Exception("Staff under 18 years old");
			}
			try {
				new SimpleDateFormat("dd/MM/yyyy").format(dob);
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Invalid date of birth.");
				return;
			}

			try {
				new SimpleDateFormat("dd/MM/yyyy").format(start_date);
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Invalid start date.");
				return;
			}

			if (PositionModel.getAuthority_level(position_id) > PositionModel
					.getAuthority_level(JFrameMain.getLogin_staff().getPosition_id())) {
				throw new Exception("Can't create a staff with higher authority level than yours.");
			}

			if (PositionModel.getAuthority_level(JFrameMain.getLogin_staff().getPosition_id()) == 2
					&& department_id != JFrameMain.getLogin_staff().getDepartment_id()) {
				throw new Exception("User with authority level 2 can only create staff in the same department.");
			}

			staff.setFirst_name(first_name);
			staff.setLast_name(last_name);
			staff.setGender(gender);
			staff.setUsername(username);
			staff.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
			staff.setDob(dob);
			staff.setPosition_id(position_id);
			staff.setAddress(address);
			staff.setSalary(salary);
			staff.setStart_date(start_date);
			staff.setEnd_date(null);
			staff.setDepartment_id(department_id);
			staff.setStatus("working");

			if (StaffModel.create(staff)) {
				JOptionPane.showMessageDialog(null, "Update success.");
				this.setVisible(false);
				JInternalFrameStaffProfiles.loadData();
			} else {
				JOptionPane.showMessageDialog(null, "Update failed.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
