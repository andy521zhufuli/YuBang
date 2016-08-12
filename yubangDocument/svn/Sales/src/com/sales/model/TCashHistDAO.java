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
 * TCashHist entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sales.model.TCashHist
 * @author MyEclipse Persistence Tools
 */

public class TCashHistDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TCashHistDAO.class);
	// property constants
	public static final String CASHID = "cashid";
	public static final String STATUS = "status";
	public static final String OPERATOR = "operator";
	public static final String OPERATION = "operation";
	public static final String NOTES = "notes";
	public static final String AMOUNT = "amount";
	public static final String ACCOUNTTYPE = "accounttype";
	public static final String ACCOUNTNAME = "accountname";
	public static final String ACCOUNTID = "accountid";
	public static final String BANKNAME = "bankname";
	public static final String USERID = "userid";

	protected void initDao() {
		// do nothing
	}

	public void save(TCashHist transientInstance) {
		log.debug("saving TCashHist instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TCashHist persistentInstance) {
		log.debug("deleting TCashHist instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TCashHist findById(java.lang.Integer id) {
		log.debug("getting TCashHist instance with id: " + id);
		try {
			TCashHist instance = (TCashHist) getHibernateTemplate().get(
					"com.sales.model.TCashHist", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TCashHist> findByExample(TCashHist instance) {
		log.debug("finding TCashHist instance by example");
		try {
			List<TCashHist> results = (List<TCashHist>) getHibernateTemplate()
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
		log.debug("finding TCashHist instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TCashHist as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TCashHist> findByCashid(Object cashid) {
		return findByProperty(CASHID, cashid);
	}

	public List<TCashHist> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List<TCashHist> findByOperator(Object operator) {
		return findByProperty(OPERATOR, operator);
	}

	public List<TCashHist> findByOperation(Object operation) {
		return findByProperty(OPERATION, operation);
	}

	public List<TCashHist> findByNotes(Object notes) {
		return findByProperty(NOTES, notes);
	}

	public List<TCashHist> findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List<TCashHist> findByAccounttype(Object accounttype) {
		return findByProperty(ACCOUNTTYPE, accounttype);
	}

	public List<TCashHist> findByAccountname(Object accountname) {
		return findByProperty(ACCOUNTNAME, accountname);
	}

	public List<TCashHist> findByAccountid(Object accountid) {
		return findByProperty(ACCOUNTID, accountid);
	}

	public List<TCashHist> findByBankname(Object bankname) {
		return findByProperty(BANKNAME, bankname);
	}

	public List<TCashHist> findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List findAll() {
		log.debug("finding all TCashHist instances");
		try {
			String queryString = "from TCashHist";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TCashHist merge(TCashHist detachedInstance) {
		log.debug("merging TCashHist instance");
		try {
			TCashHist result = (TCashHist) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TCashHist instance) {
		log.debug("attaching dirty TCashHist instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TCashHist instance) {
		log.debug("attaching clean TCashHist instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TCashHistDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TCashHistDAO) ctx.getBean("TCashHistDAO");
	}
}