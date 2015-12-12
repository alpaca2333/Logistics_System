package businesslogic.impl.order;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import businesslogic.service.order.OrderListService;
import data.enums.DataType;
import data.enums.POType;
import utils.DataServiceFactory;
import data.po.DataPO;
import data.po.OrderPO;
import data.service.OrderDataService;
import data.vo.BriefOrderVO;

public class OrderList implements OrderListService {
	ArrayList<OrderPO> orders;
	
	public ArrayList<OrderPO> getOrderList(long[] orderID) {
		ArrayList<OrderPO> order = new Order().search(orderID);
		return order;
	}

	public void modifyOrder(ArrayList<Long> orderID,String info) {
		ArrayList<Long> orderNum = orderID;
		long[] orderNumL = new long[orderNum.size()];
		for (int i = 0; i < orderNum.size(); i++) {
			orderNumL[i] = orderNum.get(i);
		}
		ArrayList<OrderPO> order = new Order().search(orderNumL);
		for (int i = 0; i < order.size(); i++) {
			// 修改订单物流信息
		}
	}
	
	public void modifyOrderPosition(ArrayList<Long> orderID){
		for (int i = 0; i < orderID.size(); i++) {
			for (OrderPO o: orders) {
				if (o.getSerialNum() == orderID.get(i)) {
					o.updateRoutine();
				}
			}
		}
	}

	public BriefOrderVO getFreshOrder(long institution, long destID) {
		orders = new ArrayList<>();
		OrderDataService orderDataService = (OrderDataService) DataServiceFactory
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
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		for (OrderPO d : orders) {
			if (d.getNextDestination() != destID) {
				orders.remove(d);
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
