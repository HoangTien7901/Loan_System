package main;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entities.Organization;
import models.OrganizationsModel;
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

public class JInternalFrameOrganizations extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField textFieldName;
	private JLabel lblName;
	private JButton btnAdd;
	private int current_row = 0;
	private boolean update_flag = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameOrganizations frame = new JInternalFrameOrganizations();
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
	public JInternalFrameOrganizations() {
		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBounds(100, 100, 619, 589);
		getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("Organization manager");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitle.setBounds(10, 10, 595, 53);
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
		btnAdd.setIcon(new ImageIcon(JInternalFrameOrganizations.class.getResource("/img/add.png")));
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

		textFieldName = new JTextField();
		textFieldName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldName.setBounds(209, 134, 256, 35);
		textFieldName.addKeyListener(new KeyAdapter() {
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
		getContentPane().add(textFieldName);
		textFieldName.setColumns(10);

		lblName = new JLabel("Organization name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(50, 132, 137, 35);
		getContentPane().add(lblName);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setIcon(new ImageIcon(JInternalFrameOrganizations.class.getResource("/img/reset.png")));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
				loadData();
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReset.setBounds(327, 190, 130, 42);
		getContentPane().add(btnReset);

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
		defaultTableModel.addColumn("true_id");
		defaultTableModel.addColumn("Id");
		defaultTableModel.addColumn("Name");
		try {
			int i = 1;
			if (OrganizationsModel.findAll() == null) {
				throw new Exception("Table organization in database is empty.");
			}
			for (Organization organization : OrganizationsModel.findAll()) {
				defaultTableModel.addRow(new Object[] { organization.getId(), i, organization.getName() });
				i++;
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
		table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = table.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		TableColumnAdjuster tca = new TableColumnAdjuster(table);
		tca.adjustColumns();

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// hide column true id
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
	}

	public void reset() {
		textFieldName.setText("");
		btnAdd.setText("Add");
	}
	
	public void insert() {
		try {
			if (textFieldName.getText().trim().isEmpty()) {
				throw new Exception("Please fill in organization name");
			}
	
			String organization_name = textFieldName.getText().trim();
			
			if (OrganizationsModel.isDuplicate(organization_name)) {
				throw new Exception("Organization name existed.");
			}
			
			if (!ValidationModel.isValidatedStringWithSpace(organization_name)) {
				throw new Exception("Organization name invalid");
			}
			
			if (!OrganizationsModel.create(organization_name)) {
				throw new Exception("Update failed");
			} else {
				JOptionPane.showMessageDialog(null, "Update success");
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

			int id = (int) table.getValueAt(selectedIndex, 0);

			if (btnAdd.getText().equals("Add") || !update_flag) {
				Organization organization = OrganizationsModel.findById(id);
				textFieldName.setText(organization.getName());
				btnAdd.setText("Update");
			} else {
				if (textFieldName.getText().trim().isEmpty()) {
					throw new Exception("Please fill in organization name");
				}
				
				if (!ValidationModel.isValidatedStringWithSpace(textFieldName.getText().trim())) {
					throw new Exception("Organization name invalid");
				}
				
				Organization organization = new Organization(id, textFieldName.getText().trim());
				Organization organization_older = OrganizationsModel.findById(id);
				
				if (!organization_older.getName().equalsIgnoreCase(organization.getName())) {
					if (OrganizationsModel.isDuplicate(organization.getName())) {
						throw new Exception("Organization name existed.");
					}
				}
				
				int selection = JOptionPane.showConfirmDialog(null, "This change won't affect old loans, continue?",
						"Confirm", JOptionPane.YES_NO_OPTION);
				if (selection != JOptionPane.OK_OPTION) {
					return;
				}
				
				if (!OrganizationsModel.update(organization)) {
					throw new Exception("Update failed");
				} else {
					JOptionPane.showMessageDialog(null, "Update success");
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
					"You can only delete an organization if there is no loan with that organization in the database, continue?",
					"Confirm", JOptionPane.YES_NO_OPTION);
			if (selection != JOptionPane.YES_OPTION) {
				return;
			}
			
			int id = (int) table.getValueAt(selectedIndex, 0);
			if (!OrganizationsModel.delete(id)) {
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
