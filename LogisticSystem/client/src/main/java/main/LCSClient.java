package main;

import javax.swing.*;

import businesslogic.controller.ClientConfigurationController;
import data.enums.UserRole;

import org.jb2011.lnf.windows2.Windows2LookAndFeel;

import data.message.LoginMessage;
import data.message.ResultMessage;
import presentation.company.companyManage;
import presentation.financial.FINANCE;
import presentation.order.OrderUI;
import presentation.order.SimplifiedOrderUI;
import presentation.storage.StorageFrame;
import presentation.transfer.center.TransferCenterFrame;
import presentation.transfer.hall.TransferHallFrame;
import presentation.user.login.LoginDlg;
import presentation.user.userMngUI.UserMngUI;
import utils.Connection;


/**
 *
 * Created by mist on 2015/11/13 0013.
 */
public class LCSClient extends JFrame{

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new Windows2LookAndFeel());
        } catch (UnsupportedLookAndFeelException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        ClientConfigurationController configurationController = new ClientConfigurationController();
        configurationController.readConfig();
        configurationController.applyConfig();

        // 启动一个连接检查线程
        Connection.startConnectionCheck();

        // 一个默认的界面是订单审查界面，作为loginDlg的owner
        SimplifiedOrderUI simplifiedOrderUI = new SimplifiedOrderUI();


        LoginMessage loginMessage = new LoginMessage(ResultMessage.FAILED);

        // 显示登录界面
        LoginDlg loginDlg = new LoginDlg(simplifiedOrderUI, loginMessage);
        loginDlg.setVisible(true);




        // 以下是，登录动作完成后的界面跳转。
        if (loginMessage.getResult() == ResultMessage.FAILED || loginMessage.getResult() == ResultMessage.NOTEXIST) System.exit(1);
        else {
            if (loginMessage.getUserSN() == 0) {                          // 匿名用户，进入订单查询界面
                simplifiedOrderUI.setVisible(true);
            }
            if (loginMessage.getUserRole() == UserRole.快递员) {           // 快递员登录
                OrderUI orderUI = new OrderUI(loginMessage);
                orderUI.setVisible(true);
            } else
            if (loginMessage.getUserRole() == UserRole.仓库管理员) {         // 仓库管理员登录
                StorageFrame storageUI = new StorageFrame(loginMessage);
                storageUI.setVisible(true);
            } else
            if (loginMessage.getUserRole() == UserRole.营业厅业务员) {        // 营业厅业务员登录
                TransferHallFrame transferHall = new TransferHallFrame(loginMessage);
                transferHall.setVisible(true);
            } else
            if (loginMessage.getUserRole() == UserRole.系统管理员) {         // 系统管理员
                UserMngUI userMngUI = new UserMngUI();
                userMngUI.setVisible(true);
            }
            if (loginMessage.getUserRole() == UserRole.总经理) {           // 总经理界面
                companyManage companyUI = new companyManage(loginMessage);
                companyUI.setVisible(true);
            } else
            if (loginMessage.getUserRole() == UserRole.普通财务人员) {          // 财务人员界面
                FINANCE finance = new FINANCE();
                finance.setVisible(true);
            } else
            if (loginMessage.getUserRole() == UserRole.中转中心业务员) {   // 中转中心业务员界面
                TransferCenterFrame transferUI = new TransferCenterFrame(loginMessage);
                transferUI.setVisible(true);
            }
        }
    }
}
