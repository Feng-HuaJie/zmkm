package com.sjq.zmkm.service.imp;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sjq.zmkm.controller.resDto.AwardResultDto;
import com.sjq.zmkm.dao.CardDao;
import com.sjq.zmkm.dao.UserCardMappingDao;
import com.sjq.zmkm.dao.UserDao;
import com.sjq.zmkm.dao.VisitorsDao;
import com.sjq.zmkm.dao.entity.Card;
import com.sjq.zmkm.dao.entity.User;
import com.sjq.zmkm.dao.entity.UserCardMapping;
import com.sjq.zmkm.dao.entity.Visitors;
import com.sjq.zmkm.exception.BusinessException;
import com.sjq.zmkm.service.VisitorsService;
import com.sjq.zmkm.util.Constant;
import com.sjq.zmkm.util.DateUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VisitorsServiceImp implements VisitorsService{   
	
	@Resource
    public UserDao userDao;
	
	@Resource
    public UserCardMappingDao userCardMappingDao;
	
	@Resource
	public VisitorsDao visitorsDao;
	
	
	@Resource
    public CardDao cardDao;
	
	/*
	 * phone 下发访客钥匙者的手机号码
	 * cardid 要下发的门禁id
	 * phoneStr 要下发的用户数组
	 */
	@Override
	public ArrayList<AwardResultDto>  awarded(String phone,int cardId, String[] phoneStr) {
		
		User user =userDao.queryByPhone(phone);
		if(user == null) {
			throw new BusinessException("下发访客钥匙失败：申请人不存在");
		}
		Card card = cardDao.getById(cardId);
		if(card==null){
			throw new BusinessException("下发访客钥匙失败：门禁不存在");
		}
		UserCardMapping mapping = userCardMappingDao.queryByCardIdAndUserId( card.getPkid(),user.getPkid());
		if(mapping==null){
			throw new BusinessException("下发访客钥匙失败：您不是该门禁的住户，不能下发访客钥匙");
		}
		ArrayList<AwardResultDto> list=new ArrayList<AwardResultDto>();
		for(String p : phoneStr){
			User u=userDao.queryByPhone(p);
			if(u==null){
				//用户不存在 ，则开通用户,下发钥匙
				u =new User();
	 			u.setPhone(p);
	 			u.setCreateTime(DateUtil.getCurrentDateStr());
	 			u.setImage("image");
	 			u.setName("德玛西亚");
	 			u.setSex(User.SexEnum.UNKNOW.ordinal());
	 			u.setStatus(User.StatusEnum.ENABLE.ordinal());
	 			//用户下发访客钥匙 ，为访客注册访客用户
				u.setType(User.TypeEnum.FANGKE.ordinal());
	 			userDao.save(u);
	 			
	 			Visitors visitors=new Visitors();
	 			visitors.setStartTime(DateUtil.getCurrentDateStr());
	 			visitors.setEndTime(DateUtil.getAfterDateMiddleNight());
	 			visitors.setNum(Constant.Sys.USE_COUNT);
	 			visitors.setUserCardMappingId(mapping.getPkid());
	 			visitors.setUserId(u.getPkid());
	 			visitorsDao.save(visitors);
	 			
	 			AwardResultDto result=new AwardResultDto();
	 			result.setPhone(phone);
				result.setFlag(true);
				result.setMsg("已为用户注册，并下发钥匙");
				list.add(result);
				continue;
			}
			
			//如果用户拥用了住户钥匙 则不能下发访客钥匙
			if(userCardMappingDao.queryByCardIdAndUserId(cardId, u.getPkid())!=null){
				AwardResultDto result=new AwardResultDto();
				result.setPhone(phone);
				result.setFlag(false);
				result.setMsg("用户已注册且拥有该门禁的住户钥匙，不能下发访客钥匙");
				list.add(result);
				continue;
			}
			
			//用户存在
			Visitors visitors = visitorsDao.queryByUserIdAndMappingId(u.getPkid(),mapping.getPkid());
			if(visitors!=null){	
				//已拥有访客钥匙 则更新访客钥匙的有效时间 和使用次数
				visitors.setStartTime(DateUtil.getCurrentDateStr());
	 			visitors.setEndTime(DateUtil.getAfterDateMiddleNight());
				visitors.setNum(Constant.Sys.USE_COUNT);
				visitorsDao.update(visitors);
				
				AwardResultDto result=new AwardResultDto();
				result.setPhone(phone);
				result.setFlag(true);
				result.setMsg("用户已注册且拥有改钥匙，为该用户跟新钥匙有效期和使用次数");
				list.add(result);
				continue;
			}else{
				//未拥有访客钥匙
	 			visitors=new Visitors();
	 			visitors.setStartTime(DateUtil.getCurrentDateStr());
	 			visitors.setEndTime(DateUtil.getAfterDateMiddleNight());
	 			visitors.setNum(Constant.Sys.USE_COUNT);
	 			visitors.setUserCardMappingId(mapping.getPkid());
	 			visitors.setUserId(u.getPkid());
	 			visitorsDao.save(visitors);
 		 			
	 			AwardResultDto result=new AwardResultDto();
	 			result.setPhone(phone);
				result.setFlag(true);
				result.setMsg("用户已注册但未拥有该访客钥匙，为用户下发访客钥匙成功");
				list.add(result);
			}
		}
		return list;
	}

}