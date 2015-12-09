/*
 * Created by JFormDesigner on Mon Nov 30 23:08:01 CST 2015
 */

package presentation.transfer.hall;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import data.message.ResultMessage;
import data.vo.ArrivalListVO;
import data.vo.ArrivalVO;
import data.vo.DeliveryListVO;
import data.vo.EntruckListVO;
import data.vo.TransferListVO;
import businesslogic.service.Transfer.hall.EntruckReceiveService;

/**
 * @author sunxu
 */
public class EntruckReceivePanel extends JPanel {
	EntruckReceiveService entruckReceive;
	ArrivalVO arrival;
	TransferListVO transferList;
	EntruckListVO entruckList;
	ArrivalListVO arrivalList;
	
	public EntruckReceivePanel(EntruckReceiveService entruckReceive) {
		this.entruckReceive = entruckReceive;
		initComponents();
		searchList.setEnabled(false);
		selectArrival.setEnabled(false);
		getArrivalList();//只能调用一次
		setArrivalList();
		this.validate();
		this.updateUI();
		this.setVisible(true);
	}
	
	private void setArrivalList(){
		arrivalTable.validate();
		arrivalTable.updateUI();
		arrivalTable.repaint();
		if(arrivalVO != null)
			remove(arrivalVO);
		if(entruckVO != null)
			remove(entruckVO);
		add(startPane,BorderLayout.CENTER);
		startPane.updateUI();
		startPane.setVisible(true);
	}

	//设置到达单列表
	private void getArrivalList() {
		arrivalList = null;
		try {
			arrivalList = entruckReceive.getCheckedArrivals();
			if(arrivalList == null){
				return;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			errorDialog.validate();
			errorDialog.repaint();
			errorDialog.setVisible(true);
		}
		DefaultTableModel arrivalListModel = new DefaultTableModel(
				arrivalList.info, arrivalList.header);
		arrivalTable.setModel(arrivalListModel);
		selectArrival.setEnabled(false);
	}

	//设置到达单
	private void setArrivalVO() {
		modifyStatus.setEnabled(false);
		DefaultTableModel model = new DefaultTableModel(
				arrival.getOrderAndStatus(), arrival.getHeader());
		arrivalVOTable.setModel(model);
		arrivalVOTable.validate();
		arrivalVOTable.updateUI();
		DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<String>(arrival.statusList);
		status.setModel(model1);
		status.updateUI();
		status.setEnabled(false);
		transferID.setText(arrival.getDeliveryListNum());
		from.setText(arrival.getFromName());
		arrivalDate.setText(arrival.getDate());
		remove(startPane);
		add(arrivalVO, BorderLayout.CENTER);
		arrivalVO.validate();
		arrivalVO.updateUI();
		arrivalVO.setVisible(true);
	}

	//设置运输单列表
	private void getDeliveryList(long id) {

		try {
			if (transfer.isSelected()) {
				transferList = entruckReceive.searchTransferList(id);
				if(transferList == null){
					deliveryID.setText("单号不存在");
					return;
				}
				setTransferList();
			
			} else {
				entruckList = entruckReceive.searchEntruckList(id);
				if(entruckList == null){
					deliveryID.setText("单号不存在");
					return;
				}
				setEntruckList();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			errorDialog.setVisible(true);
		}
		

		remove(startPane);
		if (arrivalVO != null)
			remove(arrivalVO);
		add(entruckVO, BorderLayout.CENTER);

		entruckVO.validate();
		entruckVO.updateUI();
		entruckVO.setVisible(true);

	}

	//设置装车单
	private void setEntruckList() {
		DefaultTableModel model = new DefaultTableModel(entruckList.info,
				entruckList.header);
		listType.setText("装车单");
		listID.setText(entruckList.entruckListID);
		date.setText(entruckList.loadingDate);
		fromName.setText(entruckList.fromName);
		destName.setText(entruckList.destName);
		vehicleID.setText(entruckList.vehicleID);
		staff.setText(entruckList.monitorName);
		driverName.setText(entruckList.driverName);
		fee.setText(entruckList.fee);
		transferType.setText("汽运");
		deliveryTable.setModel(model);
		deliveryTable.validate();
		deliveryTable.updateUI();
		deliveryTable.repaint();
	}

	//设置中转单
	private void setTransferList() {
		DefaultTableModel model = new DefaultTableModel(
				transferList.orderAndPosition, transferList.header);
		deliveryTable.setModel(model);
		listType.setText("中转单");
		listID.setText(transferList.transferListID);
		fromName.setText(transferList.transferCenter);
		destName.setText(transferList.targetName);
		date.setText(transferList.date);
		vehicleID.setText(transferList.vehicleCode);
		staff.setText(transferList.staff);
		driverName.setText("");
		fee.setText(transferList.fee);
		transferType.setText(transferList.transferType);
		deliveryTable.validate();
		deliveryTable.updateUI();
		deliveryTable.repaint();
	}

	//===============================监听===============================
	private void cancelArrivalMouseClicked(MouseEvent e) {
		cancelDialog.setVisible(true);

	}

	private void selectArrivalMouseClicked(MouseEvent e) {
		if(arrivalList != null){
		int row = arrivalTable.getSelectedRow();
		String id = (String) arrivalTable.getValueAt(row, 0);
		long ID = Long.parseLong(id);
		arrival = entruckReceive.chooseArrival(ID);
		doArrive.setEnabled(true);
		doArrive.setVisible(true);
		saveArrival.setEnabled(false);
		saveArrival.setVisible(false);
		setArrivalVO();
		}else {
			return ;
		}
	}

	private void searchListMouseClicked(MouseEvent e){
		String id = deliveryID.getText();
		long num = -1;
		try {
		num = Long.parseLong(id);
		} catch (NumberFormatException e2) {
			deliveryID.setText("单号格式错误");
			return;
		}
		getDeliveryList(num);
	}
	
	private void deleteRow(int row){
		String[][] info = arrivalList.info;
		String[][] newInfo = new String[info.length-1][ArrivalListVO.getColumnNum()];
		for(int i = 0 ,j = 0; i < info.length;i++,j++){
			if(i == row){
				continue;
			}else{
				newInfo[j] = info[i];
			}
		}
		info = newInfo;
	}
	
	private void doArriveMouseClicked(MouseEvent e) {
		ResultMessage result = entruckReceive.doArrive();
		if (result == ResultMessage.FAILED) {
			JOptionPane.showMessageDialog(null, "操作失败，请稍后再试", "提示", JOptionPane.ERROR_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(null, "操作成功", "提示", JOptionPane.INFORMATION_MESSAGE);
			
			int row = arrivalTable.getSelectedRow();
			deleteRow(row);
			setArrivalList();
		}
	}

	private void cancelLoadMouseClicked(MouseEvent e) {
		if (arrivalVO != null)
			remove(arrivalVO);
		remove(entruckVO);
		add(startPane, BorderLayout.CENTER);

		startPane.validate();
		startPane.updateUI();
		startPane.setVisible(true);
	}

	private void button1MouseClicked(MouseEvent e) {
		// TODO add your code here
	}

	private void resultSureButtonMouseClicked(MouseEvent e) {
		resultDialog.setVisible(false);
		
		remove(arrivalVO);
		remove(entruckVO);
		add(startPane);
		
		startPane.validate();
		startPane.updateUI();
		startPane.setVisible(true);
	}

	private void errorSureMouseClicked(MouseEvent e) {
		errorDialog.setVisible(false);
	}

	private void entruckMouseClicked(MouseEvent e) {
		transfer.setSelected(false);
		entruck.setSelected(true);
	}

	private void transferMouseClicked(MouseEvent e) {
		entruck.setSelected(false);
		transfer.setSelected(true);
	}

	private void createArrivalMouseClicked(MouseEvent e) {
		if (transfer.isSelected())
			arrival = entruckReceive.createArrival(transferList);
		else
			arrival = entruckReceive.createArrival(entruckList);
		
		doArrive.setVisible(false);
		doArrive.setEnabled(false);
		saveArrival.setVisible(true);
		saveArrival.setEnabled(true);
		setArrivalVO();
		
	}

	private void saveArrivalMouseClicked(MouseEvent e) {
		ResultMessage result = entruckReceive.saveArrival(arrival);
		if(result == ResultMessage.SUCCESS){
			label6.setText("操作成功");
			resultDialog.repaint();
			resultDialog.setVisible(true);
		}
	}

	private void arrivalTableMouseClicked(MouseEvent e) {
		selectArrival.setEnabled(true);
	}



	private void cancelSureButtonMouseClicked(MouseEvent e) {
		cancelDialog.setVisible(false);
		remove(arrivalVO);
		if (entruckVO != null)
			remove(entruckVO);
		add(startPane, BorderLayout.CENTER);

		startPane.validate();
		startPane.updateUI();
		startPane.setVisible(true);
		
	}

	private void notCancelButtonMouseReleased(MouseEvent e) {
		cancelDialog.setVisible(false);
	}

	private void deliveryIDMouseClicked(MouseEvent e) {
		deliveryID.setText("");
		searchList.setEnabled(true);
	}

	private void modifyStatusMouseReleased(MouseEvent e) {//修改订单状态
		if (modifyStatus.isEnabled()) {
			int index = status.getSelectedIndex();
			if(index != -1){
			String s = (String) status.getItemAt(index);
			int[] rows = arrivalVOTable.getSelectedRows();
			if (rows == null) {
				return;
			}
			String[][] info = arrival.getOrderAndStatus();
			for(int i = 0;i < rows.length;i++){
				info[rows[i]][1] = s;
			}
			
			arrivalVOTable.updateUI();
			arrivalVOTable.repaint();
			}
		}
	}

	private void arrivalVOTableMouseClicked(MouseEvent e) {
		status.setEnabled(true);
		modifyStatus.setEnabled(true);
	}
//==============================监听=================================

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		startPane = new JTabbedPane();
		arrivalListPanel = new JPanel();
		scrollPane2 = new JScrollPane();
		arrivalTable = new JTable();
		selectArrival = new JButton();
		searchListPanel = new JPanel();
		label5 = new JLabel();
		deliveryID = new JTextField();
		entruck = new JRadioButton();
		transfer = new JRadioButton();
		searchList = new JButton();
		arrivalVO = new JTabbedPane();
		panel1 = new JPanel();
		scrollPane1 = new JScrollPane();
		arrivalVOTable = new JTable();
		label1 = new JLabel();
		transferID = new JTextField();
		label2 = new JLabel();
		from = new JTextField();
		label3 = new JLabel();
		arrivalDate = new JTextField();
		status = new JComboBox();
		modifyStatus = new JButton();
		cancelArrival = new JButton();
		doArrive = new JButton();
		label4 = new JLabel();
		saveArrival = new JButton();
		label15 = new JLabel();
		entruckVO = new JTabbedPane();
		DeliveryListPanel = new JPanel();
		scrollPane3 = new JScrollPane();
		deliveryTable = new JTable();
		cancelLoad = new JButton();
		createArrival = new JButton();
		label10 = new JLabel();
		listType = new JTextField();
		label11 = new JLabel();
		listID = new JTextField();
		label12 = new JLabel();
		fromName = new JTextField();
		label13 = new JLabel();
		destName = new JTextField();
		label14 = new JLabel();
		date = new JTextField();
		vehicleLabel = new JLabel();
		vehicleID = new JTextField();
		staffLabel = new JLabel();
		driverLabel = new JLabel();
		staff = new JTextField();
		driverName = new JTextField();
		label18 = new JLabel();
		fee = new JTextField();
		label19 = new JLabel();
		transferTypeLabel = new JLabel();
		transferType = new JTextField();
		resultDialog = new JDialog();
		label6 = new JLabel();
		resultSureButton = new JButton();
		errorDialog = new JDialog();
		label7 = new JLabel();
		errorSure = new JButton();
		cancelDialog = new JDialog();
		panel = new JPanel();
		label8 = new JLabel();
		label9 = new JLabel();
		cancelSureButton = new JButton();
		notCancelButton = new JButton();

		//======== this ========
		setLayout(new BorderLayout());

		//======== startPane ========
		{

			//======== arrivalListPanel ========
			{

				//======== scrollPane2 ========
				{

					//---- arrivalTable ----
					arrivalTable.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							arrivalTableMouseClicked(e);
						}
					});
					scrollPane2.setViewportView(arrivalTable);
				}

				//---- selectArrival ----
				selectArrival.setText("\u67e5\u770b");
				selectArrival.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						selectArrivalMouseClicked(e);
					}
				});

				GroupLayout arrivalListPanelLayout = new GroupLayout(arrivalListPanel);
				arrivalListPanel.setLayout(arrivalListPanelLayout);
				arrivalListPanelLayout.setHorizontalGroup(
					arrivalListPanelLayout.createParallelGroup()
						.addGroup(arrivalListPanelLayout.createSequentialGroup()
							.addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 686, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(selectArrival, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
							.addContainerGap())
				);
				arrivalListPanelLayout.setVerticalGroup(
					arrivalListPanelLayout.createParallelGroup()
						.addComponent(scrollPane2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
						.addGroup(GroupLayout.Alignment.TRAILING, arrivalListPanelLayout.createSequentialGroup()
							.addContainerGap(304, Short.MAX_VALUE)
							.addComponent(selectArrival, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
				);
			}
			startPane.addTab("\u5230\u8fbe\u5355\u5217\u8868", arrivalListPanel);

			//======== searchListPanel ========
			{

				//---- label5 ----
				label5.setText("\u8bf7\u8f93\u5165\u8fd0\u8f93\u5355\u53f7");

				//---- deliveryID ----
				deliveryID.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						deliveryIDMouseClicked(e);
					}
				});

				//---- entruck ----
				entruck.setText("\u88c5\u8f66\u5355");
				entruck.setSelected(true);
				entruck.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						entruckMouseClicked(e);
						entruckMouseClicked(e);
					}
				});

				//---- transfer ----
				transfer.setText("\u4e2d\u8f6c\u5355");
				transfer.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						transferMouseClicked(e);
					}
				});

				//---- searchList ----
				searchList.setText("\u67e5\u627e");
				searchList.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						searchListMouseClicked(e);
					}
				});

				GroupLayout searchListPanelLayout = new GroupLayout(searchListPanel);
				searchListPanel.setLayout(searchListPanelLayout);
				searchListPanelLayout.setHorizontalGroup(
					searchListPanelLayout.createParallelGroup()
						.addGroup(searchListPanelLayout.createSequentialGroup()
							.addGap(128, 128, 128)
							.addComponent(label5, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(searchListPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addGroup(searchListPanelLayout.createSequentialGroup()
									.addComponent(entruck)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(transfer))
								.addComponent(deliveryID, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE))
							.addGap(18, 18, 18)
							.addComponent(searchList, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(314, Short.MAX_VALUE))
				);
				searchListPanelLayout.setVerticalGroup(
					searchListPanelLayout.createParallelGroup()
						.addGroup(searchListPanelLayout.createSequentialGroup()
							.addGap(95, 95, 95)
							.addGroup(searchListPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label5)
								.addComponent(deliveryID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(searchList))
							.addGap(18, 18, 18)
							.addGroup(searchListPanelLayout.createParallelGroup()
								.addComponent(transfer)
								.addComponent(entruck))
							.addContainerGap(187, Short.MAX_VALUE))
				);
			}
			startPane.addTab("\u641c\u7d22\u8fd0\u8f93\u5355", searchListPanel);
		}
		add(startPane, BorderLayout.CENTER);

		//======== arrivalVO ========
		{

			//======== panel1 ========
			{

				//======== scrollPane1 ========
				{

					//---- arrivalVOTable ----
					arrivalVOTable.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							arrivalVOTableMouseClicked(e);
						}
					});
					scrollPane1.setViewportView(arrivalVOTable);
				}

				//---- label1 ----
				label1.setText("\u8fd0\u8f6c\u5355\u7f16\u53f7");

				//---- label3 ----
				label3.setText("\u65e5\u671f");

				//---- modifyStatus ----
				modifyStatus.setText("\u4fee\u6539\u72b6\u6001");
				modifyStatus.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						modifyStatusMouseReleased(e);
					}
				});

				//---- cancelArrival ----
				cancelArrival.setText("\u53d6\u6d88");
				cancelArrival.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						cancelArrivalMouseClicked(e);
					}
				});

				//---- doArrive ----
				doArrive.setText("\u786e\u8ba4\u5230\u8fbe");
				doArrive.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						doArriveMouseClicked(e);
					}
				});

				//---- label4 ----
				label4.setText("\u51fa\u53d1\u5730");

				//---- saveArrival ----
				saveArrival.setText("\u4fdd\u5b58");
				saveArrival.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						saveArrivalMouseClicked(e);
					}
				});

				//---- label15 ----
				label15.setText("\u8ba2\u5355\u72b6\u6001");

				GroupLayout panel1Layout = new GroupLayout(panel1);
				panel1.setLayout(panel1Layout);
				panel1Layout.setHorizontalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(panel1Layout.createSequentialGroup()
									.addComponent(label15)
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(status, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(modifyStatus, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
								.addGroup(panel1Layout.createParallelGroup()
									.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 671, GroupLayout.PREFERRED_SIZE)
									.addGroup(panel1Layout.createSequentialGroup()
										.addGroup(panel1Layout.createParallelGroup()
											.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
												.addComponent(label3, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addGroup(GroupLayout.Alignment.LEADING, panel1Layout.createSequentialGroup()
													.addGap(30, 30, 30)
													.addComponent(label2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addComponent(label1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
											.addComponent(label4, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
											.addComponent(transferID, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
											.addComponent(from, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
											.addComponent(arrivalDate, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)))))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(cancelArrival, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
								.addComponent(doArrive, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
								.addComponent(saveArrival, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
				panel1Layout.setVerticalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(panel1Layout.createSequentialGroup()
									.addGap(0, 168, Short.MAX_VALUE)
									.addComponent(saveArrival, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(doArrive, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(cancelArrival))
								.addGroup(panel1Layout.createSequentialGroup()
									.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(transferID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(label1))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(label2)
										.addComponent(from, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(label4))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(label3)
										.addComponent(arrivalDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(11, 11, 11)
									.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(modifyStatus)
										.addComponent(label15))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)))
							.addContainerGap())
				);
			}
			arrivalVO.addTab("\u5230\u8fbe\u5355", panel1);
		}

		//======== entruckVO ========
		{

			//======== DeliveryListPanel ========
			{

				//======== scrollPane3 ========
				{
					scrollPane3.setViewportView(deliveryTable);
				}

				//---- cancelLoad ----
				cancelLoad.setText("\u53d6\u6d88");
				cancelLoad.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						cancelLoadMouseClicked(e);
					}
				});

				//---- createArrival ----
				createArrival.setText("\u751f\u6210\u5230\u8fbe\u5355");
				createArrival.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						createArrivalMouseClicked(e);
					}
				});

				//---- label10 ----
				label10.setText("\u5355\u636e\u7c7b\u578b");

				//---- label11 ----
				label11.setText("\u5355\u53f7");

				//---- label12 ----
				label12.setText("\u51fa\u53d1\u5730");

				//---- label13 ----
				label13.setText("\u76ee\u7684\u5730");

				//---- label14 ----
				label14.setText("\u88c5\u8fd0\u65e5\u671f");

				//---- vehicleLabel ----
				vehicleLabel.setText("\u8f66\u8f86\u7f16\u53f7");

				//---- driverLabel ----
				driverLabel.setText("\u62bc\u8fd0\u5458");

				//---- label18 ----
				label18.setText("\u8fd0\u8d39");

				//---- label19 ----
				label19.setText("\u76d1\u88c5\u5458");

				//---- transferTypeLabel ----
				transferTypeLabel.setText("\u8fd0\u8f93\u7c7b\u578b");

				GroupLayout DeliveryListPanelLayout = new GroupLayout(DeliveryListPanel);
				DeliveryListPanel.setLayout(DeliveryListPanelLayout);
				DeliveryListPanelLayout.setHorizontalGroup(
					DeliveryListPanelLayout.createParallelGroup()
						.addGroup(DeliveryListPanelLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(DeliveryListPanelLayout.createParallelGroup()
								.addGroup(DeliveryListPanelLayout.createSequentialGroup()
									.addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 676, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(DeliveryListPanelLayout.createParallelGroup()
										.addComponent(cancelLoad, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(DeliveryListPanelLayout.createSequentialGroup()
											.addComponent(createArrival)
											.addGap(0, 0, Short.MAX_VALUE))))
								.addGroup(DeliveryListPanelLayout.createSequentialGroup()
									.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addGroup(DeliveryListPanelLayout.createSequentialGroup()
											.addComponent(label13)
											.addGap(18, 18, 18)
											.addComponent(destName, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
										.addGroup(DeliveryListPanelLayout.createSequentialGroup()
											.addGroup(DeliveryListPanelLayout.createParallelGroup()
												.addGroup(GroupLayout.Alignment.TRAILING, DeliveryListPanelLayout.createSequentialGroup()
													.addGroup(DeliveryListPanelLayout.createParallelGroup()
														.addComponent(label10)
														.addComponent(label11))
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))
												.addGroup(DeliveryListPanelLayout.createSequentialGroup()
													.addComponent(label12)
													.addGap(16, 16, 16)))
											.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
												.addComponent(fromName, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
												.addComponent(listType, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
												.addComponent(listID, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))))
									.addGap(45, 45, 45)
									.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addGroup(DeliveryListPanelLayout.createSequentialGroup()
											.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(label14, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(vehicleLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addGroup(DeliveryListPanelLayout.createSequentialGroup()
													.addComponent(label19)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(staffLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(date, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
												.addComponent(vehicleID, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
												.addComponent(staff, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)))
										.addGroup(DeliveryListPanelLayout.createSequentialGroup()
											.addComponent(driverLabel)
											.addGap(18, 18, 18)
											.addComponent(driverName, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)))
									.addGap(37, 37, 37)
									.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addGroup(DeliveryListPanelLayout.createSequentialGroup()
											.addComponent(label18)
											.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(fee, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
										.addGroup(DeliveryListPanelLayout.createSequentialGroup()
											.addComponent(transferTypeLabel)
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(transferType, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))
									.addGap(0, 0, Short.MAX_VALUE)))
							.addContainerGap())
				);
				DeliveryListPanelLayout.setVerticalGroup(
					DeliveryListPanelLayout.createParallelGroup()
						.addGroup(DeliveryListPanelLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label10)
								.addComponent(listType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label14)
								.addComponent(date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label11)
								.addComponent(listID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(vehicleLabel)
								.addComponent(vehicleID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label12)
								.addComponent(fromName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(staffLabel)
								.addComponent(staff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label19)
								.addComponent(transferTypeLabel)
								.addComponent(transferType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label13)
								.addComponent(destName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(driverLabel)
								.addComponent(label18)
								.addComponent(fee, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(driverName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
							.addGroup(DeliveryListPanelLayout.createParallelGroup()
								.addGroup(GroupLayout.Alignment.TRAILING, DeliveryListPanelLayout.createSequentialGroup()
									.addComponent(createArrival, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(cancelLoad)
									.addContainerGap())
								.addComponent(scrollPane3, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)))
				);
			}
			entruckVO.addTab("\u8fd0\u8f6c\u5355", DeliveryListPanel);
		}

		//======== resultDialog ========
		{
			resultDialog.setTitle("\u64cd\u4f5c\u7ed3\u679c");
			Container resultDialogContentPane = resultDialog.getContentPane();

			//---- label6 ----
			label6.setText("\u64cd\u4f5c\u6210\u529f");

			//---- resultSureButton ----
			resultSureButton.setText("\u786e\u5b9a");
			resultSureButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					resultSureButtonMouseClicked(e);
				}
			});

			GroupLayout resultDialogContentPaneLayout = new GroupLayout(resultDialogContentPane);
			resultDialogContentPane.setLayout(resultDialogContentPaneLayout);
			resultDialogContentPaneLayout.setHorizontalGroup(
				resultDialogContentPaneLayout.createParallelGroup()
					.addGroup(resultDialogContentPaneLayout.createSequentialGroup()
						.addGap(118, 118, 118)
						.addGroup(resultDialogContentPaneLayout.createParallelGroup()
							.addComponent(resultSureButton)
							.addComponent(label6, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(123, Short.MAX_VALUE))
			);
			resultDialogContentPaneLayout.setVerticalGroup(
				resultDialogContentPaneLayout.createParallelGroup()
					.addGroup(resultDialogContentPaneLayout.createSequentialGroup()
						.addGap(51, 51, 51)
						.addComponent(label6)
						.addGap(28, 28, 28)
						.addComponent(resultSureButton)
						.addContainerGap(44, Short.MAX_VALUE))
			);
			resultDialog.setSize(320, 200);
			resultDialog.setLocationRelativeTo(null);
		}

		//======== errorDialog ========
		{
			errorDialog.setTitle("\u5f02\u5e38");
			Container errorDialogContentPane = errorDialog.getContentPane();

			//---- label7 ----
			label7.setText("\u7f51\u7edc\u8fde\u63a5\u4e2d\u65ad");

			//---- errorSure ----
			errorSure.setText("\u786e\u5b9a");
			errorSure.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					button1MouseClicked(e);
					errorSureMouseClicked(e);
				}
			});

			GroupLayout errorDialogContentPaneLayout = new GroupLayout(errorDialogContentPane);
			errorDialogContentPane.setLayout(errorDialogContentPaneLayout);
			errorDialogContentPaneLayout.setHorizontalGroup(
				errorDialogContentPaneLayout.createParallelGroup()
					.addGroup(errorDialogContentPaneLayout.createSequentialGroup()
						.addContainerGap(95, Short.MAX_VALUE)
						.addGroup(errorDialogContentPaneLayout.createParallelGroup()
							.addGroup(GroupLayout.Alignment.TRAILING, errorDialogContentPaneLayout.createSequentialGroup()
								.addComponent(label7, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addGap(80, 80, 80))
							.addGroup(GroupLayout.Alignment.TRAILING, errorDialogContentPaneLayout.createSequentialGroup()
								.addComponent(errorSure)
								.addGap(111, 111, 111))))
			);
			errorDialogContentPaneLayout.setVerticalGroup(
				errorDialogContentPaneLayout.createParallelGroup()
					.addGroup(errorDialogContentPaneLayout.createSequentialGroup()
						.addGap(41, 41, 41)
						.addComponent(label7)
						.addGap(36, 36, 36)
						.addComponent(errorSure)
						.addContainerGap(46, Short.MAX_VALUE))
			);
			errorDialog.pack();
			errorDialog.setLocationRelativeTo(errorDialog.getOwner());
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
									.addGap(76, 76, 76)
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
	private JTabbedPane startPane;
	private JPanel arrivalListPanel;
	private JScrollPane scrollPane2;
	private JTable arrivalTable;
	private JButton selectArrival;
	private JPanel searchListPanel;
	private JLabel label5;
	private JTextField deliveryID;
	private JRadioButton entruck;
	private JRadioButton transfer;
	private JButton searchList;
	private JTabbedPane arrivalVO;
	private JPanel panel1;
	private JScrollPane scrollPane1;
	private JTable arrivalVOTable;
	private JLabel label1;
	private JTextField transferID;
	private JLabel label2;
	private JTextField from;
	private JLabel label3;
	private JTextField arrivalDate;
	private JComboBox status;
	private JButton modifyStatus;
	private JButton cancelArrival;
	private JButton doArrive;
	private JLabel label4;
	private JButton saveArrival;
	private JLabel label15;
	private JTabbedPane entruckVO;
	private JPanel DeliveryListPanel;
	private JScrollPane scrollPane3;
	private JTable deliveryTable;
	private JButton cancelLoad;
	private JButton createArrival;
	private JLabel label10;
	private JTextField listType;
	private JLabel label11;
	private JTextField listID;
	private JLabel label12;
	private JTextField fromName;
	private JLabel label13;
	private JTextField destName;
	private JLabel label14;
	private JTextField date;
	private JLabel vehicleLabel;
	private JTextField vehicleID;
	private JLabel staffLabel;
	private JLabel driverLabel;
	private JTextField staff;
	private JTextField driverName;
	private JLabel label18;
	private JTextField fee;
	private JLabel label19;
	private JLabel transferTypeLabel;
	private JTextField transferType;
	private JDialog resultDialog;
	private JLabel label6;
	private JButton resultSureButton;
	private JDialog errorDialog;
	private JLabel label7;
	private JButton errorSure;
	private JDialog cancelDialog;
	private JPanel panel;
	private JLabel label8;
	private JLabel label9;
	private JButton cancelSureButton;
	private JButton notCancelButton;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
