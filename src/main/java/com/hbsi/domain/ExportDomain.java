package com.hbsi.domain;

import java.util.List;

public class ExportDomain {

	//从前端接收wireRope
	private WireRope wireRope;
	//用户名
	private String userName;
	/**
	 * 破断拉力或者抗拉强度的值，之前用单条数据传递给前端
	 * 现在用单条数据接收
	 * pdorqd： 破断or强度的首字母缩写
	 */
	private String pdorqd;
	//版本号
	private String versonNumber;
	//产品名称
	private String proName;
	
//	private BreakConclusion breakConclusion;
	//从前端接收 拆股实验结论列表信息，用于后端更改
	private List<BreakConclusion> list;
	
	
	public WireRope getWireRope() {
		return wireRope;
	}
	public void setWireRope(WireRope wireRope) {
		this.wireRope = wireRope;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPdorqd() {
		return pdorqd;
	}
	public void setPdorqd(String pdorqd) {
		this.pdorqd = pdorqd;
	}
	
	
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getVersonNumber() {
		return versonNumber;
	}
	public void setVersonNumber(String versonNumber) {
		this.versonNumber = versonNumber;
	}
	public List<BreakConclusion> getList() {
		return list;
	}
	public void setList(List<BreakConclusion> list) {
		this.list = list;
	}
	/**
	 * 无参构造
	 */
	public ExportDomain() {}
	/**
	 * 有参构造
	 * @param wireRope
	 * @param userName
	 * @param pdorqd
	 * @param breakConclusionList
	 */
	public ExportDomain(WireRope wireRope, String userName, String pdorqd, String versonNumber, String proName,
			List<BreakConclusion> list) {
		super();
		this.wireRope = wireRope;
		this.userName = userName;
		this.pdorqd = pdorqd;
		this.versonNumber = versonNumber;
		this.proName = proName;
		this.list = list;
	}
	
}
