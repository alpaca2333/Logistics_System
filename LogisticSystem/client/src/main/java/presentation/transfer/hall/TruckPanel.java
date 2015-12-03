/*
 * Created by JFormDesigner on Wed Dec 02 23:23:16 CST 2015
 */

package presentation.transfer.hall;

import java.awt.*;

import javax.swing.*;

import businesslogic.service.Transfer.hall.EntruckReceiveService;
import businesslogic.service.Transfer.hall.TruckManagementService;

/**
 * @author sunxu
 */
public class TruckPanel extends JPanel {
	TruckManagementService truckManagement;
	public TruckPanel() {
		initComponents();
	}

	public TruckPanel(TruckManagementService truckManagement) {
		this.truckManagement = truckManagement;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		truckPane = new JTabbedPane();
		panel1 = new JPanel();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		addTruckButton = new JButton();
		showTruckButton = new JButton();
		tabbedPane1 = new JTabbedPane();
		panel2 = new JPanel();

		//======== this ========
		setLayout(new BorderLayout());

		//======== truckPane ========
		{

			//======== panel1 ========
			{

				//======== scrollPane1 ========
				{
					scrollPane1.setViewportView(table1);
				}

				//---- addTruckButton ----
				addTruckButton.setText("\u6dfb\u52a0");

				//---- showTruckButton ----
				showTruckButton.setText("\u67e5\u770b");

				GroupLayout panel1Layout = new GroupLayout(panel1);
				panel1.setLayout(panel1Layout);
				panel1Layout.setHorizontalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 693, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel1Layout.createParallelGroup()
								.addComponent(addTruckButton, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
								.addComponent(showTruckButton, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
							.addContainerGap())
				);
				panel1Layout.setVerticalGroup(
					panel1Layout.createParallelGroup()
						.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
						.addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
							.addContainerGap(284, Short.MAX_VALUE)
							.addComponent(showTruckButton)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(addTruckButton)
							.addContainerGap())
				);
			}
			truckPane.addTab("\u8f66\u8f86\u5217\u8868", panel1);
		}
		add(truckPane, BorderLayout.CENTER);

		//======== tabbedPane1 ========
		{

			//======== panel2 ========
			{

				GroupLayout panel2Layout = new GroupLayout(panel2);
				panel2.setLayout(panel2Layout);
				panel2Layout.setHorizontalGroup(
					panel2Layout.createParallelGroup()
						.addGap(0, 795, Short.MAX_VALUE)
				);
				panel2Layout.setVerticalGroup(
					panel2Layout.createParallelGroup()
						.addGap(0, 346, Short.MAX_VALUE)
				);
			}
			tabbedPane1.addTab("\u8f66\u8f86\u8be6\u7ec6\u4fe1\u606f", panel2);
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JTabbedPane truckPane;
	private JPanel panel1;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JButton addTruckButton;
	private JButton showTruckButton;
	private JTabbedPane tabbedPane1;
	private JPanel panel2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
