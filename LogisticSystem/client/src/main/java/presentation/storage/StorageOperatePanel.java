/*
 * Created by JFormDesigner on Thu Dec 03 15:28:21 CST 2015
 */

package presentation.storage;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;

import javax.swing.*;

import data.message.ResultMessage;
import data.vo.StorageListVO;
import businesslogic.service.storage.StorageOperateService;

/**
 * @author sunxu
 */
public class StorageOperatePanel extends JPanel {
	StorageOperateService storageOperate;
	StorageListVO storageList;
	public StorageOperatePanel(StorageOperateService storageOperate) {
		this.storageOperate = storageOperate;
		initComponents();
		selectStorageListButton.setEnabled(false);
	}

	private void clearInitInput(){
		numInput.setText("");
		shelfInput.setText("");
		planeInput.setText("");
		trainInput.setText("");
		truckInput.setText("");
		flexibleInput.setText("");
		alarmPercentInput.setText("");
		this.updateUI();
		this.repaint();
	}
	
	private boolean setInitInput(){
		int num = 0 , shelf = 0 , planeRow = 0 , trainRow = 0 , truckRow = 0 , flexibleRow = 0;
		double alarmPercent = 0.0;
	try {
		num = Integer.parseInt(numInput.getText());
		shelf = Integer.parseInt(shelfInput.getText());
		planeRow = Integer.parseInt(planeInput.getText());
		trainRow = Integer.parseInt(trainInput.getText());
		truckRow = Integer.parseInt(truckInput.getText());
		flexibleRow = Integer.parseInt(flexibleInput.getText());
		alarmPercent = Double.parseDouble(alarmPercentInput.getText());
	} catch (NumberFormatException e) {
		return false;
	}
		ResultMessage result = storageOperate.inputStorageInitInfo(num, shelf, planeRow, trainRow, truckRow, flexibleRow, alarmPercent);
		if(result == result.SUCCESS) return true;
		else return false;
	}
	
//==============================监听===================================
	private void storageInMouseClicked(MouseEvent e) {
		storageIn.setSelected(true);
		storageOut.setSelected(false);
	}

	private void storageOutMouseClicked(MouseEvent e) {
		storageIn.setSelected(false);
		storageOut.setSelected(true);
	}

	private void InitSureButtonMouseClicked(MouseEvent e) {
		boolean r = setInitInput();
		if(r){
			JOptionPane.showMessageDialog(null, "初始化成功", "提示信息", JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(null, "初始化失败","提示信息", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void reInputButtonMouseClicked(MouseEvent e) {
		clearInitInput();
	}


	private void listTableMouseClicked(MouseEvent e) {
		selectStorageListButton.setEnabled(true);
	}

	private void searchListButtonMouseReleased(MouseEvent e) {
		int sy = -1;
		int sm = -1;
		int sd = -1;
		int ey = -1;
		int em = -1;
		int ed = -1;
		try {
			sy = Integer.parseInt(startY.getText());
			sm = Integer.parseInt(startM.getText());
			sd = Integer.parseInt(startD.getText());
			ey = Integer.parseInt(endY.getText());
			em = Integer.parseInt(endM.getText());
			ed = Integer.parseInt(endD.getText());
		} catch (NumberFormatException e2) {
			JOptionPane.showMessageDialog(null, "输入格式有误", "提示", JOptionPane.INFORMATION_MESSAGE);
			return ;
		}
		if(isTimeValid(sy, sm, sd, ey, em, ed)){
			int[] start = {sy,sm,sd};
			int[] end = {ey,em,ed};
			try {
				storageList = storageOperate.inputTime(start, end);
				
			} catch (RemoteException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "网络连接中断", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
			if (storageList != null) {
				setStorageList();
			}else {
				JOptionPane.showMessageDialog(null, "未能获取单据", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	private void setStorageList(){
		//设置入库单，出库单列表
	}
	//未完成
	private boolean isTimeValid(int sy,int sm,int sd,int ey,int em,int ed){
		boolean st = (sy>2000&&sm>0&&sm<13&&sd>0&&sd<32);
		boolean et = (ey>2000&&em>0&&em<13&&ed>0&&ed<32);
		if (st!= true||et!= true) {
			return false;
		}else{
			if(sy>ey){
				return false;
			}
		}
		return true;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		operatePane = new JTabbedPane();
		showPanel = new JPanel();
		scrollPane1 = new JScrollPane();
		listTable = new JTable();
		label9 = new JLabel();
		label10 = new JLabel();
		startY = new JTextField();
		label11 = new JLabel();
		label12 = new JLabel();
		startM = new JTextField();
		label13 = new JLabel();
		startD = new JTextField();
		label14 = new JLabel();
		endY = new JTextField();
		label15 = new JLabel();
		endM = new JTextField();
		label16 = new JLabel();
		endD = new JTextField();
		searchListButton = new JButton();
		selectStorageListButton = new JButton();
		storageIn = new JRadioButton();
		storageOut = new JRadioButton();
		checkPanel = new JPanel();
		scrollPane2 = new JScrollPane();
		table2 = new JTable();
		button5 = new JButton();
		button6 = new JButton();
		adjustPanel = new JPanel();
		label17 = new JLabel();
		label18 = new JLabel();
		label19 = new JLabel();
		planePercent = new JTextField();
		trainPercent = new JTextField();
		truckPercent = new JTextField();
		button7 = new JButton();
		button8 = new JButton();
		button9 = new JButton();
		initPanel = new JPanel();
		label1 = new JLabel();
		textField1 = new JTextField();
		label2 = new JLabel();
		numInput = new JTextField();
		label3 = new JLabel();
		label4 = new JLabel();
		shelfInput = new JTextField();
		label5 = new JLabel();
		label6 = new JLabel();
		label7 = new JLabel();
		label8 = new JLabel();
		planeInput = new JTextField();
		trainInput = new JTextField();
		truckInput = new JTextField();
		flexibleInput = new JTextField();
		InitSureButton = new JButton();
		reInputButton = new JButton();
		label20 = new JLabel();
		alarmPercentInput = new JTextField();

		//======== this ========
		setLayout(new BorderLayout());

		//======== operatePane ========
		{

			//======== showPanel ========
			{

				//======== scrollPane1 ========
				{

					//---- listTable ----
					listTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
					listTable.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							listTableMouseClicked(e);
						}
					});
					scrollPane1.setViewportView(listTable);
				}

				//---- label9 ----
				label9.setText("\u8d77\u59cb\u65e5\u671f");

				//---- label10 ----
				label10.setText("\u7ec8\u6b62\u65e5\u671f");

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

				//---- searchListButton ----
				searchListButton.setText("\u67e5\u627e");
				searchListButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						searchListButtonMouseReleased(e);
					}
				});

				//---- selectStorageListButton ----
				selectStorageListButton.setText("\u67e5\u770b");

				//---- storageIn ----
				storageIn.setText("\u5165\u5e93\u5355");
				storageIn.setSelected(true);
				storageIn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						storageInMouseClicked(e);
					}
				});

				//---- storageOut ----
				storageOut.setText("\u51fa\u5e93\u5355");
				storageOut.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						storageOutMouseClicked(e);
					}
				});

				GroupLayout showPanelLayout = new GroupLayout(showPanel);
				showPanel.setLayout(showPanelLayout);
				showPanelLayout.setHorizontalGroup(
					showPanelLayout.createParallelGroup()
						.addComponent(scrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
						.addGroup(showPanelLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(showPanelLayout.createParallelGroup()
								.addComponent(label9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(label10, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(showPanelLayout.createParallelGroup()
								.addComponent(startY, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
								.addComponent(endY, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(showPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(label14, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
								.addComponent(label11, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(showPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addGroup(showPanelLayout.createSequentialGroup()
									.addComponent(startM, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(label12, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
								.addGroup(showPanelLayout.createSequentialGroup()
									.addComponent(endM, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(label15, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
							.addGroup(showPanelLayout.createParallelGroup()
								.addGroup(showPanelLayout.createSequentialGroup()
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(startD, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
								.addGroup(showPanelLayout.createSequentialGroup()
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(endD, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(showPanelLayout.createParallelGroup()
								.addComponent(label13)
								.addComponent(label16))
							.addGap(155, 155, 155)
							.addGroup(showPanelLayout.createParallelGroup()
								.addGroup(showPanelLayout.createSequentialGroup()
									.addComponent(storageOut)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
									.addComponent(searchListButton)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(selectStorageListButton))
								.addGroup(showPanelLayout.createSequentialGroup()
									.addComponent(storageIn)
									.addGap(0, 235, Short.MAX_VALUE)))
							.addContainerGap())
				);
				showPanelLayout.setVerticalGroup(
					showPanelLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, showPanelLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(showPanelLayout.createParallelGroup()
								.addGroup(showPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(label11, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(startM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(label12, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addComponent(startD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(label13, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(showPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(storageIn)
									.addComponent(startY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(label9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(showPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(selectStorageListButton)
								.addComponent(searchListButton)
								.addComponent(storageOut)
								.addComponent(label10, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(endY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label14)
								.addComponent(endM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label15)
								.addComponent(endD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label16))
							.addGap(31, 31, 31)
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 266, GroupLayout.PREFERRED_SIZE))
				);
			}
			operatePane.addTab("\u5e93\u5b58\u67e5\u770b", showPanel);

			//======== checkPanel ========
			{

				//======== scrollPane2 ========
				{
					scrollPane2.setViewportView(table2);
				}

				//---- button5 ----
				button5.setText("\u4fdd\u5b58");

				//---- button6 ----
				button6.setText("\u5bfc\u51fa");

				GroupLayout checkPanelLayout = new GroupLayout(checkPanel);
				checkPanel.setLayout(checkPanelLayout);
				checkPanelLayout.setHorizontalGroup(
					checkPanelLayout.createParallelGroup()
						.addComponent(scrollPane2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
						.addGroup(checkPanelLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(button5, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(button6, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(604, Short.MAX_VALUE))
				);
				checkPanelLayout.setVerticalGroup(
					checkPanelLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, checkPanelLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(checkPanelLayout.createParallelGroup()
								.addComponent(button5, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
								.addComponent(button6, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
							.addGap(18, 18, 18)
							.addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE))
				);
			}
			operatePane.addTab("\u5e93\u5b58\u76d8\u70b9", checkPanel);

			//======== adjustPanel ========
			{

				//---- label17 ----
				label17.setText("\u822a\u8fd0\u533a\uff1a");

				//---- label18 ----
				label18.setText("\u94c1\u8fd0\u533a\uff1a");

				//---- label19 ----
				label19.setText("\u6c7d\u8fd0\u533a\uff1a");

				//---- button7 ----
				button7.setText("\u6269\u5145");

				//---- button8 ----
				button8.setText("\u6269\u5145");

				//---- button9 ----
				button9.setText("\u6269\u5145");

				GroupLayout adjustPanelLayout = new GroupLayout(adjustPanel);
				adjustPanel.setLayout(adjustPanelLayout);
				adjustPanelLayout.setHorizontalGroup(
					adjustPanelLayout.createParallelGroup()
						.addGroup(adjustPanelLayout.createSequentialGroup()
							.addGap(235, 235, 235)
							.addGroup(adjustPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(label17, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
								.addComponent(label18, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
								.addComponent(label19, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(adjustPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(planePercent, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
								.addComponent(trainPercent, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
								.addComponent(truckPercent, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(adjustPanelLayout.createParallelGroup()
								.addComponent(button7)
								.addComponent(button8)
								.addComponent(button9))
							.addContainerGap(324, Short.MAX_VALUE))
				);
				adjustPanelLayout.setVerticalGroup(
					adjustPanelLayout.createParallelGroup()
						.addGroup(adjustPanelLayout.createSequentialGroup()
							.addGap(63, 63, 63)
							.addGroup(adjustPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label17, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
								.addComponent(planePercent, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(button7))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(adjustPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label18, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(trainPercent, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(button8))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(adjustPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label19, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
								.addComponent(truckPercent, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(button9))
							.addContainerGap(203, Short.MAX_VALUE))
				);
			}
			operatePane.addTab("\u5e93\u5b58\u8c03\u6574", adjustPanel);

			//======== initPanel ========
			{

				//---- label1 ----
				label1.setText("\u533a\u57df\u6570\u91cf");

				//---- textField1 ----
				textField1.setText("4");
				textField1.setEditable(false);

				//---- label2 ----
				label2.setText("\u8d27\u67b6\u89c4\u683c\uff08\u4f4d\uff09");

				//---- label4 ----
				label4.setText("\u6bcf\u6392\u67b6\u6570\uff08\u67b6\uff09");

				//---- label5 ----
				label5.setText("\u822a\u8fd0\u533a");

				//---- label6 ----
				label6.setText("\u94c1\u8fd0\u533a");

				//---- label7 ----
				label7.setText("\u6c7d\u8fd0\u533a");

				//---- label8 ----
				label8.setText("\u673a\u52a8\u533a");

				//---- InitSureButton ----
				InitSureButton.setText("\u786e\u8ba4");
				InitSureButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						InitSureButtonMouseClicked(e);
					}
				});

				//---- reInputButton ----
				reInputButton.setText("\u91cd\u65b0\u8f93\u5165");
				reInputButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						reInputButtonMouseClicked(e);
					}
				});

				//---- label20 ----
				label20.setText("\u62a5\u8b66\u6bd4\u4f8b");

				GroupLayout initPanelLayout = new GroupLayout(initPanel);
				initPanel.setLayout(initPanelLayout);
				initPanelLayout.setHorizontalGroup(
					initPanelLayout.createParallelGroup()
						.addGroup(initPanelLayout.createSequentialGroup()
							.addGap(231, 231, 231)
							.addGroup(initPanelLayout.createParallelGroup()
								.addGroup(initPanelLayout.createSequentialGroup()
									.addComponent(label20)
									.addGap(0, 0, Short.MAX_VALUE))
								.addGroup(initPanelLayout.createSequentialGroup()
									.addGroup(initPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
										.addComponent(label8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(label7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(label6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(label5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(initPanelLayout.createSequentialGroup()
											.addGap(30, 30, 30)
											.addComponent(label3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addComponent(label1, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
										.addComponent(label2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(label4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(InitSureButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addGroup(initPanelLayout.createParallelGroup()
										.addGroup(initPanelLayout.createSequentialGroup()
											.addGroup(initPanelLayout.createParallelGroup()
												.addGroup(initPanelLayout.createSequentialGroup()
													.addGap(11, 11, 11)
													.addGroup(initPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
														.addComponent(textField1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
														.addComponent(numInput, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
													.addGap(0, 0, Short.MAX_VALUE))
												.addGroup(initPanelLayout.createSequentialGroup()
													.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
													.addGroup(initPanelLayout.createParallelGroup()
														.addComponent(shelfInput)
														.addComponent(planeInput)
														.addComponent(trainInput)
														.addComponent(truckInput)
														.addComponent(flexibleInput)
														.addComponent(alarmPercentInput))))
											.addGap(399, 399, 399))
										.addGroup(initPanelLayout.createSequentialGroup()
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(reInputButton, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
											.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
				);
				initPanelLayout.setVerticalGroup(
					initPanelLayout.createParallelGroup()
						.addGroup(initPanelLayout.createSequentialGroup()
							.addGap(16, 16, 16)
							.addGroup(initPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label1)
								.addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(initPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(numInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label2))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(initPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label4)
								.addComponent(shelfInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label3)
							.addGap(37, 37, 37)
							.addGroup(initPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label5)
								.addComponent(planeInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(initPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label6)
								.addComponent(trainInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(initPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label7)
								.addComponent(truckInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(initPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label8)
								.addComponent(flexibleInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18, 18, 18)
							.addGroup(initPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label20)
								.addComponent(alarmPercentInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
							.addGroup(initPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(InitSureButton)
								.addComponent(reInputButton))
							.addGap(20, 20, 20))
				);
			}
			operatePane.addTab("\u521d\u59cb\u5316", initPanel);
		}
		add(operatePane, BorderLayout.NORTH);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JTabbedPane operatePane;
	private JPanel showPanel;
	private JScrollPane scrollPane1;
	private JTable listTable;
	private JLabel label9;
	private JLabel label10;
	private JTextField startY;
	private JLabel label11;
	private JLabel label12;
	private JTextField startM;
	private JLabel label13;
	private JTextField startD;
	private JLabel label14;
	private JTextField endY;
	private JLabel label15;
	private JTextField endM;
	private JLabel label16;
	private JTextField endD;
	private JButton searchListButton;
	private JButton selectStorageListButton;
	private JRadioButton storageIn;
	private JRadioButton storageOut;
	private JPanel checkPanel;
	private JScrollPane scrollPane2;
	private JTable table2;
	private JButton button5;
	private JButton button6;
	private JPanel adjustPanel;
	private JLabel label17;
	private JLabel label18;
	private JLabel label19;
	private JTextField planePercent;
	private JTextField trainPercent;
	private JTextField truckPercent;
	private JButton button7;
	private JButton button8;
	private JButton button9;
	private JPanel initPanel;
	private JLabel label1;
	private JTextField textField1;
	private JLabel label2;
	private JTextField numInput;
	private JLabel label3;
	private JLabel label4;
	private JTextField shelfInput;
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	private JTextField planeInput;
	private JTextField trainInput;
	private JTextField truckInput;
	private JTextField flexibleInput;
	private JButton InitSureButton;
	private JButton reInputButton;
	private JLabel label20;
	private JTextField alarmPercentInput;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
