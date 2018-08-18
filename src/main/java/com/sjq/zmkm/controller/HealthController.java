package com.sjq.zmkm.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sjq.zmkm.dao.entity.User;
import com.sjq.zmkm.pojo.Response;
import com.sjq.zmkm.pojo.ResponseSuc;

@Controller
@RequestMapping("/health")
public class HealthController extends BaseController{
     
//    @Resource
//    private CardService service;
    
	  //查询
    @RequestMapping(method=RequestMethod.GET,value="test")
    @ResponseBody
	public Response test(){
    	
   		return new ResponseSuc("health test操作成功");
	}
    //查询
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
	public Response page(@RequestParam int pageNum,@RequestParam int pageSize){
    	
   		return new ResponseSuc("health get操作成功");
	}
    
    @RequestMapping(method=RequestMethod.PUT,value="/{pkid}")
	@ResponseBody
 	public Response put(@PathVariable int pkid,@RequestBody List<User> user){
		
    	return new ResponseSuc("health put操作成功",user);
	}
    
    @RequestMapping(method=RequestMethod.POST)
	@ResponseBody
 	public Response post(@RequestBody User user){
    	
    	return new ResponseSuc("health post操作成功",user);
	}
    
    //删除
    @RequestMapping(method=RequestMethod.DELETE,value="/{pkid}")
	@ResponseBody
 	public Response delete(@PathVariable int pkid){
    	
    	return new ResponseSuc("health delete操作成功");
    } 
}