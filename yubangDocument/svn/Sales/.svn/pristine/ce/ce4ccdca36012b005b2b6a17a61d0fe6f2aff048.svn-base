package com.sales.model;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * TAccesstoken entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sales.model.TAccesstoken
 * @author MyEclipse Persistence Tools
 */

public class TAccesstokenDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TAccesstokenDAO.class);
	// property constants
	public static final String ACCESSTOKEN = "accesstoken";
	public static final String TRIGGERS = "triggers";

	protected void initDao() {
		// do nothing
	}

	public void save(TAccesstoken transientInstance) {
		log.debug("saving TAccesstoken instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TAccesstoken persistentInstance) {
		log.debug("deleting TAccesstoken instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TAccesstoken findById(java.lang.Integer id) {
		log.debug("getting TAccesstoken instance with id: " + id);
		try {
			TAccesstoken instance = (TAccesstoken) getHibernateTemplate().get(
					"com.sales.model.TAccesstoken", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TAccesstoken> findByExample(TAccesstoken instance) {
		log.debug("finding TAccesstoken instance by example");
		try {
			List<TAccesstoken> results = (List<TAccesstoken>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TAccesstoken instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TAccesstoken as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TAccesstoken> findByAccesstoken(Object accesstoken) {
		return findByProperty(ACCESSTOKEN, accesstoken);
	}

	public List<TAccesstoken> findByTriggers(Object triggers) {
		return findByProperty(TRIGGERS, triggers);
	}

	public List findAll() {
		log.debug("finding all TAccesstoken instances");
		try {
			String queryString = "from TAccesstoken";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TAccesstoken merge(TAccesstoken detachedInstance) {
		log.debug("merging TAccesstoken instance");
		try {
			TAccesstoken result = (TAccesstoken) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TAccesstoken instance) {
		log.debug("attaching dirty TAccesstoken instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TAccesstoken instance) {
		log.debug("attaching clean TAccesstoken instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TAccesstokenDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (TAccesstokenDAO) ctx.getBean("TAccesstokenDAO");
	}
}