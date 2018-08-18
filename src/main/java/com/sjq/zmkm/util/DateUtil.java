package com.sjq.zmkm.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sjq.zmkm.exception.BusinessException;
public class DateUtil {


	public static final String BACKEND_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
	private static Logger logger =  LoggerFactory.getLogger(DateUtil.class);
	//获取当前日期 yyyy/MM/dd HH:mm:ss
	public static String getCurrentDateStr(){
		SimpleDateFormat df = new SimpleDateFormat(BACKEND_FORMAT_STRING);
		return df.format(new Date());
	}
	//根据指定格式获取当前日期 
	public static String getCurrentDateStr(String format){
		SimpleDateFormat fm = new SimpleDateFormat(format);
		return fm.format(new Date());
	}
	//将指定日期转换为指定格式
	public static String getDateStr(String format,Date date){
		SimpleDateFormat fm = new SimpleDateFormat(format);
		return fm.format(date);
	}
	//
	public static String getDateStr(Date date){
		if(date==null){
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(BACKEND_FORMAT_STRING);
		return df.format(date);
	}
	
	
	
	//将yyyy-MM-dd HH:mm:ss时间转为Date
	public static Date toDate(String str) throws BusinessException {
		try {
			SimpleDateFormat df = new SimpleDateFormat(BACKEND_FORMAT_STRING);
			Date date= df.parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("时间转换异常 str="+str);
			throw new BusinessException("时间转换异常");
		}
	}
	/**
	 * 对日期加上指定的分钟
	 */
	public static String addYear(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, i);
		return getDateStr(cal.getTime());
	}
	/**
	 * 对日期加上指定的分钟
	 */
	public static String addDate(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, i);
		return getDateStr(cal.getTime());
	}
	/**
	 * 对日期加上指定的分钟
	 */
	public static String addHour(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, i);
		return getDateStr(cal.getTime());
	}
	/**
	 * 对日期加上指定的分钟
	 */
	public static Date addMinute1(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, i);
		return cal.getTime();
	}
	/**
	 * 对日期加上指定的分钟
	 */
	public static String addMinute(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, i);
		return getDateStr(cal.getTime());
	}
	public static int bwSec(Date startDate,Date endDate){
		long bw = endDate.getTime()-startDate.getTime();
		return (int)(bw/1000);
	}
	//返回剩余有效秒数
	public static int isExpired(String endDate) throws BusinessException {
		int bwMins=DateUtil.bwSec(new Date(),toDate(endDate));
		return bwMins; 
	}  
	
	/**
	 * 对日期加上指定的分钟
	 */
	public static String getAfterDateMiddleNight() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND,59);
		cal.set(Calendar.MINUTE,59);
		return getDateStr(cal.getTime());
	}
	//2017.07.25T10:00+08:00
	public static void main(String[] args) throws ParseException, IOException {
		System.out.println(getAfterDateMiddleNight());
	} 
}
