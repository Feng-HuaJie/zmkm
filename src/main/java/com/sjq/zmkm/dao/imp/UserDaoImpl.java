package com.sjq.zmkm.dao.imp;

import java.util.ArrayList;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sjq.zmkm.dao.UserDao;
import com.sjq.zmkm.dao.entity.User;

@Repository
public class UserDaoImpl extends BaseDaoImp<User> implements UserDao {


	@Override
	public User queryByPhone(String phone) {
		
		ArrayList<Criterion> exprssions=new ArrayList<Criterion>();
		exprssions.add(Restrictions.eq("phone", phone));
		ArrayList<User> Users=this.listAll(exprssions);
		if(null!=Users && !Users.isEmpty()){
			return Users.get(0);
		}
		return null;
	}
	
}
