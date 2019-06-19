package com.hbsi.response;

public class PageResult {
	private Integer limit;
	private Integer offset;
	private long total;
	private Object datalist;
	public PageResult() {
		
	}
	
	public PageResult(Integer limit, Integer offset, long total, Object datalist) {
		super();
		this.limit = limit;
		this.offset = offset;
		this.total = total;
		this.datalist = datalist;
	}

	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public Object getDatalist() {
		return datalist;
	}
	public void setDatalist(Object datalist) {
		this.datalist = datalist;
	}
	@Override
	public String toString() {
		return "PageResult [limit=" + limit + ", offset=" + offset + ", total=" + total + ", datalist=" + datalist
				+ "]";
	}
	

}
