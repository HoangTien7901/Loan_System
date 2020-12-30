package main;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entities.Customer;
import models.ConnectDB;
import models.CustomerModel;
import models.ValidationModel;
import tableAdjuster.TableColumnAdjuster;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.Cursor;

public class JInternalFrameCustomersList extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTable tableCustomersList;
	private static JPopupMenu popupMenu;
	private static String[] organizations = new String[10];
	private JTextField textFieldLoanAccountNo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameCustomersList frame = new JInternalFrameCustomersList();
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
	public JInternalFrameCustomersList() {
		try {
			PreparedStatement preparedStatment = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `organizations`");
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

		setBorder(null);
		setClosable(true);
		setMaximizable(true);
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		setBounds(253, 13, 1360, 871);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(tableCustomersList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(44, 299, 1281, 459);
		contentPane.add(scrollPane);

		popupMenu = new JPopupMenu();
		popupMenu.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		addPopup(scrollPane, popupMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Update");
		mntmNewMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showUpdateFrame();
			}
		});
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		popupMenu.add(mntmNewMenuItem);

		tableCustomersList = new JTable();
		tableCustomersList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tableCustomersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableCustomersList.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(tableCustomersList);

		JLabel lblTitle = new JLabel("Customers list");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblTitle.setBounds(10, 10, 1326, 64);
		contentPane.add(lblTitle);

		JButton btnAdd = new JButton("Add");
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.setIcon(new ImageIcon(JInternalFrameCustomersList.class.getResource("/img/add.png")));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showInsertFrame();
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnAdd.setBounds(547, 226, 120, 42);
		contentPane.add(btnAdd);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setIcon(new ImageIcon(JInternalFrameCustomersList.class.getResource("/img/delete.png")));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedIndex = tableCustomersList.getSelectedRow();
				try {
					if (selectedIndex == -1) {
						throw new Exception("Please select a row.");
					} else {
						int selection = JOptionPane.showConfirmDialog(null,
								"To delete this customer, you must first delete all his/her "
										+ "loan details and related installment details, continue?",
								"Confirm delete", JOptionPane.OK_CANCEL_OPTION);
						if (selection == JOptionPane.OK_OPTION) {
							delete(selectedIndex);
							loadData();
						}
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnDelete.setBounds(684, 226, 124, 42);
		contentPane.add(btnDelete);

		textFieldLoanAccountNo = new JTextField();
		textFieldLoanAccountNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldLoanAccountNo.setColumns(10);
		textFieldLoanAccountNo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (ValidationModel.isSpecialCharacterOrSpace(e.getKeyChar())) {
					e.consume();
				}
			}
		});
		textFieldLoanAccountNo.setBounds(580, 125, 211, 37);
		contentPane.add(textFieldLoanAccountNo);

		JLabel lblLoanAccountNo = new JLabel("Loan Account no");
		lblLoanAccountNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLoanAccountNo.setBounds(412, 133, 142, 20);
		contentPane.add(lblLoanAccountNo);

		JButton btnSearch = new JButton("Search");
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textFieldLoanAccountNo.getText().isEmpty()) 
					return;
				findByLoanAccountNo(textFieldLoanAccountNo.getText());
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSearch.setBounds(837, 103, 126, 42);
		contentPane.add(btnSearch);

		JButton btnReset = new JButton("Reset");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldLoanAccountNo.setText("");
				loadData();
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReset.setBounds(840, 155, 120, 42);
		contentPane.add(btnReset);

		loadData();

		// hiding title bar
		((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
	}

	public static void loadData() {
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

		defaultTableModel.addColumn("Loan account no");
		defaultTableModel.addColumn("Created date");
		defaultTableModel.addColumn("Customer name");
		defaultTableModel.addColumn("Phone number");
		defaultTableModel.addColumn("Email");
		defaultTableModel.addColumn("Id no");
		defaultTableModel.addColumn("Dob");
		defaultTableModel.addColumn("Address");
		defaultTableModel.addColumn("Organization");
		defaultTableModel.addColumn("Organization name");
		defaultTableModel.addColumn("Gross salary");
		List<Customer> customers_list = CustomerModel.findAll();
		for (Customer customer : customers_list) {
			defaultTableModel.addRow(new Object[] { customer.getLoan_account_no(), customer.getCreated_date(),
					customer.getFirst_name() + " " + customer.getLast_name(), customer.getPhone_number(),
					customer.getEmail(), customer.getId_no(), customer.getDob(), customer.getAddress(),
					organizations[customer.getOrganization_id()],
					customer.getOrganization_name() == null ? "no" : customer.getOrganization_name(),
					customer.getGross_salary() });
		}
		tableCustomersList.setModel(defaultTableModel);
		tableCustomersList.setComponentPopupMenu(popupMenu);

		// modify table
		tableCustomersList.setRowHeight(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableCustomersList.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = tableCustomersList.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		TableColumnAdjuster tca = new TableColumnAdjuster(tableCustomersList);
		tca.adjustColumns();

		tableCustomersList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	public static void findByLoanAccountNo(String loan_account_no) {
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

		defaultTableModel.addColumn("Loan account no");
		defaultTableModel.addColumn("Created date");
		defaultTableModel.addColumn("Customer name");
		defaultTableModel.addColumn("Phone number");
		defaultTableModel.addColumn("Email");
		defaultTableModel.addColumn("Id no");
		defaultTableModel.addColumn("Dob");
		defaultTableModel.addColumn("Address");
		defaultTableModel.addColumn("Organization");
		defaultTableModel.addColumn("Organization name");
		defaultTableModel.addColumn("Gross salary");
		Customer customer = CustomerModel.findByLoanAccountNo(loan_account_no);

		defaultTableModel.addRow(new Object[] { customer.getLoan_account_no(), customer.getCreated_date(),
				customer.getFirst_name() + " " + customer.getLast_name(), customer.getPhone_number(),
				customer.getEmail(), customer.getId_no(), customer.getDob(), customer.getAddress(),
				organizations[customer.getOrganization_id()],
				customer.getOrganization_name() == null ? "no" : customer.getOrganization_name(),
				customer.getGross_salary() });

		tableCustomersList.setModel(defaultTableModel);
		tableCustomersList.setComponentPopupMenu(popupMenu);

		// modify table
		tableCustomersList.setRowHeight(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableCustomersList.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = tableCustomersList.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		TableColumnAdjuster tca = new TableColumnAdjuster(tableCustomersList);
		tca.adjustColumns();

		tableCustomersList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
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

	public void showUpdateFrame() {
		int selectedIndex = tableCustomersList.getSelectedRow();
		try {
			if (selectedIndex == -1) {
				throw new Exception("Please select a row.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		String loan_account_no = tableCustomersList.getValueAt(selectedIndex, 0).toString();
		JInternalFrameUpdateCustomerDetails internalFrameUpdateCustomerDetails = new JInternalFrameUpdateCustomerDetails(
				loan_account_no);
		getDesktopPane().add(internalFrameUpdateCustomerDetails);
		internalFrameUpdateCustomerDetails.toFront();
		internalFrameUpdateCustomerDetails.setVisible(true);
	}

	public void showInsertFrame() {
		JInternalFrameInsertCustomerDetails jInternalFrameInsertCustomerDetails = new JInternalFrameInsertCustomerDetails();
		getDesktopPane().add(jInternalFrameInsertCustomerDetails);
		jInternalFrameInsertCustomerDetails.toFront();
		jInternalFrameInsertCustomerDetails.setVisible(true);
	}

	public void delete(int selectedIndex) {
		String loan_account_no = tableCustomersList.getValueAt(selectedIndex, 0).toString();
		if (CustomerModel.deleteByLoanAccountNo(loan_account_no)) {
			JOptionPane.showMessageDialog(null, "Update success");
		} else {
			JOptionPane.showMessageDialog(null, "Update failed");
		}
	}
}
