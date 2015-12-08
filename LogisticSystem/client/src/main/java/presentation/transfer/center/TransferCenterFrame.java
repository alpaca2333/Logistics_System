/*
 * Created by JFormDesigner on Sun Nov 22 09:58:36 CST 2015
 */

package presentation.transfer.center;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;

import javax.swing.*;

import org.jb2011.lnf.windows2.Windows2LookAndFeel;

import data.message.LoginMessage;
import data.message.ResultMessage;
import businesslogic.impl.transfer.center.TransferCenterController;
import businesslogic.service.Transfer.hall.TransferCenterService;

/**
 * @author sunxu
 */
public class TransferCenterFrame extends JFrame {
	
	TransferCenterService transferCenter;
	TransferReceivePanel receivePanel;//中转接收面板
	TransferLoadPanel loadPanel;//装运管理面板
	

	
	public TransferCenterFrame(LoginMessage login) {
		try {
			UIManager.setLookAndFeel(new Windows2LookAndFeel());
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		try {
			transferCenter = new TransferCenterController(login);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "网络连接中断", "LCS物流管理系统", JOptionPane.INFORMATION_MESSAGE);
		}
		initComponents();
		transferReceiveStart();//默认显示中转接收界面
		this.setVisible(true);
	}
	
	public void transferReceiveStart(){
		if(receivePanel == null)
			receivePanel = new TransferReceivePanel(transferCenter.startTransferReceiver());
		
		receive.setSelected(true);
		load.setSelected(false);
		receive.setEnabled(false);
		load.setEnabled(true);
		
		Container container = getContentPane();
		container.remove(emptyPanel);
		if(loadPanel != null)
		container.remove(loadPanel);
		container.add(receivePanel,BorderLayout.CENTER);
		
		receivePanel.setVisible(true);
		receivePanel.validate();
		receivePanel.updateUI();
		container.repaint();
		
	}
	
	public void transferLoadStart(){
		if(loadPanel == null)
			loadPanel = new TransferLoadPanel(transferCenter.startTransferLoad());
		
		receive.setSelected(false);
		load.setSelected(true);
		receive.setEnabled(true);
		load.setEnabled(false);
		
		Container container = getContentPane();
		container.remove(emptyPanel);
		container.remove(receivePanel);
		container.add(loadPanel,BorderLayout.CENTER);
		loadPanel.setVisible(true);
		loadPanel.validate();
		loadPanel.updateUI();
		container.repaint();
	}

	private void receiveMouseClicked(MouseEvent e) {
		transferReceiveStart();
	}

	private void loadMouseClicked(MouseEvent e) {
		transferLoadStart();
	}
	
	
	
	
//=======================================================================================================================================
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menu2 = new JMenu();
        panel1 = new JPanel();
        receive = new JButton();
        load = new JButton();
        label6 = new JLabel();
        label7 = new JLabel();
        emptyPanel = new JPanel();
        tabbedPane1 = new JTabbedPane();
        label1 = new JLabel();

        //======== this ========
        setTitle("\u4e2d\u8f6c\u4e2d\u5fc3");
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

        //======== panel1 ========
        {

            //---- receive ----
            receive.setIcon(new ImageIcon(getClass().getResource("/icons/transit_72x72.png")));
            receive.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
            receive.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    receiveMouseClicked(e);
                }
            });

            //---- load ----
            load.setIcon(new ImageIcon(getClass().getResource("/icons/box_72x72.png")));
            load.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
            load.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    loadMouseClicked(e);
                }
            });

            //---- label6 ----
            label6.setText("\u4e2d\u8f6c\u63a5\u6536");
            label6.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

            //---- label7 ----
            label7.setText("\u88c5\u8fd0\u7ba1\u7406");
            label7.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel1Layout.createParallelGroup()
                            .addComponent(receive, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(label6, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(load))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(label7)))
                        .addContainerGap(554, Short.MAX_VALUE))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel1Layout.createParallelGroup()
                            .addComponent(receive, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(load, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label6, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label7))
                        .addGap(6, 6, 6))
            );
        }
        contentPane.add(panel1, BorderLayout.NORTH);

        //======== emptyPanel ========
        {

            //======== tabbedPane1 ========
            {
                tabbedPane1.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));

                //---- label1 ----
                label1.setText("text");
                tabbedPane1.addTab("text", label1);
            }

            GroupLayout emptyPanelLayout = new GroupLayout(emptyPanel);
            emptyPanel.setLayout(emptyPanelLayout);
            emptyPanelLayout.setHorizontalGroup(
                emptyPanelLayout.createParallelGroup()
                    .addGroup(emptyPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tabbedPane1, GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE))
            );
            emptyPanelLayout.setVerticalGroup(
                emptyPanelLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, emptyPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tabbedPane1, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))
            );
        }
        contentPane.add(emptyPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenu menu2;
    private JPanel panel1;
    private JButton receive;
    private JButton load;
    private JLabel label6;
    private JLabel label7;
    private JPanel emptyPanel;
    private JTabbedPane tabbedPane1;
    private JLabel label1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
