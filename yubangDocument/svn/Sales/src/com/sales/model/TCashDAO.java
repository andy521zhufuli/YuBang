package com.sales.model;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for TCash
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.sales.model.TCash
 * @author MyEclipse Persistence Tools
 */

public class TCashDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(TCashDAO.class);
	// property constants
	public static final String USERID = "userid";
	public static final String AMOUNT = "amount";
	public static final String STATUS = "status";
	public static final String ACCOUNTTYPE = "accounttype";
	public static final String ACCOUNTNAME = "accountname";
	public static final String BANKNAME = "bankname";
	public static final String ACCOUNTID = "accountid";

	protected void initDao() {
		// do nothing
	}

	public void save(TCash transientInstance) {
		log.debug("saving TCash instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TCash persistentInstance) {
		log.debug("deleting TCash instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TCash findById(java.lang.String id) {
		log.debug("getting TCash instance with id: " + id);
		try {
			TCash instance = (TCash) getHibernateTemplate().get(
					"com.sales.model.TCash", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TCash> findByExample(TCash instance) {
		log.debug("finding TCash instance by example");
		try {
			List<TCash> results = (List<TCash>) getHibernateTemplate()
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
		log.debug("finding TCash instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TCash as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TCash> findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List<TCash> findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List<TCash> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List<TCash> findByAccounttype(Object accounttype) {
		return findByProperty(ACCOUNTTYPE, accounttype);
	}

	public List<TCash> findByAccountname(Object accountname) {
		return findByProperty(ACCOUNTNAME, accountname);
	}

	public List<TCash> findByBankname(Object bankname) {
		return findByProperty(BANKNAME, bankname);
	}

	public List<TCash> findByAccountid(Object accountid) {
		return findByProperty(ACCOUNTID, accountid);
	}

	public List findAll() {
		log.debug("finding all TCash instances");
		try {
			String queryString = "from TCash";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TCash merge(TCash detachedInstance) {
		log.debug("merging TCash instance");
		try {
			TCash result = (TCash) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TCash instance) {
		log.debug("attaching dirty TCash instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TCash instance) {
		log.debug("attaching clean TCash instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TCashDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TCashDAO) ctx.getBean("TCashDAO");
	}
}