package data.po;

import data.enums.POType;
import data.vo.TruckInfoVO;


public class VehicleInfoPO extends DataPO {

    private static final long serialVersionUID = 23;
    long ID;
    String license;
    // Format: yyyy/mm/dd
    String dutyDate;
    boolean engaged;
    
    
    public VehicleInfoPO(TruckInfoVO vo){
    	super(POType.VEHICLEINFO);
    	license = vo.license;
    	dutyDate = vo.dutyDate;
    	engaged = false;
    }
    public String getEngagedString(){
    	if(engaged) return "����";
    	else return "����";
    }

    public boolean isEngaged() {
		return engaged;
	}

	public void setEngaged(boolean engaged) {
		this.engaged = engaged;
	}

	public VehicleInfoPO(long ID, String license, String dutyDate) {
        super(POType.VEHICLEINFO);
        this.ID = ID;
        this.license = license;
        this.dutyDate = dutyDate;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getDutyDate() {
        return dutyDate;
    }

    public boolean setDutyDate(String dutyDate) {
        if (dutyDate.matches("[0-9]{4}[/][0-9]{2}[/][0-9]{2}")) {
            this.dutyDate = dutyDate;
            return true;
        }
        return false;
    }

}
