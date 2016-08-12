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
 * TCashAccount entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.sales.model.TCashAccount
 * @author MyEclipse Persistence Tools
 */

public class TCashAccountDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TCashAccountDAO.class);
	// property constants
	public static final String USERID = "userid";
	public static final String TYPE = "type";
	public static final String ACCOUNTID = "accountid";
	public static final String ACCOUNTNAME = "accountname";
	public static final String BANKNAME = "bankname";
	public static final String SELECTED = "selected";

	protected void initDao() {
		// do nothing
	}

	public void save(TCashAccount transientInstance) {
		log.debug("saving TCashAccount instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TCashAccount persistentInstance) {
		log.debug("deleting TCashAccount instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TCashAccount findById(java.lang.Integer id) {
		log.debug("getting TCashAccount instance with id: " + id);
		try {
			TCashAccount instance = (TCashAccount) getHibernateTemplate().get(
					"com.sales.model.TCashAccount", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TCashAccount> findByExample(TCashAccount instance) {
		log.debug("finding TCashAccount instance by example");
		try {
			List<TCashAccount> results = (List<TCashAccount>) getHibernateTemplate()
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
		log.debug("finding TCashAccount instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TCashAccount as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TCashAccount> findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List<TCashAccount> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<TCashAccount> findByAccountid(Object accountid) {
		return findByProperty(ACCOUNTID, accountid);
	}

	public List<TCashAccount> findByAccountname(Object accountname) {
		return findByProperty(ACCOUNTNAME, accountname);
	}

	public List<TCashAccount> findByBankname(Object bankname) {
		return findByProperty(BANKNAME, bankname);
	}

	public List<TCashAccount> findBySelected(Object selected) {
		return findByProperty(SELECTED, selected);
	}

	public List findAll() {
		log.debug("finding all TCashAccount instances");
		try {
			String queryString = "from TCashAccount";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TCashAccount merge(TCashAccount detachedInstance) {
		log.debug("merging TCashAccount instance");
		try {
			TCashAccount result = (TCashAccount) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TCashAccount instance) {
		log.debug("attaching dirty TCashAccount instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TCashAccount instance) {
		log.debug("attaching clean TCashAccount instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TCashAccountDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (TCashAccountDAO) ctx.getBean("TCashAccountDAO");
	}
}