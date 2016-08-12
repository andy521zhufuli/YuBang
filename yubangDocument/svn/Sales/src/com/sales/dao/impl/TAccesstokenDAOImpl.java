package com.sales.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.sales.model.TAccesstokenDAO;
import com.sales.model.TAddress;
import com.sales.model.TAddressDAO;
import com.sales.model.TGoods;

@Repository
public class TAccesstokenDAOImpl extends TAccesstokenDAO {
	  @Resource(name = "sessionFactory")
	    public void setSuperSessionFactory(SessionFactory sessionFactory)
	    {

	        super.setSessionFactory(sessionFactory);

	    }


}