package com.sjq.zmkm.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.sjq.zmkm.controller.resDto.AwardResultDto;
import com.sjq.zmkm.dao.entity.User;
import com.sjq.zmkm.pojo.Page;
import com.sjq.zmkm.pojo.Response;
import com.sjq.zmkm.pojo.ResponseSuc;
import com.sjq.zmkm.service.UserCardService;
import com.sjq.zmkm.service.UserService;
import com.sjq.zmkm.service.VisitorsService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
     
    @Resource
    private UserService service;
    @Resource
    private UserCardService userCardService;
    @Resource
    private VisitorsService visitorsService;
    
    //登录接口 获取用户信息 ，同时查出用户的门卡
    @RequestMapping(method=RequestMethod.GET,value="/login")
    @ResponseBody
	public Response login(@RequestParam String phone,@RequestParam String code){
    	
		//验证手机号码格式 ，短信码格式
		User user=  service.login( phone, code);
		_session.get().setAttribute("user",user);
		return new ResponseSuc(user);
	}
    
//    //登录接口 获取用户信息 ，同时查出用户的门卡
//    @RequestMapping(method=RequestMethod.GET,value="/{userId}")
//	public Response get(HttpServletRequest HttpServletResponse @PathVariable int userId){
//    	
//    	try {
////    		User sessionUser = this.userValidate( response);
////    		if(sessionUser.getPkid()!= userId) {
////    			throw new SjqException("操作异常，非法访问");
////    		}
//    		User user=  service.get(userId);
//    		ResponseObj responseObj=new ResponseObjSuc(user);
//			this.response( responseObj);
//		}catch (SjqException e) {
//			logger.error(e.getMessage());
//			this.response( new ResponseObjFai(e.getMessage()));
//		}catch (Exception e) {
//			logger.error("服务器异常", e);
//			this.response(new ResponseObjFai("服务器异常"));
//		}  
//	}
    
    //根据手机号获取用户信息
    @RequestMapping(method=RequestMethod.GET,value="/{phone}")
    @ResponseBody
	public Response queryByPhone(@PathVariable String phone){
    	
		this.userValidate(phone);
		User user=  service.queryByPhone(phone);
		return new ResponseSuc(user);
	}
    
    //根据手机号获取用户信息
    @RequestMapping(method=RequestMethod.GET,value="/{phone}/card")
    @ResponseBody
	public Response queryCardByPhone(@PathVariable String phone){
    	
		this.userValidate(phone);
		User user=  service.queryCardByPhone(phone);
		return new ResponseSuc(user);
	}
    
	//下发访客吗
    @RequestMapping(method=RequestMethod.POST,value="/{phone}/card/{cardId}/awarded")
    @ResponseBody
	public Response awardedCard(@PathVariable String phone,@PathVariable int cardId,@RequestBody JsonNode phoneStrJson){
    	
		this.userValidate(phone);
		String[] phones=phoneStrJson.get("phoneStr").asText().split(",");
		ArrayList<AwardResultDto> result = visitorsService.awarded(phone,cardId,phones);
		return new ResponseSuc(result);
	}
    
    //查询
    @RequestMapping(method=RequestMethod.GET,value="page")
    @ResponseBody
	public Response page(@PathVariable int pageNum,@PathVariable int pageSize){
		
    	HashMap<String,Object> map=this.getRequestParamMap();
		pageValidate(map);
		Page page=  service.page(pageNum, pageSize);
   		return new ResponseSuc("操作成功", page);
	}
}