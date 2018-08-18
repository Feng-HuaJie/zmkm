package com.sjq.zmkm.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sjq.zmkm.pojo.Page;
import com.sjq.zmkm.pojo.Response;
import com.sjq.zmkm.pojo.ResponseSuc;
import com.sjq.zmkm.service.CardService;

@Controller
@RequestMapping("/card")
public class CardController extends BaseController{
     
    @Resource
    private CardService service;
    
    //查询
    //@SystemControllerLog(description = "分页查询shop信息")
    @RequestMapping(method=RequestMethod.GET,value="page")
    @ResponseBody
	public Response page(@RequestParam int pageNum,@RequestParam int pageSize){
    	
		Page page=  service.page(pageNum, pageSize);
		return new ResponseSuc("操作成功", page);
	}
//    //查询
//    @RequestMapping(value="/{id}",method=RequestMethod.GET)
// 	public void get(@PathVariable int id,HttpServletRequest request,HttpServletResponse response){
//    	
//    	try {
//    		JSONObject jsonObj=  service.getById(id);
//    		ResponseObj responseObj=new ResponseObjSuc("操作成功",jsonObj);
//    		this.response(response,responseObj);
//		}catch (SjqException e) {
//			logger.error(e.getMessage(), e);
//			this.response(response, new ResponseObjFai(e.getMessage()));
//		}catch (Exception e) {
//			logger.error("服务器异常", e);
//			this.response(response,new ResponseObjFai("服务器异常"));//不可预见异常
//		}  
//	}
//    
//    @RequestMapping(method=RequestMethod.PUT,value="/{pkid}")
// 	public void put(HttpServletRequest request,HttpServletResponse response){
//    	try {
//    		Shop b=this.getRequestBody(request,Shop.class);
//    		service.update(b);
//    		ResponseObj responseObj=new ResponseObjSuc("操作成功");
//			this.response(response, responseObj);
//		}catch (SjqException e) {
//			logger.error(e.getMessage(), e);
//			this.response(response, new ResponseObjFai(e.getMessage()));
//		}catch (Exception e) {
//			logger.error("服务器异常", e);
//			this.response(response,new ResponseObjFai("服务器异常"));//不可预见异常
//		}  
//	}
//    //修改
// 	@RequestMapping(method=RequestMethod.PUT,value="/{pkid}/enable")
// 	public void enable(@PathVariable int pkid,HttpServletRequest request,HttpServletResponse response){
//    	try {
//      		service.enable(pkid);
//    		ResponseObj responseObj=new ResponseObjSuc("操作成功");
//    		this.response(response, responseObj);
//		}catch (SjqException e) {
//			logger.error(e.getMessage(), e);
//			this.response(response, new ResponseObjFai(e.getMessage()));
//		}catch (Exception e) {
//			logger.error("服务器异常", e);
//			this.response(response,new ResponseObjFai("服务器异常"));//不可预见异常
//		}  
//	}
//    //修改
// 	@RequestMapping(method=RequestMethod.PUT,value="/{pkid}/disable")
// 	public void disable(@PathVariable int pkid,HttpServletRequest request,HttpServletResponse response){
//    	try {
//      		service.disable(pkid);
//    		ResponseObj responseObj=new ResponseObjSuc("操作成功");
//    		this.response(response, responseObj);
//		}catch (SjqException e) {
//			logger.error(e.getMessage(), e);
//			this.response(response, new ResponseObjFai(e.getMessage()));
//		}catch (Exception e) {
//			logger.error("服务器异常", e);
//			this.response(response,new ResponseObjFai("服务器异常"));//不可预见异常
//		}  
//	}
//    @RequestMapping(method=RequestMethod.POST)
// 	public void post(HttpServletRequest request,HttpServletResponse response){
//    	
//    	try {
//    		Shop b=this.getRequestBody(request,Shop.class);
//    		b.setStatus(Constant.SysConstants.STATUS_CHECK);
//			service.insert(b);
//			ResponseObj responseObj=new ResponseObjSuc("操作成功");
//			this.response(response, responseObj);
//		}catch (SjqException e) {
//			logger.error(e.getMessage(), e);
//			this.response(response, new ResponseObjFai(e.getMessage()));
//		}catch (Exception e) {
//			logger.error("服务器异常", e);
//			this.response(response,new ResponseObjFai("服务器异常"));//不可预见异常
//		}   
//	}
//    
//    //删除
//    @RequestMapping(value="/{pkid}",method=RequestMethod.DELETE)
// 	public void delete(@PathVariable int pkid,HttpServletRequest request,HttpServletResponse response){
//    	
//    	try {
//    		service.delete(pkid);
//			ResponseObj responseObj=new ResponseObjSuc("操作成功");
//			this.response(response, responseObj);
//		} catch (SjqException e) {
//			logger.error(e.getMessage(), e);
//			this.response(response, new ResponseObjFai(e.getMessage()));
//		} catch (Exception e) {
//			logger.error("服务器异常", e);
//			this.response(response,new ResponseObjFai("服务器异常"));//不可预见异常
//		} 
//    } 
//    @RequestMapping(value="/checkShopName",method=RequestMethod.GET)
// 	public void chechShopName(@RequestParam String shopName,HttpServletRequest request,HttpServletResponse response){
//    	try {
//    		if(service.queryShopByShopName(shopName)!=null){
//    			this.response(response, new ResponseObjFai("该名称已注册"));
//    		}
//			ResponseObj responseObj=new ResponseObjSuc("该名称还未注册");
//			this.response(response,  responseObj);
//		}catch (Exception e) {
//			logger.error("服务器异常", e);
//			this.response(response,new ResponseObjFai("服务器异常"));//不可预见异常
//		}  
//    }  
}