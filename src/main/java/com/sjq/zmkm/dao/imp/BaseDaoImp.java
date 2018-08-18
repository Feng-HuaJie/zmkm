package com.sjq.zmkm.dao.imp;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Criterion;

import com.sjq.zmkm.dao.BaseDao;
import com.sjq.zmkm.pojo.Page;

@SuppressWarnings("unchecked")
public class BaseDaoImp<T> implements BaseDao<T> {

	@Resource
    private SessionFactory sessionFactory;

	protected Class<T> clazz;
	
	public BaseDaoImp(){
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}
	
    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
    //基本增删改查
	@Override
	public T getById(int id) {		
		return (T)getCurrentSession().get(clazz, id);
	}
	@Override
    public void save(T entity){
        getCurrentSession().save(entity);
        getCurrentSession().flush();
    }
	@Override
    public void update(T entity){
        getCurrentSession().update(entity);
        getCurrentSession().flush();
    }
	@Override
	public void delete(T entity) {
		getCurrentSession().delete(entity);
		getCurrentSession().flush();
	}
	@Override
    public void saveOrUpdate(T entity){
        getCurrentSession().saveOrUpdate(entity);
        getCurrentSession().flush();
        getCurrentSession().clear();
    }
    //批处理
	@Override
	@SuppressWarnings("hiding")
    public <T> void saveList(List<T> entitys){
        for (T entity : entitys)
        {
        	getCurrentSession().save(entity);
        }
    }
	@Override
	@SuppressWarnings("hiding")
    public <T> void updateList(List<T> entitys){
        for (T entity : entitys)
        {
        	getCurrentSession().update(entity);
        }
    }
	@Override
	@SuppressWarnings("hiding")
    public <T> void saveOrUpdateList(List<T> entitys){
        for (T entity : entitys)
        {
        	getCurrentSession().saveOrUpdate(entity);
        }
    }
	//简单的单表按条件查询 分页查询 等
	@Override
	public ArrayList<T> listAll(ArrayList<Criterion> exprssions) {
		
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		if(exprssions!=null){
			for(Criterion exprssion:exprssions){
				criteria.add(exprssion);
			}
		}
		ArrayList<T> list = (ArrayList<T>) criteria.list();
		return list;
	}
	@Override
	public ArrayList<T> list(int pageNum,int pageSize,ArrayList<Criterion> exprssions) {
		
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		criteria.setFirstResult((pageNum - 1) * pageSize).setMaxResults(pageSize);
		if(exprssions!=null){
			for(Criterion exprssion:exprssions){
				criteria.add(exprssion);
			}
		}
		ArrayList<T> list = (ArrayList<T>) criteria.list();
		return list;
	}
	@Override
	public int count(ArrayList<Criterion> exprssions) {
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		if(exprssions!=null){
			for(Criterion exprssion:exprssions){
				criteria.add(exprssion);
			}
		}
		criteria.setProjection(Projections.rowCount());
		String totalrecord=criteria.uniqueResult().toString();
		return Integer.parseInt(totalrecord);
	}
	public Page page(int pageNum,int pageSize,ArrayList<Criterion> exprssions){
		ArrayList<T> list=this.list(pageNum, pageSize, exprssions);
		int totalrecord=this.count(exprssions);
		Page page=new Page(pageNum,pageSize,totalrecord,list);
		return page;
	}
	//不建议使用 因为无法规避过多的dto问题，框架定位就是要干掉dto
	// "select new cn.itheima03.hibernate.domain.ClassesView(c.cname,s.sname) from Classes c,Student s where c.cid=s.classes.cid"
	//多表查询时 用的分页方法 较少使用 尽量少些hql 使用面向对象的方式操作数据库
	@Override
    public ArrayList<T> listByHql(int pageNum, int pageSize,String hql, Object... values) {
        Query query = getCurrentSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (ArrayList<T>)  query.setFirstResult((pageNum - 1) * pageSize).setMaxResults(pageSize).list();
    }
	@Override
    public int countByHql(String hql, Object... values) {
        Query query = getCurrentSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (Integer) query.uniqueResult();
    }
	//不建议使用 因为无法规避过多的dto问题，框架定位就是要干掉dto
	public Page pageByHql(int pageNum, int pageSize,String queryHql,String countHql,Object... values){
		ArrayList<T> list=this.listByHql(pageNum,pageSize,queryHql,values);
		int totalrecord=this.countByHql(countHql,values);
		Page page=new Page(pageNum,pageSize,totalrecord,list);
		return page;
	}
	/**
     * <执行Sql语句>
     * @param sqlString sql
     * @param values    不定参数数组
     */
	@Override
    public void executeSql(String sqlString, Object... values) {
        Query query = getCurrentSession().createSQLQuery(sqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        query.executeUpdate();
    }
//	public void test(){
//		this .pageByHql(1, 2, "", "", 3);
//	}
}
