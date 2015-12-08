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
        cancelArrival2 = new JButton();
        deliveryVO = new JTabbedPane();
        transferVO = new JPanel();
        scrollPane3 = new JScrollPane();
        orderAndPosition = new JTable();
        label6 = new JLabel();
        textField3 = new JTextField();
        label7 = new JLabel();
        textField4 = new JTextField();
        label12 = new JLabel();
        textField9 = new JTextField();
        label8 = new JLabel();
        textField5 = new JTextField();
        label9 = new JLabel();
        textField6 = new JTextField();
        label10 = new JLabel();
        textField7 = new JTextField();
        label11 = new JLabel();
        textField8 = new JTextField();
        createArrival = new JButton();
        transferCancel = new JButton();

        //======== this ========
        setLayout(new BorderLayout());

        //======== startPane ========
        {
            startPane.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

            //======== arrivelistPanel ========
            {

                //======== scrollPane2 ========
                {

                    //---- arriveListTabble ----
                    arriveListTabble.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    arriveListTabble.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
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
                selectArrival.setIcon(new ImageIcon(getClass().getResource("/icons/see_24x24.png")));
                selectArrival.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
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
                            .addComponent(selectArrival, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                            .addContainerGap())
                );
                arrivelistPanelLayout.setVerticalGroup(
                    arrivelistPanelLayout.createParallelGroup()
                        .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                        .addGroup(arrivelistPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(selectArrival)
                            .addContainerGap(279, Short.MAX_VALUE))
                );
            }
            startPane.addTab("\u5230\u8fbe\u5355\u5217\u8868", arrivelistPanel);

            //======== searchListPanel ========
            {

                //---- label5 ----
                label5.setText("\u8bf7\u8f93\u5165\u8fd0\u8f93\u5355\u53f7");
                label5.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- deliveryID ----
                deliveryID.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                deliveryID.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        deliveryIDMouseReleased(e);
                    }
                });

                //---- entruck ----
                entruck.setText("\u88c5\u8f66\u5355");
                entruck.setSelected(true);
                entruck.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                entruck.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        entruckMouseClicked(e);
                    }
                });

                //---- transfer ----
                transfer.setText("\u4e2d\u8f6c\u5355");
                transfer.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                transfer.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        transferMouseReleased(e);
                    }
                });

                //---- searchList ----
                searchList.setText("\u67e5\u627e");
                searchList.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
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
                            .addContainerGap(315, Short.MAX_VALUE))
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
                            .addContainerGap(150, Short.MAX_VALUE))
                );
            }
            startPane.addTab("\u641c\u7d22\u8fd0\u8f93\u5355", searchListPanel);
        }
        add(startPane, BorderLayout.CENTER);

        //======== arrivalVO ========
        {
            arrivalVO.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

            //======== panel1 ========
            {
                panel1.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //======== scrollPane1 ========
                {

                    //---- table2 ----
                    table2.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                    scrollPane1.setViewportView(table2);
                }

                //---- label1 ----
                label1.setText("\u8fd0\u8f6c\u5355\u7f16\u53f7\uff1a");
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

                //---- statusBox ----
                statusBox.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- modifyStatus ----
                modifyStatus.setText("\u4fee\u6539\u72b6\u6001");
                modifyStatus.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- cancelArrival ----
                cancelArrival.setText("\u53d6\u6d88");
                cancelArrival.setIcon(new ImageIcon(getClass().getResource("/icons/cancel_24x24.png")));
                cancelArrival.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelArrivalMouseClicked(e);
                        cancelArrivalMouseClicked(e);
                    }
                });

                //---- doArrive ----
                doArrive.setText("\u786e\u8ba4\u5230\u8fbe");
                doArrive.setIcon(new ImageIcon(getClass().getResource("/icons/ok_24x24.png")));
                doArrive.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                doArrive.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        createStorageInMouseClicked(e);
                        doArriveMouseClicked(e);
                    }
                });

                //---- label4 ----
                label4.setText("\u51fa\u53d1\u5730\uff1a");
                label4.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- cancelArrival2 ----
                cancelArrival2.setText("\u53d6\u6d88");
                cancelArrival2.setIcon(new ImageIcon(getClass().getResource("/icons/cancel_24x24.png")));
                cancelArrival2.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                cancelArrival2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelArrivalMouseClicked(e);
                        cancelArrivalMouseClicked(e);
                    }
                });

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addGroup(panel1Layout.createParallelGroup()
                                        .addGroup(panel1Layout.createSequentialGroup()
                                            .addGap(130, 130, 130)
                                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
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
                                            .addGap(18, 18, 18)
                                            .addComponent(statusBox, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(modifyStatus, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
                                .addComponent(scrollPane1))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(doArrive, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                .addComponent(cancelArrival2, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cancelArrival, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addContainerGap())
                );
                panel1Layout.setVerticalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel1Layout.createParallelGroup()
                                .addComponent(statusBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(panel1Layout.createSequentialGroup()
                                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label1)
                                                .addComponent(transferID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label4)
                                                .addComponent(from, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label3)
                                                .addComponent(arrivalDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))
                                        .addGroup(panel1Layout.createSequentialGroup()
                                            .addComponent(modifyStatus, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                            .addGap(4, 4, 4)))
                                    .addComponent(label2)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel1Layout.createParallelGroup()
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addComponent(doArrive)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(cancelArrival)
                                        .addComponent(cancelArrival2))
                                    .addContainerGap(195, Short.MAX_VALUE))
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)))
                );
            }
            arrivalVO.addTab("\u5230\u8fbe\u5355", panel1);
        }

        //======== deliveryVO ========
        {
            deliveryVO.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

            //======== transferVO ========
            {

                //======== scrollPane3 ========
                {

                    //---- orderAndPosition ----
                    orderAndPosition.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                    scrollPane3.setViewportView(orderAndPosition);
                }

                //---- label6 ----
                label6.setText("\u4e2d\u8f6c\u4e2d\u5fc3\uff1a");
                label6.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- textField3 ----
                textField3.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- label7 ----
                label7.setText("\u76ee\u6807\u673a\u6784\uff1a");
                label7.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- textField4 ----
                textField4.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- label12 ----
                label12.setText("\u73ed\u6b21\uff1a");
                label12.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- textField9 ----
                textField9.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- label8 ----
                label8.setText("\u8fd0\u8f93\u65b9\u5f0f\uff1a");
                label8.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- textField5 ----
                textField5.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- label9 ----
                label9.setText("\u76d1\u88c5\u5458\uff1a");
                label9.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- textField6 ----
                textField6.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- label10 ----
                label10.setText("\u8d39\u7528\uff1a");
                label10.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- textField7 ----
                textField7.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- label11 ----
                label11.setText("\u65e5\u671f\uff1a");
                label11.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- textField8 ----
                textField8.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- createArrival ----
                createArrival.setText("\u751f\u6210");
                createArrival.setIcon(new ImageIcon(getClass().getResource("/icons/generate_24x24.png")));
                createArrival.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- transferCancel ----
                transferCancel.setText("\u53d6\u6d88");
                transferCancel.setIcon(new ImageIcon(getClass().getResource("/icons/cancel_24x24.png")));
                transferCancel.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                transferCancel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        transferCancelMouseClicked(e);
                    }
                });

                GroupLayout transferVOLayout = new GroupLayout(transferVO);
                transferVO.setLayout(transferVOLayout);
                transferVOLayout.setHorizontalGroup(
                    transferVOLayout.createParallelGroup()
                        .addGroup(transferVOLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(transferVOLayout.createParallelGroup()
                                .addGroup(transferVOLayout.createSequentialGroup()
                                    .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 679, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(transferVOLayout.createParallelGroup()
                                        .addComponent(createArrival, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(transferCancel, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)))
                                .addGroup(transferVOLayout.createSequentialGroup()
                                    .addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(label7, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(label6, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(label12))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(transferVOLayout.createParallelGroup()
                                        .addComponent(textField4, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textField3, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textField9, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(transferVOLayout.createParallelGroup()
                                        .addComponent(label9, GroupLayout.Alignment.TRAILING)
                                        .addComponent(label10, GroupLayout.Alignment.TRAILING)
                                        .addComponent(label11, GroupLayout.Alignment.TRAILING))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(textField8)
                                        .addComponent(textField7)
                                        .addComponent(textField6, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addComponent(label8)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(textField5, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addContainerGap())
                );
                transferVOLayout.setVerticalGroup(
                    transferVOLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, transferVOLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(transferVOLayout.createParallelGroup()
                                .addGroup(transferVOLayout.createSequentialGroup()
                                    .addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(textField3))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label7, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(textField9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label12, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
                                .addGroup(transferVOLayout.createSequentialGroup()
                                    .addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(textField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label9, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label8, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(textField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label10, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(textField8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label11, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(transferVOLayout.createParallelGroup()
                                .addGroup(transferVOLayout.createSequentialGroup()
                                    .addComponent(createArrival)
                                    .addGap(6, 6, 6)
                                    .addComponent(transferCancel))
                                .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE))
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
    private JButton cancelArrival2;
    private JTabbedPane deliveryVO;
    private JPanel transferVO;
    private JScrollPane scrollPane3;
    private JTable orderAndPosition;
    private JLabel label6;
    private JTextField textField3;
    private JLabel label7;
    private JTextField textField4;
    private JLabel label12;
    private JTextField textField9;
    private JLabel label8;
    private JTextField textField5;
    private JLabel label9;
    private JTextField textField6;
    private JLabel label10;
    private JTextField textField7;
    private JLabel label11;
    private JTextField textField8;
    private JButton createArrival;
    private JButton transferCancel;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
