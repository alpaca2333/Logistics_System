/*
 * Created by JFormDesigner on Wed Dec 02 23:23:16 CST 2015
 */

package presentation.transfer.hall;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import data.message.ResultMessage;
import data.vo.TruckInfoVO;
import data.vo.TruckListVO;
import businesslogic.service.Transfer.hall.EntruckReceiveService;
import businesslogic.service.Transfer.hall.TruckManagementService;

/**
 * @author sunxu
 */
public class TruckPanel extends JPanel {
	TruckManagementService truckManagement;
	TruckListVO truckList;
	TruckInfoVO truck;
	
	public TruckPanel(TruckManagementService truckManagementService) {
		this.truckManagement = truckManagementService;
		initComponents();
		showTruckButton.setEnabled(false);
		this.setVisible(true);
		setList();
	}
	
	private void setTruckVO(){
		if(truck != null){
			id.setEnabled(false);
			license.setEnabled(false);
			year.setEnabled(false);
			month.setEnabled(false);
			day.setEnabled(false);
			status.setEnabled(false);
			
			id.setText(truck.ID);
			license.setText(truck.license);
			year.setText(truck.year);
			month.setText(truck.month);
			day.setText(truck.day);
			status.setText(truck.engaged);
			modifyButton.setEnabled(true);
			saveButton.setEnabled(false);
			truckPane.validate();
			truckPane.updateUI();
			truckPane.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(null, "未能获取车辆信息", "异常", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void setList(){
		truckList = truckManagement.getTrucks();
		if(truckList != null){
			DefaultTableModel model = new DefaultTableModel(truckList.info,truckList.header);
			truckTable.setModel(model);
			truckTable.validate();
			truckTable.updateUI();
			truckTable.repaint();
			if(truckPane != null){
				remove(truckPane);
				add(truckListPane);
				truckListPane.validate();
				truckListPane.updateUI();
				truckListPane.setVisible(true);
			}
			
		}else{
			JOptionPane.showMessageDialog(null, "车辆信息为空","提示",JOptionPane.INFORMATION_MESSAGE);

		}
	}

	private void showTruckButtonMouseClicked(MouseEvent e) {
		if(showTruckButton.isEnabled()){
		saveButton.setVisible(false);
		modifyButton.setVisible(false);
		int row = truckTable.getSelectedRow();
		String id = (String) truckTable.getValueAt(row, truckList.getIDRow());
		long idNum = Long.parseLong(id);
		truck = truckManagement.chooseTruck(idNum);
		setTruckVO();
		remove(truckListPane);
		add(truckPane);
		
		truckPane.updateUI();
		truckPane.setVisible(true);
		}
	}



	private void truckTableMouseClicked(MouseEvent e) {
		showTruckButton.setEnabled(true);
	}

	private void modifyButtonMouseReleased(MouseEvent e) {
		license.setEnabled(true);
		year.setEnabled(true);
		month.setEnabled(true);
		day.setEnabled(true);
		saveButton.setEnabled(true);
	}
	
	private boolean checkAllInput(){
		boolean result = true;
		if(id.getText().equals("")){
			result = false;
		}else if(license.getText().equals("")){
			result = false;
		}else if(year.getText().equals("")){
			result = false;
		}else if(month.getText().equals("")){
			result = false;
		}else if(day.getText().equals("")){
			result = false;
		}else{
			;
		}
		
		return result;
	}

	private void saveButtonMouseReleased(MouseEvent e) {
		if(!checkAllInput()){
			JOptionPane.showMessageDialog(null, "输入不完整，请重新检查输入", "提示", JOptionPane.INFORMATION_MESSAGE);
			return ;
		}
		truck.ID = id.getText();
		truck.license = license.getText();
		truck.year = year.getText();
		truck.month = month.getText();
		truck.day = day.getText();
		truck.dutyDate = truck.year+"/"+truck.month+"/"+truck.day;
		ResultMessage result = ResultMessage.SUCCESS;
		if (modifyButton.isEnabled()) {
			result = truckManagement.modifyTruck(truck);
		}else{
			result = truckManagement.addTruck(truck);
		}
		if(result == ResultMessage.SUCCESS){
			setList();
			JOptionPane.showMessageDialog(null, "保存成功", "提示", JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(null, "保存未成功,请稍后再试", "异常", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cancelSureButtonMouseClicked(MouseEvent e) {
		cancelDialog.setVisible(false);
		remove(truckPane);
		add(truckListPane);
		
		truckListPane.updateUI();
		truckListPane.setVisible(true);
		
		id.setText("");
		license.setText("");
		year.setText("");
		month.setText("");
		day.setText("");
	}

	private void cancelButtonMouseReleased(MouseEvent e) {
		if(modifyButton.isVisible() || saveButton.isVisible()){
			cancelDialog.setVisible(true);
		}else{
			remove(truckPane);
			add(truckListPane);
			
			truckListPane.updateUI();
			truckListPane.setVisible(true);
		}
	}

	private void notCancelButtonMouseReleased(MouseEvent e) {
		cancelDialog.setVisible(false);
	}

	private void addTruckButtonMouseClicked(MouseEvent e) {
		truck = new TruckInfoVO();
		modifyButton.setVisible(false);
		modifyButton.setEnabled(false);
		saveButton.setVisible(true);
		saveButton.setEnabled(true);
		remove(truckListPane);
		add(truckPane);
		truckPane.validate();
		truckPane.updateUI();
		truckPane.setVisible(true);
	}
	
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		truckListPane = new JTabbedPane();
		panel1 = new JPanel();
		scrollPane1 = new JScrollPane();
		truckTable = new JTable();
		addTruckButton = new JButton();
		showTruckButton = new JButton();
		truckPane = new JTabbedPane();
		panel2 = new JPanel();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		year = new JTextField();
		label4 = new JLabel();
		month = new JTextField();
		label5 = new JLabel();
		day = new JTextField();
		label6 = new JLabel();
		license = new JTextField();
		id = new JTextField();
		label7 = new JLabel();
		status = new JTextField();
		saveButton = new JButton();
		modifyButton = new JButton();
		cancelButton = new JButton();
		cancelDialog = new JDialog();
		panel = new JPanel();
		label8 = new JLabel();
		label9 = new JLabel();
		cancelSureButton = new JButton();
		notCancelButton = new JButton();

		//======== this ========
		setLayout(new BorderLayout());

		//======== truckListPane ========
		{

			//======== panel1 ========
			{

				//======== scrollPane1 ========
				{

					//---- truckTable ----
					truckTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
					truckTable.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							truckTableMouseClicked(e);
						}
					});
					scrollPane1.setViewportView(truckTable);
				}

				//---- addTruckButton ----
				addTruckButton.setText("\u6dfb\u52a0");
				addTruckButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						addTruckButtonMouseClicked(e);
					}
				});

				//---- showTruckButton ----
				showTruckButton.setText("\u67e5\u770b");
				showTruckButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						showTruckButtonMouseClicked(e);
					}
				});

				GroupLayout panel1Layout = new GroupLayout(panel1);
				panel1.setLayout(panel1Layout);
				panel1Layout.setHorizontalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 693, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel1Layout.createParallelGroup()
								.addComponent(addTruckButton, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
								.addComponent(showTruckButton, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
							.addContainerGap())
				);
				panel1Layout.setVerticalGroup(
					panel1Layout.createParallelGroup()
						.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
						.addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
							.addContainerGap(284, Short.MAX_VALUE)
							.addComponent(showTruckButton)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(addTruckButton)
							.addContainerGap())
				);
			}
			truckListPane.addTab("\u8f66\u8f86\u5217\u8868", panel1);
		}
		add(truckListPane, BorderLayout.CENTER);

		//======== truckPane ========
		{

			//======== panel2 ========
			{

				//---- label1 ----
				label1.setText("\u8f66\u8f86\u7f16\u53f7");

				//---- label2 ----
				label2.setText("\u8f66\u724c\u53f7");

				//---- label3 ----
				label3.setText("\u670d\u5f79\u65f6\u95f4");

				//---- label4 ----
				label4.setText("\u5e74");

				//---- label5 ----
				label5.setText("\u6708");

				//---- label6 ----
				label6.setText("\u65e5");

				//---- id ----
				id.setEditable(false);
				id.setText("\u4fdd\u5b58\u65f6\u751f\u6210");

				//---- label7 ----
				label7.setText("\u5de5\u4f5c\u72b6\u6001");

				//---- status ----
				status.setEditable(false);
				status.setText("\u7a7a\u95f2");

				//---- saveButton ----
				saveButton.setText("\u4fdd\u5b58");
				saveButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						saveButtonMouseReleased(e);
					}
				});

				//---- modifyButton ----
				modifyButton.setText("\u4fee\u6539");
				modifyButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						modifyButtonMouseReleased(e);
					}
				});

				//---- cancelButton ----
				cancelButton.setText("\u53d6\u6d88");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						cancelButtonMouseReleased(e);
					}
				});

				GroupLayout panel2Layout = new GroupLayout(panel2);
				panel2.setLayout(panel2Layout);
				panel2Layout.setHorizontalGroup(
					panel2Layout.createParallelGroup()
						.addGroup(panel2Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(panel2Layout.createParallelGroup()
								.addGroup(panel2Layout.createSequentialGroup()
									.addGroup(panel2Layout.createParallelGroup()
										.addGroup(panel2Layout.createSequentialGroup()
											.addGroup(panel2Layout.createParallelGroup()
												.addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
													.addComponent(label1)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(id, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
												.addGroup(panel2Layout.createSequentialGroup()
													.addComponent(label2)
													.addGap(18, 18, 18)
													.addComponent(license, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
												.addGroup(panel2Layout.createSequentialGroup()
													.addComponent(label3)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(year, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(label4)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(month, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(label5)
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(day, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(label6))
										.addGroup(panel2Layout.createSequentialGroup()
											.addComponent(label7)
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(status, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)))
									.addGap(0, 547, Short.MAX_VALUE))
								.addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
									.addGap(0, 718, Short.MAX_VALUE)
									.addGroup(panel2Layout.createParallelGroup()
										.addComponent(cancelButton, GroupLayout.Alignment.TRAILING)
										.addComponent(modifyButton, GroupLayout.Alignment.TRAILING)
										.addComponent(saveButton, GroupLayout.Alignment.TRAILING))))
							.addContainerGap())
				);
				panel2Layout.setVerticalGroup(
					panel2Layout.createParallelGroup()
						.addGroup(panel2Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label1)
								.addComponent(id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label2)
								.addComponent(license, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label3)
								.addComponent(label4)
								.addComponent(month, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label5)
								.addComponent(day, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label6)
								.addComponent(year, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label7)
								.addComponent(status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
							.addComponent(saveButton)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(modifyButton)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(cancelButton)
							.addGap(13, 13, 13))
				);
			}
			truckPane.addTab("\u8f66\u8f86\u8be6\u7ec6\u4fe1\u606f", panel2);
		}

		//======== cancelDialog ========
		{
			cancelDialog.setTitle("\u63d0\u793a");
			Container cancelDialogContentPane = cancelDialog.getContentPane();
			cancelDialogContentPane.setLayout(new BorderLayout());

			//======== panel ========
			{

				//---- label8 ----
				label8.setText("\u53d6\u6d88\u540e\u672c\u6b21\u7f16\u8f91\u5c06\u4e0d\u80fd\u751f\u6548");

				//---- label9 ----
				label9.setText("\u662f\u5426\u786e\u8ba4\u53d6\u6d88\uff1f");

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
						.addGroup(GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
							.addGap(0, 88, Short.MAX_VALUE)
							.addComponent(label9)
							.addGap(87, 87, 87))
						.addGroup(panelLayout.createSequentialGroup()
							.addGroup(panelLayout.createParallelGroup()
								.addGroup(panelLayout.createSequentialGroup()
									.addGap(55, 55, 55)
									.addComponent(label8))
								.addGroup(panelLayout.createSequentialGroup()
									.addGap(70, 70, 70)
									.addComponent(cancelSureButton)
									.addGap(18, 18, 18)
									.addComponent(notCancelButton)))
							.addContainerGap(60, Short.MAX_VALUE))
				);
				panelLayout.setVerticalGroup(
					panelLayout.createParallelGroup()
						.addGroup(panelLayout.createSequentialGroup()
							.addGap(42, 42, 42)
							.addComponent(label8, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label9)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(cancelSureButton)
								.addComponent(notCancelButton))
							.addContainerGap(39, Short.MAX_VALUE))
				);
			}
			cancelDialogContentPane.add(panel, BorderLayout.CENTER);
			cancelDialog.pack();
			cancelDialog.setLocationRelativeTo(cancelDialog.getOwner());
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JTabbedPane truckListPane;
	private JPanel panel1;
	private JScrollPane scrollPane1;
	private JTable truckTable;
	private JButton addTruckButton;
	private JButton showTruckButton;
	private JTabbedPane truckPane;
	private JPanel panel2;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JTextField year;
	private JLabel label4;
	private JTextField month;
	private JLabel label5;
	private JTextField day;
	private JLabel label6;
	private JTextField license;
	private JTextField id;
	private JLabel label7;
	private JTextField status;
	private JButton saveButton;
	private JButton modifyButton;
	private JButton cancelButton;
	private JDialog cancelDialog;
	private JPanel panel;
	private JLabel label8;
	private JLabel label9;
	private JButton cancelSureButton;
	private JButton notCancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
