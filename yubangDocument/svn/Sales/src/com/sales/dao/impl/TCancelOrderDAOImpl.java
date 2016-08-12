package com.sales.dao.impl;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sales.model.TCancelOrderDAO;


@Repository
public class TCancelOrderDAOImpl extends TCancelOrderDAO{
	
	  @Resource(name = "sessionFactory")
	    public void setSuperSessionFactory(SessionFactory sessionFactory)

	    {

	        super.setSessionFactory(sessionFactory);

	    }

}
