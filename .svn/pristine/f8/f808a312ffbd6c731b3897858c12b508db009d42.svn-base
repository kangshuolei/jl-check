package com.hbsi.exception;

@SuppressWarnings("serial")
public class BaseException extends RuntimeException {
	private String status;

	public BaseException(ExceptionEnum e) {
		super(e.getMessage());
		this.status = e.getStatus();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
