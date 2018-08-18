package com.sjq.zmkm.pojo;


public class ResponsejFai extends Response {

	public ResponsejFai(String responseMark,Object obj){
		super(false,responseMark,obj);
	}
	public ResponsejFai(String responseMark) {
		super(false,responseMark,null);
	}
}
