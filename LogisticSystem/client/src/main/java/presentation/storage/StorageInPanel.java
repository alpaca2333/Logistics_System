/*
 * Created by JFormDesigner on Tue Nov 24 22:01:45 CST 2015
 */

package presentation.storage;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import utils.Timestamper;
import data.message.ResultMessage;
import data.vo.ArrivalVO;
import data.vo.BriefArrivalAndStorageInVO;
import data.vo.StorageInVO;
import businesslogic.service.storage.StorageInService;

/**
 * @author sunxu
 */
public class StorageInPanel extends JPanel {
	BriefArrivalAndStorageInVO briefArrivalAndStorageInVO;
	StorageInService storageInService;
	ArrivalVO arrival;
	StorageInVO storageIn;

	
	public StorageInPanel(StorageInService storageIn) {
		this.storageInService = storageIn;
		initComponents();
		setList();
		this.repaint();
	}
	
	

	
	
	
	private void setList(){
		selectArrival.setEnabled(false);
		selectStorageIn.setEnabled(false);
		briefArrivalAndStorageInVO = storageInService.newStorageIn();
		DefaultTableModel storageInModel = new DefaultTableModel(briefArrivalAndStorageInVO.getStorageInListInfo(), briefArrivalAndStorageInVO.getStorageInTittle());
		storageInTable.setModel(storageInModel);
		DefaultTableModel arrivalModel = new DefaultTableModel(briefArrivalAndStorageInVO.getArrivalListInfo(),briefArrivalAndStorageInVO.getArrivalTittle());
		storageInTable.setModel(storageInModel);
		arriveListTable.setModel(arrivalModel);
		
		storageInTable.updateUI();
		arriveListTable.updateUI();
		storageInTable.repaint();
		arriveListTable.repaint();
		if(storageInVO != null)
		remove(storageInVO);
		if(arrivalVO != null)
		remove(arrivalVO);
		add(listPane,BorderLayout.CENTER);
		
		listPane.validate();
		listPane.updateUI();
		this.repaint();
	}

	private void setStorageIn(){
		storageDate.setText(storageIn.getDate());
		DefaultTableModel model = new DefaultTableModel(storageIn.getInfo(),storageIn.getHeader());
		storageOrder.setModel(model);
		storageOrder.updateUI();
		storageOrder.repaint();
		storageInVO.validate();
		storageInVO.updateUI();
		
		remove(listPane);
		remove(arrivalVO);
		add(storageInVO,BorderLayout.CENTER);
		
		storageInVO.setVisible(true);
	}
	
	private void setArrival(ArrivalVO vo) {
		DefaultTableModel model = new DefaultTableModel(vo.getOrderAndStatus(),vo.getHeader());
		arrivalTable.setModel(model);
		arrivalTable.updateUI();
		transferID.setText(vo.getDeliveryListNum());
		from.setText(vo.getFromName());
		arrivalDate.setText(vo.getDate());
		remove(listPane);
		remove(storageInVO);
		add(arrivalVO);
		
		arrivalVO.validate();
		arrivalVO.updateUI();
		this.repaint();
	}

	private void selectStorageInMouseClicked(MouseEvent e) {
		int row = storageInTable.getSelectedRow();
		String info = (String) storageInTable.getValueAt(row, 0);
		long storageInID = Long.parseLong(info);
		storageIn = storageInService.getStorageIn(storageInID);
		setStorageIn();
		

	}
	
	

	private void arriveListTableMouseClicked(MouseEvent e) {
		selectArrival.setEnabled(true);
	}

	private void selectArrivalMouseClicked(MouseEvent e) {
		int row = arriveListTable.getSelectedRow();
		String info = (String) arriveListTable.getValueAt(row, 0);
		System.out.println(info);
		long arrivalID = Long.parseLong(info);
		arrival = storageInService.getArriveList(arrivalID);
		setArrival(arrival);
		

	}

	private void createStorageInMouseClicked(MouseEvent e) {
		storageIn = storageInService.sort(arrival);
		storageIn.setDate(Timestamper.getTimeByDate());
		setStorageIn();		
		
		storageInVO.validate();
		storageInVO.updateUI();
		this.setVisible(true);
	}

	private void storageInTableMouseClicked(MouseEvent e) {
		selectStorageIn.setEnabled(true);
	}

	private void doStorageInMouseClicked(MouseEvent e) {
	}

	private void cancelArrivalMouseClicked(MouseEvent e) {
		remove(arrivalVO);
		add(listPane);
		
		listPane.setVisible(true);
		this.updateUI();
		this.repaint();
	}

	private void saveStorageInMouseReleased(MouseEvent e) {
		ResultMessage result = storageInService.saveStorageInList(storageIn);
		if (result == ResultMessage.SUCCESS) {
			JOptionPane.showMessageDialog(null, "保存成功", "提示", JOptionPane.INFORMATION_MESSAGE);
			DefaultTableModel model = (DefaultTableModel) arrivalTable.getModel();
			model.removeRow(arriveListTable.getSelectedRow());
			arriveListTable.updateUI();
			remove(storageInVO);
			add(listPane,BorderLayout.CENTER);
			listPane.updateUI();
			listPane.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(null, "操作失败", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		listPane = new JTabbedPane();
		storageInList = new JPanel();
		scrollPane4 = new JScrollPane();
		storageInTable = new JTable();
		doStorageIn = new JButton();
		selectStorageIn = new JButton();
		arriveList = new JPanel();
		scrollPane3 = new JScrollPane();
		arriveListTable = new JTable();
		arrivalID = new JTextField();
		search = new JButton();
		selectArrival = new JButton();
		storageInVO = new JTabbedPane();
		storageInPanel = new JPanel();
		scrollPane2 = new JScrollPane();
		storageOrder = new JTable();
		label10 = new JLabel();
		storageDate = new JTextField();
		cancelStorageIn = new JButton();
		saveStorageIn = new JButton();
		arrivalVO = new JTabbedPane();
		panel1 = new JPanel();
		scrollPane1 = new JScrollPane();
		arrivalTable = new JTable();
		label1 = new JLabel();
		transferID = new JTextField();
		label2 = new JLabel();
		from = new JTextField();
		label3 = new JLabel();
		arrivalDate = new JTextField();
		status = new JComboBox();
		modifyStatus = new JButton();
		label4 = new JLabel();
		createStorageIn = new JButton();
		cancelArrival = new JButton();

		//======== this ========
		setLayout(new BorderLayout());

		//======== listPane ========
		{
			listPane.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

			//======== storageInList ========
			{

				//======== scrollPane4 ========
				{

					//---- storageInTable ----
					storageInTable.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
					storageInTable.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							storageInTableMouseClicked(e);
							storageInTableMouseClicked(e);
						}
					});
					scrollPane4.setViewportView(storageInTable);
				}

				//---- doStorageIn ----
				doStorageIn.setText("\u5165\u5e93");
				doStorageIn.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
				doStorageIn.setIcon(new ImageIcon(getClass().getResource("/icons/storagein_24x24.png")));
				doStorageIn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						doStorageInMouseClicked(e);
					}
				});

				//---- selectStorageIn ----
				selectStorageIn.setText("\u9009\u62e9");
				selectStorageIn.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
				selectStorageIn.setIcon(new ImageIcon(getClass().getResource("/icons/ok_24x24.png")));
				selectStorageIn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						selectStorageInMouseClicked(e);
						selectStorageInMouseClicked(e);
						selectStorageInMouseClicked(e);
					}
				});

				GroupLayout storageInListLayout = new GroupLayout(storageInList);
				storageInList.setLayout(storageInListLayout);
				storageInListLayout.setHorizontalGroup(
					storageInListLayout.createParallelGroup()
						.addGroup(storageInListLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 638, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(storageInListLayout.createParallelGroup()
								.addComponent(selectStorageIn, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
								.addComponent(doStorageIn, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
							.addContainerGap())
				);
				storageInListLayout.setVerticalGroup(
					storageInListLayout.createParallelGroup()
						.addGroup(storageInListLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(storageInListLayout.createParallelGroup()
								.addComponent(scrollPane4, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
								.addGroup(storageInListLayout.createSequentialGroup()
									.addComponent(selectStorageIn)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(doStorageIn)
									.addGap(0, 234, Short.MAX_VALUE)))
							.addContainerGap())
				);
			}
			listPane.addTab("\u5df2\u5ba1\u6279\u5165\u5e93\u5355", storageInList);

			//======== arriveList ========
			{

				//======== scrollPane3 ========
				{

					//---- arriveListTable ----
					arriveListTable.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							arriveListTableMouseClicked(e);
							arriveListTableMouseClicked(e);
							arriveListTableMouseClicked(e);
						}
					});
					scrollPane3.setViewportView(arriveListTable);
				}

				//---- search ----
				search.setText("search");

				//---- selectArrival ----
				selectArrival.setText("select");
				selectArrival.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						selectArrivalMouseClicked(e);
					}
				});

				GroupLayout arriveListLayout = new GroupLayout(arriveList);
				arriveList.setLayout(arriveListLayout);
				arriveListLayout.setHorizontalGroup(
					arriveListLayout.createParallelGroup()
						.addGroup(arriveListLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(arrivalID, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(search)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 499, Short.MAX_VALUE)
							.addComponent(selectArrival, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addComponent(scrollPane3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
				);
				arriveListLayout.setVerticalGroup(
					arriveListLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, arriveListLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(arriveListLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(arrivalID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(search)
								.addComponent(selectArrival))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
							.addContainerGap())
				);
			}
			listPane.addTab("\u5230\u8fbe\u5355", arriveList);
		}
		add(listPane, BorderLayout.CENTER);

		//======== storageInVO ========
		{
			storageInVO.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

			//======== storageInPanel ========
			{

				//======== scrollPane2 ========
				{

					//---- storageOrder ----
					storageOrder.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
					scrollPane2.setViewportView(storageOrder);
				}

				//---- label10 ----
				label10.setText("\u65e5\u671f\uff1a");
				label10.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- storageDate ----
				storageDate.setEditable(false);
				storageDate.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- cancelStorageIn ----
				cancelStorageIn.setText("\u53d6\u6d88");
				cancelStorageIn.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
				cancelStorageIn.setIcon(new ImageIcon(getClass().getResource("/icons/cancel_24x24.png")));

				//---- saveStorageIn ----
				saveStorageIn.setText("\u4fdd\u5b58");
				saveStorageIn.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
				saveStorageIn.setIcon(new ImageIcon(getClass().getResource("/icons/save_24x24.png")));
				saveStorageIn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						saveStorageInMouseReleased(e);
					}
				});

				GroupLayout storageInPanelLayout = new GroupLayout(storageInPanel);
				storageInPanel.setLayout(storageInPanelLayout);
				storageInPanelLayout.setHorizontalGroup(
					storageInPanelLayout.createParallelGroup()
						.addGroup(storageInPanelLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(storageInPanelLayout.createParallelGroup()
								.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 785, Short.MAX_VALUE)
								.addGroup(storageInPanelLayout.createSequentialGroup()
									.addComponent(label10)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(storageDate, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
									.addGap(409, 409, 409)
									.addComponent(saveStorageIn, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(cancelStorageIn, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
									.addContainerGap())))
				);
				storageInPanelLayout.setVerticalGroup(
					storageInPanelLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, storageInPanelLayout.createSequentialGroup()
							.addGroup(storageInPanelLayout.createParallelGroup()
								.addGroup(storageInPanelLayout.createSequentialGroup()
									.addContainerGap()
									.addGroup(storageInPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(label10, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
										.addComponent(storageDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(storageInPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(cancelStorageIn)
									.addComponent(saveStorageIn)))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
							.addContainerGap())
				);
			}
			storageInVO.addTab("\u5165\u5e93\u5355", storageInPanel);
		}

		//======== arrivalVO ========
		{

			//======== panel1 ========
			{

				//======== scrollPane1 ========
				{

					//---- arrivalTable ----
					arrivalTable.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
					scrollPane1.setViewportView(arrivalTable);
				}

				//---- label1 ----
				label1.setText("\u8fd0\u8f93\u5355\u7f16\u53f7\uff1a");
				label1.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- transferID ----
				transferID.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- from ----
				from.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- label3 ----
				label3.setText("\u65e5\u671f\uff1a");
				label3.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- arrivalDate ----
				arrivalDate.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- status ----
				status.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- modifyStatus ----
				modifyStatus.setText("\u4fee\u6539\u72b6\u6001");
				modifyStatus.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
				modifyStatus.setIcon(new ImageIcon(getClass().getResource("/icons/search_16x16.png")));

				//---- label4 ----
				label4.setText("\u51fa\u53d1\u5730\uff1a");
				label4.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- createStorageIn ----
				createStorageIn.setText("\u5165\u5e93");
				createStorageIn.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
				createStorageIn.setIcon(new ImageIcon(getClass().getResource("/icons/new_24x24.png")));
				createStorageIn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						createStorageInMouseClicked(e);
					}
				});

				//---- cancelArrival ----
				cancelArrival.setText("\u53d6\u6d88");
				cancelArrival.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
				cancelArrival.setIcon(new ImageIcon(getClass().getResource("/icons/cancel_24x24.png")));
				cancelArrival.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						cancelArrivalMouseClicked(e);
					}
				});

				GroupLayout panel1Layout = new GroupLayout(panel1);
				panel1.setLayout(panel1Layout);
				panel1Layout.setHorizontalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
							.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(panel1Layout.createSequentialGroup()
									.addContainerGap()
									.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE))
								.addGroup(panel1Layout.createSequentialGroup()
									.addContainerGap(16, Short.MAX_VALUE)
									.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addGroup(panel1Layout.createSequentialGroup()
											.addGap(138, 138, 138)
											.addComponent(label2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGroup(panel1Layout.createSequentialGroup()
											.addComponent(label1)
											.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(transferID, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(label4)
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(from, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(label3)
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(arrivalDate, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(status, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(modifyStatus, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)))))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel1Layout.createParallelGroup()
								.addComponent(cancelArrival, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
								.addComponent(createStorageIn, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())
				);
				panel1Layout.setVerticalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label1)
								.addComponent(transferID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label4)
								.addComponent(from, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label3)
								.addComponent(arrivalDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(status)
								.addComponent(modifyStatus, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label2)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel1Layout.createParallelGroup()
								.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE)
								.addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
									.addComponent(createStorageIn)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(cancelArrival)))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
			}
			arrivalVO.addTab("\u5230\u8fbe\u5355", panel1);
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JTabbedPane listPane;
	private JPanel storageInList;
	private JScrollPane scrollPane4;
	private JTable storageInTable;
	private JButton doStorageIn;
	private JButton selectStorageIn;
	private JPanel arriveList;
	private JScrollPane scrollPane3;
	private JTable arriveListTable;
	private JTextField arrivalID;
	private JButton search;
	private JButton selectArrival;
	private JTabbedPane storageInVO;
	private JPanel storageInPanel;
	private JScrollPane scrollPane2;
	private JTable storageOrder;
	private JLabel label10;
	private JTextField storageDate;
	private JButton cancelStorageIn;
	private JButton saveStorageIn;
	private JTabbedPane arrivalVO;
	private JPanel panel1;
	private JScrollPane scrollPane1;
	private JTable arrivalTable;
	private JLabel label1;
	private JTextField transferID;
	private JLabel label2;
	private JTextField from;
	private JLabel label3;
	private JTextField arrivalDate;
	private JComboBox status;
	private JButton modifyStatus;
	private JLabel label4;
	private JButton createStorageIn;
	private JButton cancelArrival;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
