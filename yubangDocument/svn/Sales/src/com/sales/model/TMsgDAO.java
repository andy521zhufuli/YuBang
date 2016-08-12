package com.sales.model;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for TMsg
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.sales.model.TMsg
 * @author MyEclipse Persistence Tools
 */

public class TMsgDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(TMsgDAO.class);
	// property constants
	public static final String USERID = "userid";
	public static final String CODE = "code";
	public static final String TID = "tid";
	public static final String PHONE = "phone";
	public static final String STATUS = "status";

	protected void initDao() {
		// do nothing
	}

	public void save(TMsg transientInstance) {
		log.debug("saving TMsg instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TMsg persistentInstance) {
		log.debug("deleting TMsg instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMsg findById(java.lang.Integer id) {
		log.debug("getting TMsg instance with id: " + id);
		try {
			TMsg instance = (TMsg) getHibernateTemplate().get(
					"com.sales.model.TMsg", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TMsg> findByExample(TMsg instance) {
		log.debug("finding TMsg instance by example");
		try {
			List<TMsg> results = (List<TMsg>) getHibernateTemplate()
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
		log.debug("finding TMsg instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TMsg as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TMsg> findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List<TMsg> findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List<TMsg> findByTid(Object tid) {
		return findByProperty(TID, tid);
	}

	public List<TMsg> findByPhone(Object phone) {
		return findByProperty(PHONE, phone);
	}

	public List<TMsg> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all TMsg instances");
		try {
			String queryString = "from TMsg";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TMsg merge(TMsg detachedInstance) {
		log.debug("merging TMsg instance");
		try {
			TMsg result = (TMsg) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TMsg instance) {
		log.debug("attaching dirty TMsg instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TMsg instance) {
		log.debug("attaching clean TMsg instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TMsgDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TMsgDAO) ctx.getBean("TMsgDAO");
	}
}