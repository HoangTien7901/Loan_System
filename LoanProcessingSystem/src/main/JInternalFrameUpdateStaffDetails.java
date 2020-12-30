package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.border.LineBorder;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;

import entities.Staff;
import models.DepartmentModel;
import models.PositionModel;
import models.StaffModel;
import models.ValidationModel;

import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class JInternalFrameUpdateStaffDetails extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Staff staff;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private final ButtonGroup buttonGroupGender = new ButtonGroup();
	private JPasswordField passwordField;
	private JTextField textFieldAddress;
	private JTextField textFieldSalary;
	private final ButtonGroup buttonGroupStatus = new ButtonGroup();
	private ArrayList<String> positions;
	private ArrayList<String> departments;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	private JRadioButton rdbtnWorking;
	private JRadioButton rdbtnRetired;
	private JRadioButton rdbtnFired;
	private JComboBox<String> comboBoxDepartment;
	private JComboBox<String> comboBoxPosition;
	private JDateChooser dateChooserDob;
	private JDateChooser dateChooserStartDate;
	private JDateChooser dateChooserEndDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameUpdateStaffDetails frame = new JInternalFrameUpdateStaffDetails(staff);
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
	public JInternalFrameUpdateStaffDetails(Staff staff) {
		JInternalFrameUpdateStaffDetails.staff = staff;
		try {
			positions = PositionModel.arrayListName();
			departments = DepartmentModel.arrayListName();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBounds(100, 50, 659, 734);
		getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("Staff details update");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitle.setBounds(10, 10, 635, 57);
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
					Double.parseDouble(textFieldSalary.getText() + e.getKeyChar());
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
		lblLastName.setBounds(334, 91, 85, 35);
		getContentPane().add(lblLastName);

		textFieldLastName = new JTextField();
		textFieldLastName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldLastName.setColumns(10);
		textFieldLastName.setBounds(433, 91, 172, 35);
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
		lblNewLabel_2.setBounds(20, 193, 85, 35);
		getContentPane().add(lblNewLabel_2);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordField.setBounds(115, 195, 179, 35);
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			// only one word's allowed
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == ' ' || e.getKeyChar() == '"' || e.getKeyChar() == '\'' || e.getKeyChar() == ';') {
					e.consume();
				}
			}
		});
		getContentPane().add(passwordField);

		dateChooserDob = new JDateChooser();
		dateChooserDob.setBounds(433, 193, 179, 35);
		dateChooserDob.setFont(new Font("Tahoma", Font.PLAIN, 15));
		dateChooserDob.setDateFormatString("dd/MM/yyyy");
		getContentPane().add(dateChooserDob);

		JLabel lblNewLabel_2_1 = new JLabel("<html>Dob <font color=red>(*)</font></html>");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_1.setBounds(370, 193, 49, 35);
		getContentPane().add(lblNewLabel_2_1);

		comboBoxPosition = new JComboBox<String>();
		comboBoxPosition.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxPosition.setBounds(115, 257, 179, 35);
		getContentPane().add(comboBoxPosition);

		if (PositionModel.getAuthority_level(staff.getPosition_id()) != 4) {
			DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<String>();
			for (int i = 0; i < positions.size(); i++) {
				defaultComboBoxModel.addElement((i + 1) + " - " + positions.get(i));
			}

			comboBoxPosition.setModel(defaultComboBoxModel);
		} else {
			comboBoxPosition.setVisible(false);
		}

		JLabel lblNewLabel_2_1_1 = new JLabel("<html>Position <font color=red>(*)</font></html>");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_1_1.setBounds(20, 257, 67, 35);
		getContentPane().add(lblNewLabel_2_1_1);

		textFieldAddress = new JTextField();
		textFieldAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldAddress.setColumns(10);
		textFieldAddress.setBounds(115, 321, 322, 35);
		getContentPane().add(textFieldAddress);

		JLabel lblAddress = new JLabel("<html>Address <font color=red>(*)</font></html>");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAddress.setBounds(20, 321, 417, 35);
		getContentPane().add(lblAddress);

		JLabel lblSalary = new JLabel("<html>Salary <font color=red>(*)</font></html>");
		lblSalary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSalary.setBounds(20, 384, 79, 35);
		getContentPane().add(lblSalary);

		textFieldSalary = new JTextField();
		textFieldSalary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldSalary.setColumns(10);
		textFieldSalary.setBounds(115, 384, 172, 35);
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
		dateChooserStartDate.setBounds(115, 450, 179, 35);
		dateChooserStartDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		dateChooserStartDate.setDateFormatString("dd/MM/yyyy");
		getContentPane().add(dateChooserStartDate);

		JLabel lblNewLabel_2_1_2 = new JLabel("<html>Start date <font color=red>(*)</font></html>");
		lblNewLabel_2_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_1_2.setBounds(20, 450, 79, 35);
		getContentPane().add(lblNewLabel_2_1_2);

		dateChooserEndDate = new JDateChooser();
		dateChooserEndDate.setBounds(433, 450, 179, 35);
		dateChooserEndDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		dateChooserEndDate.setDateFormatString("dd/MM/yyyy");
		getContentPane().add(dateChooserEndDate);

		JLabel lblNewLabel_2_1_2_1 = new JLabel("End date");
		lblNewLabel_2_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_1_2_1.setBounds(354, 450, 83, 35);
		getContentPane().add(lblNewLabel_2_1_2_1);

		JLabel lblDepartment = new JLabel("<html>Department <font color=red>(*)</font></html>");
		lblDepartment.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDepartment.setBounds(20, 509, 93, 35);
		getContentPane().add(lblDepartment);

		comboBoxDepartment = new JComboBox<String>();
		comboBoxDepartment.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxDepartment.setBounds(115, 511, 241, 35);
		getContentPane().add(comboBoxDepartment);

		DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<String>();
		for (int i = 0; i < departments.size(); i++) {
			defaultComboBoxModel.addElement((i + 1) + " - " + departments.get(i));
		}
		comboBoxDepartment.setModel(defaultComboBoxModel);

		JLabel lblStatus = new JLabel("<html>Status <font color=red>(*)</font></html>");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStatus.setBounds(20, 569, 93, 35);
		getContentPane().add(lblStatus);

		rdbtnWorking = new JRadioButton("working");
		buttonGroupStatus.add(rdbtnWorking);
		rdbtnWorking.setSelected(true);
		rdbtnWorking.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnWorking.setBounds(115, 578, 103, 21);
		getContentPane().add(rdbtnWorking);

		rdbtnRetired = new JRadioButton("retired");
		buttonGroupStatus.add(rdbtnRetired);
		rdbtnRetired.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnRetired.setBounds(237, 578, 103, 21);
		getContentPane().add(rdbtnRetired);

		rdbtnFired = new JRadioButton("fired");
		buttonGroupStatus.add(rdbtnFired);
		rdbtnFired.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnFired.setBounds(365, 578, 103, 21);
		getContentPane().add(rdbtnFired);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setIcon(new ImageIcon(JInternalFrameUpdateStaffDetails.class.getResource("/img/update.png")));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnUpdate.setBounds(277, 641, 126, 42);
		getContentPane().add(btnUpdate);

		loadData();
	}

	public void loadData() {
		textFieldFirstName.setText(staff.getFirst_name());
		textFieldLastName.setText(staff.getLast_name());
		if (staff.isGender()) {
			rdbtnMale.setSelected(true);
		} else {
			rdbtnFemale.setSelected(true);
		}
		dateChooserDob.setDate(staff.getDob());
		comboBoxPosition.setSelectedIndex(staff.getPosition_id() - 1);
		textFieldAddress.setText(staff.getAddress());
		textFieldSalary.setText(staff.getSalary() + "");
		dateChooserStartDate.setDate(staff.getStart_date());
		dateChooserEndDate.setDate(staff.getEnd_date());
		comboBoxDepartment.setSelectedIndex(staff.getDepartment_id() - 1);
		if (staff.getStatus().equals("working")) {
			rdbtnWorking.setSelected(true);
		} else {
			if (staff.getStatus().equals("retired")) {
				rdbtnRetired.setSelected(true);
			} else {
				rdbtnFired.setSelected(true);
			}
		}
	}

	public void update() {
		Staff staff = JInternalFrameUpdateStaffDetails.staff;

		String first_name = textFieldFirstName.getText().trim();
		String last_name = textFieldLastName.getText().trim();
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
		Date end_date = dateChooserEndDate.getDate();
		int department_id = comboBoxDepartment.getSelectedIndex() + 1;
		String status;
		if (rdbtnWorking.isSelected()) {
			status = "working";
		} else {
			if (rdbtnRetired.isSelected()) {
				status = "retired";
			} else {
				status = "fired";
			}
		}

		try {
			if (first_name.isEmpty() || last_name.isEmpty() || password.isEmpty()
					|| start_date.toString().trim().isEmpty() || dob == null || salary == 0 || address.isEmpty()) {
				throw new Exception("Please fill in all fields have *");
			}

			if (!ValidationModel.isValidatedString(first_name)
					|| !ValidationModel.isValidatedStringWithSpace(last_name)) {
				throw new Exception("Invalid name.");
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

			try {
				if (end_date != null) {
					new SimpleDateFormat("dd/MM/yyyy").format(end_date);
					if (rdbtnWorking.isSelected()) {
						JOptionPane.showMessageDialog(null, "Invalid work status (have to be retired or fired).");
						return;
					}
				} else {
					if (!rdbtnWorking.isSelected()) {
						JOptionPane.showMessageDialog(null,
								"Invalid work status (end date is null, work status have to be working).");
						return;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Invalid end date.");
				return;
			}

			if (PositionModel.getAuthority_level(position_id) > PositionModel
					.getAuthority_level(JFrameMain.getLogin_staff().getPosition_id())) {
				throw new Exception(
						"Can't update staff's position to a position with higher authority level than yours.");
			}

			if (PositionModel.getAuthority_level(JFrameMain.getLogin_staff().getPosition_id()) == 2
					&& department_id != JFrameMain.getLogin_staff().getDepartment_id()) {
				throw new Exception("User with authority level 2 can only update staff in the same department.");
			}

			staff.setFirst_name(first_name);
			staff.setLast_name(last_name);
			staff.setGender(gender);
			staff.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
			staff.setDob(dob);
			staff.setPosition_id(position_id);
			staff.setAddress(address);
			staff.setSalary(salary);
			staff.setStart_date(start_date);
			staff.setEnd_date(end_date);
			staff.setDepartment_id(department_id);
			staff.setStatus(status);

			if (StaffModel.update(staff)) {
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
