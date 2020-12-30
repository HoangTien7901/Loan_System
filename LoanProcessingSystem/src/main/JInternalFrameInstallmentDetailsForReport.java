package main;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entities.Loan;
import models.ConnectDB;
import tableAdjuster.TableColumnAdjuster;

import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class JInternalFrameInstallmentDetailsForReport extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static List<Loan> loans_list;
	private JTable tableInstallment;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameInstallmentDetailsForReport frame = new JInternalFrameInstallmentDetailsForReport(
							loans_list);
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
	public JInternalFrameInstallmentDetailsForReport(List<Loan> loans_list) {
		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBounds(100, 100, 960, 608);
		setClosable(true);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 37, 936, 491);
		getContentPane().add(scrollPane);

		tableInstallment = new JTable();
		tableInstallment.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(tableInstallment);

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
		defaultTableModel.addColumn("Loan details id");
		defaultTableModel.addColumn("Payday");
		defaultTableModel.addColumn("Installment term");
		defaultTableModel.addColumn("Amount");
		defaultTableModel.addColumn("Status");

		try {
			String loan_id_str = "(";
			for (Loan loan : loans_list) {
				loan_id_str += loan.getId();
				loan_id_str += ",";
			}
			loan_id_str = loan_id_str.substring(0, loan_id_str.length() - 1);
			loan_id_str += ")";

			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `installment_details` where loan_details_id in " + loan_id_str
							+ " and CURRENT_DATE > `installment_term`");

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				defaultTableModel.addRow(new Object[] { resultSet.getInt("loan_details_id"),
						resultSet.getDate("payday") == null ? "no" : resultSet.getDate("payday"),
						resultSet.getDate("installment_term"), resultSet.getDouble("amount"),
						resultSet.getString("status") });
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		tableInstallment.setModel(defaultTableModel);
		
		tableInstallment.setRowHeight(30);
		tableInstallment.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableInstallment.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = tableInstallment.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		TableColumnAdjuster tca = new TableColumnAdjuster(tableInstallment);
		tca.adjustColumns();
	}
}
