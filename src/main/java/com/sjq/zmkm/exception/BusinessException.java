package com.sjq.zmkm.exception;

/**
 * 自定义异常类 简单实用省事
 */
public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public BusinessException(String message){
        super(message);
    }
}
