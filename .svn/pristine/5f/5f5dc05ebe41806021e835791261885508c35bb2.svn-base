package com.hbsi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hbsi.response.Response;

@RestControllerAdvice
public class BaseExceptionHandler {
	private final Logger logger=LoggerFactory.getLogger(BaseExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public Response<Object> handle(Exception e) {
		if(e instanceof BaseException) {
			BaseException ex=(BaseException)e;
			logger.error(ex.getMessage());
			return new Response<Object>(ex.getStatus(),ex.getMessage(), null);
		}else {
			logger.error("[系统异常] {}",e);
			return new Response<Object>("-1", "系统异常", null);
		}
	}

}
