package com.sjq.zmkm.exception;    
    
import java.lang.annotation.*;    
    
/**  
 *自定义注解  用于标记要拦截拦截Controller  
 */    
@Target({ElementType.PARAMETER, ElementType.FIELD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented    
public  @interface EnumValue {    
    
    String value()  default "";    
    String defaultValue()  default ""; 
    
}    