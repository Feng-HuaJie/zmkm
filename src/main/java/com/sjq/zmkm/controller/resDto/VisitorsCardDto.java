package com.sjq.zmkm.controller.resDto;

public class VisitorsCardDto {
	
	private int pkid;

	private String cardName;
	
	private String inviterName;
	
	private int num;
	
	private String startTime;
	
	private String endTime;
	
    private String macAddress;	//门卡 物理地址
    
    private String openCmd;	//开门指令

	public int getPkid() {
		return pkid;
	}

	public void setPkid(int pkid) {
		this.pkid = pkid;
	}
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getInviterName() {
		return inviterName;
	}

	public void setInviterName(String inviterName) {
		this.inviterName = inviterName;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getOpenCmd() {
		return openCmd;
	}

	public void setOpenCmd(String openCmd) {
		this.openCmd = openCmd;
	}
}
