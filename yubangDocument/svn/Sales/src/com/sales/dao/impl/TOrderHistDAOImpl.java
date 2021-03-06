package com.sales.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.common.web.hibernate.IBaseDAOBase;
import com.sales.model.TOrderHist;
import com.sales.model.TOrderHistDAO;

@Repository
public class TOrderHistDAOImpl extends TOrderHistDAO {
	  @Resource(name = "sessionFactory")
	    public void setSuperSessionFactory(SessionFactory sessionFactory)
	    {

	        super.setSessionFactory(sessionFactory);

	    }
	  
}