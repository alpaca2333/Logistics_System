package businesslogic.impl.storage;

import java.rmi.RemoteException;
import data.message.LoginMessage;
import data.enums.DataType;
import data.factory.DataServiceFactory;
import data.service.StorageDataService;
import businesslogic.impl.user.InstitutionInfo;
import businesslogic.service.storage.StorageBusinessService;
import businesslogic.service.storage.StorageInService;
import businesslogic.service.storage.StorageOperateService;
import businesslogic.service.storage.StorageOutService;

/**
 * !!mock!!
 * @author xu
 *
 */
public class StorageBusinessController implements StorageBusinessService {
	InstitutionInfo center;
	StorageInfo storageInfo;
	StorageDataService storageData;

	@Override
	public StorageInService startStorageIn() {
		if (!isStorageExist()) {
			return null;
		}

		try {
			return new StorageIn(center, storageInfo, storageData);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public StorageOutService startStorageOut() {
		if (!isStorageExist()) {
			return null;
		}
		try {
			return new StorageOut(center, storageInfo, storageData);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * �ֿ��Ƿ����
	 * @return
	 */
	private boolean isStorageExist() {
		return storageInfo.isStorageExist();
	}

	public StorageBusinessController(LoginMessage user) throws Exception {
		storageData = (StorageDataService) DataServiceFactory
				.getDataServiceByType(DataType.StorageDataService);
		//storageData = new MockStorageDataService();
		this.center = new InstitutionInfo(user);
		storageInfo = new StorageInfo(storageData, center.getCenterID());
	}

	@Override
	public StorageOperateService startStorageOperate() throws RemoteException {
		return new StorageOperate(center, storageData,storageInfo);
	}

}
