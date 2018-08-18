package com.sjq.zmkm.dao.imp;

import java.util.ArrayList;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sjq.zmkm.dao.UserCardMappingDao;
import com.sjq.zmkm.dao.entity.UserCardMapping;

@Repository
public class UserCardDaoImpl extends BaseDaoImp<UserCardMapping> implements UserCardMappingDao {


	@Override
	public UserCardMapping queryByCardIdAndUserId(int cardId, int userId) {
		
		ArrayList<Criterion> exprssions=new ArrayList<Criterion>();
		exprssions.add(Restrictions.eq("cardId", cardId));
		exprssions.add(Restrictions.eq("userId", userId));
		ArrayList<UserCardMapping> UserCardMapping=this.listAll(exprssions);
		if(null!=UserCardMapping && !UserCardMapping.isEmpty()){
			return UserCardMapping.get(0);
		}
		return null;
	}

	//查询用户门卡 过滤掉地区
	@Override
	public ArrayList<UserCardMapping> queryByUserId(int userId) {
		
		ArrayList<Criterion> exprssions=new ArrayList<Criterion>();
		exprssions.add(Restrictions.eq("userId", userId));
		ArrayList<UserCardMapping> UserCardMapping=this.listAll(exprssions);
		return UserCardMapping;
	}

	@Override
	public ArrayList<UserCardMapping> queryByCardId(int cardId) {
		
		ArrayList<Criterion> exprssions=new ArrayList<Criterion>();
		exprssions.add(Restrictions.eq("cardId", cardId));
		ArrayList<UserCardMapping> UserCardMapping=this.listAll(exprssions);
		return UserCardMapping;
	}

}
