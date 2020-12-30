package main;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import models.ConnectDB;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;

public class JInternalFrameHelp extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textAreaContent;
	private JTree tree;
	private DefaultTreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode());
	private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Loan Processing System") {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			DefaultMutableTreeNode node_1;
			DefaultMutableTreeNode node_2;
			node_1 = new DefaultMutableTreeNode("General");
			node_1.add(new DefaultMutableTreeNode("Login page"));
			node_1.add(new DefaultMutableTreeNode("Home page"));
			node_1.add(new DefaultMutableTreeNode("Messages"));
			node_1.add(new DefaultMutableTreeNode("All customer' report"));
			node_1.add(new DefaultMutableTreeNode("Payment due report"));
			node_1.add(new DefaultMutableTreeNode("Fine report"));
			node_1.add(new DefaultMutableTreeNode("Authority level"));
			node_1.add(new DefaultMutableTreeNode("About other functions"));
			add(node_1);
			node_1 = new DefaultMutableTreeNode("User");
			node_1.add(new DefaultMutableTreeNode("Customers list"));
			node_1.add(new DefaultMutableTreeNode("Loans list"));
			node_1.add(new DefaultMutableTreeNode("Staffs list(user)"));
			node_1.add(new DefaultMutableTreeNode("Fines list"));
			add(node_1);
			node_1 = new DefaultMutableTreeNode("Admin");
			node_1.add(new DefaultMutableTreeNode("Staffs list(admin)"));
			node_2 = new DefaultMutableTreeNode("Admin manager");
			node_2.add(new DefaultMutableTreeNode("Departments"));
			node_2.add(new DefaultMutableTreeNode("Fine rates"));
			node_2.add(new DefaultMutableTreeNode("Loan types"));
			node_2.add(new DefaultMutableTreeNode("Organizations"));
			node_2.add(new DefaultMutableTreeNode("Positions"));
			node_2.add(new DefaultMutableTreeNode("Login history"));
			node_1.add(node_2);
			add(node_1);
		}
	};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameHelp frame = new JInternalFrameHelp();
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
	public JInternalFrameHelp() {
		setBorder(null);
		setClosable(true);
		setMaximizable(true);
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		setBounds(253, 13, 1360, 871);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPaneContent = new JScrollPane();
		scrollPaneContent.setViewportBorder(new EmptyBorder(10, 10, 10, 10));
		scrollPaneContent.setBounds(459, 65, 891, 662);
		contentPane.add(scrollPaneContent);

		textAreaContent = new JTextArea();
		textAreaContent.setWrapStyleWord(true);
		textAreaContent.setLineWrap(true);
		textAreaContent.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		textAreaContent.setBounds(10, 10, 869, 729);
		textAreaContent.setTabSize(2);
		scrollPaneContent.setViewportView(textAreaContent);

		JScrollPane scrollPaneCategory = new JScrollPane();
		scrollPaneCategory.setViewportBorder(new EmptyBorder(10, 10, 10, 10));
		scrollPaneCategory.setBounds(32, 85, 407, 600);
		contentPane.add(scrollPaneCategory);

		tree = new JTree();
		treeModel = new DefaultTreeModel(rootNode);
		tree.setModel(treeModel);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				showHelpContent(getSelectedNodePath());
			}
		});
		tree.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		tree.setRootVisible(false);

		scrollPaneCategory.setViewportView(tree);

		JButton btnSave = new JButton("Save");
		btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save(getSelectedNodePath());
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSave.setBounds(836, 737, 128, 47);
		contentPane.add(btnSave);

		// hiding title bar
		((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
	}

	// save file after change function
	public void save(String path) {
		String new_content = textAreaContent.getText();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement preparedStatment = con
					.prepareStatement("UPDATE `help_details` SET `content`=? WHERE name = ?");
			preparedStatment.setString(1, new_content);
			preparedStatment.setString(2, path);

			if (preparedStatment.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(null, "Update success");
			} else {
				JOptionPane.showMessageDialog(null, "Update failed");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}  finally {
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

	// ##############################
	// suport functions
	public void showHelpContent(String path) {
		Connection con = ConnectDB.getConnection();
		try {	
			PreparedStatement preparedStatment = con.prepareStatement("select * from help_details where name = ?");
			preparedStatment.setString(1, path);
			ResultSet resultSet = preparedStatment.executeQuery();
			if (resultSet.next()) {
				String file_content = resultSet.getString("content");
				textAreaContent.setText(file_content);
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

	public String getSelectedNodePath() {
		try {
			if (tree.getSelectionPath() == null) {
				return "";
			}

			String path_str = tree.getSelectionPath().toString()
					.substring(1, tree.getSelectionPath().toString().length() - 1).toLowerCase();
			String[] path_full = path_str.split(", ");
			String path_final = path_full[1];
			for (int i = 2; i < path_full.length; i++) {
				path_full[i] = path_full[i].replace(' ', '_');
				path_final += '_' + path_full[i];
			}
			return path_final;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return "";
		}
	}
}
