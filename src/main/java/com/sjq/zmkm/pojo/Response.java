package com.sjq.zmkm.pojo;
/*
 * 超简单响应协议
 * success true 成功 false 失败
 * remark 描述
 * obj 返回对象
 * 构造方法在子类中重载
 * 拒绝errorcode
 */
public class Response {

	private boolean success;
	
	private String remark;
	
	private Object obj;
	
	public Response(boolean success, String remark,Object obj) {
		this.success=success;
		this.remark=remark;
		this.obj=obj;
	}
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
}
