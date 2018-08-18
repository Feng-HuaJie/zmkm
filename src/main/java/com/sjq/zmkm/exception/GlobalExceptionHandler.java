package com.sjq.zmkm.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sjq.zmkm.pojo.Response;
import com.sjq.zmkm.pojo.ResponsejFai;

/**
 * global exception handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Response businessExceptionHandler(Exception e){
    	logger.error(e.getMessage(),e);
    	return new ResponsejFai(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response exceptionHandler(Exception e){
    	logger.error("服务器异常", e);
    	return new ResponsejFai("服务器异常:"+e.getMessage());
    }
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Response runTimeExceptionHandler( Exception e){
    	logger.error("运行时异常", e);
    	return new ResponsejFai("运行时异常:"+e.getMessage());
    }
}
