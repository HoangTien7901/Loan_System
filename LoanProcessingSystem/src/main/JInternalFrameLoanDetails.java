package main;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entities.Loan;
import models.LoanModel;
import models.LoanTypesModel;
import models.ValidationModel;
import tableAdjuster.TableColumnAdjuster;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;
import java.awt.Cursor;

public class JInternalFrameLoanDetails extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JTable jtableLoanDetails;
	private JTextField jtextFieldLoanAmountStart;
	private JDateChooser jdateChooserStart;
	private JDateChooser jdateChooserEnd;
	private JTextField jtextFieldLoanAmountEnd;
	private JTextField jtextFieldLoanAccounNo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameLoanDetails frame = new JInternalFrameLoanDetails();
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
	public JInternalFrameLoanDetails() {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 18));
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		getContentPane().setLayout(null);

		setMaximizable(true);
		setBounds(0, 0, 1350, 871);

		JButton jButtonSearch = new JButton("Search");
		jButtonSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonSearch.setIcon(
				new ImageIcon(JInternalFrameLoanDetails.class.getResource("/img/iconfinder_search_326690.png")));
		jButtonSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
		jButtonSearch.setBounds(930, 133, 126, 42);
		jButtonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jButtonSearch_actionPerformed(arg0);
			}
		});
		getContentPane().add(jButtonSearch);

		JScrollPane jscrollPane = new JScrollPane();
		jscrollPane.setBounds(58, 362, 1247, 412);
		getContentPane().add(jscrollPane);

		jtableLoanDetails = new JTable();
		jtableLoanDetails.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jtableLoanDetails.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtableLoanDetails.setFont(new Font("Tahoma", Font.PLAIN, 15));
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		addPopup(jtableLoanDetails, popupMenu);

		JMenuItem jMenuItemInstallmentDetail = new JMenuItem("Installment Detail");
		jMenuItemInstallmentDetail.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jMenuItemInstallmentDetail.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		jMenuItemInstallmentDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// here
				jMenuItemInstallmentDetail_actionPerformed(arg0);
			}
		});
		popupMenu.add(jMenuItemInstallmentDetail);

		JMenuItem jMenuItemUpdate = new JMenuItem("Update");
		jMenuItemUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jMenuItemUpdate.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		jMenuItemUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jMenuItemUpdate_actionPerformed(arg0);
			}
		});
		popupMenu.add(jMenuItemUpdate);
		jscrollPane.setViewportView(jtableLoanDetails);

		jtextFieldLoanAmountStart = new JTextField();
		jtextFieldLoanAmountStart.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldLoanAmountStart.setBounds(380, 158, 211, 37);
		jtextFieldLoanAmountStart.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				jtextFieldLoanAmountStart_keyTyped(arg0);
			}
		});
		jtextFieldLoanAmountStart.setColumns(10);
		getContentPane().add(jtextFieldLoanAmountStart);

		JLabel lblLoanAccountNo = new JLabel("Loan Account no");
		lblLoanAccountNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLoanAccountNo.setBounds(205, 105, 142, 20);
		getContentPane().add(lblLoanAccountNo);

		JLabel lblLoanAmount = new JLabel("Loan amount from");
		lblLoanAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLoanAmount.setBounds(205, 164, 165, 31);
		getContentPane().add(lblLoanAmount);

		JLabel lblDate = new JLabel("Date from");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDate.setBounds(205, 232, 108, 23);
		getContentPane().add(lblDate);

		jdateChooserStart = new JDateChooser();
		jdateChooserStart.setBounds(380, 218, 211, 37);
		jdateChooserStart.setDateFormatString("dd/MM/yyyy");
		jdateChooserStart.setFont(new Font("Tahoma", Font.PLAIN, 15));
		getContentPane().add(jdateChooserStart);

		JLabel lblEnd = new JLabel("to");
		lblEnd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEnd.setBounds(621, 229, 42, 20);
		getContentPane().add(lblEnd);

		jdateChooserEnd = new JDateChooser();
		jdateChooserEnd.setBounds(658, 218, 211, 37);
		jdateChooserEnd.setDateFormatString("dd/MM/yyyy");
		jdateChooserEnd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		getContentPane().add(jdateChooserEnd);
		// Reset function
		JButton jButtonReset = new JButton("Reset");
		jButtonReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonReset.setIcon(new ImageIcon(JInternalFrameLoanDetails.class.getResource("/img/reset.png")));
		jButtonReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		jButtonReset.setBounds(933, 185, 120, 42);
		jButtonReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					loadData();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jtextFieldLoanAccounNo.setText(null);
				jtextFieldLoanAmountStart.setText(null);
				jtextFieldLoanAmountEnd.setText(null);
				jdateChooserStart.setDate(null);
				jdateChooserEnd.setDate(null);
			}
		});
		getContentPane().add(jButtonReset);
		jtextFieldLoanAmountEnd = new JTextField();
		jtextFieldLoanAmountEnd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldLoanAmountEnd.setBounds(658, 158, 211, 37);
		jtextFieldLoanAmountEnd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				jtextFieldLoanAmountEnd_keyTyped(arg0);
			}
		});
		jtextFieldLoanAmountEnd.setColumns(10);
		getContentPane().add(jtextFieldLoanAmountEnd);

		JLabel lblNewLabel = new JLabel("to");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(621, 165, 34, 18);
		getContentPane().add(lblNewLabel);

		jtextFieldLoanAccounNo = new JTextField();
		jtextFieldLoanAccounNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldLoanAccounNo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (ValidationModel.isSpecialCharacterOrSpace(e.getKeyChar())) {
					e.consume();
				}
			}
		});
		jtextFieldLoanAccounNo.setBounds(380, 99, 211, 37);
		getContentPane().add(jtextFieldLoanAccounNo);
		jtextFieldLoanAccounNo.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Loan details list");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 10, 1326, 64);
		getContentPane().add(lblNewLabel_1);

		JButton btnAdd = new JButton("Add");
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.setIcon(new ImageIcon(JInternalFrameLoanDetails.class.getResource("/img/add.png")));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showInsertFrame();
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAdd.setBounds(492, 296, 120, 42);
		getContentPane().add(btnAdd);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setIcon(new ImageIcon(JInternalFrameLoanDetails.class.getResource("/img/delete.png")));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				delete();
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnDelete.setBounds(643, 296, 124, 42);
		getContentPane().add(btnDelete);

		try {
			loadData();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
	}

	public static void loadData() throws SQLException {
		DefaultTableModel defaultTableModel = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// khong cho user edit table
				return false;
			}
		};
		defaultTableModel.addColumn("Id");
		defaultTableModel.addColumn("Loan account no");
		defaultTableModel.addColumn("Amount");
		defaultTableModel.addColumn("Loan type");
		defaultTableModel.addColumn("Disbursement date");
		defaultTableModel.addColumn("Term date");
		defaultTableModel.addColumn("Interest rate");
		defaultTableModel.addColumn("Monthly installment");
		defaultTableModel.addColumn("Total installment");
		defaultTableModel.addColumn("Balance to be paid");
		defaultTableModel.addColumn("Total fine");
		defaultTableModel.addColumn("Status");
		defaultTableModel.addColumn("Staff id");
		for (Loan loan : LoanModel.findAll()) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String start = simpleDateFormat.format(loan.getDisbursement_date());
			String end = simpleDateFormat.format(loan.getTerm_date());
			defaultTableModel.addRow(new Object[] { loan.getId(), loan.getLoan_account_no(), loan.getAmount(),
					LoanTypesModel.findById(loan.getLoan_type_id()).getName(), start, end, loan.getInterest_rate(), loan.getMonthly_installment(),
					loan.getTotal_installment(), loan.getBalance_to_be_paid(), loan.getTotal_fine(), loan.getStatus(),
					loan.getStaff_id() });
		}

		jtableLoanDetails.setModel(defaultTableModel);

		// modify table
		jtableLoanDetails.setRowHeight(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		jtableLoanDetails.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = jtableLoanDetails.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		jtableLoanDetails.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnAdjuster tca = new TableColumnAdjuster(jtableLoanDetails);
		tca.adjustColumns();
	}

	// search by account amount,date
	public void loadData_SearchAccount_Amount_Date(String account_loan_no, String amount_start, String amount_end,
			String disbursement_date, String term_date0) throws SQLException {
		DefaultTableModel defaultTableModel = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// khong cho user edit table
				return false;
			}
		};
		defaultTableModel.addColumn("Id");
		defaultTableModel.addColumn("Loan account no");
		defaultTableModel.addColumn("Amount");
		defaultTableModel.addColumn("Loan type");
		defaultTableModel.addColumn("Disbursement date");
		defaultTableModel.addColumn("Term date");
		defaultTableModel.addColumn("Interest rate");
		defaultTableModel.addColumn("Monthly installment");
		defaultTableModel.addColumn("Total installment");
		defaultTableModel.addColumn("Balance to be paid");
		defaultTableModel.addColumn("Total fine");
		defaultTableModel.addColumn("Status");
		defaultTableModel.addColumn("Staff id");
		List<Loan> loans = LoanModel.findByAccountAmountDate(account_loan_no, amount_start, amount_end, disbursement_date,
				term_date0);
		for (Loan loan : loans) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String start = simpleDateFormat.format(loan.getDisbursement_date());
			String end = simpleDateFormat.format(loan.getTerm_date());
			defaultTableModel.addRow(new Object[] { loan.getId(), loan.getLoan_account_no(), loan.getAmount(),
					LoanTypesModel.findById(loan.getLoan_type_id()).getName(), start, end, loan.getInterest_rate(), loan.getMonthly_installment(),
					loan.getTotal_installment(), loan.getBalance_to_be_paid(), loan.getTotal_fine(), loan.getStatus(),
					loan.getStaff_id()  });
		}
		jtableLoanDetails.setModel(defaultTableModel);

		// modify table
		jtableLoanDetails.setRowHeight(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		jtableLoanDetails.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = jtableLoanDetails.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		jtableLoanDetails.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnAdjuster tca = new TableColumnAdjuster(jtableLoanDetails);
		tca.adjustColumns();
	}

	// search
	public void jButtonSearch_actionPerformed(ActionEvent arg0) {
		String account_loan_no = "%%";
		String amount_start = "0";
		String amount_end = "999999999";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String disbursement_date = "1999-10-10";
		String term_date0 = "2100-10-10";
		if (!jtextFieldLoanAccounNo.getText().isEmpty()) {
			account_loan_no = "%" + jtextFieldLoanAccounNo.getText() + "%";
		}
		if (!jtextFieldLoanAmountStart.getText().trim().isEmpty()) {
			amount_start = jtextFieldLoanAmountStart.getText().trim();
		}
		if (!jtextFieldLoanAmountEnd.getText().trim().isEmpty()) {
			amount_end = jtextFieldLoanAmountEnd.getText().trim();
		}
		if (jdateChooserStart.getDate() != null) {
			disbursement_date = simpleDateFormat.format(jdateChooserStart.getDate());
		}
		if (jdateChooserEnd.getDate() != null) {
			term_date0 = simpleDateFormat.format(jdateChooserEnd.getDate());
		}
		try {
			loadData_SearchAccount_Amount_Date(account_loan_no, amount_start, amount_end, disbursement_date, term_date0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Update Loan Detail
	public void jMenuItemUpdate_actionPerformed(ActionEvent arg0) {
		int selectedIndex = jtableLoanDetails.getSelectedRow();

		if (selectedIndex == -1) {
			JOptionPane.showMessageDialog(null, "Please select a row.");
			return;
		}

		int id = Integer.parseInt(jtableLoanDetails.getValueAt(selectedIndex, 0).toString());
		JInternalFrameUpdateLoanDetails internalFrameUpdateLoanDetails = new JInternalFrameUpdateLoanDetails(id);
		getDesktopPane().add(internalFrameUpdateLoanDetails);
		internalFrameUpdateLoanDetails.toFront();
		internalFrameUpdateLoanDetails.setVisible(true);
	}

	public void jMenuItemInstallmentDetail_actionPerformed(ActionEvent arg0) {
		int selectedIndex = jtableLoanDetails.getSelectedRow();

		if (selectedIndex == -1) {
			JOptionPane.showMessageDialog(null, "Please select a row.");
			return;
		}

		int id = Integer.parseInt(jtableLoanDetails.getValueAt(selectedIndex, 0).toString());
		JInternalFrameInstallmentDetails internalFrameInstallmentDetails = new JInternalFrameInstallmentDetails(id);
		getDesktopPane().add(internalFrameInstallmentDetails);
		internalFrameInstallmentDetails.toFront();
		internalFrameInstallmentDetails.setVisible(true);
	}

	public void showInsertFrame() {
		JInternalFrameInsertLoanDetail internalFrameInsertLoanDetails = new JInternalFrameInsertLoanDetail();
		getDesktopPane().add(internalFrameInsertLoanDetails);
		internalFrameInsertLoanDetails.toFront();
		internalFrameInsertLoanDetails.setVisible(true);
	}

	public void delete() {
		int selectedIndex = jtableLoanDetails.getSelectedRow();
		try {
			if (selectedIndex == -1) {
				throw new Exception("Please select a row.");
			}

			int selection = JOptionPane.showConfirmDialog(null,
					"Delete this loan will also delete all related installments, fines and related messages, continue?",
					"Warning", JOptionPane.OK_CANCEL_OPTION);
			if (selection == JOptionPane.OK_OPTION) {
				int id = Integer.parseInt(jtableLoanDetails.getValueAt(selectedIndex, 0).toString());
				if (LoanModel.delete(id)) {
					JOptionPane.showMessageDialog(null, "Update success.");
					loadData();
				} else {
					JOptionPane.showMessageDialog(null, "Update failed.");
				}
			} else {
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	// only allows you to enter a decimal into jtextfieldAmount
	public void jtextFieldLoanAmountStart_keyTyped(KeyEvent arg0) {
		boolean ret = true;
		try {
			Double.parseDouble(jtextFieldLoanAmountStart.getText() + arg0.getKeyChar());
		} catch (NumberFormatException ee) {
			ret = false;
		}
		if (!ret) {
			arg0.consume();
		}
	}

	public void jtextFieldLoanAmountEnd_keyTyped(KeyEvent arg0) {
		boolean ret = true;
		try {
			Double.parseDouble(jtextFieldLoanAmountEnd.getText() + arg0.getKeyChar());
		} catch (NumberFormatException ee) {
			ret = false;
		}
		if (!ret) {
			arg0.consume();
		}
	}

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
