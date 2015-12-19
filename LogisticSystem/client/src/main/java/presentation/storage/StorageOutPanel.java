/*
 * Created by JFormDesigner on Tue Nov 24 22:44:48 CST 2015
 */

package presentation.storage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import data.vo.BriefTransferAndStorageOutVO;
import businesslogic.service.storage.StorageOutService;

/**
 * @author sunxu
 */
public class StorageOutPanel extends JPanel {
	StorageOutService storageOut;
	BriefTransferAndStorageOutVO briefTransferAndStorageOutVO;
	public StorageOutPanel(StorageOutService storageOut) {
		this.storageOut	 = storageOut;
		initComponents();
		setList();
		this.setVisible(true);
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

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		tabbedPane1 = new JTabbedPane();
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
		tabbedPane2 = new JTabbedPane();
		transferVO = new JPanel();
		scrollPane3 = new JScrollPane();
		orderAndPosition = new JTable();
		transferCancel = new JButton();
		createStorageOut = new JButton();
		tabbedPane3 = new JTabbedPane();
		storageOutVO = new JPanel();
		scrollPane4 = new JScrollPane();
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

		//======== this ========
		setLayout(new BorderLayout());

		//======== tabbedPane1 ========
		{
			tabbedPane1.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

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
							.addContainerGap()
							.addGroup(storageOutListLayout.createParallelGroup()
								.addGroup(storageOutListLayout.createSequentialGroup()
									.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 685, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(storageOutListLayout.createParallelGroup()
										.addComponent(doStorageOut, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(selectStorageOut, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addGroup(storageOutListLayout.createSequentialGroup()
									.addComponent(textField1, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(button3, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
									.addGap(0, 0, Short.MAX_VALUE)))
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
							.addGroup(storageOutListLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(storageOutListLayout.createSequentialGroup()
									.addComponent(selectStorageOut)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(doStorageOut)
									.addGap(0, 219, Short.MAX_VALUE))
								.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE))
							.addContainerGap())
				);
			}
			tabbedPane1.addTab("\u5df2\u5ba1\u6279\u5165\u5e93\u5355", storageOutList);

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

				GroupLayout transferListLayout = new GroupLayout(transferList);
				transferList.setLayout(transferListLayout);
				transferListLayout.setHorizontalGroup(
					transferListLayout.createParallelGroup()
						.addGroup(transferListLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(transferListLayout.createParallelGroup()
								.addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 685, GroupLayout.PREFERRED_SIZE)
								.addGroup(transferListLayout.createSequentialGroup()
									.addComponent(textField2, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(searchTransfer)))
							.addGap(2147482940, 2147482940, 2147482940)
							.addComponent(selectTransfer, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
							.addContainerGap())
				);
				transferListLayout.setVerticalGroup(
					transferListLayout.createParallelGroup()
						.addGroup(transferListLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(transferListLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(searchTransfer, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
							.addGap(14, 14, 14)
							.addGroup(transferListLayout.createParallelGroup()
								.addGroup(transferListLayout.createSequentialGroup()
									.addGap(0, 0, Short.MAX_VALUE)
									.addComponent(selectTransfer, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
								.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
							.addContainerGap())
				);
			}
			tabbedPane1.addTab("\u5df2\u5ba1\u6279\u4e2d\u8f6c\u5355", transferList);
		}
		add(tabbedPane1, BorderLayout.CENTER);

		//======== tabbedPane2 ========
		{
			tabbedPane2.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

			//======== transferVO ========
			{

				//======== scrollPane3 ========
				{

					//---- orderAndPosition ----
					orderAndPosition.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
					scrollPane3.setViewportView(orderAndPosition);
				}

				//---- transferCancel ----
				transferCancel.setText("\u53d6\u6d88");
				transferCancel.setIcon(new ImageIcon(getClass().getResource("/icons/cancel_24x24.png")));
				transferCancel.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				//---- createStorageOut ----
				createStorageOut.setText("\u65b0\u5efa");
				createStorageOut.setIcon(new ImageIcon(getClass().getResource("/icons/new_24x24.png")));
				createStorageOut.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

				GroupLayout transferVOLayout = new GroupLayout(transferVO);
				transferVO.setLayout(transferVOLayout);
				transferVOLayout.setHorizontalGroup(
					transferVOLayout.createParallelGroup()
						.addGroup(transferVOLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 679, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(transferVOLayout.createParallelGroup()
								.addComponent(createStorageOut, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
								.addComponent(transferCancel, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
							.addContainerGap())
				);
				transferVOLayout.setVerticalGroup(
					transferVOLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, transferVOLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(transferVOLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(transferVOLayout.createSequentialGroup()
									.addComponent(createStorageOut)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 240, Short.MAX_VALUE)
									.addComponent(transferCancel))
								.addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE))
							.addContainerGap())
				);
			}
			tabbedPane2.addTab("\u4e2d\u8f6c\u5355", transferVO);
		}

		//======== tabbedPane3 ========
		{
			tabbedPane3.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

			//======== storageOutVO ========
			{

				//======== scrollPane4 ========
				{
					scrollPane4.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
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
							.addContainerGap()
							.addGroup(storageOutVOLayout.createParallelGroup()
								.addGroup(storageOutVOLayout.createSequentialGroup()
									.addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 680, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(storageOutVOLayout.createParallelGroup()
										.addComponent(storageOutCancel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(storageOutSave, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addGroup(storageOutVOLayout.createSequentialGroup()
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
									.addGap(0, 0, Short.MAX_VALUE)))
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
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(storageOutVOLayout.createParallelGroup()
								.addGroup(storageOutVOLayout.createSequentialGroup()
									.addComponent(storageOutSave)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
									.addComponent(storageOutCancel))
								.addComponent(scrollPane4, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
							.addContainerGap())
				);
			}
			tabbedPane3.addTab("\u51fa\u5e93\u5355", storageOutVO);
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JTabbedPane tabbedPane1;
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
	private JTabbedPane tabbedPane2;
	private JPanel transferVO;
	private JScrollPane scrollPane3;
	private JTable orderAndPosition;
	private JButton transferCancel;
	private JButton createStorageOut;
	private JTabbedPane tabbedPane3;
	private JPanel storageOutVO;
	private JScrollPane scrollPane4;
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
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
