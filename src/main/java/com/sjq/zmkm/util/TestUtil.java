package com.sjq.zmkm.util;

import java.lang.reflect.Field;

import com.sjq.zmkm.dao.entity.Card;
import com.sjq.zmkm.exception.EnumValue;

public class TestUtil {

   public static void test1(){
        int a=1;
        int b=1;
        if(a<b && b<a/0){
            System.out.println("Oh，That's Impossible!!!");
        }else{
            System.out.println("That's in my control.");
        }
    }
    public static void test2(){
        int a=1;
        int b=1;
        if(a==b || b<a/0){
            System.out.println("That's in my control.");
        }else{
            System.out.println("Oh，That's Impossible!!!");
        }
    }
    public static void main(String[] args) throws NoSuchFieldException, SecurityException{
    	//test1();
        Class targetClass = Card.class;  
        Field typeField=targetClass.getField("type");
        String value = typeField.getAnnotation(EnumValue.class).value();
        String defaultValue = typeField.getAnnotation(EnumValue.class).defaultValue();
        System.out.println(value+";"+defaultValue);
    }
}
