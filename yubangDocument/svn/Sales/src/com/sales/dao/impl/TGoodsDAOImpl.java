package com.sales.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.sales.model.TGoods;
import com.sales.model.TGoodsDAO;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;

@Repository
public class TGoodsDAOImpl extends TGoodsDAO {
	
	  @Resource(name = "sessionFactory")
	    public void setSuperSessionFactory(SessionFactory sessionFactory)
	    {

	        super.setSessionFactory(sessionFactory);

	    }
	
	//public final static String GETGOODSSQL = "select * from t_goods t ";
	
	public List<TGoods> getGoods(int begin , int end){
		String sql = "select * from t_goods t  where t.order > 0 order by t.order asc limit ?,?";
		SQLQuery sqlQuery = getSessionFactory().getCurrentSession().createSQLQuery(sql);
		sqlQuery.setInteger(0, begin);
		sqlQuery.setInteger(1, end-begin);
		List<TGoods> list = sqlQuery.addEntity(TGoods.class).list();
		return list;
	}
}