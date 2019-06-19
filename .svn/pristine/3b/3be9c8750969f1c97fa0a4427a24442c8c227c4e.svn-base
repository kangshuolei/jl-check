package com.hbsi.response;

import java.io.Serializable;

import com.hbsi.exception.ExceptionEnum;

/**
 * 自定义返回类型
 * 
 * @author chen
 *
 * @param <T>
 */
public class Response<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 状态码，200为成功
	 */
	private String status;
	/**
	 * 返回信息
	 */
	private String message;
	/**
	 * 返回的具体数据
	 * T是泛型，代指返回的数据的数据类型，与类定义处的数据类型为一种。例如：返回的数据User的信息，则此处为User；若返回的是列表，则此处为List
	 */
	private T data;

	public Response() {
		this.status = ExceptionEnum.SUCCESS.getStatus();
		this.message =ExceptionEnum.SUCCESS.getMessage();
	}

	public Response( T data) {
		this.status = ExceptionEnum.SUCCESS.getStatus();
		this.message =ExceptionEnum.SUCCESS.getMessage();
		this.data = data;
	}

	public Response(String status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", message=" + message + ", data="
				+ data + "]";
	}

}
