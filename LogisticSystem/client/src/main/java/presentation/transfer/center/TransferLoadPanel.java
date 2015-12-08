/*
 * Created by JFormDesigner on Mon Nov 30 21:09:47 CST 2015
 */

package presentation.transfer.center;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import data.enums.StorageArea;
import data.po.TransferListPO;
import data.vo.BriefOrderVO;
import data.vo.TransferListVO;
import data.vo.TransferLoadVO;
import businesslogic.impl.transfer.TransferInfo;
import businesslogic.service.Transfer.center.TransferLoadService;

/**
 * @author sunxu
 */
public class TransferLoadPanel extends JPanel {
	TransferLoadService transferLoad;
	TransferLoadVO order;
	TransferListVO transferList;

	public TransferLoadPanel(TransferLoadService transferLoad) {
		this.transferLoad = transferLoad;
		initComponents();
		setTransferTypeBox();
		// 在未选择运输类型和目的地时，本面板上三个按钮均无效
		getOrderButton.setEnabled(false);
		removeOrderButton.setEnabled(false);
		createTransferButton.setEnabled(false);
		destBox.setEnabled(false);
	}

	private void setTransferTypeBox() {
		String[] type = TransferInfo.transferType;
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(
				type);
		transferTypeBox.setModel(model);
		transferTypeBox.setSelectedIndex(0);
		transferTypeBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {// 监听，获取所选项，获取相应的目的地
				if (e.getStateChange() == ItemEvent.SELECTED) {
					int item = transferTypeBox.getSelectedIndex();
					String s = (String) transferTypeBox.getItemAt(item);
					StorageArea type = TransferInfo.getTypeByString(s);
					setDestBox(type);
				}
			}
		});
		transferTypeBox.validate();
		transferTypeBox.updateUI();
		transferTypeBox.repaint();
	}

	private void setDestBox(StorageArea type) {// 根据所选运输类型，获取目的地
		ArrayList<String> des = transferLoad.chooseTransferType(type);
		String[] desArray = new String[des.size()];
		des.toArray(desArray);
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(
				desArray);
		destBox.setModel(model);
		destBox.validate();
		destBox.updateUI();
		destBox.repaint();
		getOrderButton.setEnabled(true);// 获取订单信息按钮生效
		destBox.setEnabled(true);// 目的地列表生效
	}

	private void setOrderList() {
		DefaultTableModel model = new DefaultTableModel(order.getOrderInfo(),
				order.header);
		orderTable.setModel(model);
		orderTable.validate();
		orderTable.updateUI();
		orderTable.repaint();
	}

	private void getOrderButtonMouseReleased(MouseEvent e) {
		if (getOrderButton.isEnabled()) {
			int item = destBox.getSelectedIndex();
			if (item >= 0) {
				String desName = (String) destBox.getItemAt(item);
				order = transferLoad.getOrder(desName);
				if (order != null) {
					setOrderList();
					removeOrderButton.setEnabled(true);// 移除按钮生效
					createTransferButton.setEnabled(true);// 生成中转单按钮生效
				} else {
					JOptionPane.showMessageDialog(null, "未能正确获取仓库信息", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	private void createTransferButtonMouseReleased(MouseEvent e) {
		if (createTransferButton.isEnabled()) {
			if (order == null) {
				JOptionPane.showMessageDialog(null, "未能正确获取仓库信息，无法生成中转单", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String[][] info = order.getOrderInfo();
			if (info.length == 0) {
				JOptionPane.showMessageDialog(null, "仓库内订单为空，无法生成中转单", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			} else {
				transferList = transferLoad.createTransferList(order);
			}
		}
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
        tabbedPane1 = new JTabbedPane();
        loadPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        orderTable = new JTable();
        label1 = new JLabel();
        transferTypeBox = new JComboBox();
        label2 = new JLabel();
        destBox = new JComboBox();
        getOrderButton = new JButton();
        createTransferButton = new JButton();
        removeOrderButton = new JButton();
        tabbedPane2 = new JTabbedPane();
        DeliveryListPanel = new JPanel();
        scrollPane2 = new JScrollPane();
        table1 = new JTable();
        cancelLoad = new JButton();
        saveList = new JButton();

        //======== this ========
        setLayout(new BorderLayout());

        //======== tabbedPane1 ========
        {
            tabbedPane1.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

            //======== loadPanel ========
            {

                //======== scrollPane1 ========
                {

                    //---- orderTable ----
                    orderTable.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                    scrollPane1.setViewportView(orderTable);
                }

                //---- label1 ----
                label1.setText("\u8fd0\u8f93\u7c7b\u578b\uff1a");
                label1.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- transferTypeBox ----
                transferTypeBox.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- label2 ----
                label2.setText("\u76ee\u7684\u5730\uff1a");
                label2.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- destBox ----
                destBox.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- getOrderButton ----
                getOrderButton.setText("\u641c\u7d22\u5e93\u5b58");
                getOrderButton.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                getOrderButton.setIcon(new ImageIcon(getClass().getResource("/icons/search_16x16.png")));
                getOrderButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        getOrderButtonMouseReleased(e);
                    }
                });

                //---- createTransferButton ----
                createTransferButton.setText("\u751f\u6210");
                createTransferButton.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                createTransferButton.setIcon(new ImageIcon(getClass().getResource("/icons/new_24x24.png")));
                createTransferButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        createTransferButtonMouseReleased(e);
                    }
                });

                //---- removeOrderButton ----
                removeOrderButton.setText("\u79fb\u9664");
                removeOrderButton.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                removeOrderButton.setIcon(new ImageIcon(getClass().getResource("/icons/delete_24x24.png")));

                GroupLayout loadPanelLayout = new GroupLayout(loadPanel);
                loadPanel.setLayout(loadPanelLayout);
                loadPanelLayout.setHorizontalGroup(
                    loadPanelLayout.createParallelGroup()
                        .addGroup(loadPanelLayout.createSequentialGroup()
                            .addGroup(loadPanelLayout.createParallelGroup()
                                .addGroup(loadPanelLayout.createSequentialGroup()
                                    .addComponent(label1)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(transferTypeBox, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label2)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(destBox, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(getOrderButton, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 244, Short.MAX_VALUE))
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(loadPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(removeOrderButton, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                                .addComponent(createTransferButton, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                            .addContainerGap())
                );
                loadPanelLayout.setVerticalGroup(
                    loadPanelLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, loadPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(loadPanelLayout.createParallelGroup()
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                .addComponent(transferTypeBox)
                                .addComponent(label2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(loadPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(destBox)
                                    .addComponent(getOrderButton, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(loadPanelLayout.createParallelGroup()
                                .addGroup(loadPanelLayout.createSequentialGroup()
                                    .addComponent(createTransferButton)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(removeOrderButton))
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE))
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
            }
            tabbedPane1.addTab("\u4e2d\u8f6c\u88c5\u8fd0", loadPanel);
        }
        add(tabbedPane1, BorderLayout.CENTER);

        //======== tabbedPane2 ========
        {
            tabbedPane2.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

            //======== DeliveryListPanel ========
            {

                //======== scrollPane2 ========
                {

                    //---- table1 ----
                    table1.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                    scrollPane2.setViewportView(table1);
                }

                //---- cancelLoad ----
                cancelLoad.setText("\u53d6\u6d88");
                cancelLoad.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                cancelLoad.setIcon(new ImageIcon(getClass().getResource("/icons/cancel_24x24.png")));

                //---- saveList ----
                saveList.setText("\u4fdd\u5b58");
                saveList.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                saveList.setIcon(new ImageIcon(getClass().getResource("/icons/save_24x24.png")));

                GroupLayout DeliveryListPanelLayout = new GroupLayout(DeliveryListPanel);
                DeliveryListPanel.setLayout(DeliveryListPanelLayout);
                DeliveryListPanelLayout.setHorizontalGroup(
                    DeliveryListPanelLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, DeliveryListPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(saveList, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                .addComponent(cancelLoad, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                            .addContainerGap())
                );
                DeliveryListPanelLayout.setVerticalGroup(
                    DeliveryListPanelLayout.createParallelGroup()
                        .addGroup(DeliveryListPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(DeliveryListPanelLayout.createParallelGroup()
                                .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                                .addGroup(DeliveryListPanelLayout.createSequentialGroup()
                                    .addComponent(saveList)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 232, Short.MAX_VALUE)
                                    .addComponent(cancelLoad)
                                    .addContainerGap())))
                );
            }
            tabbedPane2.addTab("DeliveryList", DeliveryListPanel);
        }
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
    private JTabbedPane tabbedPane1;
    private JPanel loadPanel;
    private JScrollPane scrollPane1;
    private JTable orderTable;
    private JLabel label1;
    private JComboBox transferTypeBox;
    private JLabel label2;
    private JComboBox destBox;
    private JButton getOrderButton;
    private JButton createTransferButton;
    private JButton removeOrderButton;
    private JTabbedPane tabbedPane2;
    private JPanel DeliveryListPanel;
    private JScrollPane scrollPane2;
    private JTable table1;
    private JButton cancelLoad;
    private JButton saveList;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
