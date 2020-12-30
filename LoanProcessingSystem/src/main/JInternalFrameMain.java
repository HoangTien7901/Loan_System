package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mindrot.jbcrypt.BCrypt;

import entities.Staff;
import models.ConnectDB;
import models.PositionModel;
import models.StaffModel;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.JTextArea;

public class JInternalFrameMain extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	public static JDesktopPane desktopPaneContent;
	private JMenuItem mntmCustomersList;
	private JMenuItem mntmStaffProfiles;
	private JMenuItem mntmLoansList;
	private JMenuItem mntmMessages;
	private JMenuItem mntmAllReport;
	private JMenuItem mntmPaymentReport;
	private JMenuItem mntmFineReport;
	private JMenuItem mntmHelps;
	private JTextField textFieldUsername;
	private JPanel panel;

	private boolean isUpdateVisible = false;
	private JPasswordField passwordFieldOldPass;
	private JPasswordField passwordFieldNewPass;
	private JPasswordField passwordFieldConfirmNewPass;

	private Staff login_staff = JFrameMain.getLogin_staff();

	private float[] interest_rates = new float[10];
	private JLabel labelReset = new JLabel("");
	private JLabel lblLogo = new JLabel("");
	public static JButton btnToggleNotification;
	public static JTextArea textAreaNotification;
	private boolean isNotificationShown = true;
	
	
	// 1 - education | 2 - home | 3 - personal | 4 - vehicle

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameMain frame = new JInternalFrameMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public JInternalFrameMain() throws SQLException {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `loan_types`");
			ResultSet resultSet = preparedStatement.executeQuery();
			int i = 1;
			while (resultSet.next()) {
				interest_rates[i] = resultSet.getFloat("interest_rate");
				i++;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		setBorder(null);
		setClosable(true);
		setMaximizable(true);
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		setBounds(0, 0, 1572, 845);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JDesktopPane desktopPaneIcon = new JDesktopPane();
		desktopPaneIcon.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		desktopPaneIcon.setBackground(Color.WHITE);
		desktopPaneIcon.setBounds(12, 13, 243, 223);
		contentPane.add(desktopPaneIcon);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setToolTipText("<html><font size=5>Home</font></html>");
		lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				showDefaultContent();
			}
		});
		lblNewLabel.setIcon(new ImageIcon(JInternalFrameMain.class.getResource("/img/icon-removebg-preview.png")));
		lblNewLabel.setBounds(10, 10, 212, 203);
		desktopPaneIcon.add(lblNewLabel);

		desktopPaneContent = new JDesktopPane();
		desktopPaneContent.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		desktopPaneContent.setBackground(Color.WHITE);
		desktopPaneContent.setBounds(253, 13, 1360, 871);
		contentPane.add(desktopPaneContent);
		desktopPaneContent.setLayout(null);

		showDefaultContent();

		JDesktopPane desktopPaneMenuDownLeft = new JDesktopPane();
		desktopPaneMenuDownLeft.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		desktopPaneMenuDownLeft.setBackground(new Color(255, 250, 240));
		desktopPaneMenuDownLeft.setBounds(12, 234, 268, 650);
		contentPane.add(desktopPaneMenuDownLeft);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		menuBar.setBorder(null);
		menuBar.setBounds(10, 25, 219, 507);
		menuBar.setLayout(new GridLayout(0, 1));
		desktopPaneMenuDownLeft.add(menuBar);

		mntmCustomersList = new JMenuItem("Customers list");
		mntmCustomersList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmCustomersList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				callJInternalFrameCustomersList();
			}
		});
		mntmCustomersList.setBackground(Color.WHITE);
		mntmCustomersList.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mntmCustomersList);

		JSeparator separator_4 = new JSeparator();
		menuBar.add(separator_4);

		mntmStaffProfiles = new JMenuItem("Staffs list");
		mntmStaffProfiles.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmStaffProfiles.setBackground(Color.WHITE);
		mntmStaffProfiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					callJInternalFrameStaffProfile();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmStaffProfiles.setFont(new Font("Segoe UI", Font.PLAIN, 18));

		menuBar.add(mntmStaffProfiles);

		JSeparator separator = new JSeparator();
		menuBar.add(separator);

		mntmLoansList = new JMenuItem("Loans list");
		mntmLoansList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmLoansList.setBackground(Color.WHITE);
		mntmLoansList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					callJInternalFrameLoanDetails();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		mntmLoansList.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mntmLoansList);

		JSeparator separator_1 = new JSeparator();
		menuBar.add(separator_1);

		mntmMessages = new JMenuItem("Messages");
		mntmMessages.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmMessages.setBackground(Color.WHITE);
		mntmMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				callJInternalFrameNotifications();
			}
		});
		mntmMessages.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mntmMessages);

		JSeparator separator_3 = new JSeparator();
		menuBar.add(separator_3);

		mntmAllReport = new JMenuItem("All customer' report");
		mntmAllReport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmAllReport.setBackground(Color.WHITE);
		mntmAllReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				callJInternalFrameReport("all");
			}
		});
		
		JMenuItem mntmFineList = new JMenuItem("Fines list");
		mntmFineList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmFineList.setBackground(Color.WHITE);
		mntmFineList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				callJInternalFrameFineDetails();
			}
		});
		mntmFineList.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mntmFineList);
		
		JSeparator separator_6 = new JSeparator();
		menuBar.add(separator_6);
		mntmAllReport.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mntmAllReport);

		JSeparator separator_41 = new JSeparator();
		menuBar.add(separator_41);

		mntmPaymentReport = new JMenuItem("Payment due report");
		mntmPaymentReport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmPaymentReport.setBackground(Color.WHITE);
		mntmPaymentReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				callJInternalFrameReport("payment_due");
			}
		});
		mntmPaymentReport.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mntmPaymentReport);

		JSeparator separator_42 = new JSeparator();
		menuBar.add(separator_42);

		mntmFineReport = new JMenuItem("Fine report");
		mntmFineReport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmFineReport.setBackground(Color.WHITE);
		mntmFineReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				callJInternalFrameReport("fine");
			}
		});
		mntmFineReport.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mntmFineReport);

		JSeparator separator_2 = new JSeparator();
		menuBar.add(separator_2);

		mntmHelps = new JMenuItem("Helps", KeyEvent.VK_H);
		mntmHelps.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmHelps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				callInternalFrameHelp();
			}
		});
		mntmHelps.setBackground(Color.WHITE);
		mntmHelps.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mntmHelps);

		JSeparator separator_5 = new JSeparator();
		separator_5.setForeground(Color.WHITE);
		menuBar.add(separator_5);
		
		JMenu mnAdminManager = new JMenu("Admin manager");
		mnAdminManager.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mnAdminManager.setBackground(Color.WHITE);
		mnAdminManager.setForeground(Color.BLACK);
		mnAdminManager.setHorizontalAlignment(SwingConstants.CENTER);
		mnAdminManager.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnAdminManager);
		
		JMenuItem mntmDepartment = new JMenuItem("Departments");
		mntmDepartment.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showDepartmentManager();
			}
		});
		mntmDepartment.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnAdminManager.add(mntmDepartment);
		
		JMenuItem mntmLoanType = new JMenuItem("Loan types");
		mntmLoanType.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmLoanType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showLoanTypesManager();
			}
		});
		mntmLoanType.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnAdminManager.add(mntmLoanType);
		
		JMenuItem mntmOrganization = new JMenuItem("Organizations");
		mntmOrganization.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmOrganization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showOrganizationsManager();
			}
		});
		mntmOrganization.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnAdminManager.add(mntmOrganization);
		
		JMenuItem mntmPosition = new JMenuItem("Positions");
		mntmPosition.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmPosition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPositionsManager();
			}
		});
		mntmPosition.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnAdminManager.add(mntmPosition);
		
		JMenuItem mntmLoginHistory = new JMenuItem("Login history");
		mntmLoginHistory.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmLoginHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showLoginHistory();
			}
		});
		
		JMenuItem mntmFineRates = new JMenuItem("Fine rates");
		mntmFineRates.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmFineRates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFineRatesManager();
			}
		});
		mntmFineRates.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnAdminManager.add(mntmFineRates);
		mntmLoginHistory.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnAdminManager.add(mntmLoginHistory);
		
		JSeparator separator_5_1 = new JSeparator();
		menuBar.add(separator_5_1);
		contentPane.setComponentZOrder(desktopPaneContent, 1);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(JInternalFrameMain.class.getResource("/img/logo.png")));
		lblLogo.setBounds(346, 255, 695, 174);
		desktopPaneContent.add(lblLogo);
		
		btnToggleNotification = new JButton("Hide notification");
		btnToggleNotification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isNotificationShown = !isNotificationShown;
				textAreaNotification.setVisible(isNotificationShown);
				if (isNotificationShown) {
					btnToggleNotification.setText("Show notification");
				} else {
					btnToggleNotification.setText("Hide notification");
				}
			}
		});
		btnToggleNotification.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnToggleNotification.setVisible(false);
		btnToggleNotification.setBounds(713, 26, 173, 38);
		desktopPaneContent.add(btnToggleNotification);
		
		textAreaNotification = new JTextArea();
		textAreaNotification.setEditable(false);
		textAreaNotification.setVisible(false);
		textAreaNotification.setBorder(UIManager.getBorder("TextField.border"));
		textAreaNotification.setFont(new Font("Monospaced", Font.ITALIC, 17));
		textAreaNotification.setBounds(713, 86, 456, 122);
		desktopPaneContent.add(textAreaNotification);
		
		if (!PositionModel.findById(JFrameMain.getLogin_staff().getPosition_id()).getName().equals("admin")) {
			mnAdminManager.setVisible(false);
			separator_5_1.setVisible(false);
		}
		
		contentPane.setComponentZOrder(desktopPaneIcon, 2);
		contentPane.setComponentZOrder(desktopPaneMenuDownLeft, 2);

		// hiding title bar
		((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
	}

	public void showDefaultContent() {
		desktopPaneContent.removeAll();
		desktopPaneContent.revalidate();
		desktopPaneContent.repaint();
		JLabel lblWelcome = new JLabel("Welcome, " + JFrameMain.getLogin_staff().getUsername());
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblWelcome.setBounds(10, 10, 348, 66);
		desktopPaneContent.add(lblWelcome);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selection = JOptionPane.showConfirmDialog(null, "Do you want to logout?", "Confirm logout",
						JOptionPane.OK_CANCEL_OPTION);
				if (selection == JOptionPane.OK_OPTION) {
					try {
						PreparedStatement preparedStatement = ConnectDB.getConnection()
								.prepareStatement("SELECT * FROM `login_history` where status = \"login\"");
						ResultSet resultSet = preparedStatement.executeQuery();
						if (resultSet.next()) {
							Date date = new Date();
							Timestamp logout_time = new Timestamp(date.getTime());

							preparedStatement = ConnectDB.getConnection().prepareStatement(
									"UPDATE `login_history` SET `status`= ? ,`logout_time`= ? where status = \"login\"");
							preparedStatement.setString(1, "logout");
							preparedStatement.setTimestamp(2, logout_time);

							int result = preparedStatement.executeUpdate();
							if (result <= 0) {
								throw new Exception("No row is affected.");
							} else {
								backToLoginScreen();
							}
						}
					} catch (Exception exception) {
						// TODO Auto-generated catch block
						exception.printStackTrace();
						JOptionPane.showMessageDialog(null, exception.getMessage());
					}
				}
			}
		});
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogout.setBounds(1164, 26, 142, 38);
		desktopPaneContent.add(btnLogout);

		JButton btnAddcustomer = new JButton("Add new customer");
		btnAddcustomer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAddcustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showInsertCustomerFrame();
			}
		});
		btnAddcustomer.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnAddcustomer.setBounds(556, 700, 288, 66);
		desktopPaneContent.add(btnAddcustomer);

		JButton btnNewButton_2 = new JButton("Change username and password");
		btnNewButton_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isUpdateVisible = !isUpdateVisible;
				panel.setVisible(isUpdateVisible);
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_2.setBounds(326, 22, 246, 29);
		desktopPaneContent.add(btnNewButton_2);

		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 51, 102), 1, true));
		panel.setBounds(20, 86, 569, 265);
		panel.setVisible(false);
		desktopPaneContent.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("Username :");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(49, 24, 114, 38);
		panel.add(lblNewLabel_3);

		textFieldUsername = new JTextField(login_staff.getUsername());
		textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == ' ') {
					e.consume();
				}
			}
		});
		if (PositionModel.findById(JFrameMain.getLogin_staff().getPosition_id()).getName().equals("admin")) {
			textFieldUsername.setEditable(false);
		}
		textFieldUsername.setColumns(10);
		textFieldUsername.setBounds(205, 27, 199, 35);
		panel.add(textFieldUsername);

		JLabel lblNewLabel_3_1 = new JLabel("Old password :");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3_1.setBounds(49, 86, 114, 38);
		panel.add(lblNewLabel_3_1);

		JLabel lblNewLabel_3_1_1 = new JLabel("New password :");
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3_1_1.setBounds(49, 141, 114, 38);
		panel.add(lblNewLabel_3_1_1);

		JLabel lblNewLabel_3_1_1_1 = new JLabel("Confirm password :");
		lblNewLabel_3_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3_1_1_1.setBounds(49, 200, 146, 38);
		panel.add(lblNewLabel_3_1_1_1);

		JButton btnNewButton_3 = new JButton("Update");
		btnNewButton_3.setIcon(new ImageIcon(JInternalFrameMain.class.getResource("/img/update.png")));
		btnNewButton_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textFieldUsername.getText().trim();
				String new_password = new String(passwordFieldNewPass.getPassword());
				String confirm_password = new String(passwordFieldConfirmNewPass.getPassword());
				String old_password = new String(passwordFieldOldPass.getPassword());
				try {
					if (new_password.trim().isEmpty() || confirm_password.trim().isEmpty()
							|| old_password.trim().isEmpty()) {
						throw new Exception("Please fill in all fields");
					}

					PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(
							"select * from staff_profiles where username = ? and id != ?");
					preparedStatement.setString(1, username);
					preparedStatement.setInt(2, JFrameMain.getLogin_staff().getId());
					ResultSet resultSet = preparedStatement.executeQuery();
					if (resultSet.next()) {
						throw new Exception("Username existed.");
					}				
					if (BCrypt.checkpw(old_password, login_staff.getPassword())) {
						if (new_password.equals(confirm_password)) {
							login_staff.setUsername(username);
							login_staff.setPassword(BCrypt.hashpw(new_password, BCrypt.gensalt()));
							if (StaffModel.update(login_staff)) {
								JOptionPane.showMessageDialog(null, "Change success, now you need to login again");
								backToLoginScreen();
							} else {
								throw new Exception("Update failed");
							}
						} else {
							throw new Exception("Password doesn't match");
						}
					} else {
						throw new Exception("Wrong old password");
					}
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}

			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_3.setBounds(432, 103, 126, 42);
		panel.add(btnNewButton_3);

		passwordFieldOldPass = new JPasswordField();
		passwordFieldOldPass.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordFieldOldPass.setBounds(205, 89, 199, 35);
		passwordFieldOldPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == ' ') {
					e.consume();
				}
			}
		});
		panel.add(passwordFieldOldPass);

		passwordFieldNewPass = new JPasswordField();
		passwordFieldNewPass.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordFieldNewPass.setBounds(205, 144, 199, 35);
		passwordFieldNewPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == ' ') {
					e.consume();
				}
			}
		});
		panel.add(passwordFieldNewPass);

		passwordFieldConfirmNewPass = new JPasswordField();
		passwordFieldConfirmNewPass.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordFieldConfirmNewPass.setBounds(205, 200, 199, 35);
		passwordFieldConfirmNewPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == ' ') {
					e.consume();
				}
			}
		});
		panel.add(passwordFieldConfirmNewPass);
		
		labelReset.setBounds(1292, 800, 64, 66);
		labelReset.setIcon(new ImageIcon(JInternalFrameMain.class.getResource("/img/big_reset_icon.png")));
		labelReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFrameMain jFrameMain = new JFrameMain();
				jFrameMain.updateInstallment();
				jFrameMain.updateFines();
				jFrameMain.generateMessages();
				jFrameMain.notifyUnreadMessages();
			}
		});
		labelReset.setToolTipText("<html><font size=5>Reset</font></html>");
		labelReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		desktopPaneContent.add(labelReset);
		
		lblLogo.setIcon(new ImageIcon(JInternalFrameMain.class.getResource("/img/logo.png")));
		lblLogo.setBounds(346, 255, 695, 174);
		desktopPaneContent.add(lblLogo);
	}

	public static JDesktopPane getDesktopPaneContent() {
		return desktopPaneContent;
	}

	public static void setDesktopPaneContent(JDesktopPane desktopPaneContent) {
		JInternalFrameMain.desktopPaneContent = desktopPaneContent;
	}

	// call internal frame functions
	public void callJInternalFrameStaffProfile() throws SQLException {
		desktopPaneContent.removeAll();
		JInternalFrameStaffProfiles jInternalFrameStaffProfiles = new JInternalFrameStaffProfiles();
		desktopPaneContent.add(jInternalFrameStaffProfiles);

		try {
			jInternalFrameStaffProfiles.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jInternalFrameStaffProfiles.setVisible(true);
	}

	public void callJInternalFrameLoanDetails() throws SQLException {
		desktopPaneContent.removeAll();
		JInternalFrameLoanDetails jInternalFrameLoanDetails = new JInternalFrameLoanDetails();
		desktopPaneContent.add(jInternalFrameLoanDetails);

		try {
			jInternalFrameLoanDetails.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jInternalFrameLoanDetails.setVisible(true);
	}

	public void callJInternalFrameNotifications() {
		desktopPaneContent.removeAll();
		JInternalFrameNotifications jInternalFrameNotifications = new JInternalFrameNotifications();
		desktopPaneContent.add(jInternalFrameNotifications);
		try {
			jInternalFrameNotifications.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jInternalFrameNotifications.toFront();
		jInternalFrameNotifications.setVisible(true);
	}

	public void callJInternalFrameReport(String report_type) {
		desktopPaneContent.removeAll();
		JInternalFrameReport jInternalFrameReport = new JInternalFrameReport(report_type);
		desktopPaneContent.add(jInternalFrameReport);
		try {
			jInternalFrameReport.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jInternalFrameReport.setVisible(true);
	}

	public void callJInternalFrameCustomersList() {
		desktopPaneContent.removeAll();
		JInternalFrameCustomersList jInternalFrameCustomersList = new JInternalFrameCustomersList();
		desktopPaneContent.add(jInternalFrameCustomersList);
		try {
			jInternalFrameCustomersList.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jInternalFrameCustomersList.setVisible(true);
	}
	
	public void callInternalFrameHelp() {
		desktopPaneContent.removeAll();
		JInternalFrameHelp jInternalFrameHelp = new JInternalFrameHelp();
		desktopPaneContent.add(jInternalFrameHelp);
		try {
			jInternalFrameHelp.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jInternalFrameHelp.setVisible(true);
	}
	
	public void callJInternalFrameFineDetails() {
		desktopPaneContent.removeAll();
		JInternalFrameFineDetails jInternalFrameFineDetails = new JInternalFrameFineDetails();
		desktopPaneContent.add(jInternalFrameFineDetails);
		try {
			jInternalFrameFineDetails.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jInternalFrameFineDetails.setVisible(true);
	}
	
	public void backToLoginScreen() {
		JFrameMain jFrameMain = (JFrameMain) this.getTopLevelAncestor();
		this.setVisible(false);
		this.toBack();
		jFrameMain.callJInternalFrameLogin();
		jFrameMain.setBounds(100, 100, 712, 650);
		jFrameMain.setLocationRelativeTo(null);
	}
	
	public void showInsertCustomerFrame() {
		JInternalFrameInsertCustomerDetails jInternalFrameInsertCustomerDetails = new JInternalFrameInsertCustomerDetails();
		desktopPaneContent.add(jInternalFrameInsertCustomerDetails);
		jInternalFrameInsertCustomerDetails.toFront();
		jInternalFrameInsertCustomerDetails.setVisible(true);
	}
	
	public void showDepartmentManager() {
		JInternalFrameDepartments jInternalFrameDepartments = new JInternalFrameDepartments();
		desktopPaneContent.add(jInternalFrameDepartments);
		jInternalFrameDepartments.toFront();
		jInternalFrameDepartments.setVisible(true);
	}
	
	public void showLoanTypesManager() {
		JInternalFrameLoanTypes jInternalFrameLoanTypes = new JInternalFrameLoanTypes();
		desktopPaneContent.add(jInternalFrameLoanTypes);
		jInternalFrameLoanTypes.toFront();
		jInternalFrameLoanTypes.setVisible(true);
	}
	
	public void showOrganizationsManager() {
		JInternalFrameOrganizations jInternalFrameOrganizations = new JInternalFrameOrganizations();
		desktopPaneContent.add(jInternalFrameOrganizations);
		jInternalFrameOrganizations.toFront();
		jInternalFrameOrganizations.setVisible(true);
	}
	
	public void showPositionsManager() {
		JInternalFramePositions jInternalFramePositions = new JInternalFramePositions();
		desktopPaneContent.add(jInternalFramePositions);
		jInternalFramePositions.toFront();
		jInternalFramePositions.setVisible(true);
	}
	
	public void showFineRatesManager() {
		JInternalFrameFineRates jInternalFrameFineRates = new JInternalFrameFineRates();
		desktopPaneContent.add(jInternalFrameFineRates);
		jInternalFrameFineRates.toFront();
		jInternalFrameFineRates.setVisible(true);
	}
	
	public void showLoginHistory() {
		JInternalFrameLoginHistory jInternalFrameLoginHistory = new JInternalFrameLoginHistory();
		desktopPaneContent.add(jInternalFrameLoginHistory);
		jInternalFrameLoginHistory.toFront();
		jInternalFrameLoginHistory.setVisible(true);
	}
}
