package main;

import java.rmi.RemoteException;
import java.util.ArrayList;

import data.enums.DataType;
import data.enums.POType;
import data.po.DataPO;
import data.po.OrderPO;
import data.service.DataService;
import data.service.StorageDataService;
import utils.DataServiceFactory;

public class Test_sx {
	public static void main(String[] args) {
//		ArrayList<DataPO> order = null;
//		DataService d =  DataServiceFactory.getDataServiceByType(DataType.OrderDataService);
//			try {
//				order = d.getPOList(POType.ORDER);
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		for (int i = 0; i < order.size(); i++) {
//			OrderPO o = (OrderPO) order.get(i);
//			System.out.println(o.getNextDestination());
//			o.updateRoutine();
//		
//		}
//		
//		try {
//			order = d.getPOList(POType.ORDER);
//			for (int i = 0; i < order.size(); i++) {
//				OrderPO o = (OrderPO) order.get(i);
//				System.out.println(o.getNextDestination());
//			}
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		
//		try {
//			ArrayList<DataPO> entruck = DataServiceFactory.getDataServiceByType(DataType.TransferDataService).getPOList(POType.ENTRUCK);
//			for (int i = 0; i < entruck.size(); i++) {
//				System.out.println(entruck.get(i).getSerialNum());
//			}
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		StorageDataService d = (StorageDataService) DataServiceFactory.getDataServiceByType(DataType.StorageDataService);
		try {
			ArrayList<DataPO> i = d.getNewlyApprovedPO(POType.STORAGEINLIST, 10021);
			for (int j = 0; j < i.size(); j++) {
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
 	}
}
