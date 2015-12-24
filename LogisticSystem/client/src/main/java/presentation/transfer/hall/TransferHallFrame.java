/*
 * Created by JFormDesigner on Mon Nov 30 22:28:21 CST 2015
 */

package presentation.transfer.hall;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

import org.jb2011.lnf.windows2.Windows2LookAndFeel;

import data.message.LoginMessage;
import data.message.ResultMessage;
import businesslogic.impl.transfer.hall.TransferHallController;
import businesslogic.service.Transfer.hall.TransferHallService;

/**
 * @author sunxu
 */
public class TransferHallFrame extends JFrame {
	TransferHallService transferHall;
	EntruckReceivePanel entruckReceivePanel;// 装车接收面板
	LoadAndSortPanel loadAndSortPanel;// 分拣装车面板
	DriverPanel driverPanel;// 司机信息管理面板
	TruckPanel truckPanel;// 车辆信息管理面板
	ReceiveMoneyPanel receiveMoneyPanel;
	ArrayList<JButton> buttons;

	public TransferHallFrame(LoginMessage login) {
		try {
			UIManager.setLookAndFeel(new Windows2LookAndFeel());
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		initComponents();
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
			//	closeDialog.setVisible(true);
				if(loadAndSortPanel.isClear() && entruckReceivePanel.isClear()){
					System.exit(DISPOSE_ON_CLOSE);
				}else{
					JOptionPane.showMessageDialog(null, "有已审批到达单或装车单未处理,请处理完后再退出", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		buttons = new ArrayList<JButton>();
		buttons.add(receiveButton);
		buttons.add(loadButton);
		buttons.add(driverButton);
		buttons.add(truckButton);
		buttons.add(moneyButton);
		
		try {
			transferHall = new TransferHallController(login);
		} catch (Exception e) {
			e.printStackTrace();
			errorDialog.validate();
			errorDialog.repaint();
			errorDialog.setVisible(true);
		}
		entruckReceiveStart();
		this.setVisible(true);
	}

	public void entruckReceiveStart() {
		receiveButton.setSelected(true);
		receiveButton.setEnabled(false);

		if (entruckReceivePanel == null)
			try {
				entruckReceivePanel = new EntruckReceivePanel(
						transferHall.startEntruckReceive());
			} catch (Exception e) {
				e.printStackTrace();
				errorDialog.setVisible(true);
			}

		Container container = getContentPane();
		container.remove(emptyPanel);
		if (loadAndSortPanel != null)
			container.remove(loadAndSortPanel);
		if (truckPanel != null)
			container.remove(truckPanel);
		if (driverPanel != null)
			container.remove(driverPanel);
		container.add(entruckReceivePanel, BorderLayout.CENTER);
		container.repaint();

		entruckReceivePanel.validate();
		entruckReceivePanel.updateUI();
		entruckReceivePanel.setVisible(true);
	}

	// ==============================监听=================================
	
	private void setButtons(int index){
		for(int i = 0 ; i < buttons.size();i++){
			JButton b = buttons.get(i);
			if(i == index){
				b.setSelected(true);
				b.setEnabled(false);
			}else{
				b.setSelected(false);
				b.setEnabled(true);
			}
		}
	}
	
	
	private void receiveButtonMouseClicked(MouseEvent e) {
		setButtons(0);

		entruckReceiveStart();
	}

	private void loadButtonMouseClicked(MouseEvent e) {
		setButtons(1);

		if (loadAndSortPanel == null)
			try {
				loadAndSortPanel = new LoadAndSortPanel(
						transferHall.startOrderSort());
			} catch (RemoteException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null,"网络连接中断，请稍后再试"	, "提示", JOptionPane.INFORMATION_MESSAGE);
			}

		Container container = getContentPane();
		container.remove(emptyPanel);
		container.remove(entruckReceivePanel);
		if (truckPanel != null) {
			container.remove(truckPanel);
		}
		if (driverPanel != null) {
			container.remove(driverPanel);
		}
		container.add(loadAndSortPanel, BorderLayout.CENTER);
		container.repaint();

		loadAndSortPanel.validate();
		loadAndSortPanel.updateUI();
		loadAndSortPanel.setVisible(true);
	}

	private void errorSureMouseClicked(MouseEvent e) {
		System.exit(0);
	}

	private void driverButtonMouseClicked(MouseEvent e) {
		setButtons(2);

		if (driverPanel == null) {
			driverPanel = new DriverPanel(transferHall.startDriverManagement());
		}

		Container container = getContentPane();
		container.remove(emptyPanel);
		container.remove(entruckReceivePanel);
		if (truckPanel != null)
			remove(truckPanel);
		if (loadAndSortPanel != null)
			remove(loadAndSortPanel);
		container.add(driverPanel, BorderLayout.CENTER);
		container.repaint();

		driverPanel.validate();
		driverPanel.updateUI();
		driverPanel.setVisible(true);
	}

	private void truckButtonMouseClicked(MouseEvent e) {
		setButtons(3);

		if (truckPanel == null) {
			truckPanel = new TruckPanel(transferHall.startTruckManagement());
		}

		Container container = getContentPane();
		container.remove(emptyPanel);
		container.remove(entruckReceivePanel);
		if (driverPanel != null)
			remove(driverPanel);
		if (loadAndSortPanel != null)
			remove(loadAndSortPanel);
		if (entruckReceivePanel != null) 
			remove(entruckReceivePanel);
		if(receiveMoneyPanel != null)
			remove(receiveMoneyPanel);
		
		container.add(truckPanel, BorderLayout.CENTER);
		container.repaint();

		truckPanel.validate();
		truckPanel.updateUI();
		truckPanel.setVisible(true);
	}

	private void moneyButtonMouseReleased(MouseEvent e) {
		setButtons(4);
		
		if(receiveMoneyPanel == null){
			receiveMoneyPanel = new ReceiveMoneyPanel(transferHall.startReceive());
		}
		
		Container container = getContentPane();
		container.remove(emptyPanel);
		if(driverPanel != null){
			container.remove(driverPanel);
		}
		if (truckPanel != null) {
			container.remove(truckPanel);
		}
		if(loadAndSortPanel != null){
			container.remove(loadAndSortPanel);
		}
		if(entruckReceivePanel != null){
			container.remove(entruckReceivePanel);
		}
		container.add(receiveMoneyPanel,BorderLayout.CENTER);
		receiveMoneyPanel.validate();
		receiveMoneyPanel.updateUI();
		receiveMoneyPanel.setVisible(true);
		container.repaint();
		}

		private void thisWindowClosed(WindowEvent e) {
			closeDialog.setVisible(true);
		}

		private void button1MouseReleased(MouseEvent e) {
			closeDialog.setVisible(false);
		}

		private void button2MouseReleased(MouseEvent e) {
			System.exit(DISPOSE_ON_CLOSE);
		}



	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		menuBar1 = new JMenuBar();
		option = new JMenu();
		help = new JMenu();
		receiveButton = new JButton();
		loadButton = new JButton();
		driverButton = new JButton();
		truckButton = new JButton();
		moneyButton = new JButton();
		label1 = new JLabel();
		label2 = new JLabel();
		label4 = new JLabel();
		label5 = new JLabel();
		label6 = new JLabel();
		emptyPanel = new JPanel();
		tabbedPane1 = new JTabbedPane();
		label7 = new JLabel();
		errorDialog = new JDialog();
		panel2 = new JPanel();
		label8 = new JLabel();
		label9 = new JLabel();
		errorSure = new JButton();
		closeDialog = new JDialog();
		panel3 = new JPanel();
		label3 = new JLabel();
		label10 = new JLabel();
		button1 = new JButton();
		button2 = new JButton();

		//======== this ========
		setTitle("\u8425\u4e1a\u5385");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				thisWindowClosed(e);
			}
		});
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== panel1 ========
		{

			//======== menuBar1 ========
			{

				//======== option ========
				{
					option.setText("\u9009\u9879");
				}
				menuBar1.add(option);

				//======== help ========
				{
					help.setText("\u5e2e\u52a9");
				}
				menuBar1.add(help);
			}

			//---- receiveButton ----
			receiveButton.setText("text");
			receiveButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					receiveButtonMouseClicked(e);
				}
			});

			//---- loadButton ----
			loadButton.setText("text");
			loadButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					loadButtonMouseClicked(e);
				}
			});

			//---- driverButton ----
			driverButton.setText("text");
			driverButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					driverButtonMouseClicked(e);
				}
			});

			//---- truckButton ----
			truckButton.setText("text");
			truckButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					truckButtonMouseClicked(e);
				}
			});

			//---- moneyButton ----
			moneyButton.setText("text");
			moneyButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					moneyButtonMouseReleased(e);
				}
			});

			//---- label1 ----
			label1.setText("\u88c5\u8f66\u63a5\u6536");

			//---- label2 ----
			label2.setText("\u5206\u62e3\u88c5\u8f66");

			//---- label4 ----
			label4.setText("\u53f8\u673a\u4fe1\u606f");

			//---- label5 ----
			label5.setText("\u8f66\u8f86\u4fe1\u606f");

			//---- label6 ----
			label6.setText("\u7ed3\u7b97");

			GroupLayout panel1Layout = new GroupLayout(panel1);
			panel1.setLayout(panel1Layout);
			panel1Layout.setHorizontalGroup(
				panel1Layout.createParallelGroup()
					.addGroup(panel1Layout.createSequentialGroup()
						.addComponent(menuBar1, GroupLayout.PREFERRED_SIZE, 794, GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE))
					.addGroup(panel1Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel1Layout.createParallelGroup()
							.addComponent(receiveButton, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addGroup(panel1Layout.createSequentialGroup()
								.addGap(10, 10, 10)
								.addComponent(label1)))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panel1Layout.createParallelGroup()
							.addComponent(loadButton, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addGroup(panel1Layout.createSequentialGroup()
								.addGap(10, 10, 10)
								.addComponent(label2)))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panel1Layout.createParallelGroup()
							.addComponent(driverButton, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addComponent(label4, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panel1Layout.createParallelGroup()
							.addComponent(truckButton, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addGroup(panel1Layout.createSequentialGroup()
								.addGap(8, 8, 8)
								.addComponent(label5)))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panel1Layout.createParallelGroup()
							.addGroup(panel1Layout.createSequentialGroup()
								.addGap(10, 10, 10)
								.addComponent(label6))
							.addComponent(moneyButton, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(390, Short.MAX_VALUE))
			);
			panel1Layout.setVerticalGroup(
				panel1Layout.createParallelGroup()
					.addGroup(panel1Layout.createSequentialGroup()
						.addComponent(menuBar1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
							.addComponent(receiveButton, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
							.addComponent(loadButton, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
							.addComponent(driverButton, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
							.addComponent(truckButton, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
							.addComponent(moneyButton, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label1)
							.addComponent(label2)
							.addComponent(label4)
							.addComponent(label5)
							.addComponent(label6))
						.addContainerGap())
			);
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		//======== emptyPanel ========
		{
			emptyPanel.setLayout(new BorderLayout());

			//======== tabbedPane1 ========
			{

				//---- label7 ----
				label7.setText("text");
				tabbedPane1.addTab("text", label7);
			}
			emptyPanel.add(tabbedPane1, BorderLayout.CENTER);
		}
		contentPane.add(emptyPanel, BorderLayout.CENTER);
		setSize(800, 500);
		setLocationRelativeTo(getOwner());

		//======== errorDialog ========
		{
			errorDialog.setTitle("\u7f51\u7edc\u5f02\u5e38");
			Container errorDialogContentPane = errorDialog.getContentPane();
			errorDialogContentPane.setLayout(new BorderLayout());

			//======== panel2 ========
			{

				//---- label8 ----
				label8.setText("\u7f51\u7edc\u8fde\u63a5\u4e2d\u65ad");

				//---- label9 ----
				label9.setText("\u8bf7\u7a0d\u540e\u518d\u8bd5");

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
							.addGroup(panel2Layout.createParallelGroup()
								.addGroup(panel2Layout.createSequentialGroup()
									.addGap(88, 88, 88)
									.addGroup(panel2Layout.createParallelGroup()
										.addGroup(panel2Layout.createSequentialGroup()
											.addGap(10, 10, 10)
											.addComponent(label9, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
										.addComponent(label8, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)))
								.addGroup(panel2Layout.createSequentialGroup()
									.addGap(109, 109, 109)
									.addComponent(errorSure)))
							.addContainerGap(88, Short.MAX_VALUE))
				);
				panel2Layout.setVerticalGroup(
					panel2Layout.createParallelGroup()
						.addGroup(panel2Layout.createSequentialGroup()
							.addGap(46, 46, 46)
							.addComponent(label8)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(label9)
							.addGap(18, 18, 18)
							.addComponent(errorSure)
							.addContainerGap(34, Short.MAX_VALUE))
				);
			}
			errorDialogContentPane.add(panel2, BorderLayout.CENTER);
			errorDialog.setSize(290, 200);
			errorDialog.setLocationRelativeTo(null);
		}

		//======== closeDialog ========
		{
			closeDialog.setTitle("\u63d0\u793a");
			Container closeDialogContentPane = closeDialog.getContentPane();
			closeDialogContentPane.setLayout(new BorderLayout());

			//======== panel3 ========
			{

				//---- label3 ----
				label3.setText("\u8bf7\u68c0\u67e5\u662f\u5426\u5df2\u7ecf\u5904\u7406");

				//---- label10 ----
				label10.setText("\u5168\u90e8\u5df2\u5ba1\u6279\u88c5\u8f66\u5355\u548c\u5230\u8fbe\u5355");

				//---- button1 ----
				button1.setText("\u672a\u5904\u7406\u5b8c");
				button1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						button1MouseReleased(e);
					}
				});

				//---- button2 ----
				button2.setText("\u786e\u8ba4\u5173\u95ed");
				button2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						button2MouseReleased(e);
					}
				});

				GroupLayout panel3Layout = new GroupLayout(panel3);
				panel3.setLayout(panel3Layout);
				panel3Layout.setHorizontalGroup(
					panel3Layout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
							.addContainerGap(57, Short.MAX_VALUE)
							.addGroup(panel3Layout.createParallelGroup()
								.addGroup(panel3Layout.createSequentialGroup()
									.addGap(10, 10, 10)
									.addComponent(button1, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
									.addContainerGap())
								.addGroup(GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
									.addComponent(label10, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
									.addGap(50, 50, 50))))
						.addGroup(panel3Layout.createSequentialGroup()
							.addGroup(panel3Layout.createParallelGroup()
								.addGroup(panel3Layout.createSequentialGroup()
									.addGap(79, 79, 79)
									.addComponent(label3))
								.addGroup(panel3Layout.createSequentialGroup()
									.addGap(94, 94, 94)
									.addComponent(button2, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap(82, Short.MAX_VALUE))
				);
				panel3Layout.setVerticalGroup(
					panel3Layout.createParallelGroup()
						.addGroup(panel3Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(label3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label10, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button1, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
				);
			}
			closeDialogContentPane.add(panel3, BorderLayout.CENTER);
			closeDialog.pack();
			closeDialog.setLocationRelativeTo(closeDialog.getOwner());
		}
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	private JPanel panel1;
	private JMenuBar menuBar1;
	private JMenu option;
	private JMenu help;
	private JButton receiveButton;
	private JButton loadButton;
	private JButton driverButton;
	private JButton truckButton;
	private JButton moneyButton;
	private JLabel label1;
	private JLabel label2;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JPanel emptyPanel;
	private JTabbedPane tabbedPane1;
	private JLabel label7;
	private JDialog errorDialog;
	private JPanel panel2;
	private JLabel label8;
	private JLabel label9;
	private JButton errorSure;
	private JDialog closeDialog;
	private JPanel panel3;
	private JLabel label3;
	private JLabel label10;
	private JButton button1;
	private JButton button2;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
