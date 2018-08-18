package com.sjq.zmkm.dao;

import com.sjq.zmkm.dao.entity.User;

public interface UserDao extends BaseDao<User> {

	User queryByPhone(String phone);

	//User login(User b);
	
	
}
