package com.sjq.zmkm.controller;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.sjq.zmkm.controller.resDto.AwardResultDto;
import com.sjq.zmkm.dao.entity.Card;
import com.sjq.zmkm.pojo.Response;
import com.sjq.zmkm.pojo.ResponseSuc;
import com.sjq.zmkm.service.CardService;
import com.sjq.zmkm.service.UserCardService;
import com.sjq.zmkm.service.UserService;

//管理员入口，这里管理员就是业主 形式业主职权
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{
     
    @Resource
    private UserService service;
    @Resource
    private CardService cardService;
    @Resource
    private UserCardService userCardService;
    
    //业主查询所有门卡
    @RequestMapping(method=RequestMethod.GET,value="/{adminId}/card")
    @ResponseBody
	public Response listCard(@PathVariable int adminId){
		
    	this.adminValidate(adminId);
		ArrayList<Card> cards=cardService.listAll();
		return new ResponseSuc(cards);
	}
       
    //添加门卡
    @RequestMapping(method=RequestMethod.POST,value="/{adminId}/card")
    @ResponseBody
	public Response addCard(@PathVariable int adminId,@RequestBody Card card){
    	
		this.adminValidate(adminId);
		cardService.addCard(card);
		return new ResponseSuc();
	}
    
    @RequestMapping(method=RequestMethod.PUT,value="/{adminId}/card/{cardId}")
    @ResponseBody
	public Response put(@PathVariable int adminId,@PathVariable int cardId,@RequestBody Card card){
	  	
  		 this.adminValidate(adminId);
		 card.setPkid(cardId);
		 cardService.updateCard(card);
		 return new ResponseSuc("操作成功");
	}
	//删除门卡 实际是修改操作
    @RequestMapping(value="/{adminId}/card/{cardId}/disable",method=RequestMethod.PUT)
    @ResponseBody
	public Response delete(@PathVariable int adminId,@PathVariable int cardId){
	
		 this.adminValidate(adminId);
		 cardService.disable(cardId);
		 return new ResponseSuc("操作成功");  
	 }
    
    //修改
 	@RequestMapping(method=RequestMethod.PUT,value="/{adminId}/card/{cardId}/enable")
    @ResponseBody
 	public Response enable(@PathVariable int adminId,@PathVariable int cardId){
		
 		 this.adminValidate(adminId);
		 cardService.enable(cardId);
		 return new ResponseSuc("操作成功");
	}
 	//下发门卡
    @RequestMapping(method=RequestMethod.POST,value="/{adminId}/card/{cardId}/awarded")
    @ResponseBody
	public Response awardedCard(@PathVariable int adminId,@PathVariable int cardId,@RequestBody JsonNode phoneStrJson){
    	
		this.adminValidate(adminId);
		String[] phones=phoneStrJson.get("phoneStr").asText().split(",");
		ArrayList<AwardResultDto> result  = userCardService.awarded(cardId,phones);
		return new ResponseSuc(result);
	}
}