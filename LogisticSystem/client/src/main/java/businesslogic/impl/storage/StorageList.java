package businesslogic.impl.storage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import utils.Timestamper;
import data.enums.POType;
import data.message.ResultMessage;
import data.po.DataPO;
import data.po.StorageInListPO;
import data.po.StorageListPO;
import data.service.StorageDataService;
import data.vo.StorageInVO;

public class StorageList {
	StorageDataService storageData;
	ArrayList<DataPO> checkedstorageList;
	POType storageListType;
	public StorageListPO getCheckedStorageList(long listID) throws RemoteException{
		for(DataPO s : checkedstorageList){
			if(s.getSerialNum() == listID){
				return (StorageListPO) s;
			}
		}
		return null;
	}
	
	
	public ResultMessage saveStorageInList(StorageInVO vo) throws RemoteException {
		StorageInListPO storageInPO = new StorageInListPO(vo);
		return storageData.add(storageInPO);
	}
	

	
	public String[][] getBriefStorageList(){
		if (checkedstorageList == null) {
			return null;
		}
		System.out.println("��ⵥ������"+checkedstorageList.size());
		String[][] storageListInfo = new String[checkedstorageList.size()][2];
		for (int i = 0; i < checkedstorageList.size(); i++) {
			DataPO s =  checkedstorageList.get(i);
			String[] info = new String[2];
			info[0] = s.getSerialNum() + "";
			info[1] = Timestamper.getTimeByDate(s.getGenDate());
			storageListInfo[i] = info;
		}
		return storageListInfo;

			
		
	}
	
	
	public StorageList(StorageDataService storageData, long centerID,POType storageListType) throws RemoteException {
		this.storageData = storageData;
		this.storageListType = storageListType;
		checkedstorageList = storageData.getNewlyApprovedPO(storageListType, centerID);
	}


	public StorageListPO getStorageList(long storageListID) throws RemoteException {
		if(storageListType == POType.STORAGEINLIST)
		return (StorageListPO) storageData.search(POType.STORAGEINLIST, storageListID);
		else 
			return (StorageListPO) storageData.search(POType.STORAGEOUTLIST, storageListID);
	}

}
