package main;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDesktopPane;
import javax.swing.border.LineBorder;

import org.mindrot.jbcrypt.BCrypt;

import models.ConnectDB;
import models.StaffModel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JInternalFrameLogin extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUsername;
	private JPasswordField passwordField;
	private JCheckBox chckbxRememberLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameLogin frame = new JInternalFrameLogin();
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
	public JInternalFrameLogin() {
		setBorder(null);
		setClosable(true);
		setMaximizable(true);
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		setBounds(100, 100, 702, 633);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);
		desktopPane.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		desktopPane.setBounds(12, 13, 672, 579);
		contentPane.add(desktopPane);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblUsername.setBounds(99, 331, 186, 50);
		desktopPane.add(lblUsername);

		textFieldUsername = new JTextField();
		textFieldUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == ' ')
					e.consume();
			}
		});
		textFieldUsername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLogin_actionPerformed(e);
			}
		});
		textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUsername.setLabelFor(textFieldUsername);
		textFieldUsername.setBounds(297, 331, 272, 50);
		desktopPane.add(textFieldUsername);
		textFieldUsername.setColumns(10);

		JLabel labelPassword = new JLabel("Password");
		labelPassword.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelPassword.setBounds(99, 394, 186, 50);
		desktopPane.add(labelPassword);

		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(JInternalFrameLogin.class.getResource("/img/icon-removebg-preview.png")));
		lblIcon.setBounds(219, 10, 257, 234);
		desktopPane.add(lblIcon);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblLogin.setBounds(250, 257, 152, 37);
		desktopPane.add(lblLogin);

		chckbxRememberLogin = new JCheckBox("Remember me next time");
		chckbxRememberLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxRememberLogin.setBackground(Color.WHITE);
		chckbxRememberLogin.setFont(new Font("Tahoma", Font.PLAIN, 17));
		chckbxRememberLogin.setBounds(219, 464, 257, 25);
		desktopPane.add(chckbxRememberLogin);

		// check if there is a remembered login account
		checkRememberLogin();

		JButton btnNewButton = new JButton("Login");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnLogin_actionPerformed(arg0);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnNewButton.setBounds(262, 510, 127, 42);
		desktopPane.add(btnNewButton);

		passwordField = new JPasswordField();
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLogin_actionPerformed(e);
			}
		});
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == ' ')
					e.consume();
			}
		});
		labelPassword.setLabelFor(passwordField);
		passwordField.setBounds(297, 394, 272, 50);
		desktopPane.add(passwordField);

		// hiding title bar
		((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
	}

	// remember login check
	public void checkRememberLogin() {
		try {
			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `login_history` order by login_time desc limit 1");
			ResultSet resultSetLogin = preparedStatement.executeQuery();

			if (resultSetLogin.next()) {
				if (resultSetLogin.getBoolean("remember")) {
					textFieldUsername.setText(resultSetLogin.getString("username"));
					chckbxRememberLogin.setSelected(true);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// login process
	public void btnLogin_actionPerformed(ActionEvent arg0) {
		try {
			String username = textFieldUsername.getText().trim();
			if (username.isEmpty()) {
				throw new Exception("Username can't be empty!");
			}
			boolean isRemember = chckbxRememberLogin.isSelected();

			PreparedStatement preparedStatement = ConnectDB.getConnection()
					.prepareStatement("SELECT * FROM `staff_profiles` where username = ?");
			preparedStatement.setString(1, username);
			ResultSet resultSetLogin = preparedStatement.executeQuery();

			if (!resultSetLogin.next()) {
				throw new Exception("Username does not exist.");
			} else {
				String db_password = resultSetLogin.getString("password");
				String password = new String(passwordField.getPassword()).trim();
				if (password.isEmpty()) {
					throw new Exception("Password can't be empty!");
				}

				if (BCrypt.checkpw(password, db_password)) {
					JFrameMain.setLogin_staff(StaffModel.findByUsername(username));

					JOptionPane.showMessageDialog(null, "Login success!");

					Date date = new Date();
					Timestamp login_time = new Timestamp(date.getTime());

					preparedStatement = ConnectDB.getConnection().prepareStatement(
							"INSERT INTO `login_history`(`status`, `username`, `remember`, `login_time`, `logout_time`) VALUES (?, ?, ?, ?, ?)");
					preparedStatement.setString(1, "login");
					preparedStatement.setString(2, username);
					preparedStatement.setBoolean(3, isRemember);
					preparedStatement.setTimestamp(4, login_time);
					preparedStatement.setTimestamp(5, null);

					int resultUpdate = preparedStatement.executeUpdate();
					if (resultUpdate == 0) {
						throw new Exception("No row is affected.");
					} else {
						this.setVisible(false);
						// call internal frame main after login success
						JFrameMain jFrameMain = (JFrameMain) this.getTopLevelAncestor();
						jFrameMain.callJInternalFrameMain();
						jFrameMain.setBounds(10, 10, 1650, 960);
						jFrameMain.dailyUpdates();
						jFrameMain.setLocationRelativeTo(null);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Username and password do not match.");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
