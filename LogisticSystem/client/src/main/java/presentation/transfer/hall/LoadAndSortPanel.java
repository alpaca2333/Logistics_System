/*
 * Created by JFormDesigner on Mon Nov 30 23:38:21 CST 2015
 */

package presentation.transfer.hall;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import data.message.ResultMessage;
import data.vo.BriefEntruckListVO;
import data.vo.BriefOrderVO;
import data.vo.EntruckListVO;
import businesslogic.service.Transfer.hall.LoadAndSortService;

/**
 * @author sunxu
 */
public class LoadAndSortPanel extends JPanel {
	LoadAndSortService loadAndSort;
	BriefOrderVO briefOrder;
	BriefEntruckListVO briefEntruckList;
	EntruckListVO entruck;
	int entruckListCounter = 0;
	
	
	public boolean isClear(){
		if(entruckListCounter>0){
			return false;
		}else{
			return true;
		}
	}

	public LoadAndSortPanel(LoadAndSortService loadAndSort)
			throws RemoteException {
		this.loadAndSort = loadAndSort;
		initComponents();
		selectEntruck.setEnabled(false);
		removeOrder.setEnabled(false);
		createEntruck.setEnabled(false);
		setDestination();
		setEntruckList();
		this.setVisible(true);
	}

	// =========================设置界面====================================================
	// 设置目的地comboBox
	private void setDestination() throws RemoteException {

		String[] des = null;
		des = loadAndSort.getDestination();
		if (des != null) {// 目的地信息获取成功时显示，不成功则不显示
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(des);
			comboBox1.setModel(model);
			comboBox1.validate();
			comboBox1.updateUI();
		} else {
			return;
		}
	}

	// 设置装车单
	private void setEntruck() {
		if (entruck != null) {
			// 设置装车单 订单列表
			DefaultTableModel model = new DefaultTableModel(entruck.info,
					entruck.header);
			entruckDate.setText(entruck.loadingDate);
			entruckTable.setModel(model);
			listID.setText("保存后生成");
			hallID.setText(entruck.fromID);
			hallName.setText(entruck.fromName);
			destID.setText(entruck.destID + "");
			destName.setText(entruck.destName);
			truckID.setText(entruck.vehicleID);
			staffName.setText(entruck.monitorName);
			driverName.setText(entruck.escortName);
			entruckTable.validate();
			entruckTable.updateUI();
			remove(loadAndSortPane);
			add(entruckVO);

			entruckVO.validate();
			entruckVO.updateUI();
			entruckVO.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "未查找到装车单信息", "异常",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// 设置装车单不可编辑
	private void setDisabled() {
		entruckTable.setEnabled(false);
		listID.setEnabled(false);
		hallID.setEnabled(false);
		hallName.setEnabled(false);
		destID.setEnabled(false);
		destName.setEnabled(false);
		truckID.setEnabled(false);
		staffName.setEnabled(false);
		driverName.setEnabled(false);
		doEntruck.setVisible(true);
		entruckDate.setEnabled(false);
	}

	// 设置显示装车单
	private void setEntruckList() {
		try {
			if (briefEntruckList == null)
				briefEntruckList = loadAndSort.getEntruckList();
		} catch (RemoteException e) {
			e.printStackTrace();
			errorDialog.setVisible(true);
		}
		if (briefEntruckList != null) {// 为空则获取失败
			DefaultTableModel model = new DefaultTableModel(
					briefEntruckList.info, briefEntruckList.header);
			entruckListCounter = briefEntruckList.info.length;
			entruckListTable.setModel(model);
			entruckListTable.validate();
			entruckListTable.updateUI();
			entruckListTable.setVisible(true);
		}else{
			entruckListTable.setEnabled(false);
		}
	}

	// =========================监听==================================================
	private void cancelLoadMouseClicked(MouseEvent e) {
		remove(entruckVO);
		add(loadAndSortPane);

		loadAndSortPane.validate();
		loadAndSortPane.updateUI();
		loadAndSortPane.setVisible(true);
	}

	private void errorSureMouseClicked(MouseEvent e) {
		errorDialog.setVisible(false);
	}

	private void sortButtonMouseClicked(MouseEvent e) {
		int index = comboBox1.getSelectedIndex();
		String des = (String) comboBox1.getItemAt(index);
		System.out.println(des);
		briefOrder = loadAndSort.chooseDestination(des);
		DefaultTableModel model = new DefaultTableModel();
		if(briefOrder != null){
		model.setDataVector(briefOrder.info, briefOrder.header);
		orderTable.setModel(model);
		orderTable.setEnabled(true);
		orderTable.updateUI();
		orderTable.setVisible(true);
		removeOrder.setEnabled(true);
		createEntruck.setEnabled(true);
		}else{
			orderTable.setEnabled(false);
		}
	}

	private void createEntruckMouseClicked(MouseEvent e) {
		// vector 转 string[][] 暂时这么着
		if(orderTable.isEnabled()){
		Vector<Vector<String>> v = briefOrder.info;
		if(v.size() == 0){//订单列表长度为0时，只提示
			JOptionPane.showMessageDialog(null, "没有可装车订单","提示",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String[][] info = new String[v.size()][BriefOrderVO.getColumnNum()];
		for (int i = 0; i < v.size(); i++) {
			Vector<String> in = v.get(i);
			String[] ins = new String[in.size()];
			ins[0] = in.get(0);
			ins[1] = in.get(1);
			info[i] = ins;
		}
		try {
			entruck = loadAndSort.createEntruckList(info);
			if (entruck == null) {
				JOptionPane.showMessageDialog(null, "请检查本营业厅是否有司机和车辆", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "网络连接中断", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
		doEntruck.setVisible(false);
		doEntruck.setEnabled(false);
		saveEntruck.setVisible(true);
		saveEntruck.setEnabled(true);
		setEntruck();
		setDisabled();
		}
	}

	private void saveEntruckMouseClicked(MouseEvent e) {
		try {
			ResultMessage result = loadAndSort.saveEntruckList(entruck);
			if (result == ResultMessage.SUCCESS) {
				JOptionPane.showMessageDialog(null, "保存成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				DefaultTableModel model = new DefaultTableModel();
				orderTable.setModel(model);
				remove(entruckVO);
				add(loadAndSortPane, BorderLayout.CENTER);
				loadAndSortPane.updateUI();
				loadAndSortPane.setVisible(true);
			}else {
				JOptionPane.showMessageDialog(null, "操作失败", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
			errorDialog.setVisible(true);
		}
	}

	private void selectEntruckMouseClicked(MouseEvent e) {
		if (entruckListTable.isEnabled()) {
			int row = entruckListTable.getSelectedRow();
			String id = (String) entruckListTable.getValueAt(row, 0);
			entruck = loadAndSort.chooseEntruckList(Long.parseLong(id));
			doEntruck.setVisible(true);
			doEntruck.setEnabled(true);
			saveEntruck.setVisible(false);
			saveEntruck.setEnabled(false);
			setEntruck();
			setDisabled();
			entruckDate.setEnabled(false);
		}
	}

	private void doEntruckMouseClicked(MouseEvent e) {
		ResultMessage result = ResultMessage.FAILED;
		try {
			result = loadAndSort.doEntruck();
		
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		if (result == ResultMessage.SUCCESS) {
			JOptionPane.showMessageDialog(null, "操作成功", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			int row = entruckListTable.getSelectedRow();
			DefaultTableModel model = (DefaultTableModel) entruckListTable.getModel();
			model.removeRow(row);
			entruckListCounter--;
			entruckListTable.updateUI();
			entruckListTable.repaint();
			
			remove(entruckVO);
			add(loadAndSortPane,BorderLayout.CENTER);
			loadAndSortPane.updateUI();
			loadAndSortPane.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "操作失败,请稍后再试", "提示",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void resultSureMouseClicked(MouseEvent e) {
		setEntruckList();
		resultDialog.setVisible(false);
	}

	private void entruckListTableMouseReleased(MouseEvent e) {
		selectEntruck.setEnabled(true);
	}

	private void orderTableMouseReleased(MouseEvent e) {
		removeOrder.setEnabled(true);
	}

	// 删除选中订单项
	private void removeOrderMouseReleased(MouseEvent e) {
		if(orderTable.isEnabled()){//订单列表有效时才生效
		int[] rows = orderTable.getSelectedRows();
		if (rows.length == 0) {
			JOptionPane.showMessageDialog(null, "未选中订单","提示",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Vector<Vector<String>> v = briefOrder.info;
		for (int i = (rows.length-1); i>= 0; i--) {
			v.remove(rows[i]);
		}
		DefaultTableModel model = new DefaultTableModel(briefOrder.info,
				briefOrder.header);
		orderTable.setModel(model);
		orderTable.updateUI();
		orderTable.repaint();
		}
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		loadAndSortPane = new JTabbedPane();
		entruckListPanel = new JPanel();
		selectEntruck = new JButton();
		scrollPane3 = new JScrollPane();
		entruckListTable = new JTable();
		panel1 = new JPanel();
		scrollPane1 = new JScrollPane();
		orderTable = new JTable();
		label1 = new JLabel();
		comboBox1 = new JComboBox();
		sortButton = new JButton();
		createEntruck = new JButton();
		removeOrder = new JButton();
		entruckVO = new JTabbedPane();
		DeliveryListPanel = new JPanel();
		scrollPane2 = new JScrollPane();
		entruckTable = new JTable();
		cancelLoad = new JButton();
		saveEntruck = new JButton();
		doEntruck = new JButton();
		label5 = new JLabel();
		label6 = new JLabel();
		label7 = new JLabel();
		label8 = new JLabel();
		label9 = new JLabel();
		listID = new JTextField();
		hallID = new JTextField();
		hallName = new JTextField();
		destID = new JTextField();
		destName = new JTextField();
		label10 = new JLabel();
		label11 = new JLabel();
		label12 = new JLabel();
		truckID = new JTextField();
		staffName = new JTextField();
		driverName = new JTextField();
		label4 = new JLabel();
		entruckDate = new JTextField();
		errorDialog = new JDialog();
		panel2 = new JPanel();
		label2 = new JLabel();
		errorSure = new JButton();
		resultDialog = new JDialog();
		panel3 = new JPanel();
		label3 = new JLabel();
		resultSure = new JButton();

		//======== this ========
		setLayout(new BorderLayout());

		//======== loadAndSortPane ========
		{

			//======== entruckListPanel ========
			{

				//---- selectEntruck ----
				selectEntruck.setText("\u67e5\u770b");
				selectEntruck.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						selectEntruckMouseClicked(e);
					}
				});

				//======== scrollPane3 ========
				{

					//---- entruckListTable ----
					entruckListTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
					entruckListTable.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							entruckListTableMouseReleased(e);
						}
					});
					scrollPane3.setViewportView(entruckListTable);
				}

				GroupLayout entruckListPanelLayout = new GroupLayout(entruckListPanel);
				entruckListPanel.setLayout(entruckListPanelLayout);
				entruckListPanelLayout.setHorizontalGroup(
					entruckListPanelLayout.createParallelGroup()
						.addGroup(entruckListPanelLayout.createSequentialGroup()
							.addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 698, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(selectEntruck, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
							.addContainerGap())
				);
				entruckListPanelLayout.setVerticalGroup(
					entruckListPanelLayout.createParallelGroup()
						.addGroup(entruckListPanelLayout.createSequentialGroup()
							.addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 346, GroupLayout.PREFERRED_SIZE)
							.addGap(0, 0, Short.MAX_VALUE))
						.addGroup(GroupLayout.Alignment.TRAILING, entruckListPanelLayout.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(selectEntruck)
							.addContainerGap())
				);
			}
			loadAndSortPane.addTab("\u5df2\u5ba1\u6279\u88c5\u8f66\u5355", entruckListPanel);

			//======== panel1 ========
			{

				//======== scrollPane1 ========
				{

					//---- orderTable ----
					orderTable.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							orderTableMouseReleased(e);
						}
					});
					scrollPane1.setViewportView(orderTable);
				}

				//---- label1 ----
				label1.setText("\u76ee\u7684\u5730");

				//---- sortButton ----
				sortButton.setText("\u5206\u62e3");
				sortButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						sortButtonMouseClicked(e);
					}
				});

				//---- createEntruck ----
				createEntruck.setText("\u751f\u6210\u88c5\u8f66\u5355");
				createEntruck.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						createEntruckMouseClicked(e);
					}
				});

				//---- removeOrder ----
				removeOrder.setText("\u79fb\u9664");
				removeOrder.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						removeOrderMouseReleased(e);
					}
				});

				GroupLayout panel1Layout = new GroupLayout(panel1);
				panel1.setLayout(panel1Layout);
				panel1Layout.setHorizontalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(panel1Layout.createParallelGroup()
								.addGroup(panel1Layout.createSequentialGroup()
									.addComponent(label1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(sortButton)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(removeOrder)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 405, Short.MAX_VALUE)
									.addComponent(createEntruck))
								.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE))
							.addContainerGap())
				);
				panel1Layout.setVerticalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label1)
								.addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(sortButton)
								.addComponent(createEntruck)
								.addComponent(removeOrder))
							.addGap(10, 10, 10)
							.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE))
				);
			}
			loadAndSortPane.addTab("\u5206\u62e3\u88c5\u8f66", panel1);
		}
		add(loadAndSortPane, BorderLayout.CENTER);

		//======== entruckVO ========
		{

			//======== DeliveryListPanel ========
			{

				//======== scrollPane2 ========
				{

					//---- entruckTable ----
					entruckTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
					scrollPane2.setViewportView(entruckTable);
				}

				//---- cancelLoad ----
				cancelLoad.setText("\u53d6\u6d88");
				cancelLoad.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						cancelLoadMouseClicked(e);
					}
				});

				//---- saveEntruck ----
				saveEntruck.setText("\u4fdd\u5b58");
				saveEntruck.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						saveEntruckMouseClicked(e);
					}
				});

				//---- doEntruck ----
				doEntruck.setText("\u88c5\u8f66");
				doEntruck.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						doEntruckMouseClicked(e);
					}
				});

				//---- label5 ----
				label5.setText("\u8425\u4e1a\u5385\u7f16\u53f7");

				//---- label6 ----
				label6.setText("\u6c7d\u8fd0\u7f16\u53f7");

				//---- label7 ----
				label7.setText("\u76ee\u7684\u5730");

				//---- label8 ----
				label8.setText("\u76ee\u7684\u5730\u7f16\u53f7");

				//---- label9 ----
				label9.setText("\u8425\u4e1a\u5385\u540d");

				//---- label10 ----
				label10.setText("\u8f66\u8f86\u4ee3\u53f7");

				//---- label11 ----
				label11.setText("\u76d1\u88c5\u5458");

				//---- label12 ----
				label12.setText("\u62bc\u8fd0\u5458");

				//---- label4 ----
				label4.setText("\u88c5\u8f66\u65e5\u671f");

				GroupLayout DeliveryListPanelLayout = new GroupLayout(DeliveryListPanel);
				DeliveryListPanel.setLayout(DeliveryListPanelLayout);
				DeliveryListPanelLayout.setHorizontalGroup(
					DeliveryListPanelLayout.createParallelGroup()
						.addComponent(scrollPane2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
						.addGroup(DeliveryListPanelLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(DeliveryListPanelLayout.createParallelGroup()
								.addGroup(DeliveryListPanelLayout.createSequentialGroup()
									.addGroup(DeliveryListPanelLayout.createParallelGroup()
										.addComponent(label8)
										.addComponent(label7))
									.addGap(18, 18, 18)
									.addGroup(DeliveryListPanelLayout.createParallelGroup()
										.addComponent(destName)
										.addComponent(destID)))
								.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
									.addGroup(DeliveryListPanelLayout.createSequentialGroup()
										.addComponent(label6)
										.addGap(30, 30, 30)
										.addComponent(listID, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
									.addGroup(DeliveryListPanelLayout.createSequentialGroup()
										.addComponent(label5)
										.addGap(18, 18, 18)
										.addComponent(hallID, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
									.addGroup(DeliveryListPanelLayout.createSequentialGroup()
										.addComponent(label9)
										.addGap(30, 30, 30)
										.addComponent(hallName))))
							.addGap(135, 135, 135)
							.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addGroup(DeliveryListPanelLayout.createSequentialGroup()
									.addGroup(DeliveryListPanelLayout.createParallelGroup()
										.addComponent(label10)
										.addComponent(label11)
										.addComponent(label12))
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
										.addComponent(truckID, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
										.addComponent(staffName, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
										.addComponent(driverName, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)))
								.addGroup(DeliveryListPanelLayout.createSequentialGroup()
									.addComponent(label4)
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(entruckDate, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)))
							.addGap(264, 264, 264)
							.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(cancelLoad, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
								.addComponent(saveEntruck, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
								.addComponent(doEntruck, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
							.addContainerGap())
				);
				DeliveryListPanelLayout.setVerticalGroup(
					DeliveryListPanelLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, DeliveryListPanelLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(DeliveryListPanelLayout.createParallelGroup()
								.addComponent(label6)
								.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(listID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(label4)
									.addComponent(entruckDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label5)
								.addComponent(label10)
								.addComponent(truckID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(hallID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(DeliveryListPanelLayout.createParallelGroup()
								.addGroup(DeliveryListPanelLayout.createSequentialGroup()
									.addComponent(doEntruck)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(saveEntruck)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(cancelLoad))
								.addGroup(DeliveryListPanelLayout.createSequentialGroup()
									.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(label9)
										.addComponent(label11)
										.addComponent(staffName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(hallName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(DeliveryListPanelLayout.createParallelGroup()
										.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
											.addComponent(label8)
											.addComponent(destID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(DeliveryListPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
											.addComponent(label12)
											.addComponent(driverName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(DeliveryListPanelLayout.createParallelGroup()
										.addComponent(label7)
										.addComponent(destName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE))
				);
			}
			entruckVO.addTab("\u88c5\u8f66\u5355", DeliveryListPanel);
		}

		//======== errorDialog ========
		{
			errorDialog.setTitle("\u7f51\u7edc\u5f02\u5e38");
			Container errorDialogContentPane = errorDialog.getContentPane();
			errorDialogContentPane.setLayout(new BorderLayout());

			//======== panel2 ========
			{

				//---- label2 ----
				label2.setText("\u7f51\u7edc\u8fde\u63a5\u4e2d\u65ad");

				//---- errorSure ----
				errorSure.setText("\u786e\u5b9a");
				errorSure.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						errorSureMouseClicked(e);
					}
				});

				GroupLayout panel2Layout = new GroupLayout(panel2);
				panel2.setLayout(panel2Layout);
				panel2Layout.setHorizontalGroup(
					panel2Layout.createParallelGroup()
						.addGroup(panel2Layout.createSequentialGroup()
							.addContainerGap(80, Short.MAX_VALUE)
							.addGroup(panel2Layout.createParallelGroup()
								.addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
									.addComponent(label2, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
									.addGap(68, 68, 68))
								.addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
									.addComponent(errorSure)
									.addGap(86, 86, 86))))
				);
				panel2Layout.setVerticalGroup(
					panel2Layout.createParallelGroup()
						.addGroup(panel2Layout.createSequentialGroup()
							.addGap(47, 47, 47)
							.addComponent(label2)
							.addGap(18, 18, 18)
							.addComponent(errorSure)
							.addContainerGap(58, Short.MAX_VALUE))
				);
			}
			errorDialogContentPane.add(panel2, BorderLayout.CENTER);
			errorDialog.setSize(260, 200);
			errorDialog.setLocationRelativeTo(null);
		}

		//======== resultDialog ========
		{
			Container resultDialogContentPane = resultDialog.getContentPane();
			resultDialogContentPane.setLayout(new BorderLayout());

			//======== panel3 ========
			{

				//---- label3 ----
				label3.setText("\u64cd\u4f5c\u6210\u529f");

				//---- resultSure ----
				resultSure.setText("\u786e\u5b9a");
				resultSure.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						resultSureMouseClicked(e);
					}
				});

				GroupLayout panel3Layout = new GroupLayout(panel3);
				panel3.setLayout(panel3Layout);
				panel3Layout.setHorizontalGroup(
					panel3Layout.createParallelGroup()
						.addGroup(panel3Layout.createSequentialGroup()
							.addGap(89, 89, 89)
							.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(resultSure)
								.addComponent(label3))
							.addContainerGap(98, Short.MAX_VALUE))
				);
				panel3Layout.setVerticalGroup(
					panel3Layout.createParallelGroup()
						.addGroup(panel3Layout.createSequentialGroup()
							.addGap(59, 59, 59)
							.addComponent(label3)
							.addGap(26, 26, 26)
							.addComponent(resultSure)
							.addContainerGap(38, Short.MAX_VALUE))
				);
			}
			resultDialogContentPane.add(panel3, BorderLayout.CENTER);
			resultDialog.pack();
			resultDialog.setLocationRelativeTo(resultDialog.getOwner());
		}
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	private JTabbedPane loadAndSortPane;
	private JPanel entruckListPanel;
	private JButton selectEntruck;
	private JScrollPane scrollPane3;
	private JTable entruckListTable;
	private JPanel panel1;
	private JScrollPane scrollPane1;
	private JTable orderTable;
	private JLabel label1;
	private JComboBox comboBox1;
	private JButton sortButton;
	private JButton createEntruck;
	private JButton removeOrder;
	private JTabbedPane entruckVO;
	private JPanel DeliveryListPanel;
	private JScrollPane scrollPane2;
	private JTable entruckTable;
	private JButton cancelLoad;
	private JButton saveEntruck;
	private JButton doEntruck;
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	private JLabel label9;
	private JTextField listID;
	private JTextField hallID;
	private JTextField hallName;
	private JTextField destID;
	private JTextField destName;
	private JLabel label10;
	private JLabel label11;
	private JLabel label12;
	private JTextField truckID;
	private JTextField staffName;
	private JTextField driverName;
	private JLabel label4;
	private JTextField entruckDate;
	private JDialog errorDialog;
	private JPanel panel2;
	private JLabel label2;
	private JButton errorSure;
	private JDialog resultDialog;
	private JPanel panel3;
	private JLabel label3;
	private JButton resultSure;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
