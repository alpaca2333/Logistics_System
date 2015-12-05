package businesslogic.impl.order;

import data.enums.*;
import data.factory.DataServiceFactory;
import data.message.ResultMessage;
import data.po.*;
import data.service.*;
import data.vo.OrderVO;
import utils.Connection;
import utils.Timestamper;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.*;

/**
 *
 * Created by mist on 2015/11/15 0015.
 */
public class Order {

    OrderDataService orderDataService = (OrderDataService) DataServiceFactory.getDataServiceByType(DataType.OrderDataService);
    CompanyDataService companyDataService = (CompanyDataService) DataServiceFactory.getDataServiceByType(DataType.CompanyDataService);

    public OrderPO search(long sn) {
        OrderPO result = null;
        try {
            result = (OrderPO) orderDataService.search(POType.ORDER, sn);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultMessage createOrder(OrderVO order) {
        OrderPO newOrder = new OrderPO();

        // 克隆订单信息
        newOrder.setInfo(OrderPO.RADDRESS, order.raddress);
        newOrder.setInfo(OrderPO.RCOMPANY, order.rcompany);
        newOrder.setInfo(OrderPO.RNAME, order.rname);
        newOrder.setInfo(OrderPO.RPHONE, order.rphone);
        newOrder.setInfo(OrderPO.SADDRESS, order.saddress);
        newOrder.setInfo(OrderPO.SCOMPANY, order.scompany);
        newOrder.setInfo(OrderPO.SNAME, order.sname);
        newOrder.setInfo(OrderPO.SPHONE, order.sphone);
        newOrder.setStockNum(order.stockNum, order.stockType);
        newOrder.setVolume(order.volume);
        newOrder.setWeight(order.weight);
        newOrder.setServiceType(order.serviceType);
        newOrder.setFee(generateFee(order));
        newOrder.setDestID(getDestID(order.raddress));
        newOrder.setEvaluatedTime(evaluateTime(order));

        // 创建物流信息
        LogisticInfoPO logisticInfoPO =  new LogisticInfoPO(newOrder.getSerialNum());
        logisticInfoPO.addInfo(Timestamper.getTimeBySecond(), "快递员已揽件。");

        // 提交订单
        try {
            orderDataService.add(newOrder);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return ResultMessage.SUCCESS;
    }

    /**
     * 获得显示数据
     *
     * @return 包含所有OrderVO的ArrayList
     */
    public ArrayList<OrderVO> getDisplayData() {
        ArrayList<OrderVO> result = new ArrayList<>();
        ArrayList<DataPO> data = null;
        try {
            data = orderDataService.getPOList(POType.ORDER);
        } catch (RemoteException e) {
        }
        if (data == null) {
            System.err.println("获取订单数据时发生错误");
            return null;
        }
        for (DataPO dat: data) {
            result.add(new OrderVO((OrderPO) dat));
        }
        return result;
    }

    public ArrayList<String> getCityList() {
        ArrayList<String> result = new ArrayList<>();
        DataService ds = utils.DataServiceFactory.getDataServiceByType(DataType.CompanyDataService);
        try {
            for (DataPO dataPO: ds.getPOList(POType.CITYINFO)) {
                result.add(((CityInfoPO) dataPO).getName());
            }
        } catch (RemoteException e) {
            System.err.println("获取城市列表时网络连接失败");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据地址，解析出目标营业厅地址
     *
     * @param address 地址信息，必须符合 [城市]-[区]-[详细地址]的规范
     * @return 目标营业厅编号
     */
    public long getDestID(String address) {
        if (!Connection.connected) {
            System.err.println("尚未连接到服务器，无法估计目标营业厅地址" + Calendar.getInstance().getTime());
            return 0;
        }
        long destID;
        String addr[] = address.split("\\-");
        CityInfoPO cityInfoPO = null;
        CompanyDataService companyDataService = (CompanyDataService) DataServiceFactory.getDataServiceByType(DataType.CompanyDataService);

        // 根据城市名称确定目标城市
        try {
            cityInfoPO = (CityInfoPO) companyDataService.searchCity(addr[0]);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        // 根据区位信息确定营业厅编号
        if (cityInfoPO == null) return 0;
        else {
            try {
                long tmp = companyDataService.searchBusinessOffice(addr[1]);
                return tmp;
            } catch (RemoteException e) {
                System.err.println("与服务器(" + Connection.RMI_PREFIX + ")的连接断开 -" + Calendar.getInstance().getTime());
            }
        }
        return 0;
    }

    public int evaluateTime(OrderVO orderVO) {
        ArrayList<DataPO> orders = null;
        float time = 0.0f;
        if (!Connection.connected) {
            System.err.println("尚未连接到服务器，无法预计送达时间" + Calendar.getInstance().getTime());
            return 0;
        }
        try {
            orders = orderDataService.searchByLoc(orderVO.saddress.substring(0, 3) + "-" + orderVO.raddress.substring(0, 3));
        } catch (RemoteException e) {
            System.err.println("与服务器(" + Connection.RMI_PREFIX + ")的连接断开 -" + Calendar.getInstance().getTime());
            return 0;
        }

        if (orders.size() == 0) return 0;

        for (DataPO data : orders) {
            OrderPO orderPO = (OrderPO) data;
            Calendar sendDate = orderPO.getGenDate();
            SignPO signPO = null;
            try {
                signPO = (SignPO) orderDataService.searchSignInfo(orderPO.getSerialNum());
            } catch (RemoteException e) {
                System.err.println("与服务器(" + Connection.RMI_PREFIX + ")的连接断开 -" + Calendar.getInstance().getTime());
                return 0;
            }
            Calendar signDate = signPO.getGenDate();
            time += (signDate.getTimeInMillis() - sendDate.getTimeInMillis()) / 1000.0f / 60.0f / 60.0f / 24.0f;
        }
        time /= orders.size();
        time *= 10;

        // 四舍五入
        if ((int) time % 10 >= 5) time = ((int) time) / 10 * 10 + 10;
        else time = ((int) time) / 10 * 10;
        return (int) time;
    }

    /**
     * 根据订单信息产生订单号
     *
     * @param orderVO 需要计算费用的订单号
     * @return 计算出的费用
     */
    public double generateFee(OrderVO orderVO) {
        return 0;
    }

    public ArrayList<String> getBlockByCity(String city) {
        CompanyDataService ds = (CompanyDataService) DataServiceFactory.getDataServiceByType(DataType.CompanyDataService);
        CityInfoPO cityInfo = null;
        try {
            cityInfo = (CityInfoPO) ds.searchCity(city);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (cityInfo == null) {
            ArrayList<String> res = new ArrayList<>();
            res.add("暂时无法获取分区信息");
        }
        ArrayList<String> result = new ArrayList<>();
        for (long bosn: cityInfo.getBusinessOfficeList()) {
            try {
                result.add(((InstitutionPO) ds.search(POType.INSTITUTION, bosn)).getName());
            } catch (RemoteException e) {
                System.err.println("网络连接错误。无法获取城区信息 - " + Calendar.getInstance().getTime());
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 输入气运编号，完成所包含的订单的收获操作
     *
     * @param sn 气运编号（中转单编号）
     * @return ResultMessage.SUCCESS表示收货成功，NOTEXIST表示该中转单不存在，FAILED表示收货失败
     */
    public ResultMessage receive(long sn) {

        if (!Connection.connected) {
            System.err.println("尚未连接到服务器，无法完成收货操作" + Calendar.getInstance().getTime());
            return ResultMessage.FAILED;
        }

        TransferDataService transferDataService = (TransferDataService) DataServiceFactory.getDataServiceByType(DataType.TransferDataService);
        if (transferDataService == null) {
            System.err.println("");
            return ResultMessage.FAILED;
        }



        // 从持久化数据文件中获得中转单信息
        TransferListPO transferListPO = null;
        try {
            transferListPO = (TransferListPO) transferDataService.search(POType.TRANSFERLIST, sn);
        } catch (RemoteException e) {
            System.err.println("与服务器(" + Connection.RMI_PREFIX + ")的连接断开 -" + Calendar.getInstance().getTime());
            return ResultMessage.FAILED;
        }
        if (transferListPO == null) return ResultMessage.NOTEXIST;


        ArrayList<Long> orderList = transferListPO.getOrderArray();

        // 根据中转单信息生成到达单信息
        ArrivalPO arrivalPO = new ArrivalPO();
        for (long order : orderList) {
            arrivalPO.addOrder(order);
            arrivalPO.addStckStatus(StockStatus.ROUND);
        }
        arrivalPO.setDate(Timestamper.getTimeByDate());
        arrivalPO.setTransferList(sn);
        arrivalPO.setFrom(transferListPO.getTransferCenter());

        try {
            transferDataService.add(arrivalPO);
        } catch (RemoteException e) {
            System.err.println("与服务器(" + Connection.RMI_PREFIX + ")的连接断开 -" + Calendar.getInstance().getTime());
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    /**
     * 签收操作
     *
     * @param sn    需要签收的订单号
     * @param name  签收者姓名
     * @param phone 签收者电话
     * @return SUCCESS表示签收成功。FAILED表示订单已经被签收。NOTEXIST表示订单号不存在。
     */
    public ResultMessage sign(long sn, String name, String phone) {
        LogisticInfoPO logisticInfo = null;
        OrderPO order = null;
        try {
            // 获取订单和物流信息
            order = (OrderPO) orderDataService.search(POType.ORDER, sn);
            logisticInfo = (LogisticInfoPO) orderDataService.search(POType.LOGISTICINFO, sn);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (order == null) return ResultMessage.NOTEXIST;
        logisticInfo.addInfo(Timestamper.getTimeBySecond(), "快件已被 " + name + " 签收");

        // 生成签收信息
        SignPO signPO = new SignPO(sn);
        signPO.setSdate(name);
        signPO.setSphone(phone);
        try {
            orderDataService.modify(logisticInfo);
            orderDataService.add(signPO);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return ResultMessage.SUCCESS;
    }

    public ArrayList<OrderPO> search(long[] order) {
        ArrayList<OrderPO> result = new ArrayList<>();
        for (long ordersn : order) {
            try {
                OrderPO orderPO = (OrderPO) orderDataService.search(POType.ORDER, ordersn);
                if (orderPO != null) result.add(orderPO);
            } catch (RemoteException e) {
                System.err.println("与服务器(" + Connection.RMI_PREFIX + ")的连接断开 -" + Calendar.getInstance().getTime());
            }
        }
        return result;
    }
}
