package businesslogic.service.order;

import data.po.OrderPO;
import data.vo.BriefOrderVO;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by mist on 2015/12/11 0011.
 */
public interface OrderListService {
	/**
	 * 获取新订单（营业厅）
	 * @param institution 当前营业厅编号
	 * @param destID 目标地编号
	 * @return 订单列表
	 */
	public BriefOrderVO getFreshOrder(long institution,long destID) ;
	
	public void modifyOrder(long[] orderID,String info) throws RemoteException;
	
	public void modifyOrderPosition(long[] orderID) throws RemoteException;
	
    ArrayList<OrderPO> search(long[] order);
}
