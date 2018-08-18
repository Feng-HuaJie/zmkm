package com.sjq.zmkm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.sjq.zmkm.pojo.Page;

@SuppressWarnings("hiding")
public interface BaseDao <T> {
	
	public abstract void save(T bean);
	public abstract void update(T bean);
	public abstract void delete(T bean);
	public abstract T getById(int id);
	public abstract void saveOrUpdate(T bean);
	public abstract ArrayList<T> listAll(ArrayList<Criterion> exprssions);
	public abstract ArrayList<T> list(int pageNum,int pageSize,ArrayList<Criterion> exprssions);
	public abstract int count(ArrayList<Criterion> exprssions);
	public abstract Page page(int pageNum,int pageSize,ArrayList<Criterion> exprssions);
	public abstract <T> void saveList(List<T> entitys);
	public abstract <T> void updateList(List<T> entitys);
	public abstract <T> void saveOrUpdateList(List<T> entitys);
	public abstract void executeSql(String sqlString, Object[] values);
	//不建议使用 因为无法规避过多的dto问题，框架定位就是要干掉dto
	public abstract ArrayList<T> listByHql(int pageNum, int pageSize, String hql,Object[] values);
	public abstract int countByHql(String hql, Object[] values);
}
