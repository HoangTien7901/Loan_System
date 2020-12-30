package main;

import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import entities.Fine;
import models.FineModel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class JInternalFrameUpdateFine extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int fine_id;
	private JTextField jtextFieldInstrallmentId;
	private JTextField jtextFieldAmount;
	private JTextField jtextFieldCreatedDate;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton jButtonUpdate;
	private JDateChooser jdateChooserPayday;
	private JRadioButton jRadioButtonPending;
	private JRadioButton jRadioButtonDone;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameUpdateFine frame = new JInternalFrameUpdateFine(fine_id);
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
	public JInternalFrameUpdateFine(int fine_id) {
		setClosable(true);
		setBounds(100, 100, 564, 476);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Update Fine Detail");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 27, 532, 43);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Installment detail id");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(26, 110, 162, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Amount");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(302, 111, 94, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblCreatedDate = new JLabel("Created Date");
		lblCreatedDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCreatedDate.setBounds(26, 191, 162, 14);
		getContentPane().add(lblCreatedDate);

		JLabel lblPay = new JLabel("Payday");
		lblPay.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPay.setBounds(302, 192, 162, 14);
		getContentPane().add(lblPay);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStatus.setBounds(26, 283, 81, 14);
		getContentPane().add(lblStatus);

		jtextFieldInstrallmentId = new JTextField();
		jtextFieldInstrallmentId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldInstrallmentId.setEditable(false);
		jtextFieldInstrallmentId.setBounds(26, 135, 162, 35);
		getContentPane().add(jtextFieldInstrallmentId);
		jtextFieldInstrallmentId.setColumns(10);

		jtextFieldAmount = new JTextField();
		jtextFieldAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldAmount.setEditable(false);
		jtextFieldAmount.setColumns(10);
		jtextFieldAmount.setBounds(302, 135, 162, 35);
		getContentPane().add(jtextFieldAmount);

		jtextFieldCreatedDate = new JTextField();
		jtextFieldCreatedDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jtextFieldCreatedDate.setEditable(false);
		jtextFieldCreatedDate.setColumns(10);
		jtextFieldCreatedDate.setBounds(26, 216, 162, 35);
		getContentPane().add(jtextFieldCreatedDate);

		jdateChooserPayday = new JDateChooser();
		jdateChooserPayday.setBounds(302, 216, 162, 35);
		jdateChooserPayday.setDateFormatString("dd/MM/yyyy");
		jdateChooserPayday.setFont(new Font("Tahoma", Font.PLAIN, 15));
		getContentPane().add(jdateChooserPayday);

		jRadioButtonPending = new JRadioButton("Pending");
		jRadioButtonPending.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonGroup.add(jRadioButtonPending);
		jRadioButtonPending.setBounds(133, 277, 109, 26);
		getContentPane().add(jRadioButtonPending);

		jRadioButtonDone = new JRadioButton("Done");
		jRadioButtonDone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonGroup.add(jRadioButtonDone);
		jRadioButtonDone.setBounds(302, 273, 109, 30);
		getContentPane().add(jRadioButtonDone);

		jButtonUpdate = new JButton("Update");
		jButtonUpdate.setIcon(new ImageIcon(JInternalFrameUpdateFine.class.getResource("/img/update.png")));
		jButtonUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
		jButtonUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jButtonUpdate_actionPerformed(arg0);
			}
		});
		jButtonUpdate.setBounds(219, 366, 126, 42);
		getContentPane().add(jButtonUpdate);
		
		loadData(fine_id);

	}
	public void loadData(int fine_id) {
		Fine fine  = FineModel.findByInstallmentId(fine_id);
		jtextFieldInstrallmentId.setText(String.valueOf(fine.getInstallment_details_id()));
		jtextFieldAmount.setText(String.valueOf(fine.getAmount()));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		jtextFieldCreatedDate.setText(simpleDateFormat.format(fine.getCreated_date()));
		if(fine.getPayday() == null) {
			jdateChooserPayday.setDate(null);
		}else {
			jdateChooserPayday.setDate(fine.getPayday());
		}
		if(fine.getStatus().equalsIgnoreCase("done")) {
			jRadioButtonDone.setSelected(true);
		}else {
			jRadioButtonPending.setSelected(true);
		}
	}
	public void jButtonUpdate_actionPerformed(ActionEvent arg0) {
		Fine fine = new Fine();
		Date date = null;
		fine.setInstallment_details_id(Integer.parseInt(jtextFieldInstrallmentId.getText()));
		try {
			if (jdateChooserPayday.getDate() != null) {
				fine.setPayday(jdateChooserPayday.getDate());
				jRadioButtonDone.setSelected(true);
			} else {
				fine.setPayday(date);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (jRadioButtonDone.isSelected()) {
			fine.setStatus("done");
		} else {
			fine.setStatus("pending");
		}
		
		if (jdateChooserPayday.getDate() == null && jRadioButtonDone.isSelected()) {
			JOptionPane.showMessageDialog(null, "Payday can't be null if status is done.");
			return;
		}
		
		if (FineModel.update(fine)) {
			JOptionPane.showMessageDialog(null, "Update Success");
			this.setVisible(false);
			JInternalFrameFineDetails.loadData();
		} else {
			JOptionPane.showMessageDialog(null, "Update Failed");
		}
	}
}
