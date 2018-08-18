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
import com.sjq.zmkm.dao.entity.User;
import com.sjq.zmkm.dao.entity.UserCardMapping;
import com.sjq.zmkm.exception.BusinessException;
import com.sjq.zmkm.service.UserCardService;
import com.sjq.zmkm.util.Constant;
import com.sjq.zmkm.util.DateUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserCardServiceImp implements UserCardService{   
	
	@Resource
    public UserDao userDao;

	@Resource
    public CardDao cardDao;
	
	@Resource
    public UserCardMappingDao userCardMappingDao;
	
//	@Override
//	public void applyCard(UserCardMapping b) throws SjqException {
//		
//		if(userDao.getById(b.getUserId())==null){
//			throw new SjqException("申请门卡失败：用户不存在");
//		}
//		if(cardDao.getById(b.getCardId())==null){
//			throw new SjqException("申请门卡失败：门卡不存在");
//		}
//		if(userCardMappingDao.queryByCardIdAndUserId(b.getCardId(), b.getUserId())!=null){
//			throw new SjqException("申请门卡失败：你已拥有该门卡");
//		}
//		//设置为待审核
//		//b.setStatus(Constant.Sys.STATUS_CHECK);
//		userCardMappingDao.save(b);
//	}
	@Override
	public ArrayList<AwardResultDto>  awarded(int cardId, String[] phoneStr) {
		if(cardDao.getById(cardId)==null){
			throw new BusinessException("下发钥匙失败：门禁不存在");
		}
		ArrayList<AwardResultDto> list=new ArrayList<AwardResultDto>();
		for(String phone : phoneStr){
			User user=userDao.queryByPhone(phone);
			if(user==null){
				//用户不存在 ，则开通用户,下发钥匙
				user =new User();
	 			user.setPhone(phone);
	 			user.setCreateTime(DateUtil.getCurrentDateStr());
	 			user.setImage("image");
	 			user.setName("德玛西亚");
	 			user.setSex(User.SexEnum.UNKNOW.ordinal());
	 			user.setStatus(User.StatusEnum.ENABLE.ordinal());
	 			//管理员下发钥匙 ，则用户为住户类型
	 			user.setType(User.TypeEnum.ZHUHU.ordinal());
	 			userDao.save(user);
	 			
	 			UserCardMapping mapping=new UserCardMapping();
	 			mapping.setCardId(cardId);
	 			mapping.setUserId(user.getPkid());
	 			userCardMappingDao.save(mapping);
	 			AwardResultDto result=new AwardResultDto();
	 			result.setPhone(phone);
				result.setFlag(true);
				result.setMsg("已为用户注册，并下发钥匙");
				list.add(result);
				continue;
			}
			//用户存在
			UserCardMapping UserCardMapping=userCardMappingDao.queryByCardIdAndUserId(cardId, user.getPkid());
			if(UserCardMapping!=null){	
				//已拥有钥匙
				AwardResultDto result=new AwardResultDto();
				result.setPhone(phone);
				result.setFlag(false);
				result.setMsg("用户已注册，且用户已拥有改钥匙");
				continue;
			}else{
				//未拥有钥匙
				UserCardMapping mapping=new UserCardMapping();
	 			mapping.setCardId(cardId);
	 			mapping.setUserId(user.getPkid());
	 			userCardMappingDao.save(mapping);
	 			AwardResultDto result=new AwardResultDto();
	 			result.setPhone(phone);
				result.setFlag(true);
				result.setMsg("用户已注册但未拥有该钥匙，为用户下发钥匙成功");
				list.add(result);
			}
		}
		return list;
	}
	
}