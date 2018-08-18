package com.sjq.zmkm.service.imp;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sjq.zmkm.controller.resDto.VisitorsCardDto;
import com.sjq.zmkm.dao.UserCardMappingDao;
import com.sjq.zmkm.dao.UserDao;
import com.sjq.zmkm.dao.VisitorsDao;
import com.sjq.zmkm.dao.entity.Card;
import com.sjq.zmkm.dao.entity.User;
import com.sjq.zmkm.dao.entity.UserCardMapping;
import com.sjq.zmkm.dao.entity.Visitors;
import com.sjq.zmkm.exception.BusinessException;
import com.sjq.zmkm.pojo.Page;
import com.sjq.zmkm.service.UserService;
import com.sjq.zmkm.util.Constant;
import com.sjq.zmkm.util.DateUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserServiceImp implements UserService{   
	
	@Resource
    public UserDao userDao;
	
	@Resource
    public UserCardMappingDao userCardMappingDao;
	
	@Resource
	public VisitorsDao visitorsDao;
	
	//注册业务 改注册业务并不单独使用
//	@Override
//	public void register(User b) throws SjqException {
//		
//		User temp =userDao.queryByPhone(b.getPhone());
//		if(temp!=null && temp.getPkid() != b.getPkid()){
//			throw new SjqException("注册用户失败：号码"+b.getPhone()+"已注册");
//		}
//		userDao.save(b);
//	}

	@Override
	public User login(String phone,String code) {
		
		//确认是本机主人
		if(!code.equals("1234")){
			throw new BusinessException("短信验证码错误，短信验证码为1234!");
		}
		//先调用获取用户信息业务
		User user =this.queryByPhone(phone);
		//若用户未注册 则直接注册 并返回
 		if(user==null){
			//throw new SjqException("该手机号码未注册!");
 			user =new User();
 			user.setPhone(phone);
 			user.setCreateTime(DateUtil.getCurrentDateStr());
 			user.setImage("image");
 			user.setName("德玛西亚");
 			user.setSex(User.SexEnum.UNKNOW.ordinal());
 			user.setStatus(User.StatusEnum.ENABLE.ordinal());
 			user.setType(User.TypeEnum.FANGKE.ordinal());
 			userDao.save(user);
 			return user;
		}
		//用户钥匙信息
		ArrayList<Card> cards=new ArrayList<Card>();
		if(user!=null){
			ArrayList<UserCardMapping> mappings=userCardMappingDao.queryByUserId(user.getPkid());
			for(UserCardMapping mapping:mappings){
				cards.add(mapping.getCard());
			}
			user.setCards(cards);
		}
		//返回访客钥匙信息
		ArrayList<VisitorsCardDto> visitorsCards=new ArrayList<VisitorsCardDto>();
		if(user!=null){
			ArrayList<Visitors> Visitors=visitorsDao.queryByUserId(user.getPkid());
			for(Visitors visitors:Visitors){
				if(DateUtil.isExpired(visitors.getEndTime())<0||visitors.getNum()<=0) {
					//失效了的访客钥匙
					visitorsDao.delete(visitors);
					continue;
				}
				//获取访客钥匙有效时间和使用次数
				VisitorsCardDto visitorsCard=new VisitorsCardDto(); 
				visitorsCard.setStartTime(visitors.getStartTime());
				visitorsCard.setEndTime(visitors.getEndTime());
				visitorsCard.setNum(visitors.getNum());
				//获取邀请人名字，访客钥匙名字
				User inviter=visitors.getUserCardMapping().getUser();
				visitorsCard.setInviterName(inviter.getName());
				Card card=visitors.getUserCardMapping().getCard();
				visitorsCard.setPkid(card.getPkid());
				visitorsCard.setCardName(card.getName());
				visitorsCard.setMacAddress(card.getMacAddress());
				visitorsCard.setOpenCmd(card.getOpenCmd());
				visitorsCards.add(visitorsCard);
			}
			user.setVisitorsCards(visitorsCards);
		}
		return user;
	}

	@Override
	public User get(int uesrId) {
		
		User user = userDao.getById(uesrId);
		if(user == null) {
			throw new BusinessException("用户不存在");
		}
		return user;
	}

	@Override
	public User queryByPhone(String phone) {
		User user = userDao.queryByPhone(phone);
//		if(user==null) {
//			throw new SjqException("查询用户信息失败，该用户不存在");
//		}
		return user;
	}
	
	@Override
	public User queryCardByPhone(String phone) {
		
		//先调用获取用户信息业务
		User user =this.queryByPhone(phone);
		//若用户未注册 则直接注册 并返回
 		if(user==null){
			throw new BusinessException("查询用户钥匙信息失败，该用户不存在"); 
 		}
		//用户钥匙信息
		ArrayList<Card> cards=new ArrayList<Card>();
		ArrayList<UserCardMapping> mappings=userCardMappingDao.queryByUserId(user.getPkid());
		for(UserCardMapping mapping:mappings){
			cards.add(mapping.getCard());
		}
		user.setCards(cards);
		//返回访客钥匙信息
		ArrayList<VisitorsCardDto> visitorsCards=new ArrayList<VisitorsCardDto>();
		ArrayList<Visitors> Visitors=visitorsDao.queryByUserId(user.getPkid());
		for(Visitors visitors:Visitors){
			
			if(DateUtil.isExpired(visitors.getEndTime())<0||visitors.getNum()<=0) {
				//失效了的访客钥匙
				visitorsDao.delete(visitors);
				continue;
			}
			//获取访客钥匙有效时间和使用次数
			VisitorsCardDto visitorsCard=new VisitorsCardDto(); 
			visitorsCard.setStartTime(visitors.getStartTime());
			visitorsCard.setEndTime(visitors.getEndTime());
			visitorsCard.setNum(visitors.getNum());
			//获取邀请人名字，访客钥匙名字
			
			Card card=visitors.getUserCardMapping().getCard();
			visitorsCard.setPkid(card.getPkid());
			visitorsCard.setCardName(card.getName());
			visitorsCard.setMacAddress(card.getMacAddress());
			visitorsCard.setOpenCmd(card.getOpenCmd());
			
			User inviter=visitors.getUserCardMapping().getUser();
			visitorsCard.setInviterName(inviter.getName());
			
			visitorsCards.add(visitorsCard);
		}
		user.setVisitorsCards(visitorsCards);
		return user;
	}

	@Override
	public Page page(int pageNum, int pageSize) {
		return userDao.page(pageNum, pageSize, null);
	}
}