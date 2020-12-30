package main;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import entities.Customer;
import entities.Loan;
import entities.LoanTypes;
import models.CustomerModel;
import models.LoanModel;
import models.LoanTypesModel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Cursor;

public class JInternalFrameInsertLoanDetail extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField jtextFieldAmount;
	private JTextField jtextFieldDuration;
	private JTextField jtextFieldInterestRate;
	private JTextField jtextFieldMonthlyInstallment;
	private JTextField jtextFieldBalanceToBePaid;
	private JComboBox<String> jcomboBoxLoanType;
	private JComboBox<String> jcomboBoxLoanAccount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameInsertLoanDetail frame = new JInternalFrameInsertLoanDetail();
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
	public JInternalFrameInsertLoanDetail() {
		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBounds(100, 100, 587, 526);
		getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("Add Loan Detail");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitle.setBounds(10, 20, 563, 30);
		getContentPane().add(lblTitle);

		jcomboBoxLoanAccount = new JComboBox<String>();
		jcomboBoxLoanAccount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jcomboBoxLoanAccount.setBounds(50, 134, 172, 30);
		getContentPane().add(jcomboBoxLoanAccount);

		JLabel lblNewLabel_1 = new JLabel("Loan account no");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(50, 92, 149, 30);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("<html>Amount ($)<font color=red> *</font></html>");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(345, 92, 110, 23);
		getContentPane().add(lblNewLabel_2);

		jtextFieldAmount = new JTextField();
		jtextFieldAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldAmount.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				updateValues();
			}

			public void removeUpdate(DocumentEvent e) {
				updateValues();
			}

			public void insertUpdate(DocumentEvent e) {
				updateValues();
			}
		});
		jtextFieldAmount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				jtextFieldAmount_keyTyped(arg0);
			}
		});
		jtextFieldAmount.setBounds(345, 131, 172, 30);
		getContentPane().add(jtextFieldAmount);
		jtextFieldAmount.setColumns(10);

		JLabel lblLoanType = new JLabel("Loan type");
		lblLoanType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLoanType.setBounds(50, 176, 125, 20);
		getContentPane().add(lblLoanType);

		jcomboBoxLoanType = new JComboBox<String>();
		jcomboBoxLoanType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateValues();
			}
		});

		jcomboBoxLoanType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jcomboBoxLoanType.setBounds(50, 208, 298, 30);
		getContentPane().add(jcomboBoxLoanType);

		JLabel lblDuration = new JLabel("<html>Duration (month)<font color=red> *</font></html>");
		lblDuration.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDuration.setBounds(345, 253, 139, 20);
		getContentPane().add(lblDuration);

		jtextFieldDuration = new JTextField();
		jtextFieldDuration.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				updateValues();
			}

			public void removeUpdate(DocumentEvent e) {
				updateValues();
			}

			public void insertUpdate(DocumentEvent e) {
				updateValues();
			}
		});
		jtextFieldDuration.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldDuration.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				jtextFieldDuration_keyTyped(arg0);
			}
		});
		jtextFieldDuration.setColumns(10);
		jtextFieldDuration.setBounds(345, 290, 172, 30);
		getContentPane().add(jtextFieldDuration);

		JLabel lblInterestRate = new JLabel("Interest rate");
		lblInterestRate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInterestRate.setBounds(50, 250, 110, 30);
		getContentPane().add(lblInterestRate);

		jtextFieldInterestRate = new JTextField();
		jtextFieldInterestRate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldInterestRate.setEditable(false);
		jtextFieldInterestRate.setColumns(10);
		jtextFieldInterestRate.setBounds(50, 288, 172, 30);
		getContentPane().add(jtextFieldInterestRate);

		JLabel lblMonthly = new JLabel("Monthly installment");
		lblMonthly.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMonthly.setBounds(345, 332, 139, 15);
		getContentPane().add(lblMonthly);

		jtextFieldMonthlyInstallment = new JTextField();
		jtextFieldMonthlyInstallment.setEditable(false);
		jtextFieldMonthlyInstallment.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldMonthlyInstallment.setColumns(10);
		jtextFieldMonthlyInstallment.setBounds(345, 357, 172, 30);
		getContentPane().add(jtextFieldMonthlyInstallment);

		JLabel lblBalanceToBe = new JLabel("Balance to be paid");
		lblBalanceToBe.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBalanceToBe.setBounds(50, 332, 149, 15);
		getContentPane().add(lblBalanceToBe);

		jtextFieldBalanceToBePaid = new JTextField();
		jtextFieldBalanceToBePaid.setEditable(false);
		jtextFieldBalanceToBePaid.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldBalanceToBePaid.setColumns(10);
		jtextFieldBalanceToBePaid.setBounds(50, 357, 172, 30);
		getContentPane().add(jtextFieldBalanceToBePaid);

		JButton jButtonAdd = new JButton("Add");
		jButtonAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonAdd.setIcon(new ImageIcon(JInternalFrameInsertLoanDetail.class.getResource("/img/add.png")));
		jButtonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					jButtonAdd_actionPerformed(arg0);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		jButtonAdd.setFont(new Font("Tahoma", Font.BOLD, 16));
		jButtonAdd.setBounds(236, 425, 120, 42);
		getContentPane().add(jButtonAdd);

		loadData();
	}

	public void loadData() {
		// textfield interest rate
		for (LoanTypes loanTypes : LoanTypesModel.find()) {
			if (loanTypes.getId() == 1) {
				jtextFieldInterestRate.setText(String.valueOf(loanTypes.getInterest_rate()));
			}
		}
		
		// JCombobox
		DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<String>();
		for (LoanTypes loanTypes : LoanTypesModel.find()) {
			defaultComboBoxModel.addElement(loanTypes.getId() + " - " + loanTypes.getName());
		}
		jcomboBoxLoanType.setModel(defaultComboBoxModel);

		DefaultComboBoxModel<String> defaultComboBoxModel1 = new DefaultComboBoxModel<String>();
		for (Customer customer : CustomerModel.findAll()) {
			defaultComboBoxModel1.addElement(customer.getLoan_account_no());
		}
		jcomboBoxLoanAccount.setModel(defaultComboBoxModel1);
	}

	// just write number and dot in jTextfield Amount
	public void jtextFieldAmount_keyTyped(KeyEvent arg0) {
		boolean ret = true;
		try {
			Double.parseDouble(jtextFieldAmount.getText() + arg0.getKeyChar());
		} catch (NumberFormatException ee) {
			ret = false;
		}
		if (!ret) {
			arg0.consume();
		}

	}

	// just write number and in jTextfield Duration
	public void jtextFieldDuration_keyTyped(KeyEvent arg0) {
		boolean ret = true;
		try {

			Integer.parseInt(jtextFieldDuration.getText() + arg0.getKeyChar());
		} catch (NumberFormatException ee) {
			ret = false;
		}
		if (!ret) {
			arg0.consume();
		}

	}

	public void jButtonAdd_actionPerformed(ActionEvent arg0) throws SQLException {
		Loan loan = new Loan();

		if (isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please fill in all fields with *");
			return;
		}

		if (!isEligible()) {
			return;
		}

		if (!jcomboBoxLoanAccount.getSelectedItem().toString().isEmpty()) {
			loan.setLoan_account_no(jcomboBoxLoanAccount.getSelectedItem().toString().trim());
		}
		if (!jtextFieldAmount.getText().isEmpty()) {
			loan.setAmount(Double.parseDouble(jtextFieldAmount.getText()));
		}
		if (!jcomboBoxLoanType.getSelectedItem().toString().isEmpty()) {
			String s[] = jcomboBoxLoanType.getSelectedItem().toString().split(" - ");
			loan.setLoan_type_id(Integer.parseInt(s[0]));
		}
		Calendar calendar = Calendar.getInstance();
		loan.setDisbursement_date(calendar.getTime());
		int duration = Integer.parseInt(jtextFieldDuration.getText());
		calendar.add(Calendar.MONTH, duration);
		loan.setTerm_date(calendar.getTime());
		if (!jtextFieldInterestRate.getText().isEmpty()) {
			loan.setInterest_rate(Float.parseFloat(jtextFieldInterestRate.getText()));
		}
		if (!jtextFieldInterestRate.getText().isEmpty()) {
			loan.setMonthly_installment(Double.parseDouble(jtextFieldInterestRate.getText()));
		}
		loan.setTotal_installment(0);
		if (!jtextFieldBalanceToBePaid.getText().isEmpty()) {
			loan.setBalance_to_be_paid(Double.parseDouble(jtextFieldBalanceToBePaid.getText()));
		}
		loan.setTotal_fine(0);
		loan.setStatus("pending");
		loan.setStaff_id(JFrameMain.getLogin_staff().getId());
		JOptionPane.showMessageDialog(null, "If the duration is long, it may take time to finish creating installments, please wait...");
		if (LoanModel.create(loan)) {
			JOptionPane.showMessageDialog(null, "Update success");
			this.setVisible(false);
			JInternalFrameLoanDetails.loadData();
		} else {
			JOptionPane.showMessageDialog(null, "Update failed");
		}
	}

	public void updateValues() {
		String[] s = jcomboBoxLoanType.getSelectedItem().toString().split(" - ");
		for (LoanTypes loanTypes : LoanTypesModel.find()) {
			if (String.valueOf(loanTypes.getId()).equalsIgnoreCase(s[0])) {
				jtextFieldInterestRate.setText(String.valueOf(loanTypes.getInterest_rate()));
			}
		}
		double amount = 0;
		int duration = 0;
		float interest_rate = 5;
		double balance = 0;
		double monthly = 0;
		try {
			amount = Double.parseDouble(jtextFieldAmount.getText());
			duration = Integer.parseInt(jtextFieldDuration.getText());
			interest_rate = Float.parseFloat(jtextFieldInterestRate.getText());
		} catch (Exception e) {
			// ignore
		}

		if (amount != 0 && duration != 0) {
			balance = Math.round((amount * (interest_rate / 100 + 1)) * 10) / 10;
			monthly = Math.ceil((balance / duration) * 10) / 10;
		}

		jtextFieldBalanceToBePaid.setText(String.valueOf(balance));
		jtextFieldMonthlyInstallment.setText(String.valueOf(monthly));
	}

	public boolean isEmpty() {
		try {
			double amount = Double.parseDouble(jtextFieldAmount.getText());
			int duration = Integer.parseInt(jtextFieldDuration.getText());
			if (amount == 0 || duration == 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isEligible() {
		Customer customer = CustomerModel.findByLoanAccountNo(jcomboBoxLoanAccount.getSelectedItem().toString());
		double monthly = Double.parseDouble(jtextFieldMonthlyInstallment.getText());
		double salary = customer.getGross_salary();
		int duration = Integer.parseInt(jtextFieldDuration.getText());
		try {
			if (duration > 600) {
				throw new Exception("Duration can't be exceeded 50 years (600 months)");
			}

			if (monthly > (salary * 15 / 100)) {
				throw new Exception("15% of your gross salary must be higher or equal to monthly installment (your gross salary : "
						+ salary + "$)");
			}

			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
	}
}
