package main;

import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import entities.Customer;
import entities.Loan;
import entities.LoanTypes;
import entities.Staff;
import models.CustomerModel;
import models.LoanModel;
import models.LoanTypesModel;
import models.StaffModel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.SwingConstants;

public class JInternalFrameUpdateLoanDetails extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int id;
	private JTextField jtextFieldAmount;
	private JTextField jtextFieldBalanceToBePaid;
	private JTextField jtextFieldDuration;
	private JTextField jtextFieldMonthlyInstallment;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JDateChooser jdateChooserDisbursalDate;
	private JComboBox<String> jcomboBoxLoanTypeId;
	private JComboBox<String> jcomboBoxStaff;
	private JRadioButton jradioButtonPending;
	private JRadioButton jradioButtonCancel;
	private JTextField textFieldInterestRate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameUpdateLoanDetails frame = new JInternalFrameUpdateLoanDetails(id);
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
	public JInternalFrameUpdateLoanDetails(int id) {
		JInternalFrameUpdateLoanDetails.id = id;
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBounds(100, 100, 570, 676);
		getContentPane().setLayout(null);

		JButton jButtonUpdate = new JButton("Update");
		jButtonUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
		jButtonUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonUpdate.setIcon(new ImageIcon(JInternalFrameUpdateInstallmentDetails.class.getResource("/img/update.png")));
		jButtonUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jButtonUpdate_actionPerformed(arg0, id);
			}
		});
		jButtonUpdate.setBounds(231, 563, 126, 42);
		getContentPane().add(jButtonUpdate);
		JLabel lblLoanAccountDetail = new JLabel("<html>Amount <font color=red>*</font></html>");
		lblLoanAccountDetail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLoanAccountDetail.setBounds(31, 98, 65, 16);
		getContentPane().add(lblLoanAccountDetail);

		jtextFieldAmount = new JTextField();
		jtextFieldAmount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
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
		});

		jtextFieldAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldAmount.setColumns(10);
		jtextFieldAmount.setBounds(31, 126, 221, 28);
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
		getContentPane().add(jtextFieldAmount);

		JLabel lblLoanTypeid = new JLabel("Loan type");
		lblLoanTypeid.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLoanTypeid.setBounds(31, 189, 90, 16);
		getContentPane().add(lblLoanTypeid);

		JLabel lblDisbursalDate = new JLabel("Disbursement date");
		lblDisbursalDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDisbursalDate.setBounds(31, 304, 134, 16);
		getContentPane().add(lblDisbursalDate);

		JLabel lblDuration = new JLabel("<html>Duration <font color=red>*</font></html>");
		lblDuration.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDuration.setBounds(301, 304, 90, 16);
		getContentPane().add(lblDuration);

		JLabel lblMonthlyinstallment = new JLabel("Monthly installment");
		lblMonthlyinstallment.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMonthlyinstallment.setBounds(31, 388, 150, 16);
		getContentPane().add(lblMonthlyinstallment);

		JLabel lblBalanceToBe = new JLabel("Balance to be paid");
		lblBalanceToBe.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBalanceToBe.setBounds(301, 98, 150, 16);
		getContentPane().add(lblBalanceToBe);

		jtextFieldBalanceToBePaid = new JTextField();
		jtextFieldBalanceToBePaid.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldBalanceToBePaid.setBorder(null);
		jtextFieldBalanceToBePaid.setColumns(10);
		jtextFieldBalanceToBePaid.setBounds(301, 126, 221, 28);
		jtextFieldBalanceToBePaid.setEditable(false);
		getContentPane().add(jtextFieldBalanceToBePaid);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStatus.setBounds(301, 388, 65, 16);
		getContentPane().add(lblStatus);

		JLabel lblStaff = new JLabel("Staff");
		lblStaff.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStaff.setBounds(31, 486, 122, 16);
		getContentPane().add(lblStaff);

		jcomboBoxStaff = new JComboBox<String>();
		jcomboBoxStaff.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jcomboBoxStaff.setBounds(28, 512, 494, 26);
		getContentPane().add(jcomboBoxStaff);

		jdateChooserDisbursalDate = new JDateChooser();
		jdateChooserDisbursalDate.setDateFormatString("dd/MM/yyyy");
		jdateChooserDisbursalDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jdateChooserDisbursalDate.setBounds(31, 331, 221, 28);
		getContentPane().add(jdateChooserDisbursalDate);

		jradioButtonPending = new JRadioButton("Pending");
		jradioButtonPending.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jradioButtonPending.setSelected(true);
		buttonGroup.add(jradioButtonPending);
		jradioButtonPending.setBackground(Color.WHITE);
		jradioButtonPending.setBounds(401, 388, 109, 23);

		getContentPane().add(jradioButtonPending);

		jradioButtonCancel = new JRadioButton("Cancel");
		jradioButtonCancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonGroup.add(jradioButtonCancel);
		jradioButtonCancel.setBackground(Color.WHITE);
		jradioButtonCancel.setBounds(400, 433, 109, 23);
		getContentPane().add(jradioButtonCancel);

		jcomboBoxLoanTypeId = new JComboBox<String>();
		jcomboBoxLoanTypeId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jcomboBoxLoanTypeId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jcomboBoxLoanTypeId_actionPerformed(arg0);
			}
		});
		jcomboBoxLoanTypeId.setBounds(153, 184, 262, 28);
		getContentPane().add(jcomboBoxLoanTypeId);
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
		jtextFieldDuration.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean ret = true;
				try {
					Integer.parseInt(jtextFieldDuration.getText() + e.getKeyChar());
				} catch (NumberFormatException ee) {
					ret = false;
				}
				if (!ret) {
					e.consume();
				}
			}
		});
		jtextFieldDuration.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldDuration.setColumns(10);
		jtextFieldDuration.setBounds(301, 331, 221, 28);
		getContentPane().add(jtextFieldDuration);
		jtextFieldMonthlyInstallment = new JTextField();
		jtextFieldMonthlyInstallment.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean ret = true;
				try {
					Double.parseDouble(jtextFieldMonthlyInstallment.getText() + e.getKeyChar());
				} catch (NumberFormatException ee) {
					ret = false;
				}
				if (!ret) {
					e.consume();
				}
			}
		});
		jtextFieldMonthlyInstallment.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldMonthlyInstallment.setColumns(10);
		jtextFieldMonthlyInstallment.setEditable(false);
		jtextFieldMonthlyInstallment.setBounds(31, 415, 221, 28);
		getContentPane().add(jtextFieldMonthlyInstallment);
		JLabel lblNewLabel = new JLabel("Loan Detail Update");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(27, 11, 529, 55);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Interest rate");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(31, 239, 90, 28);
		getContentPane().add(lblNewLabel_1);

		textFieldInterestRate = new JTextField();
		textFieldInterestRate.setEditable(false);
		textFieldInterestRate.setText("0");
		textFieldInterestRate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldInterestRate.setColumns(10);
		textFieldInterestRate.setBounds(153, 241, 142, 28);
		getContentPane().add(textFieldInterestRate);
		combobox();
		loadData(JInternalFrameUpdateLoanDetails.id);
		this.setClosable(true);
	}

	public void loadData(int id) {
		Loan loan = LoanModel.findById(id);
		jtextFieldAmount.setText(Double.toString(loan.getAmount()));
		jtextFieldBalanceToBePaid.setText(String.valueOf(loan.getBalance_to_be_paid()));
		jtextFieldMonthlyInstallment.setText(String.valueOf(loan.getMonthly_installment()));
		jcomboBoxLoanTypeId.setSelectedIndex(loan.getLoan_type_id() - 1);
		for (int i = 0; i < jcomboBoxStaff.getItemCount(); i++) {
			String item = ((String) jcomboBoxStaff.getItemAt(i));
			if (item.startsWith(String.valueOf(loan.getStaff_id()))) {
				jcomboBoxStaff.setSelectedIndex(i);
				break;
			}
		}
		jdateChooserDisbursalDate.setDate(loan.getDisbursement_date());
		if (loan.getStatus().equalsIgnoreCase("pending")) {
			jradioButtonPending.setSelected(true);
		} else {
			jradioButtonCancel.setSelected(true);
		}
		
		LocalDate dispursal_date = loan.getDisbursement_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate term_date = loan.getTerm_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Period diff = Period.between(dispursal_date, term_date);
		
		int month = diff.getMonths();
		
		int year = diff.getYears();
		int duration = year * 12 + month;
		jtextFieldDuration.setText(String.valueOf(duration));
	}

	public void combobox() {
		DefaultComboBoxModel<String> Loan_typeId = new DefaultComboBoxModel<String>();
		for (LoanTypes loanType : LoanTypesModel.find()) {
			Loan_typeId.addElement(loanType.getId() + " - " + loanType.getName());
		}
		jcomboBoxLoanTypeId.setModel(Loan_typeId);

		DefaultComboBoxModel<String> cbbStaff = new DefaultComboBoxModel<String>();
		for (Staff staff : StaffModel.findById(jcomboBoxLoanTypeId.getSelectedIndex() + 1)) {
			cbbStaff.addElement(staff.getId() + " - " + staff.getFirst_name() + " " + staff.getLast_name());
		}
		jcomboBoxStaff.setModel(cbbStaff);
	}

	public void jcomboBoxLoanTypeId_actionPerformed(ActionEvent arg0) {
		DefaultComboBoxModel<String> cbbStaff = new DefaultComboBoxModel<String>();
		for (Staff staff : StaffModel.findById(jcomboBoxLoanTypeId.getSelectedIndex() + 1)) {
			cbbStaff.addElement(staff.getId() + " - " + staff.getFirst_name() + " " + staff.getLast_name());
		}
		jcomboBoxStaff.setModel(cbbStaff);
		updateValues();
	}

	public void jButtonUpdate_actionPerformed(ActionEvent arg0, int id) {
		try {
			if (jtextFieldAmount.getText().trim().isEmpty() || jtextFieldDuration.getText().trim().isEmpty()) {
				throw new Exception("Please fill in all fields");
			}

			if (!isEligible()) {
				return;
			}

			int result = JOptionPane.showConfirmDialog(null,
					"If you change amount, duration, loan type or dispursal date of the loan, it will also renew all of its installments,fines and \n"
					+ "related messages (set installments status to pending and delete all fines, message) and need sometime to process, continue?",
					"Confirm", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				Calendar calendar = Calendar.getInstance();
				Loan loan = new Loan();
				loan.setId(JInternalFrameUpdateLoanDetails.id);
				loan.setAmount(Double.parseDouble(jtextFieldAmount.getText().trim()));
				loan.setLoan_type_id(jcomboBoxLoanTypeId.getSelectedIndex() + 1);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date date;
				try {
					date = simpleDateFormat.parse(simpleDateFormat.format(jdateChooserDisbursalDate.getDate()));
					loan.setDisbursement_date(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// calculate term date based on duration
				calendar.setTime(loan.getDisbursement_date());
				calendar.add(Calendar.MONTH, Integer.parseInt(jtextFieldDuration.getText()));
				loan.setTerm_date(calendar.getTime());
				
				for (LoanTypes loanType : LoanTypesModel.find()) {
					if (loanType.getId() == (jcomboBoxLoanTypeId.getSelectedIndex() + 1)) {
						loan.setInterest_rate(loanType.getInterest_rate());
					}
				}
				loan.setAmount(Double.parseDouble(jtextFieldAmount.getText()));
				loan.setBalance_to_be_paid(Double.parseDouble(jtextFieldBalanceToBePaid.getText()));
				if (jradioButtonCancel.isSelected()) {
					loan.setStatus("cancel");
				} else {
					loan.setStatus("pending");
				}
				loan.setStaff_id(Integer.parseInt(String.valueOf(jcomboBoxStaff.getSelectedItem()).substring(0, 1)));
				if (LoanModel.update(loan)) {
					JOptionPane.showMessageDialog(null, "Update success");
					this.setVisible(false);
					JInternalFrameLoanDetails.loadData();
				} else {
					JOptionPane.showMessageDialog(null, "Update failed");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void updateValues() {
		String[] s = jcomboBoxLoanTypeId.getSelectedItem().toString().split(" - ");
		for (LoanTypes loanTypes : LoanTypesModel.find()) {
			if (String.valueOf(loanTypes.getId()).equalsIgnoreCase(s[0])) {
				textFieldInterestRate.setText(String.valueOf(loanTypes.getInterest_rate()));
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
			interest_rate = Float.parseFloat(textFieldInterestRate.getText());

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

	public boolean isEligible() {
		String loan_account_no = LoanModel.findById(id).getLoan_account_no();
		Customer customer = CustomerModel.findByLoanAccountNo(loan_account_no);
		double monthly = Double.parseDouble(jtextFieldMonthlyInstallment.getText());
		float interest_rate = Float.parseFloat(textFieldInterestRate.getText());
		double salary = customer.getGross_salary();
		int duration = Integer.parseInt(jtextFieldDuration.getText());
		try {
			if (duration > 600) {
				throw new Exception("Duration can't be exceeded 50 years (600 months)");
			}

			if (monthly > (salary * (interest_rate + 5) / 100)) {
				throw new Exception(interest_rate + 5
						+ "% of your gross salary must be higher or equal to monthly installment (your gross salary : "
						+ salary + "$)");
			}

			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
	}
}