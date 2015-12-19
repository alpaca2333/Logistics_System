package data.vo;

import java.util.ArrayList;

public class TransferLoadVO {
	public String[][] getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String[][] orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String[] header = {"������","����","�ź�","�ܺ�", "λ��","������kg��"};
	public String[][] orderInfo;
	
	public TransferLoadVO(ArrayList<String> orderInfo){
		this.orderInfo = new String[orderInfo.size()][6];
		for(int i = 0 ; i < orderInfo.size();i++){
			String[] s = orderInfo.get(i).split("-");
			this.orderInfo[i] =s;
		}
	}
}
