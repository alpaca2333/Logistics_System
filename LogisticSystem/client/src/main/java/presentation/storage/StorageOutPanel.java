/*
 * Created by JFormDesigner on Tue Nov 24 22:44:48 CST 2015
 */

package presentation.storage;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import data.vo.BriefTransferAndStorageOutVO;
import data.vo.TransferListVO;
import businesslogic.service.storage.StorageOutService;

/**
 * @author sunxu
 */
public class StorageOutPanel extends JPanel {
	StorageOutService storageOut;
	TransferListVO transferListVO;
	BriefTransferAndStorageOutVO briefTransferAndStorageOutVO;
	public StorageOutPanel(StorageOutService storageOut) {
		this.storageOut	 = storageOut;
		initComponents();
		setList();
		this.setVisible(true);
	}
	
	private void setTransferList(TransferListVO transferList){
		if (transferList != null) {
			if(transferList.transferListID != null){
			listID.setText(transferList.transferListID);
			}
			else {
				listID.setText("保存后生成");
			}
			centerID.setText(transferList.transferCenterID);
			centerName.setText(transferList.transferCenter);
			destID.setText(transferList.target);
			destName.setText(transferList.targetName);
			date.setText(transferList.date);
			vehicleID.setText(transferList.vehicleCode);
			staffName.setText(transferList.staff);
			if (transferList.transferType.equals("汽运")) {
				driverName.setText(transferList.driver);
				label12.setText("押运员");
				label10.setText("车辆编号");
			}else{
			driverName.setText(transferList.fee);
			label12.setText("费用");
			label10.setText("班次");
			}
			DefaultTableModel model = new DefaultTableModel(transferList.orderAndPosition,transferList.header);
			loadTable.setModel(model);
			loadTable.updateUI();
			
			remove(startPane);
			add(transferListPane,BorderLayout.CENTER);
			transferListPane.updateUI();
			transferListPane.setVisible(true);
		}
	}
	
	private void setList(){
		selectStorageOut.setEnabled(false);
		selectTransfer.setEnabled(false);
		briefTransferAndStorageOutVO = storageOut.newStorageOut();
		DefaultTableModel storageOutModel  = new DefaultTableModel(briefTransferAndStorageOutVO.getStorageOutList(),briefTransferAndStorageOutVO.getStorageOutListHeader());
		DefaultTableModel transferListModel = new DefaultTableModel(briefTransferAndStorageOutVO.getTransferList(),briefTransferAndStorageOutVO.getTransferListHeader());
		storageOutTable.setModel(storageOutModel);
		transferListTable.setModel(transferListModel);
		storageOutList.repaint();
		transferListTable.repaint();
	}

	private void doStorageOutMouseClicked(MouseEvent e) {
		//storageBusiness.doAllStorageOut();
	}

	private void storageOutTableMouseClicked(MouseEvent e) {
		selectStorageOut.setEnabled(true);
	}

	private void transferListTableMouseClicked(MouseEvent e) {
		selectTransfer.setEnabled(true);
	}

	private void cancelLoadMouseReleased(MouseEvent e) {
		// TODO add your code here
	}

	private void getTransferButtonMouseReleased(MouseEvent e) {
		int row = transferListTable.getSelectedRow();
		String s = (String) transferListTable.getValueAt(row, 0);
		long id = Long.parseLong(s);
		 transferListVO =storageOut.getTransferList(id);
		setTransferList(transferListVO);
	}

	private void createStorageOutMouseReleased(MouseEvent e) {
		// TODO add your code here
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		startPane = new JTabbedPane();
		storageOutList = new JPanel();
		scrollPane1 = new JScrollPane();
		storageOutTable = new JTable();
		doStorageOut = new JButton();
		selectStorageOut = new JButton();
		textField1 = new JTextField();
		button3 = new JButton();
		transferList = new JPanel();
		scrollPane2 = new JScrollPane();
		transferListTable = new JTable();
		selectTransfer = new JButton();
		textField2 = new JTextField();
		searchTransfer = new JButton();
		getTransferButton = new JButton();
		tabbedPane3 = new JTabbedPane();
		storageOutVO = new JPanel();
		scrollPane4 = new JScrollPane();
		table1 = new JTable();
		storageOutCancel = new JButton();
		storageOutSave = new JButton();
		label5 = new JLabel();
		textField7 = new JTextField();
		label6 = new JLabel();
		textField8 = new JTextField();
		label1 = new JLabel();
		textField3 = new JTextField();
		label2 = new JLabel();
		textField4 = new JTextField();
		label3 = new JLabel();
		textField5 = new JTextField();
		label7 = new JLabel();
		textField9 = new JTextField();
		textField6 = new JTextField();
		label4 = new JLabel();
		transferListPane = new JTabbedPane();
		DeliveryListPanel = new JPanel();
		scrollPane5 = new JScrollPane();
		loadTable = new JTable();
		cancelLoad = new JButton();
		label8 = new JLabel();
		label9 = new JLabel();
		label10 = new JLabel();
		label11 = new JLabel();
		label12 = new JLabel();
		listID = new JTextField();
		centerID = new JTextField();
		centerName = new JTextField();
		destID = new JTextField();
		destName = new JTextField();
		label13 = new JLabel();
		label14 = new JLabel();
		label15 = new JLabel();
		vehicleID = new JTextField();
		staffName = new JTextField();
		driverName = new JTextField();
		label16 = new JLabel();
		date = new JTextField();
		createStorageOut = new JButton();

		//======== this ========
		setLayout(new BorderLayout());

		//======== startPane ========
		{
			startPane.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

			//======== storageOutList ========
			{

				//======== scrollPane1 ========
				{

					//---- storageOutTable ----
					storageOutTable.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
					storageOutTable.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							storageOutTableMouseClicked(e);
						}
					});
					scrollPane1.setViewportView(storageOutTable);
				}

				//---- doStorageOut ----
				doStorageOut.setText("\u51fa\u5e93");
				doStorageOut.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
				doStorageOut.setIcon(new ImageIcon(getClass().getResource("/icons/storageout_24x24.png")));
				doStorageOut.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						doStorageOutMouseClicked(e);
					}
				});

				//---- selectStorageOut ----
				selectStorageOut.setText("\u67e5\u770b");
				selectStorageOut.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
				selectStorageOut.setIcon(new ImageIcon(getClass().getResource("/icons/see_24x24.png")));

				//---- textField1 ----
				textField1.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- button3 ----
				button3.setText("\u641c\u7d22");
				button3.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
				button3.setIcon(new ImageIcon(getClass().getResource("/icons/search_16x16.png")));

				GroupLayout storageOutListLayout = new GroupLayout(storageOutList);
				storageOutList.setLayout(storageOutListLayout);
				storageOutListLayout.setHorizontalGroup(
					storageOutListLayout.createParallelGroup()
						.addGroup(storageOutListLayout.createSequentialGroup()
							.addGroup(storageOutListLayout.createParallelGroup()
								.addGroup(storageOutListLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(textField1, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(button3, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
									.addGap(0, 0, Short.MAX_VALUE))
								.addGroup(storageOutListLayout.createSequentialGroup()
									.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 673, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(storageOutListLayout.createParallelGroup()
										.addComponent(doStorageOut, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
										.addComponent(selectStorageOut, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))))
							.addContainerGap())
				);
				storageOutListLayout.setVerticalGroup(
					storageOutListLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, storageOutListLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(storageOutListLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(button3, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(storageOutListLayout.createParallelGroup()
								.addGroup(storageOutListLayout.createSequentialGroup()
									.addGap(0, 219, Short.MAX_VALUE)
									.addComponent(selectStorageOut)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(doStorageOut)
									.addContainerGap())
								.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)))
				);
			}
			startPane.addTab("\u5df2\u5ba1\u6279\u5165\u5e93\u5355", storageOutList);

			//======== transferList ========
			{

				//======== scrollPane2 ========
				{

					//---- transferListTable ----
					transferListTable.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
					transferListTable.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							transferListTableMouseClicked(e);
						}
					});
					scrollPane2.setViewportView(transferListTable);
				}

				//---- selectTransfer ----
				selectTransfer.setText("select");

				//---- textField2 ----
				textField2.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- searchTransfer ----
				searchTransfer.setText("\u641c\u7d22");
				searchTransfer.setIcon(new ImageIcon(getClass().getResource("/icons/search_16x16.png")));
				searchTransfer.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- getTransferButton ----
				getTransferButton.setText("\u67e5\u770b");
				getTransferButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						getTransferButtonMouseReleased(e);
					}
				});

				GroupLayout transferListLayout = new GroupLayout(transferList);
				transferList.setLayout(transferListLayout);
				transferListLayout.setHorizontalGroup(
					transferListLayout.createParallelGroup()
						.addGroup(transferListLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(transferListLayout.createParallelGroup()
								.addGroup(transferListLayout.createSequentialGroup()
									.addComponent(textField2, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(searchTransfer))
								.addGroup(transferListLayout.createSequentialGroup()
									.addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 685, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(getTransferButton, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)))
							.addGap(32691, 32691, 32691)
							.addComponent(selectTransfer, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
							.addContainerGap())
				);
				transferListLayout.setVerticalGroup(
					transferListLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, transferListLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(transferListLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(transferListLayout.createSequentialGroup()
									.addGap(0, 288, Short.MAX_VALUE)
									.addComponent(getTransferButton, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
								.addGroup(transferListLayout.createSequentialGroup()
									.addGroup(transferListLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(searchTransfer, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
									.addGap(14, 14, 14)
									.addGroup(transferListLayout.createParallelGroup()
										.addGroup(transferListLayout.createSequentialGroup()
											.addGap(0, 0, Short.MAX_VALUE)
											.addComponent(selectTransfer, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
										.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))))
							.addContainerGap())
				);
			}
			startPane.addTab("\u5df2\u5ba1\u6279\u4e2d\u8f6c\u5355", transferList);
		}
		add(startPane, BorderLayout.CENTER);

		//======== tabbedPane3 ========
		{
			tabbedPane3.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

			//======== storageOutVO ========
			{

				//======== scrollPane4 ========
				{
					scrollPane4.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
					scrollPane4.setViewportView(table1);
				}

				//---- storageOutCancel ----
				storageOutCancel.setText("\u53d6\u6d88");
				storageOutCancel.setIcon(new ImageIcon(getClass().getResource("/icons/cancel_24x24.png")));
				storageOutCancel.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- storageOutSave ----
				storageOutSave.setText("\u4fdd\u5b58");
				storageOutSave.setIcon(new ImageIcon(getClass().getResource("/icons/save_24x24.png")));
				storageOutSave.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- label5 ----
				label5.setText("\u8d39\u7528");
				label5.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- textField7 ----
				textField7.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- label6 ----
				label6.setText("\u65e5\u671f");
				label6.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- textField8 ----
				textField8.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- label1 ----
				label1.setText("\u4e2d\u8f6c\u4e2d\u5fc3");
				label1.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- textField3 ----
				textField3.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- label2 ----
				label2.setText("\u76ee\u6807\u673a\u6784");
				label2.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- textField4 ----
				textField4.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- label3 ----
				label3.setText("\u8fd0\u8f93\u65b9\u5f0f");
				label3.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- textField5 ----
				textField5.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- label7 ----
				label7.setText("\u73ed\u6b21");
				label7.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- textField9 ----
				textField9.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- textField6 ----
				textField6.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- label4 ----
				label4.setText("\u76d1\u88c5\u5458");
				label4.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				GroupLayout storageOutVOLayout = new GroupLayout(storageOutVO);
				storageOutVO.setLayout(storageOutVOLayout);
				storageOutVOLayout.setHorizontalGroup(
					storageOutVOLayout.createParallelGroup()
						.addGroup(storageOutVOLayout.createSequentialGroup()
							.addGroup(storageOutVOLayout.createParallelGroup()
								.addGroup(storageOutVOLayout.createSequentialGroup()
									.addContainerGap()
									.addGroup(storageOutVOLayout.createParallelGroup()
										.addGroup(storageOutVOLayout.createSequentialGroup()
											.addComponent(label1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addGap(5, 5, 5)
											.addComponent(textField3, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addGap(15, 15, 15)
											.addComponent(label4, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addGap(5, 5, 5)
											.addComponent(textField6, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addGap(15, 15, 15)
											.addComponent(label3, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addGap(5, 5, 5)
											.addComponent(textField5, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
										.addGroup(storageOutVOLayout.createSequentialGroup()
											.addComponent(label2, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addGap(5, 5, 5)
											.addComponent(textField4, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addGap(15, 15, 15)
											.addComponent(label5, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addGap(5, 5, 5)
											.addComponent(textField7, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
										.addGroup(storageOutVOLayout.createSequentialGroup()
											.addComponent(label7, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addGap(5, 5, 5)
											.addComponent(textField9, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addGap(15, 15, 15)
											.addComponent(label6, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addGap(5, 5, 5)
											.addComponent(textField8, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)))
									.addGap(0, 0, Short.MAX_VALUE))
								.addGroup(storageOutVOLayout.createSequentialGroup()
									.addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 690, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(storageOutVOLayout.createParallelGroup()
										.addComponent(storageOutCancel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(storageOutSave, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
							.addContainerGap())
				);
				storageOutVOLayout.setVerticalGroup(
					storageOutVOLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, storageOutVOLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(storageOutVOLayout.createParallelGroup()
								.addComponent(label1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label4, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(10, 10, 10)
							.addGroup(storageOutVOLayout.createParallelGroup()
								.addComponent(label2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label5, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(10, 10, 10)
							.addGroup(storageOutVOLayout.createParallelGroup()
								.addComponent(label7, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label6, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(storageOutVOLayout.createParallelGroup()
								.addGroup(storageOutVOLayout.createSequentialGroup()
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
									.addComponent(storageOutSave)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(storageOutCancel)
									.addContainerGap())
								.addGroup(storageOutVOLayout.createSequentialGroup()
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(scrollPane4, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))))
				);
			}
			tabbedPane3.addTab("\u51fa\u5e93\u5355", storageOutVO);
		}

		//======== transferListPane ========
		{
			transferListPane.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

			//======== DeliveryListPanel ========
			{

				//======== scrollPane5 ========
				{

					//---- loadTable ----
					loadTable.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
					scrollPane5.setViewportView(loadTable);
				}

				//---- cancelLoad ----
				cancelLoad.setText("\u53d6\u6d88");
				cancelLoad.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
				cancelLoad.setIcon(new ImageIcon(getClass().getResource("/icons/cancel_24x24.png")));
				cancelLoad.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						cancelLoadMouseReleased(e);
					}
				});

				//---- label8 ----
				label8.setText("\u4e2d\u8f6c\u4e2d\u5fc3\u7f16\u53f7");

				//---- label9 ----
				label9.setText("\u4e2d\u8f6c\u5355\u7f16\u53f7");

				//---- label10 ----
				label10.setText("\u76ee\u7684\u5730");

				//---- label11 ----
				label11.setText("\u76ee\u7684\u5730\u7f16\u53f7");

				//---- label12 ----
				label12.setText("\u4e2d\u8f6c\u4e2d\u5fc3");

				//---- label13 ----
				label13.setText("\u73ed\u6b21  ");

				//---- label14 ----
				label14.setText("\u76d1\u88c5\u5458");

				//---- label15 ----
				label15.setText("\u62bc\u8fd0\u5458");

				//---- label16 ----
				label16.setText("\u88c5\u8fd0\u65e5\u671f");

				//---- createStorageOut ----
				createStorageOut.setText("\u65b0\u5efa");
				createStorageOut.setIcon(new ImageIcon(getClass().getResource("/icons/new_24x24.png")));
				createStorageOut.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
				createStorageOut.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						createStorageOutMouseReleased(e);
					}
				});

				GroupLayout DeliveryListPanelLayout = new GroupLayout(DeliveryListPanel);
				DeliveryListPanel.setLayout(DeliveryListPanelLayout);
				DeliveryListPanelLayout.setHorizontalGroup(
					DeliveryListPanelLayout.createParallelGroup()
						.addGroup(DeliveryListPanelLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(DeliveryListPanelLayout.createParallelGroup()
								.addGroup(DeliveryListPanelLayout.createSequentialGroup()
									.addComponent(scrollPane5)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))
								.addGroup(DeliveryListPanelLayout.createSequentialGroup()
									.addGroup(DeliveryListPanelLayout.createParallelGroup()
										.addGroup(DeliveryListPanelLayout.createSequentialGroup()
											.addComponent(label9)
											.addGap(32, 32, 32)
											.addComponent(listID, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
											.addGap(135, 135, 135)
											.addComponent(label16)
											.addGap(10, 10, 10)
											.addComponent(date, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
										.addGroup(DeliveryListPanelLayout.createSequentialGroup()
											.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
												.addGroup(DeliveryListPanelLayout.createSequentialGroup()
													.addComponent(label10)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(destName, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
												.addGroup(DeliveryListPanelLayout.createSequentialGroup()
													.addComponent(label11)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(destID, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
												.addGroup(DeliveryListPanelLayout.createSequentialGroup()
													.addComponent(label12)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(centerName, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
												.addGroup(DeliveryListPanelLayout.createSequentialGroup()
													.addComponent(label8)
													.addGap(18, 18, 18)
													.addComponent(centerID, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)))
											.addGap(135, 135, 135)
											.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addGroup(DeliveryListPanelLayout.createSequentialGroup()
													.addComponent(label15, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
													.addGap(18, 18, 18)
													.addComponent(driverName, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
												.addGroup(DeliveryListPanelLayout.createSequentialGroup()
													.addComponent(label14, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addGap(18, 18, 18)
													.addComponent(staffName, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
												.addComponent(label13, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))
										.addGroup(DeliveryListPanelLayout.createSequentialGroup()
											.addGap(365, 365, 365)
											.addComponent(vehicleID, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
									.addGap(221, 221, 221)))
							.addGroup(DeliveryListPanelLayout.createParallelGroup()
								.addComponent(createStorageOut, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(DeliveryListPanelLayout.createSequentialGroup()
									.addComponent(cancelLoad, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
									.addGap(0, 0, Short.MAX_VALUE)))
							.addContainerGap())
				);
				DeliveryListPanelLayout.setVerticalGroup(
					DeliveryListPanelLayout.createParallelGroup()
						.addGroup(DeliveryListPanelLayout.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(createStorageOut)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(cancelLoad, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(GroupLayout.Alignment.TRAILING, DeliveryListPanelLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(DeliveryListPanelLayout.createParallelGroup()
								.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(label9)
									.addComponent(listID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(DeliveryListPanelLayout.createSequentialGroup()
									.addGap(3, 3, 3)
									.addComponent(label16))
								.addComponent(date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(DeliveryListPanelLayout.createParallelGroup()
								.addGroup(GroupLayout.Alignment.TRAILING, DeliveryListPanelLayout.createSequentialGroup()
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(vehicleID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(DeliveryListPanelLayout.createSequentialGroup()
									.addGap(9, 9, 9)
									.addGroup(DeliveryListPanelLayout.createParallelGroup()
										.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
											.addComponent(label8)
											.addComponent(centerID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(label13))))
							.addGroup(DeliveryListPanelLayout.createParallelGroup()
								.addGroup(DeliveryListPanelLayout.createSequentialGroup()
									.addGap(6, 6, 6)
									.addGroup(DeliveryListPanelLayout.createParallelGroup()
										.addComponent(label12)
										.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
											.addComponent(staffName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(label14))))
								.addGroup(DeliveryListPanelLayout.createSequentialGroup()
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(centerName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(12, 12, 12)
							.addGroup(DeliveryListPanelLayout.createParallelGroup()
								.addGroup(DeliveryListPanelLayout.createSequentialGroup()
									.addGap(3, 3, 3)
									.addGroup(DeliveryListPanelLayout.createParallelGroup()
										.addComponent(label11)
										.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
											.addComponent(driverName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(label15))))
								.addComponent(destID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(DeliveryListPanelLayout.createParallelGroup()
								.addComponent(label10)
								.addComponent(destName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(scrollPane5, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE))
				);
			}
			transferListPane.addTab("\u4e2d\u8f6c\u5355", DeliveryListPanel);
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JTabbedPane startPane;
	private JPanel storageOutList;
	private JScrollPane scrollPane1;
	private JTable storageOutTable;
	private JButton doStorageOut;
	private JButton selectStorageOut;
	private JTextField textField1;
	private JButton button3;
	private JPanel transferList;
	private JScrollPane scrollPane2;
	private JTable transferListTable;
	private JButton selectTransfer;
	private JTextField textField2;
	private JButton searchTransfer;
	private JButton getTransferButton;
	private JTabbedPane tabbedPane3;
	private JPanel storageOutVO;
	private JScrollPane scrollPane4;
	private JTable table1;
	private JButton storageOutCancel;
	private JButton storageOutSave;
	private JLabel label5;
	private JTextField textField7;
	private JLabel label6;
	private JTextField textField8;
	private JLabel label1;
	private JTextField textField3;
	private JLabel label2;
	private JTextField textField4;
	private JLabel label3;
	private JTextField textField5;
	private JLabel label7;
	private JTextField textField9;
	private JTextField textField6;
	private JLabel label4;
	private JTabbedPane transferListPane;
	private JPanel DeliveryListPanel;
	private JScrollPane scrollPane5;
	private JTable loadTable;
	private JButton cancelLoad;
	private JLabel label8;
	private JLabel label9;
	private JLabel label10;
	private JLabel label11;
	private JLabel label12;
	private JTextField listID;
	private JTextField centerID;
	private JTextField centerName;
	private JTextField destID;
	private JTextField destName;
	private JLabel label13;
	private JLabel label14;
	private JLabel label15;
	private JTextField vehicleID;
	private JTextField staffName;
	private JTextField driverName;
	private JLabel label16;
	private JTextField date;
	private JButton createStorageOut;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
