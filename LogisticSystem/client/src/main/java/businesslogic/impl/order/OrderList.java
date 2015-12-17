package businesslogic.impl.order;

import java.rmi.RemoteException;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

import businesslogic.service.order.OrderListService;
import data.enums.DataType;
import data.enums.POType;
import data.message.LoginMessage;
import utils.DataServiceFactory;
import utils.Timestamper;
import data.po.DataPO;
import data.po.LogisticInfoPO;
import data.po.OrderPO;
import data.service.OrderDataService;
import data.vo.BriefOrderVO;

public class OrderList implements OrderListService {
	ArrayList<OrderPO> orders;
	OrderDataService orderDataService;
	LoginMessage loginMessage = null;
	
	public ArrayList<OrderPO> getOrderList(long[] orderID) {
		ArrayList<OrderPO> order = new Order(loginMessage).search(orderID);
		return order;
	}
	
	public OrderList(LoginMessage loginMessage){
		orderDataService = (OrderDataService) DataServiceFactory.getDataServiceByType(DataType.OrderDataService);
		this.loginMessage = loginMessage;
	}

	public void modifyOrder(ArrayList<Long> orderID,String info) throws RemoteException {
		ArrayList<Long> orderNum = orderID;
		long[] orderNumL = new long[orderNum.size()];
		for (int i = 0; i < orderNum.size(); i++) {
			orderNumL[i] = orderNum.get(i);
		}
		ArrayList<OrderPO> order = new Order(loginMessage).search(orderNumL);
		for (int i = 0; i < order.size(); i++) {
			// 修改订单物流信息
			LogisticInfoPO log = (LogisticInfoPO) orderDataService.search(POType.LOGISTICINFO, order.get(i).getSerialNum());
			log.addInfo(Timestamper.getTimeByDate(), info);
		}
	}
	
	public void modifyOrderPosition(ArrayList<Long> orderID){
		long[] id = new long[orderID.size()];
		for (int i = 0; i < orderID.size(); i++) {
			id[i] = orderID.get(i);
		}
		orders = new Order(loginMessage).search(id);
			for (int i = 0;i< orderID.size();i++) {
				System.out.println("modify next position");
					orders.get(i).updateRoutine();
			}
		
	}

	public BriefOrderVO getFreshOrder(long institution, long destID) {
		orders = new ArrayList<>();
		orderDataService = (OrderDataService) DataServiceFactory
				.getDataServiceByType(DataType.OrderDataService);
		if (orderDataService == null)
			return null;
		try {
			for (DataPO data : orderDataService.getPOList(POType.ORDER)) {
				OrderPO order = (OrderPO) data;
				if (order.isFresh()
						&& order.getPresentLocation() == institution) {
					orders.add(order);
				}
			}
			System.out.println("all order:"+ orders.size());
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		for (int i = 0 ; i < orders.size();i++) {
			System.out.println(orders.get(i).getNextDestination());
			System.out.println(destID);
			if (orders.get(i).getNextDestination() != destID) {
				System.out.println(orders.get(i).getNextDestination());
				orders.remove(i);
				--i;
			}
		}

		Vector<Vector<String>> info = new Vector<Vector<String>>();
		for (int i = 0; i < orders.size(); i++) {
			Vector<String> row = new Vector<String>();
			OrderPO oo = orders.get(i);
			row.add(oo.getSerialNum() + "");
			row.add(oo.getWeight() + "");
			info.add(row);
		}
		return new BriefOrderVO(info);
	}

	@Override
	public ArrayList<OrderPO> search(long[] order) {
		ArrayList<OrderPO> result = new ArrayList<>();
		for (long sn : order) {
			OrderPO tmp = search(sn);
			if (tmp != null) {
				result.add(tmp);
			}
		}
		return result;
	}

	public OrderPO search(long sn) {
		OrderDataService orderDataService = (OrderDataService) DataServiceFactory
				.getDataServiceByType(DataType.OrderDataService);
		if (orderDataService == null)
			return null;
		OrderPO result = null;
		try {
			result = (OrderPO) orderDataService.search(POType.ORDER, sn);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

}
