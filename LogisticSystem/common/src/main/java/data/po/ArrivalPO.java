package data.po;
import data.enums.POType;
import data.enums.StockStatus;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * 
 * @author xu
 *
 */
public class ArrivalPO extends DataPO {

	private static final long serialVersionUID = 2;
	
	public ArrivalPO(TransferListPO transfer){
		super(POType.ARRIVAL);
		destID = transfer.getSerialNum();
		order = transfer.getOrderArray();
		stockStatus = new ArrayList<StockStatus>();
		for(int i = 0 ; i < order.size();i++){
			stockStatus.add(StockStatus.ROUND);
		}
		from = transfer.getTransferCenter();
		isOperated = false;
	
		//date
	}
	
	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}


	public ArrivalPO(EntruckPO entruck){
		super(POType.ARRIVAL);
		destID = entruck.getDestID();
		order = entruck.getOrderList();
		stockStatus = new ArrayList<StockStatus>();
		for(int i = 0 ; i < order.size();i++){
			stockStatus.add(StockStatus.ROUND);
		}
		from = entruck.getFrom();
		isOperated = false;

		//date
	}



	public boolean isOperated() {
		return isOperated;
	}

	public void setOperated(boolean isOperated) {
		this.isOperated = isOperated;
	}



	//到达单号
	//中转中心编号
	//订单号
	//订单状态
	//日期：yyyy-mm-dd
	//出发机构编号
    long destID;
    String date;
    String destName;
	ArrayList<Long> order;
    ArrayList<StockStatus> stockStatus;
    long from;//ID
    String fromName;

    boolean isOperated = false;//是否入库（仓库），是否修改订单信息（营业厅，中转中心）

    public ArrivalPO() {
    	
        super(POType.ARRIVAL);
    }
    
    
    
    public long getDestID() {
		return destID;
	}

	public void setDestID(long destID) {
		this.destID = destID;
	}

	public String getDestName() {
		return destName;
	}

	public void setDestName(String destName) {
		this.destName = destName;
	}


    public long getTransferList() {
        return destID;
    }

    public void setTransferList(long transferList) {
        this.destID = transferList;
    }



    public long[] getOrder() {
    	long[] result = new long[order.size()];
    	for (int i = 0; i < result.length; ++i) {
    		result[i] = order.get(i);
    	}
        return result;
    }

    public void setOrder(ArrayList<Long> order) {
        this.order = order;
    }


    public ArrayList<StockStatus> getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(ArrayList<StockStatus> stockStatus) {
        this.stockStatus = stockStatus;
    }

    public void addStckStatus(StockStatus stockStatus) {
        this.stockStatus.add(stockStatus);
    }

    public StockStatus getStockStatus(long code) {
        StockStatus state = StockStatus.ROUND;
        return state;
    }

    public void setStockStatus(long code, StockStatus state) {

    }

    public String getDate() {
        return date;
    }


    
    public long getFrom() {
  		return from;
  	}

  	public void setFrom(long from) {
  		this.from = from;
  	}

  	public void setDate(String date) {
  		this.date = date;
  	}

	public void addOrder(long order) {
		this.order.add(order);
	}


}
