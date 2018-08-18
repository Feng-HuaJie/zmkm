package com.sjq.zmkm.dao;

import java.util.ArrayList;

import com.sjq.zmkm.dao.entity.UserCardMapping;

public interface UserCardMappingDao extends BaseDao<UserCardMapping> {

	ArrayList<UserCardMapping> queryByUserId(int userId);

	ArrayList<UserCardMapping> queryByCardId(int cardId);

	UserCardMapping queryByCardIdAndUserId(int cardId, int userId);

}
