package com.sjq.zmkm.dao.imp;

import java.util.ArrayList;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sjq.zmkm.dao.VisitorsDao;
import com.sjq.zmkm.dao.entity.Visitors;

@Repository
public class VisitorsDaoImpl extends BaseDaoImp<Visitors> implements VisitorsDao {


	@Override
	public ArrayList<Visitors> queryByUserId(int userId) {
		
		ArrayList<Criterion> exprssions=new ArrayList<Criterion>();
		exprssions.add(Restrictions.eq("userId", userId));
		ArrayList<Visitors> Visitors=this.listAll(exprssions);
		return Visitors;
	}

	@Override
	public Visitors queryByUserIdAndMappingId(int userId, int userCardMappingId) {

		ArrayList<Criterion> exprssions=new ArrayList<Criterion>();
		exprssions.add(Restrictions.eq("userId", userId));
		ArrayList<Visitors> Visitors=this.listAll(exprssions);
		if(Visitors!=null && !Visitors.isEmpty()) {
			return Visitors.get(0);
		}
		return null;
	}
}
