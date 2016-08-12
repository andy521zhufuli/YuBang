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
 * TCashBank entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sales.model.TCashBank
 * @author MyEclipse Persistence Tools
 */

public class TCashBankDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TCashBankDAO.class);
	// property constants
	public static final String BANKNAME = "bankname";
	public static final String IMGURL = "imgurl";
	public static final String STATUS = "status";

	protected void initDao() {
		// do nothing
	}

	public void save(TCashBank transientInstance) {
		log.debug("saving TCashBank instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TCashBank persistentInstance) {
		log.debug("deleting TCashBank instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TCashBank findById(java.lang.Integer id) {
		log.debug("getting TCashBank instance with id: " + id);
		try {
			TCashBank instance = (TCashBank) getHibernateTemplate().get(
					"com.sales.model.TCashBank", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TCashBank> findByExample(TCashBank instance) {
		log.debug("finding TCashBank instance by example");
		try {
			List<TCashBank> results = (List<TCashBank>) getHibernateTemplate()
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
		log.debug("finding TCashBank instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TCashBank as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TCashBank> findByBankname(Object bankname) {
		return findByProperty(BANKNAME, bankname);
	}

	public List<TCashBank> findByImgurl(Object imgurl) {
		return findByProperty(IMGURL, imgurl);
	}

	public List<TCashBank> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all TCashBank instances");
		try {
			String queryString = "from TCashBank";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TCashBank merge(TCashBank detachedInstance) {
		log.debug("merging TCashBank instance");
		try {
			TCashBank result = (TCashBank) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TCashBank instance) {
		log.debug("attaching dirty TCashBank instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TCashBank instance) {
		log.debug("attaching clean TCashBank instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TCashBankDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TCashBankDAO) ctx.getBean("TCashBankDAO");
	}
}