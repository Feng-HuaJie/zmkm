package com.sjq.zmkm.service;

import com.sjq.zmkm.dao.entity.User;
import com.sjq.zmkm.exception.BusinessException;
import com.sjq.zmkm.pojo.Page;

public interface UserService {
	

	public User login(String phone,String code) throws BusinessException;

	public User get(int uesrId) throws BusinessException;

	public User queryByPhone(String phone) throws BusinessException;

	User queryCardByPhone(String phone) throws BusinessException;

	public Page page(int pageNum, int pageSize);

	//void register(User b) throws SjqException;

}
