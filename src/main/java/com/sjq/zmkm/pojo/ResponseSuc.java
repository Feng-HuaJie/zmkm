package com.sjq.zmkm.pojo;

public class ResponseSuc extends Response{

	public ResponseSuc(String responseMark,Object obj){
		super(true,responseMark,obj);
	}
	public ResponseSuc(String responseMark) {
		super(true,responseMark,null);
	}
	public ResponseSuc(Object obj) {
		super(true,"操作成功",obj);
	}
	public ResponseSuc() {
		super(true,"操作成功",null);
	}
}
