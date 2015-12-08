package presentation.storage;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;

import javax.swing.*;

import org.jb2011.lnf.windows2.Windows2LookAndFeel;

import businesslogic.impl.storage.StorageBusinessController;
import businesslogic.impl.storage.StorageIn;
import businesslogic.service.storage.StorageBusinessService;
import businesslogic.service.storage.StorageInService;
import businesslogic.service.storage.StorageOperateService;
import businesslogic.service.storage.StorageOutService;
import data.message.LoginMessage;
/*
 * Created by JFormDesigner on Thu Nov 19 14:29:33 CST 2015
 */
import data.message.ResultMessage;
/**
 * @author sunxu
 */
public class StorageFrame extends JFrame {
	StorageBusinessService  storageBusiness;
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
		this.setVisible(true);
		try {
			storageBusiness = new StorageBusinessController(user);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "网络连接中断，请稍后再试", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
		storageInStart();//默认显示入库界面
	}

	//显示入库panel
	private boolean storageInStart(){
		storageIn.setEnabled(false);
		storageOut.setEnabled(true);
		storageOperete.setEnabled(true);
		
		storageIn.setSelected(true);
		storageOut.setSelected(false);
		storageOperete.setSelected(false);
		
		if(storageInVO == null){
			StorageInService storageIn = storageBusiness.startStorageIn();
			if(storageIn == null){
				JOptionPane.showMessageDialog(null, "仓库未初始化", "提示",	 JOptionPane.INFORMATION_MESSAGE);
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
		if(storageOperateVO != null)
		container.remove(storageOperateVO);
		if(storageOutVO != null)
		container.remove(storageOutVO);
		container.add(storageInVO,BorderLayout.CENTER);
		
		storageInVO.setVisible(true);
		storageInVO.validate();
		storageInVO.updateUI();
		container.repaint();
		
		return true;
	}

	//显示出库panel
	private boolean storageOutStart(){
		storageIn.setEnabled(true);
		storageOut.setEnabled(false);
		storageOperete.setEnabled(true);
		
		storageIn.setSelected(false);
		storageOut.setSelected(true);
		storageOperete.setSelected(false);
		
		if(storageOutVO == null){
			StorageOutService storageOut = storageBusiness.startStorageOut();
			if(storageOut == null){
				JOptionPane.showMessageDialog(null, "仓库未初始化", "提示",	 JOptionPane.INFORMATION_MESSAGE);
				Container container = getContentPane();
				if (storageOperateVO != null) {
					container.remove(storageOperateVO);
				}
				add(emptyPanel);
				emptyPanel.setVisible(true);
				return false;
			}else{
			storageOutVO = new StorageOutPanel(storageOut);
			}
		}
		

		
		Container container = getContentPane();
		container.remove(emptyPanel);
		container.remove(storageInVO);
		if(storageOperateVO != null)
		container.remove(storageOperateVO);
		container.add(storageOutVO,BorderLayout.CENTER);
		
		storageOutVO.setVisible(true);
		storageOutVO.validate();
		storageOutVO.updateUI();
		container.repaint();
		return true;
	}

	//显示库存操作panel
	private void storageOperateStart(){
		if(storageOperateVO == null){
			try {
				storageOperateVO = new StorageOperatePanel(storageBusiness.startStorageOperate());
			} catch (RemoteException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "网络连接中断","网络异常",JOptionPane.ERROR_MESSAGE);
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
		if(storageOutVO != null)
		container.remove(storageOutVO);
		container.add(storageOperateVO,BorderLayout.CENTER);
		
		storageOperateVO.setVisible(true);
		storageOperateVO.validate();
		storageOperateVO.updateUI();
		container.repaint();
	}
	

//========================监听=====================================	
	
	//storageOperate Button
	private void storageOpereteMouseClicked(MouseEvent e) {
		storageOperateStart();
	}
	//storageIn Button
	private void storageInMouseClicked(MouseEvent e) {
		storageInStart();

	}
	//storageOut Button
	private void storageOutMouseClicked(MouseEvent e) {
		storageOutStart();
	}

//========================监听=====================================
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menu2 = new JMenu();
        panel1 = new JPanel();
        label1 = new JLabel();
        storageOperete = new JToggleButton();
        label3 = new JLabel();
        storageOut = new JToggleButton();
        label2 = new JLabel();
        emptyPanel = new JPanel();
        storageIn = new JToggleButton();
        tabbedPane1 = new JTabbedPane();
        panel2 = new JPanel();
        label4 = new JLabel();
        label5 = new JLabel();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("\u4e2d\u8f6c\u4e2d\u5fc3\u4ed3\u5e93\u7ba1\u7406");
        Container contentPane = getContentPane();

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

        //======== panel1 ========
        {

            //---- label1 ----
            label1.setText("\u5165\u5e93");
            label1.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

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

            //======== emptyPanel ========
            {

                GroupLayout emptyPanelLayout = new GroupLayout(emptyPanel);
                emptyPanel.setLayout(emptyPanelLayout);
                emptyPanelLayout.setHorizontalGroup(
                    emptyPanelLayout.createParallelGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                );
                emptyPanelLayout.setVerticalGroup(
                    emptyPanelLayout.createParallelGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                );
            }

            //---- storageIn ----
            storageIn.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
            storageIn.setIcon(new ImageIcon(getClass().getResource("/icons/storagein_72x72.png")));
            storageIn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    storageInMouseClicked(e);
                }
            });

            //======== tabbedPane1 ========
            {
                tabbedPane1.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //======== panel2 ========
                {
                    panel2.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                    //---- label4 ----
                    label4.setText("\u4ed3\u5e93\u672a\u521d\u59cb\u5316");
                    label4.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                    //---- label5 ----
                    label5.setText("\u8bf7\u5148\u521d\u59cb\u5316\u4ed3\u5e93");
                    label5.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                    GroupLayout panel2Layout = new GroupLayout(panel2);
                    panel2.setLayout(panel2Layout);
                    panel2Layout.setHorizontalGroup(
                        panel2Layout.createParallelGroup()
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addGap(296, 296, 296)
                                .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(label4, GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                                    .addComponent(label5, GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );
                    panel2Layout.setVerticalGroup(
                        panel2Layout.createParallelGroup()
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addGap(141, 141, 141)
                                .addComponent(label4)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label5)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );
                }
                tabbedPane1.addTab("\u63d0\u793a", panel2);
            }

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(emptyPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(storageIn)
                                .addGap(18, 18, 18)
                                .addComponent(storageOut))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(label1)
                                .addGap(84, 84, 84)
                                .addComponent(label2)))
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(storageOperete))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(label3)))
                        .addContainerGap(436, Short.MAX_VALUE))
                    .addComponent(tabbedPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel1Layout.createParallelGroup()
                            .addComponent(storageIn)
                            .addComponent(storageOut)
                            .addComponent(storageOperete))
                        .addGap(6, 6, 6)
                        .addGroup(panel1Layout.createParallelGroup()
                            .addComponent(label1)
                            .addComponent(label2)
                            .addComponent(label3))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabbedPane1, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emptyPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenu menu2;
    private JPanel panel1;
    private JLabel label1;
    private JToggleButton storageOperete;
    private JLabel label3;
    private JToggleButton storageOut;
    private JLabel label2;
    private JPanel emptyPanel;
    private JToggleButton storageIn;
    private JTabbedPane tabbedPane1;
    private JPanel panel2;
    private JLabel label4;
    private JLabel label5;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
