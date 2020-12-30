package main;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.swing.border.LineBorder;

import entities.Customer;
import models.ConnectDB;

import javax.swing.JTextField;
import java.awt.Color;

public class JInternalFrameCustomerDetailsForReport extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Customer customer;

	private String[] organizations = new String[10];
	private JTextField textFieldLoanAccountNo;
	private JTextField textFieldCreated;
	private JTextField textFieldName;
	private JTextField textFieldEmail;
	private JTextField textFieldId_no;
	private JTextField textFieldDob;
	private JTextField textFieldOrganization;
	private JTextField textFieldOrganizationName;
	private JTextField textFieldPhone;
	private JTextField textFieldSalary;
	private JTextField textFieldAddress;

	private SimpleDateFormat simpleDF = new SimpleDateFormat("dd/MM/yyyy");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameCustomerDetailsForReport frame = new JInternalFrameCustomerDetailsForReport(customer);
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
	public JInternalFrameCustomerDetailsForReport(Customer customer) {
		JInternalFrameCustomerDetailsForReport.customer = customer;
		try {
			PreparedStatement prepareStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `organizations`");
			ResultSet resultSet = prepareStatement.executeQuery();
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
		setBounds(100, 100, 708, 549);
		getContentPane().setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Loan account no");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(28, 39, 111, 19);
		getContentPane().add(lblNewLabel_1);

		textFieldLoanAccountNo = new JTextField();
		textFieldLoanAccountNo.setText((String) null);
		textFieldLoanAccountNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldLoanAccountNo.setEditable(false);
		textFieldLoanAccountNo.setColumns(10);
		textFieldLoanAccountNo.setBounds(150, 31, 157, 35);
		getContentPane().add(textFieldLoanAccountNo);

		JLabel lblNewLabel_1_1 = new JLabel("Created date");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(387, 39, 86, 19);
		getContentPane().add(lblNewLabel_1_1);

		textFieldCreated = new JTextField();
		textFieldCreated.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldCreated.setEditable(false);
		textFieldCreated.setColumns(10);
		textFieldCreated.setBounds(519, 31, 157, 35);
		getContentPane().add(textFieldCreated);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(29, 123, 70, 19);
		getContentPane().add(lblName);

		textFieldName = new JTextField();
		textFieldName.setText((String) null);
		textFieldName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldName.setEditable(false);
		textFieldName.setColumns(10);
		textFieldName.setBounds(151, 115, 183, 35);
		getContentPane().add(textFieldName);

		JLabel lblNewLabel_1_4 = new JLabel("Email");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_4.setBounds(28, 176, 53, 29);
		getContentPane().add(lblNewLabel_1_4);

		textFieldEmail = new JTextField();
		textFieldEmail.setText((String) null);
		textFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldEmail.setEditable(false);
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(150, 173, 157, 35);
		getContentPane().add(textFieldEmail);

		JLabel lblNewLabel_1_5 = new JLabel("Id no");
		lblNewLabel_1_5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_5.setBounds(28, 243, 36, 19);
		getContentPane().add(lblNewLabel_1_5);

		textFieldId_no = new JTextField();
		textFieldId_no.setText((String) null);
		textFieldId_no.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldId_no.setEditable(false);
		textFieldId_no.setColumns(10);
		textFieldId_no.setBounds(150, 235, 157, 35);
		getContentPane().add(textFieldId_no);

		JLabel lblDob = new JLabel("Date of birth");
		lblDob.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDob.setBounds(387, 123, 85, 19);
		getContentPane().add(lblDob);

		textFieldDob = new JTextField();
		textFieldDob.setText((String) null);
		textFieldDob.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldDob.setEditable(false);
		textFieldDob.setColumns(10);
		textFieldDob.setBounds(519, 115, 157, 35);
		getContentPane().add(textFieldDob);

		JLabel lblNewLabel_1_1_1 = new JLabel("Organization");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1.setBounds(28, 303, 84, 19);
		getContentPane().add(lblNewLabel_1_1_1);

		textFieldOrganization = new JTextField();
		textFieldOrganization.setText((String) null);
		textFieldOrganization.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldOrganization.setEditable(false);
		textFieldOrganization.setColumns(10);
		textFieldOrganization.setBounds(150, 295, 157, 35);
		getContentPane().add(textFieldOrganization);

		JLabel lblNewLabel_1_2_1 = new JLabel("Organization name");
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_2_1.setBounds(28, 365, 126, 19);
		getContentPane().add(lblNewLabel_1_2_1);

		textFieldOrganizationName = new JTextField();
		textFieldOrganizationName.setText("<dynamic>");
		textFieldOrganizationName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldOrganizationName.setEditable(false);
		textFieldOrganizationName.setColumns(10);
		textFieldOrganizationName.setBounds(164, 357, 243, 35);
		getContentPane().add(textFieldOrganizationName);

		JLabel lblNewLabel_1_3_1 = new JLabel("Phone number");
		lblNewLabel_1_3_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_3_1.setBounds(387, 181, 99, 19);
		getContentPane().add(lblNewLabel_1_3_1);

		textFieldPhone = new JTextField();
		textFieldPhone.setText((String) null);
		textFieldPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldPhone.setEditable(false);
		textFieldPhone.setColumns(10);
		textFieldPhone.setBounds(519, 173, 157, 35);
		getContentPane().add(textFieldPhone);

		JLabel lblNewLabel_1_4_1 = new JLabel("Gross salary");
		lblNewLabel_1_4_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_4_1.setBounds(387, 303, 80, 19);
		getContentPane().add(lblNewLabel_1_4_1);

		textFieldSalary = new JTextField();
		textFieldSalary.setText("0.0");
		textFieldSalary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldSalary.setEditable(false);
		textFieldSalary.setColumns(10);
		textFieldSalary.setBounds(519, 295, 157, 35);
		getContentPane().add(textFieldSalary);

		JLabel lblNewLabel_1_5_1 = new JLabel("Address");
		lblNewLabel_1_5_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_5_1.setBounds(28, 426, 53, 19);
		getContentPane().add(lblNewLabel_1_5_1);

		textFieldAddress = new JTextField();
		textFieldAddress.setText((String) null);
		textFieldAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldAddress.setEditable(false);
		textFieldAddress.setColumns(10);
		textFieldAddress.setBounds(151, 418, 322, 35);
		getContentPane().add(textFieldAddress);
		
		loadData();
	}

	public void loadData() {
		textFieldLoanAccountNo.setText(customer.getLoan_account_no());
		textFieldCreated.setText(simpleDF.format(customer.getCreated_date()));
		textFieldName.setText(customer.getFirst_name() + " " + customer.getLast_name());
		textFieldEmail.setText(customer.getEmail());
		textFieldDob.setText(simpleDF.format(customer.getDob()));
		textFieldId_no.setText(customer.getId_no());
		textFieldOrganization.setText(organizations[customer.getOrganization_id()]);
		textFieldOrganizationName.setText(customer.getOrganization_name());
		textFieldPhone.setText(customer.getPhone_number());
		textFieldSalary.setText(customer.getGross_salary() + "");
		textFieldAddress.setText(customer.getAddress());
	}
}
