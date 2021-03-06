package businesslogic.impl.financialbl;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import utils.Timestamper;
import data.enums.DataType;
import data.enums.POType;
import utils.DataServiceFactory;
import data.message.ResultMessage;
import data.po.DataPO;
import data.po.EntruckPO;
import data.po.PaymentPO;
import data.po.ReceiptPO;
import data.po.SalaryPO;
import data.po.TransferListPO;
import data.service.FinancialDataService;
import data.service.TransferDataService;
import data.vo.PaymentVO;
import data.vo.ReceiptVO;


public class FundsManage {
	TransferDataService transferDataService;
	FinancialDataService financialDataService;
	Timestamper timestamper ;
	ArrayList<PaymentVO> paymentVOList;
	ArrayList<ReceiptVO> receiptVOList;
	private ArrayList<DataPO> pList ;
	PaymentVO paymentVO;
	PaymentPO payment;
	ReceiptVO receiptVO;
	ReceiptPO receipt;
	
//	public ResultMessage recordPayDate(String info) {
//		return null;
//	}
//
//	public ResultMessage recordPayMoney(String info) {
//		
//		return null;
//	}
	//搜索全部的付款单
	 public ArrayList<PaymentVO> searchAllPayment (){
		 paymentVOList = new ArrayList<PaymentVO>();
	    	try {
				pList = financialDataService.getPOList(POType.PAYMENT);
				for(DataPO po: pList){
					payment = (PaymentPO) po;
					paymentVO = new PaymentVO();
					paymentVO.setAccount(payment.getAccount());
					paymentVO.setDate(payment.getDate());
					paymentVO.setExInfo(payment.getExInfo());
					paymentVO.setInfo(payment.getInfo());
					paymentVO.setMoney(payment.getMoney());
					paymentVO.setName(payment.getName());
					paymentVO.setCount(payment.isCount());
					paymentVO.setId(payment.getSerialNum());
	                paymentVOList.add(paymentVO);
	    			}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	    		return 	paymentVOList;
	    }
	 public ArrayList<ReceiptVO> searchAllReceipt() {
		 receiptVOList = new ArrayList<ReceiptVO>();
	    	try {
				pList = financialDataService.getPOList(POType.RECEIPT);
				for(DataPO po: pList){
					receipt = (ReceiptPO) po;
					receiptVO = new ReceiptVO(); 
					receiptVO.setAddress(receipt.getInstitution());
					receiptVO.setPeople(receipt.getSender());
					receiptVO.setDate(receipt.getDate());
					receiptVO.setInstitution(receipt.getInstitution());
					receiptVO.setMoney(receipt.getMoney());
					receiptVO.setSender(receipt.getSender());
					receiptVO.setCount(receipt.isCount());
					receiptVO.setId(receipt.getSerialNum());
					receiptVOList.add(receiptVO);
	    			}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	    		return 	receiptVOList;
		}
	
//	 //this four methord need write!!!!
//	public PaymentVO buildPaymentFromEntruck(PaymentVO pay,long institution) throws RemoteException {
//		// if each , then not use all; else , use all to add continue
//		
//		ArrayList<DataPO> entruckList = transferDataService.searchUncountedList(POType.ENTRUCK, institution);
//		
//        payment = new PaymentPO();
//        double sum = 0;
//		for(DataPO po1: entruckList){
//			
//			//成本管理（付款日期、付款金额、付款人、付款账号、条目(运费（按次计算）)、备注(运单号)
//			EntruckPO en = (EntruckPO) po1;
//			double fee = en.getFee();
//			en.setIscounted(true);
//			transferDataService.modify(en);
//			sum = sum + fee ; 
//			
//			
//		}
//		payment.setMoney(sum);
//		//continue add the element
//		payment.setDate(pay.getDate());
//	    payment.setAccount(pay.getAccount());
//	    payment.setExInfo(pay.getExInfo());
//	    payment.setInfo(pay.getInfo());
//	    payment.setName(pay.getName());
//	    
//		financialDataService.add(payment);
//		
//		paymentVO = pay;
//		paymentVO.setMoney(sum);
//		
//		return paymentVO;
//	}
	public PaymentVO buildPaymentFromTransfer(PaymentVO pay,long institution) throws RemoteException {
		// if each , then not use all; else , use all to add continue
		ArrayList<TransferListPO> TransferList = transferDataService.searchUncountedList(POType.TRANSFERLIST, institution);
		
		double sum = 0;
        payment = new PaymentPO();
		for(TransferListPO po1: TransferList){
			
			//成本管理（付款日期、付款金额、付款人、付款账号、条目(运费（按次计算）)、备注(运单号)
			double fee = po1.getFee();
			po1.setAccount(true);
			transferDataService.modify(po1);
			sum = sum + fee ; 
			
		}
		payment.setMoney(sum);
		//continue add the element
		payment.setDate(pay.getDate());
		payment.setAccount(pay.getAccount());
	    payment.setExInfo(pay.getExInfo());
	    payment.setInfo(pay.getInfo());
	    payment.setName(pay.getName());		
		financialDataService.add(payment);		
		paymentVO = pay;
		paymentVO.setMoney(sum);
		
		
		return paymentVO;
	}
	public ResultMessage buildPaymentFromRent(PaymentVO pay) throws RemoteException  {
		PaymentPO payment = new PaymentPO();
		//continue add the element
		payment.setDate(pay.getDate());
		payment.setAccount(pay.getAccount());
	    payment.setExInfo(pay.getExInfo());
	    payment.setInfo(pay.getInfo());
	    payment.setName(pay.getName());
	    payment.setMoney(pay.getMoney());
	    
		financialDataService.add(payment);
		
//		//为了测试需要，新建一个收款单
//		ReceiptPO receipt = new ReceiptPO();
//		receipt.setDate(Timestamper.getTimeByDate());
//		receipt.setCount(false);
//		receipt.setInstitution("上海市闵行营业厅");
//		receipt.setMoney(177778.9);
//		receipt.setSender("孙浩");
//		financialDataService.add(receipt);
			
			
			return null;
		
	}
	
	
	public PaymentVO buildPaymentFromWages(PaymentVO pay,String institution){
		payment = new PaymentPO();
		paymentVO = pay;
		try {
			ArrayList<DataPO> a = DataServiceFactory.getDataServiceByPO(POType.SALARY).getPOList(POType.SALARY);
		for(DataPO po:a){
			SalaryPO salaryPO = (SalaryPO) po; 
			if(salaryPO.getInstitution().equals(institution)){
				 payment.setMoney(salaryPO.getSalary());
				 payment.setInfo(pay.getInfo());
				 payment.setExInfo(salaryPO.getType());
				 payment.setDate(pay.getDate());
				 payment.setName(pay.getName());
				 payment.setAccount(pay.getAccount());
				 
				 paymentVO.setMoney(salaryPO.getSalary());
				 payment.setExInfo(salaryPO.getType());
			}
		}
		
		} catch (RemoteException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		
		
    	
		try {
			financialDataService.add(payment);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
		return paymentVO;
		
	}
	
	public ResultMessage printPayment(ArrayList<PaymentVO> payList) {
		try{

		//打开文件

		WritableWorkbook book = Workbook.createWorkbook(new File("付款单.xls"));

		//生成名为“第一页”的工作表，参数0表示这是第一页

		WritableSheet sheet = book.createSheet("第一页",0);

		//在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
    	//将定义好的单元格添加到工作表中
	   Label label = new Label(0,0,"付款日期");
	   sheet.addCell(label);
       label = new Label(1,0,"付款金额");
       sheet.addCell(label);
       label = new Label(2,0,"付款人");
       sheet.addCell(label);
       label = new Label(3,0,"付款账号");
       sheet.addCell(label);
       label = new Label(4,0,"条目");
       sheet.addCell(label);
       label = new Label(5,0,"备注");
       sheet.addCell(label);
		
       //其中i表示列，j表示行
       int j =1;
       for(PaymentVO pay:payList){
    	   if(j <= payList.size()){
    		   label = new Label(0,j,pay.getDate());
               sheet.addCell(label);
               label = new Label(1,j,Double.toString(pay.getMoney()));
               sheet.addCell(label);
               label = new Label(2,j,pay.getName());
               sheet.addCell(label);
               label = new Label(3,j,pay.getAccount());
               sheet.addCell(label);
               label = new Label(4,j,pay.getInfo());
               sheet.addCell(label);
               label = new Label(5,j,pay.getExInfo());
               sheet.addCell(label);
               j++;
    	   }
    	   
       }
		//写入数据并关闭文件

		book.write();

		book.close();

		}catch(Exception e)

		{

		System.out.println(e);

		}
		return null;
	}
	public ArrayList<ReceiptVO> checkFromAddress(String institution) {
	
		receiptVOList = new ArrayList<ReceiptVO>();
		try {
			ArrayList<DataPO> receiptList = financialDataService.searchRecFromAddress(institution);
			for(DataPO po:receiptList){
				
				receipt = (ReceiptPO) po;
				receiptVO = new ReceiptVO(); 
				receiptVO.setAddress(receipt.getInstitution());
				receiptVO.setPeople(receipt.getSender());
				receiptVO.setDate(receipt.getDate());
				receiptVO.setInstitution(receipt.getInstitution());
				receiptVO.setMoney(receipt.getMoney());
				receiptVO.setSender(receipt.getSender());
				receiptVOList.add(receiptVO);
				
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
			
		}
		return receiptVOList;
	}

	public ArrayList<ReceiptVO> checkFromDate(String date) {
		
		receiptVOList = new ArrayList<ReceiptVO>();
		try {
			ArrayList<DataPO> receiptList = financialDataService.searchRecFromDate(date);
			for(DataPO po:receiptList){
			
				receipt = (ReceiptPO) po;
				receiptVO = new ReceiptVO(); 
				receiptVO.setAddress(receipt.getInstitution());
				receiptVO.setPeople(receipt.getSender());
				receiptVO.setDate(receipt.getDate());
				receiptVO.setInstitution(receipt.getInstitution());
				receiptVO.setMoney(receipt.getMoney());
				receiptVO.setSender(receipt.getSender());
				receiptVOList.add(receiptVO);
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
			
		}
	return receiptVOList;
	}
   
	public double total (ArrayList<ReceiptVO> rec){
		double sum = 0;
		
			for(ReceiptVO vo:rec){
				
				double money = vo.getMoney();
				sum = sum + money;
				
			}
			
		
		return sum;
		
	}
	public ResultMessage printReceipt(ArrayList<ReceiptVO> recList) {
		try{

			//打开文件

			WritableWorkbook book = Workbook.createWorkbook(new File("收款单.xls"));

			//生成名为“第一页”的工作表，参数0表示这是第一页

			WritableSheet sheet = book.createSheet("第一页",0);

			//在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
	    	//将定义好的单元格添加到工作表中
		   Label label = new Label(0,0,"收款日期");
		   sheet.addCell(label);
	       label = new Label(1,0,"收款单位");
	       sheet.addCell(label);
	       label = new Label(2,0,"收款人");
	       sheet.addCell(label);
	       label = new Label(3,0,"收款方");
	       sheet.addCell(label);
	       label = new Label(4,0,"收款金额");
	       sheet.addCell(label);
	       label = new Label(5,0,"收款地点");
	       sheet.addCell(label);
			
	       //其中i表示列，j表示行
	       int j=1;
	       for(ReceiptVO rec:recList){
	    	   if(j <= recList.size()){
	    		   label = new Label(0,j,rec.getDate());
	               sheet.addCell(label);
	               label = new Label(1,j,rec.getInstitution());
	               sheet.addCell(label);
	               label = new Label(2,j,rec.getPeople());
	               sheet.addCell(label);
	               label = new Label(3,j,rec.getSender());
	               sheet.addCell(label);
	               label = new Label(4,j,Double.toString(rec.getMoney()));
	               sheet.addCell(label);
	               label = new Label(5,j,rec.getAddress());
	               sheet.addCell(label);
	               j++;
	    	   }
	    	   
	       }
			//写入数据并关闭文件

			book.write();

			book.close();

			}catch(Exception e)

			{

			System.out.println(e);

			}
		return null;
	}
	public FundsManage(){
		transferDataService = (TransferDataService) DataServiceFactory.getDataServiceByType(DataType.TransferDataService);
		financialDataService = (FinancialDataService) DataServiceFactory.getDataServiceByType(DataType.FinancialDataService);
		timestamper = new Timestamper();
		}


	

}
