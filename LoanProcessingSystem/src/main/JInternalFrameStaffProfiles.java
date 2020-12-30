package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entities.Staff;
import models.DepartmentModel;
import models.PositionModel;
import models.StaffModel;
import tableAdjuster.TableColumnAdjuster;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Cursor;

public class JInternalFrameStaffProfiles extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTable tableStaffsList;
	private static ArrayList<String> positions;
	private static ArrayList<String> departments;
	private static JPopupMenu popupMenu;
	private static SimpleDateFormat simpleDF = new SimpleDateFormat("dd/MM/yyyy");
	private JTextField textFieldUsername;
	private JTextField textFieldId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameStaffProfiles frame = new JInternalFrameStaffProfiles();
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
	public JInternalFrameStaffProfiles() {
		try {
			positions = PositionModel.arrayListName();
			departments = DepartmentModel.arrayListName();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
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

		JScrollPane scrollPane = new JScrollPane(tableStaffsList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(44, 299, 1281, 459);
		contentPane.add(scrollPane);

		popupMenu = new JPopupMenu();
		addPopup(scrollPane, popupMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Update");
		mntmNewMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showUpdateFrame();
			}
		});
		mntmNewMenuItem.setFont(new Font("Tahoma", Font.PLAIN, 15));
		popupMenu.add(mntmNewMenuItem);

		tableStaffsList = new JTable();
		tableStaffsList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tableStaffsList.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(tableStaffsList);

		JLabel lblTitle = new JLabel("Staffs list");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblTitle.setBounds(10, 10, 1326, 64);
		contentPane.add(lblTitle);

		JButton btnAdd = new JButton("Add");
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.setIcon(new ImageIcon(JInternalFrameStaffProfiles.class.getResource("/img/add.png")));
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
		btnDelete.setIcon(new ImageIcon(JInternalFrameStaffProfiles.class.getResource("/img/delete.png")));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				delete();
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnDelete.setBounds(684, 226, 124, 42);
		contentPane.add(btnDelete);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(479, 154, 86, 29);
		contentPane.add(lblUsername);

		textFieldUsername = new JTextField();
		textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldUsername.setBounds(575, 154, 150, 29);
		textFieldUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == ' ' || e.getKeyChar() == '"' || e.getKeyChar() == '\'' || e.getKeyChar() == ';') {
					e.consume();
				}
			}
		});
		contentPane.add(textFieldUsername);
		textFieldUsername.setColumns(10);

		JButton btnReset = new JButton("Reset");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadData();
				textFieldId.setText("");
				textFieldUsername.setText("");
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReset.setBounds(777, 154, 130, 42);
		contentPane.add(btnReset);

		JButton btnSearch = new JButton("Search");
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSearch.setBounds(777, 102, 130, 42);
		contentPane.add(btnSearch);

		JLabel lblId = new JLabel("Id");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblId.setBounds(479, 115, 86, 29);
		contentPane.add(lblId);

		textFieldId = new JTextField();
		textFieldId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean ret = true;
				try {
					Integer.parseInt(e.getKeyChar() + "");
				} catch (NumberFormatException ee) {
					ret = false;
				}
				if (!ret) {
					e.consume();
				}
			}
		});
		textFieldId.setColumns(10);
		textFieldId.setBounds(575, 115, 77, 29);
		contentPane.add(textFieldId);

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

		defaultTableModel.addColumn("Id");
		defaultTableModel.addColumn("Name");
		defaultTableModel.addColumn("Gender");
		defaultTableModel.addColumn("Username");
		defaultTableModel.addColumn("Dob");
		defaultTableModel.addColumn("Position");
		defaultTableModel.addColumn("Address");
		defaultTableModel.addColumn("Salary");
		defaultTableModel.addColumn("Start date");
		defaultTableModel.addColumn("End date");
		defaultTableModel.addColumn("Department");
		defaultTableModel.addColumn("Status");

		List<Staff> staffs_list = StaffModel.findAll();
		for (Staff staff : staffs_list) {
			defaultTableModel.addRow(new Object[] { staff.getId(), staff.getFirst_name() + " " + staff.getLast_name(),
					staff.isGender() ? "male" : "female", staff.getUsername(),
					simpleDF.format(staff.getDob()),
					PositionModel.getAuthority_level(staff.getPosition_id()) == 4 ? "admin"
							: positions.get(staff.getPosition_id() - 1),
					staff.getAddress(), staff.getSalary(), simpleDF.format(staff.getStart_date()),
					staff.getEnd_date() == null ? "no" : simpleDF.format(staff.getEnd_date()),
					staff.getDepartment_id() != 0 ? departments.get(staff.getDepartment_id() - 1).split(" ")[0] : null,
					staff.getStatus() });
		}

		tableStaffsList.setModel(defaultTableModel);
		tableStaffsList.setComponentPopupMenu(popupMenu);

		// modify table
		tableStaffsList.setRowHeight(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableStaffsList.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = tableStaffsList.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		TableColumnAdjuster tca = new TableColumnAdjuster(tableStaffsList);
		tca.adjustColumns();

		tableStaffsList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	public void search() {
		int id = 0;
		String username = "%%";

		if (textFieldId.getText().isEmpty() && textFieldUsername.getText().isEmpty()) {
			return;
		}

		if (!textFieldId.getText().isEmpty()) {
			id = Integer.parseInt(textFieldId.getText());
		}

		if (!textFieldUsername.getText().isEmpty()) {
			username = "%" + textFieldUsername.getText() + "%";
		}

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
		defaultTableModel.addColumn("Gender");
		defaultTableModel.addColumn("Username");
		defaultTableModel.addColumn("Dob");
		defaultTableModel.addColumn("Position");
		defaultTableModel.addColumn("Address");
		defaultTableModel.addColumn("Salary");
		defaultTableModel.addColumn("Start date");
		defaultTableModel.addColumn("End date");
		defaultTableModel.addColumn("Department");
		defaultTableModel.addColumn("Status");

		List<Staff> staffs_list = StaffModel.findByIdAndUsername(id, username);

		for (Staff staff : staffs_list) {
			defaultTableModel.addRow(new Object[] { staff.getId(), staff.getFirst_name() + " " + staff.getLast_name(),
					staff.isGender() ? "male" : "female", staff.getUsername(),
					simpleDF.format(staff.getDob()),
					PositionModel.getAuthority_level(staff.getPosition_id()) == 4 ? "admin"
							: positions.get(staff.getPosition_id() - 1),
					staff.getAddress(), staff.getSalary(), simpleDF.format(staff.getStart_date()),
					staff.getEnd_date() == null ? "no" : simpleDF.format(staff.getEnd_date()),
					departments.get(staff.getDepartment_id() - 1).split(" ")[0], staff.getStatus() });
		}

		tableStaffsList.setModel(defaultTableModel);
		tableStaffsList.setComponentPopupMenu(popupMenu);

		// modify table
		tableStaffsList.setRowHeight(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableStaffsList.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = tableStaffsList.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		TableColumnAdjuster tca = new TableColumnAdjuster(tableStaffsList);
		tca.adjustColumns();

		tableStaffsList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	public void showInsertFrame() {
		JInternalFrameInsertStaffDetails internalFrameInsertStaffDetails = new JInternalFrameInsertStaffDetails();
		getDesktopPane().add(internalFrameInsertStaffDetails);
		internalFrameInsertStaffDetails.toFront();
		internalFrameInsertStaffDetails.setVisible(true);
	}

	public void showUpdateFrame() {
		int selectedIndex = tableStaffsList.getSelectedRow();

		try {
			if (selectedIndex == -1) {
				throw new Exception("Please select a row.");
			} else {
				int id = (int) tableStaffsList.getValueAt(selectedIndex, 0);

				Staff updatingStaff = StaffModel.findByStaff_Id(id);

				if (JFrameMain.authority_level < 2) {
					throw new Exception(
							"Your need authority level 2 (manager in the same department) or above to update STAFF, "
									+ "your authority level : " + JFrameMain.authority_level);
				}

				if (JFrameMain.authority_level == 2
						&& (!positions.get(updatingStaff.getPosition_id() - 1).equals("staff")
								|| JFrameMain.login_staff.getDepartment_id() != updatingStaff.getDepartment_id())) {
					throw new Exception(
							"User with authority level 2 can only change information of STAFF in the same department.");
				}

				if (JFrameMain.authority_level <= PositionModel.getAuthority_level(updatingStaff.getPosition_id())) {
					throw new Exception("User can only update staff with lower authority level.");
				}

				JInternalFrameUpdateStaffDetails internalFrameUpdateStaffDetails = new JInternalFrameUpdateStaffDetails(
						StaffModel.findByStaff_Id(id));
				getDesktopPane().add(internalFrameUpdateStaffDetails);
				internalFrameUpdateStaffDetails.toFront();
				internalFrameUpdateStaffDetails.setVisible(true);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return;
		}
	}

	public void delete() {
		int selectedIndex = tableStaffsList.getSelectedRow();

		try {
			if (selectedIndex == -1) {
				throw new Exception("Please select a row.");
			} else {
				int id = (int) tableStaffsList.getValueAt(selectedIndex, 0);

				Staff updatingStaff = StaffModel.findByStaff_Id(id);

				if (JFrameMain.authority_level < 2) {
					throw new Exception(
							"Your need authority level 2 (manager in the same department) or above to update STAFF, "
									+ "your authority level : " + JFrameMain.authority_level);
				}

				if (JFrameMain.authority_level == 2
						&& (!positions.get(updatingStaff.getPosition_id() - 1).equals("staff")
								|| JFrameMain.login_staff.getDepartment_id() != updatingStaff.getDepartment_id())) {
					throw new Exception(
							"User with authority level 2 can only change information of STAFF in the same department.");
				}

				if (JFrameMain.authority_level < PositionModel.getAuthority_level(updatingStaff.getPosition_id())) {
					throw new Exception("Can't change or delete staff with higher or equal authority level.");
				}

				int selection = JOptionPane.showConfirmDialog(null,
						"To delete this staff, you must first delete all his/her " + "related loan details, continue?",
						"Confirm delete", JOptionPane.OK_CANCEL_OPTION);
				if (selection != JOptionPane.OK_OPTION) {
					return;
				}

				if (StaffModel.deleteById(id)) {
					JOptionPane.showMessageDialog(null, "Update success");
					loadData();
				} else {
					JOptionPane.showMessageDialog(null, "Update failed");
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return;
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
