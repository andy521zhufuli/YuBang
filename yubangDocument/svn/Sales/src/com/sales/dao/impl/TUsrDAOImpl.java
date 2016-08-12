package com.sales.dao.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.common.web.hibernate.IBaseDAOBase;
import com.sales.model.TAddress;
import com.sales.model.TUsr;
import com.sales.model.TUsrDAO;

@Repository
public class TUsrDAOImpl extends TUsrDAO {
	  @Resource(name = "sessionFactory")
	    public void setSuperSessionFactory(SessionFactory sessionFactory)
	    {

	        super.setSessionFactory(sessionFactory);

	    }
	  
	  public List<TUsr>  getFriendList(int puserid)
	  {
		  String sql =  "select userid, username, nickname, headimgurl from t_usr  t where t.puserid = " + puserid+ " order by t.nickname desc";
		  SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		  List<TUsr> list = sqlQuery.addEntity(TUsr.class).list();
		  return list;
	  }

	  /**
	   * 更新用户的提现余额
	   * */
	public void updateBalance(int userid, int cashBalance) {
		String sql = "update t_usr  set cashbalance=? where userid = ?";
		Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.setInteger(0, cashBalance);
		query.setInteger(1, userid);
		query.executeUpdate();		
	}
	 
}