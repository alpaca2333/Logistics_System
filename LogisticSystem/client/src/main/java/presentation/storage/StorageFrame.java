package presentation.storage;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;

import javax.swing.*;

import org.jb2011.lnf.windows2.Windows2LookAndFeel;

import businesslogic.impl.storage.StorageBusinessController;
import businesslogic.service.storage.StorageInService;
import businesslogic.service.storage.StorageOutService;
import data.message.LoginMessage;

/*
 * Created by JFormDesigner on Thu Nov 19 14:29:33 CST 2015
 */
/**
 * @author sunxu
 */
public class StorageFrame extends JFrame {
	StorageBusinessController storageBusiness;
	StorageOperatePanel storageOperateVO;
	StorageOutPanel storageOutVO;
	StorageInPanel storageInVO;

	public StorageFrame(LoginMessage user) {
		try {
			UIManager.setLookAndFeel(new Windows2LookAndFeel());
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		initComponents();
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				//closeDialog.setVisible(true);
				boolean outIsClear = true;
				boolean inIsClear = true;
				if(storageOutVO != null){
					if(!storageOutVO.isClear()){
						outIsClear = false;
					}
				}
				
				if(storageInVO != null){
					if(!storageInVO.isClear()){
						inIsClear = false;
					}
				}
				if(inIsClear && outIsClear){
					System.exit(DISPOSE_ON_CLOSE);
				}else{
					JOptionPane.showMessageDialog(null, "有已审批出库单或中转单未处理，请处理完后再退出", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		try {
			storageBusiness = new StorageBusinessController(user);
			String[] info = storageBusiness.getUserInfo().split("-");
			staff.setText(staff.getText()+info[0]);
			institution.setText(institution.getText()+info[1]);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "网络连接中断，请稍后再试", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
		storageInStart();// 默认显示入库界面
	}

	// 显示入库panel
	private boolean storageInStart() {
		storageIn.setEnabled(false);
		storageOut.setEnabled(true);
		storageOperete.setEnabled(true);

		storageIn.setSelected(true);
		storageOut.setSelected(false);
		storageOperete.setSelected(false);

		if (storageInVO == null) {
			StorageInService storageIn = storageBusiness.startStorageIn();
			if (storageIn == null) {
				JOptionPane.showMessageDialog(null, "仓库未初始化", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				Container container = getContentPane();
				if (storageOperateVO != null) {
					container.remove(storageOperateVO);
				}
				add(emptyPanel);
				emptyPanel.setVisible(true);
				return false;
			}
			storageInVO = new StorageInPanel(storageIn);
		}

		Container container = getContentPane();
		container.remove(emptyPanel);
		if (storageOperateVO != null)
			container.remove(storageOperateVO);
		if (storageOutVO != null)
			container.remove(storageOutVO);
		container.add(storageInVO, BorderLayout.CENTER);

		storageInVO.setVisible(true);
		storageInVO.validate();
		storageInVO.updateUI();
		container.repaint();

		return true;
	}

	// 显示出库panel
	private boolean storageOutStart() {
		storageIn.setEnabled(true);
		storageOut.setEnabled(false);
		storageOperete.setEnabled(true);

		storageIn.setSelected(false);
		storageOut.setSelected(true);
		storageOperete.setSelected(false);

		if (storageOutVO == null) {
			StorageOutService storageOut = storageBusiness.startStorageOut();
			if (storageOut == null) {
				JOptionPane.showMessageDialog(null, "仓库未初始化", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				Container container = getContentPane();
				if (storageOperateVO != null) {
					container.remove(storageOperateVO);
				}
				add(emptyPanel);
				emptyPanel.setVisible(true);
				return false;
			} else {
				storageOutVO = new StorageOutPanel(storageOut);
			}
		}

		Container container = getContentPane();
		container.remove(emptyPanel);
		container.remove(storageInVO);
		if (storageOperateVO != null)
			container.remove(storageOperateVO);
		container.add(storageOutVO, BorderLayout.CENTER);

		storageOutVO.setVisible(true);
		storageOutVO.validate();
		storageOutVO.updateUI();
		container.repaint();
		return true;
	}

	// 显示库存操作panel
	private void storageOperateStart() {
		if (storageOperateVO == null) {
			try {
				storageOperateVO = new StorageOperatePanel(
						storageBusiness.startStorageOperate());
			} catch (RemoteException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "网络连接中断", "网络异常",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		storageIn.setEnabled(true);
		storageOut.setEnabled(true);
		storageOperete.setEnabled(false);

		storageIn.setSelected(false);
		storageOut.setSelected(false);
		storageOperete.setSelected(true);

		Container container = getContentPane();
		container.remove(emptyPanel);
		if (storageInVO != null)
			container.remove(storageInVO);
		if (storageOutVO != null)
			container.remove(storageOutVO);
		if (storageOperateVO != null) {
			container.add(storageOperateVO, BorderLayout.CENTER);
		}
		container.repaint();
		storageOperateVO.validate();
		storageOperateVO.updateUI();
		storageOperateVO.setVisible(true);
	}

	// ========================监听=====================================

	// storageOperate Button
	private void storageOpereteMouseClicked(MouseEvent e) {
		storageOperateStart();
	}

	// storageIn Button
	private void storageInMouseClicked(MouseEvent e) {
		storageInStart();

	}

	// storageOut Button
	private void storageOutMouseClicked(MouseEvent e) {
		storageOutStart();
	}

	private void button1MouseReleased(MouseEvent e) {
		closeDialog.setVisible(false);
	}

	private void button2MouseReleased(MouseEvent e) {
		System.exit(DISPOSE_ON_CLOSE);
	}

	private void thisWindowClosing(WindowEvent e) {
		closeDialog.repaint();
		closeDialog.setVisible(true);
		System.out.println("StorageFrame.thisWindowClosed()");
	}

	// ========================监听=====================================
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		menuBar1 = new JMenuBar();
		menu1 = new JMenu();
		menu2 = new JMenu();
		panel2 = new JPanel();
		storageIn = new JToggleButton();
		label1 = new JLabel();
		storageOut = new JToggleButton();
		label2 = new JLabel();
		storageOperete = new JToggleButton();
		label3 = new JLabel();
		label6 = new JLabel();
		institution = new JLabel();
		staff = new JLabel();
		emptyPanel = new JPanel();
		tabbedPane1 = new JTabbedPane();
		panel3 = new JPanel();
		label4 = new JLabel();
		closeDialog = new JDialog();
		panel4 = new JPanel();
		label5 = new JLabel();
		label10 = new JLabel();
		button1 = new JButton();
		button2 = new JButton();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("\u4e2d\u8f6c\u4e2d\u5fc3\u4ed3\u5e93\u7ba1\u7406");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== menuBar1 ========
		{

			//======== menu1 ========
			{
				menu1.setText("\u9009\u9879");
				menu1.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
			}
			menuBar1.add(menu1);

			//======== menu2 ========
			{
				menu2.setText("\u5e2e\u52a9");
				menu2.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
			}
			menuBar1.add(menu2);
		}
		setJMenuBar(menuBar1);

		//======== panel2 ========
		{

			//---- storageIn ----
			storageIn.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
			storageIn.setIcon(new ImageIcon(getClass().getResource("/icons/storagein_72x72.png")));
			storageIn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					storageInMouseClicked(e);
				}
			});

			//---- label1 ----
			label1.setText("\u5165\u5e93");
			label1.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

			//---- storageOut ----
			storageOut.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
			storageOut.setIcon(new ImageIcon(getClass().getResource("/icons/storageout_72x72.png")));
			storageOut.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					storageOutMouseClicked(e);
				}
			});

			//---- label2 ----
			label2.setText("\u51fa\u5e93");
			label2.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

			//---- storageOperete ----
			storageOperete.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
			storageOperete.setIcon(new ImageIcon(getClass().getResource("/icons/storage_72x72.png")));
			storageOperete.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					storageOpereteMouseClicked(e);
				}
			});

			//---- label3 ----
			label3.setText("\u5e93\u5b58\u64cd\u4f5c");
			label3.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

			//---- label6 ----
			label6.setText("text");
			label6.setIcon(new ImageIcon(getClass().getResource("/icons/blackman.png")));

			//---- institution ----
			institution.setText("\u673a\u6784\uff1a");

			//---- staff ----
			staff.setText("\u59d3\u540d\uff1a");

			GroupLayout panel2Layout = new GroupLayout(panel2);
			panel2.setLayout(panel2Layout);
			panel2Layout.setHorizontalGroup(
				panel2Layout.createParallelGroup()
					.addGroup(panel2Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel2Layout.createParallelGroup()
							.addGroup(panel2Layout.createSequentialGroup()
								.addGap(32, 32, 32)
								.addComponent(label1)
								.addGap(86, 86, 86)
								.addComponent(label2)
								.addGap(62, 62, 62)
								.addComponent(label3)
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(panel2Layout.createSequentialGroup()
								.addComponent(storageIn)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(storageOut)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(storageOperete)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 324, Short.MAX_VALUE)
								.addComponent(label6, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(panel2Layout.createParallelGroup()
									.addComponent(staff, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
									.addComponent(institution, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
								.addGap(28, 28, 28))))
			);
			panel2Layout.setVerticalGroup(
				panel2Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
						.addGroup(panel2Layout.createParallelGroup()
							.addGroup(panel2Layout.createParallelGroup()
								.addComponent(storageIn)
								.addComponent(storageOut)
								.addComponent(storageOperete)
								.addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
									.addComponent(staff)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(institution)))
							.addGroup(panel2Layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(label6, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(panel2Layout.createParallelGroup()
							.addComponent(label1)
							.addComponent(label2)
							.addComponent(label3)))
			);
		}
		contentPane.add(panel2, BorderLayout.NORTH);

		//======== emptyPanel ========
		{
			emptyPanel.setLayout(new BorderLayout());

			//======== tabbedPane1 ========
			{

				//======== panel3 ========
				{

					//---- label4 ----
					label4.setText("\u4ed3\u5e93\u672a\u521d\u59cb\u5316");

					GroupLayout panel3Layout = new GroupLayout(panel3);
					panel3.setLayout(panel3Layout);
					panel3Layout.setHorizontalGroup(
						panel3Layout.createParallelGroup()
							.addGroup(panel3Layout.createSequentialGroup()
								.addGap(358, 358, 358)
								.addComponent(label4, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(392, Short.MAX_VALUE))
					);
					panel3Layout.setVerticalGroup(
						panel3Layout.createParallelGroup()
							.addGroup(panel3Layout.createSequentialGroup()
								.addGap(136, 136, 136)
								.addComponent(label4, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(202, Short.MAX_VALUE))
					);
				}
				tabbedPane1.addTab("\u63d0\u793a", panel3);
			}
			emptyPanel.add(tabbedPane1, BorderLayout.CENTER);
		}
		contentPane.add(emptyPanel, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());

		//======== closeDialog ========
		{
			closeDialog.setTitle("\u63d0\u793a");
			Container closeDialogContentPane = closeDialog.getContentPane();
			closeDialogContentPane.setLayout(new BorderLayout());

			//======== panel4 ========
			{

				//---- label5 ----
				label5.setText("\u8bf7\u68c0\u67e5\u662f\u5426\u5df2\u7ecf\u5904\u7406");

				//---- label10 ----
				label10.setText("\u5168\u90e8\u5df2\u5ba1\u6279\u4e2d\u8f6c\u5355\u548c\u5230\u8fbe\u5355");

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

				GroupLayout panel4Layout = new GroupLayout(panel4);
				panel4.setLayout(panel4Layout);
				panel4Layout.setHorizontalGroup(
					panel4Layout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
							.addContainerGap(57, Short.MAX_VALUE)
							.addGroup(panel4Layout.createParallelGroup()
								.addGroup(panel4Layout.createSequentialGroup()
									.addGap(10, 10, 10)
									.addComponent(button1, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
									.addContainerGap())
								.addGroup(GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
									.addComponent(label10, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
									.addGap(50, 50, 50))))
						.addGroup(panel4Layout.createSequentialGroup()
							.addGroup(panel4Layout.createParallelGroup()
								.addGroup(panel4Layout.createSequentialGroup()
									.addGap(79, 79, 79)
									.addComponent(label5))
								.addGroup(panel4Layout.createSequentialGroup()
									.addGap(94, 94, 94)
									.addComponent(button2, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap(82, Short.MAX_VALUE))
				);
				panel4Layout.setVerticalGroup(
					panel4Layout.createParallelGroup()
						.addGroup(panel4Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(label5, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label10, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button1, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
				);
			}
			closeDialogContentPane.add(panel4, BorderLayout.CENTER);
			closeDialog.pack();
			closeDialog.setLocationRelativeTo(closeDialog.getOwner());
		}
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	private JMenuBar menuBar1;
	private JMenu menu1;
	private JMenu menu2;
	private JPanel panel2;
	private JToggleButton storageIn;
	private JLabel label1;
	private JToggleButton storageOut;
	private JLabel label2;
	private JToggleButton storageOperete;
	private JLabel label3;
	private JLabel label6;
	private JLabel institution;
	private JLabel staff;
	private JPanel emptyPanel;
	private JTabbedPane tabbedPane1;
	private JPanel panel3;
	private JLabel label4;
	private JDialog closeDialog;
	private JPanel panel4;
	private JLabel label5;
	private JLabel label10;
	private JButton button1;
	private JButton button2;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
