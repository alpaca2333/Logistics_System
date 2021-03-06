package businesslogic.impl.transfer.hall;

import java.rmi.RemoteException;
import java.util.ArrayList;

import businesslogic.impl.user.InstitutionInfo;
import data.enums.DataType;
import data.enums.POType;
import utils.DataServiceFactory;
import utils.Timestamper;
import data.message.ResultMessage;
import data.po.DataPO;
import data.po.EntruckPO;
import data.service.TransferDataService;
import data.vo.BriefEntruckListVO;
import data.vo.EntruckListVO;

public class EntruckList {
	ArrayList<DataPO> entruckList;
	EntruckPO choosedEntruck;
	TransferDataService transferData;

	public EntruckList(TransferDataService transferData) {
		this.transferData = transferData;
	}
	
	
	public EntruckListVO searchEntruck(long id) throws RemoteException{
		EntruckPO entruck = (EntruckPO) transferData.search(POType.ENTRUCK, id);
		if (entruck != null) {
			return new EntruckListVO(entruck);
		}else{
			return null;
		}
	}

	public EntruckList(){
		this.transferData = (TransferDataService) DataServiceFactory.getDataServiceByType(DataType.TransferDataService);
	}
	public long[] getOrder() {
		ArrayList<Long> order = choosedEntruck.getOrderList();
		long[] o = new long[order.size()];
		for (int i = 0; i < order.size(); i++) {
			o[i] = order.get(i);
		}

		return o;
	}

	public EntruckListVO chooseEntruckList(long id) {
		for (DataPO d : entruckList) {
			if (d.getSerialNum() == id) {
				choosedEntruck = (EntruckPO) d;
				return new EntruckListVO((EntruckPO) d);
			}
		}

		return null;
	}

	public BriefEntruckListVO getEntruckList(long institutionID)
			throws RemoteException {
		entruckList = transferData.getNewlyApprovedPO(POType.ENTRUCK,
				institutionID);
		System.out.println(institutionID);
		System.out.println(entruckList.size());
		if (entruckList != null) {//获取成功则显示
			String[][] info = new String[entruckList.size()][2];
			for (int i = 0; i < entruckList.size(); i++) {
				EntruckPO e = (EntruckPO) entruckList.get(i);
				String[] in = { e.getSerialNum() + "", e.getLoadingDate() };
				info[i] = in;
			}
			return new BriefEntruckListVO(info);
		} else {
			return null;//不成功返回null
		}
	}

	public ResultMessage saveEntruckList(EntruckListVO entruckList)
			throws RemoteException {
		EntruckPO entruckPO = new EntruckPO();
		//装车单号格式：
		long entruckID = Long.parseLong(entruckList.fromID)*10000+ entruckPO.getSerialNum();
		entruckPO.setSerialNum(entruckID);
		entruckPO.setFromName(entruckList.fromName);
		entruckPO.setVehicleID(Long.parseLong(entruckList.vehicleID));
		entruckPO.setDestID(Long.parseLong(entruckList.destID));
		entruckPO.setDestName(entruckList.destName);
		entruckPO.setEscortID(entruckList.escortID);
		entruckPO.setEscortName(entruckList.escortName);
		entruckPO.setFromID(Long.parseLong(entruckList.fromID));
		entruckPO.setLoadingDate(entruckList.loadingDate);
		entruckPO.setMonitorName(entruckList.monitorName);
		ArrayList<Long> order = new ArrayList<Long>();
		String[][] info = entruckList.info;
		for (int i = 0; i < info.length; i++) {
			order.add(Long.parseLong(info[i][0]));
		}
		entruckPO.setOrderList(order);
		return transferData.add(entruckPO);

	}

	public EntruckListVO createEntruckList(String[][] orders,
			InstitutionInfo user, String desName, String destID,String driverID,
			String driverName, String truckID) throws RemoteException {
		EntruckListVO entruck = new EntruckListVO();
		entruck.loadingDate = Timestamper.getTimeByDate();
		System.out.println(entruck.loadingDate);
		entruck.escortID = user.getStaffID();
		entruck.info = orders;
		entruck.destName = desName;
		entruck.destID = destID;
		entruck.escortName = driverName;
		entruck.fromID = user.getInstitutionID() + "";
		entruck.fromName = user.getInstitutionName();
		entruck.monitorName = user.getStaffName();
		entruck.driverID = driverID;
		entruck.driverName = driverName;
		entruck.vehicleID = truckID;
		
		return entruck;
	}
}
