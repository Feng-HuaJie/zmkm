package com.sjq.zmkm.dao;

import java.util.ArrayList;

import com.sjq.zmkm.dao.entity.Card;

public interface CardDao extends BaseDao<Card> {

	Card queryByName(String name);

	ArrayList<Card> queryByParentId(int parentId);
}
