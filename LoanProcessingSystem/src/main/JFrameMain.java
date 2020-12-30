package main;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemTray;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import entities.Message;
import entities.Staff;
import models.ConnectDB;
import models.MessageModel;
import models.PositionModel;

import javax.swing.JOptionPane;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

public class JFrameMain extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4613227318397707211L;

	private JPanel contentPane;

	public boolean isLogin = false; // to check if user has login or not
	public static Staff login_staff;
	public static int authority_level;

	public SimpleDateFormat simpleDF = new SimpleDateFormat("dd/MM/yyyy");
	public Date today = new Date();

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public static Staff getLogin_staff() {
		return login_staff;
	}

	public static void setLogin_staff(Staff login_staff) {
		JFrameMain.login_staff = login_staff;
		JFrameMain.authority_level = PositionModel.findById(login_staff.getPosition_id()).getAuthority_level();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
					UIManager.put("TableHeader.font", new Font("Tahoma", Font.BOLD, 16));
					UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.BOLD, 15));
					UIManager.put("OptionPane.buttonFont", new Font("Tahoma", Font.PLAIN, 14));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				try {
					JFrameMain frame = new JFrameMain();
					frame.setLocationRelativeTo(null);
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
	public JFrameMain() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JFrameMain.class.getResource("/img/icon.png")));
		setResizable(false);
		setTitle("Loan Processing System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 712, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// call login internal frame
		callJInternalFrameLogin();

		// process when user close application
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
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

						preparedStatement.executeUpdate();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, "Shutdown-thread"));
	}

	// call internal frame functions
	public void callJInternalFrameLogin() {
		JInternalFrameLogin jInternalFrameLogin = new JInternalFrameLogin();
		contentPane.add(jInternalFrameLogin);
		try {
			jInternalFrameLogin.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jInternalFrameLogin.setVisible(true);
	}

	public void callJInternalFrameMain() {
		try {
			JInternalFrameMain jInternalFrameMain = new JInternalFrameMain();
			contentPane.add(jInternalFrameMain);
			jInternalFrameMain.setMaximum(true);
			jInternalFrameMain.setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ###############################################
	// internal working functions
	// generate messages functions
	public void generateMessages() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
		calendar.setTime(new Date());
		Connection con = ConnectDB.getConnection();
		try {
			// get upcoming due installments
			PreparedStatement preparedStatement = con.prepareStatement(
					"SELECT installment_details.id AS installment_id, loan_details.loan_account_no AS loan_account_no,"
							+ " loan_details.id AS loan_details_id, installment_details.installment_term AS installment_term "
							+ "FROM `installment_details`, `loan_details`, `customer_profiles` "
							+ "WHERE loan_details.id = installment_details.loan_details_id AND "
							+ "loan_details.loan_account_no = customer_profiles.loan_account_no AND "
							+ "((DATEDIFF(installment_details.installment_term,CURRENT_DATE()) <= 7 AND "
							+ "installment_details.status = \"pending\") OR (installment_details.status = \"due\"))");

			ResultSet resultSet = preparedStatement.executeQuery();

			// generate messages content
			while (resultSet.next()) {
				Message message = new Message();

				Date today = new Date();
				String content = "";

				if (resultSet.getDate("installment_term").after(today)) {
					content = "Notification for loan account no " + resultSet.getString("loan_account_no")
							+ " - loan id " + resultSet.getInt("loan_details_id") + ", installment id "
							+ resultSet.getInt("installment_id") + " is coming due on "
							+ simpleDF.format(resultSet.getDate("installment_term"));
					message.setType("near due");
				} else {
					content = "Notification for loan account no " + resultSet.getString("loan_account_no")
							+ " - loan id " + resultSet.getInt("loan_details_id") + ", installment id "
							+ resultSet.getInt("installment_id") + " is overdue";
					message.setType("overdue");
				}
				message.setContent(content);
				message.setInstallment_id(resultSet.getInt("installment_id"));
				message.setStatus("unread");
				message.setCreated_date(today);

				// upload messages to database
				MessageModel.create(message);
			}

			// store last update messages date
			preparedStatement = con
					.prepareStatement("UPDATE `help_details` SET `content`=? WHERE name = \"last_update_date\"");
			preparedStatement.setString(1, simpleDF.format(today));
			
			preparedStatement.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void notifyUnreadMessages() {
		Connection con = ConnectDB.getConnection();
		try {
			// count unread message to show notification to current user
			PreparedStatement prepareStatement = con.prepareStatement(
					"SELECT count(*) as num_of_unread_messages FROM `messages` WHERE status = \"unread\"");
			ResultSet resultSet = prepareStatement.executeQuery();
			if (resultSet.next() && resultSet.getInt("num_of_unread_messages") > 0) {
				String notification = "You have " + resultSet.getInt("num_of_unread_messages") + " unread message(s)";

				// notification here #####
				displayNotification(notification);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// update installment function
	public void updateInstallment() {
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement prepareStatement = con.prepareStatement(
					"UPDATE `installment_details` SET `status`= \"due\" WHERE `installment_term` < ? and payday is null");
			prepareStatement.setDate(1, new java.sql.Date(today.getTime()));
			prepareStatement.execute();

			prepareStatement = con.prepareStatement(
					"UPDATE `installment_details` SET `status`= \"pending\" WHERE `installment_term` > ? and `status`= \"due\" and payday is null");
			prepareStatement.setDate(1, new java.sql.Date(today.getTime()));
			prepareStatement.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// check due installment and create fine
	public void updateFines() {
		Connection con = ConnectDB.getConnection();
		try {
			// select due installment
			PreparedStatement prepareStatement = con
					.prepareStatement("SELECT " + "ld.loan_account_no AS loan_account_no, "
							+ "id.id AS installment_details_id, " + "id.amount AS installment_amount, "
							+ "ld.amount AS loan_amount, ld.balance_to_be_paid as balance_to_be_paid, ld.id as loan_id,"
							+ "DATE_ADD( id.installment_term, INTERVAL 1 DAY ) AS created_date "
							+ "FROM installment_details id, loan_details ld "
							+ "WHERE id.status = \"due\" AND id.loan_details_id = ld.id AND id.payday IS NULL");
			ResultSet resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				// check if an installment has a fine yet.
				prepareStatement = con.prepareStatement(
						"select count(*) as num_of_fine FROM `fine_details` WHERE `installment_details_id` = ?");
				prepareStatement.setInt(1, resultSet.getInt("installment_details_id"));

				ResultSet resultSet2 = prepareStatement.executeQuery();
				resultSet2.next();

				if (resultSet2.getInt("num_of_fine") == 0) {
					double installment_amount = resultSet.getDouble("installment_amount");
					double balance_to_be_paid = resultSet.getDouble("balance_to_be_paid");

					prepareStatement = con.prepareStatement(
							"SELECT rate FROM `fine_rates` WHERE ? BETWEEN lower_limit and higher_limit");
					prepareStatement.setDouble(1, installment_amount);
					ResultSet resultSetRate = prepareStatement.executeQuery();
					float fine_rate = 0;
					if (resultSetRate.next()) {
						fine_rate = resultSetRate.getFloat("rate");
					}

					prepareStatement = con.prepareStatement(
							"INSERT INTO `fine_details`(`installment_details_id`, `amount`, `created_date`) "
									+ "VALUES (?,?,?)");
					prepareStatement.setInt(1, resultSet.getInt("installment_details_id"));

					double fine_amount = Math.ceil(balance_to_be_paid * (fine_rate / 100));

					prepareStatement.setDouble(2, fine_amount);
					prepareStatement.setDate(3, resultSet.getDate("created_date"));
					prepareStatement.executeUpdate();

					prepareStatement = con
							.prepareStatement("UPDATE `loan_details` SET `total_fine`= `total_fine` + ? WHERE id = ?");
					prepareStatement.setDouble(1, fine_amount);
					prepareStatement.setInt(2, resultSet.getInt("loan_id"));
					prepareStatement.executeUpdate();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// update message every day
	public void dailyUpdates() {
		updateInstallment();
		updateFines();

		Connection con = ConnectDB.getConnection();
		try {
			
			PreparedStatement preparedStatment = con.prepareStatement("select * from help_details where name = \"last_update_date\"");
			ResultSet resultSet = preparedStatment.executeQuery();
			
			String line = "";
			if (resultSet.next()) {
				line = resultSet.getString("content");
			}

			// if this is the first day or a date different with last update date, make
			// message
			if (line == null || (line != null && !line.equals(simpleDF.format(today)))) {
				generateMessages();
				// notify
				notifyUnreadMessages();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// support functions
	public void displayNotification(String message) throws AWTException {
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();

			// If the icon is a file
			Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
			// Alternative (if the icon is on the classpath):
			// Image image =
			// Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

			TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
			// Let the system resize the image if needed
			trayIcon.setImageAutoSize(true);
			// Set tooltip text for the tray icon
			trayIcon.setToolTip("Notifications");
			tray.add(trayIcon);

			trayIcon.displayMessage(message, "Loan processing system", MessageType.INFO);
		} else {
			JInternalFrameMain.btnToggleNotification.setVisible(true);
			JInternalFrameMain.textAreaNotification.setVisible(true);
		}
	}
}
