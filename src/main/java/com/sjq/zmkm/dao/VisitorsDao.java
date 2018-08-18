package com.sjq.zmkm.dao;

import java.util.ArrayList;

import com.sjq.zmkm.dao.entity.Visitors;

public interface VisitorsDao extends BaseDao<Visitors> {

	ArrayList<Visitors> queryByUserId(int userId);

	Visitors queryByUserIdAndMappingId(int userId,int userCardMappingId);
}
