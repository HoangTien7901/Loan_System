package main;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entities.LoanTypes;
import models.LoanTypesModel;
import models.ValidationModel;
import tableAdjuster.TableColumnAdjuster;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import javax.swing.UIManager;

public class JInternalFrameLoanTypes extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField textFieldLoanTypeName;
	private JLabel lblLoanName;
	private JButton btnAdd;
	private JTextField textFieldInterestRate;
	private int current_row = 0;
	private boolean update_flag = false;
	private JLabel lblLoan;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameLoanTypes frame = new JInternalFrameLoanTypes();
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
	public JInternalFrameLoanTypes() {
		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBounds(100, 100, 619, 589);
		getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("Loan types manager");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitle.setBounds(10, 10, 595, 42);
		getContentPane().add(lblTitle);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 258, 514, 272);
		getContentPane().add(scrollPane);

		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		addPopup(scrollPane, popupMenu);

		JMenuItem mntmUpdate = new JMenuItem("Update");
		mntmUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		});
		mntmUpdate.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		popupMenu.add(mntmUpdate);

		table = new JTable();
		table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(table);
		table.setComponentPopupMenu(popupMenu);

		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		mntmDelete.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		popupMenu.add(mntmDelete);

		btnAdd = new JButton("Add");
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.setIcon(new ImageIcon(JInternalFrameLoanTypes.class.getResource("/img/add.png")));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnAdd.getText().equals("Add")) {
					insert();
				} else {
					update();
				}
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAdd.setBounds(191, 190, 126, 42);
		getContentPane().add(btnAdd);

		textFieldLoanTypeName = new JTextField();
		textFieldLoanTypeName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldLoanTypeName.setBounds(209, 98, 155, 35);
		textFieldLoanTypeName.addKeyListener(new KeyAdapter() {
			@Override
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
		getContentPane().add(textFieldLoanTypeName);
		textFieldLoanTypeName.setColumns(10);

		lblLoanName = new JLabel("Loan type name");
		lblLoanName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLoanName.setBounds(50, 96, 137, 35);
		getContentPane().add(lblLoanName);

		JButton btnReset = new JButton("Reset");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setIcon(new ImageIcon(JInternalFrameLoanTypes.class.getResource("/img/reset.png")));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
				loadData();
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReset.setBounds(327, 190, 120, 42);
		getContentPane().add(btnReset);

		JLabel lblInteresetRate = new JLabel("Intereset rate");
		lblInteresetRate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInteresetRate.setBounds(50, 143, 137, 35);
		getContentPane().add(lblInteresetRate);

		textFieldInterestRate = new JTextField();
		textFieldInterestRate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldInterestRate.setColumns(10);
		textFieldInterestRate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean ret = true;
				try {
					Float.parseFloat(textFieldInterestRate.getText() + e.getKeyChar());
				} catch (NumberFormatException ee) {
					ret = false;
				}
				if (!ret) {
					e.consume();
				}
			}
		});
		textFieldInterestRate.setBounds(209, 143, 120, 35);
		getContentPane().add(textFieldInterestRate);
		
		lblLoan = new JLabel(" Loan");
		lblLoan.setBackground(Color.WHITE);
		lblLoan.setBorder(UIManager.getBorder("TextField.border"));
		lblLoan.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLoan.setBounds(363, 98, 77, 35);
		getContentPane().add(lblLoan);

		loadData();
	}

	public void loadData() {
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
		defaultTableModel.addColumn("Name");
		defaultTableModel.addColumn("Interest rate");
		try {
			if (LoanTypesModel.find() == null) {
				throw new Exception("Table loan_types in database is empty.");
			}
			for (LoanTypes loan_type : LoanTypesModel.find()) {
				defaultTableModel.addRow(
						new Object[] { loan_type.getId(), loan_type.getName(), loan_type.getInterest_rate() });
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		table.setModel(defaultTableModel);

		// modify table
		table.setRowHeight(30);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = table.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		TableColumnAdjuster tca = new TableColumnAdjuster(table);
		tca.adjustColumns();

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	public void reset() {
		textFieldLoanTypeName.setText("");
		textFieldLoanTypeName.setBounds(209, 98, 155, 35);
		textFieldInterestRate.setText("");
		btnAdd.setText("Add");
		lblLoan.setVisible(true);
	}

	public void insert() {
		try {
			if (textFieldLoanTypeName.getText().trim().isEmpty() || textFieldInterestRate.getText().trim().isEmpty()) {
				throw new Exception("Please fill in all fields.");
			}

			String loan_type_name = textFieldLoanTypeName.getText().trim() + " loan";
			float intereset_rate = Float.parseFloat(textFieldInterestRate.getText());
			
			if (LoanTypesModel.isDuplicate(loan_type_name)) {
				throw new Exception("Loan type name existed.");
			}
			
			if (!ValidationModel.isValidatedStringWithSpace(loan_type_name)) {
				throw new Exception("Department name invalid");
			}

			LoanTypes loan_type = new LoanTypes(0, loan_type_name, intereset_rate);

			if (!LoanTypesModel.create(loan_type)) {
				throw new Exception("Update failed");
			} else {
				JOptionPane.showMessageDialog(null, "Update success, please go to \"Department\" module and create a corresponding department to avoid error.");
				loadData();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void update() {
		int selectedIndex = table.getSelectedRow();

		try {
			if (selectedIndex == -1) {
				throw new Exception("Please select a row.");
			}

			if (current_row != selectedIndex) {
				current_row = selectedIndex;
				update_flag = false;
			} else {
				update_flag = true;
			}
			
			lblLoan.setVisible(false);
			textFieldLoanTypeName.setBounds(209, 98, 250, 35);

			int id = (int) table.getValueAt(selectedIndex, 0);

			if (btnAdd.getText().equals("Add") || !update_flag) {
				LoanTypes loan_type = LoanTypesModel.findById(id);
				textFieldLoanTypeName.setText(loan_type.getName());
				textFieldInterestRate.setText(loan_type.getInterest_rate() + "");
				btnAdd.setText("Update");
			} else {
				if (textFieldLoanTypeName.getText().trim().isEmpty()
						|| textFieldInterestRate.getText().trim().isEmpty()) {
					throw new Exception("Please fill in all fields.");
				}

				int selection = JOptionPane.showConfirmDialog(null, "This change won't affect old loans, continue?",
						"Confirm", JOptionPane.YES_NO_OPTION);
				if (selection != JOptionPane.OK_OPTION) {
					return;
				}

				String loan_type_name = textFieldLoanTypeName.getText().trim();
				float intereset_rate = Float.parseFloat(textFieldInterestRate.getText());
				
				LoanTypes loan_type_older = LoanTypesModel.findById(id);
				if (!loan_type_older.getName().equalsIgnoreCase(loan_type_name)) {
					if (LoanTypesModel.isDuplicate(loan_type_name)) {
						throw new Exception("Loan type name existed.");
					}	
				}
				
				if (!ValidationModel.isValidatedStringWithSpace(loan_type_name)) {
					throw new Exception("Loan type name invalid");
				}

				LoanTypes loan_type = new LoanTypes(id, loan_type_name, intereset_rate);
				if (!LoanTypesModel.update(loan_type)) {
					throw new Exception("Update failed");
				} else {
					JOptionPane.showMessageDialog(null, "Update success, please go to \"Department\" module and update corresponding department to avoid errors.");
					loadData();
					reset();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void delete() {
		int selectedIndex = table.getSelectedRow();
		try {
			if (selectedIndex == -1) {
				throw new Exception("Please select a row.");
			}
			
			int selection = JOptionPane.showConfirmDialog(null,
					"You can only delete a loan type if there is no loan with that type in the database, continue?",
					"Confirm", JOptionPane.YES_NO_OPTION);
			if (selection != JOptionPane.YES_OPTION) {
				return;
			}

			int id = (int) table.getValueAt(selectedIndex, 0);
			if (!LoanTypesModel.delete(id)) {
				throw new Exception("Update failed");
			} else {
				JOptionPane.showMessageDialog(null, "Update success");
				loadData();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
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
