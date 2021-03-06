package businesslogic.impl.transfer.hall;

import java.rmi.RemoteException;
import java.util.ArrayList;

import utils.Timestamper;
import data.enums.DataType;
import data.enums.POType;
import data.enums.StockStatus;
import utils.DataServiceFactory;
import data.message.ResultMessage;
import data.po.ArrivalPO;
import data.po.DataPO;
import data.service.TransferDataService;
import data.vo.ArrivalListVO;
import data.vo.ArrivalVO;
import data.vo.EntruckListVO;
import data.vo.TransferListVO;

/**
 * 到达单相关服务
 * 
 * @author xu
 *
 */
public class ArrivalList {
	TransferDataService transferData;
	ArrayList<DataPO> checkedArrivals;
	ArrivalPO choosenArrival;

	/**
	 * 确认到达，修改到达单状态信息（待确认）
	 * 
	 * @return 该到达单单号列表
	 * @throws RemoteException
	 */
	public long[] getOrder(StockStatus status) throws RemoteException {
		// choosenArrival.setOperated(true);
		// transferData.modify(choosenArrival);
		return choosenArrival.getOrder(status);

	}

	public long[] getOrder() {
		return choosenArrival.getOrder();
	}

	/**
	 * 修改到达单状态为已入库
	 */
	public void modifyArriveListState() {
		if (choosenArrival != null) {
			choosenArrival.setOperated(true);
		}
	}

	public ArrivalList(TransferDataService transferData) {
		this.transferData = transferData;
	}

	public ArrivalList() {
		this.transferData = (TransferDataService) DataServiceFactory
				.getDataServiceByType(DataType.TransferDataService);
	}

	public ArrivalListVO getCheckedArrivals(long institutionID)
			throws RemoteException {
		checkedArrivals = transferData.getNewlyApprovedPO(POType.ARRIVAL,
				institutionID);
		System.out.println(checkedArrivals.size());
		if (checkedArrivals != null) {
			String[][] info = new String[checkedArrivals.size()][2];
			for (int i = 0; i < checkedArrivals.size(); i++) {
				ArrivalPO arrival = (ArrivalPO) checkedArrivals.get(i);
				String[] in = { arrival.getSerialNum() + "", arrival.getDate() };
				info[i] = in;
			}
			return new ArrivalListVO(info);
		} else {
			return null;
		}
	}

	public ArrivalPO chooseArrival(long ID) {
		if (checkedArrivals != null) {
			for (DataPO a : checkedArrivals) {
				if (a.getSerialNum() == ID) {
					choosenArrival = (ArrivalPO) a;
					return choosenArrival;
				}
			}
			return null;
		} else {
			return null;
		}
	}

	public long[] getOrderID(ArrivalVO vo) {
		String[][] info = vo.getOrderAndStatus();
		ArrayList<Long> id = new ArrayList<Long>();
		for (int i = 0; i < info.length; i++) {
			if (info[i][1].equals("ROUND") || info[i][1].equals("DAMAGED") || info[i][1].equals("完整") || info[i][1].equals("破损")) {
				id.add(Long.parseLong(info[i][0]));
			}
		}
		long[] ids = new long[id.size()];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = id.get(i);
		}
		return ids;
	}

	public ArrivalVO createArrival(TransferListVO transferList) {
		ArrivalVO vo = new ArrivalVO();
		vo.setDate(Timestamper.getTimeByDate());
		vo.setDeliveryListNum(transferList.transferListID);
		vo.setFromName(transferList.transferCenter);
		vo.setFromNum(transferList.transferCenterID);
		vo.setDestID(transferList.target);
		vo.setDestName(transferList.targetName);
		String[][] info = transferList.orderAndPosition;
		String[][] orderAndStatus = new String[info.length][2];
		for (int i = 0; i < info.length; i++) {
			String[] in = { info[i][0], "完整" };
			orderAndStatus[i] = in;
		}

		vo.setOrderAndStatus(orderAndStatus);

		return vo;
	}

	public ArrivalVO createArrival(EntruckListVO entruck) {
		ArrivalVO vo = new ArrivalVO();
		vo.setDestName(entruck.destName);
		vo.setDestID(entruck.destID);
		System.out.println("目的地：" + vo.getDestID());
		vo.setDate(entruck.loadingDate);
		vo.setDeliveryListNum(entruck.entruckListID);
		vo.setFromName(entruck.fromName);
		vo.setFromNum(entruck.fromID);
		String[][] orders = entruck.info;
		for (int i = 0; i < orders.length; i++) {
			orders[i][1] = "完整";
		}
		vo.setOrderAndStatus(orders);
		return vo;
	}

	public ResultMessage saveArrival(ArrivalVO vo,long institutionID) throws RemoteException {
		ArrivalPO arrivalPO = new ArrivalPO();
		arrivalPO.setSerialNum(institutionID*10000+arrivalPO.getSerialNum());
		arrivalPO.setDestID(Long.parseLong(vo.getDestID()));
		arrivalPO.setDate(vo.getDate());
		arrivalPO.setFrom(Long.parseLong(vo.getFromNum()));
		arrivalPO.setFromName(vo.getFromName());
		arrivalPO.setDeliveryListID(Long.parseLong(vo.getDeliveryListNum()));
		ArrayList<Long> order = new ArrayList<>();
		ArrayList<StockStatus> status = new ArrayList<StockStatus>();
		String[][] orders = vo.getOrderAndStatus();
		for (int i = 0; i < orders.length; i++) {
			long ID = Long.parseLong(orders[i][0]);
			order.add(ID);
			switch (orders[i][1]) {
			case "完整": {
				status.add(StockStatus.ROUND);
				break;
			}
			case "破损": {
				status.add(StockStatus.DAMAGED);
				break;
			}
			default: {
				status.add(StockStatus.LOST);
				break;
			}
			}
		}
		arrivalPO.setOrder(order);
		arrivalPO.setStockStatus(status);
		return transferData.add(arrivalPO);
	}
}
