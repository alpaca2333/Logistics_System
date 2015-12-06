/*
 * Created by JFormDesigner on Mon Nov 30 21:05:40 CST 2015
 */

package presentation.transfer.center;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import data.vo.ArrivalListVO;
import data.vo.ArrivalVO;
import data.vo.DeliveryListVO;
import data.vo.EntruckListVO;
import data.vo.TransferListVO;
import businesslogic.service.Transfer.center.TransferReceiveService;

/**
 * @author sunxu
 */
public class TransferReceivePanel extends JPanel {
	TransferReceiveService transferReceive;
	ArrivalListVO arriveList;
	ArrivalVO arrival;
	TransferListVO transferListVO;
	EntruckListVO entruckListVO;

	public void showArriveList() {
		arriveList = transferReceive.getCheckedArrivalList();
		if(arriveList == null){//如果没有成功获取，则跳过后面步骤
			return;
		}
		DefaultTableModel arriveListModel = new DefaultTableModel(
				arrival.getOrderAndStatus(), arrival.getHeader());
		arriveListTabble.setModel(arriveListModel);

		arriveListTabble.repaint();
	}

	public TransferReceivePanel(TransferReceiveService transferReceive) {
		this.transferReceive = transferReceive;
		initComponents();
		showArriveList();
		selectArrival.setEnabled(false);
		this.setVisible(true);
	}

	private void createStorageInMouseClicked(MouseEvent e) {
		// TODO add your code here
	}



	private void selectArrivalMouseClicked(MouseEvent e) {
		if (selectArrival.isEnabled()) {
			int row = arriveListTabble.getSelectedRow();
			String info = (String) arriveListTabble.getValueAt(row, 0);
			long id = Long.parseLong(info);
			arrival = transferReceive.chooseArrival(id);
			setArrivalVO();
			remove(startPane);
			add(arrivalVO);
			arrivalVO.validate();
			arrivalVO.updateUI();
			this.repaint();
		}

	}

	private void setArrivalVO() {
		// 设置arrivalVO
	}

	private void setTransferListVO() {
		// 设置中转单显示信息
	}

	private void setEntruckListVO() {
		// 设置装车单显示信息
	}

	private void doArriveMouseClicked(MouseEvent e) {
		// 修改订单状态，，这个有问题，再确认一下
		transferReceive.saveArriveList(arrival);
	}

	private void searchListMouseClicked(MouseEvent e) {
		String input = deliveryID.getText();
		long id = -1;
		try {
			id = Long.parseLong(input);
		} catch (NumberFormatException e2) {
			deliveryID.setText("单号格式错误");
			return;
		}
		if (transfer.isSelected()) {
			transferListVO = transferReceive.getTransferList(id);
			if (transferListVO == null) {
				deliveryID.setText("单号不存在");
				return;
			}
		} else {
			entruckListVO = transferReceive.getEntruckList(id);
			if (entruckListVO == null) {
				deliveryID.setText("单号不存在");
				return;
			}
		}
		remove(startPane);
		if (arrivalVO != null)
			remove(arrivalVO);
		add(deliveryVO);

		deliveryVO.validate();
		deliveryVO.updateUI();
		deliveryVO.setVisible(true);
		this.repaint();
	}

	private void transferCancelMouseClicked(MouseEvent e) {
		remove(deliveryVO);
		add(startPane);

		startPane.validate();
		startPane.updateUI();
		this.repaint();

	}

	private void cancelArrivalMouseClicked(MouseEvent e) {
		remove(arrivalVO);
		add(startPane);

		startPane.validate();
		startPane.updateUI();
		this.repaint();
	}

	private void entruckMouseClicked(MouseEvent e) {
		transfer.setSelected(false);
		entruck.setSelected(true);
	}

	private void transferMouseReleased(MouseEvent e) {
		transfer.setSelected(true);
		entruck.setSelected(false);
	}

	private void deliveryIDMouseReleased(MouseEvent e) {
		deliveryID.setText("");
	}

	private void arriveListTabbleMouseReleased(MouseEvent e) {
		selectArrival.setEnabled(true);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		startPane = new JTabbedPane();
		arrivelistPanel = new JPanel();
		scrollPane2 = new JScrollPane();
		arriveListTabble = new JTable();
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
		table2 = new JTable();
		label1 = new JLabel();
		transferID = new JTextField();
		label2 = new JLabel();
		from = new JTextField();
		label3 = new JLabel();
		arrivalDate = new JTextField();
		statusBox = new JComboBox();
		modifyStatus = new JButton();
		cancelArrival = new JButton();
		doArrive = new JButton();
		label4 = new JLabel();
		label13 = new JLabel();
		deliveryVO = new JTabbedPane();
		transferVO = new JPanel();
		scrollPane3 = new JScrollPane();
		orderAndPosition = new JTable();
		label6 = new JLabel();
		textField3 = new JTextField();
		label7 = new JLabel();
		textField4 = new JTextField();
		label8 = new JLabel();
		textField5 = new JTextField();
		label9 = new JLabel();
		textField6 = new JTextField();
		label10 = new JLabel();
		textField7 = new JTextField();
		label11 = new JLabel();
		textField8 = new JTextField();
		label12 = new JLabel();
		textField9 = new JTextField();
		transferCancel = new JButton();
		createArrival = new JButton();

		//======== this ========
		setLayout(new BorderLayout());

		//======== startPane ========
		{

			//======== arrivelistPanel ========
			{

				//======== scrollPane2 ========
				{

					//---- arriveListTabble ----
					arriveListTabble.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
					arriveListTabble.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							arriveListTabbleMouseReleased(e);
						}
					});
					scrollPane2.setViewportView(arriveListTabble);
				}

				//---- selectArrival ----
				selectArrival.setText("\u67e5\u770b");
				selectArrival.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						selectArrivalMouseClicked(e);
					}
				});

				GroupLayout arrivelistPanelLayout = new GroupLayout(arrivelistPanel);
				arrivelistPanel.setLayout(arrivelistPanelLayout);
				arrivelistPanelLayout.setHorizontalGroup(
					arrivelistPanelLayout.createParallelGroup()
						.addGroup(arrivelistPanelLayout.createSequentialGroup()
							.addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 684, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(selectArrival, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
							.addContainerGap())
				);
				arrivelistPanelLayout.setVerticalGroup(
					arrivelistPanelLayout.createParallelGroup()
						.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
						.addGroup(arrivelistPanelLayout.createSequentialGroup()
							.addGap(0, 280, Short.MAX_VALUE)
							.addComponent(selectArrival, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
				);
			}
			startPane.addTab("\u5230\u8fbe\u5355\u5217\u8868", arrivelistPanel);

			//======== searchListPanel ========
			{

				//---- label5 ----
				label5.setText("\u8bf7\u8f93\u5165\u8fd0\u8f93\u5355\u53f7");

				//---- deliveryID ----
				deliveryID.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						deliveryIDMouseReleased(e);
					}
				});

				//---- entruck ----
				entruck.setText("\u88c5\u8f66\u5355");
				entruck.setSelected(true);
				entruck.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						entruckMouseClicked(e);
					}
				});

				//---- transfer ----
				transfer.setText("\u4e2d\u8f6c\u5355");
				transfer.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						transferMouseReleased(e);
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
							.addContainerGap(162, Short.MAX_VALUE))
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
					scrollPane1.setViewportView(table2);
				}

				//---- label1 ----
				label1.setText("\u8fd0\u8f6c\u5355\u7f16\u53f7");

				//---- label3 ----
				label3.setText("\u65e5\u671f");

				//---- modifyStatus ----
				modifyStatus.setText("\u4fee\u6539\u72b6\u6001");

				//---- cancelArrival ----
				cancelArrival.setText("\u53d6\u6d88");
				cancelArrival.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						cancelArrivalMouseClicked(e);
						cancelArrivalMouseClicked(e);
					}
				});

				//---- doArrive ----
				doArrive.setText("\u786e\u8ba4\u5230\u8fbe");
				doArrive.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						createStorageInMouseClicked(e);
						doArriveMouseClicked(e);
					}
				});

				//---- label4 ----
				label4.setText("\u51fa\u53d1\u5730");

				//---- label13 ----
				label13.setText("\u72b6\u6001");

				GroupLayout panel1Layout = new GroupLayout(panel1);
				panel1.setLayout(panel1Layout);
				panel1Layout.setHorizontalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(panel1Layout.createSequentialGroup()
									.addComponent(label13)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(statusBox, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
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
								.addComponent(doArrive, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
				panel1Layout.setVerticalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(panel1Layout.createSequentialGroup()
									.addGap(0, 224, Short.MAX_VALUE)
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
										.addComponent(statusBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(modifyStatus)
										.addComponent(label13))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)))
							.addContainerGap())
				);
			}
			arrivalVO.addTab("\u5230\u8fbe\u5355", panel1);
		}

		//======== deliveryVO ========
		{

			//======== transferVO ========
			{

				//======== scrollPane3 ========
				{
					scrollPane3.setViewportView(orderAndPosition);
				}

				//---- label6 ----
				label6.setText("\u4e2d\u8f6c\u4e2d\u5fc3");

				//---- label7 ----
				label7.setText("\u76ee\u6807\u673a\u6784");

				//---- label8 ----
				label8.setText("\u8fd0\u8f93\u65b9\u5f0f");

				//---- label9 ----
				label9.setText("\u76d1\u88c5\u5458");

				//---- label10 ----
				label10.setText("\u8d39\u7528");

				//---- label11 ----
				label11.setText("\u65e5\u671f");

				//---- label12 ----
				label12.setText("\u73ed\u6b21");

				//---- transferCancel ----
				transferCancel.setText("\u53d6\u6d88");
				transferCancel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						transferCancelMouseClicked(e);
					}
				});

				//---- createArrival ----
				createArrival.setText("\u751f\u6210\u5230\u8fbe\u5355");

				GroupLayout transferVOLayout = new GroupLayout(transferVO);
				transferVO.setLayout(transferVOLayout);
				transferVOLayout.setHorizontalGroup(
					transferVOLayout.createParallelGroup()
						.addGroup(transferVOLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(transferVOLayout.createParallelGroup()
								.addGroup(transferVOLayout.createSequentialGroup()
									.addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 679, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(transferVOLayout.createParallelGroup()
										.addComponent(transferCancel, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
										.addComponent(createArrival, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
								.addGroup(transferVOLayout.createSequentialGroup()
									.addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
										.addGroup(GroupLayout.Alignment.LEADING, transferVOLayout.createSequentialGroup()
											.addComponent(label12, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(textField9, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
										.addGroup(GroupLayout.Alignment.LEADING, transferVOLayout.createSequentialGroup()
											.addComponent(label8, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(textField5, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
										.addGroup(GroupLayout.Alignment.LEADING, transferVOLayout.createSequentialGroup()
											.addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
												.addComponent(label7, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
												.addComponent(label6, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
											.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
											.addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(textField3, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
												.addComponent(textField4, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))))
									.addGap(99, 99, 99)
									.addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
										.addComponent(label9, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
										.addComponent(label10, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
										.addComponent(label11, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(transferVOLayout.createParallelGroup()
										.addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
											.addComponent(textField6, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
											.addComponent(textField8, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
										.addComponent(textField7, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
									.addGap(0, 376, Short.MAX_VALUE)))
							.addContainerGap())
				);
				transferVOLayout.setVerticalGroup(
					transferVOLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, transferVOLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(transferVOLayout.createSequentialGroup()
									.addGap(0, 259, Short.MAX_VALUE)
									.addComponent(createArrival)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(transferCancel))
								.addGroup(transferVOLayout.createSequentialGroup()
									.addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(label6)
										.addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(label9)
										.addComponent(textField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(transferVOLayout.createParallelGroup()
										.addComponent(label7)
										.addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
											.addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(label10)
											.addComponent(textField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(label8, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
										.addComponent(textField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(label11)
										.addComponent(textField8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(label12)
										.addComponent(textField9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(38, 38, 38)
									.addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)))
							.addContainerGap())
				);
			}
			deliveryVO.addTab("\u8fd0\u8f93\u5355", transferVO);
		}
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	private JTabbedPane startPane;
	private JPanel arrivelistPanel;
	private JScrollPane scrollPane2;
	private JTable arriveListTabble;
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
	private JTable table2;
	private JLabel label1;
	private JTextField transferID;
	private JLabel label2;
	private JTextField from;
	private JLabel label3;
	private JTextField arrivalDate;
	private JComboBox statusBox;
	private JButton modifyStatus;
	private JButton cancelArrival;
	private JButton doArrive;
	private JLabel label4;
	private JLabel label13;
	private JTabbedPane deliveryVO;
	private JPanel transferVO;
	private JScrollPane scrollPane3;
	private JTable orderAndPosition;
	private JLabel label6;
	private JTextField textField3;
	private JLabel label7;
	private JTextField textField4;
	private JLabel label8;
	private JTextField textField5;
	private JLabel label9;
	private JTextField textField6;
	private JLabel label10;
	private JTextField textField7;
	private JLabel label11;
	private JTextField textField8;
	private JLabel label12;
	private JTextField textField9;
	private JButton transferCancel;
	private JButton createArrival;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
