import javax.swing.*;

import data.enums.DataType;
import data.enums.POType;
import data.enums.UserRole;

import data.factory.DataServiceFactory;
import data.po.UserPO;
import data.service.UserDataService;
import org.jb2011.lnf.windows2.Windows2LookAndFeel;

import data.message.LoginMessage;
import data.message.ResultMessage;
import presentation.company.AddManager;
import presentation.order.OrderUI;
import presentation.order.SimplifiedOrderUI;
import presentation.storage.StorageFrame;
import presentation.transfer.center.TransferCenterFrame;
import presentation.user.login.LoginDlg;
import presentation.user.userMngUI.UserMngUI;
import utils.Connection;

import java.rmi.RemoteException;

/**
 *
 * Created by mist on 2015/11/13 0013.
 */
public class LCSClient extends JFrame{

    public static void main(String[] args) {
        AddManager addManager = new AddManager();
        addManager.add();
        // 一些数据的初始化操作
        UserPO user1 = new UserPO(10000, "系统管理员", "123456", UserRole.SYS_ADMIN);
        UserPO user2 = new UserPO(10001, "营业厅业务员", "123456", UserRole.BO_CLERK);
        UserPO user3 = new UserPO(10002, "中转中心业务员", "123456", UserRole.TC_CLERK);
        UserPO user4 = new UserPO(10003, "仓库管理员", "123456", UserRole.STOCK_ADMIN);
        UserPO user5 = new UserPO(10004, "总经理", "123456", UserRole.TOP_MNGR);

        UserDataService userDataService = (UserDataService) DataServiceFactory.getDataServiceByType(DataType.UserDataService);
        try {
            userDataService.add(user1);
            userDataService.add(user2);
            userDataService.add(user3);
            userDataService.add(user4);
            userDataService.add(user5);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        // 数据初始化操作结束


        try {
			UIManager.setLookAndFeel(new Windows2LookAndFeel());
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        // 启动一个连接检查线程
        Connection.startConnectionCheck();

        // 一个默认的界面是订单审查界面，作为loginDlg的owner
        SimplifiedOrderUI simplifiedOrderUI = new SimplifiedOrderUI();


		LoginMessage loginMessage = new LoginMessage(ResultMessage.FAILED);

        // 显示登录界面
		LoginDlg loginDlg = new LoginDlg(simplifiedOrderUI, loginMessage);
        loginDlg.setVisible(true);

        // 以下是，登录动作完成后的界面跳转。
        if (loginMessage.getResult() == ResultMessage.FAILED) System.exit(1);
        else {
            if (loginMessage.getUserSN() == 0) {    // 匿名用户，进入订单查询界面
                simplifiedOrderUI.setVisible(true);
            }
            if (loginMessage.getUserRole() == UserRole.COURIER) {   // 快递员登录
                OrderUI orderUI = new OrderUI(loginMessage);
                orderUI.setVisible(true);
            } else
            if (loginMessage.getUserRole() == UserRole.STOCK_ADMIN) {   // 仓库管理员登录
                StorageFrame storageUI = new StorageFrame(loginMessage);
                storageUI.setVisible(true);
            } else
            if (loginMessage.getUserRole() == UserRole.BO_CLERK) {  // 营业厅业务员登录
                TransferCenterFrame transferUI = new TransferCenterFrame(loginMessage);
                transferUI.setVisible(true);
            } else
            if (loginMessage.getUserRole() == UserRole.SYS_ADMIN) {
                UserMngUI userMngUI = new UserMngUI();
                userMngUI.setVisible(true);
            }
        }
    }
}
