package main;

import data.enums.DataType;
import data.enums.POType;
import data.po.ArrivalPO;
import data.po.DataPO;
import data.service.DataService;
import data.service.OrderDataService;
import utils.DataServiceFactory;

import java.rmi.RemoteException;

/**
 * Created by mist on 2015/12/17 0017.
 */
public class Test {
    public static void main(String[] args) {
    	DataService d = DataServiceFactory.getDataServiceByType(DataType.TransferDataService);
    	try {
			ArrivalPO a = (ArrivalPO) d.search(POType.ARRIVAL, 9105);
			System.out.println(a.getState());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
