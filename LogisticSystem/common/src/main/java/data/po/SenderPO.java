package data.po;

import data.enums.POType;

public class SenderPO extends StaffPO{

	private static final long serialVersionUID = 14;
	boolean isAvailable;

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	
}
