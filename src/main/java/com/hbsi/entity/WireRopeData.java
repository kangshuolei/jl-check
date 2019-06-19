package com.hbsi.entity;
/*检测钢丝数据条件*/
public class WireRopeData {
	private String standardNum;//标准号
	private String surface;//表面状态
	private double ndiamete;//公称直径
	private String usage;//用途（欧盟新增）
	public String getStandardNum() {
		return standardNum;
	}
	public void setStandardNum(String standardNum) {
		this.standardNum = standardNum;
	}
	public String getSurface() {
		return surface;
	}
	public void setSurface(String surface) {
		this.surface = surface;
	}
	public double getNdiamete() {
		return ndiamete;
	}
	public void setNdiamete(double ndiamete) {
		this.ndiamete = ndiamete;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	
}
