package main;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entities.Department;
import models.DepartmentModel;
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

public class JInternalFrameDepartments extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField textFieldDepartmentName;
	private JLabel lblDepartment;
	private JButton btnAdd;
	private int current_row = 0;
	private boolean update_flag = false;
	private JLabel lblFinanceDepartment;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameDepartments frame = new JInternalFrameDepartments();
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
	public JInternalFrameDepartments() {
		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBounds(100, 100, 619, 589);
		getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("Department manager");
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
		btnAdd.setIcon(new ImageIcon(JInternalFrameDepartments.class.getResource("/img/add.png")));
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
		btnAdd.setBounds(191, 190, 130, 42);
		getContentPane().add(btnAdd);

		textFieldDepartmentName = new JTextField();
		textFieldDepartmentName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldDepartmentName.setBounds(197, 132, 153, 35);
		textFieldDepartmentName.addKeyListener(new KeyAdapter() {
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
		getContentPane().add(textFieldDepartmentName);
		textFieldDepartmentName.setColumns(10);

		lblDepartment = new JLabel("Department name");
		lblDepartment.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDepartment.setBounds(50, 132, 137, 35);
		getContentPane().add(lblDepartment);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setIcon(new ImageIcon(JInternalFrameDepartments.class.getResource("/img/reset.png")));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
				loadData();
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReset.setBounds(327, 190, 130, 42);
		getContentPane().add(btnReset);
		
		lblFinanceDepartment = new JLabel(" Finance Department");
		lblFinanceDepartment.setBorder(UIManager.getBorder("TextField.border"));
		lblFinanceDepartment.setBackground(Color.WHITE);
		lblFinanceDepartment.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFinanceDepartment.setBounds(349, 132, 153, 35);
		getContentPane().add(lblFinanceDepartment);

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
		try {
			if (DepartmentModel.findAll() == null) {
				throw new Exception("Table departments in database is empty.");
			}
			for (Department department : DepartmentModel.findAll()) {
				defaultTableModel.addRow(new Object[] { department.getId(), department.getName() });
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
		textFieldDepartmentName.setText("");
		btnAdd.setText("Add");
		lblFinanceDepartment.setVisible(true);
		textFieldDepartmentName.setBounds(197, 132, 153, 35);
	}
	
	public void insert() {
		try {
			if (textFieldDepartmentName.getText().trim().isEmpty()) {
				throw new Exception("Please fill in department name");
			}
	
			String department_name = textFieldDepartmentName.getText().trim() ;
			
			if (DepartmentModel.isDuplicate(department_name + " Finance Department")) {
				throw new Exception("Department name existed.");
			}
			
			if (!ValidationModel.isValidatedStringWithSpace(department_name)) {
				throw new Exception("Department name invalid");
			}
			
			if (!DepartmentModel.create(department_name)) {
				throw new Exception("Update failed");
			} else {
				JOptionPane.showMessageDialog(null, "Update success, please go to \"Loan types\" module and create a corresponding loan type to avoid errors.");
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
			
			lblFinanceDepartment.setVisible(false);
			textFieldDepartmentName.setBounds(197, 132, 320, 35);

			int id = (int) table.getValueAt(selectedIndex, 0);

			if (btnAdd.getText().equals("Add") || !update_flag) {
				Department department = DepartmentModel.findById(id);
				textFieldDepartmentName.setText(department.getName());
				btnAdd.setText("Update");
			} else {
				if (textFieldDepartmentName.getText().trim().isEmpty()) {
					throw new Exception("Please fill in department name");
				}
				
				Department department_older = DepartmentModel.findById(id);
				if (!department_older.getName().equalsIgnoreCase(textFieldDepartmentName.getText().trim())) {
					if (DepartmentModel.isDuplicate(textFieldDepartmentName.getText().trim())) {
						throw new Exception("Department name existed.");
					}
				}
				
				if (!ValidationModel.isValidatedStringWithSpace(textFieldDepartmentName.getText().trim())) {
					throw new Exception("Department name invalid");
				}
				
				Department department = new Department(id, textFieldDepartmentName.getText().trim());
				
				if (!DepartmentModel.update(department)) {
					throw new Exception("Update failed");
				} else {
					JOptionPane.showMessageDialog(null, "Update success, please go to \"Loan types\" module and update corresponding loan type to avoid errors.");
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
					"You can only delete a department if there is no staff with that department in the database, continue?",
					"Confirm", JOptionPane.YES_NO_OPTION);
			if (selection != JOptionPane.YES_OPTION) {
				return;
			}
			
			int id = (int) table.getValueAt(selectedIndex, 0);
			if (!DepartmentModel.delete(id)) {
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
