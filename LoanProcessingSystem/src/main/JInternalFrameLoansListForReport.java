package main;

import java.awt.EventQueue;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entities.Loan;
import models.ConnectDB;
import tableAdjuster.TableColumnAdjuster;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Font;

public class JInternalFrameLoansListForReport extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static List<Loan> loans_list = new ArrayList<Loan>();
	private String[] loan_types = new String[10];
	private JTable tableLoansList;
	private JLabel lblTitle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameLoansListForReport frame = new JInternalFrameLoansListForReport(loans_list);
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
	 * @param loans_list
	 */
	public JInternalFrameLoansListForReport(List<Loan> loans_list) {
		JInternalFrameLoansListForReport.loans_list = loans_list;

		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBounds(100, 100, 1200, 612);
		getContentPane().setLayout(null);

		// get loan type names
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `loan_types`");
			ResultSet resultSet = preparedStatement.executeQuery();
			int i = 1;
			while (resultSet.next()) {
				loan_types[i] = resultSet.getString("loan_name");
				i++;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error : Can't get loan types name.");
		}

		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane(tableLoansList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(56, 137, 1105, 414);
		getContentPane().add(scrollPane);

		tableLoansList = new JTable();
		tableLoansList.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(tableLoansList);

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
		
		defaultTableModel.addColumn("Loan type");
		defaultTableModel.addColumn("Amount");
		defaultTableModel.addColumn("Disbursal date");
		defaultTableModel.addColumn("Term date");
		defaultTableModel.addColumn("Interest rate");
		defaultTableModel.addColumn("Monthly installment");
		defaultTableModel.addColumn("Total installment");
		defaultTableModel.addColumn("Balance to be paid");
		defaultTableModel.addColumn("Total fine");
		defaultTableModel.addColumn("Status");
		defaultTableModel.addColumn("Staff id");

		String loan_account_no = loans_list.get(0).getLoan_account_no();

		SimpleDateFormat simpleDF = new SimpleDateFormat("dd/MM/yyyy");
		for (Loan loan : loans_list) {
			String disbursal_date = simpleDF.format(loan.getDisbursement_date().getTime());
			String term_date = simpleDF.format(loan.getTerm_date().getTime());

			defaultTableModel.addRow(new Object[] { loan_types[loan.getLoan_type_id()], loan.getAmount(),
					disbursal_date, term_date, loan.getInterest_rate(), loan.getMonthly_installment(),
					loan.getTotal_installment(), loan.getBalance_to_be_paid(), loan.getTotal_fine(), loan.getStatus(),
					loan.getStaff_id() });
		}

		tableLoansList.setModel(defaultTableModel);

		lblTitle = new JLabel("\n\nLoans list of loan account no " + loan_account_no);
		lblTitle.setBounds(10, 37, 1176, 28);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblTitle);

		// modify table
		tableLoansList.setRowHeight(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableLoansList.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = tableLoansList.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		tableLoansList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnAdjuster tca = new TableColumnAdjuster(tableLoansList);
		tca.adjustColumns();
	}
}
