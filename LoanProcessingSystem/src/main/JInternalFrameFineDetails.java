package main;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import entities.Fine;
import models.ConnectDB;
import models.FineModel;
import tableAdjuster.TableColumnAdjuster;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.swing.JRadioButton;
import com.toedter.calendar.JDateChooser;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Cursor;

public class JInternalFrameFineDetails extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTable jtableFineList;
	private static JPopupMenu popupMenu;
	private static String[] organizations = new String[10];
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton jRadioButtonPending;
	private JRadioButton jRadioButtonDone;
	private JDateChooser jdateChooserCreatedDate;
	private JButton jButtonSearch;
	private JButton jButtonReset;
	private JTextField textFieldId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameFineDetails frame = new JInternalFrameFineDetails();
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
	public JInternalFrameFineDetails() {
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

		JScrollPane scrollPane = new JScrollPane(jtableFineList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(44, 349, 1281, 459);
		contentPane.add(scrollPane);

		popupMenu = new JPopupMenu();
		addPopup(scrollPane, popupMenu);

		JMenuItem jMenuItemUpdate = new JMenuItem("Update");
		jMenuItemUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jMenuItemUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showUpdateFrame();
			}
		});
		jMenuItemUpdate.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		popupMenu.add(jMenuItemUpdate);

		jtableFineList = new JTable();
		jtableFineList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jtableFineList.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(jtableFineList);

		JLabel lblTitle = new JLabel("Fine list");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblTitle.setBounds(10, 10, 1326, 64);
		contentPane.add(lblTitle);

		JLabel lblNewLabel = new JLabel("Status");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(717, 159, 71, 23);
		contentPane.add(lblNewLabel);

		jRadioButtonPending = new JRadioButton("Pending");
		jRadioButtonPending.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jRadioButtonPending.setSelected(true);
		buttonGroup.add(jRadioButtonPending);
		jRadioButtonPending.setBounds(794, 159, 109, 23);
		contentPane.add(jRadioButtonPending);

		jRadioButtonDone = new JRadioButton("Done");
		jRadioButtonDone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonGroup.add(jRadioButtonDone);
		jRadioButtonDone.setBounds(794, 191, 109, 23);
		contentPane.add(jRadioButtonDone);

		jButtonReset = new JButton("Reset");
		jButtonReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonReset.setIcon(new ImageIcon(JInternalFrameFineDetails.class.getResource("/img/reset.png")));
		jButtonReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jButtonReset_actionPerformed(arg0);
			}
		});
		jButtonReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		jButtonReset.setBounds(711, 290, 120, 42);
		contentPane.add(jButtonReset);

		JLabel lblCreatedDate = new JLabel("Created date");
		lblCreatedDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCreatedDate.setBounds(513, 240, 109, 23);
		contentPane.add(lblCreatedDate);

		jdateChooserCreatedDate = new JDateChooser();
		jdateChooserCreatedDate.setBounds(669, 234, 162, 35);
		jdateChooserCreatedDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jdateChooserCreatedDate.setDateFormatString("dd/MM/yyyy");
		contentPane.add(jdateChooserCreatedDate);

		jButtonSearch = new JButton("Search");
		jButtonSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonSearch.setIcon(
				new ImageIcon(JInternalFrameFineDetails.class.getResource("/img/iconfinder_search_326690.png")));
		jButtonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jButtonSearch_actionPerformed(arg0);
			}
		});
		jButtonSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
		jButtonSearch.setBounds(525, 290, 126, 42);
		contentPane.add(jButtonSearch);

		JLabel lblId = new JLabel("Id");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblId.setBounds(508, 161, 44, 23);
		contentPane.add(lblId);

		textFieldId = new JTextField();
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
		textFieldId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldId.setBounds(547, 159, 77, 35);
		contentPane.add(textFieldId);
		textFieldId.setColumns(10);

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

		defaultTableModel.addColumn("Installment details id ");
		defaultTableModel.addColumn("Amount");
		defaultTableModel.addColumn("Created date");
		defaultTableModel.addColumn("Payday");
		defaultTableModel.addColumn("Status");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		List<Fine> fine_list = FineModel.findAll();
		for (Fine fine : fine_list) {
			defaultTableModel.addRow(new Object[] { fine.getInstallment_details_id(), fine.getAmount(),
					simpleDateFormat.format(fine.getCreated_date()),
					fine.getPayday() != null ? simpleDateFormat.format(fine.getPayday()) : null, fine.getStatus()

			});
		}
		jtableFineList.setModel(defaultTableModel);
		jtableFineList.setComponentPopupMenu(popupMenu);

		// modify table
		jtableFineList.setRowHeight(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		jtableFineList.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = jtableFineList.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		TableColumnAdjuster tca = new TableColumnAdjuster(jtableFineList);
		tca.adjustColumns();

		jtableFineList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	// search
	public void jButtonSearch_actionPerformed(ActionEvent arg0) {
		int id = 0;
		String status = "pending";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;

		if (!textFieldId.getText().isEmpty()) {
			id = Integer.parseInt(textFieldId.getText());
		}

		try {
			date = simpleDateFormat.parse("01/01/1990");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (jRadioButtonDone.isSelected()) {
			status = "done";
		} else if (jRadioButtonPending.isSelected()) {
			status = "pending";
		}

		try {
			if (jdateChooserCreatedDate.getDate() != null) {
				date = simpleDateFormat.parse(simpleDateFormat.format(jdateChooserCreatedDate.getDate()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		loadDataBySearch(status, date, id);
	}

	// loadData by Search
	public static void loadDataBySearch(String status, Date date, int id) {
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

		defaultTableModel.addColumn("Installment details id ");
		defaultTableModel.addColumn("Amount");
		defaultTableModel.addColumn("Created date");
		defaultTableModel.addColumn("Payday");
		defaultTableModel.addColumn("Status");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		List<Fine> fine_list = FineModel.findStatusAndDate(status, date, id);
		for (Fine fine : fine_list) {
			defaultTableModel.addRow(new Object[] { fine.getInstallment_details_id(), fine.getAmount(),
					simpleDateFormat.format(fine.getCreated_date()),
					fine.getPayday() != null ? simpleDateFormat.format(fine.getPayday()) : null, fine.getStatus()

			});
		}
		jtableFineList.setModel(defaultTableModel);
		jtableFineList.setComponentPopupMenu(popupMenu);

		// modify table
		jtableFineList.setRowHeight(30);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		jtableFineList.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);

		JTableHeader tableHeaderLoanDetails = jtableFineList.getTableHeader();
		tableHeaderLoanDetails.setFont(new Font("Tahoma", Font.BOLD, 16));

		tableHeaderLoanDetails.setReorderingAllowed(false);

		tableHeaderLoanDetails.setResizingAllowed(false);
		TableColumnAdjuster tca = new TableColumnAdjuster(jtableFineList);
		tca.adjustColumns();

		jtableFineList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	// reset
	public void jButtonReset_actionPerformed(ActionEvent arg0) {
		jRadioButtonPending.setSelected(true);
		jdateChooserCreatedDate.setDate(null);
		textFieldId.setText("");
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

	public void showUpdateFrame() {
		int selectedIndex = jtableFineList.getSelectedRow();
		try {
			if (selectedIndex == -1) {
				throw new Exception("Please select a row.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		int fine_id = Integer.parseInt(jtableFineList.getValueAt(selectedIndex, 0).toString());
		JInternalFrameUpdateFine internalFrameUpdateFine = new JInternalFrameUpdateFine(fine_id);
		getDesktopPane().add(internalFrameUpdateFine);
		internalFrameUpdateFine.toFront();
		internalFrameUpdateFine.setVisible(true);
	}
}
