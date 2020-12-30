package main;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entities.FineRate;
import models.FineRateModel;
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

public class JInternalFrameFineRates extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField textFieldLowerLimit;
	private JLabel lblLowerLimit;
	private JButton btnAdd;
	private JTextField textFieldFineRate;
	private int current_row = 0;
	private boolean update_flag = false;
	private JTextField textFieldHigherLimit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameFineRates frame = new JInternalFrameFineRates();
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
	public JInternalFrameFineRates() {
		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBounds(100, 100, 619, 589);
		getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("Fine rates manager");
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
		btnAdd.setIcon(new ImageIcon(JInternalFrameFineRates.class.getResource("/img/add.png")));
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

		textFieldLowerLimit = new JTextField();
		textFieldLowerLimit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldLowerLimit.setBounds(161, 96, 120, 35);
		textFieldLowerLimit.addKeyListener(new KeyAdapter() {
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
		getContentPane().add(textFieldLowerLimit);
		textFieldLowerLimit.setColumns(10);

		lblLowerLimit = new JLabel("Lower limit");
		lblLowerLimit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLowerLimit.setBounds(50, 96, 101, 35);
		getContentPane().add(lblLowerLimit);

		JButton btnReset = new JButton("Reset");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setIcon(new ImageIcon(JInternalFrameFineRates.class.getResource("/img/reset.png")));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
				loadData();
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReset.setBounds(327, 190, 130, 42);
		getContentPane().add(btnReset);

		JLabel lblFineRate = new JLabel("Fine rate");
		lblFineRate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFineRate.setBounds(50, 143, 137, 35);
		getContentPane().add(lblFineRate);

		textFieldFineRate = new JTextField();
		textFieldFineRate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldFineRate.setColumns(10);
		textFieldFineRate.addKeyListener(new KeyAdapter() {
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
		textFieldFineRate.setBounds(161, 141, 72, 35);
		getContentPane().add(textFieldFineRate);

		JLabel lblHigherLimit = new JLabel("Higher limit");
		lblHigherLimit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblHigherLimit.setBounds(333, 96, 101, 35);
		getContentPane().add(lblHigherLimit);

		textFieldHigherLimit = new JTextField();
		textFieldHigherLimit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldHigherLimit.setColumns(10);
		textFieldHigherLimit.addKeyListener(new KeyAdapter() {
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
		textFieldHigherLimit.setBounds(444, 96, 120, 35);
		getContentPane().add(textFieldHigherLimit);

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
		defaultTableModel.addColumn("Lower limit");
		defaultTableModel.addColumn("Higher limit");
		defaultTableModel.addColumn("Rate");
		try {
			if (FineRateModel.findAll() == null) {
				throw new Exception("Table fine_rates in database is empty.");
			}
			for (FineRate fine_rate : FineRateModel.findAll()) {
				defaultTableModel.addRow(new Object[] { fine_rate.getId(), fine_rate.getLower_limit(),
						fine_rate.getHigher_limit(), fine_rate.getRate() });
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
		textFieldLowerLimit.setText("");
		textFieldHigherLimit.setText("");
		textFieldFineRate.setText("");
		btnAdd.setText("Add");
	}

	public void insert() {
		try {
			if (textFieldLowerLimit.getText().trim().isEmpty() || textFieldHigherLimit.getText().trim().isEmpty()
					|| textFieldFineRate.getText().trim().isEmpty()) {
				throw new Exception("Please fill in all fields.");
			}

			double lower_limit = Double.parseDouble(textFieldLowerLimit.getText().trim());
			double higher_limit = Double.parseDouble(textFieldHigherLimit.getText().trim());
			float rate = Integer.parseInt(textFieldFineRate.getText());

			FineRate fine_rate = new FineRate(0, lower_limit, higher_limit, rate);

			if (!FineRateModel.create(fine_rate)) {
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

			int id = (int) table.getValueAt(selectedIndex, 0);

			if (btnAdd.getText().equals("Add") || !update_flag) {
				FineRate fine_rate = FineRateModel.findById(id);
				textFieldLowerLimit.setText(fine_rate.getLower_limit() + "");
				textFieldHigherLimit.setText(fine_rate.getHigher_limit() + "");
				textFieldFineRate.setText(fine_rate.getRate() + "");
				btnAdd.setText("Update");
			} else {
				if (textFieldLowerLimit.getText().trim().isEmpty() || textFieldFineRate.getText().trim().isEmpty()) {
					throw new Exception("Please fill in all fields.");
				}

				double lower_limit = Double.parseDouble(textFieldLowerLimit.getText().trim());
				double higher_limit = Double.parseDouble(textFieldHigherLimit.getText().trim());
				float rate = Float.parseFloat(textFieldFineRate.getText());

				int selection = JOptionPane.showConfirmDialog(null,
						"Change a fine rate unnecessarily may cause an error when the system generates fines, continue?",
						"Confirm", JOptionPane.YES_NO_OPTION);
				if (selection != JOptionPane.YES_OPTION) {
					return;
				}
				
				FineRate fine_rate = new FineRate(0, lower_limit, higher_limit, rate);
				if (!FineRateModel.update(fine_rate)) {
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
		int selection = JOptionPane.showConfirmDialog(null,
				"Delete a fine rate may cause an error when the system generates fines, continue?",
				"Confirm", JOptionPane.YES_NO_OPTION);
		if (selection != JOptionPane.YES_OPTION) {
			return;
		}
		
		int selectedIndex = table.getSelectedRow();

		try {
			if (selectedIndex == -1) {
				throw new Exception("Please select a row.");
			}

			int id = (int) table.getValueAt(selectedIndex, 0);
			if (!FineRateModel.delete(id)) {
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
