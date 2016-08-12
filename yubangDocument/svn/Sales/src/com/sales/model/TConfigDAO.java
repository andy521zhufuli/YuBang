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
 * TConfig entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sales.model.TConfig
 * @author MyEclipse Persistence Tools
 */

public class TConfigDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(TConfigDAO.class);
	// property constants
	public static final String VALUE = "value";
	public static final String OPERATOR = "operator";

	protected void initDao() {
		// do nothing
	}

	public void save(TConfig transientInstance) {
		log.debug("saving TConfig instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TConfig persistentInstance) {
		log.debug("deleting TConfig instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TConfig findById(java.lang.String id) {
		log.debug("getting TConfig instance with id: " + id);
		try {
			TConfig instance = (TConfig) getHibernateTemplate().get(
					"com.sales.model.TConfig", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TConfig> findByExample(TConfig instance) {
		log.debug("finding TConfig instance by example");
		try {
			List<TConfig> results = (List<TConfig>) getHibernateTemplate()
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
		log.debug("finding TConfig instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TConfig as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TConfig> findByValue(Object value) {
		return findByProperty(VALUE, value);
	}

	public List<TConfig> findByOperator(Object operator) {
		return findByProperty(OPERATOR, operator);
	}

	public List findAll() {
		log.debug("finding all TConfig instances");
		try {
			String queryString = "from TConfig";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TConfig merge(TConfig detachedInstance) {
		log.debug("merging TConfig instance");
		try {
			TConfig result = (TConfig) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TConfig instance) {
		log.debug("attaching dirty TConfig instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TConfig instance) {
		log.debug("attaching clean TConfig instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TConfigDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TConfigDAO) ctx.getBean("TConfigDAO");
	}
}