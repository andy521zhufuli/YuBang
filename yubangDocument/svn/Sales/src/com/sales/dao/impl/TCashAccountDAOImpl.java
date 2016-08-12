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
import com.sales.model.TCashAccount;
import com.sales.model.TCashAccountDAO;

@Repository
public class TCashAccountDAOImpl extends TCashAccountDAO {
	  @Resource(name = "sessionFactory")
	    public void setSuperSessionFactory(SessionFactory sessionFactory)

	    {

	        super.setSessionFactory(sessionFactory);

	    }

	public List<TCashAccount> getCashAccount(int usrid) {
		String sql = "select * from t_cash_account t  where  t.userid = ? order by updatetime desc ";
		SQLQuery sqlQuery = getSessionFactory().getCurrentSession().createSQLQuery(sql);
		sqlQuery.setInteger(0, Integer.valueOf(usrid));
		List<TCashAccount> list = sqlQuery.addEntity(TCashAccount.class).list();
		return list;
	}
}