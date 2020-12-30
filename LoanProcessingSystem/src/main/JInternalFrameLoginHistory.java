package main;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import models.ConnectDB;
import tableAdjuster.TableColumnAdjuster;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import com.toedter.calendar.JDateChooser;
import javax.swing.ImageIcon;
import java.awt.Cursor;

public class JInternalFrameLoginHistory extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JDateChooser dateChooserStart;
	private JDateChooser dateChooserEnd;
	private SimpleDateFormat simpleDF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameLoginHistory frame = new JInternalFrameLoginHistory();
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
	public JInternalFrameLoginHistory() {
		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBounds(100, 100, 619, 650);
		getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("Login history");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitle.setBounds(238, 10, 187, 42);
		getContentPane().add(lblTitle);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 224, 514, 371);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(table);
		
		dateChooserStart = new JDateChooser();
		dateChooserStart.setBounds(104, 97, 201, 33);
		dateChooserStart.setDateFormatString("dd/MM/yyyy HH:mm:ss");
		dateChooserStart.setFont(new Font("Tahoma", Font.PLAIN, 15));
		getContentPane().add(dateChooserStart);
		
		JLabel lblNewLabel = new JLabel("From");
		lblNewLabel.setBounds(50, 97, 44, 33);
		getContentPane().add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearch.setIcon(new ImageIcon(JInternalFrameLoginHistory.class.getResource("/img/iconfinder_search_326690.png")));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSearch.setBounds(185, 161, 126, 42);
		getContentPane().add(btnSearch);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setIcon(new ImageIcon(JInternalFrameLoginHistory.class.getResource("/img/reset.png")));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReset.setBounds(355, 161, 120, 42);
		getContentPane().add(btnReset);
		
		JLabel lblTo = new JLabel("to");
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTo.setBounds(326, 97, 24, 33);
		getContentPane().add(lblTo);
		
		dateChooserEnd = new JDateChooser();
		dateChooserEnd.setBounds(359, 97, 201, 33);
		dateChooserEnd.setDateFormatString("dd/MM/yyyy HH:mm:ss");
		dateChooserEnd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		getContentPane().add(dateChooserEnd);

		loadData();
	}

	public void reset() {
		dateChooserEnd.setDate(null);
		dateChooserStart.setDate(null);
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
		defaultTableModel.addColumn("Username");
		defaultTableModel.addColumn("Login time");
		defaultTableModel.addColumn("Logout time");
		
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("select * from login_history where logout_time is not null order by id desc");
			ResultSet resultSet = preparedStatement.executeQuery();
			int id = 0;
			while (resultSet.next()) {
				defaultTableModel.addRow(new Object[] {
						++id,
						resultSet.getString("username"),
						simpleDF.format(resultSet.getTimestamp("login_time")),
						simpleDF.format(resultSet.getTimestamp("logout_time"))
				});
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

	public void search() {
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
		defaultTableModel.addColumn("Username");
		defaultTableModel.addColumn("Login time");
		defaultTableModel.addColumn("Logout time");
		
		try {
			if (dateChooserEnd.getDate() == null || dateChooserStart.getDate() == null) {
				throw new Exception("Please fill in all fields.");
			}
			
			Date start_date = dateChooserStart.getDate();
			Date end_date = dateChooserEnd.getDate();
			
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("select * from login_history "
							+ "where login_time > ? and login_time < ? and logout_time is not null order by id desc");
			preparedStatement.setDate(1, new java.sql.Date(start_date.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(end_date.getTime()));
			ResultSet resultSet = preparedStatement.executeQuery();
			int id = 0;
			while (resultSet.next()) {
				defaultTableModel.addRow(new Object[] {
						++id,
						resultSet.getString("username"),
						simpleDF.format(resultSet.getTimestamp("login_time")),
						simpleDF.format(resultSet.getTimestamp("logout_time"))
				});
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return;
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
}
