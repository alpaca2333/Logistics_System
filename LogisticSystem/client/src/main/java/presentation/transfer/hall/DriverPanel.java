/*
 * Created by JFormDesigner on Mon Nov 30 22:38:57 CST 2015
 */

package presentation.transfer.hall;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import data.message.ResultMessage;
import data.vo.DriverInfoVO;
import data.vo.DriverListVO;
import businesslogic.service.Transfer.hall.DriverManagementService;

/**
 * @author sunxu
 */
public class DriverPanel extends JPanel {
	DriverManagementService driverManagement;
	DriverListVO driverList;
	DriverInfoVO driver;

	public DriverPanel(DriverManagementService driverManagement) {
		initComponents();
		this.driverManagement = driverManagement;
		showDriverButton.setEnabled(false);
		this.setVisible(true);
		setList();
	}

	// ===========================显示设置=========================

	private void setDriverVO() {
		if (driver != null) {
			setTextFieldEditable(false);
			setTextFieldEnabled(false);
			name.setEnabled(false);
			num.setEnabled(false);
			id.setEnabled(false);
			gender.setEnabled(false);
			phone.setEnabled(false);
			status.setEnabled(false);
			bornY.setEnabled(false);
			bornM.setEnabled(false);
			bornD.setEnabled(false);
			licenseY.setEnabled(false);
			licenseM.setEnabled(false);
			licenseD.setEnabled(false);
			name.setText(driver.name);
			num.setText(driver.driverID);
			id.setText(driver.IDCard);
			gender.setText(driver.gender);
			phone.setText(driver.phoneNum);
			status.setText(driver.engaged);
			bornY.setText(driver.bornY);
			bornM.setText(driver.bornM);
			bornD.setText(driver.bornD);
			licenseY.setText(driver.licenseY);
			licenseM.setText(driver.licenseM);
			licenseD.setText(driver.licenseD);
			modifyButton.setEnabled(true);
			saveButton.setEnabled(false);
			driverPane.validate();
			driverPane.updateUI();
			driverPane.repaint();
		} else {
			JOptionPane.showMessageDialog(null, "未能获取司机信息", "异常",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void setList() {
		driverList = driverManagement.getDrivers();
		if (driverList != null) {
			driverTable.setEnabled(true);
			DefaultTableModel model = new DefaultTableModel(driverList.info,
					driverList.header);
			driverTable.setModel(model);
			driverTable.validate();
			driverTable.updateUI();
			driverTable.repaint();
			if (driverPane != null) {
				remove(driverPane);
				add(driverListPane);
				driverListPane.validate();
				driverListPane.updateUI();
				driverListPane.setVisible(true);
			}

		} else {
			JOptionPane.showMessageDialog(null, "司机信息为空", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// ============================监听=============================
	private void driverTableMouseClicked(MouseEvent e) {
		showDriverButton.setEnabled(true);
	}

	private void showDriverButtonMouseClicked(MouseEvent e) {
		if (driverTable.isEnabled()) {
			deleteButton.setVisible(true);
			modifyButton.setVisible(true);
			saveButton.setVisible(true);
		int row = driverTable.getSelectedRow();
		String id = (String) driverTable.getValueAt(row, driverList.getIDRow());
		long idNum = Long.parseLong(id);
		driver = driverManagement.chooseDriver(idNum);
		setDriverVO();
		remove(driverListPane);
		add(driverPane);
		driverPane.updateUI();
		driverPane.setVisible(true);
		}
	}

	private void addDriverButtonMouseClicked(MouseEvent e) {
		setTextFieldEditable(true);
		removeInfoInTextField();
		driver = new DriverInfoVO();
		deleteButton.setVisible(false);
		modifyButton.setVisible(false);
		saveButton.setVisible(true);
		saveButton.setEnabled(true);
		remove(driverListPane);
		add(driverPane);
		driverPane.validate();
		driverPane.updateUI();
		driverPane.setVisible(true);
	}

	private void setTextFieldEditable(boolean b){
		name.setEditable(b);
		id.setEditable(b);
		gender.setEditable(b);
		phone.setEditable(b);
		bornD.setEditable(b);
		bornM.setEditable(b);
		bornY.setEditable(b);
		licenseD.setEditable(b);
		licenseM.setEditable(b);
		licenseY.setEditable(b);
	}
	
	private void setTextFieldEnabled(boolean b){
		name.setEnabled(b);
		num.setEnabled(b);
		id.setEnabled(b);
		gender.setEnabled(b);
		phone.setEnabled(b);
		status.setEnabled(b);
		bornY.setEnabled(b);
		bornM.setEnabled(b);
		bornD.setEnabled(b);
		licenseY.setEnabled(b);
		licenseM.setEnabled(b);
		licenseD.setEnabled(b);
	}
	
	private void removeInfoInTextField(){
		setTextFieldEnabled(true);
		name.setText("");
		num.setText("保存后生成");
		status.setText("空闲");
		id.setText("");
		gender.setText("");
		phone.setText("");
		bornY.setText("");
		bornM.setText("");
		bornD.setText("");
		licenseD.setText("");
		licenseM.setText("");
		licenseY.setText("");
	}
	
	private void modifyButtonMouseClicked(MouseEvent e) {
		setTextFieldEditable(true);
		setTextFieldEnabled(true);
		status.setEditable(true);
		modifyButton.setEnabled(false);// 只能点击一次，点击后失效
		saveButton.setEnabled(true);// 点击修改后，保存按钮可用
	}

	private void cancelButtonMouseClicked(MouseEvent e) {
		if (modifyButton.isVisible() || addDriverButton.isVisible()) {
			cancelDialog.setVisible(true);
		} else {
			remove(driverPane);
			add(driverListPane);
			driverListPane.validate();
			driverListPane.updateUI();
			driverListPane.setVisible(true);
		}
	}

	private void cancelSureButtonMouseClicked(MouseEvent e) {
		cancelDialog.setVisible(false);
		remove(driverPane);
		add(driverListPane);
		driverPane.setVisible(false);
		driverListPane.validate();
		driverListPane.updateUI();
		driverListPane.setVisible(true);
		removeInfoInTextField();
	}

	private boolean checkAddInput() {
		boolean checkResult = true;
		if (name.getText().equals("")) {
			checkResult = false;
		} else if (id.getText().equals("")) {
			checkResult = false;
		} else if (gender.getText().equals("")) {
			checkResult = false;
		} else if (phone.getText().equals("")) {
			checkResult = false;
		} else if (bornY.getText().equals("")) {
			checkResult = false;
		} else if (bornM.getText().equals("")) {
			checkResult = false;
		} else if (bornD.getText().equals("")) {
			checkResult = false;
		} else if (licenseY.getText().equals("")) {
			checkResult = false;
		}else if (licenseM.getText().equals("")) {
			checkResult = false;
		}else if (licenseD.getText().equals("")) {
			checkResult = false;
		}else if((!status.getText().equals("空闲")) && (!status.getText().equals("货运"))){
			checkResult = false;
		}else{
			;
		}
	
		return checkResult;
	}

	private void saveButtonMouseClicked(MouseEvent e) {
		if (checkAddInput()) {
			driver.name = name.getText();
			driver.IDCard = id.getText();
			driver.gender = gender.getText();
			driver.phoneNum = phone.getText();
			driver.engaged = status.getName();
			driver.bornDate = bornY.getText() + "/" + bornM.getText() + "/" + bornD.getText();
			driver.timeLimit = licenseY.getText() + "/" + licenseM.getText() + "/" + licenseD.getText();
			ResultMessage result = ResultMessage.FAILED;
			if (modifyButton.isVisible()) {
				result = driverManagement.modifyDriver(driver);
			}else {
				result = driverManagement.addDriver(driver);
			}
			if (result == ResultMessage.SUCCESS) {
				JOptionPane.showMessageDialog(null, "保存成功", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				setList();
			} else {
				JOptionPane.showMessageDialog(null, "保存未成功,请稍后再试", "异常",
						JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "输入有误或不完整，请重新检查输入", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void notCancelButtonMouseReleased(MouseEvent e) {
		cancelDialog.setVisible(false);
	}

	private void deleteButtonMouseClicked(MouseEvent e) {
		long id = Long.parseLong(driver.driverID);
		ResultMessage result = driverManagement.deleteDriver(id);
		if (result == ResultMessage.SUCCESS) {
			JOptionPane.showMessageDialog(null, "已删除", "提示", JOptionPane.INFORMATION_MESSAGE);
			DefaultTableModel model = (DefaultTableModel) driverTable.getModel();
			int row = driverTable.getSelectedRow();
			model.removeRow(row);
			driverTable.updateUI();
			driverTable.repaint();
			remove(driverPane);
			add(driverListPane);
			driverListPane.updateUI();
			driverListPane.setVisible(true);
		}else {
			JOptionPane.showMessageDialog(null, "操作失败", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		driverListPane = new JTabbedPane();
		panel1 = new JPanel();
		scrollPane1 = new JScrollPane();
		driverTable = new JTable();
		addDriverButton = new JButton();
		showDriverButton = new JButton();
		driverPane = new JTabbedPane();
		panel2 = new JPanel();
		saveButton = new JButton();
		modifyButton = new JButton();
		cancelButton = new JButton();
		label3 = new JLabel();
		label4 = new JLabel();
		label5 = new JLabel();
		label6 = new JLabel();
		label7 = new JLabel();
		label8 = new JLabel();
		label9 = new JLabel();
		name = new JTextField();
		num = new JTextField();
		id = new JTextField();
		gender = new JTextField();
		phone = new JTextField();
		status = new JTextField();
		bornY = new JTextField();
		licenseY = new JTextField();
		label10 = new JLabel();
		label11 = new JLabel();
		bornM = new JTextField();
		label12 = new JLabel();
		bornD = new JTextField();
		label13 = new JLabel();
		label14 = new JLabel();
		licenseM = new JTextField();
		label15 = new JLabel();
		licenseD = new JTextField();
		label16 = new JLabel();
		deleteButton = new JButton();
		label17 = new JLabel();
		cancelDialog = new JDialog();
		panel = new JPanel();
		label1 = new JLabel();
		label2 = new JLabel();
		cancelSureButton = new JButton();
		notCancelButton = new JButton();

		//======== this ========
		setLayout(new BorderLayout());

		//======== driverListPane ========
		{

			//======== panel1 ========
			{

				//======== scrollPane1 ========
				{

					//---- driverTable ----
					driverTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
					driverTable.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							driverTableMouseClicked(e);
						}
					});
					scrollPane1.setViewportView(driverTable);
				}

				//---- addDriverButton ----
				addDriverButton.setText("\u6dfb\u52a0");
				addDriverButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						addDriverButtonMouseClicked(e);
					}
				});

				//---- showDriverButton ----
				showDriverButton.setText("\u67e5\u770b");
				showDriverButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						showDriverButtonMouseClicked(e);
					}
				});

				GroupLayout panel1Layout = new GroupLayout(panel1);
				panel1.setLayout(panel1Layout);
				panel1Layout.setHorizontalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 688, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel1Layout.createParallelGroup()
								.addComponent(addDriverButton, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
								.addComponent(showDriverButton, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
							.addContainerGap())
				);
				panel1Layout.setVerticalGroup(
					panel1Layout.createParallelGroup()
						.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
						.addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
							.addContainerGap(284, Short.MAX_VALUE)
							.addComponent(showDriverButton)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(addDriverButton)
							.addContainerGap())
				);
			}
			driverListPane.addTab("\u53f8\u673a\u5217\u8868", panel1);
		}
		add(driverListPane, BorderLayout.CENTER);

		//======== driverPane ========
		{

			//======== panel2 ========
			{

				//---- saveButton ----
				saveButton.setText("\u4fdd\u5b58");
				saveButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						saveButtonMouseClicked(e);
					}
				});

				//---- modifyButton ----
				modifyButton.setText("\u4fee\u6539");
				modifyButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						modifyButtonMouseClicked(e);
					}
				});

				//---- cancelButton ----
				cancelButton.setText("\u53d6\u6d88");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						cancelButtonMouseClicked(e);
					}
				});

				//---- label3 ----
				label3.setText("\u59d3\u540d");

				//---- label4 ----
				label4.setText("\u7f16\u53f7");

				//---- label5 ----
				label5.setText("\u8eab\u4efd\u8bc1\u53f7");

				//---- label6 ----
				label6.setText("\u6027\u522b");

				//---- label7 ----
				label7.setText("\u8054\u7cfb\u7535\u8bdd");

				//---- label8 ----
				label8.setText("\u5de5\u4f5c\u72b6\u6001");

				//---- label9 ----
				label9.setText("\u51fa\u751f\u65e5\u671f");

				//---- num ----
				num.setEditable(false);
				num.setText("\u4fdd\u5b58\u540e\u751f\u6210");

				//---- status ----
				status.setEditable(false);
				status.setText("\u7a7a\u95f2");

				//---- label10 ----
				label10.setText("\u9a7e\u9a76\u8bc1\u671f\u9650");

				//---- label11 ----
				label11.setText("\u5e74");

				//---- label12 ----
				label12.setText("\u6708");

				//---- label13 ----
				label13.setText("\u65e5");

				//---- label14 ----
				label14.setText("\u5e74");

				//---- label15 ----
				label15.setText("\u6708");

				//---- label16 ----
				label16.setText("\u65e5");

				//---- deleteButton ----
				deleteButton.setText("\u5220\u9664");
				deleteButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						deleteButtonMouseClicked(e);
					}
				});

				//---- label17 ----
				label17.setText("\uff08\u7a7a\u95f2\u3001\u8d27\u8fd0\uff09");

				GroupLayout panel2Layout = new GroupLayout(panel2);
				panel2.setLayout(panel2Layout);
				panel2Layout.setHorizontalGroup(
					panel2Layout.createParallelGroup()
						.addGroup(panel2Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(panel2Layout.createParallelGroup()
								.addComponent(label3)
								.addComponent(label4)
								.addComponent(label5)
								.addComponent(label6)
								.addComponent(label7)
								.addComponent(label8)
								.addComponent(label9)
								.addComponent(label10))
							.addGap(36, 36, 36)
							.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addGroup(panel2Layout.createSequentialGroup()
									.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
										.addComponent(name, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
										.addComponent(num, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
										.addComponent(id, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
										.addComponent(gender, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
										.addComponent(phone, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
										.addComponent(status, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(label17, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
								.addGroup(panel2Layout.createSequentialGroup()
									.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
										.addComponent(licenseY, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
										.addComponent(bornY, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
										.addGroup(panel2Layout.createSequentialGroup()
											.addComponent(label11)
											.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(bornM, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
										.addGroup(panel2Layout.createSequentialGroup()
											.addComponent(label14)
											.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(licenseM)))
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
										.addGroup(panel2Layout.createSequentialGroup()
											.addComponent(label12)
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(bornD, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
										.addGroup(panel2Layout.createSequentialGroup()
											.addComponent(label15)
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(licenseD)))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(panel2Layout.createParallelGroup()
										.addComponent(label13)
										.addComponent(label16))))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 408, Short.MAX_VALUE)
							.addGroup(panel2Layout.createParallelGroup()
								.addComponent(cancelButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
								.addComponent(modifyButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
								.addComponent(saveButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
								.addComponent(deleteButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())
				);
				panel2Layout.setVerticalGroup(
					panel2Layout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label3)
								.addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label4)
								.addComponent(num, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label5)
								.addComponent(id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label6)
								.addComponent(gender, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(panel2Layout.createParallelGroup()
								.addGroup(panel2Layout.createSequentialGroup()
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(label7)
										.addComponent(phone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(panel2Layout.createParallelGroup()
										.addComponent(label8)
										.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
											.addComponent(status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(label17)))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(panel2Layout.createParallelGroup()
										.addComponent(label9)
										.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
											.addComponent(bornY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(label11)
											.addComponent(bornM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(label12)
											.addComponent(bornD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(label13)))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(label10)
										.addComponent(licenseY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(label14)
										.addComponent(licenseM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(label15)
										.addComponent(licenseD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(label16)))
								.addGroup(panel2Layout.createSequentialGroup()
									.addGap(1, 1, 1)
									.addComponent(deleteButton)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(saveButton)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(modifyButton)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(cancelButton)))
							.addContainerGap(33, Short.MAX_VALUE))
				);
			}
			driverPane.addTab("\u53f8\u673a\u8be6\u7ec6\u4fe1\u606f", panel2);
		}

		//======== cancelDialog ========
		{
			cancelDialog.setTitle("\u63d0\u793a");
			Container cancelDialogContentPane = cancelDialog.getContentPane();
			cancelDialogContentPane.setLayout(new BorderLayout());

			//======== panel ========
			{

				//---- label1 ----
				label1.setText("\u53d6\u6d88\u540e\u672c\u6b21\u7f16\u8f91\u5c06\u4e0d\u80fd\u751f\u6548");

				//---- label2 ----
				label2.setText("\u662f\u5426\u786e\u8ba4\u53d6\u6d88\uff1f");

				//---- cancelSureButton ----
				cancelSureButton.setText("\u662f");
				cancelSureButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						cancelSureButtonMouseClicked(e);
					}
				});

				//---- notCancelButton ----
				notCancelButton.setText("\u5426");
				notCancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						notCancelButtonMouseReleased(e);
					}
				});

				GroupLayout panelLayout = new GroupLayout(panel);
				panel.setLayout(panelLayout);
				panelLayout.setHorizontalGroup(
					panelLayout.createParallelGroup()
						.addGroup(panelLayout.createSequentialGroup()
							.addGap(55, 55, 55)
							.addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addGroup(panelLayout.createSequentialGroup()
									.addGap(13, 13, 13)
									.addComponent(cancelSureButton)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(notCancelButton))
								.addComponent(label1))
							.addContainerGap(60, Short.MAX_VALUE))
						.addGroup(GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
							.addGap(0, 88, Short.MAX_VALUE)
							.addComponent(label2)
							.addGap(87, 87, 87))
				);
				panelLayout.setVerticalGroup(
					panelLayout.createParallelGroup()
						.addGroup(panelLayout.createSequentialGroup()
							.addGap(42, 42, 42)
							.addComponent(label1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label2)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(cancelSureButton)
								.addComponent(notCancelButton))
							.addContainerGap(43, Short.MAX_VALUE))
				);
			}
			cancelDialogContentPane.add(panel, BorderLayout.CENTER);
			cancelDialog.pack();
			cancelDialog.setLocationRelativeTo(cancelDialog.getOwner());
		}
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	private JTabbedPane driverListPane;
	private JPanel panel1;
	private JScrollPane scrollPane1;
	private JTable driverTable;
	private JButton addDriverButton;
	private JButton showDriverButton;
	private JTabbedPane driverPane;
	private JPanel panel2;
	private JButton saveButton;
	private JButton modifyButton;
	private JButton cancelButton;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	private JLabel label9;
	private JTextField name;
	private JTextField num;
	private JTextField id;
	private JTextField gender;
	private JTextField phone;
	private JTextField status;
	private JTextField bornY;
	private JTextField licenseY;
	private JLabel label10;
	private JLabel label11;
	private JTextField bornM;
	private JLabel label12;
	private JTextField bornD;
	private JLabel label13;
	private JLabel label14;
	private JTextField licenseM;
	private JLabel label15;
	private JTextField licenseD;
	private JLabel label16;
	private JButton deleteButton;
	private JLabel label17;
	private JDialog cancelDialog;
	private JPanel panel;
	private JLabel label1;
	private JLabel label2;
	private JButton cancelSureButton;
	private JButton notCancelButton;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
