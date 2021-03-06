package businesslogic.impl.storage;

import java.rmi.RemoteException;
import java.util.ArrayList;

import businesslogic.impl.order.OrderList;
import businesslogic.impl.transfer.hall.ArrivalList;
import businesslogic.impl.user.InstitutionInfo;
import businesslogic.service.order.OrderListService;
import businesslogic.service.storage.StorageInService;
import data.enums.DataType;
import data.enums.POType;
import data.enums.StockStatus;
import data.message.LoginMessage;
import utils.DataServiceFactory;
import utils.Timestamper;
import data.message.ResultMessage;
import data.po.ArrivalPO;
import data.po.OrderPO;
import data.po.StorageInListPO;
import data.service.StorageDataService;
import data.service.TransferDataService;
import data.vo.ArrivalListVO;
import data.vo.ArrivalVO;
import data.vo.BriefArrivalAndStorageInVO;
import data.vo.StorageInVO;

/**
 * 入库服务 实现类
 * @author xu
 *
 */
public class StorageIn implements StorageInService{
	InstitutionInfo user;
	StorageInfo storageInfo;
	ArrivalList arrivalList;
	StorageList storageInList;
	OrderList orderList;
	/**
	 * 
	 * 一次新的入库活动，1.新建一个入库单，2.完成所有已审批入库单的入库操作
	 * @return 到达单列表，已审批的入库单列表
	 * @throws RemoteException 
	 */
	public BriefArrivalAndStorageInVO newStorageIn() throws RemoteException {
		// listID+date
		String[][] arrivelistInfo = null;
		String[][] storageInListInfo = null;
			storageInListInfo = storageInList.getBriefStorageList();
			System.out.println("仓库："+user.getCenterID());
			ArrivalListVO arrivals = arrivalList.getCheckedArrivals(user.getCenterID());
			arrivelistInfo = arrivals.info;
			System.out.println("网络连接中断");

		return new BriefArrivalAndStorageInVO(arrivelistInfo, storageInListInfo);

	}

	/**
	 * 
	 * 要求查看一个到达单详细信息
	 * 
	 * @param arriveListcode
	 * @return 到达单显示信息
	 * @throws RemoteException
	 */
	public ArrivalVO getArriveList(long arriveListcode){
		ArrivalPO a=  arrivalList.chooseArrival(arriveListcode);
		if(a!= null)
		return new ArrivalVO(a);
		else {
			return null;
		}
	}
	
	
	

	/**
	 * 
	 *
	 * 要求查看一个入库单信息
	 *
	 * @return 入库单显示信息
	 * @throws RemoteException
	 */
	public StorageInVO getStorageIn(long storageInID)  {
	try {
		StorageInListPO storageInListPO = (StorageInListPO) storageInList.getCheckedStorageList(storageInID);
		return new StorageInVO(storageInListPO);
	} catch (RemoteException e) {
		System.out.println("网络连接中断");
		e.printStackTrace();
		return null;
	}
	}

	/**
	 * 给订单分配入库位置
	 * 
	 *
	 * @return 入库单展示信息
	 * @throws RemoteException
	 */
	public StorageInVO sort(ArrivalVO arrival) {
		long[] orderID = arrivalList.getOrderID(arrival);
		ArrayList<OrderPO> order = orderList.getOrderList(orderID);
		try {
			StorageInVO s=  storageInfo.allocateSpace(order);
			if(s == null){
				return null;
			}
			s.setDate(Timestamper.getTimeByDate());
			s.centerID = user.getCenterID();
			return s;
		} catch (RemoteException e) {
			System.out.println("网络连接中断");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 修改到达单状态为已入库
	 */
	private void modifyArriveListState() {
		arrivalList.modifyArriveListState();
	}

	/**
	 * 修改订单物流信息(未完)
	 * @throws RemoteException 
	 */
	private void modifyOrder(ArrayList<Long> orderID) throws RemoteException {
		long[] o = new long[orderID.size()];
		for (int i = 0; i < o.length; i++) {
			o[i] = orderID.get(i);
		}
		//orderList.modifyOrder(o,"到达"+user.getInstitutionName()+"中转中心");
	}

	/**
	 * 
	 * 对当前所有入库单进行入库操作
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ResultMessage doStorageIn(long StorageInID) throws RemoteException{
			StorageInListPO storageIn = null;
			try {
				storageIn = (StorageInListPO) storageInList.getStorageList(StorageInID);
			} catch (RemoteException e) {
				e.printStackTrace();
				return ResultMessage.FAILED;
			}
			modifyOrder(storageIn.getOrder());
		return ResultMessage.SUCCESS;
	}

	/**
	 * 确认入库后，更新库存信息，将到达单状态修改为已入库
	 * @return result
	 * @throws RemoteException
	 */
	public ResultMessage saveStorageInList(StorageInVO vo) throws RemoteException{
		//modifyArriveListState();
			storageInfo.modifyStorageInfo(vo);
			storageInfo.saveStorageInfo();
			return storageInList.saveStorageInList(vo,user.getInstitutionID());

	}

	public StorageIn(InstitutionInfo user,StorageInfo storageInfo,StorageDataService storageData) throws RemoteException {
		TransferDataService transferData = (TransferDataService) DataServiceFactory
				.getDataServiceByType(DataType.TransferDataService);
		this.user = user;
		this.storageInfo = storageInfo;
		orderList = new OrderList(new LoginMessage(ResultMessage.FAILED));
		arrivalList = new ArrivalList(transferData);
		storageInList = new StorageList(storageData, user.getCenterID(), POType.STORAGEINLIST);
	}

	@Override
	public ResultMessage doArrive() throws RemoteException{
		long[] roundOrder = null;
		long[] lostOrder = null;
		long[] damagedOrder = null;
		try {
		roundOrder =  arrivalList.getOrder(StockStatus.ROUND);
		lostOrder = arrivalList.getOrder(StockStatus.LOST);
		damagedOrder = arrivalList.getOrder(StockStatus.DAMAGED);
		} catch (RemoteException e) {
			e.printStackTrace();
			return ResultMessage.FAILED;
		}
		//修改订单信息
		OrderListService orderList = new OrderList(new LoginMessage(ResultMessage.SUCCESS));
		orderList.modifyOrder(roundOrder, "订单到达"+user.getInstitutionName()+"中转中心");
		orderList.modifyOrder(lostOrder, "订单于"+user.getInstitutionName()+"丢失");
		orderList.modifyOrder(damagedOrder, "货物于"+user.getInstitutionName()+"破损");
		//orderList.modifyOrderPosition(roundOrder);
		//orderList.modifyOrderPosition(damagedOrder);
		return ResultMessage.SUCCESS;
		
	}



}
