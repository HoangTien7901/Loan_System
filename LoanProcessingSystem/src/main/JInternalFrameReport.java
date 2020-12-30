package main;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import entities.Customer;
import entities.Loan;
import models.ConnectDB;
import models.CustomerModel;
import models.LoanModel;
import tableAdjuster.TableColumnAdjuster;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Cursor;

public class JInternalFrameReport extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String report_type;
	private JPanel contentPane;
	private JTable tableReport;
	private JLabel lblTitle;
	private JPopupMenu popupMenu;
	private JMenuItem mntmLoanDetails;
	private JMonthChooser monthChooser = new JMonthChooser();
	private JYearChooser yearChooser = new JYearChooser();
	private Date today = new Date();
	private JTextField textFieldLoanAccountNo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameReport frame = new JInternalFrameReport(report_type);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param report_type
	 */
	public JInternalFrameReport(String report_type) {
		JInternalFrameReport.report_type = report_type;

		setBorder(null);
		setClosable(true);
		setMaximizable(true);
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		setBounds(253, 13, 1360, 871);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setContentPane(contentPane);

		lblTitle = new JLabel("");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTitle.setBounds(10, 10, 1274, 55);
		contentPane.add(lblTitle);

		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(tableReport, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(58, 270, 1247, 519);
		contentPane.add(scrollPane);

		popupMenu = new JPopupMenu();
		addPopup(scrollPane, popupMenu);

		tableReport = new JTable();
		tableReport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tableReport.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tableReport.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableReport);
		tableReport.setComponentPopupMenu(popupMenu);

		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 35));
		contentPane.add(lblTitle, BorderLayout.PAGE_START);

		JButton btnFind = new JButton("Search");
		btnFind.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFind.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnFind.setIcon(
				new ImageIcon(JInternalFrameFineDetails.class.getResource("/img/iconfinder_search_326690.png")));
		btnFind.setBounds(757, 121, 126, 46);
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				findReport();
			}
		});

		contentPane.add(btnFind);

		monthChooser = new JMonthChooser();
		monthChooser.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		monthChooser.getComboBox().setFont(new Font("Tahoma", Font.PLAIN, 15));
		monthChooser.getComboBox().setPreferredSize(new Dimension(120,46));
		monthChooser.setBounds(434, 121, 138, 46);
		contentPane.add(monthChooser);

		yearChooser = new JYearChooser();
		yearChooser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		yearChooser.getSpinner().setFont(new Font("Tahoma", Font.PLAIN, 15));
		yearChooser.setBounds(615, 121, 100, 46);
		contentPane.add(yearChooser);

		if (report_type.equals("fine")) {
			monthChooser.setVisible(true);
			yearChooser.setVisible(true);
		} else {
			monthChooser.setVisible(false);
			yearChooser.setVisible(false);
		}

		JButton btnReset = new JButton("Reset");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadData();
			}
		});
		btnReset.setIcon(new ImageIcon(JInternalFrameReport.class.getResource("/img/reset.png")));
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		if (report_type.equals("fine")) {
			btnReset.setVisible(false);
		}
		btnReset.setBounds(593, 204, 126, 42);
		contentPane.add(btnReset);

		JLabel lblNewLabel = new JLabel("Loan account no");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(413, 130, 126, 27);
		contentPane.add(lblNewLabel);

		textFieldLoanAccountNo = new JTextField();
		textFieldLoanAccountNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldLoanAccountNo.setBounds(549, 121, 192, 42);
		textFieldLoanAccountNo.addKeyListener(new KeyAdapter() {
			@Override
			// only word
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == ' ' || e.getKeyChar() == '"' || e.getKeyChar() == '\'') {
					e.consume();
				}
			}
		});
		if (!report_type.equals("fine")) {
			textFieldLoanAccountNo.setVisible(true);
			lblNewLabel.setVisible(true);
		} else {
			textFieldLoanAccountNo.setVisible(false);
			lblNewLabel.setVisible(false);
		}
		contentPane.add(textFieldLoanAccountNo);
		textFieldLoanAccountNo.setColumns(10);

		loadData();

		// set string for popup menu
		mntmLoanDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (report_type.equals("all")) {
					int rowIndexTable = tableReport.getSelectedRow();
					String loan_account_no = tableReport.getValueAt(rowIndexTable, 1).toString();
					showLoanDetails(loan_account_no);
				}

				if (report_type.equals("payment_due")) {
					int rowIndexTable = tableReport.getSelectedRow();
					String loan_account_no = tableReport.getValueAt(rowIndexTable, 1).toString();
					showInstallmentDetails(loan_account_no);
				}

				if (report_type.equals("fine")) {
					int rowIndexTable = tableReport.getSelectedRow();
					String loan_account_no = tableReport.getValueAt(rowIndexTable, 1).toString();
					showCustomerDetails(loan_account_no);
				}
			}
		});
		mntmLoanDetails.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mntmLoanDetails.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		popupMenu.add(mntmLoanDetails);

		// hiding title bar
		((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
	}

	// load data functions
	public void loadData() {
		if (report_type.equals("all")) {
			tableReport.setModel(createAllCustomersTable());
		}

		if (report_type.equals("payment_due")) {
			tableReport.setModel(createPaymentDueTable());
		}

		if (report_type.equals("fine")) {
			tableReport.setModel(createFinesTable());
		}

		tableReport.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// modify table
		tableReport.setRowHeight(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableReport.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = tableReport.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		TableColumnAdjuster tca = new TableColumnAdjuster(tableReport);
		tca.adjustColumns();
	}

	public void findReport() {
		if (report_type.equals("all")) {
			tableReport.setModel(findCustomerReports());
		}

		if (report_type.equals("payment_due")) {
			tableReport.setModel(findPaymentDueReports());
		}

		if (report_type.equals("fine")) {
			tableReport.setModel(findFineReports());
		}

		tableReport.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// modify table
		tableReport.setRowHeight(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableReport.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = tableReport.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		TableColumnAdjuster tca = new TableColumnAdjuster(tableReport);
		tca.adjustColumns();
	}

	public DefaultTableModel findCustomerReports() {
		DefaultTableModel defaultTableModel = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5286773014964612433L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		mntmLoanDetails = new JMenuItem("Show loans list");
		String loan_account_no = textFieldLoanAccountNo.getText().toLowerCase();

		defaultTableModel.addColumn("No.");
		defaultTableModel.addColumn("Loan account no");
		defaultTableModel.addColumn("Customer name");
		defaultTableModel.addColumn("Phone number");
		defaultTableModel.addColumn("Email");
		defaultTableModel.addColumn("Address");
		defaultTableModel.addColumn("Organization");
		defaultTableModel.addColumn("Organization name");
		defaultTableModel.addColumn("Gross salary");
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(""
					+ "SELECT cp.loan_account_no as loan_account_no, cp.first_name as first_name, "
					+ "cp.last_name as last_name, cp.phone_number as phone_number, cp.email as email, "
					+ "cp.address as address, og.name as organization, cp.organization_name as organization_name, "
					+ "cp.gross_salary as salary " + "FROM customer_profiles cp, organizations og "
					+ "WHERE og.id = cp.organization_id and cp.loan_account_no = ?");
			preparedStatement.setString(1, loan_account_no);

			ResultSet resultSet = preparedStatement.executeQuery();
			int i = 0;
			while (resultSet.next()) {
				defaultTableModel.addRow(new Object[] { ++i, resultSet.getObject("loan_account_no"),
						resultSet.getObject("first_name") + " " + resultSet.getObject("last_name"),
						resultSet.getObject("phone_number"), resultSet.getObject("email"),
						resultSet.getObject("address"), resultSet.getObject("organization"),
						resultSet.getObject("organization_name") == null ? "no"
								: resultSet.getObject("organization_name"),
						resultSet.getObject("salary") });
				i++;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return defaultTableModel;
	}

	public DefaultTableModel findPaymentDueReports() {
		DefaultTableModel defaultTableModel = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		mntmLoanDetails = new JMenuItem("Show due installment details");
		String loan_account_no = textFieldLoanAccountNo.getText().toLowerCase();

		defaultTableModel.addColumn("No.");
		defaultTableModel.addColumn("Loan account no");
		defaultTableModel.addColumn("Customer name");
		defaultTableModel.addColumn("Phone number");
		defaultTableModel.addColumn("Email");
		defaultTableModel.addColumn("Address");
		defaultTableModel.addColumn("Gross salary");
		defaultTableModel.addColumn("Organization");
		defaultTableModel.addColumn("Organization name");
		defaultTableModel.addColumn("No of due payment");

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement("SELECT\r\n"
					+ "    cp.*,\r\n" + "    (\r\n" + "    SELECT\r\n"
					+ "        COUNT(IF(id.status = \"due\", 1, NULL))\r\n" + "    FROM\r\n"
					+ "        installment_details id\r\n" + "		where" + "			id.loan_details_id = ld.id\r\n"
					+ ") AS no_of_due_payment,\r\n" + "og.name AS organization\r\n" + "FROM\r\n"
					+ "    loan_details ld,\r\n" + "    customer_profiles cp,\r\n" + "    installment_details id,\r\n"
					+ "    organizations og\r\n" + "WHERE\r\n"
					+ "    ld.loan_account_no = cp.loan_account_no AND cp.loan_account_no = ? AND id.loan_details_id = ld.id AND og.id = cp.organization_id\r\n"
					+ "GROUP BY\r\n" + "    cp.loan_account_no");
			preparedStatement.setString(1, loan_account_no);
			ResultSet resultSet = preparedStatement.executeQuery();
			int i = 0;
			while (resultSet.next()) {
				defaultTableModel
						.addRow(new Object[] { ++i, resultSet.getObject("loan_account_no"),
								resultSet.getObject("first_name") + " " + resultSet.getObject("last_name"),
								resultSet.getObject("phone_number"), resultSet.getObject("email"),
								resultSet.getObject("address"), resultSet.getObject("gross_salary"),
								resultSet.getObject("organization"),
								resultSet.getObject("organization_name") == null ? "no"
										: resultSet.getObject("organization_name"),
								resultSet.getObject("no_of_due_payment") });
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return defaultTableModel;
	}

	public DefaultTableModel findFineReports() {
		DefaultTableModel defaultTableModel = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		mntmLoanDetails = new JMenuItem("Show customer details");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);

		int month = monthChooser.getMonth() + 1;
		int year = yearChooser.getYear();

		lblTitle.setText("Fine report of " + month + "/" + year);

		defaultTableModel.addColumn("No.");
		defaultTableModel.addColumn("Loan account no");
		defaultTableModel.addColumn("Installment details id");
		defaultTableModel.addColumn("Amount");
		defaultTableModel.addColumn("Created date");
		defaultTableModel.addColumn("Payday");
		defaultTableModel.addColumn("Status");

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT fd.*, cp.loan_account_no "
							+ "FROM fine_details fd, installment_details id, loan_details ld, customer_profiles cp "
							+ "WHERE month(fd.created_date) = ? and year(fd.created_date) = ? and fd.installment_details_id = id.id and id.loan_details_id = ld.id "
							+ "and ld.loan_account_no = cp.loan_account_no");
			preparedStatement.setInt(1, month);
			preparedStatement.setInt(2, year);

			ResultSet resultSet = preparedStatement.executeQuery();
			int i = 0;
			while (resultSet.next()) {
				defaultTableModel.addRow(new Object[] { ++i, resultSet.getObject("loan_account_no"),
						resultSet.getObject("installment_details_id"), resultSet.getObject("amount"),
						resultSet.getObject("created_date"),
						resultSet.getObject("payday") == null ? "no" : resultSet.getObject("payday"),
						resultSet.getObject("status") });
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return defaultTableModel;
	}

	// ############
	// create table model functions
	public DefaultTableModel createAllCustomersTable() {
		lblTitle.setText("All customers' report");

		DefaultTableModel defaultTableModel = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5286773014964612433L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		mntmLoanDetails = new JMenuItem("Show loans list");

		defaultTableModel.addColumn("No.");
		defaultTableModel.addColumn("Loan account no");
		defaultTableModel.addColumn("Customer name");
		defaultTableModel.addColumn("Phone number");
		defaultTableModel.addColumn("Email");
		defaultTableModel.addColumn("Address");
		defaultTableModel.addColumn("Organization");
		defaultTableModel.addColumn("Organization name");
		defaultTableModel.addColumn("Gross salary");
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(""
					+ "SELECT cp.loan_account_no as loan_account_no, cp.first_name as first_name, "
					+ "cp.last_name as last_name, cp.phone_number as phone_number, cp.email as email, "
					+ "cp.address as address, og.name as organization, cp.organization_name as organization_name, "
					+ "cp.gross_salary as salary " + "FROM customer_profiles cp, organizations og "
					+ "WHERE og.id = cp.organization_id");

			ResultSet resultSet = preparedStatement.executeQuery();
			int i = 0;
			while (resultSet.next()) {
				defaultTableModel.addRow(new Object[] { ++i, resultSet.getObject("loan_account_no"),
						resultSet.getObject("first_name") + " " + resultSet.getObject("last_name"),
						resultSet.getObject("phone_number"), resultSet.getObject("email"),
						resultSet.getObject("address"), resultSet.getObject("organization"),
						resultSet.getObject("organization_name") == null ? "no"
								: resultSet.getObject("organization_name"),
						resultSet.getObject("salary") });
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return defaultTableModel;
	}

	public DefaultTableModel createPaymentDueTable() {
		lblTitle.setText("Payment due report");

		DefaultTableModel defaultTableModel = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		mntmLoanDetails = new JMenuItem("Show due installment details");

		defaultTableModel.addColumn("No.");
		defaultTableModel.addColumn("Loan account no");
		defaultTableModel.addColumn("Customer name");
		defaultTableModel.addColumn("Phone number");
		defaultTableModel.addColumn("Email");
		defaultTableModel.addColumn("Address");
		defaultTableModel.addColumn("Gross salary");
		defaultTableModel.addColumn("Organization");
		defaultTableModel.addColumn("Organization name");
		defaultTableModel.addColumn("No of due payment");

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement("SELECT\r\n"
					+ "    cp.*,\r\n" + "    (\r\n" + "    SELECT\r\n"
					+ "        COUNT(IF(id.status = \"due\", 1, NULL))\r\n" + "    FROM\r\n"
					+ "        installment_details id\r\n" + "		where" + "			id.loan_details_id = ld.id\r\n"
					+ ") AS no_of_due_payment,\r\n" + "og.name AS organization\r\n" + "FROM\r\n"
					+ "    loan_details ld,\r\n" + "    customer_profiles cp,\r\n" + "    installment_details id,\r\n"
					+ "    organizations og\r\n" + "WHERE\r\n"
					+ "    ld.loan_account_no = cp.loan_account_no AND id.loan_details_id = ld.id AND og.id = cp.organization_id\r\n"
					+ "GROUP BY\r\n" + "    cp.loan_account_no");

			ResultSet resultSet = preparedStatement.executeQuery();
			int i = 0;
			while (resultSet.next()) {
				if ((long) resultSet.getObject("no_of_due_payment") > 0) {
					defaultTableModel
					.addRow(new Object[] { ++i, resultSet.getObject("loan_account_no"),
							resultSet.getObject("first_name") + " " + resultSet.getObject("last_name"),
							resultSet.getObject("phone_number"), resultSet.getObject("email"),
							resultSet.getObject("address"), resultSet.getObject("gross_salary"),
							resultSet.getObject("organization"),
							resultSet.getObject("organization_name") == null ? "no"
									: resultSet.getObject("organization_name"),
							resultSet.getObject("no_of_due_payment") });
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return defaultTableModel;
	}

	public DefaultTableModel createFinesTable() {
		SimpleDateFormat simpleDF = new SimpleDateFormat("MM/yyyy");
		Date today = new Date();
		lblTitle.setText("Fine report of " + simpleDF.format(today));

		DefaultTableModel defaultTableModel = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		mntmLoanDetails = new JMenuItem("Show customer details");

		defaultTableModel.addColumn("No.");
		defaultTableModel.addColumn("Loan account no");
		defaultTableModel.addColumn("Installment details id");
		defaultTableModel.addColumn("Amount");
		defaultTableModel.addColumn("Created date");
		defaultTableModel.addColumn("Payday");
		defaultTableModel.addColumn("Status");

		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT fd.*, cp.loan_account_no "
							+ "FROM fine_details fd, installment_details id, loan_details ld, customer_profiles cp "
							+ "WHERE fd.installment_details_id = id.id and id.loan_details_id = ld.id "
							+ "and ld.loan_account_no = cp.loan_account_no");

			ResultSet resultSet = preparedStatement.executeQuery();
			int i = 0;
			while (resultSet.next()) {
				defaultTableModel.addRow(new Object[] { ++i, resultSet.getObject("loan_account_no"),
						resultSet.getObject("installment_details_id"), resultSet.getObject("amount"),
						resultSet.getObject("created_date"),
						resultSet.getObject("payday") == null ? "no" : resultSet.getObject("payday"),
						resultSet.getObject("status") });
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return defaultTableModel;
	}
	// ############

	// show inframe functions :
	public void showLoanDetails(String loan_account_no) {
		try {
			List<Loan> loans_list = LoanModel.findByLoanAccountNo(loan_account_no);

			if (loans_list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "This customer doesn't have any loan");
			} else {
				JInternalFrameLoansListForReport jInternalFrameLoansListForReport = new JInternalFrameLoansListForReport(
						loans_list);
				getDesktopPane().add(jInternalFrameLoansListForReport);
				jInternalFrameLoansListForReport.toFront();
				jInternalFrameLoansListForReport.setVisible(true);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void showInstallmentDetails(String loan_account_no) {
		try {
			List<Loan> loans_list = LoanModel.findByLoanAccountNo(loan_account_no);
			JInternalFrameInstallmentDetailsForReport jInternalFrameInstallmentDetailsForReport = new JInternalFrameInstallmentDetailsForReport(
					loans_list);

			getDesktopPane().add(jInternalFrameInstallmentDetailsForReport);
			jInternalFrameInstallmentDetailsForReport.toFront();
			jInternalFrameInstallmentDetailsForReport.setVisible(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void showCustomerDetails(String loan_account_no) {
		try {
			Customer customer = CustomerModel.findByLoanAccountNo(loan_account_no);
			JInternalFrameCustomerDetailsForReport jInternalFrameCustomerDetailsForReport = new JInternalFrameCustomerDetailsForReport(
					customer);

			getDesktopPane().add(jInternalFrameCustomerDetailsForReport);
			jInternalFrameCustomerDetailsForReport.toFront();
			jInternalFrameCustomerDetailsForReport.setVisible(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// other function
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
