package com.sales.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.sales.model.TCash;
import com.sales.model.TCashHist;
import com.sales.model.TCashHistDAO;
import com.sales.model.TGoods;
import com.sales.model.TUsr;

@Repository
public class TCashHistDAOImpl extends TCashHistDAO {
	  @Resource(name = "sessionFactory")
	    public void setSuperSessionFactory(SessionFactory sessionFactory)
	    {

	        super.setSessionFactory(sessionFactory);

	    }
	  


}