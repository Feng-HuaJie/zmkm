package com.sjq.zmkm.dao.imp;

import java.util.ArrayList;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sjq.zmkm.dao.CardDao;
import com.sjq.zmkm.dao.entity.Card;

@Repository
public class CardDaoImpl extends BaseDaoImp<Card> implements CardDao {


	@Override
	public Card queryByName(String name) {
		
		ArrayList<Criterion> exprssions=new ArrayList<Criterion>();
		exprssions.add(Restrictions.eq("name", name));
		exprssions.add(Restrictions.eq("type", Card.TypeEnum.CARD.ordinal()));
		ArrayList<Card> Card=this.listAll(exprssions);
		if(null!=Card && !Card.isEmpty()){
			return Card.get(0);
		}
		return null;
	}
	//通过父id查找所有直接子节点
	@Override
	public ArrayList<Card> queryByParentId(int parentId) {
		
		ArrayList<Criterion> exprssions=new ArrayList<Criterion>();
		exprssions.add(Restrictions.eq("parentId", parentId));
		ArrayList<Card> Card=this.listAll(exprssions);
		return Card;
	}
}
