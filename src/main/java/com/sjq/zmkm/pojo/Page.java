package com.sjq.zmkm.pojo;
import java.util.List;

//封装页面数据  
@SuppressWarnings("rawtypes")
public class Page {  
	
	private int totalrecord;  //总纪录数  
	private int pagesize = 8;  //每页多少条数据  默认10
	private int totalpage; //总过多少页 
	private int pagenum; //用户想哪一页  即当前页
	private boolean pageUp=true;  //是否存在上一页 
	private boolean pageDown=true;//是否存在下一页   
	private List list;   //保存页面数据 ;  
	   
	public Page(int pagenum,int pageSize ,int totalrecord,List list){  
		
		//构造函数根据用户传递进来的总纪录数和页号，算出总页数，和该页在数据库的起始位置  
		this.totalrecord = totalrecord;  
		this.pagenum = pagenum;  
		this.list=list;
		this.pagesize=pageSize;
		
		//1.算出总页数  
		if(this.totalrecord%this.pagesize==0){  
			this.totalpage = this.totalrecord/this.pagesize;  
		}else{  
			this.totalpage = this.totalrecord/this.pagesize + 1;  
		}  
		//2.算出页号在数据库的起始位置，不同数据库中不一样，mysql查询出来的结果是这个位置的下一个数据。从这个的下一个开始  
		//this.startindex = (this.pagenum-1)*this.pagesize;  
	    
		//3.算出是否存在上一页 下一页 
		if(pagenum<=1){
			pageUp=false; 
		}
		if(pagenum>=totalpage){
			pageDown=false;
		}
	 }  
	   
	 public int getTotalrecord() {  
		 return totalrecord;  
	 }  
	 public void setTotalrecord(int totalrecord) {  
		 this.totalrecord = totalrecord;  
	 }  
	 public int getPagesize() {  
		 return pagesize;  
	 }  
	 public void setPagesize(int pagesize) {  
		 this.pagesize = pagesize;  
	 }  
	 public int getTotalpage() {  
		 return totalpage;  
	 }  
	 public void setTotalpage(int totalpage) {  
		 this.totalpage = totalpage;  
	 }  
	 public int getPagenum() {  
		 return pagenum;  
	 }  
	 public void setPagenum(int pagenum) {  
		 this.pagenum = pagenum;  
	 }  
	 public boolean isPageUp() {
		 return pageUp;
	 }
	 public void setPageUp(boolean pageUp) {
		 this.pageUp = pageUp;
	 }
	 public boolean isPageDown() {
		 return pageDown;
	 }
	 public void setPageDown(boolean pageDown) {
		 this.pageDown = pageDown;
	 }
	 public List getList() {
		 return list;
	 }
	 public void setList(List list) {
		 this.list = list;
	 } 
} 