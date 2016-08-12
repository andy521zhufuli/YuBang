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
 * TAddress entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.sales.model.TAddress
 * @author MyEclipse Persistence Tools
 */

public class TAddressDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TAddressDAO.class);
	// property constants
	public static final String USERID = "userid";
	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String PHONE = "phone";

	protected void initDao() {
		// do nothing
	}

	public void save(TAddress transientInstance) {
		log.debug("saving TAddress instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TAddress persistentInstance) {
		log.debug("deleting TAddress instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TAddress findById(java.lang.Integer id) {
		log.debug("getting TAddress instance with id: " + id);
		try {
			TAddress instance = (TAddress) getHibernateTemplate().get(
					"com.sales.model.TAddress", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TAddress> findByExample(TAddress instance) {
		log.debug("finding TAddress instance by example");
		try {
			List<TAddress> results = (List<TAddress>) getHibernateTemplate()
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
		log.debug("finding TAddress instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TAddress as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TAddress> findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List<TAddress> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<TAddress> findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List<TAddress> findByPhone(Object phone) {
		return findByProperty(PHONE, phone);
	}

	public List findAll() {
		log.debug("finding all TAddress instances");
		try {
			String queryString = "from TAddress";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TAddress merge(TAddress detachedInstance) {
		log.debug("merging TAddress instance");
		try {
			TAddress result = (TAddress) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TAddress instance) {
		log.debug("attaching dirty TAddress instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TAddress instance) {
		log.debug("attaching clean TAddress instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TAddressDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TAddressDAO) ctx.getBean("TAddressDAO");
	}
}