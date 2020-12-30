package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entities.Message;
import models.ConnectDB;
import models.MessageModel;
import tableAdjuster.TableColumnAdjuster;

import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.awt.Cursor;

public class JInternalFrameNotifications extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableMessages;
	private JLabel lblTitle;
	private SimpleDateFormat simpleDF = new SimpleDateFormat("dd/MM/yyyy");
	private JDateChooser dateChooserStart;
	private JDateChooser dateChooserEnd;
	private final ButtonGroup buttonGroupStatus = new ButtonGroup();
	private JRadioButton rdbtnRead;
	private JRadioButton rdbtnUnread;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameNotifications frame = new JInternalFrameNotifications();
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
	public JInternalFrameNotifications() {
		setBorder(null);
		setClosable(true);
		setMaximizable(true);
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		setBounds(253, 13, 1360, 871);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(125, 359, 1112, 425);
		contentPane.add(scrollPane);

		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		addPopup(scrollPane, popupMenu);

		JMenuItem mntmCheckMessage = new JMenuItem("Check as read");
		mntmCheckMessage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmCheckMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateMessage();
			}
		});
		mntmCheckMessage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		popupMenu.add(mntmCheckMessage);

		tableMessages = new JTable();
		tableMessages.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tableMessages.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tableMessages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableMessages.setComponentPopupMenu(popupMenu);
		scrollPane.setViewportView(tableMessages);

		lblTitle = new JLabel("Messages");
		lblTitle.setBounds(10, 36, 1340, 43);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 35));
		contentPane.add(lblTitle);

		dateChooserStart = new JDateChooser();
		dateChooserStart.setFont(new Font("Tahoma", Font.PLAIN, 15));
		dateChooserStart.setDateFormatString("dd/MM/yyyy");
		dateChooserStart.setBounds(432, 230, 180, 30);
		contentPane.add(dateChooserStart);

		dateChooserEnd = new JDateChooser();
		dateChooserEnd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		dateChooserEnd.setDateFormatString("dd/MM/yyyy");
		dateChooserEnd.setBounds(667, 230, 180, 30);
		contentPane.add(dateChooserEnd);

		JLabel lblNewLabel = new JLabel("From");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(356, 230, 55, 30);
		contentPane.add(lblNewLabel);

		JLabel lblTo = new JLabel("to");
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTo.setBounds(635, 230, 55, 30);
		contentPane.add(lblTo);

		JButton btnSearch = new JButton("Search");
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSearch.setIcon(new ImageIcon(JInternalFrameNotifications.class.getResource("/img/iconfinder_search_326690.png")));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadData_searched();
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSearch.setBounds(507, 296, 126, 42);
		contentPane.add(btnSearch);

		JButton btnReset = new JButton("Reset");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setIcon(new ImageIcon(JInternalFrameNotifications.class.getResource("/img/reset.png")));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadData();
				dateChooserStart.setDate(null);
				dateChooserEnd.setDate(null);
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReset.setBounds(649, 296, 120, 42);
		contentPane.add(btnReset);

		rdbtnRead = new JRadioButton("Read");
		rdbtnRead.setSelected(true);
		buttonGroupStatus.add(rdbtnRead);
		rdbtnRead.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnRead.setBounds(448, 180, 85, 21);
		contentPane.add(rdbtnRead);

		rdbtnUnread = new JRadioButton("Unread");
		buttonGroupStatus.add(rdbtnUnread);
		rdbtnUnread.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnUnread.setBounds(553, 180, 103, 21);
		contentPane.add(rdbtnUnread);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStatus.setBounds(356, 175, 55, 30);
		contentPane.add(lblStatus);
		loadData();

		// hiding title bar
		((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
	}

	public void loadData() {
		DefaultTableModel defaultTableModel = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		defaultTableModel.addColumn("True_id");
		defaultTableModel.addColumn("Message no.");
		defaultTableModel.addColumn("Type");
		defaultTableModel.addColumn("Content");
		defaultTableModel.addColumn("Created date");
		defaultTableModel.addColumn("Status");
		
		int i = MessageModel.findAll().size();
		for (Message message : MessageModel.findAll()) {
			defaultTableModel.addRow(new Object[] { message.getId(), i, message.getType(), message.getContent(),
					simpleDF.format(message.getCreated_date()), message.getStatus() });
			i--;
		}

		tableMessages.setModel(defaultTableModel);

		// modify table
		tableMessages.setRowHeight(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableMessages.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = tableMessages.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		tableMessages.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumnAdjuster tca = new TableColumnAdjuster(tableMessages);
		tca.adjustColumns();

		// hide column true id
		tableMessages.getColumnModel().getColumn(0).setMaxWidth(0);
		tableMessages.getColumnModel().getColumn(0).setMinWidth(0);
		tableMessages.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		tableMessages.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
	}

	public void loadData_searched() {
		DefaultTableModel defaultTableModel = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		defaultTableModel.addColumn("True_id");
		defaultTableModel.addColumn("Message no.");
		defaultTableModel.addColumn("Type");
		defaultTableModel.addColumn("Content");
		defaultTableModel.addColumn("Created date");
		defaultTableModel.addColumn("Status");

		try {
			String status = "";
			Date startDate = simpleDF.parse("01/01/1975");
			Date endDate = simpleDF.parse("01/01/2100");

			if (rdbtnUnread.isSelected()) {
				status = "and status = \"unread\"";
			} else {
				status = "and status = \"read\"";
			}

			if (dateChooserStart.getDate() != null && dateChooserEnd.getDate() != null) {
				startDate = dateChooserStart.getDate();
				endDate = dateChooserEnd.getDate();
			}

			PreparedStatement preparedStatment = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `messages` where created_date >= ? and created_date <= ? " + status
							+ " order by id desc");
			preparedStatment.setDate(1, new java.sql.Date(startDate.getTime()));
			preparedStatment.setDate(2, new java.sql.Date(endDate.getTime()));
			ResultSet resultSet = preparedStatment.executeQuery();

			resultSet.last();
			int i = resultSet.getRow();
			resultSet.beforeFirst();
			while (resultSet.next()) {
				defaultTableModel.addRow(new Object[] { resultSet.getInt("id"), i, resultSet.getString("type"),
						resultSet.getString("content"), simpleDF.format(resultSet.getDate("created_date")),
						resultSet.getString("status") });
				i--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		tableMessages.setModel(defaultTableModel);

		// modify table
		tableMessages.setRowHeight(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableMessages.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = tableMessages.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		tableMessages.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumnAdjuster tca = new TableColumnAdjuster(tableMessages);
		tca.adjustColumns();

		// hide column true id
		tableMessages.getColumnModel().getColumn(0).setMaxWidth(0);
		tableMessages.getColumnModel().getColumn(0).setMinWidth(0);
		tableMessages.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		tableMessages.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
	}

	public void updateMessage() {
		int selectedIndex = tableMessages.getSelectedRow();
		try {
			if (selectedIndex == -1) {
				throw new Exception("Please select a row.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		int id = Integer.parseInt(tableMessages.getValueAt(selectedIndex, 0).toString());
		MessageModel.markAsRead(id);
		loadData();
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
