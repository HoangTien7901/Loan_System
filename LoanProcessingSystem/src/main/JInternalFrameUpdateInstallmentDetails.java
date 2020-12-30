package main;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import entities.Installment;
import models.InstallmentModel;

import com.toedter.calendar.JDateChooser;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Cursor;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class JInternalFrameUpdateInstallmentDetails extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int id;
	private static int loan_details_id;
	private JTextField jtextFieldId;
	private JTextField jtextFieldInsatllmentTerm;
	private JTextField jtextFieldamount;
	private JDateChooser jdateChooserPayday;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton jRadioButtonPending;
	private JRadioButton jRadioButtonDone;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameUpdateInstallmentDetails frame = new JInternalFrameUpdateInstallmentDetails(id,
							loan_details_id);
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
	public JInternalFrameUpdateInstallmentDetails(int id, int loan_details_id) {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 15));
		setClosable(true);
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBounds(150, 150, 571, 505);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Installment Detail Update");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 27, 547, 42);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Id");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(30, 79, 99, 25);
		getContentPane().add(lblNewLabel_1);

		jtextFieldId = new JTextField();
		jtextFieldId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldId.setEditable(false);
		jtextFieldId.setBounds(28, 110, 166, 35);
		getContentPane().add(jtextFieldId);
		jtextFieldId.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Payday");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(32, 165, 114, 28);
		getContentPane().add(lblNewLabel_2);

		jdateChooserPayday = new JDateChooser();
		jdateChooserPayday.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jdateChooserPayday.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if ("date".equals(evt.getPropertyName())) {
					if ((Date) evt.getNewValue() != null) {
						jRadioButtonDone.setSelected(true);
					}
				}
			}
		});
		jdateChooserPayday.setDateFormatString("dd/MM/yyyy");
		jdateChooserPayday.setBounds(30, 203, 164, 35);
		getContentPane().add(jdateChooserPayday);

		JLabel lblInstallmentTerm = new JLabel("Installment Term");
		lblInstallmentTerm.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInstallmentTerm.setBounds(314, 165, 155, 21);
		getContentPane().add(lblInstallmentTerm);

		jtextFieldInsatllmentTerm = new JTextField();
		jtextFieldInsatllmentTerm.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldInsatllmentTerm.setEditable(false);
		jtextFieldInsatllmentTerm.setBounds(314, 198, 203, 35);
		getContentPane().add(jtextFieldInsatllmentTerm);
		jtextFieldInsatllmentTerm.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Amount");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(32, 248, 97, 21);
		getContentPane().add(lblNewLabel_3);

		jtextFieldamount = new JTextField();
		jtextFieldamount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldamount.setEditable(false);
		jtextFieldamount.setBounds(32, 279, 164, 35);
		getContentPane().add(jtextFieldamount);
		jtextFieldamount.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("<html>Status <font color=red>(*)</font></html>");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(314, 268, 67, 41);
		getContentPane().add(lblNewLabel_4);

		jRadioButtonPending = new JRadioButton("Pending");
		jRadioButtonPending.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jdateChooserPayday.setDate(null);
			}
		});
		jRadioButtonPending.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonGroup.add(jRadioButtonPending);
		jRadioButtonPending.setSelected(true);
		jRadioButtonPending.setBounds(408, 262, 109, 23);
		getContentPane().add(jRadioButtonPending);

		jRadioButtonDone = new JRadioButton("Done");
		jRadioButtonDone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonGroup.add(jRadioButtonDone);
		jRadioButtonDone.setBounds(408, 293, 109, 23);
		getContentPane().add(jRadioButtonDone);

		JButton jButtonUpdate = new JButton("Update");
		jButtonUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonUpdate.setIcon(new ImageIcon(JInternalFrameUpdateInstallmentDetails.class.getResource("/img/update.png")));
		jButtonUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonUpdate_actionPerformed(e, loan_details_id);
			}
		});
		jButtonUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
		jButtonUpdate.setBounds(233, 394, 126, 42);
		getContentPane().add(jButtonUpdate);
		loadData(id);
	}

//loadData by id
	public void loadData(int id) {
		Installment installmentDetails = InstallmentModel.findById(id);
		jtextFieldId.setText(String.valueOf(installmentDetails.getId()));
		jtextFieldamount.setText(String.valueOf(installmentDetails.getAmount()));
		if (installmentDetails.getPayday() != null) {
			jdateChooserPayday.setDate(installmentDetails.getPayday());
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String installment_term = null;
		if (installmentDetails.getInstallment_term() != null) {
			installment_term = simpleDateFormat.format(installmentDetails.getInstallment_term());
		}
		jtextFieldInsatllmentTerm.setText(installment_term);
		if (jRadioButtonDone.getText().toLowerCase().equalsIgnoreCase(installmentDetails.getStatus())) {
			jRadioButtonDone.setSelected(true);
		} else {
			jRadioButtonPending.setSelected(true);
		}
	}

//button Update
	public void jButtonUpdate_actionPerformed(ActionEvent e, int loan_details_id) {
		try {
			Installment installmentDetails = new Installment();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			installmentDetails.setId(Integer.parseInt(jtextFieldId.getText()));
			installmentDetails.setAmount(Double.parseDouble(jtextFieldamount.getText()));
			Date date;

			if (jdateChooserPayday.getDate() == null && jRadioButtonDone.isSelected()) {
				throw new Exception("You must fill in payday field if status is done.");
			} else {
				date = jdateChooserPayday.getDate() == null ? null : jdateChooserPayday.getDate();
				installmentDetails.setPayday(date);
			}

			try {
				installmentDetails.setInstallment_term(simpleDateFormat.parse(jtextFieldInsatllmentTerm.getText()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (jRadioButtonPending.isSelected()) {
				installmentDetails.setStatus(jRadioButtonPending.getText().toLowerCase().trim());
			} else {
				installmentDetails.setStatus(jRadioButtonDone.getText().toLowerCase().trim());
			}

			if (InstallmentModel.update(installmentDetails)) {
				JOptionPane.showMessageDialog(null, "Update success");
				JInternalFrameInstallmentDetails.loadData(loan_details_id);
				JInternalFrameLoanDetails.loadData();
				this.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(null, "Update failed");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			JOptionPane.showMessageDialog(null, exception.getMessage());
		}
		
	}
}
