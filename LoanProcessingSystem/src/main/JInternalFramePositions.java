package main;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entities.Position;
import models.PositionModel;
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

public class JInternalFramePositions extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField textFieldPosition;
	private JLabel lblName;
	private JButton btnAdd;
	private JTextField textFieldAuthorityLevel;
	private int current_row = 0;
	private boolean update_flag = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFramePositions frame = new JInternalFramePositions();
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
	public JInternalFramePositions() {
		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBounds(100, 100, 619, 589);
		getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("Positions manager");
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
		mntmUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
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
		mntmDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		popupMenu.add(mntmDelete);

		btnAdd = new JButton("Add");
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.setIcon(new ImageIcon(JInternalFramePositions.class.getResource("/img/add.png")));
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

		textFieldPosition = new JTextField();
		textFieldPosition.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldPosition.setBounds(209, 98, 256, 35);
		textFieldPosition.addKeyListener(new KeyAdapter() {
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
		getContentPane().add(textFieldPosition);
		textFieldPosition.setColumns(10);

		lblName = new JLabel("Position name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(50, 96, 137, 35);
		getContentPane().add(lblName);

		JButton btnReset = new JButton("Reset");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setIcon(new ImageIcon(JInternalFramePositions.class.getResource("/img/reset.png")));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
				loadData();
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReset.setBounds(327, 190, 130, 42);
		getContentPane().add(btnReset);

		JLabel lblInteresetRate = new JLabel("Authority level");
		lblInteresetRate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInteresetRate.setBounds(50, 143, 137, 35);
		getContentPane().add(lblInteresetRate);

		textFieldAuthorityLevel = new JTextField();
		textFieldAuthorityLevel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldAuthorityLevel.setColumns(10);
		textFieldAuthorityLevel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean ret = true;
				try {
					Double.parseDouble(e.getKeyChar() + "");
				} catch (NumberFormatException ee) {
					ret = false;
				}
				if (!ret) {
					e.consume();
				}
			}
		});
		textFieldAuthorityLevel.setBounds(209, 143, 120, 35);
		getContentPane().add(textFieldAuthorityLevel);

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
		defaultTableModel.addColumn("Authority level");
		try {
			if (PositionModel.findAll() == null) {
				throw new Exception("Table positions in database is empty.");
			}
			for (Position position : PositionModel.findAll()) {
				defaultTableModel
						.addRow(new Object[] { position.getId(), position.getName(), position.getAuthority_level() });
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
		textFieldPosition.setText("");
		textFieldAuthorityLevel.setText("");
		btnAdd.setText("Add");
	}

	public void insert() {
		try {
			if (textFieldPosition.getText().trim().isEmpty() || textFieldAuthorityLevel.getText().trim().isEmpty()) {
				throw new Exception("Please fill in all fields.");
			}

			String position_name = textFieldPosition.getText().trim();
			int authority_level = Integer.parseInt(textFieldAuthorityLevel.getText());

			if (PositionModel.isDuplicate(position_name)) {
				throw new Exception("Position name existed.");
			}

			if (!ValidationModel.isValidatedStringWithSpace(position_name)) {
				throw new Exception("Position name invalid");
			}

			if (authority_level >= 4) {
				throw new Exception("There can only be one position with authority level 4 or more.");
			}

			if (authority_level <= 0) {
				throw new Exception("The value of authority level must be between 1 and 3.");
			}

			Position position = new Position(0, position_name, authority_level);

			if (!PositionModel.create(position)) {
				throw new Exception("Update failed");
			} else {
				JOptionPane.showMessageDialog(null, "Update success.");
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

			if (textFieldAuthorityLevel.getText().equals("4")) {
				throw new Exception("Can't modify details of position admin");
			}

			int id = (int) table.getValueAt(selectedIndex, 0);

			if (btnAdd.getText().equals("Add") || !update_flag) {
				Position position = PositionModel.findById(id);
				textFieldPosition.setText(position.getName());
				textFieldAuthorityLevel.setText(position.getAuthority_level() + "");
				btnAdd.setText("Update");
			} else {
				if (textFieldPosition.getText().trim().isEmpty()
						|| textFieldAuthorityLevel.getText().trim().isEmpty()) {
					throw new Exception("Please fill in all fields.");
				}

				String position_name = textFieldPosition.getText().trim();
				int authority_level = Integer.parseInt(textFieldAuthorityLevel.getText());

				Position position_older = PositionModel.findById(id);
				if (!position_older.getName().equalsIgnoreCase(position_name)) {
					if (PositionModel.isDuplicate(position_name)) {
						throw new Exception("Position name existed.");
					}
				}

				if (!ValidationModel.isValidatedStringWithSpace(position_name)) {
					throw new Exception("Position name invalid");
				}

				if (authority_level != 4 && position_older.getName().equals("admin")) {
					throw new Exception("Can't change authority level of admin.");
				}

				if (authority_level >= 4 && !position_older.getName().equals("admin")) {
					throw new Exception("Only admin can have authority level 4 or more.");
				}

				if (authority_level <= 0) {
					throw new Exception("The value of authority level must be between 1 and 3.");
				}

				Position position = new Position(id, position_name, authority_level);
				if (!PositionModel.update(position)) {
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

			if ((int) table.getValueAt(selectedIndex, 2) == 4) {
				throw new Exception("Can't delete position admin");
			}

			int selection = JOptionPane.showConfirmDialog(null,
					"You can only delete a position if there is no staff with that position in the database, continue?",
					"Confirm", JOptionPane.YES_NO_OPTION);
			if (selection != JOptionPane.YES_OPTION) {
				return;
			}

			int id = (int) table.getValueAt(selectedIndex, 0);
			if (!PositionModel.delete(id)) {
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
