package main;

import java.rmi.RemoteException;

import utils.DataServiceFactory;
import data.enums.DataType;
import data.enums.POType;
import data.po.ArrivalPO;
import data.po.DataPO;
import data.service.DataService;

public class Test {
	public static void main(String[] args) {
		DataService ds = DataServiceFactory.getDataServiceByType(DataType.TransferDataService);
		try {
			for (DataPO data: ds.asdfghjkl()) {
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
