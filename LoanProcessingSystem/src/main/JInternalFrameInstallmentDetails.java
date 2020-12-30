package main;

import java.awt.EventQueue;
import java.awt.Font;
import java.text.SimpleDateFormat;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entities.Installment;
import models.InstallmentModel;
import tableAdjuster.TableColumnAdjuster;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JDesktopPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.awt.Cursor;

public class JInternalFrameInstallmentDetails extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int id = 0;
	private JDesktopPane jdesktopPaneInstallmentDetails;
	private static JTable jtableInstallmentDetails;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnPending;
	private JRadioButton rdbtnDue;
	private JRadioButton rdbtnDone;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameInstallmentDetails frame = new JInternalFrameInstallmentDetails(id);
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
	public JInternalFrameInstallmentDetails(int loan_details_id) {
		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBounds(100, 100, 1200, 671);
		getContentPane().setLayout(null);

		jdesktopPaneInstallmentDetails = new JDesktopPane();
		jdesktopPaneInstallmentDetails.setBounds(10, 11, 1176, 619);
		getContentPane().add(jdesktopPaneInstallmentDetails);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 307, 1156, 281);
		jdesktopPaneInstallmentDetails.add(scrollPane);

		jtableInstallmentDetails = new JTable();
		jtableInstallmentDetails.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jtableInstallmentDetails.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(jtableInstallmentDetails);

		JPopupMenu jpopupMenu = new JPopupMenu();
		addPopup(jtableInstallmentDetails, jpopupMenu);

		JMenuItem jMenuItemUpdateInstallmentDetails = new JMenuItem("Update");
		jMenuItemUpdateInstallmentDetails.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		jMenuItemUpdateInstallmentDetails.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jMenuItemUpdateInstallmentDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jMenuItemUpdateInstallmentDetails_actionPerformed(e, loan_details_id);
			}
		});
		jpopupMenu.add(jMenuItemUpdateInstallmentDetails);

		JLabel lblNewLabel = new JLabel("Installment Details");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(10, 40, 1156, 39);
		jdesktopPaneInstallmentDetails.add(lblNewLabel);

		JButton jButtonSearch = new JButton("Search");
		jButtonSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonSearch.setIcon(
				new ImageIcon(JInternalFrameInstallmentDetails.class.getResource("/img/iconfinder_search_326690.png")));
		jButtonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonSearch_actionPerformed(e, loan_details_id);
			}
		});
		jButtonSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
		jButtonSearch.setBounds(443, 255, 126, 42);
		jdesktopPaneInstallmentDetails.add(jButtonSearch);

		rdbtnDue = new JRadioButton("due");
		rdbtnDue.setSelected(true);
		buttonGroup.add(rdbtnDue);
		rdbtnDue.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnDue.setBounds(426, 130, 109, 25);
		jdesktopPaneInstallmentDetails.add(rdbtnDue);

		rdbtnPending = new JRadioButton("pending");
		buttonGroup.add(rdbtnPending);
		rdbtnPending.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnPending.setBounds(426, 173, 109, 25);
		jdesktopPaneInstallmentDetails.add(rdbtnPending);

		JLabel lblNewLabel_1 = new JLabel("Status");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(336, 126, 67, 32);
		jdesktopPaneInstallmentDetails.add(lblNewLabel_1);

		JButton btnReset = new JButton("Reset");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setIcon(new ImageIcon(JInternalFrameInstallmentDetails.class.getResource("/img/reset.png")));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadData(loan_details_id);
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReset.setBounds(615, 255, 120, 42);
		jdesktopPaneInstallmentDetails.add(btnReset);

		rdbtnDone = new JRadioButton("done");
		buttonGroup.add(rdbtnDone);
		rdbtnDone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnDone.setBounds(426, 217, 109, 25);
		jdesktopPaneInstallmentDetails.add(rdbtnDone);
		loadData(loan_details_id);
	}

//loadData by loan_details_id
	public static void loadData(int loan_details_id) {
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
		defaultTableModel.addColumn("Payday");
		defaultTableModel.addColumn("Installment Term");
		defaultTableModel.addColumn("Amount");
		defaultTableModel.addColumn("Status");

		for (Installment inDetail : InstallmentModel.findByLoanDetailId(loan_details_id)) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String payday = "no";
			if (inDetail.getPayday() != null) {
				payday = simpleDateFormat.format(inDetail.getPayday());
			}
			String installment_term = null;
			{
				if (inDetail.getInstallment_term() != null) {
					installment_term = simpleDateFormat.format(inDetail.getInstallment_term());
				}
			}
			defaultTableModel.addRow(new Object[] { inDetail.getId(), payday, installment_term, inDetail.getAmount(),
					inDetail.getStatus() });
		}
		jtableInstallmentDetails.setModel(defaultTableModel);

		// modify table
		jtableInstallmentDetails.setRowHeight(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		jtableInstallmentDetails.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = jtableInstallmentDetails.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		jtableInstallmentDetails.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumnAdjuster tca = new TableColumnAdjuster(jtableInstallmentDetails);
		tca.adjustColumns();
	}

//Jbutton search by status
	public void jButtonSearch_actionPerformed(ActionEvent e, int loan_details_id) {
		String status = "%%";

		if (rdbtnDue.isSelected()) {
			status = "%" + rdbtnDue.getText() + "%";
		}
		if (rdbtnPending.isSelected()) {
			status = "%" + rdbtnPending.getText() + "%";
		}
		if (rdbtnDone.isSelected()) {
			status = "%" + rdbtnDone.getText() + "%";
		}
		loadDataBySearch(status, loan_details_id);
	}

//loadData by Search
	public void loadDataBySearch(String status, int loan_details_id) {
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
		defaultTableModel.addColumn("Payday");
		defaultTableModel.addColumn("Installment Term");
		defaultTableModel.addColumn("Amount");
		defaultTableModel.addColumn("Status");

		for (Installment inDetail : InstallmentModel.findByStatus(status, loan_details_id)) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String payday = "no";
			if (inDetail.getPayday() != null) {
				payday = simpleDateFormat.format(inDetail.getPayday());
			}
			String installment_term = null;
			{
				if (inDetail.getInstallment_term() != null) {
					installment_term = simpleDateFormat.format(inDetail.getInstallment_term());
				}
			}
			defaultTableModel.addRow(new Object[] { inDetail.getId(), payday, installment_term, inDetail.getAmount(),
					inDetail.getStatus() });
		}
		jtableInstallmentDetails.setModel(defaultTableModel);

		// modify table
		jtableInstallmentDetails.setRowHeight(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		jtableInstallmentDetails.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = jtableInstallmentDetails.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		jtableInstallmentDetails.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumnAdjuster tca = new TableColumnAdjuster(jtableInstallmentDetails);
		tca.adjustColumns();
	}

//Update Installment Details
	public void jMenuItemUpdateInstallmentDetails_actionPerformed(ActionEvent e, int loan_details_id) {
		int selectedIndex = jtableInstallmentDetails.getSelectedRow();

		if (selectedIndex == -1) {
			JOptionPane.showMessageDialog(null, "Please select a row.");
			return;
		}

		int id = Integer.parseInt(jtableInstallmentDetails.getValueAt(selectedIndex, 0).toString());
		JInternalFrameUpdateInstallmentDetails internalFrameUpdateInstallmentDetails = new JInternalFrameUpdateInstallmentDetails(
				id, loan_details_id);
		getDesktopPane().add(internalFrameUpdateInstallmentDetails);
		internalFrameUpdateInstallmentDetails.toFront();
		internalFrameUpdateInstallmentDetails.setVisible(true);
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
