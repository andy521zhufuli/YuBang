package com.sales.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.sales.model.TCash;
import com.sales.model.TCashDAO;

@Repository
public class TCashDAOImpl extends TCashDAO {
	  @Resource(name = "sessionFactory")
	    public void setSuperSessionFactory(SessionFactory sessionFactory)

	    {

	        super.setSessionFactory(sessionFactory);

	    }
	  
	  /**
	   * 查询提现历史记录
	   * */
		public List<TCash> getCashList(String usrid,String status,  int start, int end){
			String sql = null;
			if(status != null)
			{
				sql = "select * from t_cash t where t.userid = ? and t.status = ?  order by updatetime desc  limit ?,?";
			}
			else
			{
				sql = "select * from t_cash t where t.userid = ?  order by updatetime desc  limit ?,?";
			}
			SQLQuery sqlQuery = getSessionFactory().getCurrentSession().createSQLQuery(sql);
			sqlQuery.setInteger(0, Integer.valueOf(usrid));
			if(status != null)
			{
				sqlQuery.setString(1, status);
				sqlQuery.setInteger(2, start);
				sqlQuery.setInteger(3, end -start);
			}
			else
			{
				sqlQuery.setInteger(1, start);
				sqlQuery.setInteger(2, end -start);
			}
			List<TCash> list = sqlQuery.addEntity(TCash.class).list();
			return list;
		}


}