package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import entities.Customer;
import models.ConnectDB;
import models.CustomerModel;
import models.ValidationModel;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class JInternalFrameUpdateCustomerDetails extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	SimpleDateFormat simpleDF = new SimpleDateFormat("dd/MM/yyyy");

	private static String loan_account_no;
	private int id;
	private JTextField textFieldLoanAccountNo;
	private JTextField textFieldCreatedDate;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldEmail;
	private JTextField textFieldId_no;
	private JTextField textFieldOrganizationName;
	private JTextField textFieldPhone;
	private JTextField textFieldSalary;
	private JTextField textFieldAddress;

	private String[] organizations = new String[10];
	private JDateChooser dateChooserDob;
	private JComboBox<String> comboBoxOrganizations;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameUpdateCustomerDetails frame = new JInternalFrameUpdateCustomerDetails(
							loan_account_no);
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
	public JInternalFrameUpdateCustomerDetails(String loan_account_no) {
		Customer customer = CustomerModel.findByLoanAccountNo(loan_account_no);
		id = customer.getId();

		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 15));
		try {
			PreparedStatement preparedStatment = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `organizations` order by id");
			ResultSet resultSet = preparedStatment.executeQuery();
			int i = 1;
			while (resultSet.next()) {
				organizations[i] = resultSet.getString("name");
				i++;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBounds(100, 100, 768, 625);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Customer update");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 10, 740, 66);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Loan account no");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(20, 86, 167, 40);
		getContentPane().add(lblNewLabel_1);

		textFieldLoanAccountNo = new JTextField(customer.getLoan_account_no());
		textFieldLoanAccountNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldLoanAccountNo.setEditable(false);
		textFieldLoanAccountNo.setBounds(166, 92, 152, 30);
		getContentPane().add(textFieldLoanAccountNo);
		textFieldLoanAccountNo.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("Created date");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(339, 86, 99, 40);
		getContentPane().add(lblNewLabel_1_1);

		textFieldCreatedDate = new JTextField(simpleDF.format(customer.getCreated_date()));
		textFieldCreatedDate.setEditable(false);
		textFieldCreatedDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldCreatedDate.setBounds(460, 92, 152, 30);
		getContentPane().add(textFieldCreatedDate);
		textFieldCreatedDate.setColumns(10);

		JLabel lblNewLabel_1_1_1 = new JLabel("<html>First name <font color=red>(*)</font></html>");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1.setBounds(20, 146, 99, 40);
		getContentPane().add(lblNewLabel_1_1_1);

		textFieldFirstName = new JTextField(customer.getFirst_name());
		textFieldFirstName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldFirstName.setColumns(10);
		textFieldFirstName.setBounds(166, 147, 152, 30);
		textFieldFirstName.addKeyListener(new KeyAdapter() {
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
		getContentPane().add(textFieldFirstName);

		JLabel lblNewLabel_1_1_2 = new JLabel("<html>Last name <font color=red>(*)</font></html>");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_2.setBounds(339, 146, 99, 40);
		getContentPane().add(lblNewLabel_1_1_2);

		textFieldLastName = new JTextField(customer.getLast_name());
		textFieldLastName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldLastName.setColumns(10);
		textFieldLastName.setBounds(460, 147, 152, 30);
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

		textFieldEmail = new JTextField(customer.getEmail());
		textFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(166, 206, 152, 30);
		getContentPane().add(textFieldEmail);

		JLabel lblNewLabel_1_1_2_1 = new JLabel("<html>Email <font color=red>(*)</font></html>");
		lblNewLabel_1_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_2_1.setBounds(20, 205, 99, 40);
		getContentPane().add(lblNewLabel_1_1_2_1);

		textFieldId_no = new JTextField(customer.getId_no());
		textFieldId_no.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldId_no.setColumns(10);
		textFieldId_no.setBounds(460, 206, 152, 30);
		textFieldId_no.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean ret = true;
				try {
					Double.parseDouble(textFieldId_no.getText() + e.getKeyChar());
				} catch (NumberFormatException ee) {
					ret = false;
				}
				if (!ret) {
					e.consume();
				}
			}
		});
		getContentPane().add(textFieldId_no);

		JLabel lblNewLabel_1_1_2_2 = new JLabel("<html>Id no <font color=red>(*)</font></html>");
		lblNewLabel_1_1_2_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_2_2.setBounds(339, 205, 99, 40);
		getContentPane().add(lblNewLabel_1_1_2_2);

		JLabel lblNewLabel_1_1_2_3 = new JLabel("<html>Dob <font color=red>(*)</font></html>");
		lblNewLabel_1_1_2_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_2_3.setBounds(20, 265, 99, 40);
		getContentPane().add(lblNewLabel_1_1_2_3);

		JLabel lblNewLabel_1_1_2_4 = new JLabel("<html>Organization <font color=red>(*)</font></html>");
		lblNewLabel_1_1_2_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_2_4.setBounds(20, 327, 99, 40);
		getContentPane().add(lblNewLabel_1_1_2_4);

		dateChooserDob = new JDateChooser();
		dateChooserDob.setBounds(141, 265, 188, 30);
		dateChooserDob.setDate(customer.getDob());
		dateChooserDob.setDateFormatString("dd/MM/yyyy");
		dateChooserDob.setFont(new Font("Tahoma", Font.PLAIN, 15));
		getContentPane().add(dateChooserDob);

		comboBoxOrganizations = new JComboBox<String>();
		comboBoxOrganizations.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxOrganizations.setBounds(141, 333, 152, 30);
		DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<String>();
		for (int i = 1; i < organizations.length; i++) {
			if (organizations[i] != null) {
				defaultComboBoxModel.addElement(i + " - " + organizations[i]);
			}
		}
		comboBoxOrganizations.setModel(defaultComboBoxModel);
		getContentPane().add(comboBoxOrganizations);

		JLabel lblNewLabel_1_1_2_1_1 = new JLabel("Organization name");
		lblNewLabel_1_1_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_2_1_1.setBounds(313, 327, 152, 40);
		getContentPane().add(lblNewLabel_1_1_2_1_1);

		textFieldOrganizationName = new JTextField(customer.getOrganization_name());
		textFieldOrganizationName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldOrganizationName.setColumns(10);
		textFieldOrganizationName.setBounds(494, 327, 231, 30);
		getContentPane().add(textFieldOrganizationName);

		JLabel lblNewLabel_1_1_2_1_2 = new JLabel("<html>Phone number <font color=red>(*)</font></html>");
		lblNewLabel_1_1_2_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_2_1_2.setBounds(20, 394, 99, 40);
		getContentPane().add(lblNewLabel_1_1_2_1_2);

		textFieldPhone = new JTextField(customer.getPhone_number());
		textFieldPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldPhone.setColumns(10);
		textFieldPhone.setBounds(141, 395, 152, 30);
		textFieldPhone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean ret = true;
				try {
					Double.parseDouble(textFieldPhone.getText() + e.getKeyChar());
				} catch (NumberFormatException ee) {
					ret = false;
				}
				if (!ret) {
					e.consume();
				}
			}
		});
		getContentPane().add(textFieldPhone);

		JLabel lblNewLabel_1_1_2_1_3 = new JLabel("<html>Gross salary <font color=red>(*)</font></html>");
		lblNewLabel_1_1_2_1_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_2_1_3.setBounds(20, 451, 99, 40);
		getContentPane().add(lblNewLabel_1_1_2_1_3);

		textFieldSalary = new JTextField(customer.getGross_salary() + "");
		textFieldSalary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldSalary.setColumns(10);
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
		textFieldSalary.setBounds(141, 452, 152, 30);
		getContentPane().add(textFieldSalary);

		JLabel lblNewLabel_1_1_2_1_4 = new JLabel("<html>Address <font color=red>(*)</font></html>");
		lblNewLabel_1_1_2_1_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_2_1_4.setBounds(339, 451, 99, 40);
		getContentPane().add(lblNewLabel_1_1_2_1_4);

		textFieldAddress = new JTextField(customer.getAddress());
		textFieldAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldAddress.setColumns(10);
		textFieldAddress.setBounds(434, 452, 320, 30);
		getContentPane().add(textFieldAddress);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setIcon(new ImageIcon(JInternalFrameUpdateInstallmentDetails.class.getResource("/img/update.png")));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateCustomer();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnUpdate.setBounds(285, 521, 126, 42);
		getContentPane().add(btnUpdate);
	}

	public void updateCustomer() {
		Customer customer = new Customer();

		String loan_account_no = textFieldLoanAccountNo.getText();
		String first_name = textFieldFirstName.getText().trim();
		String last_name = textFieldLastName.getText().trim();
		String email = textFieldEmail.getText().trim();
		String id_no = textFieldId_no.getText().trim();
		Date dob = dateChooserDob.getDate();
		String phone = textFieldPhone.getText().trim();
		int organization_id = comboBoxOrganizations.getSelectedIndex() + 1;
		String organization_name = textFieldOrganizationName.getText().trim();
		String address = textFieldAddress.getText().trim();

		double salary = Double.valueOf(textFieldSalary.getText().trim());
		try {
			try {
				simpleDF.format(dob);
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Invalid date of birth.");
				return;
			}
			
			if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || id_no.isEmpty()
					|| dob.toString().trim().isEmpty() || phone.isEmpty() || salary == 0 || address.isEmpty()) {
				throw new Exception("Please fill in all fields have *");
			}
			
			if (!ValidationModel.isValidatedString(first_name) || !ValidationModel.isValidatedStringWithSpace(last_name)) {
				throw new Exception("Invalid name.");
			}
			
			if (!ValidationModel.isValidatedStringWithSpace(organization_name) && !organization_name.isEmpty()) {
				throw new Exception("Invalid organization name.");
			}

			if (!isValidEmail(email)) {
				throw new Exception("Please write a valid email.");
			}

			if (id_no.length() < 8 || id_no.length() >= 13) {
				throw new Exception("Please write a valid id no.");
			}

			if (phone.length() != 10) {
				throw new Exception("Please write a valid phone number (10 numbers).");
			}

			// check duplicate values
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(
					"SELECT ( SELECT COUNT(*) FROM customer_profiles WHERE `email` = ? and loan_account_no != ? ) AS email,"
					+ " ( SELECT COUNT(*) FROM customer_profiles WHERE `phone_number` = ? and loan_account_no != ? ) AS phone,"
					+ " ( SELECT COUNT(*) FROM customer_profiles WHERE `id_no` = ? and loan_account_no != ? ) AS id_no,"
					+ " ( SELECT COUNT(*) FROM customer_profiles WHERE `organization_name` = ? and loan_account_no != ? ) AS organization_name"
					+ " FROM customer_profiles limit 1");
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, loan_account_no);
			preparedStatement.setString(3, phone);
			preparedStatement.setString(4, loan_account_no);
			preparedStatement.setString(5, id_no);
			preparedStatement.setString(6, loan_account_no);
			preparedStatement.setString(7, organization_name);
			preparedStatement.setString(8, loan_account_no);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String error="";
				if (resultSet.getInt("email") != 0) {
					error = "email";
				}
				if (resultSet.getInt("phone") != 0) {
					error = "phone";
				}
				if (resultSet.getInt("id_no") != 0) {
					error = "id no";
				}
				if (resultSet.getInt("organization_name") != 0) {
					error = "organization";
				}
				if (!error.isEmpty()) 
					throw new Exception("This " + error + " has been used by another customer.");
			}
			
			customer.setId(id);
			customer.setLoan_account_no(loan_account_no);
			customer.setCreated_date(simpleDF.parse(textFieldCreatedDate.getText()));
			customer.setFirst_name(first_name);
			customer.setLast_name(last_name);
			customer.setEmail(email);
			customer.setId_no(id_no);
			customer.setPhone_number(phone);
			customer.setDob(dob);
			customer.setGross_salary(salary);
			customer.setOrganization_id(organization_id);
			customer.setOrganization_name(organization_name.isEmpty() ? null : organization_name);
			customer.setAddress(address);
			if (CustomerModel.update(customer)) {
				JOptionPane.showMessageDialog(null, "Update success.");
				this.setVisible(false);
				JInternalFrameCustomersList.loadData();
			} else {
				JOptionPane.showMessageDialog(null, "Update failed.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}

	// check valid email
	public static boolean isValidEmail(String email) {
		String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		return email.matches(regex);
	}
}
