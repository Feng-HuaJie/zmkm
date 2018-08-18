package com.sjq.zmkm.service.imp;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sjq.zmkm.dao.CardDao;
import com.sjq.zmkm.dao.UserCardMappingDao;
import com.sjq.zmkm.dao.entity.Card;
import com.sjq.zmkm.exception.BusinessException;
import com.sjq.zmkm.pojo.Page;
import com.sjq.zmkm.service.CardService;
import com.sjq.zmkm.util.DateUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CardServiceImp implements CardService{   
	
	@Resource
    public CardDao cardDao;
	
	@Resource
    public UserCardMappingDao userCardMappingDao;
	
	//查询所有的门卡信息 返回树形结构
	@Transactional(readOnly=true)
	public ArrayList<Card> listAll() {
		
//		ArrayList<Criterion> exprssions=new ArrayList<Criterion>();
//		exprssions.add(Restrictions.eq("parentId", parentId));
		ArrayList<Card> Card=cardDao.listAll(null);
		return Card;
	}

	@Override
	public void addCard(Card b)  {
		
		Card temp=cardDao.queryByName(b.getName());
		if(temp!=null ){
			throw new BusinessException("新增门卡失败：名字已存在");
		}
		//门卡是挂在楼栋下面的 判断楼栋是否存在
		temp=cardDao.getById(b.getParentId());
		if(temp==null){
			throw new BusinessException("新增门卡失败：楼栋不存在");
		}
		b.setType(Card.TypeEnum.CARD.ordinal());
		b.setStatus(Card.StatusEnum.ENABLE.ordinal());
		b.setCreateTime(DateUtil.getCurrentDateStr());
		cardDao.save(b);
	}
	
	@Override
	public void disable(int cardId)  {
		
		Card temp=cardDao.getById(cardId);
		if(temp==null ){
			throw new BusinessException("删除门卡失败：门卡不存在");
		}
		//修改为禁用
		temp.setStatus(Card.StatusEnum.DISABLE.ordinal());
		cardDao.update(temp);
	}
	@Override
	public void enable(int cardId)  {
		
		Card temp=cardDao.getById(cardId);
		if(temp==null ){
			throw new BusinessException("删除门卡失败：门卡不存在");
		}
		//修改为启用
		temp.setStatus(Card.StatusEnum.ENABLE.ordinal());
		cardDao.update(temp);
	}

	@Override
	public void updateCard(Card b)  {
		Card temp=cardDao.getById(b.getPkid());
		if(temp==null ){
			throw new BusinessException("修改门卡失败：门卡不存在");
		}
		temp=cardDao.queryByName(b.getName());
		if(temp!=null && temp.getPkid()!=b.getPkid()){
			throw new BusinessException("修改门卡失败：名字已存在");
		}
		temp.setMacAddress(b.getMacAddress());
		temp.setName(b.getName());
		temp.setOpenCmd(b.getOpenCmd());
		temp.setParentId(b.getParentId());
		cardDao.update(temp);
	}

	@Override
	public Page page(int pageNum, int pageSize) {
		//throw new BusinessException("短信验证码错误，短信验证码为1234!");
		//int i =8/0;
		return cardDao.page(pageNum, pageSize, null);
	}
}